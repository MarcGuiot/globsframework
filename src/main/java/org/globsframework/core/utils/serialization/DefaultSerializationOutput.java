package org.globsframework.core.utils.serialization;

import org.globsframework.core.metamodel.fields.*;
import org.globsframework.core.model.*;
import org.globsframework.core.utils.exceptions.InvalidData;
import org.globsframework.core.utils.exceptions.UnexpectedApplicationState;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.globsframework.core.utils.serialization.DefaultSerializationInput.MAX_SIZE_FOR_BYTES;
import static org.globsframework.core.utils.serialization.DefaultSerializationInput.ORG_GLOBSFRAMWORK_SERIALIZATION_MAX_LEN;

public class DefaultSerializationOutput implements SerializedOutput, ChangeSetVisitor {
    private final OutputStream outputStream;
    private final FieldValues.Functor fieldValuesFunctor = new FieldValuesFunctor();
    private final FieldValuesWithPrevious.FunctorWithPrevious fieldValuesWithPreviousFunctor = new FieldValuesWithPreviousFunctor();

    DefaultSerializationOutput(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void writeGlob(Glob glob) {
        writeUtf8String(glob.getType().getName());
        OutputStreamFieldVisitor visitor = new OutputStreamFieldVisitor(glob);
        for (Field field : glob.getType().getFields()) {
            field.safeAccept(visitor);
        }
    }

    public void writeKnowGlob(Glob glob) {
        OutputStreamFieldVisitor visitor = new OutputStreamFieldVisitor(glob);
        for (Field field : glob.getType().getFields()) {
            field.safeAccept(visitor);
        }
    }

    public void writeChangeSet(ChangeSet changeSet) {
        writeInteger(changeSet.getChangeCount());
        changeSet.safeAccept(this);
    }

    public void write(int[] values) {
        if (values == null) {
            write(-1);
        } else {
            write(values.length);
            for (int value : values) {
                write(value);
            }
        }
    }

    public void write(long[] values) {
        if (values == null) {
            write(-1);
        } else {
            write(values.length);
            for (long value : values) {
                write(value);
            }
        }
    }

    public void write(double[] values) {
        if (values == null) {
            write(-1);
        } else {
            write(values.length);
            for (double value : values) {
                write(value);
            }
        }
    }

    public void write(String[] values) {
        if (values == null) {
            write(-1);
        } else {
            write(values.length);
            for (String value : values) {
                writeUtf8String(value);
            }
        }
    }

    public void write(BigDecimal[] values) {
        if (values == null) {
            write(-1);
        } else {
            write(values.length);
            for (BigDecimal value : values) {
                write(value);
            }
        }
    }

    public void write(BigDecimal value) {
        writeUtf8String(value.toPlainString());
    }

    public void write(int value) {
        try {
            outputStream.write((value >>> 24) & 0xFF);
            outputStream.write((value >>> 16) & 0xFF);
            outputStream.write((value >>> 8) & 0xFF);
            outputStream.write((value >>> 0) & 0xFF);
//            value >>= 8;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeInteger(Integer value) {
        if (value == null) {
            writeByte(1);
        } else {
            writeByte(0);
            write(value);
        }
    }

    public void write(long value) {
        try {
            outputStream.write((byte) (value >>> 56));
            outputStream.write((byte) (value >>> 48));
            outputStream.write((byte) (value >>> 40));
            outputStream.write((byte) (value >>> 32));
            outputStream.write((byte) (value >>> 24));
            outputStream.write((byte) (value >>> 16));
            outputStream.write((byte) (value >>> 8));
            outputStream.write((byte) (value >>> 0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLong(Long value) {
        if (value == null) {
            writeByte(1);
        } else {
            writeByte(0);
            write(value);
        }
    }

    public void write(double value) {
        write(Double.doubleToLongBits(value));
    }

    public void writeDouble(Double value) {
        if (value == null) {
            writeByte(1);
        } else {
            writeByte(0);
            write(Double.doubleToLongBits(value));
        }
    }

    public void writeDate(Date date) {
        if (date == null) {
            write(-1L);
        } else {
            write(date.getTime());
        }
    }

    public void writeUtf8String(String value) {
        if (value == null) {
            writeBytes(null);
        } else {
            writeBytes(value.getBytes(StandardCharsets.UTF_8));
        }
    }

    public void write(boolean value) {
        writeByte(value ? 1 : 0);
    }

    public void writeBoolean(Boolean value) {
        if (value == null) {
            writeByte(2);
        } else {
            writeByte(value ? 1 : 0);
        }
    }

    public void write(boolean[] value) {
        try {
            if (value == null) {
                write(-1);
                return;
            }
            write(value.length);
            for (boolean b : value) {
                outputStream.write(b ? 1 : 0);
            }
        } catch (IOException e) {
            throw new UnexpectedApplicationState(e);
        }
    }

    public void writeByte(int value) {
        try {
            outputStream.write(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeByte(byte value) {
        try {
            outputStream.write(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeBytes(byte[] value) {
        try {
            if (value == null) {
                int value1 = -1;
                write(value1);
                return;
            }
            if (MAX_SIZE_FOR_BYTES != -1 && value.length > MAX_SIZE_FOR_BYTES) {
                throw new InvalidData("More than " + MAX_SIZE_FOR_BYTES + " bytes to write  (" + value.length + ") see " + ORG_GLOBSFRAMWORK_SERIALIZATION_MAX_LEN);
            }
            write(value.length);
            outputStream.write(value);
        } catch (IOException e) {
            throw new UnexpectedApplicationState(e);
        }
    }

    public void visitCreation(Key key, FieldsValueScanner values) {
        writeUtf8String(key.getGlobType().getName());
        writeValues(key.asFieldValues());
        writeByte(1);
        writeValuesWithoutKey(values);
    }

    public void visitUpdate(Key key, FieldsValueWithPreviousScanner values) {
        writeUtf8String(key.getGlobType().getName());
        writeValues(key.asFieldValues());
        writeByte(2);
        writeValuesWithPrevious(values);
    }

    public void visitDeletion(Key key, FieldsValueScanner values) {
        writeUtf8String(key.getGlobType().getName());
        writeValues(key.asFieldValues());
        writeByte(3);
        writeValuesWithoutKey(values);
    }

    private void writeValues(FieldValues values) {
        write(values.size());
        values.safeApply(fieldValuesFunctor);
    }

    private void writeValuesWithoutKey(FieldsValueScanner values) {
        write(values.size());
        values.safeApply(fieldValuesFunctor.withoutKeyField());
    }

    private class FieldValuesFunctor implements FieldValues.Functor, FieldValueVisitor {
        public void process(Field field, Object value) {
            write(field.getIndex());
            field.safeAccept(this, value);
        }

        public void visitInteger(IntegerField field, Integer value) {
            writeInteger(value);
        }

        public void visitIntegerArray(IntegerArrayField field, int[] value) {
            write(value);
        }

        public void visitDouble(DoubleField field, Double value) {
            writeDouble(value);
        }

        public void visitDoubleArray(DoubleArrayField field, double[] value) {
            write(value);
        }

        public void visitBigDecimal(BigDecimalField field, BigDecimal value) {
            write(value);
        }

        public void visitBigDecimalArray(BigDecimalArrayField field, BigDecimal[] value) {
            if (value == null) {
                write(-1);
                return;
            } else {
                write(value.length);
            }
            for (BigDecimal s : value) {
                write(s);
            }
        }

        public void visitString(StringField field, String value) {
            writeUtf8String(value);
        }

        public void visitStringArray(StringArrayField field, String[] value) {
            if (value == null) {
                write(-1);
                return;
            } else {
                write(value.length);
            }
            for (String s : value) {
                writeUtf8String(s);
            }
        }

        public void visitBoolean(BooleanField field, Boolean value) {
            writeBoolean(value);
        }

        public void visitBooleanArray(BooleanArrayField field, boolean[] value) {
            write(value);
        }

        public void visitBlob(BlobField field, byte[] value) {
            writeBytes(value);
        }

        public void visitGlob(GlobField field, Glob value) {
            if (value != null) {
                write(true);
                writeKnowGlob(value);
            } else {
                write(false);
            }
        }

        public void visitGlobArray(GlobArrayField field, Glob[] value) {
            if (value == null) {
                write(-1);
            } else {
                write(value.length);
                for (Glob glob : value) {
                    write(glob != null);
                    if (glob != null) {
                        writeKnowGlob(glob);
                    }
                }
            }
        }

        public void visitUnionGlob(GlobUnionField field, Glob value) throws Exception {
            if (value != null) {
                write(true);
                writeGlob(value);
            } else {
                write(false);
            }
        }

        public void visitUnionGlobArray(GlobArrayUnionField field, Glob[] value) throws Exception {
            if (value == null) {
                write(-1);
            } else {
                write(value.length);
                for (Glob glob : value) {
                    write(glob != null);
                    if (glob != null) {
                        writeGlob(glob);
                    }
                }
            }
        }

        public void visitLong(LongField field, Long value) {
            writeLong(value);
        }

        public void visitLongArray(LongArrayField field, long[] value) {
            write(value);
        }

        public void visitDate(DateField field, LocalDate value) {
            writeDate(value);
        }

        public void visitDateTime(DateTimeField field, ZonedDateTime value) {
            writeDateTime(value);
        }
    }

    private void writeValuesWithPrevious(FieldsValueWithPreviousScanner values) {
        write(values.size());
        values.safeApplyWithPrevious(fieldValuesWithPreviousFunctor);
    }

    public class FieldValuesWithPreviousFunctor implements FieldValuesWithPrevious.FunctorWithPrevious, FieldValueVisitor {

        public void process(Field field, Object value, Object previousValue) {
            write(field.getIndex());
            field.safeAccept(this, value);
            field.safeAccept(this, previousValue);
        }

        public void visitInteger(IntegerField field, Integer value) {
            writeInteger(value);
        }

        public void visitIntegerArray(IntegerArrayField field, int[] value) {
            write(value);
        }

        public void visitDouble(DoubleField field, Double value) {
            writeDouble(value);
        }

        public void visitDoubleArray(DoubleArrayField field, double[] value) {
            write(value);
        }

        public void visitBigDecimal(BigDecimalField field, BigDecimal value) {
            write(value);
        }

        public void visitBigDecimalArray(BigDecimalArrayField field, BigDecimal[] values) {
            write(values);
        }

        public void visitString(StringField field, String value) {
            writeUtf8String(value);
        }

        public void visitStringArray(StringArrayField field, String[] value) {
            if (value == null) {
                write(-1);
            } else {
                write(value.length);
                for (String s : value) {
                    writeUtf8String(s);
                }
            }
        }

        public void visitBoolean(BooleanField field, Boolean value) {
            writeBoolean(value);
        }

        public void visitBooleanArray(BooleanArrayField field, boolean[] values) {
            write(values);
        }

        public void visitBlob(BlobField field, byte[] value) {
            writeBytes(value);
        }

        public void visitGlob(GlobField field, Glob value) throws Exception {
            boolean isNotNull = value != null;
            write(isNotNull);
            if (isNotNull) {
                writeKnowGlob(value);
            }
        }

        public void visitGlobArray(GlobArrayField field, Glob[] value) throws Exception {
            if (value == null) {
                write(-1);
            } else {
                write(value.length);
                for (Glob glob : value) {
                    boolean isNotNull = glob != null;
                    write(isNotNull);
                    if (isNotNull) {
                        writeKnowGlob(glob);
                    }
                }
            }
        }

        public void visitUnionGlob(GlobUnionField field, Glob value) throws Exception {
            boolean isNotNull = value != null;
            write(isNotNull);
            if (isNotNull) {
                writeGlob(value);
            }
        }

        public void visitUnionGlobArray(GlobArrayUnionField field, Glob[] value) throws Exception {
            if (value == null) {
                write(-1);
            } else {
                write(value.length);
                for (Glob glob : value) {
                    boolean isNotNull = glob != null;
                    write(isNotNull);
                    if (isNotNull) {
                        writeGlob(glob);
                    }
                }
            }

        }

        public void visitLong(LongField field, Long value) {
            writeLong(value);
        }

        public void visitLongArray(LongArrayField field, long[] value) {
            write(value);
        }

        public void visitDate(DateField field, LocalDate value) {
            writeDate(value);
        }

        public void visitDateTime(DateTimeField field, ZonedDateTime value) {
            writeDateTime(value);
        }
    }

    private class OutputStreamFieldVisitor implements FieldVisitor {
        private Glob glob;

        public OutputStreamFieldVisitor(Glob glob) {
            this.glob = glob;
        }

        public void visitInteger(IntegerField field) {
            writeInteger(glob.get(field));
        }

        public void visitIntegerArray(IntegerArrayField field) {
            write(glob.get(field));
        }

        public void visitDouble(DoubleField field) {
            writeDouble(glob.get(field));
        }

        public void visitDoubleArray(DoubleArrayField field) {
            write(glob.get(field));
        }

        public void visitBigDecimal(BigDecimalField field) {
            write(glob.get(field));
        }

        public void visitBigDecimalArray(BigDecimalArrayField field) {
            write(glob.get(field));
        }

        public void visitString(StringField field) {
            writeUtf8String(glob.get(field));
        }

        public void visitStringArray(StringArrayField field) {
            write(glob.get(field));
        }

        public void visitBoolean(BooleanField field) {
            writeBoolean(glob.get(field));
        }

        public void visitBooleanArray(BooleanArrayField field) {
            write(glob.get(field));
        }

        public void visitBlob(BlobField field) {
            writeBytes(glob.get(field));
        }

        public void visitGlob(GlobField field) throws Exception {
            Glob glob = this.glob.get(field);
            write(glob != null);
            if (glob != null) {
                writeKnowGlob(glob);
            }
        }

        public void visitGlobArray(GlobArrayField field) throws Exception {
            Glob[] globs = glob.get(field);
            if (globs == null) {
                write(-1);
            } else {
                write(globs.length);
                for (Glob glob : globs) {
                    write(glob != null);
                    if (glob != null) {
                        writeKnowGlob(glob);
                    }
                }
            }

        }

        public void visitUnionGlob(GlobUnionField field) throws Exception {
            Glob glob = this.glob.get(field);
            write(glob != null);
            if (glob != null) {
                writeGlob(glob);
            }
        }

        public void visitUnionGlobArray(GlobArrayUnionField field) throws Exception {
            Glob[] globs = glob.get(field);
            if (globs == null) {
                write(-1);
            } else {
                write(globs.length);
                for (Glob glob : globs) {
                    write(glob != null);
                    if (glob != null) {
                        writeGlob(glob);
                    }
                }
            }
        }

        public void visitLong(LongField field) {
            writeLong(glob.get(field));
        }

        public void visitLongArray(LongArrayField field) {
            write(glob.get(field));
        }

        public void visitDate(DateField field) {
            writeDate(glob.get(field));
        }

        public void visitDateTime(DateTimeField field) {
            writeDateTime(glob.get(field));
        }
    }

    private void writeDate(LocalDate date) {
        if (date == null) {
            write(Integer.MIN_VALUE);
        } else {
            write(date.getYear());
            write(date.getMonthValue());
            write(date.getDayOfMonth());
        }
    }

    private void writeDateTime(ZonedDateTime date) {
        if (date == null) {
            write(Integer.MIN_VALUE);
        } else {
            write(date.getYear());
            write(date.getMonthValue());
            write(date.getDayOfMonth());
            write(date.getHour());
            write(date.getMinute());
            write(date.getSecond());
            write(date.getNano());
            writeUtf8String(date.getZone().getId());
        }
    }
}
