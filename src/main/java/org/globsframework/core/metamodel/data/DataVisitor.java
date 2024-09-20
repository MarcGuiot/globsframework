package org.globsframework.core.metamodel.data;


// a data structure that we can only visit (like a SAX API for Json)
// It is expected to be simpler than using GlobType.
// less typed
// we should be able to represent here data like sql where (Constraints)


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
