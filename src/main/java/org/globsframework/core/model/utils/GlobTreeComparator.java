package org.globsframework.core.model.utils;

import org.globsframework.core.model.Glob;

import java.util.Comparator;

public abstract class GlobTreeComparator implements Comparator<Glob> {

    public int compare(Glob glob1, Glob glob2) {
        if ((glob1 == null) && (glob2 == null)) {
            return 0;
        }
        if (glob1 == null) {
            return -1;
        }
        if (glob2 == null) {
            return 1;
        }

        Glob parent1 = findParent(glob1);
        Glob parent2 = findParent(glob2);

        if ((parent1 != null) && (parent2 != null)) {
            if (parent1.getKey().equals(parent2.getKey())) {
                return doCompareFields(glob1, glob2);
            }
            return doCompareFields(parent1, parent2);
        } else if ((parent1 != null) && (parent2 == null)) {
            if (parent1.getKey().equals(glob2.getKey())) {
                return 1;
            }
            return doCompareFields(parent1, glob2);
        } else if ((parent1 == null) && (parent2 != null)) {
            if (glob1.getKey().equals(parent2.getKey())) {
                return -1;
            }
            return doCompareFields(glob1, parent2);
        }

        return doCompareFields(glob1, glob2);
    }

    protected abstract Glob findParent(Glob glob);

    protected abstract int doCompareFields(Glob glob1, Glob glob2);
}
