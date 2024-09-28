package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class Required {
    public static GlobType TYPE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    @InitUniqueGlob
    public static Glob UNIQUE_GLOB;

    static {
        GlobTypeLoaderFactory.create(Required.class, "Required")
                .register(GlobCreateFromAnnotation.class, annotation -> UNIQUE_GLOB)
                .load();
//      GlobTypeBuilder globTypeBuilder = new DefaultGlobTypeBuilder("requiredAnnotation");
//      globTypeBuilder.register(GlobCreateFromAnnotation.class, annotation -> create((Required)annotation));
//      TYPE = globTypeBuilder.get();
//      UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);
    }

}
