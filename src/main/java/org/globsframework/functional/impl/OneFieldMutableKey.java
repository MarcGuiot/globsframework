package org.globsframework.functional.impl;

import org.globsframework.functional.FunctionalKey;
import org.globsframework.functional.FunctionalKeyBuilder;
import org.globsframework.functional.MutableFunctionalKey;
import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.fields.FieldValueVisitor;
import org.globsframework.model.FieldValue;
import org.globsframework.utils.exceptions.ItemNotFound;

public class OneFieldMutableKey extends AbstractFieldValue<MutableFunctionalKey>
    implements MutableFunctionalKey, FunctionalKey {
    private final OneFunctionalKeyBuilder functionalKeyBuilder;
    private Object value;

    public OneFieldMutableKey(OneFunctionalKeyBuilder functionalKeyBuilder, Object value) {
        this.functionalKeyBuilder = functionalKeyBuilder;
        this.value = value;
    }

    public OneFieldMutableKey(OneFunctionalKeyBuilder functionalKeyBuilder) {
        this.functionalKeyBuilder = functionalKeyBuilder;
    }

    protected MutableFunctionalKey doSet(Field field, Object o) {
        value = o;
        return this;
    }

    protected Object doGet(Field field) {
        return value;
    }

    public FunctionalKey getShared() {
        return this;
    }

    public FunctionalKey create() {
        return new OneFieldMutableKey(functionalKeyBuilder, value);
    }

    public boolean contains(Field field) {
        return functionalKeyBuilder.getFields()[0] == field;
    }

    public int size() {
        return 1;
    }

    public <T extends Functor> T apply(T functor) throws Exception {
        functor.process(functionalKeyBuilder.getFields()[0], value);
        return functor;
    }

    public <T extends FieldValueVisitor> T accept(T functor) throws Exception {
        functionalKeyBuilder.getFields()[0].visit(functor, value);
        return functor;
    }

    public FieldValue[] toArray() {
        return new FieldValue[]{new FieldValue(functionalKeyBuilder.getFields()[0], value)};
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

        OneFieldMutableKey that = (OneFieldMutableKey)o;

        if (!functionalKeyBuilder.equals(that.functionalKeyBuilder)) {
            return false;
        }
        return value != null ? value.equals(that.value) : that.value == null;
    }

    public int hashCode() {
        int result = functionalKeyBuilder.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }


    public boolean isSet(Field field) throws ItemNotFound {
        return true;
    }


    public String toString() {
        return "OneFieldMutableKey{" +
              "functionalKeyBuilder=" + functionalKeyBuilder +
              ", value=" + value +
              '}';
    }
}
