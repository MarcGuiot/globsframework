package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class DefaultString {
    public static GlobType TYPE;

    public static StringField VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DefaultString_ defaultString) {
        return TYPE.instantiate().set(VALUE, defaultString.value());
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultString.class, "DefaultString");
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultString_) annotation));
        loader.load();
    }
}
