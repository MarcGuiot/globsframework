package org.globsframework.utils.serialization;

import org.globsframework.model.ChangeSet;
import org.globsframework.model.Glob;

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

    public void writeChangeSet(ChangeSet changeSet) {
        serializationOutput.writeUtf8String("ChangeSet");
        serializationOutput.writeChangeSet(changeSet);
    }

    public void write(int[] array) {
        serializationOutput.writeUtf8String("int array");
        serializationOutput.write(array);
    }

    public void write(long[] array) {
        serializationOutput.writeUtf8String("long array");
        serializationOutput.write(array);
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
}
