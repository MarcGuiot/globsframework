package org.globsframework.metamodel.links.impl;

import org.globsframework.metamodel.Annotations;
import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.annotations.FieldNameAnnotationType;
import org.globsframework.metamodel.links.DirectSingleLink;
import org.globsframework.metamodel.links.FieldMappingFunction;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.utils.exceptions.GlobsException;

import java.lang.annotation.Annotation;

public class UnInitializedLink extends AbstractLink implements DirectSingleLink {
    private final GlobType type;

    public UnInitializedLink(GlobType type, Annotations annotations, Annotation[] nativeAnnotations) {
        super("", getName(annotations), annotations);
        this.type = type;
    }

    static String getName(Annotations annotations) {
        Glob annotation = annotations.findAnnotation(FieldNameAnnotationType.UNIQUE_KEY);
        if (annotation != null) {
            return annotation.get(FieldNameAnnotationType.NAME);
        }
        throw new GlobsException("Missing field name annotation");
    }

    public GlobType getSourceType() {
        throw new LinkNotInitialized(type.getName() + ":" + getName());
    }

    public GlobType getTargetType() {
        throw new LinkNotInitialized(type.getName() + ":" + getName());
    }

    public boolean isRequired() {
        throw new LinkNotInitialized(type.getName() + ":" + getName());
    }

    public <T extends FieldMappingFunction> T apply(T functor) {
        throw new LinkNotInitialized(type.getName() + ":" + getName());
    }

    public boolean isContainment() {
        throw new LinkNotInitialized(type.getName() + ":" + getName());
    }

    public Key getTargetKey(Glob source) {
        throw new LinkNotInitialized(type.getName() + ":" + getName());
    }

    public Field getSourceField() {
        throw new LinkNotInitialized(type.getName() + ":" + getName());
    }

    public Field getTargetField() {
        throw new LinkNotInitialized(type.getName() + ":" + getName());
    }

    public static class LinkNotInitialized extends GlobsException {

        public LinkNotInitialized(String name) {
            super("link " + name + " not initialized. (missing code is something like : loader.register(MutableGlobLinkModel.LinkRegister.class,\n" +
                  "                      (linkModel) -> LINK = linkModel.getLinkBuilder(\"ModelName\", \"linkName\").add(sourceFieldId, targetFieldId).publish()); )");
        }
    }

}
