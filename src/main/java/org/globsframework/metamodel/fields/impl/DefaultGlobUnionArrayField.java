package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.model.Glob;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DefaultGlobUnionArrayField extends AbstractField implements GlobArrayUnionField {
    private Map<String, GlobType> targetTypes;

    public DefaultGlobUnionArrayField(String name, GlobType globType, Collection<GlobType> targetTypes,
                                      int index, boolean isKeyField, final int keyIndex) {
        super(name, globType, Glob[].class, index, keyIndex, isKeyField, null, DataType.GlobUnionArray);
        this.targetTypes = new HashMap<>();
        targetTypes.forEach(this::__add__);
    }

    public Collection<GlobType> getTargetTypes() {
        return targetTypes.values();
    }

    public void __add__(GlobType t) {
        this.targetTypes.put(t.getName(), t);
    }

    public GlobType getTargetType(String name) {
        GlobType globType = targetTypes.get(name);
        if (globType == null) {
            throw new RuntimeException("Type " + name + " not possible in " + getFullName() + " available " + targetTypes);
        }
        return globType;
    }

    public <T extends FieldVisitor> T visit(T visitor) throws Exception {
        visitor.visitUnionGlobArray(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeVisit(T visitor) {
        try {
            visitor.visitUnionGlobArray(this);
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
            visitor.visitUnionGlobArray(this, context);
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
        visitor.visitUnionGlobArray(this, context);
        return visitor;
    }

    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T visit(T visitor, C ctx1, D ctx2) throws Exception {
        visitor.visitUnionGlobArray(this, ctx1, ctx2);
        return visitor;
    }

    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T safeVisit(T visitor, C ctx1, D ctx2) {
        try {
            visitor.visitUnionGlobArray(this, ctx1, ctx2);
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
        visitor.visitUnionGlobArray(this, (Glob[])value);
    }

    public void safeVisit(FieldValueVisitor visitor, Object value) {
        try {
            visitor.visitUnionGlobArray(this, (Glob[])value);
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
            visitor.visitUnionGlobArray(this, (Glob[])value, context);
            return visitor;
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
                        isSameGlob(((Glob[]) o1), ((Glob[]) o2));
    }

    public boolean valueOrKeyEqual(Object o1, Object o2) {
        return (o1 == null) && (o2 == null) ||
                !((o1 == null) || (o2 == null)) &&
                        isSameKeyOrGlob(((Glob[]) o1), ((Glob[]) o2));
    }

    public static boolean isSameGlob(Glob[] g1, Glob[] g2) {
        if (g1.length != g2.length) {
            return false;
        }
        for (int i = 0; i < g1.length; i++) {
            Glob gg1 = g1[i];
            Glob gg2 = g2[i];
            if (gg1.getType() != gg2.getType()) {
                return false;
            }
            if (!DefaultGlobField.isSameGlob(gg1.getType(), gg1, gg2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSameKeyOrGlob(Glob[] g1, Glob[] g2) {
        if (g1.length != g2.length) {
            return false;
        }
        for (int i = 0; i < g1.length; i++) {
            Glob gg1 = g1[i];
            Glob gg2 = g2[i];
            if (gg1.getType() != gg2.getType()) {
                return false;
            }
            if (!DefaultGlobField.isSameKeyOrGlob(gg1.getType(), gg1, gg2)) {
                return false;
            }
        }
        return true;
    }

    public void checkValue(Object object) throws InvalidParameter {
        if ((object != null) && ((!(object instanceof Glob[])) || !checkType((Glob[]) object))) {
            throw new InvalidParameter("Value '" + object + "' (" + object.getClass().getName()
                    + ") is not authorized for field: " + getName() +
                    " (expected Glob)");
        }
    }

    public boolean checkType(Glob[] globs) {
        for (int i = 0; i < globs.length; i++) {
            if (globs[i] != null && !getTargetTypes().contains(globs[i].getType())) {
                return false;
            }
        }
        return true;
    }
    public void toString(StringBuilder buffer, Object value) {
        if (value == null) {
            buffer.append("null");
        }
        else {
            toString(buffer, ((Glob[]) value));
        }
    }

    public static void toString(StringBuilder stringBuilder, Glob[] values) {
        if (values.length != 0) {
            stringBuilder.append(values[0]);

            for (int i = 1, value1Length = values.length; i < value1Length; i++) {
                Glob glob = values[i];
                stringBuilder
                        .append(", ")
                        .append(glob);
            }
            stringBuilder.append("]");
        }
    }
}
