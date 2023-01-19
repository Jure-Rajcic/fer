package hr.fer.oprpp1.custom.collections;

/**
 * class Dictionary rapresent simple verzion of Map<K, V>
 * 
 * @author Jure Rajcic
 *
 * @param <K> key
 * @param <V> value
 */
public class Dictionary<K, V> {

    private ArrayIndexedCollection<Pair<K, V>> collection;

    /**
     * creates Dictionary object thaht uses ArrayIndexedCollection as storage for
     * key and value pairs
     */
    public Dictionary() {
        this.collection = new ArrayIndexedCollection<>();
    }

    private static class Pair<K, V> {
        private K key;
        private V value;

        /**
         * creates pair of @param key and @param value
         */
        public Pair(K key, V value) {
            if (key == null)
                throw new NullPointerException("Key can't be null");
            this.key = key;
            this.value = value;
        }

    }

    /**
     * @returns true only if colection doesn't contain any element(pair)
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * @returns number of elements in dictionary
     */
    public int size() {
        return this.collection.size();
    }

    /**
     * method removes all elements (pairs) from dictionary
     */
    public void clear() {
        this.collection.clear();
    }

    /**
     * method Overides @param value for given @param key
     * 
     * @return old value for given key or null if key wasnt found in dictionary
     * @throws NullPointerException if key is null;
     * 
     */
    public V put(K key, V value) {
        Pair<K, V> pair = getPairForKey(key);
        if (pair == null) {
            collection.add(new Pair<K, V>(key, value));
            return null;
        }
        V oldValue = pair.value;
        pair.value = value;
        return oldValue;
    }

    /**
     * @returns value for given @param key, if key bot in collection returns null
     * @throws NullPointerException if key is null;
     * 
     */
    public V get(Object key) {
        Pair<K, V> pair = getPairForKey(key);
        if (pair == null)
            return null;
        return pair.value;
    }

    

    /**
     * method removes pair with given @param key from colection,and returns pair
     * value if present
     * if pair with given @param key wasnt in dictionary return null;
     * 
     * @throws NullPointerException if key is null;
     */
    public V remove(K key) {
        Pair<K, V> pair = getPairForKey(key);
        if (pair == null)
            return null;
        V value = pair.value;
        collection.remove(pair);
        return value;
    }


    /**
     * private method used for optimization purposes
     * @returns refrence on pair for given @param key, if key is not present then returns null
     * @throws NullPointerException if key is null;
     */

    private Pair<K, V> getPairForKey(Object key) {
        if (key == null)
            throw new NullPointerException("Key cannot be null");
        Pair<K, V> result = null;
        for (int i = 0; i < this.collection.size() && result == null; i++) {
            Pair<K, V> pair = collection.get(i);
            if (pair.key.equals(key)) {
                result = pair;
            }
        }
        return result;
    }
}
