package org.globsframework.core.model.utils;

import org.globsframework.core.model.ChangeSetVisitor;
import org.globsframework.core.model.FieldsValueScanner;
import org.globsframework.core.model.FieldsValueWithPreviousScanner;
import org.globsframework.core.model.Key;

public class DefaultChangeSetVisitor implements ChangeSetVisitor {
    public void visitCreation(Key key, FieldsValueScanner values) throws Exception {
    }

    public void visitUpdate(Key key, FieldsValueWithPreviousScanner values) throws Exception {
    }

    public void visitDeletion(Key key, FieldsValueScanner values) throws Exception {
    }
}
