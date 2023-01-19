package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * clas SimpleHashtable represent slightly better (but still simple)
 * implementation of Map
 * 
 * @author Jure Rajcic
 *
 */

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
    private TableEntry<K, V>[] table;
    private int size;
    private long modificationCount;

    /**
     * creates a new SimpleHashtable with @param capacity whic is next greater or
     * euall potencion of number 2
     * 
     * @param capacity
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int capacity) {
        if (capacity < 1)
            throw new IllegalArgumentException("capacity cant be less than 1");
        int pow = 0;
        capacity--;
        while (capacity != 0) {
            capacity >>= 1; // shits binary operator for 1
            pow++;
        }
        this.table = new TableEntry[(int) Math.pow(2, pow)];
        this.size = 0;
        this.modificationCount = 0;

    }


    /**
     * Creates a new SimpleHashtable with default capacity of 16
     */
    public SimpleHashtable() {
        this(16);
    }

    public static class TableEntry<K, V> {

        private K key;
        private V value;
        private TableEntry<K, V> next;

        /**
         * Creates TableEntry Object from @param key and @param value
         * 
         * @param next points on next object TableEntry, (achiving simple LinkList)
         * @throws NullPointerException if key is null;
         */
        public TableEntry(K key, V value) {
            if (key == null)
                throw new NullPointerException("Key can't be null");
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * mehod overides vale of Table Entry for @param key with @param value
     * if Table entry with @param key was priviously in the table, method @return
     * previous value of now modified TableEntry
     * if table entry with @param key was not in the table, then @return null
     * 
     * @throws NullPointerException if key is null
     */
    public V put(K key, V value) {
        if (key == null)
            throw new NullPointerException("Key can't be null");

        if (this.size * 100 / table.length >= 75)
            capacityX2();
        else
            modificationCount++;
        int index = Math.abs(key.hashCode() % table.length); // ovo iz nekog razloga zna returnat negativan broj  bez Math.abs
        TableEntry<K, V> curr = table[index];
        if (curr == null) {
            table[index] = new TableEntry<>(key, value);
            size++;
            return null;
        }
        while (curr.next != null) {
            if (curr.key.equals(key)) {
                V oldValue = curr.value;
                curr.value = value;
                return oldValue;
            }
            curr = curr.next;
        }
        curr.next = new TableEntry<>(key, value);
        size++;
        
        return null;

    }

    /**
     * @returns value for given @param key in table, if key is not in table
     *          then @return null
     * @throws NullPointerException if key is null
     */
    public V get(Object key) {
        if (key == null)
            throw new NullPointerException("Key can't be null");
        int index = Math.abs(key.hashCode() % table.length);
        TableEntry<K, V> curr = table[index];
        V value = null;
        while (curr != null && value == null) {
            if (curr.key.equals(key))
                value = curr.value;
            curr = curr.next;
        }
        return value;
    }

    /**
     * method returns number of TableEntry in table
     * 
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * method @return true only if TableEntry with @param key exists in table
     */
    public boolean containsKey(Object key) {
        if (key == null)
            return false;
        int index = Math.abs(key.hashCode() % table.length);
        boolean sameKeyFound = false;
        TableEntry<K, V> curr = table[index];
        while (curr != null && !sameKeyFound) {
            sameKeyFound = curr.key.equals(key);
            curr = curr.next;
        }
        return sameKeyFound;
    }

    /**
     * method @return true only if TableEntry with @param value exists in table,
     * value can be null
     */
    public boolean containsValue(Object value) {
        boolean sameValueFound = false;
        for (int i = 0; i < table.length && !sameValueFound; i++) {
            TableEntry<K, V> curr = table[i];
            while (curr != null && !sameValueFound) {
                sameValueFound = (value == null && curr.value == null) || (value != null && value.equals(curr.value));
                curr = curr.next;
            }
        }
        return sameValueFound;
    }

    /**
     * method removes TableEntry with given @param key from table and @return value
     * of removed TableEntry
     * if TableEntry with given @param key is not found in table then returns null
     */

    public V remove(Object key) {
        if (!containsKey(key))
            return null;
        modificationCount++;
        int index = Math.abs(key.hashCode() % table.length);
        TableEntry<K, V> curr = table[index];
        
        V oldValue;
        if (curr.key.equals(key)) {
            oldValue = curr.value;
            table[index] = curr.next;
        } else {
            while (!curr.next.key.equals(key)) {
                curr = curr.next;
            }
            oldValue = curr.next.value;
            curr.next = curr.next.next;
        }
        size--;
        return oldValue;
    }

    /**
     * method @return true only if table does not contain any TableEntry
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * format is [k1=v1, k2=v2, ...] folowing slots in table and in each
     * slot folowing order how TableEntry's were puted
     * 
     * @returns string format of all tableEntrys in table
     */
    public String toString() {
        if (isEmpty()) return "";
        String s = "";
        for (int i = 0; i < table.length; i++) {
            TableEntry<K, V> curr = table[i];
            while (curr != null) {
                s += curr.toString() + ", ";
                curr = curr.next;
            }
        }
        return "[" + s.substring(0, s.length() - 2) + "]";
    }

    /**
     * method creates and @return array of TableEntry in given table,
     * array order is folowing slots in table and in each slots folows
     * order how TableEntry's were puted
     */
    @SuppressWarnings("unchecked")
    public TableEntry<K, V>[] toArray() {
        TableEntry<K, V>[] arr = new TableEntry[size()];
        int index = 0;
        for (int i = 0; i < table.length; i++) {
            TableEntry<K, V> curr = table[i];
            while (curr != null) {
                arr[index++] = curr;
                curr = curr.next;
            }
        }
        return arr;
    }

    /**
     * method alocates new table with capacity 2 times bigger and copys elements
     * into new slots
     */
    @SuppressWarnings("unchecked")
    private void capacityX2() {
        modificationCount++;
        TableEntry<K, V>[] newTable = new TableEntry[table.length * 2];
        for (int i = 0; i < table.length; i++) {
            // iterate over old slots
            TableEntry<K, V> curr = table[i];
            // iterate inside old slot
            while (curr != null) {
                TableEntry<K, V> next = curr.next; // store next node in slot
                curr.next = null; // remove refrence from current TableEntry

                int newIndex = Math.abs(curr.key.hashCode() % newTable.length);
                TableEntry<K, V> newCurr = newTable[newIndex];
                while (newCurr != null && newCurr.next != null)
                    newCurr = newCurr.next;
                if (newCurr == null)
                    newTable[newIndex] = curr;
                else
                    newCurr.next = curr;
                curr = next; // curr next TableEntry in old slot
            }
        }
        this.table = newTable;
    }

    /**
     * method removes all TableEntrys from the table
     */
    public void clear() {
        for (int i = 0; i < table.length; i++)
            table[i] = null;
        size = 0;
    }

    /**
     * @return Iterator implementation for given table
     */
    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl(this);
    }

    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

        private TableEntry<K, V>[] table;
        private int index;
        private int count;
        private TableEntry<K, V> curr;
        private TableEntry<K, V> lastRead;
        private boolean isAllowedToCAllRemove;
        private long modificationCount;

        private IteratorImpl(SimpleHashtable<K, V> sh) {
            this.table = sh.table;
            this.index = -1;
            this.count = 0;
            this.curr = null;
            this.lastRead = null;
            this.isAllowedToCAllRemove = false;
            this.modificationCount = sh.modificationCount;
        }

        /**
         * @return true if there are more elements in table
         * @throws ConcurrentModificationException if user changed table
         */
        @Override
        public boolean hasNext() {
            if (modificationCount != SimpleHashtable.this.modificationCount)
                throw new ConcurrentModificationException("Table is change while iterating");
            return count < SimpleHashtable.this.size;
        }

        /**
         * @return next element in table
         * @throws NoSuchElementException          if all elements in colections are
         *                                         read
         * @throws ConcurrentModificationException if user changed table
         */
        @Override
        public TableEntry<K, V> next() {
            if (!hasNext()) // this cheks modificatio count for us
                throw new NoSuchElementException("Table does not contain any other element");
            isAllowedToCAllRemove = true;
            count++; 
            
            if (curr == null) {
                index++;
                while (table[index] == null)
                    index++;
                curr = table[index];
            } 
            lastRead = curr;
            curr = curr.next;
            return lastRead;
        }

        /**
         * removes last red element from table
         * 
         * @throws ConcurrentModificationException if user changed table
         * @throws IllegalStateException           if user calls remove 2 times before
         *                                         calling method next()
         */
        @Override
        public void remove() {
            if (modificationCount != SimpleHashtable.this.modificationCount)
                throw new ConcurrentModificationException("Table is change while iterating");
            if (!isAllowedToCAllRemove)
                throw new IllegalStateException("method remove caled on ALREADY removed TableEntry object");
            modificationCount++;
            isAllowedToCAllRemove = false;
            SimpleHashtable.this.remove(lastRead.key);
            count--;
        }
    }

    /**
     * method used only for junit tests to chek if table capacity is 2^x
     * @return table length
     */
    public int getTableCapacity() {
        return table.length;
    }



}
