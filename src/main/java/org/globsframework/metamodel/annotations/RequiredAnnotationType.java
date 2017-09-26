package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

public class RequiredAnnotationType {
   public static GlobType TYPE;

   @InitUniqueKey
   public static Key UNIQUE_KEY;

   @InitUniqueGlob
   public static Glob UNIQUE_GLOB;

   static {
      GlobTypeLoaderFactory.create(RequiredAnnotationType.class)
         .register(GlobCreateFromAnnotation.class, annotation -> UNIQUE_GLOB)
         .load();
//      GlobTypeBuilder globTypeBuilder = new DefaultGlobTypeBuilder("requiredAnnotation");
//      globTypeBuilder.register(GlobCreateFromAnnotation.class, annotation -> create((Required)annotation));
//      TYPE = globTypeBuilder.get();
//      UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);
   }

}
