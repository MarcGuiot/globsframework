package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

// date en nombre de jours depuis 01/01/1970

public class IsDate {
    public static GlobType TYPE;

    @InitUniqueKey
    public static Key KEY;

    @InitUniqueGlob
    public static Glob UNIQUE;

    static {
        GlobTypeLoaderFactory.create(IsDate.class)
              .register(GlobCreateFromAnnotation.class, annotation -> UNIQUE)
              .load();
    }
}
