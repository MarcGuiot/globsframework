package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public interface FieldValues extends FieldValuesAccessor {

    boolean contains(Field field);

    int size();

    <T extends Functor>
    T apply(T functor) throws Exception;

    <T extends Functor>
    T safeApply(T functor);

    FieldValue[] toArray();

    interface Functor {
        void process(Field field, Object value) throws Exception;
    }

    FieldValues EMPTY = new FieldValues() {
        public boolean contains(Field field) {
            return false;
        }

        public int size() {
            return 0;
        }

        public <T extends Functor>
        T apply(T functor) throws Exception {
            return functor;
        }

        public <T extends Functor>
        T safeApply(T functor) {
            return functor;
        }

        public Double get(DoubleField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public double get(DoubleField field, double valueIfNull) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public double[] get(DoubleArrayField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public Integer get(IntegerField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public int get(IntegerField field, int valueIfNull) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public int[] get(IntegerArrayField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public String get(StringField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public String[] get(StringArrayField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public Boolean get(BooleanField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public Boolean get(BooleanField field, boolean defaultIfNull) {
            throw new ItemNotFound(field.getName());
        }

        public boolean[] get(BooleanArrayField field) {
            throw new ItemNotFound(field.getName());
        }

        public boolean isTrue(BooleanField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public boolean isNull(Field field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public Object getValue(Field field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public Long get(LongField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public long get(LongField field, long valueIfNull) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public long[] get(LongArrayField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public BigDecimal get(BigDecimalField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public BigDecimal[] get(BigDecimalArrayField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public LocalDate get(DateField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public ZonedDateTime get(DateTimeField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public byte[] get(BlobField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public FieldValue[] toArray() {
            return new FieldValue[0];
        }
    };
}
