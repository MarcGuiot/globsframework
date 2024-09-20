package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class DoublePrecisionAnnotationType {
    public static GlobType DESC;

    public static IntegerField PRECISION;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DoublePrecision precision) {
        return DESC.instantiate().set(PRECISION, precision.value());
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DoublePrecisionAnnotationType.class);
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DoublePrecision) annotation));
        loader.load();
    }
}
