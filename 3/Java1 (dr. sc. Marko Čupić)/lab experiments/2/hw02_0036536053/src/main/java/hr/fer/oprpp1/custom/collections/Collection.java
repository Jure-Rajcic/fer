package hr.fer.oprpp1.custom.collections;

/**
 * class [Collection] represents Colection in abstract way with general methods
 * each Collection object should have
 * 
 * @author Jure Rajcic
 */

public interface Collection {

    

    /**
     * @return is collection empty
     */
    abstract public boolean isEmpty();

    /**
     * @return number of objects in collection
     */
    abstract public int size();

    /**
     * adds @param value to collection
     */
    abstract public void add(Object value);

    /**
     * @return does collection contains @param value
     */
    abstract public boolean contains(Object value);

    /**
     * @returns true only if the collection contains given value as determined by
     * equals method and removes one occurrence of it (in this class it is not
     * specified which one).
     */
    abstract public boolean remove(Object value);


    /**
     * Allocates new array with size equals to the size of this collections, fills
     * it with collection content and @return the array.
     * @throws UnsupportedOperationException if children which are extending Collection class don't @Override this method
     */
    abstract public Object[] toArray();

    /**
     * Method calls @param processor.process(Object) for each element of this collection. 
     * The order in which elements will be sent is undefined in this class.
     */
    default public void forEach(Processor processor) {
        ElementsGetter eg = this.createElementsGetter();
        eg.processRemaining(processor);
    }

    /**
     * Method adds into the current collection all elements from the @param other collection. 
     * This @param other collection remains unchanged.
     */
    default public void addAll(Collection other) {
        class LocalProces implements Processor {
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
    abstract public void clear() ;

    /**
     * creates and @return new reference on ElementGetter object for given collection
     */
    abstract public ElementsGetter createElementsGetter();

    /**
     * adds all elements from @param col which pass @param tester.test metod
     */
    abstract public void addAllSatisfying(Collection col, Tester tester);

}
