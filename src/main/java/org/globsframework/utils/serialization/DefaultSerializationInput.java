package org.globsframework.utils.serialization;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobModel;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.*;
import org.globsframework.model.delta.DefaultChangeSet;
import org.globsframework.model.delta.MutableChangeSet;
import org.globsframework.utils.exceptions.EOFIOFailure;
import org.globsframework.utils.exceptions.InvalidData;
import org.globsframework.utils.exceptions.InvalidFormat;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.*;

public class DefaultSerializationInput implements SerializedInput {
    public static final String ORG_GLOBSFRAMWORK_SERIALIZATION_MAX_LEN = "org.globsframwork.serialization.max.len";
    public static final int MAX_SIZE_FOR_BYTES = Integer.getInteger(ORG_GLOBSFRAMWORK_SERIALIZATION_MAX_LEN, 512 * 1024);
    private InputStream inputStream;
    public int count;

    public DefaultSerializationInput(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Glob readGlob(GlobModel model) {
        GlobType globType = model.getType(readUtf8String());
        MutableGlob mutableGlob = globType.instantiate();
        InputStreamFieldVisitor fieldVisitorInput = new InputStreamFieldVisitor(model, mutableGlob);
        for (Field field : globType.getFields()) {
            field.safeVisit(fieldVisitorInput);
        }
        return mutableGlob;
    }

    public ChangeSet readChangeSet(GlobModel model) {
        MutableChangeSet changeSet = new DefaultChangeSet();
        int count = readInteger();
        for (int i = 0; i < count; i++) {
            GlobType type = model.getType(readUtf8String());
            Key key = KeyBuilder.createFromValues(type, readValues(model, type));
            int state = readByte();
            switch (state) {
                case 1: {
                    FieldValues values = readValues(model, type);
                    changeSet.processCreation(key, values);
                    break;
                }
                case 2: {
                    FieldValuesWithPrevious values = readValuesWithPrevious(model, type);
                    changeSet.processUpdate(key, values);
                    break;
                }
                case 3: {
                    FieldValues values = readValues(model, type);
                    changeSet.processDeletion(key, values);
                    break;
                }
                default:
                    throw new UnexpectedApplicationState("Invalid state '" + state + "' undefined for: " + key);
            }
        }
        return changeSet;
    }

    private FieldValues readValues(GlobModel model, GlobType type) {
        FieldValuesBuilder builder = FieldValuesBuilder.init();
        FieldReader fieldReader = new FieldReader(this, model, builder);
        int fieldCount = readNotNullInt();
        while (fieldCount != 0) {
            int fieldIndex = readNotNullInt();
            Field field = type.getField(fieldIndex);
            field.safeVisit(fieldReader);
            fieldCount--;
        }
        return builder.get();
    }

    private FieldValuesWithPrevious readValuesWithPrevious(GlobModel model, GlobType type) {
        FieldValuesWithPreviousBuilder builder = FieldValuesWithPreviousBuilder.init(type);
        FieldWithPreviousReader fieldReader = new FieldWithPreviousReader(this, model, builder);
        int fieldCount = readNotNullInt();
        while (fieldCount != 0) {
            int fieldIndex = readNotNullInt();
            Field field = type.getField(fieldIndex);
            field.safeVisit(fieldReader);
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

    public BigDecimal[] readBigDecimaleArray() {
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
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("fail to close", e);
        }
    }

    static class FieldReader implements FieldVisitor {
        private DefaultSerializationInput input;
        private GlobModel model;
        private FieldValuesBuilder builder;

        public FieldReader(DefaultSerializationInput input, GlobModel model, FieldValuesBuilder builder) {
            this.input = input;
            this.model = model;
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
            builder.set(field, input.readBigDecimaleArray());
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
                builder.set(field, input.readGlob(model));
            }
        }

        public void visitGlobArray(GlobArrayField field) throws Exception {
            int len = input.readNotNullInt();
            if (len >= 0) {
                Glob[] values = new Glob[len];
                for (int i = 0; i < values.length; i++) {
                    if (input.readBoolean()) {
                        values[i] = input.readGlob(model);
                    }
                }
                builder.set(field, values);
            }
        }

        public void visitUnionGlob(GlobUnionField field) {
            if (input.readBoolean()) {
                builder.set(field, input.readGlob(model));
            }
        }

        public void visitUnionGlobArray(GlobArrayUnionField field) {
            int len = input.readNotNullInt();
            if (len >= 0) {
                Glob[] values = new Glob[len];
                for (int i = 0; i < values.length; i++) {
                    if (input.readBoolean()) {
                        values[i] = input.readGlob(model);
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
            int year = input.readNotNullInt();
            int month = input.readNotNullInt();
            int day = input.readNotNullInt();
            builder.set(field, LocalDate.of(year, month, day));
        }

        public void visitDateTime(DateTimeField field) {
            int year = input.readNotNullInt();
            int month = input.readNotNullInt();
            int day = input.readNotNullInt();
            int hour = input.readNotNullInt();
            int min = input.readNotNullInt();
            int second = input.readNotNullInt();
            int nano = input.readNotNullInt();
            String zone = input.readUtf8String();
            builder.set(field, ZonedDateTime.of(LocalDate.of(year, month, day),
                    LocalTime.of(hour, min, second, nano), ZoneId.of(zone)));
        }
    }

    private BigDecimal readBigDecimal() {
        String s = readUtf8String();
        if (s == null) {
            return null;
        }
        return new BigDecimal(s);
    }

    static class FieldWithPreviousReader implements FieldVisitor {
        private DefaultSerializationInput input;
        private GlobModel model;
        private FieldValuesWithPreviousBuilder builder;

        public FieldWithPreviousReader(DefaultSerializationInput input, GlobModel model, FieldValuesWithPreviousBuilder builder) {
            this.input = input;
            this.model = model;
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
            builder.set(field, input.readBigDecimaleArray(), input.readBigDecimaleArray());
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
                newValue = input.readGlob(model);
            }
            Glob previousValue = null;
            if (input.readBoolean()) {
                previousValue = input.readGlob(model);
            }
            builder.set(field, newValue, previousValue);
        }

        public void visitGlobArray(GlobArrayField field) throws Exception {
            int lenNewValues = input.readNotNullInt();
            Glob[] newValue = null;
            if (lenNewValues >= 0) {
                newValue = new Glob[lenNewValues];
                for (int i = 0; i < newValue.length; i++) {
                    newValue[i] = input.readBoolean() ? input.readGlob(model) : null;
                }
            }

            int lenPreviousValues = input.readNotNullInt();
            Glob[] previousValue = null;
            if (lenPreviousValues >= 0) {
                previousValue = new Glob[lenPreviousValues];
                for (int i = 0; i < previousValue.length; i++) {
                    previousValue[i] = input.readBoolean() ? input.readGlob(model) : null;
                }
            }
            builder.set(field, newValue, previousValue);
        }

        public void visitUnionGlob(GlobUnionField field) {
            Glob newValue = null;
            if (input.readBoolean()) {
                newValue = input.readGlob(model);
            }
            Glob previousValue = null;
            if (input.readBoolean()) {
                previousValue = input.readGlob(model);
            }
            builder.set(field, newValue, previousValue);
        }

        public void visitUnionGlobArray(GlobArrayUnionField field) {
            int lenNewValues = input.readNotNullInt();
            Glob[] newValue = null;
            if (lenNewValues >= 0) {
                newValue = new Glob[lenNewValues];
                for (int i = 0; i < newValue.length; i++) {
                    newValue[i] = input.readBoolean() ? input.readGlob(model) : null;
                }
            }

            int lenPreviousValues = input.readNotNullInt();
            Glob[] previousValue = null;
            if (lenPreviousValues >= 0) {
                previousValue = new Glob[lenPreviousValues];
                for (int i = 0; i < previousValue.length; i++) {
                    previousValue[i] = input.readBoolean() ? input.readGlob(model) : null;
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
        byte month = readByte();
        byte day = readByte();
        return LocalDate.of(year, month, day);
    }

    public ZonedDateTime readDateTime() {
        int year = readNotNullInt();
        if (year == Integer.MIN_VALUE) {
            return null;
        }
        byte month = readByte();
        byte day = readByte();
        LocalDate localDate = LocalDate.of(year, month, day);
        long time = readNotNullLong();
        LocalTime localTime = LocalTime.ofNanoOfDay(time);
        String zoneId = readUtf8String();
        int totalSeconds = readNotNullInt();
        return ZonedDateTime.of(localDate, localTime, ZoneId.ofOffset(zoneId, ZoneOffset.ofTotalSeconds(totalSeconds)));
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
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InvalidFormat(e);
        }
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
                ((read()) << 16) +
                ((read()) << 8) +
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

    private class InputStreamFieldVisitor implements FieldVisitor {
        private GlobModel model;
        private MutableGlob mutableGlob;

        public InputStreamFieldVisitor(GlobModel model, MutableGlob mutableGlob) {
            this.model = model;
            this.mutableGlob = mutableGlob;
        }

        public void visitInteger(IntegerField field) {
            mutableGlob.set(field, readInteger());
        }

        public void visitIntegerArray(IntegerArrayField field) {
            mutableGlob.set(field, readIntArray());
        }

        public void visitDouble(DoubleField field) {
            mutableGlob.set(field, readDouble());
        }

        public void visitDoubleArray(DoubleArrayField field) {
            mutableGlob.set(field, readDoubleArray());
        }

        public void visitBigDecimal(BigDecimalField field) {
            mutableGlob.set(field, readBigDecimal());
        }

        public void visitBigDecimalArray(BigDecimalArrayField field) {
            mutableGlob.set(field, readBigDecimaleArray());
        }

        public void visitString(StringField field) {
            mutableGlob.set(field, readUtf8String());
        }

        public void visitStringArray(StringArrayField field) {
            mutableGlob.set(field, readStringArray());
        }

        public void visitBoolean(BooleanField field) {
            mutableGlob.set(field, readBoolean());
        }

        public void visitBooleanArray(BooleanArrayField field) {
            mutableGlob.set(field, readBooleanArray());
        }

        public void visitBlob(BlobField field) {
            mutableGlob.set(field, readBytes());
        }

        public void visitGlob(GlobField field) {
            if (readBoolean()) {
                mutableGlob.set(field, readGlob(model));
            }
        }

        public void visitGlobArray(GlobArrayField field) {
            int len = readNotNullInt();
            if (len >= 0) {
                Glob[] values = new Glob[len];
                for (int i = 0; i < values.length; i++) {
                    if (readBoolean()) {
                        values[i] = readGlob(model);
                    }
                }
                mutableGlob.set(field, values);
            }
        }

        public void visitUnionGlob(GlobUnionField field) {
            if (readBoolean()) {
                mutableGlob.set(field, readGlob(model));
            }
        }

        public void visitUnionGlobArray(GlobArrayUnionField field) {
            int len = readNotNullInt();
            if (len >= 0) {
                Glob[] values = new Glob[len];
                for (int i = 0; i < values.length; i++) {
                    if (readBoolean()) {
                        values[i] = readGlob(model);
                    }
                }
                mutableGlob.set(field, values);
            }
        }

        public void visitLong(LongField field) {
            mutableGlob.set(field, readLong());
        }

        public void visitLongArray(LongArrayField field) {
            mutableGlob.set(field, readLongArray());
        }

        public void visitDate(DateField field) {
            mutableGlob.set(field, readDate());
        }

        public void visitDateTime(DateTimeField field) {
            mutableGlob.set(field, readDateTime());
        }

        public Glob getValue() {
            return mutableGlob;
        }

    }
}
