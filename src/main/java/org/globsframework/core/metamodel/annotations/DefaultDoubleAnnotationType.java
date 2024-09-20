package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.DoubleField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class DefaultDoubleAnnotationType {
    public static GlobType DESC;

    @DefaultFieldValue
    public static DoubleField DEFAULT_VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(double defaultDouble) {
        return DESC.instantiate().set(DEFAULT_VALUE, defaultDouble);
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultDoubleAnnotationType.class);
        loader.register(GlobCreateFromAnnotation.class, annotation -> create(((DefaultDouble) annotation).value()))
                .load();
    }
}
