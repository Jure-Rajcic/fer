package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
    // constructrs
    @Test
    public void testConstructorWithoutArguments() {
        assertTrue((new LinkedListIndexedCollection()).isEmpty());
    }

    @Test
    public void testConstructorWithColectionArgument() {
        // link list can be empty
        assertTrue((new ArrayIndexedCollection(10)).isEmpty());
    }

    private ArrayIndexedCollection col1;
    private Random random;
    static final Object[] EXPACTED_OBJECTS = new Object[] { 1, 3.14, "string", 'c' };
    // using initialization blocks to set some testing enviroment
    {
        col1 = new ArrayIndexedCollection();
        random = new Random();
        for (Object o : LinkedListIndexedCollectionTest.EXPACTED_OBJECTS)
            col1.add(o);
    }

    // void add(Object value); 
    @Test
    public void testMethodAddShouldThrowNullPointerException() {
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
        assertThrows(NullPointerException.class, () -> col2.add(null));
    }

    @Test
    public void testMethodAdd() {
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
        int count = random.nextInt(10) + 1;
        for (int i = 0; i < count; i++)
            col2.add(i);
        assertTrue(col2.size() == col1.size() + count);
    }


    // Object get(int index);
    @Test
    public void testMethodGetShouldthrowIndexOutOfBoundsException() {
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
        assertThrows(IndexOutOfBoundsException.class, () -> col2.get(col2.size()));
    }

    @Test
    public void testMethodGet() {
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
        int index = random.nextInt(col2.size());
        assertTrue(col2.get(index).equals(LinkedListIndexedCollectionTest.EXPACTED_OBJECTS[index]));
    }

    // void clear();
    @Test
    public void testMethodClear() {
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
        col2.clear();
        assertArrayEquals(new Object[] {}, col2.toArray());
    }

    // void insert(Object value, int position);
    @Test
    public void testInsertShouldNotThrowIndexOutOfBoundsException() {
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
        assertDoesNotThrow(() -> col2.insert("x", col2.size()));
    }

    @Test
    public void testInsertShoulThrowIndexOutOfBoundsException() {
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
        assertThrows(IndexOutOfBoundsException.class, () -> col2.insert("x", col2.size() + 1));
    }

    @Test
    public void testInsert() {
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
        int index = random.nextInt(col2.size() + 1);
        Object value = "x";
        col2.insert(value, index);
        Object[] newExpactedObjects = new Object[LinkedListIndexedCollectionTest.EXPACTED_OBJECTS.length + 1];
        int counter = 0;
        while (counter < index)
            newExpactedObjects[counter] = LinkedListIndexedCollectionTest.EXPACTED_OBJECTS[counter++];
        newExpactedObjects[counter++] = value;
        while (counter < newExpactedObjects.length) {
            newExpactedObjects[counter] = LinkedListIndexedCollectionTest.EXPACTED_OBJECTS[counter - 1];
            counter++;
        }
        assertArrayEquals(newExpactedObjects, col2.toArray());
    }

    // int indexOf(Object value);
    @Test
    public void testIndexOfShouldNotThrowNullPointerException() {
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
        assertTrue(col2.indexOf(null) == -1);
    }

    @Test
    public void testIndexOf() {
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
        int index = random.nextInt(col2.size());
        Object value = LinkedListIndexedCollectionTest.EXPACTED_OBJECTS[index];
        assertTrue(col2.indexOf(value) == index);
    }

    // void remove(int index);
    @Test
    public void testMethodRemoveAtIndexShouldThrowIndexOutOfBoundsException() {
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
        assertThrows(IndexOutOfBoundsException.class, () -> col2.remove(col2.size()));
    }

    @Test
    public void testMethodRemoveAtIndex() {
        LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);
        int index = random.nextInt(col1.size() - 1);
        col2.remove(index);
        assertTrue(col2.get(index) == col1.get(index + 1));
    }
}
