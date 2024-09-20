package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.KeyField;
import org.globsframework.core.metamodel.annotations.Required;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;

public class DummyObjectWithRequiredFields {
    public static GlobType TYPE;

    @KeyField
    public static IntegerField ID;

    @Required
    public static IntegerField VALUE;

    @Required
    public static StringField NAME;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithRequiredFields.class).load();
    }
}
