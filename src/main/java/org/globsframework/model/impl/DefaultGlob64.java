package org.globsframework.model.impl;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobType;

public class DefaultGlob64 extends AbstractDefaultGlob {
    protected int hashCode;
    private long set;

    public DefaultGlob64(GlobType type) {
        super(type);
    }

    public void setSetAt(int index) {
        set |= (1L << index);
    }

    public boolean isSetAt(int index) {
        return (set & (1L << index)) != 0;
    }

    public void clearSetAt(int index) {
        set &= ~(1L << index);
    }

    public int hashCode() {
        if (hashCode != 0) {
            return hashCode;
        }
        int hashCode = getType().hashCode();
        for (Field keyField : getType().getKeyFields()) {
            Object value = getValue(keyField);
            hashCode = 31 * hashCode + (value != null ? keyField.valueHash(value) : 0);
        }
        if (hashCode == 0) {
            hashCode = 31;
        }
        this.hashCode = hashCode;
        return hashCode;
    }

    public boolean isHashComputed() {
        return hashCode != 0;
    }

}
