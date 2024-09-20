package org.globsframework.core.functional;

import org.globsframework.core.model.FieldSetter;

public interface MutableFunctionalKey extends FieldSetter<MutableFunctionalKey> {

    FunctionalKey getShared();

    FunctionalKey create();
}
