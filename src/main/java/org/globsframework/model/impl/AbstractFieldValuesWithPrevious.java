package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.FieldValues;
import org.globsframework.model.FieldValuesWithPrevious;
import org.globsframework.model.Glob;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public abstract class AbstractFieldValuesWithPrevious implements FieldValuesWithPrevious {
    protected abstract Object doGet(Field field);

    protected abstract Object doGetPrevious(Field field);

    public boolean isNull(Field field) throws ItemNotFound {
        return doGet(field) == null;
    }

    public Object getValue(Field field) throws ItemNotFound {
        return doGet(field);
    }

    public Double get(DoubleField field) throws ItemNotFound {
        return (Double)doGet(field);
    }

    public double get(DoubleField field, double valueIfNull) throws ItemNotFound {
        Object value = doGet(field);
        if (value == null) {
            return valueIfNull;
        }
        return (Double)value;
    }

    public Integer get(IntegerField field) throws ItemNotFound {
        return (Integer)doGet(field);
    }

    public int get(IntegerField field, int valueIfNull) throws ItemNotFound {
        Integer value = get(field);
        return value == null ? valueIfNull : value;
    }

    public String get(StringField field) throws ItemNotFound {
        return (String)doGet(field);
    }

    public Boolean get(BooleanField field) throws ItemNotFound {
        return (Boolean)doGet(field);
    }

    public Boolean get(BooleanField field, boolean defaultIfNull) {
        return (Boolean)doGet(field);
    }

    public double[] get(DoubleArrayField field) throws ItemNotFound {
        return (double[])doGet(field);
    }

    public int[] get(IntegerArrayField field) throws ItemNotFound {
        return (int[])doGet(field);
    }

    public String[] get(StringArrayField field) throws ItemNotFound {
        return (String[])doGet(field);
    }

    public boolean[] get(BooleanArrayField field) {
        return (boolean[])doGet(field);
    }

    public long[] get(LongArrayField field) throws ItemNotFound {
        return (long[])doGet(field);
    }

    public LocalDate get(DateField field) throws ItemNotFound {
        return (LocalDate)doGet(field);
    }

    public ZonedDateTime get(DateTimeField field) throws ItemNotFound {
        return (ZonedDateTime)doGet(field);
    }

    public BigDecimal get(BigDecimalField field) throws ItemNotFound {
        return (BigDecimal)doGet(field);
    }

    public BigDecimal[] get(BigDecimalArrayField field) throws ItemNotFound {
        return (BigDecimal[])doGet(field);
    }

    public boolean isTrue(BooleanField field) {
        return Boolean.TRUE.equals(get(field));
    }

    public Long get(LongField field) throws ItemNotFound {
        return (Long)doGet(field);
    }

    public long get(LongField field, long valueIfNull) throws ItemNotFound {
        Long ret = (Long)doGet(field);
        return ret == null ? valueIfNull : ret;
    }

    public byte[] get(BlobField field) throws ItemNotFound {
        return (byte[])doGet(field);
    }

    public Glob get(GlobField field) throws ItemNotFound {
        return (Glob) doGet(field);
    }

    public Glob[] get(GlobArrayField field) throws ItemNotFound {
        return (Glob[]) doGet(field);
    }

    public Glob get(GlobUnionField field) throws ItemNotFound {
        return (Glob) doGet(field);
    }

    public Glob[] get(GlobArrayUnionField field) throws ItemNotFound {
        return (Glob[]) doGet(field);
    }

    public Object getPreviousValue(Field field) throws ItemNotFound {
        return doGetPrevious(field);
    }

    public Double getPrevious(DoubleField field) throws ItemNotFound {
        return (Double)doGetPrevious(field);
    }

    public double getPrevious(DoubleField field, double defaultIfNull) throws ItemNotFound {
        Double previous = getPrevious(field);
        if (previous == null) {
            return defaultIfNull;
        }
        return previous;
    }

    public long getPrevious(LongField field, long valueIfNull) throws ItemNotFound {
        Object previous = doGetPrevious(field);
        return previous == null ? valueIfNull : (long)previous;
    }

    public Integer getPrevious(IntegerField field) throws ItemNotFound {
        return (Integer)doGetPrevious(field);
    }

    public String getPrevious(StringField field) throws ItemNotFound {
        return (String)doGetPrevious(field);
    }

    public Boolean getPrevious(BooleanField field) throws ItemNotFound {
        return (Boolean)doGetPrevious(field);
    }

    public Boolean getPrevious(BooleanField field, boolean defaultIfNull) {
        return (Boolean)doGetPrevious(field);
    }

    public Long getPrevious(LongField field) throws ItemNotFound {
        return (Long)doGetPrevious(field);
    }

    public byte[] getPrevious(BlobField field) throws ItemNotFound {
        return (byte[])doGetPrevious(field);
    }

    public double[] getPrevious(DoubleArrayField field) throws ItemNotFound {
        return (double[])doGetPrevious(field);
    }

    public int[] getPrevious(IntegerArrayField field) throws ItemNotFound {
        return (int[])doGetPrevious(field);
    }

    public String[] getPrevious(StringArrayField field) throws ItemNotFound {
        return (String[])doGetPrevious(field);
    }

    public Boolean[] getPrevious(BooleanArrayField field) throws ItemNotFound {
        return (Boolean[])doGetPrevious(field);
    }

    public long[] getPrevious(LongArrayField field) throws ItemNotFound {
        return (long[])doGetPrevious(field);
    }

    public LocalDate getPrevious(DateField field) throws ItemNotFound {
        return (LocalDate)doGetPrevious(field);
    }

    public LocalDateTime getPrevious(DateTimeField field) throws ItemNotFound {
        return (LocalDateTime)doGetPrevious(field);
    }

    public BigDecimal getPrevious(BigDecimalField field) throws ItemNotFound {
        return (BigDecimal)doGetPrevious(field);
    }

    public BigDecimal[] getPrevious(BigDecimalArrayField field) throws ItemNotFound {
        return (BigDecimal[])doGetPrevious(field);
    }

    public Glob getPrevious(GlobField field) throws ItemNotFound {
        return (Glob) doGetPrevious(field);
    }

    public Glob[] getPrevious(GlobArrayField field) throws ItemNotFound {
        return (Glob[]) doGetPrevious(field);
    }

    public Glob getPrevious(GlobUnionField field) throws ItemNotFound {
        return (Glob) doGetPrevious(field);
    }

    public Glob[] getPrevious(GlobArrayUnionField field) throws ItemNotFound {
        return (Glob[]) doGetPrevious(field);
    }

    public <T extends FieldValueVisitor> T safeAccept(T functor) {
        try {
            return accept(functor);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends FieldValues.Functor> T safeApply(T functor) {
        try {
            return apply(functor);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends FunctorWithPrevious> T safeApplyWithPrevious(T functor) {
        try {
            return applyWithPrevious(functor);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends FieldValues.Functor> T safeApplyOnPrevious(T functor) {
        try {
            return applyOnPrevious(functor);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends FieldValueVisitor> T safeAcceptOnPrevious(T functor) {
        try {
            return acceptOnPrevious(functor);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
