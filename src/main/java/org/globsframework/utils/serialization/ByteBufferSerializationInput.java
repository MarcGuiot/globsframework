package org.globsframework.utils.serialization;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobModel;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.*;
import org.globsframework.model.delta.DefaultChangeSet;
import org.globsframework.model.delta.MutableChangeSet;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

public class ByteBufferSerializationInput implements SerializedInput {
    private final InputStreamFieldVisitor fieldVisitorInput = new InputStreamFieldVisitor();
    private final byte[] data;
    private int count;

    public ByteBufferSerializationInput(byte[] data) {
        this.data = data;
    }

    public ByteBufferSerializationInput(byte[] data, int offset) {
        this.data = data;
        count = offset;
    }

    public int position() {
        return count;
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
        int array[] = new int[length];
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
        long array[] = new long[length];
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
        double array[] = new double[length];
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
        boolean array[] = new boolean[length];
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
        BigDecimal array[] = new BigDecimal[length];
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
        String array[] = new String[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = readUtf8String();
        }
        return array;
    }

    public void close() {
    }

    static class FieldReader implements FieldVisitor {
        private ByteBufferSerializationInput input;
        private FieldValuesBuilder builder;

        public FieldReader(ByteBufferSerializationInput input, FieldValuesBuilder builder) {
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
        private ByteBufferSerializationInput input;
        private FieldValuesWithPreviousBuilder builder;

        public FieldWithPreviousReader(ByteBufferSerializationInput input, FieldValuesWithPreviousBuilder builder) {
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
        return readI();
    }

    private int readI() {
        count+=4;
        int ch1 = data[count-4] & 0xff;
        int ch2 = data[count-3] & 0xff;
        int ch3 = data[count-2] & 0xff;
        int ch4 = data[count-1] & 0xff;
        return toInt(ch1, ch2, ch3, ch4);
    }

    public static int toInt(int ch1, int ch2, int ch3, int ch4) {
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0x0));
    }

    private boolean isNull() {
        return read() != 0;
    }

    public Double readDouble() {
        if (isNull()) {
            return null;
        }
        return Double.longBitsToDouble(readL());
    }

    public double readNotNullDouble() {
        return Double.longBitsToDouble(readNotNullLong());
    }

    public String readUtf8String() {
        int length = readNotNullInt();
        if (length == -1) {
            return null;
        }
        int offset = count;
        count += length;
        return new String(data, offset, length, StandardCharsets.UTF_8);
    }

    public Boolean readBoolean() {
        int i = read();
        return i == 0 ? Boolean.FALSE : i == 1 ? Boolean.TRUE : null;
    }

    public Long readLong() {
        if (isNull()) {
            return null;
        }
        return readL();
    }

    public long readNotNullLong() {
        return readL();
    }

    private long readL() {
        count+=8;
        return (((long) (data[count-8] & 0xff) << 56) +
                ((long) (data[count-7] & 0xff) << 48) +
                ((long) (data[count-6] & 0xff) << 40) +
                ((long) (data[count-5] & 0xff) << 32) +
                ((long) (data[count-4] & 0xff) << 24) +
                ((long) (data[count-3] & 0xff) << 16) +
                ((long) (data[count-2] & 0xff) << 8) +
                ((data[count-1] & 0xff)));
    }

    private int read() {
        return data[count++] & 0xff;
    }

    public byte readByte() {
        return (byte) read();
    }

    public byte[] readBytes() {
        int length = readNotNullInt();
        if (length == -1) {
            return null;
        }
        int offset = count;
        count += length;
        return Arrays.copyOfRange(data, offset, count);
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
