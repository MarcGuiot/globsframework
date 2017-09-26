package org.globsframework.utils.serialization;

import org.globsframework.metamodel.GlobModel;
import org.globsframework.model.ChangeSet;
import org.globsframework.model.Glob;

import java.time.ZonedDateTime;

public interface SerializedInput {

   ZonedDateTime readDate();

   Integer readInteger();

   int readNotNullInt();

   Double readDouble();

   double readNotNullDouble();

   String readUtf8String();

   Boolean readBoolean();

   Long readLong();

   long readNotNullLong();

   byte readByte();

   byte[] readBytes();

   Glob readGlob(GlobModel model);

   ChangeSet readChangeSet(GlobModel model);

   int[] readIntArray();

   long[] readLongArray();

   void close();

//  interface FieldExtension {
//    <T> T read(SerializedInput input);
//  }
//
//  interface AsyncFieldExtension {
//    interface Receive<T> {
//       void readed(T value);
//    }
//    <T> Receive<T> read(SerializedInput input);
//  }
}
