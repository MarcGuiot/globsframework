package org.globsframework.utils.serialization;

import org.globsframework.utils.LimitedByteArrayInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SerializedInputOutputFactory {
    private static final boolean checked = false;

    static public SerializedOutput init(OutputStream outputStream) {
        DefaultSerializationOutput serializationOutput = new DefaultSerializationOutput(outputStream);
        if (checked) {
            return new SerializedOutputChecker(serializationOutput);
        }
        return serializationOutput;
    }

    static public SerializedInput init(InputStream inputStream) {
        DefaultSerializationInput serializationInput = new DefaultSerializationInput(inputStream);
        if (checked) {
            return new SerializationInputChecker(serializationInput);
        }
        return serializationInput;
    }

    public static SerializedInput init(byte[] bytes) {
        return new ByteBufferSerializationInput(bytes);
    }

    public static SerializedOutput initCompressed(OutputStream outputStream) {
        return new CompressedSerializationOutput(outputStream);
    }

    public static SerializedInput initCompressedAndIntern(byte[] bytes, int size) {
        return new CompressedSerializationInput(new LimitedByteArrayInputStream(bytes, size));
    }

}
