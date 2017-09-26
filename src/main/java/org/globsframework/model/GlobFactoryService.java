package org.globsframework.model;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.impl.DefaultGlobFactoryService;

public interface GlobFactoryService {

   GlobFactory get(GlobType type);

   class Builder {
      static private Throwable firstCallLocation;
      static private GlobFactoryService builderFactory;

      public static GlobFactoryService getBuilderFactory() {
         if (builderFactory == null) {
            firstCallLocation = new Throwable();
            builderFactory = new DefaultGlobFactoryService();
         }
         return builderFactory;
      }

      public static void setBuilderFactory(GlobFactoryService builderFactory) {
         if (Builder.builderFactory != null) {
            throw new RuntimeException("GlobFactoryService already created at : ", firstCallLocation);
         }
         else {
            firstCallLocation = new Throwable();
         }
         Builder.builderFactory = builderFactory;
      }
   }

}
