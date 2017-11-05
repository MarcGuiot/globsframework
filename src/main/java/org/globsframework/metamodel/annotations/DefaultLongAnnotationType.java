package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoader;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.fields.LongField;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

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
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultLong)annotation))
            .load();
    }
}
