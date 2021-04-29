package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.math.BigDecimal;
import java.util.Arrays;

public class DefaultBigDecimalArrayField extends AbstractField implements BigDecimalArrayField {
    public DefaultBigDecimalArrayField(String name, GlobType globType, int index, boolean isKeyField, int keyIndex, BigDecimal defaultValue) {
        super(name, globType, BigDecimal[].class, index, keyIndex, isKeyField, defaultValue, DataType.BigDecimalArray);
    }

    public <T extends FieldVisitor> T visit(T visitor) throws Exception {
        visitor.visitBigDecimalArray(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeVisit(T visitor) {
        try {
            visitor.visitBigDecimalArray(this);
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
            visitor.visitBigDecimalArray(this, context);
            return visitor;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public <T extends FieldVisitorWithContext<C>, C> T visit(T visitor, C context) throws Exception {
        visitor.visitBigDecimalArray(this, context);
        return visitor;
    }

    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T visit(T visitor, C ctx1, D ctx2) throws Exception {
        visitor.visitBigDecimalArray(this, ctx1, ctx2);
        return visitor;
    }

    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T safeVisit(T visitor, C ctx1, D ctx2) {
        try {
            visitor.visitBigDecimalArray(this, ctx1, ctx2);
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
        visitor.visitBigDecimalArray(this, (BigDecimal[])value);
    }

    public void safeVisit(FieldValueVisitor visitor, Object value) {
        try {
            visitor.visitBigDecimalArray(this, (BigDecimal[])value);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public boolean valueEqual(Object o1, Object o2) {
        return Arrays.equals(((BigDecimal[])o1), ((BigDecimal[])o2));
    }

    public int valueHash(Object o) {
        return Arrays.hashCode(((BigDecimal[])o));
    }

    public void toString(StringBuilder buffer, Object value) {
        if (value == null) {
            buffer.append("null");
        }
        else {
            buffer.append(Arrays.toString(((BigDecimal[]) value)));
        }
    }
}
