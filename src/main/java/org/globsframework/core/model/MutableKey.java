package org.globsframework.core.model;

// only available from KeyBuilder
public interface MutableKey extends Key, FieldSetter<MutableKey> {
//  void set(Field field, Object value);

    void reset();

    MutableKey duplicateKey();
}
