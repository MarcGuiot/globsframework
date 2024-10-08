package org.globsframework.core.utils.serialization;

import org.globsframework.core.metamodel.GlobModel;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.model.ChangeSet;
import org.globsframework.core.model.Glob;
import org.globsframework.core.utils.exceptions.UnexpectedApplicationState;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class SerializationInputChecker implements SerializedInput {
    private final SerializedInput serializedInput;

    public SerializationInputChecker(SerializedInput serializedInput) {
        this.serializedInput = serializedInput;
    }

    public Glob readGlob(GlobModel model) {
        String value = serializedInput.readUtf8String();
        if ("Glob".equals(value)) {
            return serializedInput.readGlob(model);
        } else {
            throw new UnexpectedApplicationState("Glob expected but got " + value);
        }
    }

    public Glob readKnowGlob(GlobType type) {
        String value = serializedInput.readUtf8String();
        if ("KnowGlob".equals(value)) {
            return serializedInput.readKnowGlob(type);
        } else {
            throw new UnexpectedApplicationState("Known Glob " + type + " expected but got " + value);
        }
    }

    public ChangeSet readChangeSet(GlobModel model) {
        String value = serializedInput.readUtf8String();
        if ("ChangeSet".equals(value)) {
            return serializedInput.readChangeSet(model);
        } else {
            throw new UnexpectedApplicationState("ChangeSet expected but got " + value);
        }
    }

    public int[] readIntArray() {
        String value = serializedInput.readUtf8String();
        if ("int[]".equals(value)) {
            return serializedInput.readIntArray();
        } else {
            throw new UnexpectedApplicationState("int array expected but got " + value);
        }
    }

    public long[] readLongArray() {
        String value = serializedInput.readUtf8String();
        if ("long[]".equals(value)) {
            return serializedInput.readLongArray();
        } else {
            throw new UnexpectedApplicationState("long array expected but got " + value);
        }
    }

    public double[] readDoubleArray() {
        String value = serializedInput.readUtf8String();
        if ("long[]".equals(value)) {
            return serializedInput.readDoubleArray();
        } else {
            throw new UnexpectedApplicationState("double array expected but got " + value);
        }
    }

    public boolean[] readBooleanArray() {
        String value = serializedInput.readUtf8String();
        if ("Boolean[]".equals(value)) {
            return serializedInput.readBooleanArray();
        } else {
            throw new UnexpectedApplicationState("Boolean array expected but got " + value);
        }
    }

    public BigDecimal[] readBigDecimalArray() {
        String value = serializedInput.readUtf8String();
        if ("BigDecimal[]".equals(value)) {
            return serializedInput.readBigDecimalArray();
        } else {
            throw new UnexpectedApplicationState("BigDecimal array expected but got " + value);
        }
    }

    public String[] readStringArray() {
        String value = serializedInput.readUtf8String();
        if ("string[]".equals(value)) {
            return serializedInput.readStringArray();
        } else {
            throw new UnexpectedApplicationState("string array expected but got " + value);
        }
    }

    public void close() {
        serializedInput.close();
    }

    public BigDecimal readBigDecimal() {
        String value = serializedInput.readUtf8String();
        if ("BigDecimal".equals(value)) {
            return serializedInput.readBigDecimal();
        } else {
            throw new UnexpectedApplicationState("bigDecimal expected but got " + value);
        }
    }

    public ZonedDateTime readDateTime() {
        String value = serializedInput.readUtf8String();
        if ("Date".equals(value)) {
            return serializedInput.readDateTime();
        } else {
            throw new UnexpectedApplicationState("Date expected but got " + value);
        }
    }

    public Integer readInteger() {
        String value = serializedInput.readUtf8String();
        if ("Integer".equals(value)) {
            return serializedInput.readInteger();
        } else {
            throw new UnexpectedApplicationState("Integer expected but got " + value);
        }
    }

    public int readNotNullInt() {
        String value = serializedInput.readUtf8String();
        if ("int".equals(value)) {
            return serializedInput.readNotNullInt();
        } else {
            throw new UnexpectedApplicationState("int expected but got " + value);
        }
    }

    public Double readDouble() {
        String value = serializedInput.readUtf8String();
        if ("Double".equals(value)) {
            return serializedInput.readDouble();
        } else {
            throw new UnexpectedApplicationState("Double expected but got " + value);
        }
    }

    public double readNotNullDouble() {
        String value = serializedInput.readUtf8String();
        if ("double".equals(value)) {
            return serializedInput.readNotNullDouble();
        } else {
            throw new UnexpectedApplicationState("Double expected but got " + value);
        }
    }

    public String readUtf8String() {
        String value = serializedInput.readUtf8String();
        if ("StringUtf8".equals(value)) {
            return serializedInput.readUtf8String();
        } else {
            throw new UnexpectedApplicationState("String expected but got " + value);
        }
    }

    public Boolean readBoolean() {
        String value = serializedInput.readUtf8String();
        if ("Boolean".equals(value)) {
            return serializedInput.readBoolean();
        } else {
            throw new UnexpectedApplicationState("Boolean expected but got " + value);
        }
    }

    public Long readLong() {
        String value = serializedInput.readUtf8String();
        if ("Long".equals(value)) {
            return serializedInput.readLong();
        } else {
            throw new UnexpectedApplicationState("Long expected but got " + value);
        }
    }

    public long readNotNullLong() {
        String value = serializedInput.readUtf8String();
        if ("long".equals(value)) {
            return serializedInput.readNotNullLong();
        } else {
            throw new UnexpectedApplicationState("long expected but got " + value);
        }
    }

    public byte readByte() {
        String value = serializedInput.readUtf8String();
        if ("byte".equals(value)) {
            return serializedInput.readByte();
        } else {
            throw new UnexpectedApplicationState("byte expected but got " + value);
        }
    }

    public byte[] readBytes() {
        String value = serializedInput.readUtf8String();
        if ("Bytes".equals(value)) {
            return serializedInput.readBytes();
        } else {
            throw new UnexpectedApplicationState("Bytes expected but got " + value);
        }
    }
}
