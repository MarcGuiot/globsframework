package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.KeyField;
import org.globsframework.core.metamodel.annotations.MaxSize;
import org.globsframework.core.metamodel.annotations.NamingField;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;

public class DummyObjectWithMaxSizeString {
    public static GlobType TYPE;

    @KeyField
    public static IntegerField ID;

    @NamingField
    @MaxSize(10)
    public static StringField TEXT;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithMaxSizeString.class).load();
    }
}
