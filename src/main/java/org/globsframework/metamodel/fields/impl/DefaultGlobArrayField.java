package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.model.Glob;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.util.Collection;
import java.util.Collections;

public class DefaultGlobArrayField extends AbstractField implements GlobArrayField {
    private final GlobType targetType;

    public DefaultGlobArrayField(String name, GlobType globType, GlobType targetType,
                                 int index, boolean isKeyField, final int keyIndex) {
        super(name, globType, Glob[].class, index, keyIndex, isKeyField, null, DataType.GlobArray);
        this.targetType = targetType;
    }

    public static boolean isSameGlob(GlobType type, Glob[] g1, Glob[] g2) {
        if (g1.length != g2.length) {
            return false;
        }
        for (int i = 0; i < g1.length; i++) {
            if (!DefaultGlobField.isSameGlob(type, g1[i], g2[i])) {
                return false;
            }
        }
        return true;
    }

    public GlobType getType() {
        return targetType;
    }

    public Collection<GlobType> getTypes() {
        return Collections.singletonList(getType());
    }

    public <T extends FieldVisitor> T visit(T visitor) throws Exception {
        visitor.visitGlobArray(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeVisit(T visitor) {
        try {
            visitor.visitGlobArray(this);
            return visitor;
        } catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        } catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public <T extends FieldVisitorWithContext<C>, C> T safeVisit(T visitor, C context) {
        try {
            visitor.visitGlobArray(this, context);
            return visitor;
        } catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        } catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public <T extends FieldVisitorWithContext<C>, C> T visit(T visitor, C context) throws Exception {
        visitor.visitGlobArray(this, context);
        return visitor;
    }

    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T visit(T visitor, C ctx1, D ctx2) throws Exception {
        visitor.visitGlobArray(this, ctx1, ctx2);
        return visitor;
    }

    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T safeVisit(T visitor, C ctx1, D ctx2) {
        try {
            visitor.visitGlobArray(this, ctx1, ctx2);
            return visitor;
        } catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        } catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public void visit(FieldValueVisitor visitor, Object value) throws Exception {
        visitor.visitGlobArray(this, (Glob[]) value);
    }

    public void safeVisit(FieldValueVisitor visitor, Object value) {
        try {
            visitor.visitGlobArray(this, (Glob[]) value);
        } catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        } catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public boolean valueEqual(Object o1, Object o2) {
        return (o1 == null) && (o2 == null) ||
                !((o1 == null) || (o2 == null)) &&
                        isSameGlob(getType(), ((Glob[]) o1), ((Glob[]) o2));
    }

    public void checkValue(Object object) throws InvalidParameter {
        if ((object != null) && (!(object instanceof Glob[]))) {
            throw new InvalidParameter("Value '" + object + "' (" + object.getClass().getName()
                    + ") is not authorized for field: " + getName() +
                    " (expected Glob)");
        }
    }

    public String toString(Object value, String offset) {
        if (value == null) {
            return "null";
        } else {
            return DefaultArrayGlobUnionField.toString(offset, ((Glob[]) value));
        }
    }

}