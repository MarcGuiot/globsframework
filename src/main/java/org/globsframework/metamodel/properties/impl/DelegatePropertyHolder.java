package org.globsframework.metamodel.properties.impl;

public class DelegatePropertyHolder<T> extends AbstractPropertyHolder<T> {
    private final T propertyOwner;

    public DelegatePropertyHolder(T propertyOwner) {
        this.propertyOwner = propertyOwner;
    }

    public T getValueOwner() {
        return propertyOwner;
    }
}
