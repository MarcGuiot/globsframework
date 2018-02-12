package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class DefaultDateTimeField extends AbstractField implements DateTimeField {
    public DefaultDateTimeField(String name, GlobType globType, int index, boolean isKeyField, int keyIndex, ZonedDateTime defaultValue) {
        super(name, globType, ZonedDateTime.class, index, keyIndex, isKeyField, defaultValue, DataType.DateTime);
    }

    public <T extends FieldVisitor> T visit(T visitor) throws Exception {
        visitor.visitDateTime(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeVisit(T visitor) {
        try {
            visitor.visitDateTime(this);
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
            visitor.visitDateTime(this, context);
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
            visitor.visitDateTime(this, (ZonedDateTime)value);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }
}
