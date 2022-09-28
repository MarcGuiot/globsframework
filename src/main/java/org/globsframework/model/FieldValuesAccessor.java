package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;


public interface FieldValuesAccessor {

    Glob[] EMPTY = new Glob[0];

    boolean isSet(Field field) throws ItemNotFound;

    boolean isNull(Field field) throws ItemNotFound;

    default boolean isNotNull(Field field) throws ItemNotFound {
        return !isNull(field);
    }

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

    boolean get(BooleanField field, boolean defaultIfNull);

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

    Glob get(GlobField field) throws ItemNotFound;

    Glob[] get(GlobArrayField field) throws ItemNotFound;

    Glob get(GlobUnionField field) throws ItemNotFound;

    Glob[] get(GlobArrayUnionField field) throws ItemNotFound;

    default String get(StringField field, String valueIfNull) throws ItemNotFound {
        String value = get(field);
        return value != null ? value : valueIfNull;
    }

    default ZonedDateTime get(DateTimeField field, ZonedDateTime valueIfNull) throws ItemNotFound {
        ZonedDateTime value = get(field);
        return value != null ? value : valueIfNull;
    }

    default LocalDate get(DateField field, LocalDate valueIfNull) throws ItemNotFound {
        LocalDate value = get(field);
        return value != null ? value : valueIfNull;
    }

    default BigDecimal get(BigDecimalField field, BigDecimal valueIfNull) throws ItemNotFound {
        BigDecimal bigDecimal = get(field);
        return bigDecimal != null ? bigDecimal : valueIfNull;
    }

    default Optional<String> getOpt(StringField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<LocalDate> getOpt(DateField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<ZonedDateTime> getOpt(DateTimeField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<Long> getOpt(LongField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<Integer> getOpt(IntegerField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<Double> getOpt(DoubleField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<Boolean> getOpt(BooleanField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<String[]> getOpt(StringArrayField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<double[]> getOpt(DoubleArrayField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<long[]> getOpt(LongArrayField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<int[]> getOpt(IntegerArrayField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<boolean[]> getOpt(BooleanArrayField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<BigDecimal> getOpt(BigDecimalField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<Glob> getOptional(GlobField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<Glob> getOptional(GlobUnionField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<Glob[]> getOptional(GlobArrayField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<Glob[]> getOptional(GlobArrayUnionField field) {
        return Optional.ofNullable(get(field));
    }

}
