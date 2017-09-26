package org.globsframework.metamodel;

import org.globsframework.metamodel.annotations.KeyAnnotationType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.Glob;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public interface GlobTypeBuilder {
  GlobTypeBuilder addAnnotation(Glob annotation);

   GlobTypeBuilder addStringField(String fieldName, Collection<Glob> annotations);

  GlobTypeBuilder addIntegerField(String fieldName, Collection<Glob> globAnnotations);

  GlobTypeBuilder addDoubleField(String fieldName, Collection<Glob> globAnnotations);

  GlobTypeBuilder addLongField(String fieldName, Collection<Glob> globAnnotations);

  GlobTypeBuilder addBooleanField(String fieldName, Collection<Glob> globAnnotations);

  GlobTypeBuilder addBlobField(String fieldName, Collection<Glob> globAnnotations);

  StringField declareStringField(String fieldName, Collection<Glob> annotations);

  IntegerField declareIntegerField(String fieldName, Collection<Glob> annotations);

  BooleanField declareBooleanField(String fieldName, Collection<Glob> annotations);

  DoubleField declareDoubleField(String fieldName, Collection<Glob> annotations);

  LongField declareLongField(String fieldName, Collection<Glob> annotations);

  BlobField declareBlobField(String fieldName, Collection<Glob> annotations);

   default GlobTypeBuilder addStringField(String fieldName, Glob... annotations){
      return addStringField(fieldName, Arrays.asList(annotations));
   }

   default GlobTypeBuilder addIntegerField(String fieldName, Glob... annotations) {
      return addIntegerField(fieldName, Arrays.asList(annotations));
   }

  default GlobTypeBuilder addDoubleField(String fieldName, Glob... annotations){
    return addDoubleField(fieldName, Arrays.asList(annotations));
  }

  default GlobTypeBuilder addLongField(String fieldName, Glob... annotations){
    return addLongField(fieldName, Arrays.asList(annotations));
  }

  default GlobTypeBuilder addBooleanField(String fieldName, Glob... annotations){
    return addBooleanField(fieldName, Arrays.asList(annotations));
  }

  default GlobTypeBuilder addBlobField(String fieldName, Glob... annotations){
    return addBlobField(fieldName, Arrays.asList(annotations));
  }

  default StringField declareStringField(String fieldName, Glob... annotations){
    return declareStringField(fieldName, Arrays.asList(annotations));
  }

  default IntegerField declareIntegerField(String fieldName, Glob... annotations){
    return declareIntegerField(fieldName, Arrays.asList(annotations));
  }

  default BooleanField declareBooleanField(String fieldName, Glob... annotations){
    return declareBooleanField(fieldName, Arrays.asList(annotations));
  }

  default DoubleField declareDoubleField(String fieldName, Glob... annotations){
    return declareDoubleField(fieldName, Arrays.asList(annotations));
  }

  default LongField declareLongField(String fieldName, Glob... annotations) {
    return declareLongField(fieldName, Arrays.asList(annotations));
  }

  default BlobField declareBlobField(String fieldName, Glob... annotations){
    return declareBlobField(fieldName, Arrays.asList(annotations));
  }

   <T> void register(Class<T> klass, T t);

   GlobType get();

   default GlobTypeBuilder addIntegerKey(String fieldName) {
      addIntegerField(fieldName, KeyAnnotationType.UNINITIALIZED);
      return this;
   }

   boolean isKnown(String fieldName);
}
