package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeBuilder;
import org.globsframework.metamodel.annotations.*;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.fields.impl.*;
import org.globsframework.metamodel.Annotations;
import org.globsframework.metamodel.utils.MutableAnnotations;
import org.globsframework.model.Glob;
import org.globsframework.utils.exceptions.InvalidState;

import java.util.Arrays;
import java.util.Collection;

public class DefaultGlobTypeBuilder implements GlobTypeBuilder {
   private DefaultGlobType type;
   private DefaultFieldFactory factory;
   private int index;
   private int keyIndex;
   private Boolean isKeyFromGlob;

   public static GlobTypeBuilder init(String typeName) {
      return new DefaultGlobTypeBuilder(typeName);
   }

   public DefaultGlobTypeBuilder(String typeName) {
      type = new DefaultGlobType(typeName);
      factory = new DefaultFieldFactory(type);
   }

   public GlobTypeBuilder addIntegerKey(String fieldName, Glob... globAnnotations) {
      return this;
   }

   private MutableAnnotations adaptAnnotation(Collection<Glob> annotations) {
      DefaultAnnotations defaultAnnotations = new DefaultAnnotations();
      for (Glob annotation : annotations) {
         defaultAnnotations.addAnnotation(annotation);
      }
      return defaultAnnotations;
   }

  public GlobTypeBuilder addAnnotation(Glob annotation) {
    type.addAnnotation(annotation);
    return this;
  }

  public GlobTypeBuilder addStringField(String fieldName, Collection<Glob> annotations) {
    createStringField(fieldName, annotations);
    return this;
  }

   private DefaultStringField createStringField(String fieldName, Collection<Glob> globAnnotations) {
      MutableAnnotations annotations = adaptAnnotation(globAnnotations);
      String defaultValue = annotations.getValueOrDefault(DefaultStringAnnotationType.UNIQUE_KEY,
                                                          DefaultStringAnnotationType.DEFAULT_VALUE, null);
      boolean isKey = annotations.hasAnnotation(KeyAnnotationType.UNIQUE_KEY);
      DefaultStringField field = factory.addString(fieldName, isKey, keyIndex, index, defaultValue);
      if (isKey) {
         updateKeyIndex(annotations);
      }
      field.addAll(annotations);
      index++;
      return field;
   }

   public GlobTypeBuilder addIntegerField(String fieldName, Collection<Glob> globAnnotations) {
      createIntegerField(fieldName, globAnnotations);
      return this;
   }

   private DefaultIntegerField createIntegerField(String fieldName, Collection<Glob> globAnnotations) {
      DefaultIntegerField field;
      MutableAnnotations annotations = adaptAnnotation(globAnnotations);
      Integer defaultValue = annotations.getValueOrDefault(DefaultIntegerAnnotationType.UNIQUE_KEY, DefaultIntegerAnnotationType.DEFAULT_VALUE, null);
      boolean isKey = annotations.hasAnnotation(KeyAnnotationType.UNIQUE_KEY);
      field = factory.addInteger(fieldName, isKey, keyIndex, index, defaultValue);
      if (isKey) {
         updateKeyIndex(annotations);
      }
      field.addAll(annotations);
      index++;
      return field;
   }

   public GlobTypeBuilder addDoubleField(String fieldName, Collection<Glob> globAnnotations) {
      createDoubleField(fieldName, globAnnotations);
      return this;
   }

   private DefaultDoubleField createDoubleField(String fieldName, Collection<Glob> globAnnotations) {
      MutableAnnotations annotations = adaptAnnotation(globAnnotations);
      Double defaultValue = annotations.getValueOrDefault(DefaultDoubleAnnotationType.UNIQUE_KEY, DefaultDoubleAnnotationType.DEFAULT_VALUE, null);
      boolean isKey = annotations.hasAnnotation(KeyAnnotationType.UNIQUE_KEY);
      DefaultDoubleField doubleField = factory.addDouble(fieldName, isKey, keyIndex, index, defaultValue);
      if (isKey) {
         updateKeyIndex(annotations);
      }
      doubleField.addAll(annotations);
      index++;
      return doubleField;
   }

   public GlobTypeBuilder addLongField(String fieldName, Collection<Glob> globAnnotations) {
      createLongField(fieldName, globAnnotations);
      return this;
   }

