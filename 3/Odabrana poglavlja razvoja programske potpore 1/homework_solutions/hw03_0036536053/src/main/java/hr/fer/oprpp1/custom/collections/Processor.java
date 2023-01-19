package hr.fer.oprpp1.custom.collections;

public interface Processor<T> {
    /**
     * Processor class is a model of an object capable of performing some 
     * operation on the passed @param value using process method
     */
    abstract public void process(T value);
}
