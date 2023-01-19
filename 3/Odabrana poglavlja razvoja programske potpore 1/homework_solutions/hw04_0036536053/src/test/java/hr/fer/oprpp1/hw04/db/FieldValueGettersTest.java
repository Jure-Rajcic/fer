package hr.fer.oprpp1.hw04.db;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FieldValueGettersTest {

    
    public static StudentRecord getSomehowOneRecord() {
        StudentDatabase sd = StudentDatabase.database();
        return sd.forJMBAG("0000000019");
    }

    @Test
    public void testFIRST_NAME() {
        String expected="Slaven"; 
        StudentRecord record = getSomehowOneRecord();
        assertEquals(expected, FieldValueGetters.FIRST_NAME.get(record));
    }

    @Test
    public void testLAST_NAME() {
        String expected="Gvardijan"; 
        StudentRecord record = getSomehowOneRecord();
        assertEquals(expected, FieldValueGetters.LAST_NAME.get(record));
    }

    @Test
    public void testJMBAG() {
        String expected="0000000019"; 
        StudentRecord record = getSomehowOneRecord();
        assertEquals(expected, FieldValueGetters.JMBAG.get(record));
    }
    
}
