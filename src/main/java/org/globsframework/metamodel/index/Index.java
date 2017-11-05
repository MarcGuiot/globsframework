package org.globsframework.metamodel.index;

import org.globsframework.metamodel.Field;

import java.io.Serializable;

public interface Index extends Serializable {
    String getName();

    Field getField();

    void visitIndex(IndexVisitor visitor);
}
