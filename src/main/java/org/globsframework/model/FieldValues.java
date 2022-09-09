package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.format.GlobPrinter;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.function.Function;

public interface FieldValues extends FieldValuesAccessor, FieldsValueScanner {

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

        public boolean get(BooleanField field, boolean defaultIfNull) {
            throw new ItemNotFound(field.getName());
        }

        public boolean[] get(BooleanArrayField field) {
            throw new ItemNotFound(field.getName());
        }

        public boolean isTrue(BooleanField field) throws ItemNotFound {
            throw new ItemNotFound(field.getName());
        }

        public boolean isSet(Field field) throws ItemNotFound {
            return false;
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

        public String toString(){
            return "";
        }

    };
    Glob[] EMPTY_GLOB_ARRAY = new Glob[0];
    boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    int[] EMPTY_INTEGER_ARRAY = new int[0];
    long[] EMPTY_LONG_ARRAY = new long[0];
    double[] EMPTY_DOUBLE_ARRAY = new double[0];
    BigDecimal[] BIG_DECIMAL_GLOB_ARRAY = new BigDecimal[0];
    String[] EMPTY_STRING_ARRAY = new String[0];

    boolean contains(Field field);

    int size();

    default FieldValues withoutKeyField() {
        return new FieldValuesButKey(this);
    }

    FieldValue[] toArray();

    default boolean[] getOrEmpty(BooleanArrayField field) {
        boolean[] d = get(field);
        return d != null ? d : EMPTY_BOOLEAN_ARRAY;
    }

    default int[] getOrEmpty(IntegerArrayField field) {
        int[] d = get(field);
        return d != null ? d : EMPTY_INTEGER_ARRAY;
    }

    default long[] getOrEmpty(LongArrayField field) {
        long[] d = get(field);
        return d != null ? d : EMPTY_LONG_ARRAY;
    }

    default double[] getOrEmpty(DoubleArrayField field) {
        double[] d = get(field);
        return d != null ? d : EMPTY_DOUBLE_ARRAY;
    }

    default BigDecimal[] getOrEmpty(BigDecimalArrayField field) {
        BigDecimal[] globs = get(field);
        return globs != null ? globs : BIG_DECIMAL_GLOB_ARRAY;
    }

    default String[] getOrEmpty(StringArrayField field) {
        String[] d = get(field);
        return d != null ? d : EMPTY_STRING_ARRAY;
    }

    default Glob[] getOrEmpty(GlobArrayUnionField field) {
        Glob[] globs = get(field);
        return globs != null ? globs : EMPTY_GLOB_ARRAY;
    }

