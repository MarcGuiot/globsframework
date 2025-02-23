package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.impl.DefaultGlobTypeBuilder;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

public class IsTarget {
    public static final GlobType TYPE;

    @InitUniqueKey
    public static final Key KEY;

    @InitUniqueGlob
    public static final Glob INSTANCE;

    static {
        GlobTypeBuilder typeBuilder = new DefaultGlobTypeBuilder("IsTarget");
        TYPE = typeBuilder.get();
        KEY = KeyBuilder.newEmptyKey(TYPE);
        INSTANCE = TYPE.instantiate();
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> INSTANCE);
//        GlobTypeLoaderFactory.create(IsTarget.class, "IsTarget")
//                .register(GlobCreateFromAnnotation.class, annotation -> INSTANCE)
//                .load();
    }
}
