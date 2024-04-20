package hr.fer.oprpp1.custom.scripting;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
public class BasicJunitTest {


    @Test
	public void testForLoopWithLotsOfWhiteSpaces() {
        String expected = "{$ FOR sco_re \"-1\" 10 \"1\" $}";
        String s = "{$      foR     sco_re \"-1\"10 \"1\" $}";
        SmartScriptParser parser = new SmartScriptParser(s);
		DocumentNode document = parser.getDocumentNode();
		assertTrue(document.toString().equals(expected));
	}

    @Test
	public void testForWithAnotherVariable() {
        String expected = "{$ FOR year 1 last_year $}";
        String s = "{$ FOR year 1 last_year $}";
        SmartScriptParser parser = new SmartScriptParser(s);
		DocumentNode document = parser.getDocumentNode();
		assertTrue(document.toString().equals(expected));
	}

    @Test
	public void test3IsNotVariableName() {
        String s = "{$ FOR 3 1 10 1 $}";
        assertThrows(SmartScriptParserException.class, () -> {
			SmartScriptParser parser = new SmartScriptParser(s);
		});
	}

    @Test
	public void testStarIsNotVariableName() {
        String s = "{$ FOR * \"1\" -10 \"1\" $}";
        assertThrows(SmartScriptParserException.class, () -> {
			SmartScriptParser parser = new SmartScriptParser(s);
		});
	}

    @Test
	public void testSinFunctionElement() {
        String s = "{$ FOR year @sin 10 $}";
        assertThrows(SmartScriptParserException.class, () -> {
			SmartScriptParser parser = new SmartScriptParser(s);
		});
	}

    @Test
	public void testToManyArguments() {
        String s = "{$ FOR year 1 10 \"1\" \"10\" $}";
        assertThrows(SmartScriptParserException.class, () -> {
			SmartScriptParser parser = new SmartScriptParser(s);
		});
	}

    @Test
	public void testToFewArguments() {
        String s = "{$ FOR year $}";
        assertThrows(SmartScriptParserException.class, () -> {
			SmartScriptParser parser = new SmartScriptParser(s);
		});
	}

    @Test
	public void  testLexerShouldExtractAsManyCharactersAsPossibleIntoEachToken() {
        String expected = "{$ FOR i -1.35 bbb \"1\" $}";
        String s = "{$ FOR i-1.35bbb\"1\" $}";
        SmartScriptParser parser = new SmartScriptParser(s);
		DocumentNode document = parser.getDocumentNode();
		assertTrue(document.toString().equals(expected));
	}


    @Test
	public void  testNextDocumentIsJustASingleText() {
        String s = "Example { bla } blu \\{$=1$}. Nothing interesting {=here}.";
        SmartScriptParser parser = new SmartScriptParser(s);
		DocumentNode document = parser.getDocumentNode();
        assertTrue(document.numberOfChildren() == 1);
        assertTrue(document.getChild(0) instanceof TextNode);
        assertTrue(document.toString().equals(s));
	}

    @Test
	public void  testNowActuallyWriteOne() {
        String s = "Example \\{$=1$}. Now actually write one {$=1$}";
        String expected = "Example {$=1$}. Now actually write one ";

        SmartScriptParser parser = new SmartScriptParser(s);
		DocumentNode document = parser.getDocumentNode();
        assertTrue(document.numberOfChildren() == 2);
        assertTrue(document.getChild(0) instanceof TextNode);
        assertTrue(document.getChild(1) instanceof EchoNode);
        assertTrue(((TextNode)document.getChild(0)).getText().equals(expected));
        assertTrue(((EchoNode)document.getChild(1)).getElements().length ==1);
	}






    
}
