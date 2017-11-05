package org.globsframework.metamodel;

import org.globsframework.metamodel.annotations.KeyField;
import org.globsframework.metamodel.annotations.NamingField;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.fields.StringField;

public class DummyObjectWithQuadrupleKey {

    public static GlobType TYPE;

    @KeyField
    public static IntegerField ID1;
    @KeyField
    public static IntegerField ID2;
    @KeyField
    public static IntegerField ID3;
    @KeyField
    public static IntegerField ID4;

    @NamingField
    public static StringField NAME;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithQuadrupleKey.class).load();
    }
}