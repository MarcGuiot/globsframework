package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

import java.lang.annotation.Annotation;

public class LinkModelNameAnnotationType {
   public static GlobType TYPE;

   public static StringField NAME;

   @InitUniqueKey
   public static Key UNIQUE_KEY;

   static {
      GlobTypeLoaderFactory.create(LinkModelNameAnnotationType.class)
         .register(GlobCreateFromAnnotation.class, LinkModelNameAnnotationType::create)
      .load();
   }

   private static Glob create(Annotation annotation) {
      return TYPE.instantiate().set(NAME, ((LinkModelName)annotation).value());
   }
}
