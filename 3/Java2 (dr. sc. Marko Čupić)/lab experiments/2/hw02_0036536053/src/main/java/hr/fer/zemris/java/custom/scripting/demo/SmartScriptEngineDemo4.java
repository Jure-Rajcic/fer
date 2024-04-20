package hr.fer.zemris.java.custom.scripting.demo;

import java.io.*;
import java.util.*;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartScriptEngineDemo4 {

    public static void main(String[] args) throws IOException {
        // String documentBody = readFromDisk("fibonacci.smscr"); // za ovaj neradi ... neki retardirani format i NECU ga popravljati!!!!
        String documentBody = readFromDisk("fibonaccih.smscr");
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> persistentParameters = new HashMap<String, String>();
        List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
        // create engine and execute it
        // new SmartScriptParser(documentBody).getDocumentNode().accept(new WriterVisitor());
        new SmartScriptEngine(
                new SmartScriptParser(documentBody).getDocumentNode(),
                new RequestContext(System.out, parameters, persistentParameters, cookies, null)).execute();
    }


    private static String readFromDisk(String filename) throws IOException {
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
