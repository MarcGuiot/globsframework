package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.metamodel.impl.DefaultGlobTypeBuilder;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

public class FunctionalFieldOrder {
    public static final GlobType TYPE;

    public static final StringField NAME;

    public static final IntegerField ORDER;

    @InitUniqueKey
    public static final Key KEY;

    static {
        GlobTypeBuilder typeBuilder = new DefaultGlobTypeBuilder("FunctionalFieldOrder");

        TYPE = typeBuilder.unCompleteType();
        NAME = typeBuilder.declareStringField("name");
        ORDER = typeBuilder.declareIntegerField("order");
        typeBuilder.complete();
        KEY = KeyBuilder.newEmptyKey(TYPE);
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> TYPE.instantiate()
                .set(ORDER, ((FunctionalFieldOrder_) annotation).value())
                .set(NAME, ((FunctionalFieldOrder_) annotation).name()));

//        GlobTypeLoaderFactory.create(FunctionalFieldOrder.class, "FunctionalFieldOrder")
//                .register(GlobCreateFromAnnotation.class, annotation -> TYPE.instantiate()
//                        .set(ORDER, ((FunctionalFieldOrder_) annotation).value())
//                        .set(NAME, ((FunctionalFieldOrder_) annotation).name()))
//                .load();
    }
}
