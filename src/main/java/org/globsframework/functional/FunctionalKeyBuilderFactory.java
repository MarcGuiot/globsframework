package org.globsframework.functional;

import org.globsframework.functional.impl.DefaultFunctionalKeyBuilderFactory;
import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;

public interface FunctionalKeyBuilderFactory {

    static FunctionalKeyBuilderFactory create(GlobType globType){
        return new DefaultFunctionalKeyBuilderFactory(globType);
    }

    FunctionalKeyBuilderFactory add(Field field);

    FunctionalKeyBuilder create();

}
