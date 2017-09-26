package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.utils.exceptions.ItemNotFound;

public interface FieldSetter<T extends FieldSetter> {
   T set(DoubleField field, Double value) throws ItemNotFound;

   T set(DoubleField field, double value) throws ItemNotFound;

   T set(IntegerField field, Integer value) throws ItemNotFound;

   T set(IntegerField field, int value) throws ItemNotFound;

   T set(StringField field, String value) throws ItemNotFound;

   T set(BooleanField field, Boolean value) throws ItemNotFound;

   T set(LongField field, Long value) throws ItemNotFound;

   T set(LongField field, long value) throws ItemNotFound;

   T set(BlobField field, byte[] value) throws ItemNotFound;

   T setValue(Field field, Object value) throws ItemNotFound;
}
