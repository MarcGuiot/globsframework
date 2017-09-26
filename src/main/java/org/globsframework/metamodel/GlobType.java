package org.globsframework.metamodel;

import org.globsframework.metamodel.index.Index;
import org.globsframework.metamodel.index.MultiFieldIndex;
import org.globsframework.metamodel.properties.PropertyHolder;
import org.globsframework.model.GlobFactory;
import org.globsframework.model.Key;
import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.GlobGetAccessor;
import org.globsframework.model.globaccessor.GlobSetAccessor;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.util.Collection;

public interface GlobType extends PropertyHolder<GlobType>, Annotations {

   String getName();

   Field getField(String name) throws ItemNotFound;

   Field findField(String name);

   boolean hasField(String name);

   Field[] getFields();

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

   default MutableGlob instantiate() {
      return getGlobFactory().create();
   }
}
