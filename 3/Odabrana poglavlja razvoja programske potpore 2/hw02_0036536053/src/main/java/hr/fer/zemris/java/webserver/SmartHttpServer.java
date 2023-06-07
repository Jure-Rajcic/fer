package hr.fer.zemris.java.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;


public class SmartHttpServer {
    private String address, domainName;
    private int port, workerThreads, sessionTimeout;

    private Map<String, String> mimeTypes = new HashMap<String, String>();
    private Map<String, IWebWorker> workersMap = new HashMap<String, IWebWorker>();
    private ServerThread serverThread;
    private ExecutorService threadPool;
    private Path documentRoot;

    private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
    private Random sessionRandom = new Random();

    public SmartHttpServer(String configFileName) {
        Properties configProperties = new Properties();
        try (FileInputStream fis = new FileInputStream(configFileName)) {
            configProperties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Error reading the configuration file: " + configFileName, e);
        }

        address = configProperties.getProperty("server.address");
        domainName = configProperties.getProperty("server.domainName");
        port = Integer.parseInt(configProperties.getProperty("server.port"));
        workerThreads = Integer.parseInt(configProperties.getProperty("server.workerThreads"));
        documentRoot = Paths.get(configProperties.getProperty("server.documentRoot"));
        sessionTimeout = Integer.parseInt(configProperties.getProperty("session.timeout"));

        String mimeConfigPath = configProperties.getProperty("server.mimeConfig");
        loadMimeTypes(mimeConfigPath);

        String workersConfigPath = configProperties.getProperty("server.workers");
        loadWorkers(workersConfigPath);
    }

    private void loadMimeTypes(String mimeConfigPath) {
        Properties mimeProperties = new Properties();
        try (FileInputStream fis = new FileInputStream(mimeConfigPath)) {
            mimeProperties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Error reading the mime configuration file: " + mimeConfigPath, e);
        }
        for (String key : mimeProperties.stringPropertyNames()) {
            mimeTypes.put(key, mimeProperties.getProperty(key));
        }
    }

