package hr.fer.oprpp1.custom.collections;

public interface List extends Collection {
    public abstract Object get(int index);
    public abstract void insert(Object value, int position);
    public abstract int indexOf(Object value);
    public abstract void remove(int index);
}
