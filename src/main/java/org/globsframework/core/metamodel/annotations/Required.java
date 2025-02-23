package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.impl.DefaultGlobTypeBuilder;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

public class Required {
    public static final GlobType TYPE;

    @InitUniqueKey
    public static final Key UNIQUE_KEY;

    @InitUniqueGlob
    public static final Glob UNIQUE_GLOB;

    static {
//        GlobTypeLoaderFactory.create(Required.class, "Required")
//                .register(GlobCreateFromAnnotation.class, annotation -> UNIQUE_GLOB)
//                .load();
        GlobTypeBuilder globTypeBuilder = new DefaultGlobTypeBuilder("Required");
        TYPE = globTypeBuilder.unCompleteType();
        globTypeBuilder.complete();
        UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);
        UNIQUE_GLOB = TYPE.instantiate();
        globTypeBuilder.register(GlobCreateFromAnnotation.class, annotation -> UNIQUE_GLOB);
    }

}
