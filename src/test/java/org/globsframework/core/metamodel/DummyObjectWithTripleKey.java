package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.KeyField;
import org.globsframework.core.metamodel.annotations.NamingField;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;

public class DummyObjectWithTripleKey {

    public static GlobType TYPE;

    @KeyField
    public static IntegerField ID1;
    @KeyField
    public static IntegerField ID2;
    @KeyField
    public static IntegerField ID3;

    @NamingField
    public static StringField NAME;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithTripleKey.class).load();
    }
}
