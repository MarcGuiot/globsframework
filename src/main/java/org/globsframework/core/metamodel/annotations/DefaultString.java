package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.GlobTypeBuilderFactory;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

public class DefaultString {
    public static final GlobType TYPE;

    public static final StringField VALUE;

    @InitUniqueKey
    public static final Key KEY;

    public static Glob create(DefaultString_ defaultString) {
        return TYPE.instantiate().set(VALUE, defaultString.value());
    }

    public static Glob create(String defaultString) {
        return TYPE.instantiate().set(VALUE, defaultString);
    }

    static {
        GlobTypeBuilder typeBuilder = GlobTypeBuilderFactory.create("DefaultString");
        TYPE = typeBuilder.unCompleteType();
        VALUE = typeBuilder.declareStringField("value");
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultString_) annotation));
        typeBuilder.complete();
        KEY = KeyBuilder.newEmptyKey(TYPE);
//        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultString.class, "DefaultString");
//        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultString_) annotation));
//        loader.load();
    }
}
