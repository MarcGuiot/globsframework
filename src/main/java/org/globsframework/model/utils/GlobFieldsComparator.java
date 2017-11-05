package org.globsframework.model.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;
import org.globsframework.utils.Utils;

import java.util.Arrays;
import java.util.Comparator;

public class GlobFieldsComparator implements Comparator<Glob> {
    private Field[] fields;
    private boolean[] ascending;

    public GlobFieldsComparator(Field field1, boolean field1ascending,
                                Field field2, boolean field2ascending) {
        this(new Field[]{field1, field2}, new boolean[]{field1ascending, field2ascending});
    }

    public GlobFieldsComparator(Field field1, boolean field1ascending,
                                Field field2, boolean field2ascending,
                                Field field3, boolean field3ascending) {
        this(new Field[]{field1, field2, field3}, new boolean[]{field1ascending, field2ascending, field3ascending});
    }

    public GlobFieldsComparator(Field field1, boolean field1ascending,
                                Field field2, boolean field2ascending,
                                Field field3, boolean field3ascending,
                                Field field4, boolean field4ascending) {
        this(new Field[]{field1, field2, field3, field4},
             new boolean[]{field1ascending, field2ascending, field3ascending, field4ascending});
    }

    public GlobFieldsComparator(Field... fields) {
        this(fields, createAscending(fields));
    }

    private static boolean[] createAscending(Field[] fields) {
        boolean[] ascendings = new boolean[fields.length];
        Arrays.fill(ascendings, Boolean.TRUE);
        return ascendings;
    }

    private GlobFieldsComparator(Field[] fields, boolean[] ascending) {
        this.fields = fields;
        this.ascending = ascending;
    }

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
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            int multiplier = ascending[i] ? 1 : -1;
            int result = Utils.compare((Comparable)glob1.getValue(field), glob2.getValue(field)) * multiplier;
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    public String toString() {
        return getClass().getSimpleName() + Arrays.toString(fields);
    }
}