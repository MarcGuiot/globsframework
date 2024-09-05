package org.globsframework.utils.serialization;

import java.io.IOException;
import java.io.InputStream;

public class YANBuffereInputStream extends InputStream {
    private final byte[] buffer;
    private int currentPos;
    private int count;
    private final InputStream inputStream;

    public YANBuffereInputStream(InputStream inputStream) {
        this(inputStream, 8 * 1024);
    }

    public YANBuffereInputStream(InputStream inputStream, int size) {
        this.inputStream = inputStream;
        buffer = new byte[size];
        currentPos = 0;
        count = 0;
    }

    public int read() throws IOException {
        if (currentPos >= count) {
            count = inputStream.read(buffer);
            if (count == -1) {
                return -1;
            }
            currentPos = 0;
        }
        return buffer[currentPos++] & 0xFF;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int available = count - currentPos;
        if (available > 0) {
            int length = Math.min(available, len);
            System.arraycopy(buffer, currentPos, b, off, length);
            currentPos += length;
            return length;
        }
        currentPos = 0;
        if (len > buffer.length) {
            count = 0;
            return inputStream.read(b, off, len);
        }
        count = inputStream.read(buffer);
        if (count == -1) {
            return -1;
        }
        return read(b, off, len);
    }

    public void close() throws IOException {
        inputStream.close();
    }
}
