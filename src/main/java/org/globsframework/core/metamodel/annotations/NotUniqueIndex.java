package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.StringField;

public class NotUniqueIndex {
    public static GlobType TYPE;

    public static StringField NAME;

    static {
        GlobTypeLoaderFactory.create(NotUniqueIndex.class, "NotUniqueIndex")
                .load();
    }
}
