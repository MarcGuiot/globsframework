package org.globsframework.metamodel;

import org.globsframework.metamodel.fields.FieldVisitor;
import org.globsframework.metamodel.index.Index;
import org.globsframework.metamodel.index.MultiFieldIndex;
import org.globsframework.metamodel.properties.PropertyHolder;
import org.globsframework.model.GlobFactory;
import org.globsframework.model.Key;
import org.globsframework.model.MutableGlob;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.util.Collection;
import java.util.stream.Stream;

public interface GlobType extends PropertyHolder<GlobType>, Annotations {

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

    Collection<MultiFieldIndex> getMultiFieldIndices();

    GlobFactory getGlobFactory();

    <T> T getRegistered(Class<T> klass);

    String describe();

//    GlobGetAccessor getAccessor(Field field);

    default MutableGlob instantiate() {
        return getGlobFactory().create();
    }

    default <T extends FieldVisitor> T accept(T visitor) throws Exception {
        return getGlobFactory().accept(visitor);
    }
}
