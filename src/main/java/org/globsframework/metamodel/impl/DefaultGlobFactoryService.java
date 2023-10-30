package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.GlobFactory;
import org.globsframework.model.GlobFactoryService;

public class DefaultGlobFactoryService implements GlobFactoryService {
    public GlobFactory getFactory(GlobType type) {
        return new DefaultGlobFactory(type);
    }
}