   private DefaultLongField createLongField(String fieldName, Collection<Glob> globAnnotations) {
      MutableAnnotations annotations = adaptAnnotation(globAnnotations);
      Long defaultValue = annotations.getValueOrDefault(DefaultLongAnnotationType.UNIQUE_KEY,
                                                        DefaultLongAnnotationType.DEFAULT_VALUE, null);
      boolean isKey = annotations.hasAnnotation(KeyAnnotationType.UNIQUE_KEY);
      DefaultLongField longField = factory.addLong(fieldName, isKey, keyIndex, index, defaultValue);
      if (isKey) {
         updateKeyIndex(annotations);
      }
      longField.addAll(annotations);
      index++;
      return longField;
   }

   public GlobTypeBuilder addBooleanField(String fieldName, Collection<Glob> globAnnotations) {
      createBooleanField(fieldName, globAnnotations);
      return this;
   }

   private DefaultBooleanField createBooleanField(String fieldName, Collection<Glob> globAnnotations) {
      MutableAnnotations annotations = adaptAnnotation(globAnnotations);
      Boolean defaultValue = annotations.getValueOrDefault(DefaultBooleanAnnotationType.UNIQUE_KEY,
                                                           DefaultBooleanAnnotationType.DEFAULT_VALUE, null);
      boolean isKey = annotations.hasAnnotation(KeyAnnotationType.UNIQUE_KEY);
      DefaultBooleanField field = factory.addBoolean(fieldName, isKey, keyIndex, index, defaultValue);
      if (isKey) {
         updateKeyIndex(annotations);
      }
      field.addAll(annotations);
      index++;
      return field;
   }

   private void updateKeyIndex(MutableAnnotations annotations) {
      Glob annotation = annotations.getAnnotation(KeyAnnotationType.UNIQUE_KEY);
      int tmpKeyIndex = -1;
      if (annotation != null){
         tmpKeyIndex = annotation.get(KeyAnnotationType.INDEX, -1);
      }
      if (tmpKeyIndex == -1) {
         if (isKeyFromGlob == null){
            isKeyFromGlob = false;
         } else if (isKeyFromGlob){
            throw new InvalidState("Forbidden to mix keyIndex from annotation and default");
         }
         annotations.addAnnotation(KeyAnnotationType.create(keyIndex));
         keyIndex++;
      }
      else {
         keyIndex = tmpKeyIndex;
         if (isKeyFromGlob == null){
            isKeyFromGlob = true;
         }else {
            if (!isKeyFromGlob){
               throw new InvalidState("Forbidden to mix keyIndex from annotation and default");
            }
         }
      }
   }

   public GlobTypeBuilder addBlobField(String fieldName, Collection<Glob> globAnnotations) {
      createBlobField(fieldName, globAnnotations);
      return this;
   }

   private DefaultBlobField createBlobField(String fieldName, Collection<Glob> globAnnotations) {
      Annotations annotations = adaptAnnotation(globAnnotations);
      DefaultBlobField field = factory.addBlob(fieldName, index);
      field.addAll(annotations);
      index++;
      return field;
   }

   public StringField declareStringField(String fieldName, Collection<Glob> globAnnotations) {
      return createStringField(fieldName, globAnnotations);
   }

   public IntegerField declareIntegerField(String fieldName, Collection<Glob> annotations) {
      return createIntegerField(fieldName, annotations);
   }

   public DoubleField declareDoubleField(String fieldName, Collection<Glob> annotations) {
      return createDoubleField(fieldName, annotations);
   }

   public BooleanField declareBooleanField(String fieldName, Collection<Glob> annotations) {
      return createBooleanField(fieldName, annotations);
   }

   public LongField declareLongField(String fieldName, Collection<Glob> annotations) {
      return createLongField(fieldName, annotations);
   }

   public BlobField declareBlobField(String fieldName, Collection<Glob> annotations) {
      return createBlobField(fieldName, annotations);
   }

   public <T> void register(Class<T> klass, T t) {
      type.register(klass, t);
   }

   public GlobType get() {
      type.completeInit();
      return type;
   }

  public boolean isKnown(String fieldName) {
    return type.hasField(fieldName);
  }
}
