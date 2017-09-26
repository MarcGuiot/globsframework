package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.FieldValueVisitor;
import org.globsframework.metamodel.fields.FieldVisitor;
import org.globsframework.metamodel.fields.LongField;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

public class DefaultLongField extends AbstractField implements LongField {
   public DefaultLongField(String name, GlobType globType, int index, boolean isKeyField, int keyIndex, Long defaultValue) {
      super(name, globType, Long.class, index, keyIndex, isKeyField, defaultValue, DataType.Long);
   }

   public <T extends FieldVisitor> T visit(T visitor) throws Exception {
      visitor.visitLong(this);
      return visitor;
   }

   public <T extends FieldVisitor>  T safeVisit(T visitor) {
      try {
         visitor.visitLong(this);
         return visitor;
      }
      catch (RuntimeException e) {
         throw new RuntimeException("On " + this, e);
      }
      catch (Exception e) {
         throw new UnexpectedApplicationState("On " + this, e);
      }
   }

   public void safeVisit(FieldValueVisitor visitor, Object value) {
      try {
         visitor.visitLong(this, (Long)value);
      }
      catch (RuntimeException e) {
         throw new RuntimeException("On " + this, e);
      }
      catch (Exception e) {
         throw new UnexpectedApplicationState("On " + this, e);
      }
   }
}
