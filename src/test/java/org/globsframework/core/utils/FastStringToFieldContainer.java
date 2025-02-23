package org.globsframework.core.utils;

import org.globsframework.core.metamodel.fields.Field;

public class FastStringToFieldContainer {
    private final int[] hash;
    private final Field[] values;

    FastStringToFieldContainer(Field[] data) {
        int size = data.length;
        hash = new int[size];
        values = new Field[size];
        int i = 0;
        for (Field f : data) {
            hash[i] = f.getName().hashCode();
            values[i] = f;
            i++;
        }
    }

    Field get(String key) {
        int i1 = key.hashCode();
        for (int i = 0; i < hash.length; i++) {
            if (hash[i] == i1) {
                String name = values[i].getName();
                if (name == key || name.equals(key)) {
                    return values[i];
                }
            }
        }
        return null;
    }
}
