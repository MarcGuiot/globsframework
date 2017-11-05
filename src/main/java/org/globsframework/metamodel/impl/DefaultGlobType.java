package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.annotations.KeyAnnotationType;
import org.globsframework.metamodel.fields.impl.AbstractField;
import org.globsframework.metamodel.index.Index;
import org.globsframework.metamodel.index.MultiFieldIndex;
import org.globsframework.metamodel.properties.impl.AbstractDelegatePropertyHolder;
import org.globsframework.metamodel.utils.MutableAnnotations;
import org.globsframework.metamodel.utils.MutableGlobType;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobFactory;
import org.globsframework.model.GlobFactoryService;
import org.globsframework.model.Key;
import org.globsframework.model.format.GlobPrinter;
import org.globsframework.utils.exceptions.InvalidState;
import org.globsframework.utils.exceptions.ItemAlreadyExists;
import org.globsframework.utils.exceptions.ItemNotFound;
import org.globsframework.utils.exceptions.TooManyItems;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultGlobType extends DefaultAnnotations implements MutableGlobType, MutableAnnotations,
                                                                   AbstractDelegatePropertyHolder<GlobType> {
    private Field[] fields;
    private Field[] keyFields = new Field[0];
    private GlobFactory globFactory;
    private String name;
    private Map<String, Field> fieldsByName = new TreeMap<String, Field>(); // TODO replace with hashMap?
    private Map<String, Index> indices = new HashMap<String, Index>(2, 1);
    private Map<String, MultiFieldIndex> multiFieldIndices = new HashMap<String, MultiFieldIndex>(2, 1);
    private Map<Class, Object> registered = new ConcurrentHashMap<>();
    private volatile Object properties[] = new Object[]{NULL_OBJECT, NULL_OBJECT};


    public DefaultGlobType(String name) {
        this.name = name;
    }

    public int getFieldCount() {
        return fieldsByName.size();
    }

    public Field getField(String name) throws ItemNotFound {
        Field field = fieldsByName.get(name);
        if (field == null) {
            throw new ItemNotFound("Field '" + name + "' not found in type: " + this.name);
        }
        return field;
    }

    public <T extends Field> T getTypedField(String name) throws ItemNotFound {
        return (T)getField(name);
    }

    public boolean hasField(String name) {
        return findField(name) != null;
    }

    public Field findField(String name) {
        return fieldsByName.get(name);
    }

    public Field[] getFields() {
        return fields;
    }

    public Stream<Field> streamFields() {
        return Arrays.stream(fields);
    }

    public Field getField(int index) {
        return fields[index];
    }

    public String getName() {
        return name;
    }


    public void addField(AbstractField field) {
        if (hasField(field.getName())) {
            throw new ItemAlreadyExists("Field " + field.getName() +
                                        " declared twice for type " + getName());
        }
        if (field.getIndex() != fieldsByName.size()) {
            throw new InvalidState(field + " should be at index " + field.getIndex() + " but is at" + fieldsByName.size());
        }
        fieldsByName.put(field.getName(), field);
    }

    public void addKey(Field field) {
        Field[] tmp = new Field[keyFields.length + 1];
        System.arraycopy(keyFields, 0, tmp, 0, keyFields.length);
        keyFields = tmp;
        keyFields[keyFields.length - 1] = field;
    }

    public Field[] getKeyFields() {
        return keyFields;
    }

    public Field getFieldWithAnnotation(Key key) throws ItemNotFound {
        Field foundField = findFieldWithAnnotation(key);
        if (foundField != null) {
            return foundField;
        }
        throw new ItemNotFound("no field found with " + key + " under " + this);
    }

    public Field findFieldWithAnnotation(Key key) {
        Field foundField = null;
        for (Field field : fields) {
            if (field.hasAnnotation(key)) {
                if (foundField != null) {
                    throw new TooManyItems("Found multiple field with " + key + " => " + field + " and " + foundField);
                }
                foundField = field;
            }
        }
        return foundField;
    }

    public Collection<Field> getFieldsWithAnnotation(Key key) {
        List<Field> annotations = new ArrayList<>();
        for (Field field : fields) {
            if (field.hasAnnotation(key)) {
                annotations.add(field);
            }
        }
        return annotations;
    }

    public String toString() {
        return name;
    }

    public void completeInit() {
        fields = new Field[fieldsByName.size()];
        for (Field field : fieldsByName.values()) {
            fields[field.getIndex()] = field;
        }
        int keyFieldCount = 0;
        for (Field field : fields) {
            Glob annotation = field.findAnnotation(KeyAnnotationType.UNIQUE_KEY);
            if (annotation != null) {
                int index = annotation.get(KeyAnnotationType.INDEX, -1);
                if (index == -1) {
                    ((MutableAnnotations)field).addAnnotation(KeyAnnotationType.create(keyFieldCount));
                }
                else if (index != keyFieldCount) {
                    throw new InvalidState("For " + field + " internal index '" + index
                                           + "' is different from computed '" + keyFieldCount + "'");
                }
                keyFieldCount++;
            }
        }
        globFactory = GlobFactoryService.Builder.getBuilderFactory().get(this);
    }

    public void addIndex(Index index) {
        indices.put(index.getName(), index);
    }

    public void addIndex(MultiFieldIndex index) {
        multiFieldIndices.put(index.getName(), index);
    }

    public Collection<Index> getIndices() {
        return indices.values();
    }

    public Collection<MultiFieldIndex> getMultiFieldIndices() {
        return multiFieldIndices.values();
    }

    public GlobFactory getGlobFactory() {
        return globFactory;
    }

    public Index getIndex(String name) {
        return indices.get(name);
    }

    public MultiFieldIndex getMultiFieldIndex(String name) {
        return multiFieldIndices.get(name);
    }

    public <T> void register(Class<T> klass, T t) {
        registered.put(klass, t);
    }

    public <T> T getRegistered(Class<T> klass) {
        return (T)registered.get(klass);
    }

    final public Object[] getProperties() {
        return properties;
    }

    final public void setProperties(Object[] properties) {
        this.properties = properties;
    }

    final public GlobType getValueOwner() {
        return this;
    }

    public String describe() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'").append(name).append("' : ");
        for (Field field : keyFields) {
            stringBuilder.append("key : ").append(field.getName()).append(" (").append(field.getDataType()).append(") ");
            printAnnotations(stringBuilder, field);
            stringBuilder.append(", ");
        }
        for (Field field : fieldsByName.values()) {
            if (!field.isKeyField()) {
                stringBuilder.append(field.getName()).append(" (").append(field.getDataType()).append(") ");
                printAnnotations(stringBuilder, field);
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private void printAnnotations(StringBuilder stringBuilder, Field field) {
        Collection<Glob> annotations = field.streamAnnotations().collect(Collectors.toList());
        if (!annotations.isEmpty()) {
            stringBuilder.append('[');
            List<String> toStrings = new ArrayList<>();
            for (Glob annotation : annotations) {
                toStrings.add(annotation.getType().getName() + ": " + GlobPrinter.toString(annotation));
            }
            Collections.sort(toStrings);
            for (Iterator<String> iterator = toStrings.iterator(); iterator.hasNext(); ) {
                stringBuilder.append(iterator.next());
                if (iterator.hasNext()) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append("]");
        }
    }


}
