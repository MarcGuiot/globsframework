package org.globsframework.utils.comparators;

import java.util.Comparator;

public class CompositeComparator<T> implements Comparator<T> {
    Comparator[] comparators;

    public CompositeComparator(Comparator<T>... comparators) {
        this.comparators = comparators;
    }

    public int compare(T t1, T t2) {
        for (Comparator comparator : comparators) {
            int diff = comparator.compare(t1, t2);
            if (diff != 0) {
                return diff;
            }
        }
        return 0;
    }
}
