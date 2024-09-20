package org.globsframework.core.model.utils;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.Glob;

import java.util.Comparator;

public class ReverseGlobFieldComparator implements Comparator<Glob> {

    private GlobFieldComparator inner;

    public ReverseGlobFieldComparator(Field field) {
        inner = new GlobFieldComparator(field);
    }

    public int compare(Glob o1, Glob o2) {
        return inner.compare(o1, o2) * -1;
    }
}
