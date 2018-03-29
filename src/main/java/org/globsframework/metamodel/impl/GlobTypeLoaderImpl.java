package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.*;
import org.globsframework.metamodel.annotations.*;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.fields.impl.AbstractField;
import org.globsframework.metamodel.index.*;
import org.globsframework.metamodel.index.impl.DefaultMultiFieldNotUniqueIndex;
import org.globsframework.metamodel.index.impl.DefaultMultiFieldUniqueIndex;
import org.globsframework.metamodel.index.impl.DefaultNotUniqueIndex;
import org.globsframework.metamodel.index.impl.DefaultUniqueIndex;
import org.globsframework.metamodel.links.Link;
import org.globsframework.metamodel.utils.MutableAnnotations;
import org.globsframework.model.Glob;
import org.globsframework.utils.Strings;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemAlreadyExists;
import org.globsframework.utils.exceptions.MissingInfo;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class GlobTypeLoaderImpl implements GlobTypeLoader {
    private DefaultGlobType type;
    private DefaultFieldFactory fieldFactory;
    private String modelName;
    private Class<?> targetClass;
    private String name;
    private FieldInitializeProcessorService fieldInitializeProcessorService;
    private Map<Class, Object> registered = new ConcurrentHashMap<>();

    public GlobTypeLoaderImpl(Class<?> targetClass, String modelName, String name,
                              FieldInitializeProcessorService fieldInitializeProcessorService) {
        this.modelName = modelName;
        this.fieldInitializeProcessorService = fieldInitializeProcessorService;
        this.targetClass = targetClass;
        this.name = name;
    }

    public GlobTypeLoader load() {
        checkClassIsNotAlreadyInitialized(targetClass);
        processClass(targetClass);
        processFields(targetClass);
        processIndex(targetClass);
        type.completeInit();
        for (Map.Entry<Class, Object> entry : registered.entrySet()) {
            type.register(entry.getKey(), entry.getValue());
        }
        processOther(targetClass);
        return this;
    }

    public GlobType getType() {
        return type;
    }

    private void processOther(Class<?> targetClass) {
        for (java.lang.reflect.Field classField : targetClass.getFields()) {
            List<FieldInitializeProcessor> processor = fieldInitializeProcessorService.get(classField);
            if (processor != null && !processor.isEmpty()) {
                DefaultAnnotations<Field> annotations = new DefaultAnnotations<>();
                processFieldAnnotations(classField, annotations);
                annotations.addAnnotation(FieldNameAnnotationType.create(getFieldName(classField)));
                applyProcessor(targetClass, classField, processor, annotations);
            }
            else {
                try {
                    Object value = classField.get(null);
                    if (value == null) {
                        throw new MissingInfo("Missing initialisation on " + targetClass.getName() + " for " + classField.getName());
                    }
                }
                catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void applyProcessor(Class<?> targetClass, java.lang.reflect.Field classField, List<FieldInitializeProcessor> processor,
                                DefaultAnnotations<Field> annotations) {
        for (FieldInitializeProcessor fieldInitializeProcessor : processor) {
            Object value = fieldInitializeProcessor.getValue(type, annotations, classField.getAnnotations());
            if (value instanceof MutableAnnotations) {
                MutableAnnotations mutableAnnotations = (MutableAnnotations)value;
                mutableAnnotations.addAnnotations(annotations.streamAnnotations());
            }
            if (value != null) {
                setClassField(classField, value, targetClass);
                return;
            }
        }
    }

    private void checkClassIsNotAlreadyInitialized(Class targetClass) {
        for (java.lang.reflect.Field classField : targetClass.getFields()) {
            try {
                if (classField.getType().equals(GlobType.class)) {
                    if (classField.get(null) != null) {
                        throw new UnexpectedApplicationState(targetClass.getName() + " already initialized");
                    }
                }
            }
            catch (IllegalAccessException e) {
                throw GlobTypeLoaderImpl.getFieldAccessException(targetClass, classField, null, e);
            }
        }
    }

    private void processClass(Class targetClass) {

        for (java.lang.reflect.Field field : targetClass.getFields()) {
            if (field.getType().equals(GlobType.class)) {
                createType(field, targetClass);
            }
        }

        if (type == null) {
            throw new MissingInfo("Class " + targetClass.getName() +
                                  " must have a TYPE field of class " + GlobType.class.getName());
        }
    }

    private void createType(java.lang.reflect.Field classField,
                            Class<?> targetClass) {
        if (type != null) {
            throw new ItemAlreadyExists("Class " + targetClass.getName() +
                                        " must have only one TYPE field of class " + GlobType.class.getName());
        }
        this.type = new DefaultGlobType(getTypeName(targetClass));
        for (Annotation annotation : classField.getAnnotations()) {
            Glob glob = processAnnotation(annotation);
            if (glob != null) {
                type.addAnnotation(glob);
            }
        }
        this.fieldFactory = new DefaultFieldFactory(type);
        GlobTypeLoaderImpl.setClassField(classField, type, targetClass);
    }

    private void processFieldAnnotations(java.lang.reflect.Field field, MutableAnnotations<Field> annotations) {
        for (Annotation annotation : field.getAnnotations()) {
            Glob globAnnotation = processAnnotation(annotation);
            if (globAnnotation != null) {
                annotations.addAnnotation(globAnnotation);
            }
        }
    }

    private Glob processAnnotation(Annotation annotation) {
        try {
            if (annotation.annotationType().isAnnotationPresent(NoType.class)) {
                return null;
            }
            java.lang.reflect.Field[] fields = annotation.getClass().getFields();
            GlobType globType = null;
            java.lang.reflect.Field foundField = null;
            for (java.lang.reflect.Field field : fields) {
                if (field.getType().equals(GlobType.class)) {
                    if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers())) {
                        globType = (GlobType)field.get(null);
                    }
                    else {
                        foundField = field;
                    }
                }
                if (field.getType().equals(Glob.class)) {
                    if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers())) {
                        return (Glob)field.get(null);
                    }
                }
            }
            if (globType == null) {
                if (foundField != null) {
                    throw new RuntimeException(foundField.getName() + " must be static public");
                }
                throw new RuntimeException("For " + annotation.annotationType() + " missing GlobType in annotation : code is missing :" +
                                           "in annotation : GlobType TYPE = TheAnnotationType.TYPE;");
            }
            else {
                GlobCreateFromAnnotation registered = globType.getRegistered(GlobCreateFromAnnotation.class);
                if (registered != null) {
                    return registered.create(annotation);
                }
                throw new RuntimeException("For " + annotation.annotationType() + " no GlobCreateFromAnnotation registered. Code like following is missing \n" +
                                           "       loader.register(GlobCreateFromAnnotation.class, annotation -> create((theAnnotation)annotation))\n");
            }
        }
        catch (IllegalAccessException e) {
        }
        return null;
    }

    private void processFields(Class<?> targetClass) {
        int fieldIndex = 0;
        int keyCount = 0;
        Set<Integer> allocatedKeys = new HashSet<>();
        for (java.lang.reflect.Field classField : targetClass.getFields()) {
            int keyIndex = -1;
            if (isGlobField(classField)) {
                boolean isKeyField = classField.isAnnotationPresent(KeyField.class);
                if (isKeyField) {
                    keyIndex = classField.getAnnotation(KeyField.class).value();
                    if (keyIndex == -1) {
                        keyIndex = keyCount;
                    }
                    allocatedKeys.add(keyIndex);
                }
                String fieldName;
                boolean hasFieldNameAnnotation = classField.isAnnotationPresent(FieldNameAnnotation.class);
                if (hasFieldNameAnnotation) {
                    fieldName = classField.getAnnotation(FieldNameAnnotation.class).value();
                }
                else {
                    fieldName = getFieldName(classField);
                }
                AbstractField field = create(fieldName, classField.getType(), isKeyField, keyIndex, fieldIndex, classField);
                if (!hasFieldNameAnnotation) {
                    field.addAnnotation(FieldNameAnnotationType.create(fieldName));
                }
                if (isKeyField) {
                    field.addAnnotation(KeyAnnotationType.create(keyCount));
                    keyCount++;
                }
                setClassField(classField, field, targetClass);
                fieldIndex++;
                processFieldAnnotations(classField, field);
            }
        }
        if (!IntStream.range(0, keyCount).allMatch(allocatedKeys::contains)) {
            throw new RuntimeException("Bug unconstitency between key count " + keyCount + " and key id " + allocatedKeys);
        }
    }

    private AbstractField create(String name, Class<?> fieldClass, boolean isKeyField, int keyIndex, int index, java.lang.reflect.Field field) {
        if (StringField.class.isAssignableFrom(fieldClass)) {
            DefaultString defaultString = field.getAnnotation(DefaultString.class);
            String defaultValue = defaultString != null ? defaultString.value() : null;
            return fieldFactory.addString(name, isKeyField, keyIndex, index, defaultValue);
        }
        else if (IntegerField.class.isAssignableFrom(fieldClass)) {
            DefaultInteger defaultInteger = field.getAnnotation(DefaultInteger.class);
            return fieldFactory.addInteger(name, isKeyField, keyIndex, index,
                                           defaultInteger != null ? defaultInteger.value() : null);
        }
        else if (LongField.class.isAssignableFrom(fieldClass)) {
            DefaultLong defaultLong = field.getAnnotation(DefaultLong.class);
            return fieldFactory.addLong(name, isKeyField, keyIndex, index,
                                        defaultLong != null ? defaultLong.value() : null);
        }
        else if (BooleanField.class.isAssignableFrom(fieldClass)) {
            DefaultBoolean defaultBoolean = field.getAnnotation(DefaultBoolean.class);
            return fieldFactory.addBoolean(name, isKeyField, keyIndex, index,
                                           defaultBoolean != null ? defaultBoolean.value() : null);
        }
        else if (DoubleField.class.isAssignableFrom(fieldClass)) {
            DefaultDouble defaultDouble = field.getAnnotation(DefaultDouble.class);
            return fieldFactory.addDouble(name, isKeyField, keyIndex, index,
                                          defaultDouble != null ? defaultDouble.value() : null);
        }
        else if (BlobField.class.isAssignableFrom(fieldClass)) {
            return fieldFactory.addBlob(name, index);
        }
        else if (DoubleArrayField.class.isAssignableFrom(fieldClass)) {
            return fieldFactory.addDoubleArray(name, isKeyField, keyIndex, index);
        }
        else if (IntegerArrayField.class.isAssignableFrom(fieldClass)) {
            return fieldFactory.addIntegerArray(name, isKeyField, keyIndex, index);
        }
        else if (BooleanArrayField.class.isAssignableFrom(fieldClass)) {
            return fieldFactory.addBooleanArray(name, isKeyField, keyIndex, index);
        }
        else if (LongArrayField.class.isAssignableFrom(fieldClass)) {
            return fieldFactory.addLongArray(name, isKeyField, keyIndex, index);
        }
        else if (StringArrayField.class.isAssignableFrom(fieldClass)) {
            return fieldFactory.addStringArray(name, isKeyField, keyIndex, index);
        }
        else if (DateField.class.isAssignableFrom(fieldClass)) {
            return fieldFactory.addDate(name, isKeyField, keyIndex, index);
        }
        else if (DateTimeField.class.isAssignableFrom(fieldClass)) {
            return fieldFactory.addDateTime(name, isKeyField, keyIndex, index);
        }
        else if (BigDecimalField.class.isAssignableFrom(fieldClass)) {
            return fieldFactory.addBigDecimal(name, isKeyField, keyIndex, index);
        }
        else if (BigDecimalArrayField.class.isAssignableFrom(fieldClass)) {
            return fieldFactory.addBigDecimalArray(name, isKeyField, keyIndex, index);
        }
        else {
            throw new InvalidParameter("Unknown type " + fieldClass.getName());
        }
    }

    private void processIndex(Class<?> targetClass) {
        for (java.lang.reflect.Field classField : targetClass.getFields()) {
            if (isUniqueIndexField(classField)) {
                SingleFieldIndex index = fieldFactory.addUniqueIndex(classField.getName());
                setClassField(classField, index, targetClass);
                type.addIndex(index);
            }
            if (isNotUniqueIndexField(classField)) {
                SingleFieldIndex index = fieldFactory.addNotUniqueIndex(classField.getName());
                setClassField(classField, index, targetClass);
                type.addIndex(index);
            }
            if (isMultiFieldNotUniqueIndexField(classField)) {
                MultiFieldNotUniqueIndex index = fieldFactory.addMultiFieldNotUniqueIndex(classField.getName());
                setClassField(classField, index, targetClass);
                type.addIndex(index);
            }
            if (isMultiFieldUniqueIndexField(classField)) {
                MultiFieldUniqueIndex index = fieldFactory.addMultiFieldUniqueIndex(classField.getName());
                setClassField(classField, index, targetClass);
                type.addIndex(index);
            }
        }
    }

    private boolean isMultiFieldUniqueIndexField(java.lang.reflect.Field field) {
        return MultiFieldUniqueIndex.class.isAssignableFrom(field.getType());
    }

    private boolean isMultiFieldNotUniqueIndexField(java.lang.reflect.Field field) {
        return MultiFieldNotUniqueIndex.class.isAssignableFrom(field.getType());
    }

    private boolean isNotUniqueIndexField(java.lang.reflect.Field field) {
        return NotUniqueIndex.class.isAssignableFrom(field.getType());
    }

    private boolean isUniqueIndexField(java.lang.reflect.Field field) {
        return UniqueIndex.class.isAssignableFrom(field.getType());
    }

    private static void setClassField(java.lang.reflect.Field classField, Object value, Class<?> targetClass) {
        try {
            classField.set(null, value);
        }
        catch (Exception e) {
            throw GlobTypeLoaderImpl.getFieldAccessException(targetClass, classField, value, e);
        }
    }

    private static RuntimeException getFieldAccessException(Class<?> targetClass, java.lang.reflect.Field classField, Object value, Exception e) {
        String valueDescription;
        if (value != null) {
            valueDescription = value.toString() + " (class " + value.getClass().getName() + ")";
        }
        else {
            valueDescription = "'null'";
        }
        return new RuntimeException("Unable to initialize field " + targetClass.getName() + "." + classField.getName() +
                                    " with value " + valueDescription, e);
    }

    GlobTypeLoader addField(AbstractField field) throws ItemAlreadyExists {
        if (type.hasField(field.getName())) {
            throw new ItemAlreadyExists("Field " + field.getName() +
                                        " declared twice for type " + type.getName());
        }
        type.addField(field);
        return this;
    }

    private String getTypeName(Class<?> aClass) {
        if (name != null) {
            return name;
        }
        else {
            String fullName = aClass.getName();
            int lastSeparatorIndex = Math.max(fullName.lastIndexOf("."), fullName.lastIndexOf("$"));
            return Strings.uncapitalize(fullName.substring(lastSeparatorIndex + 1));
        }
    }

    private String getFieldName(java.lang.reflect.Field field) {
        if (field.getName().length() == 1) {
            return field.getName();
        }
        return Strings.toNiceLowerCase(field.getName());
    }

    private boolean isGlobField(java.lang.reflect.Field field) {
        return Field.class.isAssignableFrom(field.getType());
    }

    private boolean isGlobLink(java.lang.reflect.Field field) {
        return Link.class.isAssignableFrom(field.getType());
    }

    public void defineUniqueIndex(UniqueIndex index, Field field) {
        if (index == null) {
            throw new RuntimeException("index is null. Was load call before?");
        }
        ((DefaultUniqueIndex)type.getIndex(index.getName())).setField(field);
    }

    public void defineNonUniqueIndex(NotUniqueIndex index, Field field) {
        if (index == null) {
            throw new RuntimeException("index is null. Was load call before?");
        }
        ((DefaultNotUniqueIndex)type.getIndex(index.getName())).setField(field);
    }

    public void defineMultiFieldUniqueIndex(MultiFieldUniqueIndex index, Field... fields) {
        if (index == null) {
            throw new RuntimeException("index is null. Was load call before?");
        }
        ((DefaultMultiFieldUniqueIndex)type.getIndex(index.getName())).setField(fields);
    }

    public void defineMultiFieldNotUniqueIndex(MultiFieldNotUniqueIndex index, Field... fields) {
        if (index == null) {
            throw new RuntimeException("index is null. Was load call before?");
        }
        ((DefaultMultiFieldNotUniqueIndex)type.getIndex(index.getName())).setField(fields);
    }

    public <T> GlobTypeLoader register(Class<T> klass, T t) {
        registered.put(klass, t);
        return this;
    }
}
