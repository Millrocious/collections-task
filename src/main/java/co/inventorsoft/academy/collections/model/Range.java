package co.inventorsoft.academy.collections.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class Range<T extends Comparable<T>> implements Set<T> {
    private final T start;
    private final T end;
    private final Function<T, T> stepFunction;

    private Range(T start, T end, Function<T, T> stepFunction) {
        this.start = start;
        this.end = end;
        this.stepFunction = stepFunction;
    }

    public static Range<Integer> of(Integer start, Integer end) {
        return of(start, end, (Integer current) -> current + 1);
    }

    public static Range<Float> of(Float start, Float end) {
        return of(start, end, (Float current) -> current + 0.1f);
    }

    public static Range<Double> of(Double start, Double end) {
        return of(start, end, (Double current) -> current + 0.1);
    }

    public static <T extends Comparable<T>> Range<T> of(T start, T end, Function<T, T> stepFunction) {
        if (start == null || end == null || stepFunction == null) {
            throw new IllegalArgumentException("start, end, and stepFunction must not be null");
        }
        return new Range<>(start, end, stepFunction);
    }


    public int size() {
        if (start.compareTo(end) == 0) {
            return 0;
        }

        int size = 1;
        T current = start;

        while (current.compareTo(end) < 0) {
            current = getNextElement(current);
            size++;
        }

        return size;
    }

    public Iterator<T> iterator() {
        return Stream
                .iterate(start, elem -> elem.compareTo(end) <= 0, this::getNextElement)
                .iterator();
    }

    private T getNextElement(T current) {
        return stepFunction.apply(current);
    }


    public boolean isEmpty() {
        return start.compareTo(end) == 0;
    }

    public boolean contains(Object o) {
        if (o instanceof Comparable) {
            T value = (T) o;
            return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
        }
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
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

}
