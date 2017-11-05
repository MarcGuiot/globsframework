package org.globsframework.metamodel.fields;

import org.globsframework.metamodel.Field;

public interface FieldVisitorWithContext<C> {
    void visitInteger(IntegerField field, C context) throws Exception;

    void visitDouble(DoubleField field, C context) throws Exception;

    void visitString(StringField field, C context) throws Exception;

    void visitBoolean(BooleanField field, C context) throws Exception;

    void visitLong(LongField field, C context) throws Exception;

    void visitBlob(BlobField field, C context) throws Exception;

    class AbstractFieldVisitor<C> implements FieldVisitorWithContext<C> {

        public void visitInteger(IntegerField field, C context) throws Exception {
            notManaged(field, context);
        }

        public void visitDouble(DoubleField field, C context) throws Exception {
            notManaged(field, context);
        }

        public void visitString(StringField field, C context) throws Exception {
            notManaged(field, context);
        }

        public void visitBoolean(BooleanField field, C context) throws Exception {
            notManaged(field, context);
        }

        public void visitLong(LongField field, C context) throws Exception {
            notManaged(field, context);
        }

        public void visitBlob(BlobField field, C context) throws Exception {
            notManaged(field, context);
        }

        public void notManaged(Field field, C context) throws Exception {
        }
    }

    class AbstractWithErrorVisitor<C> extends AbstractFieldVisitor<C> {
        public void notManaged(Field field, C context) throws Exception {
            throw new RuntimeException(field.getFullName() + " of type " + field.getDataType() + " not managed.");
        }
    }

}

