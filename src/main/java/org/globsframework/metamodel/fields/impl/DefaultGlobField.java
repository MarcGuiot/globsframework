package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.model.Glob;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.util.Collection;
import java.util.Collections;

public class DefaultGlobField extends AbstractField implements GlobField {
    private final GlobType targetType;

    public DefaultGlobField(String name, GlobType globType, GlobType targetType,
                            int index, boolean isKeyField, final int keyIndex) {
        super(name, globType, Glob.class, index, keyIndex, isKeyField, null, DataType.Glob);
        this.targetType = targetType;
    }

    public GlobType getTargetType() {
        return targetType;
    }

    public <T extends FieldVisitor> T accept(T visitor) throws Exception {
        visitor.visitGlob(this);
        return visitor;
    }

    public <T extends FieldVisitor> T safeAccept(T visitor) {
        try {
            visitor.visitGlob(this);
            return visitor;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public <T extends FieldVisitorWithContext<C>, C> T safeAccept(T visitor, C context) {
        try {
            visitor.visitGlob(this, context);
            return visitor;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public <T extends FieldVisitorWithContext<C>, C> T accept(T visitor, C context) throws Exception {
        visitor.visitGlob(this, context);
        return visitor;
    }

    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T accept(T visitor, C ctx1, D ctx2) throws Exception {
        visitor.visitGlob(this, ctx1, ctx2);
        return visitor;
    }

    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T safeAccept(T visitor, C ctx1, D ctx2) {
        try {
            visitor.visitGlob(this, ctx1, ctx2);
            return visitor;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }

    public void accept(FieldValueVisitor visitor, Object value) throws Exception {
        visitor.visitGlob(this, (Glob)value);
    }

    public void safeAccept(FieldValueVisitor visitor, Object value) {
        try {
            visitor.visitGlob(this, (Glob)value);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("On " + this, e);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState("On " + this, e);
        }
    }
    public <T extends FieldValueVisitorWithContext<Context>, Context> T safeAcceptValue(T visitor, Object value, Context context) {
        try {
            visitor.visitGlob(this, (Glob)value, context);
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
                !((o1 == null) || (o2 == null)) && isSameGlob(getTargetType(), (Glob)o1, (Glob)o2);
    }

    public boolean valueOrKeyEqual(Object o1, Object o2) {
        return (o1 == null) && (o2 == null) ||
                !((o1 == null) || (o2 == null)) && isSameKeyOrGlob(getTargetType(), (Glob)o1, (Glob)o2);
    }

    public void checkValue(Object object) throws InvalidParameter {
        if ((object != null) && ((!(object instanceof Glob)) || ((Glob) object).getType() != getTargetType())) {
            throw new InvalidParameter("Value '" + object + "' (" + object.getClass().getName()
                    + ") is not authorized for field: " + getName() +
                    " (expected Glob)");
        }
    }

    public static boolean isSameGlob(GlobType type, Glob g1, Glob g2) {
        Field[] fields = type.getFields();
        for (Field field : fields) {
            if (!field.valueEqual(g1.getValue(field), g2.getValue(field))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSameKeyOrGlob(GlobType type, Glob g1, Glob g2) {
        Field[] keyFields = type.getKeyFields();
        Field[] fields = keyFields.length == 0 ? type.getFields() : keyFields;
        for (Field field : fields) {
            if (!field.valueEqual(g1.getValue(field), g2.getValue(field))) {
                return false;
            }
        }
        return true;
    }

    public Collection<GlobType> getTypes() {
        return Collections.singletonList(getTargetType());
    }
}
