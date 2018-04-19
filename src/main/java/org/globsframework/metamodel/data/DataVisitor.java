package org.globsframework.metamodel.data;

public interface DataVisitor {
    DataVisitor pushArray();
    DataVisitor pushObject();

    DataVisitor attribute(String name);

    DataVisitor push(int value);
    DataVisitor push(double value);
    DataVisitor push(boolean value);

    DataVisitor endObject();
    DataVisitor endArray();
}
