package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.DoublePrecision_;
import org.globsframework.core.metamodel.annotations.KeyField_;
import org.globsframework.core.metamodel.fields.DoubleField;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;

public class DummyObject2 {

    public static GlobType TYPE;

    @KeyField_
    public static IntegerField ID;

    public static StringField LABEL;

    @DoublePrecision_(4)
    public static DoubleField VALUE;

    static {
        GlobTypeLoaderFactory.create(DummyObject2.class, true).load();
    }
}
