package org.globsframework.metamodel.fields.impl;

import org.globsframework.metamodel.Annotations;
import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.annotations.KeyAnnotationType;
import org.globsframework.metamodel.annotations.RequiredAnnotationType;
import org.globsframework.metamodel.properties.impl.AbstractDelegatePropertyHolder;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.utils.Utils;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

abstract public class AbstractField implements Field, AbstractDelegatePropertyHolder<Field> {
   private final int index;
   private final int keyIndex;
   private final GlobType globType;
   private final Map<Key, Glob> annotations = new HashMap<Key, Glob>();
   private final String name;
   private final Class valueClass;
   private final Object defaultValue;
   private final DataType dataType;
   private volatile Object[] properties = new Object[]{NULL_OBJECT, NULL_OBJECT};
   private final boolean keyField;

   protected AbstractField(String name, GlobType globType,
                           Class valueClass, int index, int keyIndex, boolean isKeyField,
                           Object defaultValue, DataType dataType) {
      this.keyIndex = keyIndex;
      this.defaultValue = defaultValue;
      this.name = name;
      this.keyField = isKeyField;
      this.index = index;
      this.globType = globType;
      this.valueClass = valueClass;
      this.dataType = dataType;
   }

   public Object normalize(Object value) {
      return value;
   }

   public String getName() {
      return name;
   }

   public String getFullName() {
      return globType.getName() + "." + name;
   }

   public GlobType getGlobType() {
      return globType;
   }

   public int getIndex() {
      return index;
   }

   public int getKeyIndex() {
      return keyIndex;
   }

   public boolean isKeyField() {
      return keyField;
   }

   public boolean isRequired() {
      return hasAnnotation(RequiredAnnotationType.UNIQUE_KEY);
   }

   public DataType getDataType() {
      return dataType;
   }

   public void checkValue(Object object) throws InvalidParameter {
      if ((object != null) && (!valueClass.equals(object.getClass()))) {
         throw new InvalidParameter("Value '" + object + "' (" + object.getClass().getName()
                                    + ") is not authorized for field: " + getName() +
                                    " (expected " + valueClass.getName() + ")");
      }
   }

   public Class getValueClass() {
      return valueClass;
   }

   public Object getDefaultValue() {
      return defaultValue;
   }

   public String toString() {
      return globType.getName() + "." + name;
   }

   public boolean valueEqual(Object o1, Object o2) {
      return Utils.equal(o1, o2);
   }


   public Field addAnnotation(Glob glob) {
      if (glob != null) {
         annotations.put(glob.getKey(), glob);
         if (glob.getType() == KeyAnnotationType.TYPE && !isKeyField()){
            throw new RuntimeException("Key field can not be set after key creation.");
         }
      }
      return this;
   }

   public void addAll(Annotations annotations) {
      for (Glob glob : annotations.listAnnotations()) {
         this.addAnnotation(glob);
      }
   }

   public boolean hasAnnotation(Key key) {
      return annotations.containsKey(key);
   }

   public Glob getAnnotation(Key key) {
      Glob annotation = annotations.get(key);
      if (annotation == null) {
         throw new ItemNotFound(key == null ? "null" : key.toString());
      }
      return annotation;
   }

   public Glob findAnnotation(Key key) {
      return annotations.get(key);
   }

   public Collection<Glob> listAnnotations() {
      return Collections.unmodifiableCollection(annotations.values());
   }

   final public Object[] getProperties() {
      return properties;
   }

   final public void setProperties(Object[] properties) {
      this.properties = properties;
   }

   final public Field getValueOwner() {
      return this;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      AbstractField other = (AbstractField)o;
      return globType.equals(other.globType) && name.equals(other.name);
   }

   public int hashCode() {
      int result;
      result = name.hashCode();
      result = 31 * result + globType.hashCode();
      return result;
   }
}
