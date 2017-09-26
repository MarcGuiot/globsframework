package org.globsframework.utils.serialization;

import org.globsframework.model.ChangeSet;
import org.globsframework.model.Glob;

import java.util.Date;

public interface SerializedOutput {
   void writeDate(Date date);

   void write(int value);

   void writeInteger(Integer value);

   void write(double value);

   void writeDouble(Double value);

   void write(boolean value);

   void writeBoolean(Boolean value);

   void write(long value);

   void writeLong(Long value);

   void writeByte(int value);

   void writeByte(byte value);

   void writeBytes(byte[] value);

   void writeUtf8String(String value);

   void writeGlob(Glob glob);

   void writeChangeSet(ChangeSet changeSet);

   void write(int[] array);

   void write(long[] array);
}
