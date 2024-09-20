package org.globsframework.core.model.repository;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.IntegerField;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DefaultGlobIdGenerator implements GlobIdGenerator {
    private Map<IntegerField, Integer> fieldToCurrentId = new HashMap<IntegerField, Integer>();

    public DefaultGlobIdGenerator() {
    }

    public int getNextId(IntegerField keyField, int idCount) {
        Integer currentId = getNextCurrentId(keyField);
        try {
            return currentId;
        } finally {
            fieldToCurrentId.put(keyField, currentId + idCount);
        }
    }

    private Integer getNextCurrentId(IntegerField keyField) {
        Integer currentId = fieldToCurrentId.get(keyField);
        if (currentId == null) {
            return 100;
        }
        return currentId;
    }

    public void update(IntegerField field, Integer lastAllocatedId) {
        fieldToCurrentId.put(field, lastAllocatedId + 1);
    }

    public void reset(Collection<GlobType> collection) {
        for (Iterator<IntegerField> it = fieldToCurrentId.keySet().iterator(); it.hasNext(); ) {
            IntegerField field = it.next();
            if (collection.contains(field.getGlobType())) {
                it.remove();
            }
        }
    }
}
