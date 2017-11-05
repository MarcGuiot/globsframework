package org.globsframework.functional.impl;

import org.globsframework.functional.FunctionalKey;
import org.globsframework.functional.FunctionalKeyBuilder;
import org.globsframework.functional.MutableFunctionalKey;
import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;

import java.util.Arrays;

public class ManyFunctionalKeyBuilder implements FunctionalKeyBuilder {
    Field fields[];
    int index[];

    public ManyFunctionalKeyBuilder(Field[] fields) {
        this.fields = fields;
        index = new int[fields[0].getGlobType().getFieldCount()];
        Arrays.fill(index, -1);
        int i = 0;
        for (Field field : fields) {
            index[field.getIndex()] = i;
            ++i;
        }
    }

    public GlobType getType() {
        return fields[0].getGlobType();
    }

    public Field[] getFields() {
        return fields;
    }

    public FunctionalKey create(Glob glob) {
        return new ManyFieldsMutableKey(this, glob);
    }

    public FunctionalKey proxy(Glob glob) {
        return create(glob);
    }

    public MutableFunctionalKey create() {
        return new ManyFieldsMutableKey(this);
    }
}
