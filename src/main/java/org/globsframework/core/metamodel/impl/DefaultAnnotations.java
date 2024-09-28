package org.globsframework.core.metamodel.impl;

import org.globsframework.core.metamodel.Annotations;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.utils.MutableAnnotations;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.format.GlobPrinter;
import org.globsframework.core.utils.exceptions.DuplicateGlobType;
import org.globsframework.core.utils.exceptions.ItemNotFound;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class DefaultAnnotations implements MutableAnnotations {
    private static final LinkedHashMap<Key, Glob> EMPTY_MAP = new LinkedHashMap<>();
    volatile private Map<Key, Glob> annotations;

    public DefaultAnnotations() {
        annotations = EMPTY_MAP;
    }

    public DefaultAnnotations(LinkedHashMap<Key, Glob> annotations) {
        this.annotations = annotations == null ? EMPTY_MAP : annotations;
    }

    public DefaultAnnotations(Glob[] annotations) {
        if (annotations == null || annotations.length == 0) {
            this.annotations = EMPTY_MAP;
        } else {
            this.annotations = new LinkedHashMap<>(annotations.length);
            for (Glob annotation : annotations) {
                this.annotations.put(annotation.getKey(), annotation);
            }
        }
    }

    public DefaultAnnotations(Annotations annotations) {
        Collection<Glob> copy = annotations.getAnnotations();
        if (copy.isEmpty()) {
            this.annotations = EMPTY_MAP;
        } else {
            this.annotations = new LinkedHashMap<>((int) Math.ceil(((double) copy.size()) / 0.75));
            annotations.streamAnnotations()
                    .forEach(annotation -> this.annotations.put(annotation.getKey(), annotation));
        }
    }

    public DefaultAnnotations(Collection<Glob> annotations) {
        if (annotations == null || annotations.isEmpty()) {
            this.annotations = EMPTY_MAP;
        } else {
            this.annotations = new LinkedHashMap<>((int) Math.ceil(((double) annotations.size()) / 0.75));
            for (Glob annotation : annotations) {
                this.annotations.put(annotation.getKey(), annotation);
            }
        }
    }

    public MutableAnnotations addAnnotation(Glob glob) {
        if (glob != null) {
            synchronized (this) {
                Map<Key, Glob> tmp = new LinkedHashMap<>(annotations);
                Glob old = tmp.put(glob.getKey(), glob);
                if (old != null && old != glob) {
                    String msg = GlobPrinter.toString(glob) + " will have replaced " + GlobPrinter.toString(old);
                    throw new DuplicateGlobType(msg);
                }
                annotations = tmp;
            }
        }
        return this;
    }

    public MutableAnnotations addAnnotations(Collection<Glob> globs) {
        synchronized (this) {
            Map<Key, Glob> tmp = new LinkedHashMap<>(annotations);
            globs.forEach(glob -> {
                Glob old = tmp.put(glob.getKey(), glob);
                if (old != null && old != glob) {
                    String msg = GlobPrinter.toString(glob) + " will have replaced " + GlobPrinter.toString(old);
                    throw new DuplicateGlobType(msg);
                }
            });

            annotations = tmp;
        }
        return this;
    }

    public Stream<Glob> streamAnnotations() {
        return annotations.values().stream();
    }

    public Stream<Glob> streamAnnotations(GlobType type) {
        return annotations.values().stream().filter(glob -> glob.getType() == type);
    }

    public boolean hasAnnotation(Key key) {
        return annotations.containsKey(key);
    }

    public Collection<Glob> getAnnotations() {
        return annotations.values();
    }

    public Glob getAnnotation(Key key) {
        Glob annotation = annotations.get(key);
        if (annotation == null) {
            throw new ItemNotFound(key.toString());
        }
        return annotation;
    }

    public Glob findAnnotation(Key key) {
        return annotations.get(key);
    }
}
