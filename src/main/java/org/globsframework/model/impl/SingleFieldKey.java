package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.AbstractKey;
import org.globsframework.model.FieldValue;
import org.globsframework.model.FieldValues;
import org.globsframework.model.Key;
import org.globsframework.utils.Utils;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.MissingInfo;

import java.util.Arrays;

public class SingleFieldKey extends AbstractKey {
   private final Object value;
   private final Field keyField;
   private final int hashCode;

   public SingleFieldKey(Field field, Object value) throws MissingInfo {
      checkValue(field, value);
      this.keyField = field;
      this.keyField.checkValue(value);
      this.value = value;
      hashCode = computeHash();
   }

   static void checkValue(Field field, Object value) throws MissingInfo {
//    if (value == null) {
//      throw new MissingInfo("Field '" + field.getName() +
//                            "' missing (should not be NULL) for identifying a Glob of type: " + field.getGlobType().getName());
//    }
   }

   public SingleFieldKey(GlobType type, Object value) throws InvalidParameter {
      this(getKeyField(type), value);
   }

   private static Field getKeyField(GlobType type) throws InvalidParameter {
      Field[] keyFields = type.getKeyFields();
      if (keyFields.length != 1) {
         throw new InvalidParameter("Cannot use a single field key for type " + type + " - " +
                                    "key fields=" + Arrays.toString(keyFields));
      }
      return keyFields[0];
   }

   public GlobType getGlobType() {
      return keyField.getGlobType();
   }

   public void apply(FieldValues.Functor functor) throws Exception {
      functor.process(keyField, value);
   }

   public void safeApply(FieldValues.Functor functor) {
      try {
         functor.process(keyField, value);
      }
      catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public int size() {
      return 1;
   }

   // optimized - do not use generated code
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null) {
         return false;
      }
      if (o.getClass().equals(SingleFieldKey.class)) {
         SingleFieldKey otherSingleFieldKey = (SingleFieldKey)o;
         return otherSingleFieldKey.keyField == keyField &&
                Utils.equal(otherSingleFieldKey.value, value);
      }

      if (!AbstractKey.class.isAssignableFrom(o.getClass())) {
         return false;
      }
      Key otherKey = (Key)o;
      return keyField.getGlobType() == otherKey.getGlobType()
             && Utils.equal(value, otherKey.getValue(keyField));
   }

   // optimized - do not use generated code
   public int hashCode() {
      return hashCode;
   }

   private int computeHash() {
      int hash = getGlobType().hashCode();
      hash = 31 * hash + (value != null ? value.hashCode() : 0);
      if (hash == 0) {
         hash = 31;
      }
      return hash;
   }

   public FieldValue[] toArray() {
      return new FieldValue[]{
         new FieldValue(keyField, value),
         };
   }

   public String toString() {
      return getGlobType().getName() + "[" + keyField.getName() + "=" + value + "]";
   }

   protected Object getSwitchValue(Field field) {
      if (field.getKeyIndex() == 0){
         return value;
      }
      throw new InvalidParameter(field + " is not a key field");
   }
}
