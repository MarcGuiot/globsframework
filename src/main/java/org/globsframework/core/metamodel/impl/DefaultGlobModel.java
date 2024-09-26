package org.globsframework.core.metamodel.impl;

import org.globsframework.core.metamodel.GlobLinkModel;
import org.globsframework.core.metamodel.GlobModel;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.MutableGlobModel;
import org.globsframework.core.metamodel.links.impl.DefaultMutableGlobLinkModel;
import org.globsframework.core.metamodel.utils.GlobTypeDependencies;
import org.globsframework.core.utils.exceptions.DuplicateGlobType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultGlobModel implements MutableGlobModel {
    private Map<String, GlobType> typesByName = new ConcurrentHashMap<>();
    private GlobModel innerModel;
    private GlobTypeDependencies dependencies;
    private DefaultMutableGlobLinkModel globLinkModel;

    public DefaultGlobModel(GlobType... types) {
        this(null, types);
    }

    public DefaultGlobModel(GlobModel innerModel, GlobType... types) {
        add(types);
        this.innerModel = innerModel;
    }

    public GlobType findType(String name) {
        GlobType globType = typesByName.get(name);
        if (globType != null) {
            return globType;
        }
        if (innerModel != null) {
            return innerModel.findType(name);
        }
        return null;
    }

    public boolean hasType(String name) {
        return typesByName.containsKey(name);
    }

    public Collection<GlobType> getAll() {
        Set<GlobType> result = new HashSet<GlobType>();
        result.addAll(typesByName.values());
        if (innerModel != null) {
            result.addAll(innerModel.getAll());
        }
        return result;
    }

    public Iterator<GlobType> iterator() {
        return getAll().iterator();
    }

    public GlobTypeDependencies getDependencies() {
        return dependencies;
    }

    public GlobLinkModel getLinkModel() {
        return globLinkModel;
    }

    private void add(GlobType... types) {
        Arrays.stream(types).forEach(this::add);
    }

    public void add(GlobType type) {
        GlobType put = typesByName.put(type.getName(), type);
        if (put != null && put != type) {
            String message = type.getName() + " already registered  : " + type.describe() + " AND " + put.describe();
            throw new DuplicateGlobType(message);
        }
    }

    public void complete() {
        globLinkModel = new DefaultMutableGlobLinkModel(this);
        this.dependencies = new GlobTypeDependencies(getAll(), globLinkModel);
    }
}
