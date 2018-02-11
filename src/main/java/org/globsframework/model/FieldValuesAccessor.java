package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface FieldValuesAccessor {

    boolean isNull(Field field) throws ItemNotFound;

    Object getValue(Field field) throws ItemNotFound;

    Double get(DoubleField field) throws ItemNotFound;

    double get(DoubleField field, double valueIfNull) throws ItemNotFound;

    double[] get(DoubleArrayField field) throws ItemNotFound;

    Integer get(IntegerField field) throws ItemNotFound;

    int get(IntegerField field, int valueIfNull) throws ItemNotFound;

    int[] get(IntegerArrayField field) throws ItemNotFound;

    String get(StringField field) throws ItemNotFound;

    String[] get(StringArrayField field) throws ItemNotFound;

    Boolean get(BooleanField field) throws ItemNotFound;

    Boolean get(BooleanField field, boolean defaultIfNull);

    boolean[] get(BooleanArrayField field);

    boolean isTrue(BooleanField field) throws ItemNotFound;

    Long get(LongField field) throws ItemNotFound;

    long get(LongField field, long valueIfNull) throws ItemNotFound;

    long[] get(LongArrayField field) throws ItemNotFound;

    BigDecimal get(BigDecimalField field) throws ItemNotFound;

    BigDecimal[] get(BigDecimalArrayField field) throws ItemNotFound;

    LocalDate get(DateField field) throws ItemNotFound;

    ZonedDateTime get(DateTimeField field) throws ItemNotFound;

    byte[] get(BlobField field) throws ItemNotFound;

    default String get(StringField field, String valueIfNull) throws ItemNotFound {
        String value = get(field);
        return value != null ? value : valueIfNull;
    }

}