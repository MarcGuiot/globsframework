package org.globsframework.utils.serialization;

import org.globsframework.metamodel.GlobModel;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.ChangeSet;
import org.globsframework.model.Glob;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public interface SerializedInput {

    ZonedDateTime readDateTime();

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

    Glob readKnowGlob(GlobType type);

    ChangeSet readChangeSet(GlobModel model);

    int[] readIntArray();

    long[] readLongArray();

    double[] readDoubleArray();

    boolean[] readBooleanArray();

    BigDecimal[] readBigDecimaleArray();

    String[] readStringArray();

    void close();

    default String readString(){
        return readUtf8String();
    }

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
