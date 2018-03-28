package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;

public interface FieldVisitor {
    void visitInteger(IntegerField field) throws Exception;

    void visitIntegerArray(IntegerArrayField field) throws Exception;

    void visitDouble(DoubleField field) throws Exception;

    void visitDoubleArray(DoubleArrayField field) throws Exception;

    void visitBigDecimal(BigDecimalField field) throws Exception;

    void visitBigDecimalArray(BigDecimalArrayField field) throws Exception;

    void visitString(StringField field) throws Exception;

    void visitStringArray(StringArrayField field) throws Exception;

    void visitBoolean(BooleanField field) throws Exception;

    void visitBooleanArray(BooleanArrayField field) throws Exception;

    void visitLong(LongField field) throws Exception;

    void visitLongArray(LongArrayField field) throws Exception;

    void visitDate(DateField field) throws Exception;

    void visitDateTime(DateTimeField field) throws Exception;

    void visitBlob(BlobField field) throws Exception;

//    void visitGlob(GlobField field) throws Exception;


    class AbstractFieldVisitor implements FieldVisitor {

        public void visitInteger(IntegerField field) throws Exception {
            notManaged(field);
        }

        public void visitIntegerArray(IntegerArrayField field) throws Exception {
            notManaged(field);
        }

        public void visitDouble(DoubleField field) throws Exception {
            notManaged(field);
        }

        public void visitDoubleArray(DoubleArrayField field) throws Exception {
            notManaged(field);
        }

        public void visitBigDecimal(BigDecimalField field) throws Exception {
            notManaged(field);
        }

        public void visitBigDecimalArray(BigDecimalArrayField field) throws Exception {
            notManaged(field);
        }

        public void visitString(StringField field) throws Exception {
            notManaged(field);
        }

        public void visitStringArray(StringArrayField field) throws Exception {
            notManaged(field);
        }

        public void visitBoolean(BooleanField field) throws Exception {
            notManaged(field);
        }

        public void visitBooleanArray(BooleanArrayField field) throws Exception {
            notManaged(field);
        }

        public void visitLong(LongField field) throws Exception {
            notManaged(field);
        }

        public void visitLongArray(LongArrayField field) throws Exception {
            notManaged(field);
        }

        public void visitDate(DateField field) throws Exception {
            notManaged(field);
        }

        public void visitDateTime(DateTimeField field) throws Exception {
            notManaged(field);
        }

        public void visitBlob(BlobField field) throws Exception {
            notManaged(field);
        }

        public void notManaged(Field field) throws Exception {
        }
    }

    class AbstractWithErrorVisitor extends AbstractFieldVisitor {
        public void notManaged(Field field) throws Exception {
            throw new RuntimeException(field.getFullName() + " of type " + field.getDataType() + " not managed.");
        }
    }

}

