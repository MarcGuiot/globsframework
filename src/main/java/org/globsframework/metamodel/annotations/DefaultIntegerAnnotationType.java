package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

public class DefaultIntegerAnnotationType {
    public static GlobType DESC;

    public static IntegerField DEFAULT_VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DefaultInteger defaultDouble) {
        return DESC.instantiate().set(DEFAULT_VALUE, defaultDouble.value());
    }

    static {
        GlobTypeLoaderFactory.create(DefaultIntegerAnnotationType.class)
            .register(GlobCreateFromAnnotation.class, annotation -> create((DefaultInteger)annotation))
            .load();
    }

}
