package org.globsframework.core.functional;

import org.globsframework.core.functional.impl.DefaultFunctionalKeyBuilderFactory;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.Field;

public interface FunctionalKeyBuilderFactory {

    static FunctionalKeyBuilderFactory create(GlobType globType) {
        return new DefaultFunctionalKeyBuilderFactory(globType);
    }

    FunctionalKeyBuilderFactory add(Field field);

    FunctionalKeyBuilder create();

}
