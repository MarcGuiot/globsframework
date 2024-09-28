package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

import java.lang.annotation.Annotation;

public class NamingField {
    public static GlobType TYPE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    @InitUniqueGlob
    public static Glob UNIQUE_GLOB;

    static {
        GlobTypeLoaderFactory.create(NamingField.class, "NamingField")
                .register(GlobCreateFromAnnotation.class, new GlobCreateFromAnnotation() {
                    public Glob create(Annotation annotation) {
                        return UNIQUE_GLOB;
                    }
                })
                .load();
    }
}
