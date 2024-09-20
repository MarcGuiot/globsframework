package org.globsframework.core.utils.serialization;

import org.globsframework.core.metamodel.GlobModel;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.*;
import org.globsframework.core.model.*;
import org.globsframework.core.model.delta.DefaultChangeSet;
import org.globsframework.core.model.delta.MutableChangeSet;
import org.globsframework.core.utils.exceptions.EOFIOFailure;
import org.globsframework.core.utils.exceptions.InvalidData;
import org.globsframework.core.utils.exceptions.UnexpectedApplicationState;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DefaultSerializationInput implements SerializedInput {
    public static final String ORG_GLOBSFRAMWORK_SERIALIZATION_MAX_LEN = "org.globsframwork.serialization.max.len";
    public static final int MAX_SIZE_FOR_BYTES = Integer.getInteger(ORG_GLOBSFRAMWORK_SERIALIZATION_MAX_LEN, 512 * 1024);
    private final InputStream inputStream;
    private final InputStreamFieldVisitor fieldVisitorInput = new InputStreamFieldVisitor();
    public int count;

    public DefaultSerializationInput(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Glob readGlob(GlobModel model) {
        GlobType globType = model.getType(readUtf8String());
        MutableGlob mutableGlob = globType.instantiate();
        for (Field field : globType.getFields()) {
            field.safeAccept(fieldVisitorInput, mutableGlob);
        }
        return mutableGlob;
    }

    public Glob readKnowGlob(GlobType type) {
        MutableGlob mutableGlob = type.instantiate();
        for (Field field : type.getFields()) {
            field.safeAccept(fieldVisitorInput, mutableGlob);
        }
        return mutableGlob;
    }

    public ChangeSet readChangeSet(GlobModel model) {
        MutableChangeSet changeSet = new DefaultChangeSet();
        int count = readInteger();
        for (int i = 0; i < count; i++) {
            GlobType type = model.getType(readUtf8String());
            Key key = KeyBuilder.createFromValues(type, readValues(type));
            int state = readByte();
            switch (state) {
                case 1: {
                    FieldValues values = readValues(type);
                    changeSet.processCreation(key, values);
                    break;
                }
                case 2: {
                    FieldValuesWithPrevious values = readValuesWithPrevious(type);
                    changeSet.processUpdate(key, values);
                    break;
                }
                case 3: {
                    FieldValues values = readValues(type);
                    changeSet.processDeletion(key, values);
                    break;
                }
                default:
                    throw new UnexpectedApplicationState("Invalid state '" + state + "' undefined for: " + key);
            }
        }
        return changeSet;
    }

    private FieldValues readValues(GlobType type) {
        FieldValuesBuilder builder = FieldValuesBuilder.init();
        FieldReader fieldReader = new FieldReader(this, builder);
        int fieldCount = readNotNullInt();
        while (fieldCount != 0) {
            int fieldIndex = readNotNullInt();
            Field field = type.getField(fieldIndex);
            field.safeAccept(fieldReader);
            fieldCount--;
        }
        return builder.get();
    }

    private FieldValuesWithPrevious readValuesWithPrevious(GlobType type) {
        FieldValuesWithPreviousBuilder builder = FieldValuesWithPreviousBuilder.init(type);
        FieldWithPreviousReader fieldReader = new FieldWithPreviousReader(this, builder);
        int fieldCount = readNotNullInt();
        while (fieldCount != 0) {
            int fieldIndex = readNotNullInt();
            Field field = type.getField(fieldIndex);
            field.safeAccept(fieldReader);
            fieldCount--;
        }
        return builder.get();
    }


    public int[] readIntArray() {
        int length = readNotNullInt();
        if (length == -1) {
            return null;
        }
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = readNotNullInt();
        }
        return array;
    }

    public long[] readLongArray() {
        int length = readNotNullInt();
        if (length == -1) {
            return null;
        }
        long[] array = new long[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = readNotNullLong();
        }
        return array;
    }

    public double[] readDoubleArray() {
        int length = readNotNullInt();
        if (length == -1) {
            return null;
        }
        double[] array = new double[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = readNotNullDouble();
        }
        return array;

    }

    public boolean[] readBooleanArray() {
        int length = readNotNullInt();
        if (length == -1) {
            return null;
        }
        boolean[] array = new boolean[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = readBoolean();
        }
        return array;
    }

    public BigDecimal[] readBigDecimalArray() {
        int length = readNotNullInt();
        if (length == -1) {
            return null;
        }
        BigDecimal[] array = new BigDecimal[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = readBigDecimal();
        }
        return array;

    }

    public String[] readStringArray() {
        int length = readNotNullInt();
        if (length == -1) {
            return null;
        }
        String[] array = new String[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = readUtf8String();
        }
        return array;
    }

    public void close() {
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("fail to close", e);
        }
    }

    static class FieldReader implements FieldVisitor {
        private final DefaultSerializationInput input;
        private final FieldValuesBuilder builder;

        public FieldReader(DefaultSerializationInput input, FieldValuesBuilder builder) {
            this.input = input;
            this.builder = builder;
        }

        public void visitInteger(IntegerField field) throws Exception {
            builder.set(field, input.readInteger());
        }

        public void visitIntegerArray(IntegerArrayField field) {
            builder.set(field, input.readIntArray());
        }

        public void visitDouble(DoubleField field) throws Exception {
            builder.set(field, input.readDouble());
        }

        public void visitDoubleArray(DoubleArrayField field) {
            builder.set(field, input.readDoubleArray());
        }

        public void visitBigDecimal(BigDecimalField field) {
            builder.set(field, input.readBigDecimal());
        }

        public void visitBigDecimalArray(BigDecimalArrayField field) {
            builder.set(field, input.readBigDecimalArray());
        }

        public void visitString(StringField field) throws Exception {
            builder.set(field, input.readUtf8String());
        }

        public void visitStringArray(StringArrayField field) {
            builder.set(field, input.readStringArray());
        }

        public void visitBoolean(BooleanField field) throws Exception {
            builder.set(field, input.readBoolean());
        }

        public void visitBooleanArray(BooleanArrayField field) {
            builder.set(field, input.readBooleanArray());
        }

        public void visitBlob(BlobField field) throws Exception {
            builder.set(field, input.readBytes());
        }

        public void visitGlob(GlobField field) throws Exception {
            if (input.readBoolean()) {
                builder.set(field, input.readKnowGlob(field.getTargetType()));
            }
        }

        public void visitGlobArray(GlobArrayField field) throws Exception {
            int len = input.readNotNullInt();
            if (len >= 0) {
                Glob[] values = new Glob[len];
                for (int i = 0; i < values.length; i++) {
                    if (input.readBoolean()) {
                        values[i] = input.readKnowGlob(field.getTargetType());
                    }
                }
                builder.set(field, values);
            }
        }

        public void visitUnionGlob(GlobUnionField field) {
            if (input.readBoolean()) {
                builder.set(field, input.readKnowGlob(field.getTargetType(input.readUtf8String())));
            }
        }

        public void visitUnionGlobArray(GlobArrayUnionField field) {
            int len = input.readNotNullInt();
            if (len >= 0) {
                Glob[] values = new Glob[len];
                for (int i = 0; i < values.length; i++) {
                    if (input.readBoolean()) {
                        values[i] = input.readKnowGlob(field.getTargetType(input.readUtf8String()));
                    }
                }
                builder.set(field, values);
            }
        }

        public void visitLong(LongField field) throws Exception {
            builder.set(field, input.readLong());
        }

        public void visitLongArray(LongArrayField field) {
            builder.set(field, input.readLongArray());
        }

        public void visitDate(DateField field) {
            builder.set(field, input.readDate());
        }

        public void visitDateTime(DateTimeField field) {
            builder.set(field, input.readDateTime());
        }
    }

    public BigDecimal readBigDecimal() {
        String s = readUtf8String();
        if (s == null) {
            return null;
        }
        return new BigDecimal(s);
    }

    static class FieldWithPreviousReader implements FieldVisitor {
        private final DefaultSerializationInput input;
        private final FieldValuesWithPreviousBuilder builder;

        public FieldWithPreviousReader(DefaultSerializationInput input, FieldValuesWithPreviousBuilder builder) {
            this.input = input;
            this.builder = builder;
        }

        public void visitInteger(IntegerField field) throws Exception {
            builder.set(field, input.readInteger(), input.readInteger());
        }

        public void visitIntegerArray(IntegerArrayField field) {
            builder.set(field, input.readIntArray(), input.readIntArray());
        }

        public void visitDouble(DoubleField field) throws Exception {
            builder.set(field, input.readDouble(), input.readDouble());
        }

        public void visitDoubleArray(DoubleArrayField field) {
            builder.set(field, input.readDoubleArray(), input.readDoubleArray());
        }

        public void visitBigDecimal(BigDecimalField field) {
            builder.set(field, input.readBigDecimal(), input.readBigDecimal());
        }

        public void visitBigDecimalArray(BigDecimalArrayField field) {
            builder.set(field, input.readBigDecimalArray(), input.readBigDecimalArray());
        }

        public void visitString(StringField field) throws Exception {
            builder.set(field, input.readUtf8String(), input.readUtf8String());
        }

        public void visitStringArray(StringArrayField field) {
            builder.set(field, input.readStringArray(), input.readStringArray());
        }

        public void visitBoolean(BooleanField field) throws Exception {
            builder.set(field, input.readBoolean(), input.readBoolean());
        }

        public void visitBooleanArray(BooleanArrayField field) {
            builder.set(field, input.readBooleanArray(), input.readBooleanArray());
        }

        public void visitBlob(BlobField field) throws Exception {
            builder.set(field, input.readBytes(), input.readBytes());
        }

        public void visitGlob(GlobField field) throws Exception {
            Glob newValue = null;
            if (input.readBoolean()) {
                newValue = input.readKnowGlob(field.getTargetType());
            }
            Glob previousValue = null;
            if (input.readBoolean()) {
                previousValue = input.readKnowGlob(field.getTargetType());
            }
            builder.set(field, newValue, previousValue);
        }

        public void visitGlobArray(GlobArrayField field) throws Exception {
            int lenNewValues = input.readNotNullInt();
            Glob[] newValue = null;
            if (lenNewValues >= 0) {
                newValue = new Glob[lenNewValues];
                for (int i = 0; i < newValue.length; i++) {
                    newValue[i] = input.readBoolean() ? input.readKnowGlob(field.getTargetType()) : null;
                }
            }

            int lenPreviousValues = input.readNotNullInt();
            Glob[] previousValue = null;
            if (lenPreviousValues >= 0) {
                previousValue = new Glob[lenPreviousValues];
                for (int i = 0; i < previousValue.length; i++) {
                    previousValue[i] = input.readBoolean() ? input.readKnowGlob(field.getTargetType()) : null;
                }
            }
            builder.set(field, newValue, previousValue);
        }

        public void visitUnionGlob(GlobUnionField field) {
            Glob newValue = null;
            if (input.readBoolean()) {
                newValue = input.readKnowGlob(field.getTargetType(input.readUtf8String()));
            }
            Glob previousValue = null;
            if (input.readBoolean()) {
                previousValue = input.readKnowGlob(field.getTargetType(input.readUtf8String()));
            }
            builder.set(field, newValue, previousValue);
        }

        public void visitUnionGlobArray(GlobArrayUnionField field) {
            int lenNewValues = input.readNotNullInt();
            Glob[] newValue = null;
            if (lenNewValues >= 0) {
                newValue = new Glob[lenNewValues];
                for (int i = 0; i < newValue.length; i++) {
                    newValue[i] = input.readBoolean() ? input.readKnowGlob(field.getTargetType(input.readUtf8String())) : null;
                }
            }

            int lenPreviousValues = input.readNotNullInt();
            Glob[] previousValue = null;
            if (lenPreviousValues >= 0) {
                previousValue = new Glob[lenPreviousValues];
                for (int i = 0; i < previousValue.length; i++) {
                    previousValue[i] = input.readBoolean() ? input.readKnowGlob(field.getTargetType(input.readUtf8String())) : null;
                }
            }
            builder.set(field, newValue, previousValue);
        }

        public void visitLong(LongField field) throws Exception {
            builder.set(field, input.readLong(), input.readLong());
        }

        public void visitLongArray(LongArrayField field) {
            builder.set(field, input.readLongArray(), input.readLongArray());
        }

        public void visitDate(DateField field) {
            builder.set(field, input.readDate(), input.readDate());
        }

        public void visitDateTime(DateTimeField field) {
            builder.set(field, input.readDateTime(), input.readDateTime());
        }
    }

    private LocalDate readDate() {
        int year = readNotNullInt();
        if (year == Integer.MIN_VALUE) {
            return null;
        }
        int month = readNotNullInt();
        int day = readNotNullInt();
        return LocalDate.of(year, month, day);
    }

    public ZonedDateTime readDateTime() {
        int year = readNotNullInt();
        if (year == Integer.MIN_VALUE) {
            return null;
        }
        int month = readNotNullInt();
        int day = readNotNullInt();
        int hour = readNotNullInt();
        int min = readNotNullInt();
        int second = readNotNullInt();
        int nano = readNotNullInt();
        String zone = readUtf8String();
        return ZonedDateTime.of(LocalDate.of(year, month, day),
                LocalTime.of(hour, min, second, nano), ZoneId.of(zone));
    }

    public Integer readInteger() {
        if (isNull()) {
            return null;
        }
        return readNotNullInt();
    }

    public int readNotNullInt() {
        int ch1 = read();
        int ch2 = read();
        int ch3 = read();
        int ch4 = read();
        return toInt(ch1, ch2, ch3, ch4);
    }

    public static int toInt(int ch1, int ch2, int ch3, int ch4) {
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFIOFailure("eof");
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0x0));
    }

    private boolean isNull() {
        return read() != 0;
    }

    public Double readDouble() {
        Long l = readLong();
        if (l == null) {
            return null;
        }
        return Double.longBitsToDouble(l);
    }

    public double readNotNullDouble() {
        return Double.longBitsToDouble(readNotNullLong());
    }

    public String readUtf8String() {
        byte[] bytes = readBytes();
        if (bytes == null) {
            return null;
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public Boolean readBoolean() {
        int i = read();
        return i == 0 ? Boolean.FALSE : i == 1 ? Boolean.TRUE : null;
    }

    public Long readLong() {
        if (isNull()) {
            return null;
        }
        return readNotNullLong();
    }

    public long readNotNullLong() {
        return (((long) read() << 56) +
                ((long) (read()) << 48) +
                ((long) (read()) << 40) +
                ((long) (read()) << 32) +
                ((long) (read()) << 24) +
                ((long) (read()) << 16) +
                ((long) (read()) << 8) +
                ((read())));
    }

    private int read() {
        try {
            int i = inputStream.read();
            if (i == -1) {
                throw new EOFIOFailure("eof");
            }
            count++;
            return i;
        } catch (IOException e) {
            throw new EOFIOFailure(e);
        }
    }

    public byte readByte() {
        return (byte) (read());
    }

    public byte[] readBytes() {
        try {
            int length = readNotNullInt();
            if (length == -1) {
                return null;
            }
            int readed = 0;
            if (MAX_SIZE_FOR_BYTES != -1 && length > MAX_SIZE_FOR_BYTES) {
                throw new InvalidData("More than " + MAX_SIZE_FOR_BYTES + " bytes to write  (" + length + ") see " + ORG_GLOBSFRAMWORK_SERIALIZATION_MAX_LEN);
            }
            if (length < 0) {
                throw new InvalidData("negative length : " + length);
            }
            byte[] bytes = new byte[length];
            while (readed != length) {
                int readSize = inputStream.read(bytes, readed, length - readed);
                if (readSize == -1) {
                    throw new EOFIOFailure("Missing data in buffer expected " + length + " but was " + readed);
                }
                readed += readSize;
            }
            count += readed;
            return bytes;
        } catch (IOException e) {
            throw new UnexpectedApplicationState(e);
        }
    }

    private class InputStreamFieldVisitor implements FieldVisitorWithContext<MutableGlob> {

        public void visitInteger(IntegerField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readInteger());
        }

        public void visitIntegerArray(IntegerArrayField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readIntArray());
        }

        public void visitDouble(DoubleField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readDouble());
        }

        public void visitDoubleArray(DoubleArrayField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readDoubleArray());
        }

        public void visitBigDecimal(BigDecimalField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readBigDecimal());
        }

        public void visitBigDecimalArray(BigDecimalArrayField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readBigDecimalArray());
        }

        public void visitString(StringField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readUtf8String());
        }

        public void visitStringArray(StringArrayField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readStringArray());
        }

        public void visitBoolean(BooleanField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readBoolean());
        }

        public void visitBooleanArray(BooleanArrayField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readBooleanArray());
        }

        public void visitBlob(BlobField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readBytes());
        }

        public void visitGlob(GlobField field, MutableGlob mutableGlob) {
            if (readBoolean()) {
                mutableGlob.set(field, readKnowGlob(field.getTargetType()));
            }
        }

        public void visitGlobArray(GlobArrayField field, MutableGlob mutableGlob) {
            int len = readNotNullInt();
            if (len >= 0) {
                Glob[] values = new Glob[len];
                for (int i = 0; i < values.length; i++) {
                    if (readBoolean()) {
                        values[i] = readKnowGlob(field.getTargetType());
                    }
                }
                mutableGlob.set(field, values);
            }
        }

        public void visitUnionGlob(GlobUnionField field, MutableGlob mutableGlob) {
            if (readBoolean()) {
                GlobType globType = field.getTargetType(readUtf8String());
                mutableGlob.set(field, readKnowGlob(globType));
            }
        }

        public void visitUnionGlobArray(GlobArrayUnionField field, MutableGlob mutableGlob) {
            int len = readNotNullInt();
            if (len >= 0) {
                Glob[] values = new Glob[len];
                for (int i = 0; i < values.length; i++) {
                    if (readBoolean()) {
                        values[i] = readKnowGlob(field.getTargetType(readUtf8String()));
                    }
                }
                mutableGlob.set(field, values);
            }
        }

        public void visitLong(LongField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readLong());
        }

        public void visitLongArray(LongArrayField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readLongArray());
        }

        public void visitDate(DateField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readDate());
        }

        public void visitDateTime(DateTimeField field, MutableGlob mutableGlob) {
            mutableGlob.set(field, readDateTime());
        }
    }
}
