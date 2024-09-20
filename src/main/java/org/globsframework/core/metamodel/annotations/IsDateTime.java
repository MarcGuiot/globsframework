package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

// date et heure en nombre de millsecond depuis 01/01/1970

public class IsDateTime {
    public static GlobType TYPE;

    @InitUniqueKey
    public static Key KEY;

    @InitUniqueGlob
    public static Glob UNIQUE;

    static {
        GlobTypeLoaderFactory.create(IsDateTime.class)
                .register(GlobCreateFromAnnotation.class, annotation -> UNIQUE)
                .load();
    }
}
