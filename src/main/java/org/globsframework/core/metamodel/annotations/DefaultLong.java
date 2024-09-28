package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.LongField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class DefaultLong {
    public static GlobType TYPE;

    public static LongField VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DefaultLong_ defaultLong) {
        return TYPE.instantiate().set(VALUE, defaultLong.value());
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultLong.class, "DefaultLong");
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultLong_) annotation))
                .load();
    }
}
