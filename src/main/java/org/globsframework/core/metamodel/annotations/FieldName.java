package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.metamodel.impl.DefaultFieldFactory;
import org.globsframework.core.metamodel.impl.DefaultGlobType;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;
import org.globsframework.core.model.MutableGlob;

import java.util.LinkedHashMap;

public class FieldName {
    public static final GlobType TYPE;

    public static final StringField NAME;

    public static final Key UNIQUE_KEY;

    public static Glob create(FieldName_ nameAnnotation) {
        return create(nameAnnotation.value());
    }

    public static MutableGlob create(String value) {
        return TYPE.instantiate().set(NAME, value);
    }

    static {
        DefaultGlobType globType = new DefaultGlobType("FieldName");
        DefaultFieldFactory factory = new DefaultFieldFactory(globType);
        TYPE = globType;
        NAME = factory.addString("name", false, 0, 0, null, new LinkedHashMap<>());
        globType.completeInit();
        UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);
        NAME.addAnnotation(create("name"));
        globType.register(GlobCreateFromAnnotation.class, annotation -> create((FieldName_) annotation));
    }

    public static String getName(Field field) {
        Glob annotation = field.findAnnotation(UNIQUE_KEY);
        if (annotation != null) {
            return annotation.get(NAME);
        }
        return field.getName();
    }
}
