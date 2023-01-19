package hr.fer.oprpp1.custom.collections;

public interface ElementsGetter<T> {
    /**
     * @return true if user didn't't read all of collection elements
     */
    abstract public boolean hasNextElement();

    /**
     * reads and @return referenc on next Object in collection
     */
    abstract public T getNextElement();

    /**
     * executes process metohod on @param p on all remaining elements in collection
     */
    default void processRemaining(Processor<? super T> p) {
        while (hasNextElement()) {
            T element = getNextElement();
            p.process(element);
        }
    }
}
