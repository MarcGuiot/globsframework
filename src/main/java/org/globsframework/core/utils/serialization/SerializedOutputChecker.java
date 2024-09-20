package org.globsframework.core.utils.serialization;

import org.globsframework.core.model.ChangeSet;
import org.globsframework.core.model.Glob;

import java.math.BigDecimal;
import java.util.Date;

class SerializedOutputChecker implements SerializedOutput {
    private final DefaultSerializationOutput serializationOutput;

    public SerializedOutputChecker(DefaultSerializationOutput serializationOutput) {
        this.serializationOutput = serializationOutput;
    }

    public void writeGlob(Glob glob) {
        serializationOutput.writeUtf8String("Glob");
        serializationOutput.writeGlob(glob);
    }

    public void writeKnowGlob(Glob glob) {
        serializationOutput.writeUtf8String("KnowGlob");
        serializationOutput.writeKnowGlob(glob);
    }

    public void writeChangeSet(ChangeSet changeSet) {
        serializationOutput.writeUtf8String("ChangeSet");
        serializationOutput.writeChangeSet(changeSet);
    }

    public void write(int[] values) {
        serializationOutput.writeUtf8String("int[]");
        serializationOutput.write(values);
    }

    public void write(long[] values) {
        serializationOutput.writeUtf8String("long[]");
        serializationOutput.write(values);
    }

    public void writeDate(Date date) {
        serializationOutput.writeUtf8String("Date");
        serializationOutput.writeDate(date);
    }

    public void write(int value) {
        serializationOutput.writeUtf8String("int");
        serializationOutput.write(value);
    }

    public void writeInteger(Integer value) {
        serializationOutput.writeUtf8String("Integer");
        serializationOutput.writeInteger(value);
    }

    public void write(double value) {
        serializationOutput.writeUtf8String("double");
        serializationOutput.write(value);
    }

    public void writeDouble(Double value) {
        serializationOutput.writeUtf8String("Double");
        serializationOutput.writeDouble(value);
    }

    public void writeUtf8String(String value) {
        serializationOutput.writeUtf8String("StringUtf8");
        serializationOutput.writeUtf8String(value);
    }

    public void write(boolean value) {
        serializationOutput.writeUtf8String("Boolean");
        serializationOutput.write(value);
    }

    public void writeBoolean(Boolean value) {
        serializationOutput.writeUtf8String("Boolean");
        serializationOutput.writeBoolean(value);
    }

    public void write(long value) {
        serializationOutput.writeUtf8String("long");
        serializationOutput.write(value);
    }

    public void writeLong(Long value) {
        serializationOutput.writeUtf8String("Long");
        serializationOutput.writeLong(value);
    }

    public void writeByte(int value) {
        serializationOutput.writeUtf8String("byte");
        serializationOutput.writeByte(value);
    }

    public void writeByte(byte value) {
        serializationOutput.writeUtf8String("byte");
        serializationOutput.writeByte(value);
    }

    public void writeBytes(byte[] value) {
        serializationOutput.writeUtf8String("Bytes");
        serializationOutput.writeBytes(value);
    }

    public void write(double[] values) {
        serializationOutput.writeUtf8String("double[]");
        serializationOutput.write(values);
    }

    public void write(String[] values) {
        serializationOutput.writeUtf8String("String[]");
        serializationOutput.write(values);
    }

    public void write(boolean[] values) {
        serializationOutput.writeUtf8String("boolean[]");
        serializationOutput.write(values);
    }

    public void write(BigDecimal[] values) {
        serializationOutput.writeUtf8String("BigDecimal[]");
        serializationOutput.write(values);
    }
}
