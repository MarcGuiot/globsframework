package org.globsframework.model;

public interface ChangeSetVisitor {
    void visitCreation(Key key, FieldValues values) throws Exception;

    void visitUpdate(Key key, FieldValuesWithPrevious values) throws Exception;

    void visitDeletion(Key key, FieldValues previousValues) throws Exception;
}
