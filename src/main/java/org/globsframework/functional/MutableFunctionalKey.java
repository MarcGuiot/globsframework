package org.globsframework.functional;

import org.globsframework.model.FieldSetter;

public interface MutableFunctionalKey extends FieldSetter<MutableFunctionalKey> {

    FunctionalKey getShared();

    FunctionalKey create();
}
