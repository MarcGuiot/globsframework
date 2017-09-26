package org.globsframework.metamodel;

import org.globsframework.metamodel.impl.DefaultGlobTypeBuilder;


public class GlobTypeBuilderFactory {

   public static GlobTypeBuilder create(String name) {
      return new DefaultGlobTypeBuilder(name);
   }
}
