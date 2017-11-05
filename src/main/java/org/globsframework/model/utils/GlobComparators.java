package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.util.Comparator;

public class GlobComparators {
    public static Comparator<Glob> ascending(Field field) {
        return new GlobFieldComparator(field);
    }

    public static Comparator<Glob> descending(Field field) {
        return new ReverseGlobFieldComparator(field);
    }
}
