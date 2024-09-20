package org.globsframework.core.utils.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class SerializedByteArrayOutput {
    protected ByteArrayOutputStream outputStream;
    protected SerializedOutput serializedOutput;

    public SerializedByteArrayOutput() {
        outputStream = new ByteArrayOutputStream();
        serializedOutput = SerializedInputOutputFactory.init(outputStream);
    }

    public SerializedOutput getOutput() {
        return serializedOutput;
    }

    public byte[] toByteArray() {
        return outputStream.toByteArray();
    }

    public int size() {
        return outputStream.size();
    }

    public SerializedInput getInput() {
        return SerializedInputOutputFactory.init(new ByteArrayInputStream(toByteArray()));
    }

    public void reset() {
        outputStream.reset();
    }
}
