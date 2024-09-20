package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Key;

public class FunctionalFieldOrderType {
    public static GlobType TYPE;

    public static StringField NAME;

    public static IntegerField ORDER;

    @InitUniqueKey
    public static Key KEY;

    static {
        GlobTypeLoaderFactory.create(FunctionalFieldOrderType.class, "FunctionalFieldOrder")
                .register(GlobCreateFromAnnotation.class, annotation -> TYPE.instantiate()
                        .set(ORDER, ((FunctionalFieldOrderAnnotation) annotation).value())
                        .set(NAME, ((FunctionalFieldOrderAnnotation) annotation).name()))
                .load();
    }
}
