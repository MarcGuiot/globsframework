package org.globsframework.utils.serialization;

import java.io.IOException;
import java.io.InputStream;

public class YANBuffereInputStream extends InputStream {
    private byte[] buffer;
    private int currentPos;
    private int count;
    private InputStream inputStream;

    public YANBuffereInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        buffer = new byte[8 * 1024];
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

    public int read(byte b[], int off, int len) throws IOException {
        int availlable = count - currentPos;
        if (availlable > 0) {
            int length = availlable > len ? len : availlable;
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
}
