package org.globsframework.core.metamodel.impl;

import org.globsframework.core.metamodel.Annotations;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.utils.MutableAnnotations;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.utils.container.hash.HashContainer;
import org.globsframework.core.utils.container.specific.HashEmptyGlobContainer;
import org.globsframework.core.utils.exceptions.ItemNotFound;

import java.util.Collection;
import java.util.stream.Stream;

public class DefaultAnnotations implements MutableAnnotations {
    volatile private HashContainer<Key, Glob> annotations;

    public DefaultAnnotations() {
        annotations = HashEmptyGlobContainer.Helper.allocate(0);
    }

    public DefaultAnnotations(HashContainer<Key, Glob> annotations) {
        this.annotations = annotations == null ? HashEmptyGlobContainer.Helper.allocate(0) : annotations;
    }

    public DefaultAnnotations(Glob[] annotations) {
        if (annotations == null || annotations.length == 0) {
            this.annotations = HashEmptyGlobContainer.Helper.allocate(0);
        } else {
            this.annotations = HashEmptyGlobContainer.Helper.allocate(annotations.length);
            for (Glob annotation : annotations) {
                this.annotations.put(annotation.getKey(), annotation);
            }
        }
    }

    public DefaultAnnotations(Annotations annotations) {
       if (annotations instanceof DefaultAnnotations) {
           this.annotations = ((DefaultAnnotations) annotations).annotations;
       }
       else {
           this.annotations = HashEmptyGlobContainer.Helper.allocate(0);
           annotations.streamAnnotations()
                   .forEach(annotation -> this.annotations = this.annotations.put(annotation.getKey(), annotation));
       }
    }

    public DefaultAnnotations(Collection<Glob> annotations) {
        if (annotations == null || annotations.isEmpty()) {
            this.annotations = HashEmptyGlobContainer.Helper.allocate(0);
        } else {
            this.annotations = HashEmptyGlobContainer.Helper.allocate(annotations.size());
            for (Glob annotation : annotations) {
                this.annotations.put(annotation.getKey(), annotation);
            }
        }
    }

    public MutableAnnotations addAnnotation(Glob glob) {
        if (glob != null) {
            synchronized (this) {
                HashContainer<Key, Glob> tmp = HashEmptyGlobContainer.Helper.allocate(annotations.size() + 1);
                annotations.forEach(tmp::put);
                tmp.put(glob.getKey(), glob);
                annotations = tmp;
            }
        }
        return this;
    }

    public MutableAnnotations addAnnotations(Collection<Glob> globs) {
        synchronized (this) {
            HashContainer<Key, Glob> tmp = HashEmptyGlobContainer.Helper.allocate(annotations.size() + globs.size());
            annotations.forEach(tmp::put);
            globs.forEach(glob -> {
                tmp.put(glob.getKey(), glob);
            });
            annotations = tmp;
        }
        return this;
    }

    public Stream<Glob> streamAnnotations() {
        return annotations.stream();
    }

    public Stream<Glob> streamAnnotations(GlobType type) {
        return annotations.stream().filter(glob -> glob.getType() == type);
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
