package org.globsframework.core.metamodel.type;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.annotations.Target;
import org.globsframework.core.metamodel.fields.GlobArrayField;

public class GlobTypesType {
    public static GlobType TYPE;

    @Target(GlobTypeType.class)
    public static GlobArrayField types;

    static {
        GlobTypeLoaderFactory.create(GlobTypesType.class).load();
    }
}
