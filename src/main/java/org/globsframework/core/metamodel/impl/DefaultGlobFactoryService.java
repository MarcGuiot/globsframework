package org.globsframework.core.metamodel.impl;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.GlobFactory;
import org.globsframework.core.model.GlobFactoryService;

public class DefaultGlobFactoryService implements GlobFactoryService {
    public GlobFactory getFactory(GlobType type) {
        return new DefaultGlobFactory(type);
    }
}
