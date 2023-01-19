package hr.fer.oprpp1.custom.collections;

public class ObjectStack<T> {

    private ArrayIndexedCollection<T> collection;

    public ObjectStack() {
        collection = new ArrayIndexedCollection<T>();
    }

    /*
     * returns true if stack is empty else returns false
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /*
     * returns number of elements on stack
     */
    public int size() {
        return collection.size();
    }

    /*
     * adds object on top of stack
     */
    public void push(T value) {
        collection.add(value);
    }

    /*
     * returns object on top of stack and removes it
     */
    public T pop() {
        if (isEmpty())
            throw new EmptyStackException();
        T top = collection.get(size() - 1);
        collection.remove(size() - 1);
        return top;
    }

    /*
     * returns reference on object that is on top of stack
     */
    public T peek() {
        T top = pop();
        push(top);
        return top;
    }

    /*
     * removes all elements from the stack
     */
    public void clear() {
        collection.clear();
    }

}
