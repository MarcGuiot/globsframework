package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.BooleanField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class DefaultBoolean {
    public static GlobType TYPE;

    public static BooleanField VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DefaultBoolean_ defaultDouble) {
        return TYPE.instantiate().set(VALUE, defaultDouble.value());
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultBoolean.class, "DefaultBoolean");
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultBoolean_) annotation))
                .load();
    }
}
