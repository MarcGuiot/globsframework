package org.globsframework.core.functional.impl;

import org.globsframework.core.functional.FunctionalKey;
import org.globsframework.core.functional.FunctionalKeyBuilder;
import org.globsframework.core.functional.LinkManager;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.Glob;

import java.util.*;

public class DefaultLinkManager implements LinkManager {
    private final Collection<Glob> globs;
    private final Map<FunctionalKey, Glob> index = new HashMap<>();
    private final Set<FunctionalKeyBuilder> indexed = new HashSet<>();

    DefaultLinkManager(Collection<Glob> globs) {
        this.globs = globs;
    }

    public Glob get(FunctionalKey functionalKey) {
        Glob glob = index.get(functionalKey);
        if (glob == null) {
            return updateIndex(functionalKey);
        } else {
            return glob;
        }
    }

    private Glob updateIndex(FunctionalKey functionalKey) {
        FunctionalKeyBuilder builder = functionalKey.getBuilder();
        if (indexed.contains(builder)) {
            return null;
        }
        indexed.add(builder);
        GlobType type = functionalKey.getBuilder().getType();
        globs.stream()
                .filter(g -> g.getType() == type)
                .forEach(g -> index.put(builder.create(g), g));
        return index.get(functionalKey);
    }
}
