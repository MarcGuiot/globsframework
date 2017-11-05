package org.globsframework.model.utils;

import org.globsframework.model.ChangeSetVisitor;
import org.globsframework.model.FieldValues;
import org.globsframework.model.FieldValuesWithPrevious;
import org.globsframework.model.Key;

public class DefaultChangeSetVisitor implements ChangeSetVisitor {
    public void visitCreation(Key key, FieldValues values) throws Exception {
    }

    public void visitUpdate(Key key, FieldValuesWithPrevious values) throws Exception {
    }

    public void visitDeletion(Key key, FieldValues values) throws Exception {
    }
}
