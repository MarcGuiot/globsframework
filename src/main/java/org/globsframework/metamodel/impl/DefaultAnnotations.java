package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.Annotations;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.utils.MutableAnnotations;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.model.format.GlobPrinter;
import org.globsframework.utils.exceptions.ItemNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class DefaultAnnotations<T extends MutableAnnotations> implements MutableAnnotations<T> {
    static private final Logger LOGGER = LoggerFactory.getLogger(DefaultAnnotations.class);
    volatile private Map<Key, Glob> annotations = new HashMap<>();

    public DefaultAnnotations() {
    }

    public DefaultAnnotations(Glob[] annotations) {
        for (Glob annotation : annotations) {
            this.annotations.put(annotation.getKey(), annotation);
        }
    }

    public DefaultAnnotations(Annotations annotations) {
        annotations.streamAnnotations()
                .forEach(annotation -> this.annotations.put(annotation.getKey(), annotation)
                );
    }

    public T addAnnotation(Glob glob) {
        if (glob != null) {
            synchronized (this) {
                Map<Key, Glob> tmp = new HashMap<>(annotations);
                Glob old = tmp.put(glob.getKey(), glob);
                if (old != null && old != glob) {
                    LOGGER.warn(GlobPrinter.toString(glob) + " has replaced " + GlobPrinter.toString(old));
                }
                annotations = tmp;
            }
        }
        return (T) this;
    }

    public T addAnnotations(Stream<Glob> globs) {
        synchronized (this) {
            Map<Key, Glob> tmp = new HashMap<>(annotations);
            globs.forEach(glob -> {
                Glob old = tmp.put(glob.getKey(), glob);
                if (old != null && old != glob) {
                    LOGGER.warn(GlobPrinter.toString(glob) + " has replaced " + GlobPrinter.toString(old));
                }
            });

            annotations = tmp;
        }
        return (T) this;
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
