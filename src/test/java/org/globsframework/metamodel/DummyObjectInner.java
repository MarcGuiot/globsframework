package org.globsframework.metamodel;

import org.globsframework.metamodel.fields.DoubleField;
import org.globsframework.metamodel.fields.IntegerField;

public class DummyObjectInner {

    public static GlobType TYPE;

    public static IntegerField DATE;

    public static DoubleField VALUE;

    static {
        GlobTypeLoaderFactory.create(DummyObjectInner.class).load();
    }
}
