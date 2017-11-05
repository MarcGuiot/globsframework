package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoader;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.fields.DoubleField;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

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
        loader.register(GlobCreateFromAnnotation.class, annotation -> create(((DefaultDouble)annotation).value()))
            .load();
    }
}
