package hr.fer.zemris.java.custom.scripting.demo;

import java.io.*;
import hr.fer.zemris.java.custom.scripting.parser.*;
import hr.fer.zemris.java.custom.scripting.nodes.WriterVisitor;


    
public class TreeWriter {

    // demo program
    public static void main(String[] args) throws IOException{
        TreeWriter.main("/Users/jrajcic/Desktop/java/hw02_0036536053/src/main/java/hr/fer/zemris/java/custom/scripting/demo/testing/tree.smscr");
    }

    public static void main(String fileName) throws IOException {
        if (!fileName.endsWith(".smscr")) 
            throw new RuntimeException("File extension must be .smscr");
        String docBody = docText(fileName);
        SmartScriptParser p = null;
        try {
            p = new SmartScriptParser(docBody); // calls parser.parse();
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.out.println(e.getMessage());
            System.exit(-1);
        } catch (RuntimeException e) {
            System.out.println("I wont fail this class, but something went wrong");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }

        WriterVisitor visitor = new WriterVisitor();
        p.getDocumentNode().accept(visitor);

        // by the time the previous line completes its job, the document should have been
        // written  on the standard output

        // I would do it with "System.out.println(visitor.getOutput());"

    }
    

    private static String docText(String filename) throws IOException{
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
