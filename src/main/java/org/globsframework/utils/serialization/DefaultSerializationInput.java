package org.globsframework.utils.serialization;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobModel;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.*;
import org.globsframework.model.delta.DefaultChangeSet;
import org.globsframework.model.delta.MutableChangeSet;
import org.globsframework.model.utils.GlobBuilder;
import org.globsframework.utils.exceptions.EOFIOFailure;
import org.globsframework.utils.exceptions.InvalidData;
import org.globsframework.utils.exceptions.InvalidFormat;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.*;

public class DefaultSerializationInput implements SerializedInput {
    private InputStream inputStream;
    public int count;

    DefaultSerializationInput(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Glob readGlob(GlobModel model) {
        GlobType globType = model.getType(readUtf8String());
        GlobBuilder builder = GlobBuilder.init(globType);
        InputStreamFieldVisitor fieldVisitorInput = new InputStreamFieldVisitor(builder);
        for (Field field : globType.getFields()) {
            field.safeVisit(fieldVisitorInput);
        }
        return builder.get();
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
            field.safeVisit(fieldReader);
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
            field.safeVisit(fieldReader);
            fieldCount--;
        }
        return builder.get();
    }


    public int[] readIntArray() {
        int length = readNotNullInt();
        int array[] = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = readNotNullInt();
        }
        return array;
    }

    public long[] readLongArray() {
        int length = readNotNullInt();
        long array[] = new long[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = readNotNullLong();
        }
        return array;
    }

    public void close() {
        try {
            inputStream.close();
        }
        catch (IOException e) {
            throw new RuntimeException("fail to close", e);
        }
    }

    static class FieldReader implements FieldVisitor {
        private DefaultSerializationInput input;
        private FieldValuesBuilder builder;

        public FieldReader(DefaultSerializationInput input, FieldValuesBuilder builder) {
            this.input = input;
            this.builder = builder;
        }

        public void visitInteger(IntegerField field) throws Exception {
            builder.set(field, input.readInteger());
        }

        public void visitDouble(DoubleField field) throws Exception {
            builder.set(field, input.readDouble());
        }

        public void visitString(StringField field) throws Exception {
            builder.set(field, input.readUtf8String());
        }

        public void visitBoolean(BooleanField field) throws Exception {
            builder.set(field, input.readBoolean());
        }

        public void visitBlob(BlobField field) throws Exception {
            builder.set(field, input.readBytes());
        }

        public void visitLong(LongField field) throws Exception {
            builder.set(field, input.readLong());
        }

    }

    static class FieldWithPreviousReader implements FieldVisitor {
        private DefaultSerializationInput input;
        private FieldValuesWithPreviousBuilder builder;

        public FieldWithPreviousReader(DefaultSerializationInput input, FieldValuesWithPreviousBuilder builder) {
            this.input = input;
            this.builder = builder;
        }

        public void visitInteger(IntegerField field) throws Exception {
            builder.set(field, input.readInteger(), input.readInteger());
        }

        public void visitDouble(DoubleField field) throws Exception {
            builder.set(field, input.readDouble(), input.readDouble());
        }

        public void visitString(StringField field) throws Exception {
            builder.set(field, input.readUtf8String(), input.readUtf8String());
        }

        public void visitBoolean(BooleanField field) throws Exception {
            builder.set(field, input.readBoolean(), input.readBoolean());
        }

        public void visitBlob(BlobField field) throws Exception {
            builder.set(field, input.readBytes(), input.readBytes());
        }

        public void visitLong(LongField field) throws Exception {
            builder.set(field, input.readLong(), input.readLong());
        }

    }
/*
      ZonedDateTime zonedDateTime = glob.get(field);
      LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
      LocalDate date = localDateTime.toLocalDate();
      Dates.decomposeDate(date, (year, month, day) -> {write(year); write(((byte)month)); write(((byte)day));});
      LocalTime time = localDateTime.toLocalTime();
      long l = time.toNanoOfDay();
      write(l);
      ZoneOffset offset = zonedDateTime.getOffset();
      int totalSeconds = offset.getTotalSeconds();
      write(totalSeconds);

 */

    public ZonedDateTime readDate() {
        int year = readNotNullInt();
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
        try {
            return ((read() & 0xFF)) +
                   ((read() & 0xFF) << 8) +
                   ((read() & 0xFF) << 16) +
                   ((read() & 0xFF) << 24);
        }
        catch (IOException e) {
            throw new UnexpectedApplicationState(e);
        }
    }

    private boolean isNull() {
        try {
            return read() != 0;
        }
        catch (IOException e) {
            throw new UnexpectedApplicationState(e);
        }
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
        }
        catch (UnsupportedEncodingException e) {
            throw new InvalidFormat(e);
        }
    }

    public Boolean readBoolean() {
        try {
            int i = read();
            return i == 0 ? Boolean.FALSE : i == 1 ? Boolean.TRUE : null;
        }
        catch (IOException e) {
            throw new UnexpectedApplicationState(e);
        }
    }

    public Long readLong() {
        if (isNull()) {
            return null;
        }
        return readNotNullLong();
    }

    public long readNotNullLong() {
        try {
            return ((read() & 0xFFL)) +
                   ((read() & 0xFFL) << 8) +
                   ((read() & 0xFFL) << 16) +
                   ((read() & 0xFFL) << 24) +
                   ((read() & 0xFFL) << 32) +
                   ((read() & 0xFFL) << 40) +
                   ((read() & 0xFFL) << 48) +
                   ((read() & 0xFFL) << 56);
        }
        catch (IOException e) {
            throw new UnexpectedApplicationState(e);
        }
    }

    private int read() throws IOException {
        try {
            int i = inputStream.read();
            if (i == -1) {
                throw new EOFIOFailure("eof");
            }
            count++;
            return i;
        }
        catch (EOFException e) {
            throw new EOFIOFailure(e);
        }
    }

    public byte readByte() {
        try {
            return (byte)(read());
        }
        catch (IOException e) {
            throw new UnexpectedApplicationState(e);
        }
    }

    public byte[] readBytes() {
        try {
            int length = readNotNullInt();
            if (length == -1) {
                return null;
            }
            int readed = 0;
            if (length > 512 * 1024) {
                throw new InvalidData("More than " + 512 * 1024 + " : " + length);
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
        }
        catch (IOException e) {
            throw new UnexpectedApplicationState(e);
        }
    }

    private class InputStreamFieldVisitor implements FieldVisitor {
        private GlobBuilder builder;

        public InputStreamFieldVisitor(GlobBuilder builder) {
            this.builder = builder;
        }

        public void visitInteger(IntegerField field) throws Exception {
            builder.set(field, readInteger());
        }

        public void visitDouble(DoubleField field) throws Exception {
            builder.set(field, readDouble());
        }

        public void visitString(StringField field) throws Exception {
            builder.set(field, readUtf8String());
        }

        public void visitBoolean(BooleanField field) throws Exception {
            builder.set(field, readBoolean());
        }

        public void visitBlob(BlobField field) throws Exception {
            builder.set(field, readBytes());
        }

        public void visitLong(LongField field) throws Exception {
            builder.set(field, readLong());
        }

        public Glob getValue() {
            return builder.get();
        }

    }
}
