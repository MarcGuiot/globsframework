package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.StringField;

public class NotUniqueIndexType {
    public static GlobType TYPE;

    public static StringField NAME;

    static {
        GlobTypeLoaderFactory.create(NotUniqueIndexType.class, "NotUniqueIndex")
                .load();
    }
}
