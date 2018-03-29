package org.globsframework.functional.impl;

import org.globsframework.functional.FunctionalKey;
import org.globsframework.functional.FunctionalKeyBuilder;
import org.globsframework.functional.MutableFunctionalKeyRepository;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;
import org.globsframework.utils.collections.MapOfMaps;
import org.globsframework.utils.collections.MultiMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class DefaultFunctionalKeyRepository implements MutableFunctionalKeyRepository {
    private final DataAccess dataAccess;
    private final Map<FunctionalKey, Collection<Glob>> index = new ConcurrentHashMap<>();
    private final MapOfMaps<GlobType, FunctionalKeyBuilder, Boolean> indexed = new MapOfMaps<>();

    public DefaultFunctionalKeyRepository(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public DefaultFunctionalKeyRepository(DataAccess dataAccess, Collection<FunctionalKeyBuilder> functionalKeyBuilders) {
        this.dataAccess = dataAccess;
        for (FunctionalKeyBuilder functionalKeyBuilder : functionalKeyBuilders) {
            indexed.put(functionalKeyBuilder.getType(), functionalKeyBuilder, Boolean.FALSE);
        }
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

    public Glob computeUniqueIfAbsent(FunctionalKey functionalKey, Function<FunctionalKey, Glob> mappingFunction) {
        Collection<Glob> globCollection = index.get(functionalKey);
        if (globCollection == null) {
            globCollection = updateIndex(functionalKey);
            if (globCollection.isEmpty()) {
                Glob glob = mappingFunction.apply(functionalKey);
                if (glob != null) {
                    index.put(functionalKey, Collections.singletonList(glob));
                    return glob;
                }
                return null;
            }
            else {
                return globCollection.size() == 1 ? globCollection.iterator().next() : null;
            }
        }
        else {
            return globCollection.size() == 1 ? globCollection.iterator().next() : null;
        }
    }

    private Collection<Glob> updateIndex(FunctionalKey functionalKey) {
        FunctionalKeyBuilder builder = functionalKey.getBuilder();
        GlobType type = builder.getType();
        Map<FunctionalKeyBuilder, Boolean> functionalKeyBuilders = indexed.get(type);
        if (functionalKeyBuilders != null && functionalKeyBuilders.get(builder) == Boolean.TRUE) {
            return Collections.emptyList();
        }
        synchronized (type) {
            dataAccess.all(type)
                .forEach(g -> {
                    FunctionalKey key = builder.create(g);
                    update(g, key);
                });
            indexed.put(type, builder, Boolean.TRUE);
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

    public void setNull(FunctionalKey oppositeKey) {
        index.put(oppositeKey, Collections.emptyList());
    }
}
