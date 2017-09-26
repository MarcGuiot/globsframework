package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.GlobFactoryService;
import org.globsframework.model.GlobFactory;

public class DefaultGlobFactoryService implements GlobFactoryService {
   public GlobFactory get(GlobType type) {
      return new DefaultGlobFactory(type);
   }
}
