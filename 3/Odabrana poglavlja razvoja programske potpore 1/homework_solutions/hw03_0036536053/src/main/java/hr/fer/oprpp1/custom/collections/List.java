package hr.fer.oprpp1.custom.collections;

public interface List<T> extends Collection<T> {
    public abstract T get(int index);
    public abstract void insert(T value, int position);
    public abstract int indexOf(T value);
    public abstract void remove(int index);
}
