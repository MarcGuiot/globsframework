package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.util.Arrays;

public class DefaultLongArrayField extends AbstractField implements LongArrayField {
    public DefaultLongArrayField(String name, GlobType globType, int index, boolean isKeyField, int keyIndex, Long defaultValue) {
        super(name, globType, long[].class, index, keyIndex, isKeyField, defaultValue, DataType.LongArray);
    }

    public <T extends FieldVisitor> T visit(T visitor) throws Exception {
        visitor.visitLongArray(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeVisit(T visitor) {
        try {
            visitor.visitLongArray(this);
            return visitor;
        } catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        } catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public <T extends FieldVisitorWithContext<C>, C> T safeVisit(T visitor, C context) {
        try {
            visitor.visitLongArray(this, context);
            return visitor;
        } catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        } catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    @Override
    public <T extends FieldVisitorWithContext<C>, C> T visit(T visitor, C context) throws Exception {
        visitor.visitLongArray(this, context);
        return visitor;
    }

    @Override
    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T visit(T visitor, C ctx1, D ctx2) throws Exception {
        visitor.visitLongArray(this, ctx1, ctx2);
        return visitor;
    }

    @Override
    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T safeVisit(T visitor, C ctx1, D ctx2) {
        try {
            visitor.visitLongArray(this, ctx1, ctx2);
            return visitor;
        } catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        } catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public void visit(FieldValueVisitor visitor, Object value) throws Exception {
        visitor.visitLongArray(this, (long[]) value);
    }

    public void safeVisit(FieldValueVisitor visitor, Object value) {
        try {
            visitor.visitLongArray(this, (long[]) value);
        } catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        } catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public boolean valueEqual(Object o1, Object o2) {
        return Arrays.equals(((long[]) o1), ((long[]) o2));
    }

    public int valueHash(Object o) {
        return Arrays.hashCode(((long[]) o));
    }

    public void toString(StringBuilder buffer, Object value) {
        if (value == null) {
            buffer.append("null");
        }
        else {
            buffer.append(Arrays.toString(((long[]) value)));
        }
    }

}
