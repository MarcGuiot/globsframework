package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationsHelper {

    public static GlobType getType(Class clazz) {
        Field type = search(clazz);
        Object value = null;
        try {
            value = type.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Field " + clazz.getName() + "." + type.getName() + " is not accesible (not public?)");
        }
        if (value == null) {
            throw new RuntimeException("Field " + clazz.getName() + "." + type.getName() + " should not be null");
        }
        return (GlobType) value;
    }

    private static Field search(Class clazz) {
        Field[] fields = clazz.getFields();
        List<Field> collect = Arrays.stream(fields).filter(field -> field.getType().isAssignableFrom(GlobType.class)
                        && Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            throw new RuntimeException("Can not find a field 'public static GlobType' on" + clazz.getName());
        }
        if (collect.size() != 1) {
            throw new RuntimeException("Duplicate GlobType field on " + clazz.getName() +
                    " fields : " + collect.stream().map(Field::getName).collect(Collectors.toList()));
        }
        return collect.get(0);
    }

}
