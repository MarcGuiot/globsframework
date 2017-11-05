package org.globsframework.functional.impl;

import org.globsframework.functional.FunctionalKey;
import org.globsframework.functional.FunctionalKeyBuilder;
import org.globsframework.functional.MutableFunctionalKey;
import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;

public class TwoFunctionalKeyBuilder implements FunctionalKeyBuilder {
    final Field field1;
    final Field field2;
    private Field fields[];

    public TwoFunctionalKeyBuilder(Field field1, Field field2) {
        this.field1 = field1;
        this.field2 = field2;
        fields = new Field[]{field1, field2};
    }

    public GlobType getType() {
        return field1.getGlobType();
    }

    public Field[] getFields() {
        return fields;
    }

    public FunctionalKey create(Glob glob) {
        return new TwoFieldsMutableKey(this,
                                       glob.getValue(field1),
                                       glob.getValue(field2));
    }

    public FunctionalKey proxy(Glob glob) {
        return create(glob);
    }

    public MutableFunctionalKey create() {
        return new TwoFieldsMutableKey(this);
    }
}
