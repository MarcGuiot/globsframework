package org.globsframework.model.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.FieldValues;
import org.globsframework.model.Glob;
import org.globsframework.model.MutableGlob;

public class DefaultGlob extends AbstractDefaultGlob {

    public DefaultGlob(GlobType type) {
        super(type);
    }

    public DefaultGlob(GlobType type, Object[] values) {
        super(type, values);
    }

    public DefaultGlob(GlobType type, FieldValues values) {
        super(type);
        setValues(values);
    }

    public MutableGlob duplicate() {
        return new DefaultGlob(type, duplicateValues());
    }
}
