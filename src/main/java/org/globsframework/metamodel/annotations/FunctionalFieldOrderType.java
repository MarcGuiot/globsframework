package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.model.Key;

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
