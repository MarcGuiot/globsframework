package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

public class DefaultStringField extends AbstractField implements StringField {

    public DefaultStringField(String name, GlobType globType, int index, boolean isKeyField, int keyIndex, String defaultValue) {
        super(name, globType, String.class, index, keyIndex, isKeyField, defaultValue, DataType.String);
    }

    public <T extends FieldVisitor> T visit(T visitor) throws Exception {
        visitor.visitString(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeVisit(T visitor) {
        try {
            visitor.visitString(this);
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
        visitor.visitString(this, context);
        return visitor;
    }

    public <T extends FieldVisitorWithContext<C>, C> T safeVisit(T visitor, C context) {
        try {
            visitor.visitString(this, context);
            return visitor;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T visit(T visitor, C ctx1, D ctx2) throws Exception {
        visitor.visitString(this, ctx1, ctx2);
        return visitor;
    }

    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T safeVisit(T visitor, C ctx1, D ctx2) {
        try {
            visitor.visitString(this, ctx1, ctx2);
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
        visitor.visitString(this, (String)value);
    }

    public void safeVisit(FieldValueVisitor visitor, Object value) {
        try {
            visitor.visitString(this, (String)value);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public void toString(StringBuilder buffer, Object value) {
        if (value == null) {
            buffer.append("null");
        }
        else {
            buffer.append("\"").append(escapeString((String) value)).append("\"");
        }
    }

    private String escapeString(String value) {
        return value.contains("\"") ? value.replaceAll("\"", "'") : value;
    }
}
