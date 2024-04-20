package hr.fer.oprpp1.hw04.db;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComparisonOperatorsTest {
    
    @Test
    public void testLESS() {
        IComparisonOperator oper = ComparisonOperators.LESS;
        assertTrue(oper.satisfied("Ana", "Jasna")); 
        assertFalse(oper.satisfied("Karla", "Jasna"));
    }

    @Test
    public void testLESS_OR_EQUALS() {
        IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
        assertTrue(oper.satisfied("Ana", "Jasna")); 
        assertTrue(oper.satisfied("Ana", "Ana")); 
        assertFalse(oper.satisfied("Karla", "Jasna"));
    }

    @Test
    public void testGREATER() {
        IComparisonOperator oper = ComparisonOperators.GREATER;
        assertTrue(oper.satisfied("Jure", "Bozo")); 
        assertFalse(oper.satisfied("Roko", "Tomislav"));
    }

    @Test
    public void testGREATER_OR_EQUALS() {
        IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
        assertTrue(oper.satisfied("Jure", "Jura")); 
        assertTrue(oper.satisfied("Jure", "Jure")); 
        assertFalse(oper.satisfied("Petar", "Šime"));
    }

    @Test
    public void testEQUALS() {
        IComparisonOperator oper = ComparisonOperators.EQUALS;
        assertTrue(oper.satisfied("Jure", "Jure")); 
        assertFalse(oper.satisfied("Roko", "Tomislav"));
    }

    @Test
    public void testNOT_EQUALS() {
        IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
        assertTrue(oper.satisfied("Jure", "Jura")); 
        assertFalse(oper.satisfied("Roko", "Roko"));
    }

    @Test
    public void testLIKE() {
        IComparisonOperator oper = ComparisonOperators.LIKE;
        assertTrue(oper.satisfied("Jure", "Ju*"));
        assertFalse(oper.satisfied("Jure", "jo*"));
        assertTrue(oper.satisfied("Jure", "*e"));
        assertFalse(oper.satisfied("Jure", "*o"));
        assertTrue(oper.satisfied("Jure", "J*e"));
        assertFalse(oper.satisfied("Jure", "J*o"));
    }

    @Test
    public void testLIKEExamplesFromHW() {
        IComparisonOperator oper = ComparisonOperators.LIKE;
        assertFalse(oper.satisfied("Zagreb", "Aba*"));
        assertFalse(oper.satisfied("AAA", "AA*AA"));
        assertTrue(oper.satisfied("AAAA", "AA*AA"));
    }


}
