package org.globsframework.core.model;

public interface ChangeSetVisitor {
    void visitCreation(Key key, FieldsValueScanner values) throws Exception;

    void visitUpdate(Key key, FieldsValueWithPreviousScanner values) throws Exception;

    void visitDeletion(Key key, FieldsValueScanner previousValues) throws Exception;
}
