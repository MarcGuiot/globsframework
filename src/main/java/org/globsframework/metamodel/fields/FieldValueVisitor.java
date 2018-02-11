package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.impl.DefaultBigDecimalField;
import org.globsframework.metamodel.fields.impl.DefaultBooleanArrayField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public interface FieldValueVisitor {
    void visitInteger(IntegerField field, Integer value) throws Exception;

    void visitIntegerArray(IntegerArrayField field, int[] value) throws Exception;

    void visitDouble(DoubleField field, Double value) throws Exception;

    void visitDoubleArray(DoubleArrayField field, double[] value) throws Exception;

    void visitBigDecimal(BigDecimalField field, BigDecimal value) throws Exception;

    void visitBigDecimalArray(BigDecimalArrayField field, BigDecimal[] value) throws Exception;

    void visitString(StringField field, String value) throws Exception;

    void visitStringArray(StringArrayField field, String[] value) throws Exception;

    void visitBoolean(BooleanField field, Boolean value) throws Exception;

    void visitBooleanArray(BooleanArrayField field, boolean[] value) throws Exception;

    void visitLong(LongField field, Long value) throws Exception;

    void visitLongArray(LongArrayField field, long[] value) throws Exception;

    void visitDate(DateField field, LocalDate date) throws Exception;

    void visitDateTime(DateTimeField field, ZonedDateTime zonedDateTime) throws Exception;

    void visitBlob(BlobField field, byte[] value) throws Exception;


    class AbstractFieldValueVisitor implements FieldValueVisitor {

        public void visitInteger(IntegerField field, Integer value) throws Exception {
            notManaged(field);
        }

        public void visitIntegerArray(IntegerArrayField field, int[] value) throws Exception {
            notManaged(field);
        }

        public void visitDouble(DoubleField field, Double value) throws Exception {
            notManaged(field);
        }

        public void visitDoubleArray(DoubleArrayField field, double[] value) throws Exception {
            notManaged(field);
        }

        public void visitBigDecimal(BigDecimalField field, BigDecimal value) throws Exception {
            notManaged(field);
        }

        public void visitBigDecimalArray(BigDecimalArrayField field, BigDecimal[] value) throws Exception {
            notManaged(field);
        }

        public void visitString(StringField field, String value) throws Exception {
            notManaged(field);
        }

        public void visitStringArray(StringArrayField field, String[] value) throws Exception {
            notManaged(field);
        }

        public void visitBoolean(BooleanField field, Boolean value) throws Exception {
            notManaged(field);
        }

        public void visitBooleanArray(BooleanArrayField field, boolean[] value) throws Exception {
            notManaged(field);
        }

        public void visitBlob(BlobField field, byte[] value) throws Exception {
            notManaged(field);
        }

        public void visitLong(LongField field, Long value) throws Exception {
            notManaged(field);
        }

        public void visitLongArray(LongArrayField field, long[] value) throws Exception {
            notManaged(field);
        }

        public void visitDate(DateField field, LocalDate date) throws Exception {
            notManaged(field);
        }

        public void visitDateTime(DateTimeField field, ZonedDateTime zonedDateTime) throws Exception {
            notManaged(field);
        }

        public void notManaged(Field field) throws Exception {
        }

    }
    class AbstractWithErrorVisitor<C> extends AbstractFieldValueVisitor {
        public void notManaged(Field field) throws Exception {
            throw new RuntimeException(field.getFullName() + " of type " + field.getDataType() + " not managed.");
        }
    }
}
