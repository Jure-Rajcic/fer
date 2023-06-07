package hr.fer.zemris.java.webserver;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.*;

public class RequestContext {

    public static class RCCookie {
        private final String name, value, domain, path;
        private final Integer maxAge;
        private IDispatcher dispather;
        private boolean httpOnly;


        public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
            this.name = name;
            this.value = value;
            this.maxAge = maxAge;
            this.domain = domain;
            this.path = path;
        }
        

        public String getName() {
            return this.name;
        }

        public String getValue() {
            return this.value;
        }

        public String getDomain() {
            return this.domain;
        }

        public String getPath() {
            return this.path;
        }

        public Integer getMaxAge() {
            return this.maxAge;
        }

        public boolean isHttpOnly() {
            return this.httpOnly;
        }

        public void setHttpOnly(boolean httpOnly) {
            this.httpOnly = httpOnly;
        }

    }

    private final OutputStream outputStream;
    private Charset charset;

    private String encoding, statusText, mimeType;
    private int statusCode;
    private Long contentLength;
    public final  Map<String, String> parameters, temporaryParameters, persistentParameters;
    final private List<RCCookie> outputCookies;
    private boolean headerGenerated;
    private final IDispatcher dispatcher;
    private String SID;

    public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters, List<RCCookie> outputCookies, String SID) {
        this(outputStream, parameters, persistentParameters, outputCookies, null, null, SID);
    }

    public RequestContext(OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters, List<RCCookie> outputCookies,Map<String,String> temporaryParameters , IDispatcher dispatcher, String SID) {
        this.encoding = "UTF-8";
        this.statusCode = 200;
        this.statusText = "OK";
        this.mimeType = "text/html";
        this.contentLength = null;
        this.outputStream = Objects.requireNonNull(outputStream);
        this.parameters = parameters == null ? new HashMap<>() : parameters;
        this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
        this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
        this.headerGenerated = false;
        this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
        this.dispatcher = dispatcher;
        this.SID = SID;
    }

    public IDispatcher getDispatcher() {
        return dispatcher;
    }

    public void setEncoding(String encoding) {
        checkHeaderGenerated();
        this.encoding = encoding;
    }

    public void setStatusCode(int statusCode) {
        checkHeaderGenerated();
        this.statusCode = statusCode;
    }

    public void setStatusText(String statusText) {
        checkHeaderGenerated();
        this.statusText = statusText;
    }

    public void setMimeType(String mimeType) {
        checkHeaderGenerated();
        this.mimeType = mimeType;
    }

    public void setContentLength(Long contentLength) {
        checkHeaderGenerated();
        this.contentLength = contentLength;
    }

    public String getParameter(String name) {
        name = name.replaceAll("\"", "");
        return this.parameters.get(name);
    }

    public Set<String> getParameterNames() {
        return Collections.unmodifiableSet(this.parameters.keySet());
    }

    public String getPersistentParameter(String name) {
        return this.persistentParameters.get(name);
    }

    public Set<String> getPersistentParameterNames() {
        return Collections.unmodifiableSet(this.persistentParameters.keySet());
    }

    public void setPersistentParameter(String name, String value) {
        this.persistentParameters.put(name, value);
    }

    public void removePersistentParameter(String name) {
        this.persistentParameters.remove(name);
    }

    public String getTemporaryParameter(String name) {
        return this.temporaryParameters.get(name);
    }

    public Set<String> getTemporaryParameterNames() {
        return Collections.unmodifiableSet(this.temporaryParameters.keySet());
    }

    // * method that retrieves an identifier which is unique for current user
    // session (for now, implement it to return empty string):
    public String getSessionID() {
        return this.SID;
    }

    public void setTemporaryParameter(String name, String value) {
        this.temporaryParameters.put(name, value);
    }

    public void removeTemporaryParameter(String name) {
        this.temporaryParameters.remove(name);
    }

    public RequestContext write(byte[] data) throws IOException {
        writeHeaderIfNeeded();
        outputStream.write(data);
        return this;
    }

    public RequestContext write(byte[] data, int offset, int len) throws IOException {
        writeHeaderIfNeeded();
        outputStream.write(data, offset, len);
        return this;
    }

    public RequestContext write(String text) throws IOException {
        writeHeaderIfNeeded();
        outputStream.write(text.getBytes(charset));
        return this;
    }

    private void writeHeaderIfNeeded() throws IOException {
        if (headerGenerated) return;
        charset = Charset.forName(encoding);
        StringBuilder header = new StringBuilder();
        header.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append("\r\n");
        header.append("Content-Type: ").append(mimeType);
        if (mimeType.startsWith("text/")) 
            header.append("; charset=").append(encoding);
        header.append("\r\n");
        if (contentLength != null) 
            header.append("Content-Length: ").append(contentLength).append("\r\n");

        for (RCCookie cookie : outputCookies) {
            header.append("Set-Cookie: ").append(cookie.getName()).append("=").append("\"" + cookie.getValue() + "\"");
            if (cookie.getDomain() != null)
                header.append("; Domain=").append(cookie.getDomain());
            if (cookie.getPath() != null)
                header.append("; Path=").append(cookie.getPath());
            if (cookie.getMaxAge() != null)
                header.append("; Max-Age=").append(cookie.getMaxAge());
            if (cookie.isHttpOnly())
                header.append("; HttpOnly");
            header.append("\r\n");
        }
        header.append("\r\n");
        outputStream.write(header.toString().getBytes(charset));
        headerGenerated = true;
    }

    private void checkHeaderGenerated() {
        if (headerGenerated) 
            throw new RuntimeException("Cannot change property after header is generated.");
    }

    public void addRCCookie(RCCookie rcCookie) {
        outputCookies.add(rcCookie);
    }

}
