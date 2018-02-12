package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.util.Arrays;

public class DefaultBooleanArrayField extends AbstractField implements BooleanArrayField {

    public DefaultBooleanArrayField(String name, GlobType globType,
                                    int index, boolean isKeyField, final int keyIndex, Boolean defaultValue) {
        super(name, globType, boolean[].class, index, keyIndex, isKeyField, defaultValue, DataType.BooleanArray);
    }

    public <T extends FieldVisitor> T visit(T visitor) throws Exception {
        visitor.visitBooleanArray(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeVisit(T visitor) {
        try {
            visitor.visitBooleanArray(this);
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
            visitor.visitBooleanArray(this, context);
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
            visitor.visitBooleanArray(this, (boolean[])value);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public boolean valueEqual(Object o1, Object o2) {
        return Arrays.equals(((boolean[])o1), ((boolean[])o2));
    }

    public int valueHash(Object o) {
        return Arrays.hashCode(((boolean[])o));
    }

}
