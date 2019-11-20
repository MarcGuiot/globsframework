package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoader;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.utils.Strings;

public class MaxSizeType {
    static public GlobType TYPE;

    @DefaultFieldValue
    static public IntegerField VALUE;

    @InitUniqueKey
    static public Key KEY;

    static public String cut(Field field, String value) {
        Glob annotation = field.findAnnotation(KEY);
        if (annotation != null && Strings.isNotEmpty(value)) {
            return value.substring(0, annotation.size());
        }
        return value;
    }

    public static Glob create(MaxSize size) {
        return TYPE.instantiate().set(VALUE, size.value());
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(MaxSizeType.class);
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((MaxSize)annotation))
            .load();
    }
}
