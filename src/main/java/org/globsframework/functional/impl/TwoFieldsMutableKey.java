package org.globsframework.functional.impl;

import org.globsframework.functional.FunctionalKey;
import org.globsframework.functional.FunctionalKeyBuilder;
import org.globsframework.functional.MutableFunctionalKey;
import org.globsframework.metamodel.Field;
import org.globsframework.model.FieldValue;

public class TwoFieldsMutableKey extends AbstractFieldValue<MutableFunctionalKey>
    implements MutableFunctionalKey, FunctionalKey {
    private final TwoFunctionalKeyBuilder functionalKeyBuilder;
    private Object value1;
    private Object value2;

    TwoFieldsMutableKey(TwoFunctionalKeyBuilder functionalKeyBuilder) {
        this.functionalKeyBuilder = functionalKeyBuilder;
    }

    TwoFieldsMutableKey(TwoFunctionalKeyBuilder functionalKeyBuilder, Object value1, Object value2) {
        this.functionalKeyBuilder = functionalKeyBuilder;
        this.value1 = value1;
        this.value2 = value2;
    }

    protected MutableFunctionalKey doSet(Field field, Object o) {
        if (functionalKeyBuilder.field1 == field) {
            value1 = o;
        }
        else {
            value2 = o;
        }
        return this;
    }

    protected Object doGet(Field field) {
        return field == functionalKeyBuilder.field1 ? value1 : value2;
    }

    public FunctionalKey getShared() {
        return this;
    }

    public FunctionalKey create() {
        return new TwoFieldsMutableKey(functionalKeyBuilder,
                                       value1, value2);
    }

    public boolean contains(Field field) {
        return field == functionalKeyBuilder.field1 ||
               field == functionalKeyBuilder.field2;
    }

    public int size() {
        return 2;
    }

    public <T extends Functor> T apply(T functor) throws Exception {
        functor.process(functionalKeyBuilder.field1, value1);
        functor.process(functionalKeyBuilder.field2, value2);
        return functor;
    }

    public FieldValue[] toArray() {
        return new FieldValue[]{FieldValue.value(functionalKeyBuilder.field1, value1),
                                FieldValue.value(functionalKeyBuilder.field2, value2)};
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

        TwoFieldsMutableKey that = (TwoFieldsMutableKey)o;

        if (!functionalKeyBuilder.equals(that.functionalKeyBuilder)) {
            return false;
        }
        if (value1 != null ? !value1.equals(that.value1) : that.value1 != null) {
            return false;
        }
        return value2 != null ? value2.equals(that.value2) : that.value2 == null;
    }

    public int hashCode() {
        int result = functionalKeyBuilder.hashCode();
        result = 31 * result + (value1 != null ? value1.hashCode() : 0);
        result = 31 * result + (value2 != null ? value2.hashCode() : 0);
        return result;
    }
}
