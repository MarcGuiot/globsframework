package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.AbstractKey;
import org.globsframework.model.FieldValue;
import org.globsframework.model.Key;
import org.globsframework.utils.Utils;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.MissingInfo;

import java.util.Arrays;

public class ThreeFieldKey extends AbstractKey {
   private final GlobType type;
   private final Object value1;
   private final Object value2;
   private final Object value3;
   private final int hashCode;

   public ThreeFieldKey(Field keyField1, Object value1,
                        Field keyField2, Object value2,
                        Field keyField3, Object value3) throws MissingInfo {
      SingleFieldKey.checkValue(keyField1, value1);
      SingleFieldKey.checkValue(keyField2, value2);
      SingleFieldKey.checkValue(keyField3, value3);

      Field[] keyFields = keyField1.getGlobType().getKeyFields();
      if (keyFields.length != 3) {
         throw new InvalidParameter("Cannot use a three-fields key for type " + keyField1.getGlobType() + " - " +
                                    "key fields=" + Arrays.toString(keyFields));
      }
      type = keyField1.getGlobType();
      Field field;
      field = keyFields[0];
      this.value1 = field == keyField1 ? value1 : field == keyField2 ? value2 : value3;
      field.checkValue(this.value1);

      field = keyFields[1];
      this.value2 = field == keyField2 ? value2 : field == keyField1 ? value1 : value3;
      field.checkValue(this.value2);

      field = keyFields[2];
      this.value3 = field == keyField3 ? value3 : field == keyField2 ? value2 : value1;
      field.checkValue(this.value3);
      hashCode = computeHash();
   }

   public GlobType getGlobType() {
      return type;
   }

   public void apply(Functor functor) throws Exception {
      Field[] fields = type.getFields();
      functor.process(fields[0], value2);
      functor.process(fields[1], value1);
      functor.process(fields[2], value3);
   }

   public void safeApply(Functor functor) {
      try {
         Field[] fields = type.getFields();
         functor.process(fields[0], value1);
         functor.process(fields[1], value2);
         functor.process(fields[2], value3);
      }
      catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public int size() {
      return 3;
   }


   // optimized - do not use generated code
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null) {
         return false;
      }
      if (o.getClass() == ThreeFieldKey.class) {
         ThreeFieldKey otherSingleFieldKey = (ThreeFieldKey)o;
         return
            type == otherSingleFieldKey.getGlobType() &&
            Utils.equal(otherSingleFieldKey.value1, value1) &&
            Utils.equal(otherSingleFieldKey.value2, value2) &&
            Utils.equal(otherSingleFieldKey.value3, value3);
      }

      if (!Key.class.isAssignableFrom(o.getClass())) {
         return false;
      }
      Field[] fields = type.getFields();
      Key otherKey = (Key)o;
      return type == otherKey.getGlobType()
             && Utils.equal(value1, otherKey.getValue(fields[0]))
             && Utils.equal(value2, otherKey.getValue(fields[1]))
             && Utils.equal(value3, otherKey.getValue(fields[2]));
   }

   // optimized - do not use generated code
   public int hashCode() {
      return hashCode;
   }

   private int computeHash() {
      int h = type.hashCode();
      h = 31 * h + (value1 != null ? value1.hashCode() : 0);
      h = 31 * h + (value2 != null ? value2.hashCode() : 0);
      h = 31 * h + (value3 != null ? value3.hashCode() : 0);
      if (h == 0) {
         h = 31;
      }
      return h;
   }

   public FieldValue[] toArray() {
      Field[] fields = type.getFields();
      return new FieldValue[]{
         new FieldValue(fields[0], value1),
         new FieldValue(fields[1], value2),
         new FieldValue(fields[2], value3)
      };
   }

   public String toString() {
      Field[] fields = type.getFields();
      return getGlobType().getName() + "[" +
             fields[0].getName() + "=" + value1 + ", " +
             fields[1].getName() + "=" + value2 + ", " +
             fields[2].getName() + "=" + value3 + "]";
   }

   protected Object getSwitchValue(Field field) {
      switch (field.getKeyIndex()) {
         case 0:
            return value1;
         case 1:
            return value2;
         case 2:
            return value3;
      }
      throw new InvalidParameter(field + " is not a key field");
   }
}