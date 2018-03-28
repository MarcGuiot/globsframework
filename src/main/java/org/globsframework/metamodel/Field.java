package org.globsframework.metamodel;

import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.properties.PropertyHolder;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.metamodel.utils.MutableAnnotations;
import org.globsframework.utils.exceptions.InvalidParameter;

public interface Field extends PropertyHolder<Field>, MutableAnnotations<Field> {

    String getName();

    String getFullName();

    GlobType getGlobType();

    void checkValue(Object object) throws InvalidParameter;

    Class getValueClass();

    boolean isKeyField();

    Object getDefaultValue();

    boolean isRequired();

    <T extends FieldVisitor> T visit(T visitor) throws Exception;

    <T extends FieldVisitor> T safeVisit(T visitor);

    <T extends FieldVisitorWithContext<C>, C> T visit(T visitor, C context)  throws Exception;

    <T extends FieldVisitorWithContext<C>, C> T safeVisit(T visitor, C context);

    <T extends FieldVisitorWithTwoContext<C, D>, C, D> T visit(T visitor, C ctx1, D ctx2) throws Exception;

    <T extends FieldVisitorWithTwoContext<C, D>, C, D> T safeVisit(T visitor, C ctx1, D ctx2);

    void visit(FieldValueVisitor visitor, Object value) throws Exception;

    void safeVisit(FieldValueVisitor visitor, Object value);

    DataType getDataType();

    /**
     * Returns the index of the field within the containing GlobType. The order of fields
     * within a GlobType is that of the declaration. This method is mainly used for optimization purposes.
     */
    int getIndex();

    int getKeyIndex();

    boolean valueEqual(Object o1, Object o2);

    int valueHash(Object o1);

    Object normalize(Object value);

    default StringField asStringField() {
        if (!(this instanceof StringField)) {
            throw new RuntimeException(getFullName() + " is not a StringField but a " + getDataType());
        }
        return (StringField) this;
    }

    default IntegerField asIntegerField() {
        if (!(this instanceof IntegerField)) {
            throw new RuntimeException(getFullName() + " is not a IntegerField but a " + getDataType());
        }
        return (IntegerField) this;
    }

    default BooleanField asBooleanField() {
        if (!(this instanceof BooleanField)) {
            throw new RuntimeException(getFullName() + " is not a BooleanField but a " + getDataType());
        }
        return (BooleanField) this;
    }

    default DoubleField asDoubleField() {
        if (!(this instanceof DoubleField)) {
            throw new RuntimeException(getFullName() + " is not a DoubleField but a " + getDataType());
        }
        return (DoubleField) this;
    }

    String toString(Object value);
}
