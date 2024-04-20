package hr.fer.oprpp1.custom.collections;

/**
 * class [ArrayIndexedCollection] is colection interpreted as array
 * 
 * @author Jure Rajcic
 */

public class ArrayIndexedCollection extends Collection {

    private int size; // number of elements actually stored in elements array
    private Object[] elements; // an array of object references which length determines its current capacity

    public ArrayIndexedCollection() {
        this(16); /* default initial capacity is set to 16 */
    }
    /**
     * @param initialCapacity initial capacity of ArrayIndexedCollection
     * @throws IllegalArgumentException if @param collection is empty
	 */
    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException("initial capacity can't be less than 1");
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * @param collection collection which elemets we copy and store as ArrayIndexedCollection
     * @param initialCapacity initial capacity of ArrayIndexedCollection
	 * @throws NullPointerException if @param collection is null
     * @throws IllegalArgumentException if @param collection is empty
     */
    public ArrayIndexedCollection(Collection collection, int initialCapacity) {
        this(initialCapacity > collection.size() ? initialCapacity : collection.size());
        /*
         * if colection is null this would throw an NullPointerException, we
         * cant ask right away if colection is null because first we need to cal
         * constructor
         */
        addAll(collection);
    }
/**
     * @param collection collection which elemets we copy and store as ArrayIndexedCollection
	 * @throws NullPointerException if @param collection is null
     * @throws IllegalArgumentException if @param collection is empty
     */
    public ArrayIndexedCollection(/* @NotNull */ Collection collection) {
        this(collection, collection.size());
    }

    /**
     * returns number of elements in collection
     * O(1)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @param value value to store in end of list
     * if colection is full then copyes current data in new array with double size
     * @throws NullPointerException if @param value is null
     * O(1)
     */
    @Override
    public void add(Object value) {
        if (value == null)
            throw new NullPointerException("value must not be null");
        if (size == elements.length)
            elements = copyArray(elements, 0, size * 2 - 1);
        elements[size++] = value;
    }

    /**
     * returns does collection contains given @param value
     * O(size)
     */
    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    /**
     * @param value object that we are removing from collection
     * returns true only if @param value is sucesfuly found and removed in collection 
     * O(size)
     */
    @Override
    public boolean remove(Object value) {
        int index = indexOf(value);
        if (index == -1) return false;
        remove(index);
        return true;
    }

    /**
     * @param index index on witch we delite object
     * after deliting Object at given index all remaining elements with index greater then @param index are shifted for 1 position back
     * @throws IndexOutOfBoundsException if @param index is smaller then 0 or greater or equal to size of given collection
     * O(size)
     */
    public void remove(int index) {

        if (!(index >= 0 && index < size))
            throw new IndexOutOfBoundsException();
        for (int i = index; i < size - 1; i++)
            elements[i] = elements[i + 1];
        elements[--size] = null;
    }

    /**
     * returns array representation of this collection, array length is same as collection size
     * O(size)
     */
    @Override
    public Object[] toArray() {
        return copyArray(elements, 0, size - 1);
    }

    /**
     * @param processor has method process that will call method add(Object value) on every element of this collection
     * O(size)
     */
    @Override
    public void forEach(Processor processor) {
        int startingSize = size;
        for (int i = 0; i < startingSize; i++)
            processor.process(elements[i]);
    }

    /**
     * clears collection
     * O(size)
     */
    @Override
    public void clear() {
        clearFromIndex(0);
    }

    /**
     *  @return Object on @param index
     *  @throws IndexOutOfBoundsException if index less then 0 or greater or equal to collection size
     *  O(1)
     */
    public Object get(int index) {
        if (!(index >= 0 && index < size))
            throw new IndexOutOfBoundsException();
        return elements[index];
    }

    /**
     * all values from @param position are shifted for 1 to front 
     * then inserts @param value in @param position
     * @throws NullPointerException if @param value is null
     * @throws IndexOutOfBoundsException if @param position is less then 0 or greater of size
     * O(size - 1 - position)
     */
    void insert(Object value, int position) {
        if(value == null) throw new NullPointerException();
        if (!(position >= 0 && position <= size))
            throw new IndexOutOfBoundsException();
        int right = size - 1;
        add(elements[right]);
        while (position < right) {
            elements[right] = elements[right-1];
            right--;
        }
        elements[position] = value;
    }

    /**
     * @return index of @param value if collection contains @param value
     * O(size)
     */
    public int indexOf(Object value) {
        if (value == null)
            return -1;
        for (int i = 0; i < size; i++)
            if (value.equals(elements[i]))
                return i;
        return -1;
    }

    // since clients won't get acess to this I see no reason to write JavaDoc for this.
    // next methods are mainly for optimization purposes and only visible in this file.

    /**
     * @param array is array which part we want to copy from index @param start to index @param end both included
     * O(end - start)
     */
    private Object[] copyArray(Object[] array, int start, int end) {
        Object[] ret = new Object[end - start + 1];
        int index = 0;
        while (start <= end && start < size) {
            ret[index++] = array[start++];
        }
        return ret;
    }

    /**
     *  clears collection elements from @param index
     */
    private void clearFromIndex(int index) {
        while (index < size)
            remove(size - 1);
    }

}