package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

public class DictionaryTest {

        private static Dictionary<String, Integer> loadExample(){
            Dictionary<String, Integer> dictionary = new Dictionary<>();
	        dictionary.put("One", 1);
	        dictionary.put("Two", 2);
            dictionary.put("Three", 3);
            dictionary.put("Four", 4);
            return dictionary;
        }

        @Test
        public void testIsEmpty() {
            Dictionary<String, Integer> d = new Dictionary<>();
            assertTrue(d.isEmpty());
        }

        @Test
        public void testSize() {
            Dictionary<String, Integer> d = loadExample();
            assertEquals(4, d.size());
        }

        @Test
        public void testClear() {
            Dictionary<String, Integer> d = loadExample();
            d.clear();
            assertTrue( d.isEmpty());
        }

        @Test
        public void testPutThrowsNullPointerException() {
            Dictionary<String, Integer> d = loadExample();
            assertThrows(NullPointerException.class, () -> d.put(null, null));
        }

        @Test
        public void testPutReturnsNullValueAndUpdaes() {
            Dictionary<String, Integer> d = loadExample();
            d.put("Five", null);
            assertNull(d.put("Five", 5));
            assertEquals(5, d.get("Five"));
        }

        @Test
        public void testPutRandom() {
            Dictionary<String, Integer> d = loadExample();
            int random = new Random().nextInt(d.size());
            d.put("One", random);
            assertEquals(random, d.get("One"));
        }

        @Test
        public void testAddPair() {
            Dictionary<String, Integer> d = loadExample();
            d.put("Five", 5);
            assertEquals(5, d.get("Five"));
        }


        @Test
	    public void testGetThrowsNullPointerException(){
            Dictionary<String, Integer> dictionary = loadExample();
            assertThrows(NullPointerException.class, () -> dictionary.get(null));
	    }

        @Test void testGetReturnNullValueForKeyThatIsNotInDictionary() {
            Dictionary<String, Integer> dictionary = loadExample();
            assertNull(dictionary.get("Five"));
        }

        @Test void testGetReturnNullValueForKeyInDictionary() {
            Dictionary<String, Integer> dictionary = loadExample();
            dictionary.put("Five", null);
            assertNull(dictionary.get("Five"));
        }

	    @Test
	    public void testRemoveThrowsNullPointerException(){
            Dictionary<String, Integer> dictionary = loadExample();
            assertThrows(NullPointerException.class, () ->  dictionary.remove(null));
	    }

        @Test
	    public void testRemovePairThatIsNotInDictionary(){
            Dictionary<String, Integer> dictionary = loadExample();
            assertNull(dictionary.remove("Five"));
	    }

        @Test
        public void testRemoveEntireDictionaryWithRemoveMethod() {
            Dictionary<String, Integer> dictionary = loadExample();
            assertEquals(1, dictionary.remove("One"));
            assertEquals(2, dictionary.remove("Two"));
            assertEquals(3, dictionary.remove("Three"));
            assertEquals(4, dictionary.remove("Four"));
            assertNull(dictionary.remove("One"));
        }

	    

}
