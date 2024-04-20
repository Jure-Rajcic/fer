package hr.fer.oprpp1.hw02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String fileName = "src/main/java/hr/fer/oprpp1/hw02/examples/doc1.txt";
        // System.out.print("Enter relative path to file: ");
        // String fileName = sc.nextLine();
        sc.close();
        String docBody = docText(fileName);
        SmartScriptParser parser = null;
        try {
            parser = new SmartScriptParser(docBody); // calls parser.parse();
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
        DocumentNode document = parser.getDocumentNode();
        /* String originalDocumentBody = document.makeTree(0); */
        String originalDocumentBody = document.toString();
        System.out.println("[" +originalDocumentBody + "]"); // something like doc1.txt
        // System.out.println(document.numberOfChildren()); // 2

        // SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        // DocumentNode document2 = parser2.getDocumentNode();
        // System.out.println("[" +document2.toString() + "]"); // something like doc1.txt

        // // now document and document2 should be structurally identical trees
        // boolean same = document.equals(document2); // ==> "same" must be true
        // System.out.println(same);

    }
    

    private static String docText(String filename) {
        try (BufferedReader br = new BufferedReader(
                new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
