package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.model.Key;

public class DefaultFieldValueType {
    public static GlobType TYPE;

    public static Key key;

    static {
        GlobTypeLoaderFactory.create(DefaultFieldValueType.class).load();
    }

    ;
}
