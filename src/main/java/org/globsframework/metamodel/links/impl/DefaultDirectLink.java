package org.globsframework.metamodel.links.impl;

import org.globsframework.metamodel.Annotations;
import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.annotations.RequiredAnnotationType;
import org.globsframework.metamodel.links.DirectLink;
import org.globsframework.metamodel.links.FieldMappingFunction;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.model.KeyBuilder;
import org.globsframework.utils.collections.Pair;
import org.globsframework.utils.exceptions.InvalidParameter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultDirectLink extends AbstractLink implements DirectLink {
    private final List<Pair<Field, Field>> mappings;
    private final GlobType sourceType;
    private final GlobType targetType;

    public DefaultDirectLink(List<Pair<Field, Field>> mappings, String modelName, String name, Annotations annotations) {
        super(modelName, name, annotations);
        this.mappings = mappings;
        Pair<Field, Field> mapping = mappings.get(0);
        sourceType = mapping.getFirst().getGlobType();
        targetType = mapping.getSecond().getGlobType();
    }

    public GlobType getSourceType() {
        return sourceType;
    }

    public GlobType getTargetType() {
        return targetType;
    }

    public boolean isRequired() {
        return hasAnnotation(RequiredAnnotationType.UNIQUE_KEY);
    }

    public <T extends FieldMappingFunction> T apply(T functor) {
        mappings.forEach(pair -> functor.process(pair.getFirst(), pair.getSecond()));
        return functor;
    }

    public boolean isContainment() {
        return false;
    }

    public Key getTargetKey(Glob source) {
        KeyBuilder keyBuilder = KeyBuilder.init(targetType);
        mappings.forEach(pair -> keyBuilder.setObject(pair.getSecond(), source.getValue(pair.getSecond())));
        return keyBuilder.get();
    }

    public static DirectLink create(List<Pair<Field, Field>> mappings, String modelName, String name,
                                    Annotations annotations) {
        Field first = null;
        Field second = null;
        for (Pair<Field, Field> mapping : mappings) {
            first = first == null ? mapping.getFirst() : first;
            if (first.getGlobType() != mapping.getFirst().getGlobType()) {
                throw new InvalidParameter("field source must come from the same type " + first.getFullName() + " != " + mapping.getFirst().getFullName());
            }
            second = second == null ? mapping.getSecond() : second;
            if (second.getGlobType() != mapping.getSecond().getGlobType()) {
                throw new InvalidParameter("field target must come from the same type " + second.getFullName() + " != " + mapping.getSecond().getFullName());
            }
        }
        if (second.getGlobType().getKeyFields().length != mappings.size()) {
            Set<Field> keySet = mappings.stream().map(Pair::getSecond).collect(Collectors.toSet());
            List<Field> missing = Arrays.stream(second.getGlobType().getKeyFields())
                .filter(field -> !keySet.contains(field)).collect(Collectors.toList());
            throw new InvalidParameter("All key field of target must be references. Missing : " + missing);
        }
        if (mappings.size() == 1) {
            return new DefaultDirectSingleLink(first, second, modelName, name, annotations);
        }
        else {
            return new DefaultDirectLink(mappings, modelName, name, annotations);
        }
    }
}
