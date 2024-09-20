package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.LongField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class DefaultLongAnnotationType {
    public static GlobType DESC;

    public static LongField DEFAULT_VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DefaultLong defaultLong) {
        return DESC.instantiate().set(DEFAULT_VALUE, defaultLong.value());
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultLongAnnotationType.class);
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultLong) annotation))
                .load();
    }
}
