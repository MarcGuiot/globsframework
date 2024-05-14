package org.globsframework.metamodel;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.fields.FieldVisitor;
import org.globsframework.metamodel.fields.FieldVisitorWithContext;
import org.globsframework.metamodel.impl.DefaultValuesFieldVisitor;
import org.globsframework.metamodel.index.Index;
import org.globsframework.metamodel.utils.MutableAnnotations;
import org.globsframework.model.GlobFactory;
import org.globsframework.model.Key;
import org.globsframework.model.MutableGlob;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public interface GlobType extends MutableAnnotations {

    String getName();

    Field getField(String name) throws ItemNotFound;

    <T extends Field> T getTypedField(String name) throws ItemNotFound;

    Field findField(String name);

    boolean hasField(String name);

    Field[] getFields();

    Stream<Field> streamFields();

    Field getField(int index);

    int getFieldCount();

    Field[] getKeyFields();

    Field findFieldWithAnnotation(Key key);

    Field getFieldWithAnnotation(Key key) throws ItemNotFound;

    Collection<Field> getFieldsWithAnnotation(Key key);

    Collection<Index> getIndices();

    GlobFactory getGlobFactory();

    <T> T getRegistered(Class<T> klass);

    <T> T getRegistered(Class<T> klass, T NULL);

    String describe();

//    GlobGetAccessor getAccessor(Field field);

    default MutableGlob instantiate() {
        return getGlobFactory().create();
    }

    default MutableGlob instantiateWithDefaults() {
        MutableGlob glob = getGlobFactory().create();
        FieldVisitorWithContext<MutableGlob> visitor = new DefaultValuesFieldVisitor();
        streamFields().forEach(field -> field.safeVisit(visitor, glob));
        return glob;
    }

    default <T extends FieldVisitor> T accept(T visitor) throws Exception {
        return getGlobFactory().accept(visitor);
    }

    default <T extends FieldVisitor> T saveAccept(T visitor) {
        try {
            return getGlobFactory().accept(visitor);
        } catch (Exception e) {
            throw new RuntimeException("for visitor : " + visitor.getClass().getName(), e);
        }
    }

    default boolean hasKeys() {
        return getKeyFields().length != 0;
    }

    default Optional<Field> findOptField(String name) {
        return Optional.ofNullable(findField(name));
    }

    Comparator<Key> sameKeyComparator();

}
