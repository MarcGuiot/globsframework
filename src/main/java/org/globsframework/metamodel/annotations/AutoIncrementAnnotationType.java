package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

public class AutoIncrementAnnotationType {
    public static GlobType TYPE;

    @InitUniqueKey
    public static Key KEY;

    @InitUniqueGlob
    public static Glob INSTANCE;

    static {
        GlobTypeLoaderFactory.create(AutoIncrementAnnotationType.class)
            .register(GlobCreateFromAnnotation.class, annotation -> INSTANCE)
            .load();
    }
}
