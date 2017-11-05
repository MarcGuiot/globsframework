package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.BooleanField;
import org.globsframework.metamodel.fields.FieldValueVisitor;
import org.globsframework.metamodel.fields.FieldVisitor;
import org.globsframework.metamodel.fields.FieldVisitorWithContext;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

public class DefaultBooleanField extends AbstractField implements BooleanField {
    public DefaultBooleanField(String name, GlobType globType, int index, boolean isKeyField, final int keyIndex, Boolean defaultValue) {
        super(name, globType, Boolean.class, index, keyIndex, isKeyField, defaultValue, DataType.Boolean);
    }

    public <T extends FieldVisitor> T visit(T visitor) throws Exception {
        visitor.visitBoolean(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeVisit(T visitor) {
        try {
            visitor.visitBoolean(this);
            return visitor;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public <T extends FieldVisitorWithContext<C>, C> T safeVisit(T visitor, C context) {
        try {
            visitor.visitBoolean(this, context);
            return visitor;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public void safeVisit(FieldValueVisitor visitor, Object value) {
        try {
            visitor.visitBoolean(this, (Boolean)value);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState(e);
        }
    }
}
