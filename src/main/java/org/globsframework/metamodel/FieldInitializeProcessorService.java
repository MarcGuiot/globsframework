package org.globsframework.metamodel;

import java.lang.reflect.Field;
import java.util.List;

public interface FieldInitializeProcessorService {

    List<FieldInitializeProcessor<?>> get(Field field);

    <T> void add(Class<T> type, FieldInitializeProcessor<T> processor) throws ServiceAlreadyUsedException;

    class ServiceAlreadyUsedException extends RuntimeException {

    }
}
