package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

import java.lang.annotation.Annotation;

public class LinkModelName {
    public static GlobType TYPE;

    public static StringField NAME;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    static {
        GlobTypeLoaderFactory.create(LinkModelName.class, "LinkModelName")
                .register(GlobCreateFromAnnotation.class, LinkModelName::create)
                .load();
    }

    private static Glob create(Annotation annotation) {
        return TYPE.instantiate().set(NAME, ((LinkModelName_) annotation).value());
    }
}
