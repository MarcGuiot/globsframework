package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.metamodel.impl.DefaultFieldFactory;
import org.globsframework.metamodel.impl.DefaultGlobType;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.model.KeyBuilder;
import org.globsframework.model.MutableGlob;

public class FieldNameAnnotationType {
    public static GlobType TYPE;

    public static StringField NAME;

    public static Key UNIQUE_KEY;

    public static Glob create(FieldNameAnnotation nameAnnotation) {
        return create(nameAnnotation.value());
    }

    public static MutableGlob create(String value) {
        return TYPE.instantiate().set(NAME, value);
    }

    static {
        DefaultGlobType globType = new DefaultGlobType("fieldNameAnnotation");
        DefaultFieldFactory factory = new DefaultFieldFactory(globType);
        TYPE = globType;
        NAME = factory.addString("name", false, 0, 0, null);
        globType.completeInit();
        UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);
        NAME.addAnnotation(create("name"));
        globType.register(GlobCreateFromAnnotation.class, annotation -> create((FieldNameAnnotation)annotation));
    }

    public static String getName(Field field) {
        Glob annotation = field.findAnnotation(UNIQUE_KEY);
        if (annotation != null) {
            return annotation.get(NAME);
        }
        return field.getName();
    }
}
