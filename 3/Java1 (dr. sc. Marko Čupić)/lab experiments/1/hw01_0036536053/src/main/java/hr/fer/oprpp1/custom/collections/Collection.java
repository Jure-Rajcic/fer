package hr.fer.oprpp1.custom.collections;

/**
 * class [Collection] represents Colection in abstract way with general methods
 * each Collection object should have
 * 
 * @author Jure Rajcic
 */

public class Collection {

    protected Collection() {
        super();
    }

    /**
     * @return is collection empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * @return number of objects in collection
     */
    public int size() {
        return 0;
    }

    /**
     * adds @param value to collection
     */
    public void add(Object value) {
    }

    /**
     * @return does collection contains @param value
     */
    public boolean contains(Object value) {
        return false;
    }

    /**
     * @returns true only if the collection contains given value as determined by
     * equals method and removes one occurrence of it (in this class it is not
     * specified which one).
     */
    public boolean remove(Object value) {
        return false;
    }


    /**
     * Allocates new array with size equals to the size of this collections, fills
     * it with collection content and @return the array.
     * @throws UnsupportedOperationException if children which are extending Collection class don't @Override this method
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method calls @param processor.process(Object) for each element of this collection. 
     * The order in which elements will be sent is undefined in this class.
     */
    public void forEach(Processor processor) {

    }

    /**
     * Method adds into the current collection all elements from the @param other collection. 
     * This @param other collection remains unchanged.
     */
    public void addAll(Collection other) {
        class LocalProces extends Processor {
            @Override
            public void process(Object value) {
                Collection.this.add(value); // https://programming.guide/java/class-this.html
            }
        }
        other.forEach(new LocalProces());
    }

    /**
     * Removes all elements from this collection.
     */
    public void clear() {
    }

}
