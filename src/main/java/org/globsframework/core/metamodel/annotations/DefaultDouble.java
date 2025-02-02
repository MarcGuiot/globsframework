package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.*;
import org.globsframework.core.metamodel.fields.DoubleField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

public class DefaultDouble {
    public static GlobType TYPE;

    public static DoubleField VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(double defaultDouble) {
        return TYPE.instantiate().set(VALUE, defaultDouble);
    }

    static {
        GlobTypeBuilder typeBuilder = GlobTypeBuilderFactory.create("DefaultDouble");
        TYPE = typeBuilder.unCompleteType();
        VALUE = typeBuilder.declareDoubleField("VALUE");
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> create(((DefaultDouble_) annotation).value()));
        typeBuilder.complete();
        UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);
//        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultDouble.class, "DefaultDouble");
//        loader.register(GlobCreateFromAnnotation.class, annotation -> create(((DefaultDouble_) annotation).value()))
//                .load();
    }
}
