package org.globsframework.core.functional.impl;

import org.globsframework.core.functional.FunctionalKey;
import org.globsframework.core.functional.FunctionalKeyBuilder;
import org.globsframework.core.functional.MutableFunctionalKey;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.metamodel.fields.FieldValueVisitor;
import org.globsframework.core.model.FieldValue;
import org.globsframework.core.model.FieldValues;
import org.globsframework.core.utils.exceptions.ItemNotFound;

import java.util.Arrays;

public class ManyFieldsMutableKey extends AbstractFieldValue<MutableFunctionalKey>
        implements MutableFunctionalKey, FunctionalKey {
    private final ManyFunctionalKeyBuilder functionalKeyBuilder;
    private Object values[];

    ManyFieldsMutableKey(ManyFunctionalKeyBuilder functionalKeyBuilder) {
        this.functionalKeyBuilder = functionalKeyBuilder;
        values = new Object[functionalKeyBuilder.fields.length];
    }

    ManyFieldsMutableKey(ManyFunctionalKeyBuilder functionalKeyBuilder, FieldValues fieldValues) {
        this.functionalKeyBuilder = functionalKeyBuilder;
        Field[] fields = functionalKeyBuilder.fields;
        values = new Object[fields.length];
        int i = 0;
        for (Field field : fields) {
            values[i] = fieldValues.getValue(field);
            i++;
        }
    }

    private ManyFieldsMutableKey(ManyFunctionalKeyBuilder functionalKeyBuilder, Object[] values) {
        this.functionalKeyBuilder = functionalKeyBuilder;
        this.values = values;
    }

    protected MutableFunctionalKey doSet(Field field, Object o) {
        values[functionalKeyBuilder.index[field.getIndex()]] = o;
        return this;
    }

    protected Object doGet(Field field) {
        return values[functionalKeyBuilder.index[field.getIndex()]];
    }

    public FunctionalKey getShared() {
        return this;
    }

    public FunctionalKey create() {
        return new ManyFieldsMutableKey(functionalKeyBuilder, Arrays.copyOf(values, values.length));
    }

    public boolean contains(Field field) {
        return functionalKeyBuilder.index[field.getIndex()] >= 0;
    }

    public int size() {
        return values.length;
    }

    public <T extends FieldValueVisitor> T accept(T functor) throws Exception {
        Field[] fields = functionalKeyBuilder.fields;
        for (int i = 0; i < values.length; i++) {
            fields[i].accept(functor, values[i]);
        }
        return functor;
    }

    public <T extends Functor> T apply(T functor) throws Exception {
        Field[] fields = functionalKeyBuilder.fields;
        for (int i = 0; i < values.length; i++) {
            functor.process(fields[i], values[i]);
        }
        return functor;
    }

    public FieldValue[] toArray() {
        FieldValue fieldValue[] = new FieldValue[values.length];
        for (int i = 0; i < values.length; i++) {
            fieldValue[i] = FieldValue.value(functionalKeyBuilder.fields[i], values[i]);
        }
        return fieldValue;
    }

    public FunctionalKeyBuilder getBuilder() {
        return functionalKeyBuilder;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ManyFieldsMutableKey that = (ManyFieldsMutableKey) o;

        if (!functionalKeyBuilder.equals(that.functionalKeyBuilder)) {
            return false;
        }
        return Arrays.equals(values, that.values);
    }

    public int hashCode() {
        int result = functionalKeyBuilder.hashCode();
        for (Object value : values) {
            result = 31 * result + (value == null ? 0 : value.hashCode());
        }
        return result;
    }

    public boolean isSet(Field field) throws ItemNotFound {
        return true;
    }

    public String toString() {
        return "ManyFieldsMutableKey{" +
                "functionalKeyBuilder=" + functionalKeyBuilder +
                ", values=" + Arrays.toString(values) +
                '}';
    }
}
