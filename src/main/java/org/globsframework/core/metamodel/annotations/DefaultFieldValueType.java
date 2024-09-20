package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.model.Key;

public class DefaultFieldValueType {
    public static GlobType TYPE;

    public static Key key;

    static {
        GlobTypeLoaderFactory.create(DefaultFieldValueType.class).load();
    }

    ;
}
