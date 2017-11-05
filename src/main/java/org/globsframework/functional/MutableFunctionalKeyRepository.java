package org.globsframework.functional;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;

import java.util.stream.Stream;

public interface MutableFunctionalKeyRepository extends FunctionalKeyRepository {
    void put(Glob glob);

    interface DataAccess {

        Stream<Glob> all(GlobType type);
    }
}
