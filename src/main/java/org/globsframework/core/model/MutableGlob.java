package org.globsframework.core.model;


import org.globsframework.core.metamodel.fields.Field;

public interface MutableGlob extends Glob, FieldSetter<MutableGlob> {
    MutableGlob unset(Field field);
}
