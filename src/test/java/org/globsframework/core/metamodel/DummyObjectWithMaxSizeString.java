package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.KeyField_;
import org.globsframework.core.metamodel.annotations.MaxSize_;
import org.globsframework.core.metamodel.annotations.NamingField_;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;

public class DummyObjectWithMaxSizeString {
    public static GlobType TYPE;

    @KeyField_
    public static IntegerField ID;

    @NamingField_
    @MaxSize_(10)
    public static StringField TEXT;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithMaxSizeString.class).load();
    }
}
