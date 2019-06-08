package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.GlobType;

import java.lang.reflect.Field;
import java.util.Collection;

public class GlobTypeInitializer {
    static <T> GlobTypeInit<T> init(Class<T> globTypeClass) {
        return new GlobTypeInit<>(globTypeClass);
    }

    interface GlobTypeAccessor {
        GlobType get(String name);
    }


    interface FieldInit<T> {
        void init(T globTypeInstance, GlobType globType) throws IllegalAccessException;
    }

    static class DoubleFieldInit<T> implements FieldInit<T>{
        String name;
        Field field;

        public void init(T globTypeInstance, GlobType globType) throws IllegalAccessException {
            field.set(globTypeInstance, globType.getField(name));
        }
    }

    static class GlobTypeInit<T> {
        private final String name;
        private final Class<T> globTypeClass;
        Collection<FieldInit<T>> fieldInits;

        public GlobTypeInit(Class<T> globTypeClass) {
            this.globTypeClass = globTypeClass;
            name = null;
        }

        GlobType init(GlobTypeAccessor globTypeAccessor) throws IllegalAccessException, InstantiationException {
            GlobType globType = globTypeAccessor.get(name);
            T globTypeInstance = globTypeClass.newInstance();
            for (FieldInit<T> fieldInit : fieldInits) {
                fieldInit.init(globTypeInstance, globType);
            }
            return globType;
        }
    }
}