    default Glob[] getOrEmpty(GlobArrayField field) {
        Glob[] globs = get(field);
        return globs != null ? globs : EMPTY_GLOB_ARRAY;
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

    default Long getNotNull(LongField field) {
        Long value = get(field);
        if (value == null) {
            throw new NullPointerException(field.getFullName() + " should not be null " + GlobPrinter.toString(this));
        }
        return value;
    }

    default Integer getNotNull(IntegerField field) {
        Integer value = get(field);
        if (value == null) {
            throw new NullPointerException(field.getFullName() + " should not be null " + GlobPrinter.toString(this));
        }
        return value;
    }

    default ZonedDateTime getNotNull(DateTimeField field) {
        ZonedDateTime value = get(field);
        if (value == null) {
            throw new NullPointerException(field.getFullName() + " should not be null " + GlobPrinter.toString(this));
        }
        return value;
    }

    default LocalDate getNotNull(DateField field) {
        LocalDate value = get(field);
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

    default GlobOpt getOpt(GlobField field) {
        return new GlobOpt(get(field));
    }

    default GlobOpt getOpt(GlobUnionField field) {
        return new GlobOpt(get(field));
    }

    interface Functor {
        void process(Field field, Object value) throws Exception;

        default Functor withoutKeyField() {
            return (field, value) -> {
                if (!field.isKeyField()) {
                    Functor.this.process(field, value);
                }
            };
        }
    }

    class GlobOpt implements FieldValues {
        private final Glob glob;

        GlobOpt(Glob glob) {
            this.glob = glob;
        }

        public boolean contains(Field field) {
            if (glob != null) {
                return glob.contains(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public int size() {
            if (glob != null) {
                return glob.size();
            }
            else {
                throw new NullPointerException();
            }
        }

        public <T extends Functor> T apply(T t) throws Exception {
            if (glob != null) {
                return glob.apply(t);
            }
            else {
                throw new NullPointerException();
            }
        }

        public <T extends Functor> T safeApply(T t) {
            if (glob != null) {
                return glob.safeApply(t);
            }
            else {
                throw new NullPointerException();
            }
        }

        public <T extends FieldValueVisitor> T accept(T t) throws Exception {
            if (glob != null) {
                return glob.accept(t);
            }
            else {
                throw new NullPointerException();
            }
        }

        public <T extends FieldValueVisitor> T safeAccept(T t) {
            if (glob != null) {
                return glob.safeAccept(t);
            }
            else {
                throw new NullPointerException();
            }
        }

        public FieldValue[] toArray() {
            if (glob != null) {
                return glob.toArray();
            }
            else {
                throw new NullPointerException("");
            }
        }

        public boolean isSet(Field field) throws ItemNotFound {
            if (glob != null) {
                return glob.isSet(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public boolean isNull(Field field) throws ItemNotFound {
            if (glob != null) {
                return glob.isNull(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public Object getValue(Field field) throws ItemNotFound {
            if (glob != null) {
                return glob.getValue(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public Double get(DoubleField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public double get(DoubleField field, double v) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field, v);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public double[] get(DoubleArrayField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public Integer get(IntegerField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public int get(IntegerField field, int i) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field, i);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public int[] get(IntegerArrayField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public String get(StringField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public String[] get(StringArrayField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public Boolean get(BooleanField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public boolean get(BooleanField field, boolean b) {
            if (glob != null) {
                return glob.get(field, b);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public boolean[] get(BooleanArrayField field) {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public boolean isTrue(BooleanField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public Long get(LongField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public long get(LongField field, long l) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field, l);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public long[] get(LongArrayField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }

        }

        public BigDecimal get(BigDecimalField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }

        }

        public BigDecimal[] get(BigDecimalArrayField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }

        }

        public LocalDate get(DateField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public ZonedDateTime get(DateTimeField field) throws ItemNotFound {
            if (glob != null) {
                return glob.get(field);
            }
            else {
                throw new NullPointerException(field.getFullName());
            }
        }

        public byte[] get(BlobField blobField) throws ItemNotFound {
            if (glob != null) {
                return glob.get(blobField);
            }
            else {
                throw new NullPointerException(blobField.getFullName());
            }
        }

        public Glob get(GlobField globField) throws ItemNotFound {
            if (glob != null) {
                return glob.get(globField);
            }
            else {
                throw new NullPointerException(globField.getFullName());
            }
        }

        public Glob[] get(GlobArrayField globArrayField) throws ItemNotFound {
            if (glob != null) {
                return glob.get(globArrayField);
            }
            else {
                throw new NullPointerException(globArrayField.getFullName());
            }
        }

        public Glob get(GlobUnionField globUnionField) throws ItemNotFound {
            if (glob != null) {
                return glob.get(globUnionField);
            }
            else {
                throw new NullPointerException(globUnionField.getFullName());
            }
        }

        public Glob[] get(GlobArrayUnionField globArrayUnionField) throws ItemNotFound {
            if (glob != null) {
                return glob.get(globArrayUnionField);
            }
            else {
                throw new NullPointerException(globArrayUnionField.getFullName());
            }
        }

        public GlobOpt getOpt(GlobField field) {
            return new GlobOpt(glob != null ? glob.get(field) : null);
        }

        public GlobOpt getOpt(GlobUnionField field) {
            return new GlobOpt(glob != null ? glob.get(field) : null);
        }

        public <T> Optional<T> map(Function<Glob, T> function) {
            return Optional.ofNullable(glob).map(function);
        }
    }

}
