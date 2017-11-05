package org.globsframework.model.repository;

import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

public interface GlobIdGenerator {
    int getNextId(IntegerField keyField, int idCount);

    GlobIdGenerator NONE = new GlobIdGenerator() {
        public int getNextId(IntegerField keyField, int idCount) {
            throw new UnexpectedApplicationState("No ID generator registered for type: " + keyField.getGlobType().getName());
        }
    };

}
