package org.globsframework.core.streams.accessors.utils;

import org.globsframework.core.streams.accessors.BlobAccessor;

public class ValueBlobAccessor implements BlobAccessor {
    private byte[] values;

    public ValueBlobAccessor() {
    }

    public ValueBlobAccessor(byte[] values) {
        this.values = values;
    }

    public void setValue(byte[] values) {
        this.values = values;
    }

    public byte[] getValue() {
        return values;
    }

    public Object getObjectValue() {
        return values;
    }
}
