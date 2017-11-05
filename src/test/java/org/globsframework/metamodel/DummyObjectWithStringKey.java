package org.globsframework.metamodel;

import org.globsframework.metamodel.annotations.KeyField;
import org.globsframework.metamodel.fields.StringField;

public class DummyObjectWithStringKey {
    public static GlobType TYPE;

    @KeyField
    public static StringField ID;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithStringKey.class).load();
    }
}
