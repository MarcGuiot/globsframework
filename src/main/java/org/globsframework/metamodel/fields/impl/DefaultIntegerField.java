package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

public class DefaultIntegerField extends AbstractField implements IntegerField {
    public DefaultIntegerField(String name, GlobType globType, int index, boolean isKeyField, int keyIndex, Integer defaultValue) {
        super(name, globType, Integer.class, index, keyIndex, isKeyField, defaultValue, DataType.Integer);
    }

    public <T extends FieldVisitor> T visit(T visitor) throws Exception {
        visitor.visitInteger(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeVisit(T visitor) {
        try {
            visitor.visitInteger(this);
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
            visitor.visitInteger(this, context);
            return visitor;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    @Override
    public <T extends FieldVisitorWithContext<C>, C> T visit(T visitor, C context) throws Exception {
        visitor.visitInteger(this, context);
        return visitor;
    }

    @Override
    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T visit(T visitor, C ctx1, D ctx2) throws Exception {
        visitor.visitInteger(this, ctx1, ctx2);
        return visitor;
    }

    @Override
    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T safeVisit(T visitor, C ctx1, D ctx2) {
        try {
            visitor.visitInteger(this, ctx1, ctx2);
            return visitor;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public void visit(FieldValueVisitor visitor, Object value) throws Exception {
        visitor.visitInteger(this, (Integer)value);
    }

    public void safeVisit(FieldValueVisitor visitor, Object value) {
        try {
            visitor.visitInteger(this, (Integer)value);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }
    public <T extends FieldValueVisitorWithContext<Context>, Context> T safeVisitValue(T visitor, Object value, Context context) {
        try {
            visitor.visitInteger(this, (Integer)value, context);
            return visitor;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

}
