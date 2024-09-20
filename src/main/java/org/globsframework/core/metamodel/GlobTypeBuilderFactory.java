package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.impl.DefaultGlobTypeBuilder;


public class GlobTypeBuilderFactory {

    public static GlobTypeBuilder create(String name) {
        return new DefaultGlobTypeBuilder(name);
    }
}
