package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.*;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;
import org.globsframework.core.model.MutableGlob;

public class DoublePrecision {
    public static GlobType TYPE;

    public static IntegerField PRECISION;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DoublePrecision_ precision) {
        return create(precision.value());
    }

    public static Glob create(int value) {
        return TYPE.instantiate().set(PRECISION, value);
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DoublePrecision.class, "DoublePrecision");
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DoublePrecision_) annotation));
        loader.load();
    }
}
