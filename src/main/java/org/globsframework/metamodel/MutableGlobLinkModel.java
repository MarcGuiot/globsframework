package org.globsframework.metamodel;

import org.globsframework.metamodel.links.DirectLink;
import org.globsframework.metamodel.links.Link;
import org.globsframework.metamodel.utils.MutableAnnotations;
import org.globsframework.model.Glob;

public interface MutableGlobLinkModel extends GlobLinkModel {

   LinkBuilder getLinkBuilder(String modelName, String name, Glob...globAnnotations);

   LinkBuilder getLinkBuilder(Annotations annotations);

   DirectLinkBuilder getDirectLinkBuilder(String modelName, String name, Glob...globAnnotations);

   DirectLinkBuilder getDirectLinkBuilder(Annotations annotations);

  // DirectLinkBuilder getDirectLinkBuilder(Annotations annotations, LinkType linkType);

   enum LinkType {
      // containment
      ContainmentOneToOne,
      ContainmentOneToOneReverse,
      ContainmentOneToManyReverse,
      // association
      AssociationOne,
      AssociationMany,
   }

//   enum LinkTypeTarget {
//      // containment
//      ToOne,
//      ToOneReverse,
//      ToManyReverse,
//      // association
//      ToOneIndirect
//   }

   interface LinkRegister {
      void register(MutableGlobLinkModel mutableGlobLinkModel);
   }

   interface DirectLinkBuilder extends LinkBuilder<DirectLinkBuilder> {
      DirectLink publish();
   }

   interface LinkBuilder<T extends LinkBuilder> extends MutableAnnotations<T> {

      T add(Field sourceField, Field targetField);

      Link publish();
   }
}
