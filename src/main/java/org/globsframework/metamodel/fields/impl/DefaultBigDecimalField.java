package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.math.BigDecimal;

public class DefaultBigDecimalField extends AbstractField implements BigDecimalField {
    public DefaultBigDecimalField(String name, GlobType globType, int index, boolean isKeyField, int keyIndex, Long defaultValue) {
        super(name, globType, BigDecimal.class, index, keyIndex, isKeyField, defaultValue, DataType.BigDecimal);
    }

    public <T extends FieldVisitor> T visit(T visitor) throws Exception {
        visitor.visitBigDecimal(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeVisit(T visitor) {
        try {
            visitor.visitBigDecimal(this);
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
            visitor.visitBigDecimal(this, context);
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
            visitor.visitBigDecimal(this, (BigDecimal)value);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }
}