package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.BooleanField;
import org.globsframework.metamodel.GlobTypeLoader;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

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
       loader.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultBoolean)annotation))
       .load();
    }
}
