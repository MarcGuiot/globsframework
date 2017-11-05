package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.DoubleField;
import org.globsframework.metamodel.fields.FieldValueVisitor;
import org.globsframework.metamodel.fields.FieldVisitor;
import org.globsframework.metamodel.fields.FieldVisitorWithContext;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

public class DefaultDoubleField extends AbstractField implements DoubleField {

    public DefaultDoubleField(String name, GlobType globType,
                              int index, boolean isKeyField, final int keyIndex, Double defaultValue) {
        super(name, globType, Double.class, index, keyIndex, isKeyField, defaultValue, DataType.Double);
    }

    public <T extends FieldVisitor> T visit(T visitor) throws Exception {
        visitor.visitDouble(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeVisit(T visitor) {
        try {
            visitor.visitDouble(this);
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
            visitor.visitDouble(this, context);
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
            visitor.visitDouble(this, (Double)value);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public boolean valueEqual(Object o1, Object o2) {
        return (o1 == null) && (o2 == null) ||
               !((o1 == null) || (o2 == null)) &&
               ((Double)o1).doubleValue() == ((Double)o2).doubleValue();
    }
}
