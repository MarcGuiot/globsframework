package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.Annotations;
import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeBuilder;
import org.globsframework.metamodel.annotations.*;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.fields.impl.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.metamodel.utils.MutableAnnotations;
import org.globsframework.model.Glob;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class DefaultGlobTypeBuilder implements GlobTypeBuilder {
    private DefaultGlobType type;
    private DefaultFieldFactory factory;
    private int index;
    private int keyIndex;

    public static GlobTypeBuilder init(String typeName) {
        return new DefaultGlobTypeBuilder(typeName);
    }

    public DefaultGlobTypeBuilder(String typeName) {
        type = new DefaultGlobType(typeName);
        factory = new DefaultFieldFactory(type);
    }

    private MutableAnnotations adaptAnnotation(Collection<Glob> annotations) {
        DefaultAnnotations defaultAnnotations = new DefaultAnnotations();
        for (Glob annotation : annotations) {
            defaultAnnotations.addAnnotation(annotation);
        }
        return defaultAnnotations;
    }

    public GlobTypeBuilder addAnnotation(Glob annotation) {
        type.addAnnotation(annotation);
        return this;
    }

    public GlobTypeBuilder addStringField(String fieldName, Collection<Glob> annotations) {
        createStringField(fieldName, annotations);
        return this;
    }

    private DefaultStringField createStringField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        String defaultValue = annotations.getValueOrDefault(DefaultStringAnnotationType.UNIQUE_KEY,
                                                            DefaultStringAnnotationType.DEFAULT_VALUE, null);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultStringField field = factory.addString(fieldName, key != null, getKeyId(key), index, defaultValue);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    public GlobTypeBuilder addStringArrayField(String fieldName, Collection<Glob> annotations) {
        createStringArrayField(fieldName, annotations);
        return this;
    }

    private DefaultStringArrayField createStringArrayField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultStringArrayField field = factory.addStringArray(fieldName, key != null, getKeyId(key), index);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    public GlobTypeBuilder addIntegerField(String fieldName, Collection<Glob> globAnnotations) {
        createIntegerField(fieldName, globAnnotations);
        return this;
    }

    private DefaultIntegerField createIntegerField(String fieldName, Collection<Glob> globAnnotations) {
        DefaultIntegerField field;
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Integer defaultValue = annotations.getValueOrDefault(DefaultIntegerAnnotationType.UNIQUE_KEY, DefaultIntegerAnnotationType.DEFAULT_VALUE, null);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        field = factory.addInteger(fieldName, key != null, getKeyId(key), index, defaultValue);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    private int getKeyId(Glob key) {
        if (key != null) {
            Integer index = key.get(KeyAnnotationType.INDEX);
            if (index == -1) {
                return keyIndex++;
            } else {
                return index;
            }
        } else {
            return 0;
        }
    }

    private DefaultIntegerArrayField createIntegerArrayField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultIntegerArrayField field = factory.addIntegerArray(fieldName, key != null, getKeyId(key), index);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    public GlobTypeBuilder addDoubleField(String fieldName, Collection<Glob> globAnnotations) {
        createDoubleField(fieldName, globAnnotations);
        return this;
    }

    public GlobTypeBuilder addDoubleArrayField(String fieldName, Collection<Glob> globAnnotations) {
        createDoubleArrayField(fieldName, globAnnotations);
        return this;
    }

    public GlobTypeBuilder addIntegerArrayField(String fieldName, Collection<Glob> globAnnotations) {
        createIntegerArrayField(fieldName, globAnnotations);
        return this;
    }

    public GlobTypeBuilder addLongArrayField(String fieldName, Collection<Glob> globAnnotations) {
        createLongArrayField(fieldName, globAnnotations);
        return this;
    }

    public GlobTypeBuilder addBigDecimalField(String fieldName, Collection<Glob> globAnnotations) {
        createBigDecimalField(fieldName, globAnnotations);
        return this;
    }

    public GlobTypeBuilder addBigDecimalArrayField(String fieldName, Collection<Glob> globAnnotations) {
        createBigDecimalArrayField(fieldName, globAnnotations);
        return this;
    }

    public GlobTypeBuilder addDateField(String fieldName, Collection<Glob> globAnnotations) {
        createDateField(fieldName, globAnnotations);
        return this;
    }

    public GlobTypeBuilder addDateTimeField(String fieldName, Collection<Glob> globAnnotations) {
        createDateTimeField(fieldName, globAnnotations);
        return this;
    }

    private DefaultDoubleField createDoubleField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Double defaultValue = annotations.getValueOrDefault(DefaultDoubleAnnotationType.UNIQUE_KEY, DefaultDoubleAnnotationType.DEFAULT_VALUE, null);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultDoubleField doubleField = factory.addDouble(fieldName, key != null, getKeyId(key), index, defaultValue);
        doubleField.addAnnotations(annotations.streamAnnotations());
        index++;
        return doubleField;
    }

    private DefaultDoubleArrayField createDoubleArrayField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultDoubleArrayField field = factory.addDoubleArray(fieldName, key != null, getKeyId(key), index);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    private DefaultBigDecimalField createBigDecimalField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        BigDecimal defaultValue = annotations.getValueOrDefault(DefaultBigDecimalAnnotationType.UNIQUE_KEY, DefaultBigDecimalAnnotationType.DEFAULT_VALUE, null);
        DefaultBigDecimalField bigDecimalField = factory.addBigDecimal(fieldName, key != null, getKeyId(key), index, defaultValue);
        bigDecimalField.addAnnotations(annotations.streamAnnotations());
        index++;
        return bigDecimalField;
    }

    private DefaultBigDecimalArrayField createBigDecimalArrayField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultBigDecimalArrayField bigDecimalArrayField = factory.addBigDecimalArray(fieldName, key != null, getKeyId(key), index);
        bigDecimalArrayField.addAnnotations(annotations.streamAnnotations());
        index++;
        return bigDecimalArrayField;
    }

    private DefaultDateTimeField createDateTimeField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultDateTimeField dateTimeField = factory.addDateTime(fieldName, key != null, getKeyId(key), index);
        dateTimeField.addAnnotations(annotations.streamAnnotations());
        index++;
        return dateTimeField;
    }

    private DefaultDateField createDateField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultDateField dateField = factory.addDate(fieldName, key != null, getKeyId(key), index);
        dateField.addAnnotations(annotations.streamAnnotations());
        index++;
        return dateField;
    }


    public GlobTypeBuilder addLongField(String fieldName, Collection<Glob> globAnnotations) {
        createLongField(fieldName, globAnnotations);
        return this;
    }

    private DefaultLongField createLongField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Long defaultValue = annotations.getValueOrDefault(DefaultLongAnnotationType.UNIQUE_KEY,
              DefaultLongAnnotationType.DEFAULT_VALUE, null);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultLongField longField = factory.addLong(fieldName, key != null, getKeyId(key), index, defaultValue);
        longField.addAnnotations(annotations.streamAnnotations());
        index++;
        return longField;
    }

    private DefaultLongArrayField createLongArrayField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultLongArrayField field = factory.addLongArray(fieldName, key != null, getKeyId(key), index);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    public GlobTypeBuilder addBooleanArrayField(String fieldName, Collection<Glob> globAnnotations) {
        createBooleanArrayField(fieldName, globAnnotations);
        return this;
    }

    private DefaultBooleanArrayField createBooleanArrayField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultBooleanArrayField field = factory.addBooleanArray(fieldName, key != null, getKeyId(key), index);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    public GlobTypeBuilder addBooleanField(String fieldName, Collection<Glob> globAnnotations) {
        createBooleanField(fieldName, globAnnotations);
        return this;
    }

    private DefaultBooleanField createBooleanField(String fieldName, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Boolean defaultValue = annotations.getValueOrDefault(DefaultBooleanAnnotationType.UNIQUE_KEY,
              DefaultBooleanAnnotationType.DEFAULT_VALUE, null);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultBooleanField field = factory.addBoolean(fieldName, key != null, getKeyId(key), index, defaultValue);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    public GlobTypeBuilder addBlobField(String fieldName, Collection<Glob> globAnnotations) {
        createBlobField(fieldName, globAnnotations);
        return this;
    }

    public GlobTypeBuilder addGlobField(String fieldName, Collection<Glob> globAnnotations, GlobType type) {
        createGlobField(fieldName, type, globAnnotations);
        return this;
    }


    public GlobTypeBuilder addGlobArrayField(String fieldName, Collection<Glob> globAnnotations, GlobType type) {
        createGlobArrayField(fieldName, type, globAnnotations);
        return this;
    }

    public GlobTypeBuilder addUnionGlobField(String fieldName, Collection<Glob> globAnnotations, List<GlobType> types) {
        createGlobUnionField(fieldName, types, globAnnotations);
        return this;
    }

    public GlobTypeBuilder addUnionGlobArrayField(String fieldName, Collection<Glob> globAnnotations, List<GlobType> types) {
        createGlobUnionArrayField(fieldName, types, globAnnotations);
        return this;
    }

    private DefaultBlobField createBlobField(String fieldName, Collection<Glob> globAnnotations) {
        Annotations annotations = adaptAnnotation(globAnnotations);
        DefaultBlobField field = factory.addBlob(fieldName, index);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    private GlobField createGlobField(String fieldName, GlobType globType, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultGlobField field = factory.addGlob(fieldName, globType, key != null, getKeyId(key), index);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    private GlobArrayField createGlobArrayField(String fieldName, GlobType globType, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        DefaultGlobArrayField field = factory.addGlobArray(fieldName, globType, key != null, getKeyId(key), index);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    private GlobUnionField createGlobUnionField(String fieldName, Collection<GlobType> types, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        if (key != null) {
            throw new RuntimeException(fieldName + " of type unionField cannot be a key");
        }
        DefaultGlobUnionField field = factory.addGlobUnion(fieldName, types, index);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    private GlobArrayUnionField createGlobUnionArrayField(String fieldName, Collection<GlobType> types, Collection<Glob> globAnnotations) {
        MutableAnnotations annotations = adaptAnnotation(globAnnotations);
        Glob key = annotations.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
        if (key != null) {
            throw new RuntimeException(fieldName + " of type unionField cannot be a key");
        }
        DefaultGlobUnionArrayField field = factory.addGlobArrayUnion(fieldName, types, index);
        field.addAnnotations(annotations.streamAnnotations());
        index++;
        return field;
    }

    public StringField declareStringField(String fieldName, Collection<Glob> globAnnotations) {
        return createStringField(fieldName, globAnnotations);
    }

    public StringArrayField declareStringArrayField(String fieldName, Collection<Glob> globAnnotations) {
        return createStringArrayField(fieldName, globAnnotations);
    }

    public IntegerField declareIntegerField(String fieldName, Collection<Glob> annotations) {
        return createIntegerField(fieldName, annotations);
    }

    public IntegerArrayField declareIntegerArrayField(String fieldName, Collection<Glob> annotations) {
        return createIntegerArrayField(fieldName, annotations);
    }

    public DoubleField declareDoubleField(String fieldName, Collection<Glob> annotations) {
        return createDoubleField(fieldName, annotations);
    }

    public DoubleArrayField declareDoubleArrayField(String fieldName, Collection<Glob> annotations) {
        return createDoubleArrayField(fieldName, annotations);
    }

    public BigDecimalField declareBigDecimalField(String fieldName, Collection<Glob> annotations) {
        return createBigDecimalField(fieldName, annotations);
    }

    public BigDecimalArrayField declareBigDecimalArrayField(String fieldName, Collection<Glob> annotations) {
        return createBigDecimalArrayField(fieldName, annotations);
    }

    public BooleanField declareBooleanField(String fieldName, Collection<Glob> annotations) {
        return createBooleanField(fieldName, annotations);
    }

    public BooleanArrayField declareBooleanArrayField(String fieldName, Collection<Glob> annotations) {
        return createBooleanArrayField(fieldName, annotations);
    }

    public DateField declareDateField(String fieldName, Collection<Glob> annotations) {
        return createDateField(fieldName, annotations);
    }

    public DateTimeField declareDateTimeField(String fieldName, Collection<Glob> annotations) {
        return createDateTimeField(fieldName, annotations);
    }

    public LongField declareLongField(String fieldName, Collection<Glob> annotations) {
        return createLongField(fieldName, annotations);
    }

    public LongArrayField declareLongArrayField(String fieldName, Collection<Glob> annotations) {
        return createLongArrayField(fieldName, annotations);
    }

    public BlobField declareBlobField(String fieldName, Collection<Glob> annotations) {
        return createBlobField(fieldName, annotations);
    }

    public GlobField declareGlobField(String fieldName, GlobType globType, Collection<Glob> annotations) {
        return createGlobField(fieldName, globType, annotations);
    }

    public GlobArrayField declareGlobArrayField(String fieldName, GlobType globType, Collection<Glob> annotations) {
        return createGlobArrayField(fieldName, globType, annotations);
    }

    public GlobUnionField declareGlobUnionField(String fieldName, Collection<GlobType> types, Collection<Glob> annotations) {
        return createGlobUnionField(fieldName, types, annotations);
    }

    public GlobArrayUnionField declareGlobUnionArrayField(String fieldName, Collection<GlobType> types, Collection<Glob> annotations) {
        return createGlobUnionArrayField(fieldName, types, annotations);
    }

    public Field declare(String fieldName, DataType dataType, Collection<Glob> annotations) {
        if (fieldName == null) {
            throw new RuntimeException("field name can not be null");
        }
        switch (dataType) {
            case String:
                return declareStringField(fieldName, annotations);
            case StringArray:
                return declareStringArrayField(fieldName, annotations);
            case Double:
                return declareDoubleField(fieldName, annotations);
            case DoubleArray:
                return declareDoubleArrayField(fieldName, annotations);
            case BigDecimal:
                return declareBigDecimalField(fieldName, annotations);
            case BigDecimalArray:
                return declareBigDecimalArrayField(fieldName, annotations);
            case Long:
                return declareLongField(fieldName, annotations);
            case LongArray:
                return declareLongArrayField(fieldName, annotations);
            case Integer:
                return declareIntegerField(fieldName, annotations);
            case IntegerArray:
                return declareIntegerArrayField(fieldName, annotations);
            case Boolean:
                return declareBooleanField(fieldName, annotations);
            case BooleanArray:
                return declareBooleanArrayField(fieldName, annotations);
            case Date:
                return declareDateField(fieldName, annotations);
            case DateTime:
                return declareDateTimeField(fieldName, annotations);
            case Bytes:
                return declareBlobField(fieldName, annotations);
        }
        throw new RuntimeException("creation of " + dataType + " not possible without additional parameter (globType)");
    }

    public <T> void register(Class<T> klass, T t) {
        type.register(klass, t);
    }

    public GlobType get() {
        type.completeInit();
        return type;
    }

    public GlobType unCompleteType() {
        return type;
    }

    public boolean isKnown(String fieldName) {
        return type.hasField(fieldName);
    }
}
