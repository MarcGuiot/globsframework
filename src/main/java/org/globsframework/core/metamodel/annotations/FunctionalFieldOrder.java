package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Key;

public class FunctionalFieldOrder {
    public static GlobType TYPE;

    public static StringField NAME;

    public static IntegerField ORDER;

    @InitUniqueKey
    public static Key KEY;

    static {
        GlobTypeLoaderFactory.create(FunctionalFieldOrder.class, "FunctionalFieldOrder")
                .register(GlobCreateFromAnnotation.class, annotation -> TYPE.instantiate()
                        .set(ORDER, ((FunctionalFieldOrder_) annotation).value())
                        .set(NAME, ((FunctionalFieldOrder_) annotation).name()))
                .load();
    }
}
