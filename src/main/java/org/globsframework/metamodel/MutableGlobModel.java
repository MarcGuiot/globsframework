package org.globsframework.metamodel;

public interface MutableGlobModel extends GlobModel {
   void add(GlobType type);

   void complete();
}
