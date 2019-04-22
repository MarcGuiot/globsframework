package org.globsframework.streams.accessors.utils;

import org.globsframework.model.Glob;
import org.globsframework.streams.accessors.GlobAccessor;
import org.globsframework.streams.accessors.GlobsAccessor;

public class ValueGlobsAccessor implements GlobsAccessor {
    private Glob value[];

    public ValueGlobsAccessor() {
    }

    public ValueGlobsAccessor(Glob[] value) {
        this.value = value;
    }

    public void setValue(Glob[] value) {
        this.value = value;
    }

    public Object getObjectValue() {
        return value;
    }

    public Glob[] getGlobs() {
        return value;
    }
}
