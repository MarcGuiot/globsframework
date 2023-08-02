package org.globsframework.metamodel;

import org.globsframework.metamodel.annotations.KeyField;
import org.globsframework.metamodel.annotations.Target;
import org.globsframework.metamodel.annotations.Targets;
import org.globsframework.metamodel.fields.*;

public class DummyObjectWithInner {

    public static GlobType TYPE;

    @KeyField
    public static IntegerField ID;

    public static BlobField byteArrayData;

    @Target(value = DummyObjectInner.class)
    public static GlobField VALUE;

    @Target(value = DummyObjectInner.class)
    public static GlobArrayField VALUES;

    @Targets({ DummyObjectInner.class, DummyObject.class})
    public static GlobUnionField VALUE_UNION;

    @Targets({ DummyObjectInner.class, DummyObject.class})
    public static GlobArrayUnionField VALUES_UNION;


    static {
        GlobTypeLoaderFactory.create(DummyObjectWithInner.class).load();
    }
}
