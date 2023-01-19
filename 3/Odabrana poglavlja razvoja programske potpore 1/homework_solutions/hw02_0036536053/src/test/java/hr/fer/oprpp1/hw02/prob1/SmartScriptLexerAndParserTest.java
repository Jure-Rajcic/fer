package hr.fer.oprpp1.hw02.prob1;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptLexerAndParserTest {

    private String readExample(int n) {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer" + n + ".txt")) {
            if (is == null)
                throw new RuntimeException("Datoteka extra/primjer" + n + ".txt je nedostupna.");
            byte[] data = is.readAllBytes();
            String text = new String(data, StandardCharsets.UTF_8);
            return text;
        } catch (IOException ex) {
            throw new RuntimeException("Greška pri čitanju datoteke.", ex);
        }
    }

    // Potom u isti razred dodati po jedan test za svaki primjer; test će biti
    // oblika:
    // String text = readExample(1);
    // isparsiraj text
    // assertaj da si dobio točno jedan textnode
    
	@Test
	public void testPrimjer1() {
		String docBody = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2); // ==> "same" must be true
		assertTrue(same);
	}

    @Test
	public void testPrimjer2() {
		String docBody = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		// document će imati samo jedno dijete
		assertTrue(document.numberOfChildren() == 1);
		// to dijete će biti tipa TextNode
		assertTrue(document.getChild(0) instanceof TextNode);
	}

    @Test
	public void testPrimjer3() {
		String docBody = readExample(3);
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		// document će imati samo jedno dijete
		assertTrue(document.numberOfChildren() == 1);
		// to dijete će biti tipa TextNode
		assertTrue(document.getChild(0) instanceof TextNode);
	}


    @Test
	public void testPrimjer4() {
		String docBody = readExample(4);
		// izazvat će se LexerException zbog nepravilnog escape znaka
		assertThrows(SmartScriptParserException.class, () -> {
			SmartScriptParser parser = new SmartScriptParser(docBody);
		});
	}
	
	@Test
	public void testPrimjer5() {
		String docBody = readExample(5);
		// izazvat će se LexerException zbog nepravilnog escape znaka
		assertThrows(SmartScriptParserException.class, () -> {
			SmartScriptParser parser = new SmartScriptParser(docBody);
		});
	}
	
	@Test
	public void testPrimjer6() {
		String docBody = readExample(6);
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// document i document2 trebaju imati isti broj djece
		assertTrue(document.numberOfChildren() == document2.numberOfChildren());
	}
	
	@Test
	public void testPrimjer7() {
		String docBody = readExample(7);
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		// document ima dvoje djece
		assertTrue(document.numberOfChildren() == 2);
		// prvo dijete je tipa TextNode
		assertTrue(document.getChild(0) instanceof TextNode);
		// drugo dijete je tipa EchoNode
		assertTrue(document.getChild(1) instanceof EchoNode);
	}
	
	@Test
	public void testPrimjer8() {
		String docBody = readExample(8);
		// izazvat će se LexerException zbog nepravilnog escape znaka
		assertThrows(SmartScriptParserException.class, () -> {
			SmartScriptParser parser = new SmartScriptParser(docBody);
		});
	}
	
	@Test
	public void testPrimjer9() {
		String docBody = readExample(9);
		// izazvat će se LexerException zbog nepravilnog escape znaka
		assertThrows(SmartScriptParserException.class, () -> {
			SmartScriptParser parser = new SmartScriptParser(docBody);
		});
	}
}
