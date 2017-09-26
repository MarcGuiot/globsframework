package org.globsframework.metamodel.properties.impl;

import org.globsframework.metamodel.properties.Property;
import org.globsframework.metamodel.properties.PropertyHolder;
import org.globsframework.utils.exceptions.ItemNotFound;

public interface AbstractDelegatePropertyHolder<T> extends PropertyHolder<T> {
   Object NULL_OBJECT = new Object();

   // must return from a volatile
   Object[] getProperties();

   // lock getValueOwner and
   void setProperties(Object[] properties);

   T getValueOwner();

   default <D> D getProperty(Property<T, D> key) throws ItemNotFound {
      Object[] currentProp = getProperties();
      int id = key.getId();
      if (id < currentProp.length) {
         Object value = currentProp[id];
         if (value != NULL_OBJECT) {
            return (D)value;
         }
      }
      return addDefaultProperty(key);
   }

   default <D> void updateProperty(Property<T, D> key, D value) {
      synchronized (this) {
         Object[] localProp = getProperties();
         if (key.getId() < localProp.length) {
            localProp[key.getId()] = value;
            return;
         }
         Object[] tmp = new Object[key.getId() + 2];
         int i;
         for (i = 0; i < localProp.length; i++) {
            tmp[i] = localProp[i];
         }
         for (; i < tmp.length; i++) {
            tmp[i] = NULL_OBJECT;
         }
         tmp[key.getId()] = value;
         setProperties(tmp);
      }
   }

   default <D> D addDefaultProperty(Property<T, D> key) {
      synchronized (this) {
         Object[] localProp = getProperties();
         if (key.getId() < localProp.length) {
            return getOrCreate(key, localProp, getValueOwner());
         }
         D value = key.createValue(getValueOwner());
         Object[] tmp = new Object[key.getId() + 2];
         int i;
         for (i = 0; i < localProp.length; i++) {
            tmp[i] = localProp[i];
         }
         for (; i < tmp.length; i++) {
            tmp[i] = NULL_OBJECT;
         }
         tmp[key.getId()] = value;
         setProperties(tmp);
         return value;
      }
   }

   static <T, D> D getOrCreate(Property<T, D> key, Object[] objects, final T owner) {
      Object value = objects[key.getId()];
      if (value == NULL_OBJECT) {
         value = key.createValue(owner);
         objects[key.getId()] = value;
      }
      return (D)value;
   }
}
