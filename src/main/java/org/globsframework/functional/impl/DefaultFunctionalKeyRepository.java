package org.globsframework.functional.impl;

import org.globsframework.functional.FunctionalKey;
import org.globsframework.functional.FunctionalKeyBuilder;
import org.globsframework.functional.MutableFunctionalKeyRepository;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DefaultFunctionalKeyRepository implements MutableFunctionalKeyRepository {
    private final DataAccess dataAccess;
    private final Map<FunctionalKey, Collection<Glob>> index = new ConcurrentHashMap<>();
    private final Map<GlobType, Map<FunctionalKeyBuilder, Boolean>> indexed = new ConcurrentHashMap<>();

    public DefaultFunctionalKeyRepository(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Glob getUnique(FunctionalKey functionalKey) {
        Collection<Glob> globs = get(functionalKey);
        return globs.size() == 1 ? globs.iterator().next() : null;
    }

    public Collection<Glob> get(FunctionalKey functionalKey) {
        Collection<Glob> glob = index.get(functionalKey);
        if (glob == null) {
            return updateIndex(functionalKey);
        }
        else {
            return glob;
        }
    }

    private Collection<Glob> updateIndex(FunctionalKey functionalKey) {
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
            Collection<Glob> globs = index.get(functionalKey);
            return globs == null ? Collections.emptyList() : globs;
        }
    }

    private void update(Glob g, FunctionalKey key) {
        Collection<Glob> globs = index.get(key);
        if (globs == null) {
            globs = new CopyOnWriteArrayList<>();
        }
        globs.add(g);
        index.put(key, globs);
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
