package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class DoublePrecision {
    public static GlobType TYPE;

    public static IntegerField PRECISION;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DoublePrecision_ precision) {
        return TYPE.instantiate().set(PRECISION, precision.value());
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DoublePrecision.class, "DoublePrecision");
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DoublePrecision_) annotation));
        loader.load();
    }
}
