package org.globsframework.functional.impl;

import org.globsframework.functional.FunctionalKey;
import org.globsframework.functional.FunctionalKeyBuilder;
import org.globsframework.functional.MutableFunctionalKeyRepository;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;
import org.globsframework.model.format.GlobPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultUniqueFunctionalKeyRepository implements MutableFunctionalKeyRepository {
    static private final Logger LOGGER = LoggerFactory.getLogger(DefaultUniqueFunctionalKeyRepository.class);
    private final DataAccess dataAccess;
    private final Map<FunctionalKey, Glob> index = new ConcurrentHashMap<>();
    private final Map<GlobType, Map<FunctionalKeyBuilder, Boolean>> indexed = new ConcurrentHashMap<>();

    public DefaultUniqueFunctionalKeyRepository(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Collection<Glob> get(FunctionalKey functionalKey) {
        Glob glob = getUnique(functionalKey);
        if (glob == null) {
            return updateIndex(functionalKey);
        } else {
            return Collections.singletonList(glob);
        }
    }

    public Glob getUnique(FunctionalKey functionalKey) {
        return index.get(functionalKey);
    }

    private List<Glob> updateIndex(FunctionalKey functionalKey) {
        FunctionalKeyBuilder builder = functionalKey.getBuilder();
        GlobType type = builder.getType();
        Map<FunctionalKeyBuilder, Boolean> functionalKeyBuilders = indexed.get(type);
        if (functionalKeyBuilders != null && functionalKeyBuilders.containsKey(builder)) {
            return Collections.emptyList();
        }
        synchronized (type) {
            functionalKeyBuilders = indexed.get(type);
            if (functionalKeyBuilders == null) {
                functionalKeyBuilders = new ConcurrentHashMap<>();
                indexed.put(type, functionalKeyBuilders);
            }
            dataAccess.all(type)
                  .forEach(g -> {
                      FunctionalKey key = builder.create(g);
                      update(g, key);
                  });
            functionalKeyBuilders.put(builder, Boolean.TRUE);
            Glob o = index.get(functionalKey);
            return o != null ? Collections.singletonList(o) : Collections.emptyList();
        }
    }

    private void update(Glob g, FunctionalKey key) {
        Glob put = index.put(key, g);
        if (put != null) {
            LOGGER.error("Duplicate entry " + GlobPrinter.toString(put) + " and " + GlobPrinter.toString(g) + " for key " + GlobPrinter.toString(key));
        }
    }

    public void put(Glob glob) {
        synchronized (glob.getType()) {
            Map<FunctionalKeyBuilder, Boolean> functionalKeyBuilderBooleanMap = indexed.get(glob.getType());
            for (Map.Entry<FunctionalKeyBuilder, Boolean> functionalKeyBuilderBooleanEntry : functionalKeyBuilderBooleanMap.entrySet()) {
                update(glob, functionalKeyBuilderBooleanEntry.getKey().create(glob));
            }
        }
    }
}
