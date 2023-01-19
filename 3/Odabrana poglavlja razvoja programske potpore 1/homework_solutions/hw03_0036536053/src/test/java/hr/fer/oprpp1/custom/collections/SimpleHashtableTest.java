package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHashtableTest {

    @Test
    public void constructorShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<String, Integer>(0));
    }

    @Test
    public void testCapacityIsPowerOf2() {
        SimpleHashtable<String, Integer> sh4 = new SimpleHashtable<String, Integer>(4);
        assertEquals(4, sh4.getTableCapacity());
        SimpleHashtable<String, Integer> sh8 = new SimpleHashtable<String, Integer>(5);
        assertEquals(8, sh8.getTableCapacity());
    }

    private static SimpleHashtable<String, Integer> loadExample() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
        examMarks.put("Ivana", 1);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 3);
        examMarks.put("Kristina", 4);
        return examMarks;
    }

    @Test
    public void testHashTablePutThrowsNullPointerException() {
        SimpleHashtable<String, Integer> examMarks = loadExample();
        assertThrows(NullPointerException.class, () -> examMarks.put(null, null));
    }

    @Test
    public void testHashTablePutDoesNotThrowsNullPointerException() {
        SimpleHashtable<String, Integer> examMarks = loadExample();
        assertDoesNotThrow(() -> examMarks.put("bla bla", null));
    }

    @Test
    public void testHashTablePut() {
        SimpleHashtable<String, Integer> examMarks = loadExample();
        assertEquals(1, examMarks.get("Ivana"));
        assertEquals(2, examMarks.get("Ante"));
        assertEquals(3, examMarks.get("Jasna"));
        assertEquals(4, examMarks.get("Kristina"));
    }

    @Test
    public void testHashTablePutAdds() {
        SimpleHashtable<String, Integer> examMarks = loadExample();
        examMarks.put("Jure", 5);
        assertEquals(5, examMarks.get("Jure"));
        assertEquals(5, examMarks.size());
    }

    @Test
    public void testHashTablePutOverides() {
        SimpleHashtable<String, Integer> examMarks = loadExample();
        examMarks.put("Ivana", 5);
        assertEquals(5, examMarks.get("Ivana"));
        assertEquals(4, examMarks.size());
    }

    @Test
    public void testGetThrowsNullPointerException() {
        SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
        assertThrows(NullPointerException.class, () -> sh.get(null));
    }

    @Test
    public void testGetReturnSNullForKeyThatIsNotInTable() {
        SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
        assertNull(sh.get("nesto sto nije u tablici"));
    }

    @Test
    public void testContainsKey() {
        SimpleHashtable<String, Integer> sh = loadExample();
        assertFalse(sh.containsKey(null));
        assertFalse(sh.containsKey("string"));
        assertTrue(sh.containsKey("Ivana"));
    }

    @Test
    public void testContainsValue() {
        SimpleHashtable<String, Integer> sh = loadExample();
        assertFalse(sh.containsValue(null));
        assertFalse(sh.containsValue("string"));
        sh.put("Ivana", null);
        assertTrue(sh.containsValue(null));
    }

    @Test
    public void testRemove() {
        SimpleHashtable<String, Integer> sh = loadExample();
        sh.remove("Ivana");
        assertEquals(3, sh.size());
        assertNull(sh.remove(null));
        assertNull(sh.remove("nesto sto nije u tablic"));
        assertEquals(3, sh.size());
    }

    @Test
    public void isEmpty() {

        SimpleHashtable<String, Integer> sh = new SimpleHashtable<>();
        assertTrue(sh.isEmpty());
        sh = loadExample();
        assertFalse(sh.isEmpty());
    }

    @Test
    public void testToString() {
        SimpleHashtable<String, Integer> examMarks = loadExample();
        // not realy good method to test if java dev decides to change algorithm for
        // default String hashCode
        assertEquals("[Ante=2, Ivana=1, Jasna=3, Kristina=4]", examMarks.toString());
    }

    @Test
    public void testToArray() {
        SimpleHashtable<String, Integer> examMarks = loadExample();
        String actual = Arrays.toString(examMarks.toArray());
        // not realy good method to test if java dev decides to change algorithm for
        // default String hashCode
        assertEquals("[Ante=2, Ivana=1, Jasna=3, Kristina=4]", actual);
    }

    @Test
    public void testClear() {
        SimpleHashtable<String, Integer> examMarks = loadExample();
        examMarks.clear();
        assertTrue(examMarks.isEmpty());
        assertEquals(0, examMarks.toArray().length);
    }

    private static SimpleHashtable<String, Integer> loadExampleFromHomeWork() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
        // fill data:
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana
        return examMarks;
    }

    @Test
    public void testExample1FromHomeWork() {
        SimpleHashtable<String, Integer> examMarks = loadExampleFromHomeWork();
        String expected = "Ante => 2 Ivana => 5 Jasna => 2 Kristina => 5 ";
        String actual = "";
        for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
            actual += String.format("%s => %d ", pair.getKey(), pair.getValue());
        }
        assertEquals(expected, actual);
    }

    @Test
    public void testExample2FromHomeWork() {
        SimpleHashtable<String, Integer> examMarks = loadExampleFromHomeWork();
        int count = 0;
        for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
            for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
                count++;
            }
        }
        assertEquals(examMarks.size() * examMarks.size(), count);
    }

    @Test
    public void testExample3FromHomeWork() {
        SimpleHashtable<String, Integer> examMarks = loadExampleFromHomeWork();
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        while (iter.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
            if (pair.getKey().equals("Ivana")) {
                iter.remove(); // sam iterator kontrolirano uklanja trenutni element
            }
        }
        assertFalse(examMarks.containsKey("Ivana"));
        assertEquals(3, examMarks.size());
    }

    @Test
    public void testExample4FromHomeWork() {
        SimpleHashtable<String, Integer> examMarks = loadExampleFromHomeWork();
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        assertThrows(IllegalStateException.class, () -> {
            while (iter.hasNext()) {
                SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
                if (pair.getKey().equals("Ivana")) {
                    iter.remove();
                    iter.remove();
                }
            }
        });
    }

    @Test
    public void testExample5FromHomeWork() {
        SimpleHashtable<String, Integer> examMarks = loadExampleFromHomeWork();
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        assertThrows(ConcurrentModificationException.class, () -> {
            while (iter.hasNext()) {
                SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
                if (pair.getKey().equals("Ivana")) {
                    examMarks.remove("Ivana");
                }
            }
        });
    }

    @Test
    public void testExample6FromHomeWork() {
        SimpleHashtable<String, Integer> examMarks = loadExampleFromHomeWork();
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        String expected = "Ante => 2 Ivana => 5 Jasna => 2 Kristina => 5 ";
        String actual = "";
        while (iter.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
            actual += String.format("%s => %d ", pair.getKey(), pair.getValue());
            iter.remove();
        }
        assertEquals(expected, actual);
        assertTrue(examMarks.isEmpty());
    }
}
