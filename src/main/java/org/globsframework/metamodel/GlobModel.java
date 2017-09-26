package org.globsframework.metamodel;

import org.globsframework.metamodel.properties.Property;
import org.globsframework.metamodel.properties.impl.PropertiesBuilder;
import org.globsframework.metamodel.utils.GlobTypeDependencies;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.util.Collection;

public interface GlobModel extends Iterable<GlobType> {

   Collection<GlobType> getAll();

   GlobType getType(String name) throws ItemNotFound;

   GlobTypeDependencies getDependencies();

   <T>
   Property<GlobType, T> createGlobTypeProperty(String name, final PropertiesBuilder.PropertyBuilder<GlobType, T> valueBuilder);

   <T>
   Property<Field, T> createFieldProperty(String name, final PropertiesBuilder.PropertyBuilder<Field, T> fieldValueBuilder);

   GlobLinkModel getLinkModel();
}
