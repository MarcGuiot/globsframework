package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.KeyField_;
import org.globsframework.core.metamodel.annotations.Required_;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;

public class DummyObjectWithRequiredFields {
    public static GlobType TYPE;

    @KeyField_
    public static IntegerField ID;

    @Required_
    public static IntegerField VALUE;

    @Required_
    public static StringField NAME;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithRequiredFields.class).load();
    }
}
