package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class DefaultStringAnnotationType {
    public static GlobType DESC;

    public static StringField DEFAULT_VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DefaultString defaultString) {
        return DESC.instantiate().set(DEFAULT_VALUE, defaultString.value());
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultStringAnnotationType.class);
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultString) annotation));
        loader.load();
    }
}
