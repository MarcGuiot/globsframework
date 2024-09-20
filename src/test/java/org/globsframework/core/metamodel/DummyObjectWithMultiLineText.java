package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.KeyField;
import org.globsframework.core.metamodel.annotations.MultiLineText;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;

public class DummyObjectWithMultiLineText {
    public static GlobType TYPE;

    @KeyField
    public static IntegerField ID;

    @MultiLineText()
    public static StringField COMMENT;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithMultiLineText.class).load();
    }
}
