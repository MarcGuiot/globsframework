package org.globsframework.utils.serialization;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class CompressedSerializationOutput extends DefaultSerializationOutput {
    private final Map<String, Integer> strings = new HashMap<String, Integer>();
    private int count = 0;

    public CompressedSerializationOutput(OutputStream outputStream) {
        super(outputStream);
    }

    public void writeUtf8String(String value) {
        if (value == null) {
            write(0);
            return;
        }
        Integer id = strings.get(value);
        if (id != null) {
            write(id);
        } else {
            count++;
            strings.put(value, count);
            write(-count);
            super.writeUtf8String(value);
        }
    }
}
