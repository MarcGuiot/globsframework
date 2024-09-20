package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

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
