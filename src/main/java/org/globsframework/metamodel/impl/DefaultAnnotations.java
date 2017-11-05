package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.Annotations;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.utils.MutableAnnotations;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.model.format.GlobPrinter;
import org.globsframework.utils.collections.MapOfMaps;
import org.globsframework.utils.exceptions.ItemNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.SIZED;

public class DefaultAnnotations<T extends MutableAnnotations> implements MutableAnnotations<T> {
    static private final Logger LOGGER = LoggerFactory.getLogger(DefaultAnnotations.class);
    volatile private MapOfMaps<GlobType, Key, Glob> annotations = new MapOfMaps<>();

    public DefaultAnnotations() {
    }

    public DefaultAnnotations(Glob[] annotations) {
        for (Glob annotation : annotations) {
            this.annotations.put(annotation.getType(), annotation.getKey(), annotation);
        }
    }

    public DefaultAnnotations(Annotations annotations) {
        annotations.streamAnnotations()
            .forEach(annotation -> this.annotations.put(annotation.getType(), annotation.getKey(), annotation)
            );
    }

    public T addAnnotation(Glob glob) {
        if (glob != null) {
            synchronized (this) {
                MapOfMaps<GlobType, Key, Glob> tmp = new MapOfMaps<>(annotations);
                Glob old = tmp.put(glob.getType(), glob.getKey(), glob);
                if (old != null) {
                    LOGGER.warn(GlobPrinter.toString(glob) + " has replaced " + GlobPrinter.toString(old));
                }
                annotations = tmp;
            }
        }
        return (T)this;
    }

    public T addAnnotations(Stream<Glob> globs) {
        synchronized (this) {
            MapOfMaps<GlobType, Key, Glob> tmp = new MapOfMaps<>(annotations);
            globs.forEach(glob -> {
                Glob old = tmp.put(glob.getType(), glob.getKey(), glob);
                if (old != null) {
                    LOGGER.warn(GlobPrinter.toString(glob) + " has replaced " + GlobPrinter.toString(old));
                }
            });

            annotations = tmp;
        }
        return (T)this;
    }

    public Stream<Glob> streamAnnotations() {
        return StreamSupport.stream(Spliterators.spliterator(annotations.iterator(), annotations.size(), SIZED), false);
    }

    public Stream<Glob> streamAnnotations(GlobType type) {
        return annotations.get(type).values().stream();
    }

    public boolean hasAnnotation(Key key) {
        return annotations.containsKey(key.getGlobType(), key);
    }

    public Glob getAnnotation(Key key) {
        Glob annotation = annotations.get(key.getGlobType(), key);
        if (annotation == null) {
            throw new ItemNotFound(key.toString());
        }
        return annotation;
    }

    public Glob findAnnotation(Key key) {
        return annotations.get(key.getGlobType(), key);
    }

}
