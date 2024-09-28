package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class DefaultInteger {
    public static GlobType TYPE;

    public static IntegerField VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DefaultInteger_ defaultDouble) {
        return TYPE.instantiate().set(VALUE, defaultDouble.value());
    }

    static {
        GlobTypeLoaderFactory.create(DefaultInteger.class, "DefaultInteger")
                .register(GlobCreateFromAnnotation.class, annotation -> create((DefaultInteger_) annotation))
                .load();
    }

}
