package org.globsframework.core.metamodel.impl;

import org.globsframework.core.metamodel.FieldInitializeProcessor;
import org.globsframework.core.metamodel.FieldInitializeProcessorService;
import org.globsframework.core.utils.collections.MultiMap;

import java.lang.reflect.Field;
import java.util.List;

public class FieldInitializeProcessorServiceImpl implements FieldInitializeProcessorService {
    private final MultiMap<Class<?>, FieldInitializeProcessor<?>> fieldProcessor = new MultiMap<>();
    private boolean getCalled = false;

    public List<FieldInitializeProcessor<?>> get(Field field) {
        getCalled = true;
        return fieldProcessor.get(field.getType());
    }

    public <T> void add(Class<T> type, FieldInitializeProcessor<T> processor) throws ServiceAlreadyUsedException {
        if (getCalled) {
            throw new ServiceAlreadyUsedException();
        }
        fieldProcessor.put(type, processor);
    }
}
