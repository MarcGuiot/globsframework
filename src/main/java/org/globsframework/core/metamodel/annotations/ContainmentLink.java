package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.*;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

public class ContainmentLink {
    public static GlobType TYPE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    @InitUniqueGlob
    public static Glob UNIQUE_GLOB;

    static {
        GlobTypeBuilder typeBuilder = GlobTypeBuilderFactory.create("ContainmentLink");
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> UNIQUE_GLOB);
        TYPE = typeBuilder.unCompleteType();
        typeBuilder.complete();
        UNIQUE_GLOB = TYPE.instantiate();
        UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);

//        GlobTypeLoader loader = GlobTypeLoaderFactory.create(ContainmentLink.class, "ContainmentLink");
//                .load();
    }
}
