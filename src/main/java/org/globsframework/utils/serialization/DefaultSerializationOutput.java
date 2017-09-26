package org.globsframework.utils.serialization;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.*;
import org.globsframework.utils.exceptions.InvalidFormat;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class DefaultSerializationOutput implements SerializedOutput, ChangeSetVisitor {
   private OutputStream outputStream;
   private FieldValues.Functor fieldValuesFunctor = new FieldValuesFunctor();
   private FieldValuesWithPrevious.Functor fieldValuesWithPreviousFunctor = new FieldValuesWithPreviousFunctor();

   DefaultSerializationOutput(OutputStream outputStream) {
      this.outputStream = outputStream;
   }

   public void writeGlob(Glob glob) {
      writeUtf8String(glob.getType().getName());
      for (Field field : glob.getType().getFields()) {
         field.safeVisit(new OutputStreamFieldVisitor(glob));
      }
   }

   public void writeChangeSet(ChangeSet changeSet) {
      writeInteger(changeSet.getChangeCount());
      changeSet.safeVisit(this);
   }

   public void write(int[] array) {
      write(array.length);
      for (int value : array) {
         write(value);
      }
   }

   public void write(long[] array) {
      write(array.length);
      for (long value : array) {
         write(value);
      }
   }

   public void write(int value) {
      try {
         outputStream.write((byte)(value & 0xFF));
         value >>= 8;
         outputStream.write((byte)(value & 0xFF));
         value >>= 8;
         outputStream.write((byte)(value & 0xFF));
         value >>= 8;
         outputStream.write((byte)(value & 0xFF));
         value >>= 8;
      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void writeInteger(Integer value) {
      if (value == null) {
         writeByte(1);
      }
      else {
         writeByte(0);
         write(value);
      }
   }

   public void write(long value) {
      try {
         outputStream.write((byte)(value & 0xFF));
         value >>= 8;
         outputStream.write((byte)(value & 0xFF));
         value >>= 8;
         outputStream.write((byte)(value & 0xFF));
         value >>= 8;
         outputStream.write((byte)(value & 0xFF));
         value >>= 8;
         outputStream.write((byte)(value & 0xFF));
         value >>= 8;
         outputStream.write((byte)(value & 0xFF));
         value >>= 8;
         outputStream.write((byte)(value & 0xFF));
         value >>= 8;
         outputStream.write((byte)(value & 0xFF));
      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void writeLong(Long value) {
      if (value == null) {
         writeByte(1);
      }
      else {
         writeByte(0);
         write(value);
      }
   }

   public void write(double value) {
      write(Double.doubleToLongBits(value));
   }

   public void writeDouble(Double value) {
      if (value == null) {
         writeByte(1);
      }
      else {
         writeByte(0);
         write(Double.doubleToLongBits(value));
      }
   }

   public void writeDate(Date date) {
      if (date == null) {
         write(-1L);
      }
      else {
         write(date.getTime());
      }
   }

   public void writeUtf8String(String value) {
      if (value == null) {
         writeBytes(null);
      }
      else {
         try {
            writeBytes(value.getBytes("UTF-8"));
         }
         catch (UnsupportedEncodingException e) {
            throw new InvalidFormat(e);
         }
      }
   }

   public void write(boolean value) {
      writeByte(value ? 1 : 0);
   }

   public void writeBoolean(Boolean value) {
      if (value == null) {
         writeByte(2);
      }
      else {
         writeByte(value ? 1 : 0);
      }
   }

   public void writeByte(int value) {
      try {
         outputStream.write(value);
      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void writeByte(byte value) {
      try {
         outputStream.write(value);
      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void writeBytes(byte[] value) {
      try {
         if (value == null) {
            int value1 = -1;
            write(value1);
            return;
         }
         write(value.length);
         outputStream.write(value);
      }
      catch (IOException e) {
         throw new UnexpectedApplicationState(e);
      }
   }

   public void visitCreation(Key key, FieldValues values) throws Exception {
      writeUtf8String(key.getGlobType().getName());
      writeValues(key.asFieldValues());
      writeByte(1);
      writeValues(values);
   }

   public void visitUpdate(Key key, FieldValuesWithPrevious values) throws Exception {
      writeUtf8String(key.getGlobType().getName());
      writeValues(key.asFieldValues());
      writeByte(2);
      writeValues(values);
   }

   public void visitDeletion(Key key, FieldValues values) throws Exception {
      writeUtf8String(key.getGlobType().getName());
      writeValues(key.asFieldValues());
      writeByte(3);
      writeValues(values);
   }

   private void writeValues(FieldValues values) {
      write(values.size());
      values.safeApply(fieldValuesFunctor);
   }

   private class FieldValuesFunctor implements FieldValues.Functor, FieldValueVisitor {
      public void process(Field field, Object value) throws Exception {
         write(field.getIndex());
         field.safeVisit(this, value);
      }

      public void visitInteger(IntegerField field, Integer value) throws Exception {
         writeInteger(value);
      }

      public void visitDouble(DoubleField field, Double value) throws Exception {
         writeDouble(value);
      }

      public void visitString(StringField field, String value) throws Exception {
         writeUtf8String(value);
      }

      public void visitBoolean(BooleanField field, Boolean value) throws Exception {
         writeBoolean(value);
      }

      public void visitBlob(BlobField field, byte[] value) throws Exception {
         writeBytes(value);
      }

      public void visitLong(LongField field, Long value) throws Exception {
         writeLong(value);
      }

   }

   private void writeValues(FieldValuesWithPrevious values) {
      write(values.size());
      values.safeApply(fieldValuesWithPreviousFunctor);
   }

   public class FieldValuesWithPreviousFunctor implements FieldValuesWithPrevious.Functor, FieldValueVisitor {

      public void process(Field field, Object value, Object previousValue) throws Exception {
         write(field.getIndex());
         field.safeVisit(this, value);
         field.safeVisit(this, previousValue);
      }

      public void visitInteger(IntegerField field, Integer value) throws Exception {
         writeInteger(value);
      }

      public void visitDouble(DoubleField field, Double value) throws Exception {
         writeDouble(value);
      }

      public void visitString(StringField field, String value) throws Exception {
         writeUtf8String(value);
      }

      public void visitBoolean(BooleanField field, Boolean value) throws Exception {
         writeBoolean(value);
      }

      public void visitBlob(BlobField field, byte[] value) throws Exception {
         writeBytes(value);
      }

      public void visitLong(LongField field, Long value) throws Exception {
         writeLong(value);
      }

   }

   private class OutputStreamFieldVisitor implements FieldVisitor {
      private Glob glob;

      public OutputStreamFieldVisitor(Glob glob) {
         this.glob = glob;
      }

      public void visitInteger(IntegerField field) throws Exception {
         writeInteger(glob.get(field));
      }

      public void visitDouble(DoubleField field) throws Exception {
         writeDouble(glob.get(field));
      }

      public void visitString(StringField field) throws Exception {
         writeUtf8String(glob.get(field));
      }

      public void visitBoolean(BooleanField field) throws Exception {
         writeBoolean(glob.get(field));
      }

      public void visitBlob(BlobField field) throws Exception {
         writeBytes(glob.get(field));
      }

      public void visitLong(LongField field) throws Exception {
         writeLong(glob.get(field));
      }

   }
}
