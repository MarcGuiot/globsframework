package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.utils.Strings;
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

    default String getOrDefault(StringField field, String valueIfEmpty) throws ItemNotFound {
        String value = get(field);
        return value != null && value.length() != 0 ? value : valueIfEmpty;
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

    default NullableOptional<String> getNullableOpt(StringField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<String[]> getNullableOpt(StringArrayField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<Integer> getNullableOpt(IntegerField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<int[]> getNullableOpt(IntegerArrayField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<Long> getNullableOpt(LongField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<long[]> getNullableOpt(LongArrayField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<Double> getNullableOpt(DoubleField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<double[]> getNullableOpt(DoubleArrayField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<BigDecimal> getNullableOpt(BigDecimalField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<BigDecimal[]> getNullableOpt(BigDecimalArrayField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<LocalDate> getNullableOpt(DateField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<ZonedDateTime> getNullableOpt(DateTimeField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<Boolean> getNullableOpt(BooleanField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<boolean[]> getNullableOpt(BooleanArrayField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<Glob> getNullableOpt(GlobField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default NullableOptional<Glob[]> getNullableOpt(GlobArrayField field) {
        return new NullableOptional<>(isSet(field), get(field));
    }

    default Optional<String> getOpt(StringField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<String> getOptNotEmpty(StringField field) {
        final String value = get(field);
        return Strings.isNullOrEmpty(value) ? Optional.empty() : Optional.of(value);
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

    default Optional<BigDecimal[]> getOpt(BigDecimalArrayField field) {
        return Optional.ofNullable(get(field));
    }

    default Optional<Object> getOptValue(Field field) {
        return Optional.ofNullable(getValue(field));
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
