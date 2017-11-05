package org.globsframework.metamodel.properties.impl;

public abstract class AbstractPropertyHolder<T> implements AbstractDelegatePropertyHolder<T> {
    private volatile Object[] properties = new Object[]{NULL_OBJECT, NULL_OBJECT};

    public Object[] getProperties() {
        return this.properties;
    }

    public void setProperties(Object[] properties) {
        this.properties = properties;
    }

}
