package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;

public class DefaultGlob128 extends AbstractDefaultGlob {
    protected int hashCode;
    private long set1;
    private long set2;

    public DefaultGlob128(GlobType type) {
        super(type);
    }

    public void setSetAt(int index) {
        if (index < 64) {
            set1 |= (1L << index);
        } else if (index < 128) {
            set2 |= (1L << (index - 64));
        }
        else {
            throw new RuntimeException("index out of range " + index);
        }
    }

    public boolean isSetAt(int index) {
        if (index < 64) {
            return (set1 & (1L << index)) != 0;
        } else if (index < 128) {
            return (set2 & (1L << (index - 64))) != 0;
        }
        else {
            throw new RuntimeException("index out of range " + index);
        }
    }

    public void clearSetAt(int index) {
        if (index < 64) {
            set1 &= ~(1L << index);
        } else if (index < 128) {
            set2 &= ~(1L << (index - 64));
        }
        else {
            throw new RuntimeException("index out of range " + index);
        }
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
