package org.globsframework.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class ReusableByteArrayOutputStream extends OutputStream {
    private byte[] buf;
    private int count;

    public ReusableByteArrayOutputStream() {
        buf = new byte[1024];
        count = 0;
    }

    public ReusableByteArrayOutputStream(int size) {
        buf = new byte[size];
        count = 0;
    }

        //code from byteArrayOutputstream (without sync)

    public void write(int b) throws IOException {
        int newcount = count + 1;
        if (newcount > buf.length) {
            buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
        }
        buf[count] = (byte)b;
        count = newcount;
    }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        if ((off < 0) || (off > b.length) || (len < 0) ||
                ((off + len) > b.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        int newcount = count + len;
        if (newcount > buf.length) {
            buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
        }
        System.arraycopy(b, off, buf, count, len);
        count = newcount;
    }

    public byte[] getBuffer() {
        return buf;
    }

    public void reset(){
        count = 0;
    }

    public void setTo(int size) {
        count = size;
    }

    public int size() {
        return count;
    }
}
