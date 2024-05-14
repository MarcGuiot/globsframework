package org.globsframework.metamodel;

import org.globsframework.metamodel.utils.GlobTypeDependencies;

import java.util.Collection;

public interface GlobModel extends Iterable<GlobType>, GlobTypeResolver {

    Collection<GlobType> getAll();

    GlobType findType(String name);

    boolean hasType(String name);

    GlobTypeDependencies getDependencies();

    GlobLinkModel getLinkModel();
}
