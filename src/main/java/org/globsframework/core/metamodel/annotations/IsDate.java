package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.impl.DefaultGlobTypeBuilder;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

// date en nombre de jours depuis 01/01/1970

public class IsDate {
    public static final GlobType TYPE;

    @InitUniqueKey
    public static final Key KEY;

    @InitUniqueGlob
    public static final Glob UNIQUE;

    static {
        GlobTypeBuilder typeBuilder = new DefaultGlobTypeBuilder("IsDate");
        TYPE = typeBuilder.get();
        KEY = KeyBuilder.newEmptyKey(TYPE);
        UNIQUE = TYPE.instantiate();
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> UNIQUE);
//        GlobTypeLoaderFactory.create(IsDate.class, "IsDate")
//                .register(GlobCreateFromAnnotation.class, annotation -> UNIQUE)
//                .load();
    }
}
