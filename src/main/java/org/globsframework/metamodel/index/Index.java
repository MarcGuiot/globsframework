package org.globsframework.metamodel.index;

import org.globsframework.metamodel.Field;

import java.util.stream.Stream;

public interface Index {
    String getName();

    <T extends IndexVisitor> T visit(T indexVisitor);

    Stream<Field> fields();
}
