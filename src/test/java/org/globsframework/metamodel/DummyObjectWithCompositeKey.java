package org.globsframework.metamodel;

import org.globsframework.metamodel.annotations.KeyField;
import org.globsframework.metamodel.annotations.NamingField;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.fields.StringField;

public class DummyObjectWithCompositeKey {

    public static GlobType TYPE;

    @KeyField
    public static IntegerField ID1;
    @KeyField
    public static IntegerField ID2;

    @NamingField
    public static StringField NAME;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithCompositeKey.class, true).load();
    }
}
