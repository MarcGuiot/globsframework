package org.globsframework.metamodel.links.impl;

import org.globsframework.metamodel.MutableGlobLinkModel;
import org.globsframework.metamodel.links.DirectLink;
import org.globsframework.metamodel.Annotations;

class DefaultDirectLinkBuilder extends DefaultLinkBuilder<MutableGlobLinkModel.DirectLinkBuilder> implements MutableGlobLinkModel.DirectLinkBuilder {
   private final DefaultMutableGlobLinkModel.OnPublish publish;

   public DefaultDirectLinkBuilder(String modelName, String name, DefaultMutableGlobLinkModel.OnPublish publish, Annotations annotations) {
      super(modelName, name, annotations);
      this.publish = publish;
   }

   public DirectLink publish() {
      DirectLink link = asDirectLink();
      publish.publish(link);
      return link;
   }

   MutableGlobLinkModel.DirectLinkBuilder getT() {
      return this;
   }
}
