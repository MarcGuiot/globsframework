package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

public class IgnoredAnnotationType {
    public static GlobType TYPE;

    @InitUniqueKey
    public static Key KEY;

    @InitUniqueGlob
    public static Glob INSTANCE;

    static {
        GlobTypeLoaderFactory.create(IgnoredAnnotationType.class, "IgnoredAnnotationType")
            .register(GlobCreateFromAnnotation.class, annotation -> INSTANCE)
            .load();
    }
}
