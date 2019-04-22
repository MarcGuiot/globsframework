package org.globsframework.streams.accessors.utils;

import org.globsframework.model.Glob;
import org.globsframework.streams.accessors.GlobAccessor;

public class ValueGlobAccessor implements GlobAccessor {
    private Glob value;

    public ValueGlobAccessor() {
    }

    public ValueGlobAccessor(Glob value) {
        this.value = value;
    }

    public void setValue(Glob value) {
        this.value = value;
    }

    public Object getObjectValue() {
        return value;
    }

    public Glob getGlob() {
        return value;
    }
}