    private void loadWorkers(String workersConfigPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(workersConfigPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
    
                String[] parts = line.split("=");
                if (parts.length != 2) {
                    throw new RuntimeException("Invalid workers configuration file format: " + workersConfigPath);
                }
    
                String path = parts[0].trim();
                String fqcn = parts[1].trim();
    
                if (workersMap.containsKey(path)) {
                    throw new RuntimeException("Duplicate path in workers configuration file: " + path);
                }
    
                try {
                    Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
                    Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
                    IWebWorker iww = (IWebWorker) newObject;
                    workersMap.put(path, iww);
                } catch (Exception e) {
                    throw new RuntimeException("Error creating worker instance for path: " + path, e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the workers configuration file: " + workersConfigPath, e);
        }
    }
    

    protected synchronized void start() {
        if (serverThread == null || !serverThread.isAlive()) {
            serverThread = new ServerThread();
            serverThread.start();
            System.out.println("Server thread started.");
        } else {
            System.out.println("Server thread is already running.");
        }

        if (threadPool == null || threadPool.isShutdown()) {
            threadPool = Executors.newFixedThreadPool(workerThreads);
            System.out.println("Thread pool initialized.");
        } else {
            System.out.println("Thread pool is already initialized.");
        }
        startSessionCleanupThread();
    }

    private void startSessionCleanupThread() {
        Thread cleanupThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5 * 60 * 1000); // Sleep for 5 minutes
    
                    long currentTime = System.currentTimeMillis();
                    sessions.entrySet().removeIf(entry -> currentTime > entry.getValue().validUntil);
    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    
        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }

    protected synchronized void stop() {
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.interrupt();
            try {
                serverThread.join();
            } catch (InterruptedException e) {
                System.out.println("Error while waiting for server thread to stop.");
            }
            System.out.println("Server thread stopped.");
        } else {
            System.out.println("Server thread is not running.");
        }

        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(30, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
            }
            System.out.println("Thread pool shut down.");
        } else {
            System.out.println("Thread pool is not initialized.");
        }
    }

    protected class ServerThread extends Thread {
        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (!Thread.currentThread().isInterrupted()) {
                    Socket clientSocket = serverSocket.accept();
                    ClientWorker clientWorker = new ClientWorker(clientSocket);
                    threadPool.submit(clientWorker);
                }
            } catch (IOException e) {
                if (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Error occurred in server thread: " + e.getMessage());
                }
            }
        }
    }

    private class ClientWorker implements Runnable, IDispatcher{
        private Socket csocket;
        private InputStream istream;
        private OutputStream ostream;
        private String version, method, host;

        private Map<String, String> params = new HashMap<String, String>();
        private Map<String, String> tempParams = new HashMap<String, String>();
        private Map<String, String> permPrams = new HashMap<String, String>();
        private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
        private String SID;
        private RequestContext context;
       


        public ClientWorker(Socket csocket) {
            super();
            this.csocket = csocket;
            this.SID = generateSID();
            this.host = csocket.getInetAddress().getHostAddress();
            
        }

        private String generateSID() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                sb.append((char) ('A' + sessionRandom.nextInt(26)));
            }
            return sb.toString();
		}

		@Override
        public void run() {

            try {
                istream = csocket.getInputStream();
                ostream = csocket.getOutputStream();
                this.context =  new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);

                List<String> request = readRequest();
                if (request.size() < 1) {
                    sendError( 400, "Bad Request");
                    return;
                }

                String firstLine = request.get(0);
                String[] firstLineParts = firstLine.split(" ");
                if (firstLineParts.length != 3) {
                    sendError( 400, "Bad Request");
                    return;
                }

                method = firstLineParts[0].toUpperCase();
                if (!method.equals("GET")) {
                    sendError( 400, "Bad Request");
                    return;
                }

                version = firstLineParts[2].toUpperCase();
                if (!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1")) {
                    sendError( 400, "Bad Request");
                    return;
                }

                String requestedPath = firstLineParts[1];

                host = domainName;
                for (String header : request) {
                    if (header.toLowerCase().startsWith("host:")) {
                        host = header.substring(5).trim();
                        int colonIndex = host.indexOf(':');
                        if (colonIndex > 0) {
                            host = host.substring(0, colonIndex);
                        }
                        break;
                    }
                }
                checkSession(request);

                // System.out.println("host: " + host);
                String path;
                String paramString;
                // (path, paramString) = split requestedPath to path and parameterString
                String[] pathParts = requestedPath.split("\\?", 2);
                path = pathParts[0];
                if (pathParts.length > 1) {
                    paramString = pathParts[1];
                    parseParameters(paramString);
                }

                

                // requestedPath = resolve path with respect to documentRoot
                Path resolvedPath = documentRoot.resolve(path.substring(1)).normalize();

                // TODO pitat profesora kako napisati tu curl naredbu za testiranje ove linije
                // 'curl "http://127.0.0.1:5721/../primjer3.txt" u terminal ???'


                // if requestedPath is not below documentRoot, return response status 403
                // forbidden
                if (!resolvedPath.startsWith(documentRoot)) {
                    sendError( 403, "Forbidden");
                    return;
                }
                
                try { if (internalDispatchRequest(resolvedPath.toString(), true)) return; } catch (Exception e) { e.printStackTrace(); };

                // check if requestedPath exists, is file and is readable; if not, return status
                // 404
                if (!Files.exists(resolvedPath) || !Files.isRegularFile(resolvedPath)
                        || !Files.isReadable(resolvedPath)) {
                    sendError( 404, "Not Found");
                    return;
                }

                // extract file extension from resolvedPath
                String fileExtension = resolvedPath.toString().substring(resolvedPath.toString().lastIndexOf('.') + 1);

                // find in mimeTypes map appropriate mimeType for current file extension
                //  if no mime type found, assume application/octet-stream (filled in constructor)
                String mimeType = mimeTypes.getOrDefault(fileExtension, "application/octet-stream");

                // create a rc = new RequestContext(...); set mime-type; set status to 200
                RequestContext rc = new RequestContext(ostream, params, permPrams, this.outputCookies, this.SID);
                rc.setMimeType(mimeType);
                rc.setStatusCode(200);

                // // If you want, you can modify RequestContext to allow you to add additional headers
                // // so that you can add “Content-Length: 12345” if you know that file has 12345 bytes
                // long contentLength = Files.size(resolvedPath);
                // rc.addHeader("Content-Length", Long.toString(contentLength));

                // open file, read its content and write it to rc (that will generate header and
                // send
                // file bytes to client)
                byte[] content = Files.readAllBytes(resolvedPath);
                rc.write(content);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error occurred in client worker: " + e.getMessage());
            } finally {
                try {
                    csocket.close();
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }

        private synchronized void checkSession(List<String> requestHeaders) {
            String sidCandidate = null;
            String host = domainName;

            for (String line : requestHeaders) {
                if (line.startsWith("Host:")) {
                    host = line.substring(5).trim().split(":")[0];
                } else if (line.startsWith("Cookie:")) {
                    String[] cookies = line.substring(7).trim().split(";");
                    
                    for (String cookie : cookies) {
                        String[] parts = cookie.trim().split("=", 2);
        
                        if (parts[0].equals("sid")) {
                            sidCandidate = parts[1].replaceAll("\"", "");
                            break;
                        }
                    }
                }
            }
            if (sidCandidate == null) {
                sidCandidate = generateSID();
            }
            System.out.println("sidCandidate: " + sidCandidate);
            synchronized (sessions) {
                if (!sessions.containsKey(sidCandidate)) {
                    SessionMapEntry sessionEntry = new SessionMapEntry(sidCandidate, host, System.currentTimeMillis() + sessionTimeout * 1000);
                    sessionEntry.map = new ConcurrentHashMap<>();
                    sessionEntry.map.put("brojPoziva", "0");
                    sessions.put(sidCandidate, sessionEntry);
                } 
                SessionMapEntry sessionEntry = sessions.get(sidCandidate);
                System.out.println("sessionEntry.map: " + sessionEntry.map);
                sessionEntry.validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
                sessionEntry.map.put("brojPoziva", Integer.toString(Integer.parseInt(sessionEntry.map.get("brojPoziva")) + 1));
                this.permPrams.put("brojPoziva", sessionEntry.map.get("brojPoziva"));
            }
            System.out.println("sessions: " + sessions);
        }

		private List<String> readRequest() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
            List<String> requestLines = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                requestLines.add(line);
            }
            return requestLines;
        }

        private void sendError(int statusCode, String statusText) throws IOException {
            ostream.write((version + " " + statusCode + " " + statusText + "\r\n" +
                    "Server: SmartHttpServer\r\n" +
                    "Content-Type: text/plain;charset=UTF-8\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    statusText).getBytes(StandardCharsets.UTF_8));
            ostream.flush();
        }

        private void parseParameters(String paramString) {
            if (paramString == null || paramString.isEmpty()) {
                return;
            }

            String[] paramPairs = paramString.split("&");
            for (String pair : paramPairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
            // System.out.println(params);
        }

        @Override
        public void dispatchRequest(String urlPath) throws Exception {
            System.out.println("dispatchRequest: " + urlPath);
            internalDispatchRequest(urlPath, false);
        }

        private boolean internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
            if (urlPath.equals("/private") || urlPath.startsWith("/private/")) {
                if (directCall) {
                    sendError(404, "Not Found");
                    return true;
                }
            }

            String relativePath = documentRoot.relativize(Paths.get(urlPath)).toString();
            if (relativePath.startsWith("ext/")) {
                String workerName = relativePath.substring(4);
                Class<?> referenceToClass = this.getClass().getClassLoader().loadClass("hr.fer.zemris.java.webserver.workers." + workerName);
                Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
                IWebWorker iww = (IWebWorker) newObject;
                iww.processRequest(this.context);
                return true;
            }
            

            // Check if the requested path is mapped to an IWebWorker
            int lastSlashIndex = urlPath.lastIndexOf('/');
            IWebWorker worker = workersMap.get(urlPath.substring(lastSlashIndex));
            if (worker != null) {
                worker.processRequest(this.context);
                return true;
            }

            if (relativePath.endsWith(".smscr")) {
                if (!urlPath.contains(documentRoot.toString())) {
                    urlPath = documentRoot + urlPath;
                }
                System.out.println(urlPath);
                final BufferedReader br = new BufferedReader(new FileReader(urlPath));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                br.close();
                String documentBody = sb.toString();
                DocumentNode documentNode = new SmartScriptParser(documentBody).getDocumentNode();
                new SmartScriptEngine(documentNode, this.context).execute();
                return true;
            }
            return false;
        }
        
    }

    private static class SessionMapEntry {
        String sid;
        String host;
        long validUntil;
        Map<String,String> map;

        public SessionMapEntry(String sid, String host, long validUntil) {
            this.sid = sid;
            this.host = host;
            this.validUntil = validUntil;
        }

        @Override
        public String toString() {
            return map.toString();
        }
    }

    public static void main(String[] args) {
        // if (args.length != 1) {
        //     System.out.println("Please provide the configuration file as an argument.");
        //     System.exit(1);
        // }
        // String configFileName = args[0];
        String configFileName = "./config/server.properties";
        SmartHttpServer server = new SmartHttpServer(configFileName);
        server.start();
    }

    // /usr/bin/env /Library/Java/JavaVirtualMachines/jdk-18.0.2.jdk/Contents/Home/bin/java -XX:+ShowCodeDetailsInExceptionMessages -cp /Users/jrajcic/Desktop/java/hw02_0036536053/target/classes hr.fer.zemris.java.webserver.SmartHttpServer ./config/server.properties
    // http://localhost:5721/index2
}