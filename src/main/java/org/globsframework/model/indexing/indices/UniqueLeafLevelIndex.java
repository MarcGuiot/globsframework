package org.globsframework.model.indexing.indices;

import org.globsframework.metamodel.Field;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.format.GlobPrinter;
import org.globsframework.model.utils.GlobFunctor;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class UniqueLeafLevelIndex implements UpdatableMultiFieldIndex, GlobRepository.MultiFieldIndexed {
    private Map<Object, Glob> indexedGlob = new HashMap<Object, Glob>();
    private Field field;

    public UniqueLeafLevelIndex(Field field) {
        this.field = field;
    }

    public GlobList getGlobs() {
        GlobList globs = new GlobList();
        for (Glob glob : indexedGlob.values()) {
            globs.add(glob);
        }
        return globs;
    }

    public Stream<Glob> streamByIndex(Object value) {
        Glob glob = indexedGlob.get(value);
        return glob == null ? Stream.empty() : Stream.of(glob);
    }

    public void saveApply(GlobFunctor functor, GlobRepository repository) {
        try {
            for (Glob glob : indexedGlob.values()) {
                functor.run(glob, repository);
            }
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState(e);
        }
    }

    public GlobList findByIndex(Object value) {
        Glob glob = indexedGlob.get(value);
        if (glob == null) {
            return new GlobList();
        }
        else {
            return new GlobList(glob);
        }
    }

    public boolean remove(Glob glob) {
        Object oldValue = glob.getValue(this.field);
        Glob oldGlob = indexedGlob.remove(oldValue);
        if (oldGlob != null && oldGlob != glob) {
            indexedGlob.put(oldValue, oldGlob);
        }
        return indexedGlob.isEmpty();
    }

    public void removeAll() {
        indexedGlob.clear();
    }

    public GlobRepository.MultiFieldIndexed findByIndex(Field field, final Object value) {
        return new GlobRepository.MultiFieldIndexed() {
            public GlobList getGlobs() {
                return UniqueLeafLevelIndex.this.findByIndex(value);
            }

            public GlobList findByIndex(Object value) {
                return new GlobList();
            }

            public GlobRepository.MultiFieldIndexed findByIndex(Field field, Object value) {
                return null;
            }

            public void saveApply(GlobFunctor functor, GlobRepository repository) {
                try {
                    Glob glob = indexedGlob.get(value);
                    if (glob != null) {
                        functor.run(glob, repository);
                    }
                }
                catch (Exception e) {
                    throw new UnexpectedApplicationState(e);
                }
            }
        };
    }

    public void add(Field field, Object newValue, Object oldValue, Glob glob) {
        if (this.field == field) {
            Glob oldGlob = indexedGlob.remove(oldValue);
            if (oldGlob != null && oldGlob != glob) {
                indexedGlob.put(oldValue, oldGlob);
            }
            indexedGlob.put(newValue, glob);
        }
        else {
            indexedGlob.put(glob.getValue(this.field), glob);
        }
    }

    public boolean remove(Field field, Object value, Glob glob) {
        if (this.field == field) {
            Glob oldGlob = indexedGlob.remove(value);
            if (oldGlob != null && oldGlob != glob) {
                indexedGlob.put(value, oldGlob);
            }
            return indexedGlob.isEmpty();
        }
        else {
            return remove(glob);
        }
    }

    public void add(Glob glob) {
        Object value = glob.getValue(this.field);
        Glob oldGlob = indexedGlob.put(value, glob);
        if (oldGlob != null) {
            throw new RuntimeException("Should be an unique index\n" +
                                       "- field: " + field.getName() + "\n" +
                                       "- type: " + field.getGlobType() + "\n" +
                                       "- new: \n " +
                                       GlobPrinter.toString(glob) + "\n" +
                                       "- old: \n " +
                                       GlobPrinter.toString(oldGlob));
        }
    }
}
