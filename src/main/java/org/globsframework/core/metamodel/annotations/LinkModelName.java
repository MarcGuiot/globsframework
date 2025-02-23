package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.GlobTypeBuilderFactory;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

import java.lang.annotation.Annotation;

public class LinkModelName {
    public static final GlobType TYPE;

    public static final StringField NAME;

    @InitUniqueKey
    public static final Key UNIQUE_KEY;

    static {
        GlobTypeBuilder typeBuilder = GlobTypeBuilderFactory.create("LinkModelName");
        TYPE = typeBuilder.unCompleteType();
        NAME = typeBuilder.declareStringField("NAME");
        typeBuilder.register(GlobCreateFromAnnotation.class, LinkModelName::create);
        typeBuilder.complete();
        UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);

//        GlobTypeLoaderFactory.create(LinkModelName.class, "LinkModelName")
//                .register(GlobCreateFromAnnotation.class, LinkModelName::create)
//                .load();
    }

    private static Glob create(Annotation annotation) {
        return TYPE.instantiate().set(NAME, ((LinkModelName_) annotation).value());
    }
}
