package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.format.GlobPrinter;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public interface FieldValues extends FieldValuesAccessor {

    FieldValues EMPTY = new FieldValues() {
        public boolean contains(Field field) {
            return false;
        }

        public int size() {
            return 0;
        }

        public <T extends Functor>
        T apply(T functor) {
            return functor;
        }

        public <T extends Functor>
        T safeApply(T functor) {
            return functor;
        }

        public <T extends FieldValueVisitor> T accept(T functor) {
            return functor;
        }

        public <T extends FieldValueVisitor> T safeAccept(T functor) {
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

        public Glob get(GlobField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public Glob[] get(GlobArrayField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public Glob get(GlobUnionField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public Glob[] get(GlobArrayUnionField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public FieldValue[] toArray() {
            return new FieldValue[0];
        }
    };
    Glob[] EMPTYARRAY = new Glob[0];

    boolean contains(Field field);

    int size();

    <T extends Functor>
    T apply(T functor) throws Exception;

    <T extends Functor>
    T safeApply(T functor);

    <T extends FieldValueVisitor>
    T accept(T functor) throws Exception;

    <T extends FieldValueVisitor>
    T safeAccept(T functor);

    FieldValue[] toArray();

    default Glob[] getOrEmpty(GlobArrayUnionField field){
        Glob[] globs = get(field);
        return globs != null ? globs : EMPTYARRAY;
    }

    default Glob[] getOrEmpty(GlobArrayField field){
        Glob[] globs = get(field);
        return globs != null ? globs : EMPTYARRAY;
    }

    default String getNotNull(StringField field) {
        String value = get(field);
        if (value == null) {
            throw new NullPointerException(field.getFullName() + " should not be null " + GlobPrinter.toString(this));
        }
        return value;
    }

    default Glob[] getNotNull(GlobArrayUnionField field) {
        Glob[] value = get(field);
        if (value == null) {
            throw new NullPointerException(field.getFullName() + " should not be null " + GlobPrinter.toString(this));
        }
        return value;
    }

    default Glob[] getNotNull(GlobArrayField field) {
        Glob[] value = get(field);
        if (value == null) {
            throw new NullPointerException(field.getFullName() + " should not be null " + GlobPrinter.toString(this));
        }
        return value;
    }

    default Glob getNotNull(GlobUnionField field) {
        Glob value = get(field);
        if (value == null) {
            throw new NullPointerException(field.getFullName() + " should not be null " + GlobPrinter.toString(this));
        }
        return value;
    }

    default Glob getNotNull(GlobField field) {
        Glob value = get(field);
        if (value == null) {
            throw new NullPointerException(field.getFullName() + " should not be null " + GlobPrinter.toString(this));
        }
        return value;
    }

    default Double getNotNull(DoubleField field) {
        Double value = get(field);
        if (value == null) {
            throw new NullPointerException(field.getFullName() + " should not be null " + GlobPrinter.toString(this));
        }
        return value;
    }

    default double[] getNotNull(DoubleArrayField field) {
        double[] value = get(field);
        if (value == null) {
            throw new NullPointerException(field.getFullName() + " should not be null " + GlobPrinter.toString(this));
        }
        return value;
    }

    default String[] getNotNull(StringArrayField field) {
        String[] value = get(field);
        if (value == null) {
            throw new NullPointerException(field.getFullName() + " should not be null " + GlobPrinter.toString(this));
        }
        return value;
    }

    interface Functor {
        void process(Field field, Object value) throws Exception;
    }
}
