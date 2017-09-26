package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.utils.exceptions.ItemNotFound;

public interface FieldValues extends FieldValuesAccessor {

   boolean contains(Field field);

   int size();

   void apply(Functor functor) throws Exception;

   void safeApply(Functor functor);

   FieldValue[] toArray();

   interface Functor {
      void process(Field field, Object value) throws Exception;
   }

   FieldValues EMPTY = new FieldValues() {
      public boolean contains(Field field) {
         return false;
      }

      public int size() {
         return 0;
      }

      public void apply(Functor functor) throws Exception {
      }

      public void safeApply(Functor functor) {
      }

      public Double get(DoubleField field) throws ItemNotFound {
         throw new ItemNotFound(field.getName());
      }

      public double get(DoubleField field, double valueIfNull) throws ItemNotFound {
         throw new ItemNotFound(field.getName());
      }

      public Integer get(IntegerField field) throws ItemNotFound {
         throw new ItemNotFound(field.getName());
      }

      public int get(IntegerField field, int valueIfNull) throws ItemNotFound {
         throw new ItemNotFound(field.getName());
      }

      public String get(StringField field) throws ItemNotFound {
         throw new ItemNotFound(field.getName());
      }

      public Boolean get(BooleanField field) throws ItemNotFound {
         throw new ItemNotFound(field.getName());
      }

      public Boolean get(BooleanField field, boolean defaultIfNull) {
         throw new ItemNotFound(field.getName());
      }

      public boolean isTrue(BooleanField field) throws ItemNotFound {
         throw new ItemNotFound(field.getName());
      }

      public boolean isNull(Field field) throws ItemNotFound {
         throw new ItemNotFound(field.getName());
      }

      public Object getValue(Field field) throws ItemNotFound {
         throw new ItemNotFound(field.getName());
      }

      public Long get(LongField field) throws ItemNotFound {
         throw new ItemNotFound(field.getName());
      }

      public long get(LongField field, long valueIfNull) throws ItemNotFound {
         throw new ItemNotFound(field.getName());
      }

      public byte[] get(BlobField field) throws ItemNotFound {
         throw new ItemNotFound(field.getName());
      }

      public FieldValue[] toArray() {
         return new FieldValue[0];
      }
   };
}
