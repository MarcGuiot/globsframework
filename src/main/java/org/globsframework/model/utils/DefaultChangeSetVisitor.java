package org.globsframework.model.utils;

import org.globsframework.model.*;

public class DefaultChangeSetVisitor implements ChangeSetVisitor {
    public void visitCreation(Key key, FieldsValueScanner values) throws Exception {
    }

    public void visitUpdate(Key key, FieldsValueWithPreviousScanner values) throws Exception {
    }

    public void visitDeletion(Key key, FieldsValueScanner values) throws Exception {
    }
}
