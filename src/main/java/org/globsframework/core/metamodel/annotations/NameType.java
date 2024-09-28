package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.MutableGlob;

public class NameType {
    public static GlobType TYPE;

    @KeyField_
    public static StringField NAME;

    public static StringField VALUE;

    static {
        GlobTypeLoaderFactory.create(NameType.class, "Name").load();
    }

    public static Glob create(Name name) {
        return create(name.name(), name.value());
    }

    public static MutableGlob create(String name, String value) {
        return TYPE.instantiate().set(NAME, name).set(VALUE, value);
    }

    public static String getName(Key key, Field field) {
        if (key.getGlobType() != TYPE) {
            throw new RuntimeException("Key is of type " + key.toString() + " but " + NAME.getName() + " was expected.");
        }
        Glob annotation = field.findAnnotation(key);
        if (annotation != null) {
            return annotation.get(NAME);
        }
        return field.getName();
    }
}
