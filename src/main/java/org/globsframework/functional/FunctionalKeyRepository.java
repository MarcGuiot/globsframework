package org.globsframework.functional;

import org.globsframework.model.Glob;

import java.util.Collection;

public interface FunctionalKeyRepository {

    Glob getUnique(FunctionalKey functionalKey);

    Collection<Glob> get(FunctionalKey functionalKey);

}
