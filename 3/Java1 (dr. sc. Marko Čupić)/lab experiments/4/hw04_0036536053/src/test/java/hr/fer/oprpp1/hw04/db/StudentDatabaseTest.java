package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentDatabaseTest {

    @Test
    // @Disabled
    public void testForJMBAGValid() {
        StudentDatabase sd = StudentDatabase.database();
        assertNotNull(sd.forJMBAG("0000000019"));
    }

    @Test
    // @Disabled
    public void testForJMBAGNotValid() {
        StudentDatabase sd = StudentDatabase.database();
        assertNull(sd.forJMBAG("xxxxxxxxx"));
    }

    /* for some reason comand [mvn test] ignores @Disable anotation */
    // @Test
    // @Disabled
    // public void testMultipleStudentsHaveSameJMBAG() {
    //     // in database.txt set multiple students to have same jmbag
    //     assertThrows(IllegalArgumentException.class, () -> StudentDatabase.database() );
    // }

    // @Test
    // @Disabled
    // public void testStudentHasInvalidgrade() {
    //     // in database.txt set student grade to value other than 1,2,3,4,5
    //     assertThrows(IllegalArgumentException.class, () -> StudentDatabase.database() );
    // }

}
