package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.GlobFactory;
import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.GlobSetAccessor;
import org.globsframework.model.globaccessor.GlobSetDoubleAccessor;
import org.globsframework.model.globaccessor.GlobSetIntAccessor;
import org.globsframework.model.impl.DefaultGlob;

class DefaultGlobFactory implements GlobFactory {
   private GlobType type;

   public DefaultGlobFactory(GlobType type) {
      this.type = type;
   }

   public MutableGlob create() {
      return new DefaultGlob(type);
   }

   public GlobSetAccessor get(Field field) {
      return null;
   }

   public GlobSetIntAccessor get(IntegerField field) {
      return null;
   }

   public GlobSetDoubleAccessor get(DoubleField field) {
      return null;
   }

   public GlobSetIntAccessor adapt(Field field) {
      field.safeVisit(new FieldVisitor() {
         public void visitInteger(IntegerField field) throws Exception {

         }

         public void visitDouble(DoubleField field) throws Exception {

         }

         public void visitString(StringField field) throws Exception {

         }

         public void visitBoolean(BooleanField field) throws Exception {

         }

         public void visitLong(LongField field) throws Exception {

         }

         public void visitBlob(BlobField field) throws Exception {

         }
      });
      return null;
   }
}
