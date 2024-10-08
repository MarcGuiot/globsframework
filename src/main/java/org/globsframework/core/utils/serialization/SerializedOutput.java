package org.globsframework.core.utils.serialization;

import org.globsframework.core.model.ChangeSet;
import org.globsframework.core.model.Glob;

import java.math.BigDecimal;
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

    void writeKnowGlob(Glob glob);

    void writeChangeSet(ChangeSet changeSet);

    void write(int[] values);

    void write(long[] values);

    void write(double[] values);

    void write(String[] values);

    void write(boolean[] values);

    void write(BigDecimal[] values);

    default void writeString(String s) {
        writeUtf8String(s);
    }
}
