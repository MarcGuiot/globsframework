package org.globsframework.directory;

import org.globsframework.utils.exceptions.ItemAlreadyExists;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class DefaultDirectory implements Directory {
    private LinkedHashMap<Class, Object> services = new LinkedHashMap<Class, Object>();
    private Directory inner;
    private HashMap<Class, Factory> servicesFactory = new HashMap<Class, Factory>();

    public DefaultDirectory() {
    }

    public DefaultDirectory(Directory inner) {
        this.inner = inner;
    }

    public <T> T find(Class<T> serviceClass) {
        T result = (T)services.get(serviceClass);
        if (result == null && servicesFactory.containsKey(serviceClass)) {
            result = ((T)servicesFactory.get(serviceClass).create());
            services.put(serviceClass, result);
        }
        if ((result == null) && (inner != null)) {
            return inner.find(serviceClass);
        }
        return result;
    }

    public boolean contains(Class serviceClass) {
        return find(serviceClass) != null;
    }

    public <T> T get(Class<T> serviceClass) {
        T service = find(serviceClass);
        if (service == null) {
            throw new ItemNotFound("No service found for class: " + serviceClass.getName());
        }
        return service;
    }

    public void add(Object service) throws ItemAlreadyExists {
        add((Class<Object>)service.getClass(), service);
    }

    public <T, D extends T> void addFactory(Class<T> serviceClass, Factory<D> factory) throws ItemAlreadyExists {
        if (services.containsKey(serviceClass) || servicesFactory.containsKey(serviceClass)) {
            throw new ItemAlreadyExists("Service already registered for class: " + serviceClass.getName());
        }
        servicesFactory.put(serviceClass, factory);
    }

    public <T, D extends T> void add(Class<T> serviceClass, D service) throws ItemAlreadyExists {
        if (services.containsKey(serviceClass) || servicesFactory.containsKey(serviceClass)) {
            throw new ItemAlreadyExists("Service already registered for class: " + serviceClass.getName());
        }
        services.put(serviceClass, service);
    }
}
