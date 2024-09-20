package org.globsframework.core.utils.serialization;

import java.io.InputStream;
import java.util.Arrays;

public class CompressedSerializationInput extends DefaultSerializationInput {
    private String[] cachedString = new String[1024];

    public CompressedSerializationInput(InputStream inputStream) {
        super(inputStream);
        cachedString[0] = null;
    }

    public String readUtf8String() {
        int i = readNotNullInt();
        if (i >= 0) {
            return cachedString[i];
        }
        String s = super.readUtf8String();
        if (-i >= cachedString.length) {
            if (-i != cachedString.length) {
                throw new RuntimeException("Unexpected string cache growth : " + cachedString.length + " => " + -i);
            }
            cachedString = Arrays.copyOf(cachedString, (int) (cachedString.length * 1.5));
        }
        cachedString[-i] = s;
        return s;
    }
}
