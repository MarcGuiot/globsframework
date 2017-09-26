package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.metamodel.GlobTypeLoader;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

public class DefaultStringAnnotationType {
   public static GlobType DESC;

   public static StringField DEFAULT_VALUE;

   @InitUniqueKey
   public static Key UNIQUE_KEY;

   public static Glob create(DefaultString defaultString) {
      return DESC.instantiate().set(DEFAULT_VALUE, defaultString.value());
   }

   static {
      GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultStringAnnotationType.class);
      loader.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultString)annotation));
      loader.load();
   }
}
