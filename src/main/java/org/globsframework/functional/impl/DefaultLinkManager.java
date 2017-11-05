package org.globsframework.functional.impl;

import org.globsframework.functional.FunctionalKey;
import org.globsframework.functional.FunctionalKeyBuilder;
import org.globsframework.functional.LinkManager;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;

import java.util.*;

public class DefaultLinkManager implements LinkManager {
    private final Collection<Glob> globs;
    private final Map<FunctionalKey, Glob> index = new HashMap<>();
    private final Set<FunctionalKeyBuilder> indexed = new HashSet<>();

    DefaultLinkManager(Collection<Glob> globs){
        this.globs = globs;
    }

    public Glob get(FunctionalKey functionalKey) {
        Glob glob = index.get(functionalKey);
        if (glob == null) {
            return updateIndex(functionalKey);
        }
        else {
            return glob;
        }
    }

    private Glob updateIndex(FunctionalKey functionalKey) {
        FunctionalKeyBuilder builder = functionalKey.getBuilder();
        if (indexed.contains(builder)){
            return null;
        }
        indexed.add(builder);
        GlobType type = functionalKey.getBuilder().getType();
        globs.stream()
              .filter(g -> g.getType() == type)
              .forEach( g -> index.put(builder.create(g), g));
        return index.get(functionalKey);
    }
}
