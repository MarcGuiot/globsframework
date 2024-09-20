package org.globsframework.core.metamodel.links.impl;

import org.globsframework.core.metamodel.Annotations;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.impl.DefaultAnnotations;
import org.globsframework.core.metamodel.links.Link;

public abstract class AbstractLink extends DefaultAnnotations implements Link {
    private final String modelName;
    private final String name;

    public AbstractLink(String modelName, String name, Annotations annotations) {
        super(annotations);
        this.modelName = modelName;
        this.name = name;
    }

    public String getLinkModelName() {
        return modelName;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return toString(name, getSourceType(), getTargetType());
    }

    public static String toString(String name, GlobType sourceType, GlobType targetType) {
        return name + "[" + sourceType.getName() + " => " +
                targetType.getName() + "]";
    }

}
