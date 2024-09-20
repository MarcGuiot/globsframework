package org.globsframework.core.functional;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.Glob;

import java.util.stream.Stream;

public interface MutableFunctionalKeyRepository extends FunctionalKeyRepository {
    void put(Glob glob);

    void setNull(FunctionalKey oppositeKey);

    interface DataAccess {

        Stream<Glob> all(GlobType type);
    }
}
