package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.annotations.RequiredAnnotationType;
import org.globsframework.metamodel.impl.DefaultAnnotations;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.utils.Utils;
import org.globsframework.utils.exceptions.InvalidParameter;

import java.util.LinkedHashMap;

abstract public class AbstractField extends DefaultAnnotations {
    private final int index;
    private final int keyIndex;
    private final GlobType globType;
    private final String name;
    private final Class valueClass;
    private final Object defaultValue;
    private final DataType dataType;
    private final boolean keyField;

    protected AbstractField(String name, GlobType globType,
                            Class valueClass, int index, int keyIndex, boolean isKeyField,
                            Object defaultValue, DataType dataType, LinkedHashMap<Key, Glob> annotations) {
        super(annotations);
        this.keyIndex = keyIndex;
        this.defaultValue = defaultValue;
        this.name = name;
        this.keyField = isKeyField;
        this.index = index;
        this.globType = globType;
        this.valueClass = valueClass;
        this.dataType = dataType;
    }

    public Object normalize(Object value) {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return globType.getName() + "." + name;
    }

    public GlobType getGlobType() {
        return globType;
    }

    public int getIndex() {
        return index;
    }

    public int getKeyIndex() {
        return keyIndex;
    }

    public boolean isKeyField() {
        return keyField;
    }

    public boolean isRequired() {
        return hasAnnotation(RequiredAnnotationType.UNIQUE_KEY);
    }

    public DataType getDataType() {
        return dataType;
    }

    public void checkValue(Object object) throws InvalidParameter {
        if ((object != null) && (!valueClass.equals(object.getClass()))) {
            throw new InvalidParameter("Value '" + object + "' (" + object.getClass().getName()
                    + ") is not authorized for field: " + getName() +
                    " (expected " + valueClass.getName() + ")");
        }
    }

    public Class getValueClass() {
        return valueClass;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public String toString() {
        return globType.getName() + "." + name;
    }

    public boolean valueEqual(Object o1, Object o2) {
        return Utils.equal(o1, o2);
    }

    public boolean valueOrKeyEqual(Object o1, Object o2) {
        return valueEqual(o1, o2);
    }

    public int valueHash(Object o) {
        return o.hashCode();
    }

    public void toString(StringBuilder buffer, Object value) {
        buffer.append(value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractField other = (AbstractField) o;
        return globType.equals(other.globType) && name.equals(other.name);
    }
}
