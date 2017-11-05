package org.globsframework.metamodel.utils;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.annotations.NamingFieldAnnotationType;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemAmbiguity;
import org.globsframework.utils.exceptions.ItemNotFound;

public class GlobTypeUtils {

    public static GlobType getType(Class targetClass) throws InvalidParameter {
        for (java.lang.reflect.Field field : targetClass.getFields()) {
            if (field.getType().equals(GlobType.class)) {
                try {
                    return (GlobType)field.get(null);
                }
                catch (Exception e) {
                    throw new InvalidParameter("Cannot access GlobType field in class " + targetClass.getName(), e);
                }
            }
        }
        throw new InvalidParameter("Class " + targetClass.getName() + " does not define a GlobType");
    }

    public static StringField findNamingField(GlobType type) {
        Field field = type.findFieldWithAnnotation(NamingFieldAnnotationType.UNIQUE_KEY);
        if (field != null) {
            return stringField(field, type);
        }
        return null;
    }

    public static StringField getNamingField(GlobType type) throws ItemNotFound, ItemAmbiguity, InvalidParameter {
        Field field = type.getFieldWithAnnotation(NamingFieldAnnotationType.UNIQUE_KEY);
        return stringField(field, type);
    }

    private static StringField stringField(Field field, GlobType type) {
        if (!(field instanceof StringField)) {
            throw new InvalidParameter("Naming field of type '" + type + "' should be a StringField");
        }
        return (StringField)field;
    }
}
