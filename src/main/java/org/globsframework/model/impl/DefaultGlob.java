package org.globsframework.model.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.MutableGlob;

import java.util.BitSet;

public class DefaultGlob extends AbstractDefaultGlob {

    public DefaultGlob(GlobType type) {
        super(type);
    }

    private DefaultGlob(GlobType type, Object[] values, BitSet bitSet) {
        super(type, values, bitSet);
    }
}
