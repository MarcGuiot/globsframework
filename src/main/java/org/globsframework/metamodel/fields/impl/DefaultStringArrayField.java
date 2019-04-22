package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.util.Arrays;

public class DefaultStringArrayField extends AbstractField implements StringArrayField {

    public DefaultStringArrayField(String name, GlobType globType, int index, boolean isKeyField, int keyIndex, String defaultValue) {
        super(name, globType, String[].class, index, keyIndex, isKeyField, defaultValue, DataType.StringArray);
    }

    public <T extends FieldVisitor> T visit(T visitor) throws Exception {
        visitor.visitStringArray(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeVisit(T visitor) {
        try {
            visitor.visitStringArray(this);
            return visitor;
        } catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        } catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public <T extends FieldVisitorWithContext<C>, C> T safeVisit(T visitor, C context) {
        try {
            visitor.visitStringArray(this, context);
            return visitor;
        } catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        } catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    @Override
    public <T extends FieldVisitorWithContext<C>, C> T visit(T visitor, C context) throws Exception {
        visitor.visitStringArray(this, context);
        return visitor;
    }

    @Override
    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T visit(T visitor, C ctx1, D ctx2) throws Exception {
        visitor.visitStringArray(this, ctx1, ctx2);
        return visitor;
    }

    @Override
    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T safeVisit(T visitor, C ctx1, D ctx2) {
        try {
            visitor.visitStringArray(this, ctx1, ctx2);
            return visitor;
        } catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        } catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public void visit(FieldValueVisitor visitor, Object value) throws Exception {
        visitor.visitStringArray(this, (String[]) value);
    }

    public void safeVisit(FieldValueVisitor visitor, Object value) {
        try {
            visitor.visitStringArray(this, (String[]) value);
        } catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        } catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public boolean valueEqual(Object o1, Object o2) {
        return Arrays.equals(((String[]) o1), ((String[]) o2));
    }

    public int valueHash(Object o) {
        return Arrays.hashCode(((String[]) o));
    }

    public String toString(Object value, String offset) {
        return Arrays.toString(((String[]) value));
    }

}
