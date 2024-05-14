package org.globsframework.metamodel.links.impl;

import org.globsframework.metamodel.Annotations;
import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.annotations.RequiredAnnotationType;
import org.globsframework.metamodel.links.DirectSingleLink;
import org.globsframework.metamodel.links.FieldMappingFunction;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.model.KeyBuilder;

public class DefaultDirectSingleLink extends AbstractLink implements DirectSingleLink {
    private final Field sourceField;
    private final Field targetField;

    public DefaultDirectSingleLink(Field sourceField, Field targetField, String modelName, String name, Annotations annotations) {
        super(modelName, name, annotations);
        this.sourceField = sourceField;
        this.targetField = targetField;

    }

    public GlobType getSourceType() {
        return sourceField.getGlobType();
    }

    public GlobType getTargetType() {
        return targetField.getGlobType();
    }

    public boolean isRequired() {
        return hasAnnotation(RequiredAnnotationType.UNIQUE_KEY);
    }

    public <T extends FieldMappingFunction> T apply(T functor) {
        functor.process(sourceField, targetField);
        return functor;
    }

    public boolean isContainment() {
        return false;
    }

    public Field getSourceField() {
        return sourceField;
    }

    public Field getTargetField() {
        return targetField;
    }

    public Key getTargetKey(Glob source) {
        return KeyBuilder.newKey(targetField, source.getValue(sourceField));
    }
}
