package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.BooleanField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class DefaultBooleanAnnotationType {
    public static GlobType TYPE;

    @DefaultFieldValue
    public static BooleanField DEFAULT_VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DefaultBoolean defaultDouble) {
        return TYPE.instantiate().set(DEFAULT_VALUE, defaultDouble.value());
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultBooleanAnnotationType.class);
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultBoolean) annotation))
                .load();
    }
}
