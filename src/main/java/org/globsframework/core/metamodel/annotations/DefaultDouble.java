package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.DoubleField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class DefaultDouble {
    public static GlobType TYPE;

    public static DoubleField VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(double defaultDouble) {
        return TYPE.instantiate().set(VALUE, defaultDouble);
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultDouble.class, "DefaultDouble");
        loader.register(GlobCreateFromAnnotation.class, annotation -> create(((DefaultDouble_) annotation).value()))
                .load();
    }
}
