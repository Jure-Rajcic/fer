package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class LinkedListIndexedCollection<T> implements List<T> {
    private static class ListNode<T> {
        private ListNode<T> prev;
        private ListNode<T> next;
        private T val;
    }

    private int size; // number of elements actually stored in elements array
    private ListNode<T> first; // first element of linked list
    private ListNode<T> last; // last element of linked list
    private long modificationCount;

    public LinkedListIndexedCollection() {
        this.modificationCount = 0;
        this.size = 0;
        this.first = null;
        this.last = null;
    }

    /**
     * @param collection collection which elemets we copy and store as
     *                   ArrayIndexedCollection
     */
    public LinkedListIndexedCollection(Collection<? extends T> collection) {
        addAll(collection);
    }

    /**
     * @returns true only if colection does not contain any elements
     *          O(1)
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
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
     * @throws NullPointerException if @param value is null
     *                              O(list size)
     */
    @Override
    public void add(T value) {
        if (value == null)
            throw new NullPointerException("value must not be null");
        ListNode<T> current = new ListNode<>();
        current.val = value;
        if (first == null)
            first = current;
        if (last == null)
            last = first;
        else {
            last.next = current;
            current.prev = last;
            last = current;
        }
        size++;
        modificationCount++;
    }

    /**
     * returns does collection contains given @param value
     * O(size)
     */
    @Override
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    /**
     * @param value object that we are removing from collection
     *              returns true only if @param value is sucesfuly found and removed
     *              in collection
     *              O(size)
     */
    @Override
    public boolean remove(T value) {
        int index = indexOf(value);
        if (index == -1)
            return false;
        remove(index);
        return true;
    }

    /**
     * @param index index on witch we delite object
     *              after deliting Object at given index all remaining elements with
     *              index greater then @param index are shifted for 1 position back
     * @throws IndexOutOfBoundsException if @param index is smaller then 0 or
     *                                   greater or equal to size of given
     *                                   collection
     *                                   O(index)
     */
    @Override
    public void remove(int index) {
        if (!(index >= 0 && index < size))
            throw new IndexOutOfBoundsException();
        if (index == 0) {
            first = first.next;
            first.prev = null;
        } else if (index == size - 1) {
            last = last.prev;
            last.next = null;
        } else {
            ListNode<T> curr = nodeAtIndex(index);
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
        }
        size--;
        modificationCount++;
    }

    /**
     * returns array representation of this collection, array length is same as
     * collection size
     * O(size)
     */
    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        Object[] array = new Object[size];
        ListNode<T> curr = first;
        for (int i = 0; i < size; i++, curr = curr.next)
            array[i] = curr.val;
        return (T[])array;
    }

    

    /**
     * clears collection
     * O(1)
     */
    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
        modificationCount++;
    }

    /**
     * @return Object on @param index
     * @throws IndexOutOfBoundsException if index less then 0 or greater or equal to
     *                                   collection size
     *                                   O(index)
     */
    @Override
    public T get(int index) {
        return nodeAtIndex(index).val;
    }

    /**
     * all values from @param position are shifted for 1 to front
     * then inserts @param value in @param position
     * 
     * @throws NullPointerException      if @param value is null
     * @throws IndexOutOfBoundsException if @param position is less then 0 or
     *                                   greater of size
     *                                   O(position)
     */
    @Override
    public void insert(T value, int position) {
        if (value == null)
            throw new NullPointerException();
        if (!(position >= 0 && position <= size))
            throw new IndexOutOfBoundsException();
        // this covers when position is == size
        if (position == size) {
            add(value);
            return;
        }
        if (position == 0) {
            ListNode<T> newNode = new ListNode<>();
            newNode.val = value;
            newNode.next = first;
            first.prev = newNode;
            first = first.prev;
        } else {
            // othervise method nodeAtIndex will check IndexOutOfBoundsException if invalid
            // postition
            ListNode<T> newNode = new ListNode<>();
            newNode.val = value;
            ListNode<T> curr = nodeAtIndex(position);
            newNode.prev = curr.prev;
            newNode.prev.next = newNode;
            newNode.next = curr;
            newNode.next.prev = newNode;
        }
        size++;
        modificationCount++;
    }

    /**
     * @return index of @param value if collection contains @param value
     *         O(size)
     */
    @Override
    public int indexOf(Object value) {
        // since storage of null references is not allowed.
        // and null is valid argument.
        if (value == null)
            return -1;
        ListNode<T> curr = first;
        for (int i = 0; curr != null; curr = curr.next, i++)
            if (curr.val.equals(value))
                return i;
        return -1;
    }

    // since clients won't get acess to this I see no reason to write JavaDoc for
    // this.
    // next methods are mainly for optimization purposes and only visible in this
    // file.

    /**
     * @return ListNode object on @param index in this collection
     *         O(index)
     */
    private ListNode<T> nodeAtIndex(int index) {
        if (!(index >= 0 && index < size))
            throw new IndexOutOfBoundsException();
        ListNode<T> curr;
        if (index >= size / 2) {
            int end = size - 1;
            curr = last;
            while (end > index) {
                curr = curr.prev;
                end--;
            }
        } else {
            int start = 0;
            curr = first;
            while (start < index) {
                curr = curr.next;
                start++;
            }
        }
        return curr;
    }

    private static class LinkedListIndexedElementGetter<T> implements ElementsGetter <T>{
        private LinkedListIndexedCollection<T> collection;
        private ListNode<T> current;
        private long savedModificationCount;

        private LinkedListIndexedElementGetter(LinkedListIndexedCollection<T> collection) {
            this.collection = collection;
            this.current = collection.first;
            this.savedModificationCount = collection.modificationCount;
        }

        /**
         * @return true if user didn't't read all of collection elements
         * @throws ConcurrentModificationException if collection has been modified after createElementsGetter method
         */
        public boolean hasNextElement() {
            if (savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException();
            return current != null;
        }

        /**
         * reads and @return referenc on next Object in collection
         * 
         * @throws NoSuchElementException if all elements in colections are read
         */
        public T getNextElement() {
            if (!hasNextElement())
                throw new NoSuchElementException();
            T value = this.current.val;
            this.current = this.current.next;
            return value;
        }
    }

    /**
     * creates and @return new reference on ElementGetter object for given
     * collection
     */
    @Override
    public ElementsGetter<T> createElementsGetter() {
        return new LinkedListIndexedElementGetter<T>(this);
    }
    
}
