package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.utils.GlobTypeDependencies;

import java.util.Collection;

public interface GlobModel extends Iterable<GlobType>, GlobTypeResolver {

    Collection<GlobType> getAll();

    GlobType findType(String name);

    boolean hasType(String name);

    GlobTypeDependencies getDependencies();

    GlobLinkModel getLinkModel();
}
