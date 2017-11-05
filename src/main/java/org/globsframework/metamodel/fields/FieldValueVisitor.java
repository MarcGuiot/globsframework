package org.globsframework.metamodel.fields;

public interface FieldValueVisitor {
    void visitInteger(IntegerField field, Integer value) throws Exception;

    void visitDouble(DoubleField field, Double value) throws Exception;

    void visitString(StringField field, String value) throws Exception;

    void visitBoolean(BooleanField field, Boolean value) throws Exception;

    void visitLong(LongField field, Long value) throws Exception;

    void visitBlob(BlobField field, byte[] value) throws Exception;

    class AbstractFieldValueVisitor implements FieldValueVisitor {

        public void visitInteger(IntegerField field, Integer value) throws Exception {

        }

        public void visitDouble(DoubleField field, Double value) throws Exception {

        }

        public void visitString(StringField field, String value) throws Exception {

        }

        public void visitBoolean(BooleanField field, Boolean value) throws Exception {

        }


        public void visitBlob(BlobField field, byte[] value) throws Exception {

        }

        public void visitLong(LongField field, Long value) throws Exception {

        }

    }

}
