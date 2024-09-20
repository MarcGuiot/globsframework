package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.metamodel.fields.StringArrayField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.MutableGlob;

import java.util.Optional;

public class EnumAnnotationType {
    public static GlobType TYPE;

    public static StringArrayField NAME;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    static {
        GlobTypeLoaderFactory.create(EnumAnnotationType.class)
                .register(GlobCreateFromAnnotation.class, annotation -> TYPE.instantiate()
                        .set(NAME, ((EnumAnnotation) annotation).value()))
                .load();
    }

    public static Glob create(EnumAnnotation nameAnnotation) {
        return create(nameAnnotation.value());
    }

    public static MutableGlob create(String[] value) {
        return TYPE.instantiate().set(NAME, value);
    }

    public static Optional<String[]> listEnums(Field field) {
        Glob annotation = field.findAnnotation(UNIQUE_KEY);
        if (annotation != null) {
            return Optional.of(annotation.get(NAME));
        }
        return Optional.empty();
    }
}
