package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
    // constructrs
    @Test
    public void testConstructorWithoutArguments() {
        assertTrue((new ArrayIndexedCollection()).isEmpty());
    }

    @Test
    public void testConstructorWithCapacityArgument() {
        assertTrue((new ArrayIndexedCollection(10)).isEmpty());
    }

    @Test
    public void testConstructorWithCapacityArgumentShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
    }

    @Test
    public void testConstructorWithCollectionArgumentShouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
    }

    @Test
    public void testConstructorWithCollectionArgumentShouldThrowIllegalArgumentException() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        // size of the given collection should be used for elements array preallocation,
        // but colection size iz 0
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(collection));
    }

    @Test
    public void testConstructorWithCollectionArgument() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(1);
        assertFalse((new ArrayIndexedCollection(collection)).isEmpty());
    }

    @Test
    public void testConstructorWithCollectionAndCapacityArgumentsShouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 10));
    }

    @Test
    public void testConstructorWithCollectionAndCapacityArgumentsShouldNotThrowIllegalArgumentException() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        assertDoesNotThrow(() -> new ArrayIndexedCollection(collection, 10));
    }

    @Test
    public void testConstructorWithCollectionAndCApacityArguments() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(1);
        assertFalse((new ArrayIndexedCollection(collection, 10)).isEmpty());
    }

    private ArrayIndexedCollection col1;
    private Random random;
    static final Object[] EXPACTED_OBJECTS = new Object[] { 1, 3.14, "string", 'c' };
    // using initialization blocks to set some testing enviroment
    {
        col1 = new ArrayIndexedCollection();
        random = new Random();
        for (Object o : ArrayIndexedCollectionTest.EXPACTED_OBJECTS)
            col1.add(o);
    }

    // void add(Object value);
    @Test
    public void testMethodAddShouldThrowNullPointerException() {
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 13);
        assertThrows(NullPointerException.class, () -> col2.add(null));
    }

    @Test
    public void testMethodAdd() {
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 13);
        int count = random.nextInt(10) + 1;
        for (int i = 0; i < count; i++)
            col2.add(i);
        assertTrue(col2.size() == col1.size() + count);
    }

    // Object get(int index);
    @Test
    public void testMethodGetShouldthrowIndexOutOfBoundsException() {
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 13);
        assertThrows(IndexOutOfBoundsException.class, () -> col2.get(col2.size()));
    }

    @Test
    public void testMethodGet() {
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 13);
        int index = random.nextInt(col2.size());
        assertTrue(col2.get(index).equals(ArrayIndexedCollectionTest.EXPACTED_OBJECTS[index]));
    }

    // void clear();
    @Test
    public void testMethodClear() {
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 13);
        col2.clear();
        assertArrayEquals(new Object[] {}, col2.toArray());
    }

    // void insert(Object value, int position);
    @Test
    public void testInsertShouldNotThrowIndexOutOfBoundsException() {
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 13);
        assertDoesNotThrow(() -> col2.insert("x", col2.size()));
    }

    @Test
    public void testInsertShoulThrowIndexOutOfBoundsException() {
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 13);
        assertThrows(IndexOutOfBoundsException.class, () -> col2.insert("x", col2.size() + 1));
    }

    @Test
    public void testInsert() {
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 13);
        int index = random.nextInt(col2.size() + 1);
        Object value = "x";
        col2.insert(value, index);
        Object[] newExpactedObjects = new Object[ArrayIndexedCollectionTest.EXPACTED_OBJECTS.length + 1];
        int counter = 0;
        while (counter < index)
            newExpactedObjects[counter] = ArrayIndexedCollectionTest.EXPACTED_OBJECTS[counter++];
        newExpactedObjects[counter++] = value;
        while (counter < newExpactedObjects.length) {
            newExpactedObjects[counter] = ArrayIndexedCollectionTest.EXPACTED_OBJECTS[counter - 1];
            counter++;
        }
        assertArrayEquals(newExpactedObjects, col2.toArray());
    }

    // int indexOf(Object value);
    @Test
    public void testIndexOfShouldNotThrowNullPointerException() {
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 13);
        assertTrue(col2.indexOf(null) == -1);
    }

    @Test
    public void testIndexOf() {
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 13);
        int index = random.nextInt(col2.size());
        Object value = ArrayIndexedCollectionTest.EXPACTED_OBJECTS[index];
        assertTrue(col2.indexOf(value) == index);
    }

    // void remove(int index);
    @Test
    public void testMethodRemoveAtIndexShouldThrowIndexOutOfBoundsException() {
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 13);
        assertThrows(IndexOutOfBoundsException.class, () -> col2.remove(col2.size()));
    }

    @Test
    public void testMethodRemoveAtIndex() {
        ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 13);
        int index = random.nextInt(col1.size() - 1);
        col2.remove(index);
        assertTrue(col2.get(index) == col1.get(index + 1));
    }

}
