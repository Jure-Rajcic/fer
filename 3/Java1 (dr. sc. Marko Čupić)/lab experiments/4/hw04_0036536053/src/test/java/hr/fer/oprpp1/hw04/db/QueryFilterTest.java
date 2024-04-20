package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class QueryFilterTest {

    public static List<StudentRecord> getStudentRecords(String input) {
        List<StudentRecord> records = new ArrayList<>();
        StudentDatabase db = StudentDatabase.database();
        QueryParser parser = new QueryParser(input);
        if (parser.isDirectQuery()) {
            StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
            records.add(r);
        } else {
            for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
                records.add(r);
            }
        }
        return records;
    }

    @Test
    public void testQueryFilter1() {
        List<StudentRecord> list = getStudentRecords("query jmbag = \"0000000003\"");
        assertEquals("0000000003", list.get(0).getJmbag());
        assertEquals("Bosnić", list.get(0).getLastName());
        assertEquals("Andrea", list.get(0).getFirstName());
        assertEquals("4", list.get(0).getFinalGrade());
        assertEquals(1, list.size());
    }

    @Test
    public void testQueryFilter2() {
        List<StudentRecord> list = getStudentRecords("query jmbag = \"0000000003\" AND lastName LIKE \"B*\"");
        assertEquals("0000000003", list.get(0).getJmbag());
        assertEquals("Bosnić", list.get(0).getLastName());
        assertEquals("Andrea", list.get(0).getFirstName());
        assertEquals("4", list.get(0).getFinalGrade());
        assertEquals(1, list.size());
    }

    @Test
    public void testQueryFilter3() {
        String[] jmbag = new String[] { "0000000002", "0000000003", "0000000004", "0000000005" };
        String[] lastName = new String[] { "Bakamović", "Bosnić", "Božić", "Brezović" };
        String[] firstName = new String[] { "Petra", "Andrea", "Marin", "Jusufadis" };
        String[] finalGrade = new String[] { "3", "4", "5", "2" };

        List<StudentRecord> list = getStudentRecords(" query lastName LIKE \"B*\"");
        assertEquals(4, list.size());
        for (int i = 0; i < list.size(); i++) {
            assertEquals(jmbag[i], list.get(i).getJmbag());
            assertEquals(lastName[i], list.get(i).getLastName());
            assertEquals(firstName[i], list.get(i).getFirstName());
            assertEquals(finalGrade[i], list.get(i).getFinalGrade());
        }
    }

    @Test
    public void testQueryFilter4() {
        List<StudentRecord> list = getStudentRecords("  query lastName LIKE \"Be*\"");
        assertEquals(0, list.size());
    }

    @Test
    public void testQueryFilter5() {
        StudentRecord expected = new StudentRecord("0000000003", "Bosnić", "Andrea", "4");
        String[] querys = new String[] { "query lastName=\"Bosnić\"", "query lastName =\"Bosnić\"",
        "query lastName= \"Bosnić\"", "query lastName = \"Bosnić\"" };
        for(int i = 0; i < querys.length; i++) {
            List<StudentRecord> list = getStudentRecords(querys[i]);
            assertEquals(1, list.size());
            assertEquals(expected, list.get(0));
        }
    }
}
