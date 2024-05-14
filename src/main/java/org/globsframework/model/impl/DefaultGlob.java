package org.globsframework.model.impl;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobType;

import java.util.BitSet;

public class DefaultGlob extends AbstractDefaultGlob {
    protected int hashCode;
    protected final BitSet isSet;

    public DefaultGlob(GlobType type) {
        super(type);
        isSet = new BitSet(type.getFieldCount());
    }

    public void setSetAt(int index) {
        isSet.set(index);
    }

    public boolean isSetAt(int index) {
        return isSet.get(index);
    }

    public void clearSetAt(int index) {
        isSet.clear(index);
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
