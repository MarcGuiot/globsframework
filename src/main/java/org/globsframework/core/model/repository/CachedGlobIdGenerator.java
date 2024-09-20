package org.globsframework.core.model.repository;

import org.globsframework.core.metamodel.fields.IntegerField;

import java.util.HashMap;
import java.util.Map;

public class CachedGlobIdGenerator implements GlobIdGenerator {
    private GlobIdGenerator innerGenerator;
    private Map<IntegerField, Id> moreData = new HashMap<IntegerField, Id>();
    public static final int MIN_COUNT = 5;
    private static final int MAX_STEP = 40;


    public CachedGlobIdGenerator(GlobIdGenerator innerGenerator) {
        this.innerGenerator = innerGenerator;
    }

    public int getNextId(IntegerField keyField, int idCount) {
        Id currentId = moreData.get(keyField);
        if (currentId == null) {
            currentId = new Id(keyField, innerGenerator);
            moreData.put(keyField, currentId);
        }
        return currentId.getNext(idCount);
    }

    private static class Id {
        private GlobIdGenerator innerGenerator;
        private final IntegerField keyField;
        private int currentStep = MIN_COUNT;
        private int nextMaxId;
        private int currentId;

        private Id(IntegerField keyField, GlobIdGenerator globIdGenerator) {
            this.keyField = keyField;
            this.innerGenerator = globIdGenerator;
        }

        int getNext(int idCount) {
            if (currentId + idCount > nextMaxId) {
                currentId = innerGenerator.getNextId(keyField, idCount + currentStep);
                nextMaxId = currentId + idCount + currentStep;
                if (currentStep < MAX_STEP) {
                    currentStep *= 2;
                }
            }
            try {
                return currentId;
            } finally {
                currentId += idCount;
            }
        }
    }
}
