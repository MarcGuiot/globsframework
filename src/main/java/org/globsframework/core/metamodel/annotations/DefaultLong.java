package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.*;
import org.globsframework.core.metamodel.fields.LongField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

public class DefaultLong {
    public static GlobType TYPE;

    public static LongField VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DefaultLong_ defaultLong) {
        return TYPE.instantiate().set(VALUE, defaultLong.value());
    }

    static {
        GlobTypeBuilder typeBuilder = GlobTypeBuilderFactory.create("DefaultLong");
        TYPE = typeBuilder.unCompleteType();
        VALUE = typeBuilder.declareLongField("VALUE");
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultLong_) annotation));
        typeBuilder.complete();
        UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);
//        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultLong.class, "DefaultLong");
//        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultLong_) annotation))
//                .load();
    }
}
