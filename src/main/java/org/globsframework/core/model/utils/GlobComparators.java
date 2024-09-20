package org.globsframework.core.model.utils;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.Glob;

import java.util.Comparator;

public class GlobComparators {
    public static Comparator<Glob> ascending(Field field) {
        return new GlobFieldComparator(field);
    }

    public static Comparator<Glob> descending(Field field) {
        return new ReverseGlobFieldComparator(field);
    }
}
