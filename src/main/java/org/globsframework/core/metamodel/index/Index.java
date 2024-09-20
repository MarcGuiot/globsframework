package org.globsframework.core.metamodel.index;

import org.globsframework.core.metamodel.fields.Field;

import java.util.stream.Stream;

public interface Index {
    String getName();

    <T extends IndexVisitor> T visit(T indexVisitor);

    Stream<Field> fields();
}
