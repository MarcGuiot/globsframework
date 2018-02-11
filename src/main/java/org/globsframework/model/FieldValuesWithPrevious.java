package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface FieldValuesWithPrevious extends FieldValues {
    Object getValue(Field field) throws ItemNotFound;

    Double get(DoubleField field) throws ItemNotFound;

    Integer get(IntegerField field) throws ItemNotFound;

    String get(StringField field) throws ItemNotFound;

    Boolean get(BooleanField field) throws ItemNotFound;

    Boolean get(BooleanField field, boolean defaultIfNull);

    Long get(LongField field) throws ItemNotFound;

    byte[] get(BlobField field) throws ItemNotFound;

    Object getPreviousValue(Field field) throws ItemNotFound;

    Double getPrevious(DoubleField field) throws ItemNotFound;

    double[] getPrevious(DoubleArrayField field) throws ItemNotFound;

    double getPrevious(DoubleField field, double defaultIfNull) throws ItemNotFound;

    Integer getPrevious(IntegerField field) throws ItemNotFound;

    int[] getPrevious(IntegerArrayField field) throws ItemNotFound;

    String getPrevious(StringField field) throws ItemNotFound;

    String[] getPrevious(StringArrayField field) throws ItemNotFound;

    Boolean getPrevious(BooleanField field) throws ItemNotFound;

    Boolean[] getPrevious(BooleanArrayField field) throws ItemNotFound;

    Boolean getPrevious(BooleanField field, boolean defaultIfNull);

    Long getPrevious(LongField field) throws ItemNotFound;

    long[] getPrevious(LongArrayField field) throws ItemNotFound;

    long getPrevious(LongField field, long valueIfNull) throws ItemNotFound;

    LocalDate getPrevious(DateField field)throws ItemNotFound;

    LocalDateTime getPrevious(DateTimeField field) throws ItemNotFound;

    BigDecimal getPrevious(BigDecimalField field) throws ItemNotFound;

    BigDecimal[] getPrevious(BigDecimalArrayField field) throws ItemNotFound;

    byte[] getPrevious(BlobField field) throws ItemNotFound;

    void apply(Functor functor) throws Exception;

    void safeApply(Functor functor);

    void safeApplyOnPrevious(FieldValues.Functor functor);

    void applyOnPrevious(FieldValues.Functor functor) throws Exception;

    FieldValues getPreviousValues();

    interface Functor {
        void process(Field field, Object value, Object previousValue) throws Exception;
    }
}