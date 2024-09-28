package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.KeyField_;
import org.globsframework.core.metamodel.fields.StringField;

public class DummyObjectWithStringKey {
    public static GlobType TYPE;

    @KeyField_
    public static StringField ID;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithStringKey.class, true).load();
    }
}
