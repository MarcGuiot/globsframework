package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.*;
import org.globsframework.metamodel.links.impl.DefaultMutableGlobLinkModel;
import org.globsframework.metamodel.utils.GlobTypeDependencies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultGlobModel implements MutableGlobModel {
    private static Logger LOGGER = LoggerFactory.getLogger(DefaultGlobModel.class);
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
            LOGGER.error(type.getName() + " already registered  : " + type.describe() + " AND " + put.describe(), new RuntimeException());
        }
    }

    public void complete() {
        globLinkModel = new DefaultMutableGlobLinkModel(this);
        this.dependencies = new GlobTypeDependencies(getAll(), globLinkModel);
    }
}
