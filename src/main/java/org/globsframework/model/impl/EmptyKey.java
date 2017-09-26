package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.FieldValue;
import org.globsframework.model.FieldValues;
import org.globsframework.model.Key;
import org.globsframework.utils.exceptions.ItemNotFound;

public class EmptyKey implements Key {
   private final GlobType type;

   public EmptyKey(GlobType type) {
      this.type = type;
   }

   public GlobType getGlobType() {
      return type;
   }

   public boolean isNull(Field field) throws ItemNotFound {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public Object getValue(Field field) throws ItemNotFound {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public Double get(DoubleField field) throws ItemNotFound {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public double get(DoubleField field, double valueIfNull) throws ItemNotFound {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public Integer get(IntegerField field) throws ItemNotFound {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public int get(IntegerField field, int valueIfNull) throws ItemNotFound {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public String get(StringField field) throws ItemNotFound {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public Boolean get(BooleanField field) throws ItemNotFound {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public Boolean get(BooleanField field, boolean defaultIfNull) {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public boolean isTrue(BooleanField field) throws ItemNotFound {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public Long get(LongField field) throws ItemNotFound {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public long get(LongField field, long valueIfNull) throws ItemNotFound {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public byte[] get(BlobField field) throws ItemNotFound {
      throw new RuntimeException("Empty key '" + field.getFullName() + " not available");
   }

   public boolean contains(Field field) {
      return false;
   }

   public int size() {
      return 0;
   }

   public void applyOnKeyField(FieldValues.Functor functor) throws Exception {
   }

   public void safeApplyOnKeyField(FieldValues.Functor functor) {
   }

   public boolean containsKey(Field field) {
      return false;
   }

   public FieldValues asFieldValues() {
      return FieldValues.EMPTY;
   }

   public FieldValue[] toArray() {
      return new FieldValue[0];
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      EmptyKey key = (EmptyKey)o;

      if (type != null ? !type.equals(key.type) : key.type != null) {
         return false;
      }

      return true;
   }

   public int hashCode() {
      return type != null ? type.hashCode() : 0;
   }

   public String toString() {
      return "EmptyKey/" + type.getName();
   }
}
