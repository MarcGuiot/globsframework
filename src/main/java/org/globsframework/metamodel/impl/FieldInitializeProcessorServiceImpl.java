package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.FieldInitializeProcessor;
import org.globsframework.metamodel.FieldInitializeProcessorService;
import org.globsframework.utils.collections.MultiMap;

import java.lang.reflect.Field;
import java.util.List;

public class FieldInitializeProcessorServiceImpl implements FieldInitializeProcessorService {
    private MultiMap<Class, FieldInitializeProcessor> fieldProcessor = new MultiMap<>();
    private boolean getCalled = false;

    public List<FieldInitializeProcessor> get(Field field) {
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
