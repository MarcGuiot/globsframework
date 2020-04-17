package org.globsframework.utils;

import java.io.InputStream;

/**
 * code from ByteArrayInputSteam (without sync)
 */


public class LimitedByteArrayInputStream extends InputStream{
    private final byte[] buf;
    private int pos;
    private final int count;

    public LimitedByteArrayInputStream(byte buf[]) {
        this.buf = buf;
        this.pos = 0;
        this.count = buf.length;
    }

    public LimitedByteArrayInputStream(byte buf[], int maxCount) {
        this.buf = buf;
        this.pos = 0;
        if (maxCount > buf.length) {
            this.count = buf.length;
        }else {
            this.count = maxCount;
        }
    }

    public int read() {
        return (pos < count) ? (buf[pos++] & 0xff) : -1;
    }

    public int read(byte b[], int off, int len) {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        }
        if (pos >= count) {
            return -1;
        }
        if (pos + len > count) {
            len = count - pos;
        }
        if (len <= 0) {
            return 0;
        }
        System.arraycopy(buf, pos, b, off, len);
        pos += len;
        return len;
    }
}
