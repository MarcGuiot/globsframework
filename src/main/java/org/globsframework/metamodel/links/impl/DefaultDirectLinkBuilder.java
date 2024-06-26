package org.globsframework.metamodel.links.impl;

import org.globsframework.metamodel.Annotations;
import org.globsframework.metamodel.MutableGlobLinkModel;
import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.links.DirectLink;

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

    DefaultDirectLinkBuilder getT() {
        return this;
    }
}
