package org.globsframework.metamodel.type;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.annotations.Target;
import org.globsframework.metamodel.fields.GlobArrayField;

public class GlobTypesType {
    public static GlobType TYPE;

    @Target(GlobTypeType.class)
    public static GlobArrayField types;

    static {
        GlobTypeLoaderFactory.create(GlobTypesType.class).load();
    }
}
