package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.KeyField_;
import org.globsframework.core.metamodel.annotations.MultiLineText_;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;

public class DummyObjectWithMultiLineText {
    public static GlobType TYPE;

    @KeyField_
    public static IntegerField ID;

    @MultiLineText_()
    public static StringField COMMENT;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithMultiLineText.class).load();
    }
}
