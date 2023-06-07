package hr.fer.zemris.java.custom.scripting.demo;
import java.io.*;
import java.util.*;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartScriptEngineDemo1 {
    
    public static void main(String[] args) throws IOException{
        String documentBody = readFromDisk("osnovni.smscr");
        Map<String,String> parameters = new HashMap<String, String>();
        Map<String,String> persistentParameters = new HashMap<String, String>();
        List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
        // put some parameter into parameters map
        parameters.put("broj", "4");
        // create engine and execute it
        DocumentNode documentNode = new SmartScriptParser(documentBody).getDocumentNode();
        // WriterVisitor visitor = new WriterVisitor();
        // documentNode.accept(visitor);
        RequestContext requestContext = new RequestContext(System.out, parameters, persistentParameters, cookies, null);
        new SmartScriptEngine(documentNode, requestContext).execute();
    }

    private static String readFromDisk(String filename) throws IOException{
        filename = "src/main/java/hr/fer/zemris/java/custom/scripting/demo/testing/" + filename; 
        final BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        br.close();
        return sb.toString();
    }
}
