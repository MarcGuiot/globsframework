package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.*;
import org.globsframework.core.metamodel.fields.BigDecimalField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

import java.math.BigDecimal;

public class DefaultBigDecimal {

    public static final GlobType TYPE;

    public static final BigDecimalField VALUE;

    @InitUniqueKey
    public static final Key UNIQUE_KEY;

    public static Glob create(String defaultBigDecimal) {
        return TYPE.instantiate().set(VALUE, new BigDecimal(defaultBigDecimal));
    }

    static {
        GlobTypeBuilder typeBuilder = GlobTypeBuilderFactory.create("DefaultBigDecimal");
        TYPE = typeBuilder.unCompleteType();
        VALUE = typeBuilder.declareBigDecimalField("VALUE");
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> create(((DefaultBigDecimal_) annotation).value()));
        typeBuilder.complete();
        UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);

//        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultBigDecimal.class, "DefaultBigDecimal");
//        loader.register(GlobCreateFromAnnotation.class, annotation -> create(((DefaultBigDecimal_) annotation).value()))
//                .load();
    }
}
