package org.globsframework.functional;

import org.globsframework.model.Glob;

import java.util.Collection;
import java.util.function.Function;

public interface FunctionalKeyRepository {

    Glob getUnique(FunctionalKey functionalKey);

    Collection<Glob> get(FunctionalKey functionalKey);

    Glob computeUniqueIfAbsent(FunctionalKey functionalKey, Function<FunctionalKey, Glob> mappingFunction);

}
