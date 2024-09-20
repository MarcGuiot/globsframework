package org.globsframework.core.metamodel.utils;

import org.globsframework.core.metamodel.GlobType;

import java.util.Comparator;

public class GlobTypeComparator {
    public static final Comparator<GlobType> INSTANCE = new Comparator<GlobType>() {
        public int compare(GlobType o1, GlobType o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
}
