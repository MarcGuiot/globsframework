package org.globsframework.core.functional;

import org.globsframework.core.model.Glob;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public interface FunctionalKeyRepository {

    Glob getUnique(FunctionalKey functionalKey);

    Collection<Glob> get(FunctionalKey functionalKey);

    Glob computeUniqueIfAbsent(FunctionalKey functionalKey, Function<FunctionalKey, Glob> mappingFunction);

    Stream<FunctionalKey> getAll(FunctionalKeyBuilder functionalKeyBuilder);


}
