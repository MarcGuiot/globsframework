package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public interface FieldValueVisitorWithContext<Context> {
    void visitInteger(IntegerField field, Integer value, Context context) throws Exception;

    void visitIntegerArray(IntegerArrayField field, int[] value, Context context) throws Exception;

    void visitDouble(DoubleField field, Double value, Context context) throws Exception;

    void visitDoubleArray(DoubleArrayField field, double[] value, Context context) throws Exception;

    void visitBigDecimal(BigDecimalField field, BigDecimal value, Context context) throws Exception;

    void visitBigDecimalArray(BigDecimalArrayField field, BigDecimal[] value, Context context) throws Exception;

    void visitString(StringField field, String value,  Context context) throws Exception;

    void visitStringArray(StringArrayField field, String[] value,  Context context) throws Exception;

    void visitBoolean(BooleanField field, Boolean value,  Context context) throws Exception;

    void visitBooleanArray(BooleanArrayField field, boolean[] value,  Context context) throws Exception;

    void visitLong(LongField field, Long value,  Context context) throws Exception;

    void visitLongArray(LongArrayField field, long[] value,  Context context) throws Exception;

    void visitDate(DateField field, LocalDate value,  Context context) throws Exception;

    void visitDateTime(DateTimeField field, ZonedDateTime value,  Context context) throws Exception;

    void visitBlob(BlobField field, byte[] value,  Context context) throws Exception;

    void visitGlob(GlobField field, Glob value,  Context context) throws Exception;

    void visitGlobArray(GlobArrayField field, Glob[] value,  Context context) throws Exception;

    void visitUnionGlob(GlobUnionField field, Glob value,  Context context) throws Exception;

    void visitUnionGlobArray(GlobArrayUnionField field, Glob[] value,  Context context) throws Exception;

    class AbstractFieldValueVisitor<Context> implements FieldValueVisitorWithContext<Context> {

        public void visitInteger(IntegerField field, Integer value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitIntegerArray(IntegerArrayField field, int[] value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitDouble(DoubleField field, Double value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitDoubleArray(DoubleArrayField field, double[] value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitBigDecimal(BigDecimalField field, BigDecimal value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitBigDecimalArray(BigDecimalArrayField field, BigDecimal[] value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitString(StringField field, String value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitStringArray(StringArrayField field, String[] value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitBoolean(BooleanField field, Boolean value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitBooleanArray(BooleanArrayField field, boolean[] value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitBlob(BlobField field, byte[] value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitGlob(GlobField field, Glob value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitUnionGlob(GlobUnionField field, Glob value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitUnionGlobArray(GlobArrayUnionField field, Glob[] value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitGlobArray(GlobArrayField field, Glob[] value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitLong(LongField field, Long value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitLongArray(LongArrayField field, long[] value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitDate(DateField field, LocalDate value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void visitDateTime(DateTimeField field, ZonedDateTime value, Context context) throws Exception {
            notManaged(field, value);
        }

        public void notManaged(Field field, Object value) throws Exception {
        }

    }

    class AbstractWithErrorVisitor<Context> extends AbstractFieldValueVisitor<Context> {
        public void notManaged(Field field, Object value) throws Exception {
            throw new RuntimeException(field.getFullName() + " of type " + field.getDataType() + " not managed.");
        }
    }
}
