package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.KeyField_;
import org.globsframework.core.metamodel.annotations.NamingField_;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;

public class DummyObjectWithTripleKey {

    public static GlobType TYPE;

    @KeyField_
    public static IntegerField ID1;
    @KeyField_
    public static IntegerField ID2;
    @KeyField_
    public static IntegerField ID3;

    @NamingField_
    public static StringField NAME;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithTripleKey.class).load();
    }
}
