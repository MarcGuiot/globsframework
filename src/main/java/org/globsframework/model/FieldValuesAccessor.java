package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.utils.exceptions.ItemNotFound;

public interface FieldValuesAccessor {

   boolean isNull(Field field) throws ItemNotFound;

   Object getValue(Field field) throws ItemNotFound;

   Double get(DoubleField field) throws ItemNotFound;

   double get(DoubleField field, double valueIfNull) throws ItemNotFound;

   Integer get(IntegerField field) throws ItemNotFound;

   int get(IntegerField field, int valueIfNull) throws ItemNotFound;

   String get(StringField field) throws ItemNotFound;

   Boolean get(BooleanField field) throws ItemNotFound;

   Boolean get(BooleanField field, boolean defaultIfNull);

   boolean isTrue(BooleanField field) throws ItemNotFound;

   Long get(LongField field) throws ItemNotFound;

   long get(LongField field, long valueIfNull) throws ItemNotFound;

   byte[] get(BlobField field) throws ItemNotFound;

   default String get(StringField field, String valueIfNull) throws ItemNotFound{
      String value = get(field);
      return value != null ? value : valueIfNull;
   }

}
