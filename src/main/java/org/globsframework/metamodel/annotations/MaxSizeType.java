package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.GlobTypeLoader;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

public class MaxSizeType {
   static public GlobType TYPE;

   @DefaultFieldValue
   static public IntegerField VALUE;

   @InitUniqueKey
   static public Key KEY;

   public static Glob create(MaxSize size) {
      return TYPE.instantiate().set(VALUE, size.value());
   }

   static {
      GlobTypeLoader loader = GlobTypeLoaderFactory.create(MaxSizeType.class);
      loader.register(GlobCreateFromAnnotation.class, annotation -> create((MaxSize)annotation))
      .load();
   }
}
