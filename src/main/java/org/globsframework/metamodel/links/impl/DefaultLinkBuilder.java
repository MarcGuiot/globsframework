package org.globsframework.metamodel.links.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.MutableGlobLinkModel;
import org.globsframework.metamodel.links.DirectLink;
import org.globsframework.metamodel.links.Link;
import org.globsframework.metamodel.Annotations;
import org.globsframework.metamodel.impl.DefaultAnnotations;
import org.globsframework.metamodel.utils.MutableAnnotations;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.utils.collections.Pair;
import org.globsframework.utils.exceptions.InvalidParameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class DefaultLinkBuilder<T extends MutableGlobLinkModel.LinkBuilder> implements MutableGlobLinkModel.LinkBuilder<T>, MutableAnnotations<T> {
   private final String modelName;
   private final String name;
   private final List<Pair<Field, Field>> mappings = new ArrayList<>();
   private final DefaultAnnotations<T> annotations;
   private GlobType sourceType;
   private GlobType targetType;

   public DefaultLinkBuilder(String modelName, String name, Annotations annotations) {
      this.modelName = modelName;
      this.name = name;
      this.annotations = new DefaultAnnotations<T>(annotations);
   }

   public String getModelName() {
      return modelName;
   }

   public String getName() {
      return name;
   }

   public T addAnnotation(Glob annotation) {
      annotations.addAnnotation(annotation);
      return getT();
   }

   public Collection<Glob> listAnnotations() {
      return Collections.unmodifiableCollection(annotations.listAnnotations());
   }

   public Glob findAnnotation(Key key) {
      return annotations.findAnnotation(key);
   }

   public Glob getAnnotation(Key key) {
      return annotations.getAnnotation(key);
   }

   public boolean hasAnnotation(Key key) {
      return annotations.hasAnnotation(key);
   }

   abstract T getT();

   public T add(Field sourceField, Field targetField) {
      if (sourceField == null) {
         throw new IllegalArgumentException("Source field for link " + name + " must not be null (circular reference " +
                                            "in static initialisation)");
      }
      if (targetField == null) {
         throw new InvalidParameter("Target field for link " + name + " must not be null (circular reference " +
                                    "in static initialisation)");
      }
      if (sourceType == null) {
         sourceType = sourceField.getGlobType();
      }
      else if (!sourceField.getGlobType().equals(sourceType)) {
         throw new InvalidParameter("Source field '" + sourceField + "' is not a field of type " +
                                    sourceType);
      }

      GlobType targetFieldType = targetField.getGlobType();
      if (targetType == null) {
         targetType = targetFieldType;
      }
      else if (!targetType.equals(targetFieldType)) {
         throw new InvalidParameter(
            "Two different target types found for link '" + name + "' of type '" + sourceType.getName() +
            "' (" + targetType.getName() + " and " + targetFieldType.getName() + ")");
      }
      mappings.add(Pair.makePair(sourceField, targetField));
      return getT();
   }

   public DirectLink asDirectLink() {
      if (mappings.size() == 0) {
         throw new RuntimeException("No mapping defined for link " + modelName + " " + name);
      } else {
         return DefaultDirectLink.create(mappings, modelName, name, annotations);
      }
   }

   public Link asLink() {
      return asDirectLink();
   }
}
