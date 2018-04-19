package org.globsframework.metamodel;

import org.globsframework.metamodel.annotations.KeyField;
import org.globsframework.metamodel.annotations.Target;
import org.globsframework.metamodel.fields.GlobArrayField;
import org.globsframework.metamodel.fields.GlobField;
import org.globsframework.metamodel.fields.IntegerField;

public class DummyObjectWithInner {

    public static GlobType TYPE;

    @KeyField
    public static IntegerField ID;

    @Target(value = DummyObjectInner.class)
    public static GlobField VALUE;

    @Target(value = DummyObjectInner.class)
    public static GlobArrayField VALUES;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithInner.class).load();
    }
}
