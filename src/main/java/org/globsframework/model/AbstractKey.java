package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.impl.AbstractFieldValues;
import org.globsframework.model.utils.FieldCheck;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public abstract class AbstractKey extends AbstractFieldValues implements MutableKey {

    public boolean contains(Field field) {
        return field.getGlobType() == getGlobType() && field.isKeyField();
    }

    public <T extends FieldValueVisitor> T accept(T functor) throws Exception {
        Field[] fields = getGlobType().getKeyFields();
        for (Field field : fields) {
            field.visit(functor, doGetValue(field));
        }
        return functor;
    }

    public <T extends FieldValueVisitor> T acceptOnKeyField(T functor) throws Exception {
        Field[] keyFields = getGlobType().getKeyFields();
        for (Field keyField : keyFields) {
            keyField.visit(functor, doGetValue(keyField));
        }
        return functor;
    }

    public <T extends FieldValueVisitor> T safeAcceptOnKeyField(T functor) {
        try {
            Field[] keyFields = getGlobType().getKeyFields();
            for (Field keyField : keyFields) {
                keyField.visit(functor, doGetValue(keyField));
            }
            return functor;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends FieldValues.Functor>
    T applyOnKeyField(T functor) throws Exception {
        for (Field field : getGlobType().getKeyFields()) {
            functor.process(field, doGetValue(field));
        }
        return functor;
    }

    public <T extends FieldValues.Functor>
    T safeApplyOnKeyField(T functor) {
        try {
            return applyOnKeyField(functor);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FieldValues asFieldValues() {
        return this;
    }

    protected Object doCheckedGet(Field field) {
        FieldCheck.checkIsKeyOf(field, getGlobType());
        return doGetValue(field);
    }

    protected abstract Object doGetValue(Field field);

    public MutableKey set(DoubleField field, Double value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(DoubleField field, double value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(DoubleArrayField field, double[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(IntegerField field, Integer value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(IntegerField field, int value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(IntegerArrayField field, int[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(StringField field, String value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(StringArrayField field, String[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(BooleanField field, Boolean value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(BooleanArrayField field, boolean[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(LongField field, Long value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(LongArrayField field, long[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(LongField field, long value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(BigDecimalField field, BigDecimal value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(BigDecimalArrayField field, BigDecimal[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(BlobField field, byte[] value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(DateField field, LocalDate value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(DateTimeField field, ZonedDateTime value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(GlobField field, Glob value) throws ItemNotFound {
        setValue(field, value);
        return this;
    }

    public MutableKey set(GlobArrayField field, Glob[] values) throws ItemNotFound {
        setValue(field, values);
        return this;
    }
}
