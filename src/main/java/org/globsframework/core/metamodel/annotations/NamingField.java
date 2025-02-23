package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.impl.DefaultGlobTypeBuilder;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

public class NamingField {
    public static final GlobType TYPE;

    @InitUniqueKey
    public static final Key KEY;

    @InitUniqueGlob
    public static final Glob UNIQUE_GLOB;

    static {
        GlobTypeBuilder typeBuilder = new DefaultGlobTypeBuilder("NamingField");
        TYPE = typeBuilder.unCompleteType();
        typeBuilder.complete();
        UNIQUE_GLOB = TYPE.instantiate();
        KEY = KeyBuilder.newEmptyKey(TYPE);
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> UNIQUE_GLOB);

//        GlobTypeLoaderFactory.create(NamingField.class, "NamingField")
//                .register(GlobCreateFromAnnotation.class, new GlobCreateFromAnnotation() {
//                    public Glob create(Annotation annotation) {
//                        return UNIQUE_GLOB;
//                    }
//                })
//                .load();
    }
}
