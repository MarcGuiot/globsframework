package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.GlobTypeBuilderFactory;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

public class DefaultInteger {
    public static GlobType TYPE;

    public static IntegerField VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DefaultInteger_ defaultDouble) {
        return TYPE.instantiate().set(VALUE, defaultDouble.value());
    }

    static {
        GlobTypeBuilder typeBuilder = GlobTypeBuilderFactory.create("DefaultInteger");
        TYPE = typeBuilder.unCompleteType();
        VALUE = typeBuilder.declareIntegerField("VALUE");
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultInteger_) annotation));
        typeBuilder.complete();
        UNIQUE_KEY = KeyBuilder.newEmptyKey( TYPE);

//        GlobTypeLoaderFactory.create(DefaultInteger.class, "DefaultInteger")
//                .register(GlobCreateFromAnnotation.class, annotation -> create((DefaultInteger_) annotation))
//                .load();
    }

}
