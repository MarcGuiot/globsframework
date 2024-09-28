package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class ContainmentLink {
    public static GlobType TYPE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    @InitUniqueGlob
    public static Glob UNIQUE_GLOB;

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(ContainmentLink.class, "ContainmentLink");
        loader.register(GlobCreateFromAnnotation.class, annotation -> UNIQUE_GLOB)
                .load();
    }
}
