package hr.fer.oprpp1.custom.collections;

public class LinkedListIndexedCollection extends Collection {
    private static class ListNode {
        private ListNode prev;
        private ListNode next;
        private Object val;
    }

    private int size; // number of elements actually stored in elements array
    private ListNode first; // first element of linked list
    private ListNode last; // last element of linked list

    
    public LinkedListIndexedCollection() {
        this.size = 0;
        this.first = null;
        this.last = null;
    }

    /**
     * @param collection collection which elemets we copy and store as ArrayIndexedCollection
     */
    public LinkedListIndexedCollection(Collection collection) {
        addAll(collection);
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
     * O(list size)
     */
    @Override
    public void add(Object value) {
        if (value == null)
            throw new NullPointerException("value must not be null");
        ListNode current = new ListNode();
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
        if (index == -1)
            return false;
        remove(index);
        return true;
    }

    /**
     * @param index index on witch we delite object
     * after deliting Object at given index all remaining elements with index greater then @param index are shifted for 1 position back
     * @throws IndexOutOfBoundsException if @param index is smaller then 0 or greater or equal to size of given collection
     * O(index)
     */
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
            ListNode curr = nodeAtIndex(index);
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
        }
        size--;
    }

    /**
     * returns array representation of this collection, array length is same as collection size
     * O(size)
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        ListNode curr = first;
        for (int i = 0; i < size; i++, curr = curr.next)
            array[i] = curr.val;
        return array;
    }

    /**
     * @param processor has method process that will call method add(Object value) on every element of this collection
     * O(size)
     */
    @Override
    public void forEach(Processor processor) {
        ListNode curr = first;
        while (curr != null) {
            processor.process(curr.val);
            curr = curr.next;
        }
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
    }

    /**
     *  @return Object on @param index
     *  @throws IndexOutOfBoundsException if index less then 0 or greater or equal to collection size
     *  O(index)
     */
    public Object get(int index) {
        return nodeAtIndex(index).val;
    }

    /**
     * all values from @param position are shifted for 1 to front 
     * then inserts @param value in @param position
     * @throws NullPointerException if @param value is null
     * @throws IndexOutOfBoundsException if @param position is less then 0 or greater of size
     * O(position)
     */
    public void insert(Object value, int position) {
        if (value == null)
            throw new NullPointerException();
            if (!(position >= 0 && position <= size))
            throw new IndexOutOfBoundsException();
        // this covers when position is == size
        if (position == size) {
            add(value);
            return;
        } else if (position == 0) {
            ListNode newNode = new ListNode();
            newNode.val = value;
            newNode.next = first;
            first.prev = newNode;
            first = first.prev;
            size++;
        } else {
            // othervise method nodeAtIndex will check IndexOutOfBoundsException if invalid
            // postition
            ListNode newNode = new ListNode();
            newNode.val = value;
            ListNode curr = nodeAtIndex(position);
            newNode.prev = curr.prev;
            newNode.prev.next = newNode;
            newNode.next = curr;
            newNode.next.prev = newNode;
            size++;
        }

    }

    /**
     * @return index of @param value if collection contains @param value
     * O(size)
     */
    public int indexOf(Object value) {
        // since storage of null references is not allowed.
        // and null is valid argument.
        if (value == null)
            return -1;
        ListNode curr = first;
        for (int i = 0; curr != null; curr = curr.next, i++)
            if (curr.val.equals(value))
                return i;
        return -1;
    }

    // since clients won't get acess to this I see no reason to write JavaDoc for this.
    // next methods are mainly for optimization purposes and only visible in this file.
    
    /**
     * @return ListNode object on @param index in this collection
     * O(index)
     */
    private ListNode nodeAtIndex(int index) {
        if (!(index >= 0 && index < size))
            throw new IndexOutOfBoundsException();
        ListNode curr;
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

}
