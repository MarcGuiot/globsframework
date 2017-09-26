package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.*;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

public class DoublePrecisionAnnotationType {
   public static GlobType DESC;

   public static IntegerField PRECISION;

   @InitUniqueKey
   public static Key UNIQUE_KEY;

   public static Glob create(DoublePrecision precision) {
      return DESC.instantiate().set(PRECISION, precision.value());
   }

   static {
      GlobTypeLoader loader = GlobTypeLoaderFactory.create(DoublePrecisionAnnotationType.class);
      loader.register(GlobCreateFromAnnotation.class, annotation -> create((DoublePrecision)annotation));
      loader.load();
   }
}
