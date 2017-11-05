package org.globsframework.metamodel.properties;

public interface PropertyHolder<T> {

    // get return te default value if no value was set via key.createValue
    <D> D getProperty(Property<T, D> key);

    <D> void updateProperty(Property<T, D> key, D value);

}
