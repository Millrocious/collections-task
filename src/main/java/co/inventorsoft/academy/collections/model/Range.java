package co.inventorsoft.academy.collections.model;

import java.util.*;
import java.util.function.Function;

public class Range<T extends Comparable<T>> implements Set<T> {
    private final T start;
    private final T end;
    private final Function<T, T> stepFunction;

    private Range(T start, T end, Function<T, T> stepFunction) {
        this.start = start;
        this.end = end;
        this.stepFunction = stepFunction;
    }

    public static <T extends Comparable<T>> Range<T> of(T start, T end) {
        return new Range<>(start, end, null);
    }

    public static <T extends Comparable<T>> Range<T> of(T start, T end, Function<T, T> stepFunction) {
        return new Range<>(start, end, stepFunction);
    }

    public int size() {
        if (start.compareTo(end) == 0) {
            return 0;
        }

        int size = 0;
        T current = start;
        while (current.compareTo(end) <= 0) {
            size++;
            current = stepFunction != null ? stepFunction.apply(current) : increment(current);
        }
        return size;
    }


    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Object o) {
        if (o instanceof Comparable) {
            T value = (T) o;
            return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
        }
        return false;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private T current = start;

            @Override
            public boolean hasNext() {
                return current.compareTo(end) <= 0;
            }

            @Override
            public T next() {
                T result = current;
                current = stepFunction != null ? stepFunction.apply(current) : increment(current);
                return result;
            }
        };
    }

    public Object[] toArray() {
        List<T> list = new ArrayList<>(this);
        return list.toArray();
    }

    public <T1> T1[] toArray(T1[] a) {
        List<T> list = new ArrayList<>(size());
        list.addAll(this);
        return list.toArray(a);
    }


    public boolean add(T t) {
        throw new UnsupportedOperationException("Adding elements to the Range is not supported.");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Removing elements from the Range is not supported.");
    }

    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Adding a collection to the Range is not supported.");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Retaining elements from the Range is not supported.");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Removing elements from the Range is not supported.");
    }

    public void clear() {
        throw new UnsupportedOperationException("Clearing the Range is not supported.");
    }

    private T increment(T value) {
        if (value instanceof Integer) {
            return (T) Integer.valueOf(((Integer) value) + 1);
        } else if (value instanceof Float) {
            return (T) Float.valueOf(((Float) value) + 0.1f);
        } else if (value instanceof Double) {
            return (T) Double.valueOf(((Double) value) + 0.1);
        } else {
            throw new UnsupportedOperationException("Unsupported type for increment");
        }
    }
}
