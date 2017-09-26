package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.FieldValueVisitor;
import org.globsframework.metamodel.fields.FieldVisitor;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

public class DefaultIntegerField extends AbstractField implements IntegerField {
   public DefaultIntegerField(String name, GlobType globType, int index, boolean isKeyField, int keyIndex, Integer defaultValue) {
      super(name, globType, Integer.class, index, keyIndex, isKeyField, defaultValue, DataType.Integer);
   }

   public <T extends FieldVisitor> T visit(T visitor) throws Exception {
      visitor.visitInteger(this);
      return visitor;
   }

   public <T extends FieldVisitor>  T safeVisit(T visitor) {
      try {
         visitor.visitInteger(this);
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
         visitor.visitInteger(this, (Integer)value);
      }
      catch (RuntimeException e) {
         throw new RuntimeException("On " + this, e);
      }
      catch (Exception e) {
         throw new UnexpectedApplicationState("On " + this, e);
      }
   }
}
