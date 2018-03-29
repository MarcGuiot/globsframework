package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.fields.StringField;

public class NotUniqueIndexType {
    public static GlobType TYPE;

    public static StringField NAME;

    static {
        GlobTypeLoaderFactory.create(NotUniqueIndexType.class, "NotUniqueIndex")
              .load();
    }
}
