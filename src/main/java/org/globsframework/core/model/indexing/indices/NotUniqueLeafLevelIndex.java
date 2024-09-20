package org.globsframework.core.model.indexing.indices;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.GlobRepository;
import org.globsframework.core.model.utils.GlobFunctor;
import org.globsframework.core.utils.exceptions.UnexpectedApplicationState;

import java.util.*;
import java.util.stream.Stream;

public class NotUniqueLeafLevelIndex implements UpdatableMultiFieldIndex, GlobRepository.MultiFieldIndexed {
    private Map<Object, List<Glob>> indexedGlob = new HashMap<>();
    private Field field;

    public NotUniqueLeafLevelIndex(Field field) {
        this.field = field;
    }

    public List<Glob> getGlobs() {
        List<Glob> globs = new ArrayList<>();
        for (List<Glob> glob : indexedGlob.values()) {
            globs.addAll(glob);
        }
        return globs;
    }

    public void saveApply(GlobFunctor functor, GlobRepository repository) {
        try {
            for (Collection<Glob> globList : indexedGlob.values()) {
                for (Glob glob : globList) {
                    functor.run(glob, repository);
                }
            }
        } catch (Exception e) {
            throw new UnexpectedApplicationState(e);
        }
    }

    public Stream<Glob> streamByIndex(Object value) {
        Collection<Glob> globs = indexedGlob.get(value);
        return globs == null ? Stream.empty() : globs.stream();
    }

    public List<Glob> findByIndex(Object value) {
        List<Glob> globs = indexedGlob.get(value);
        if (globs == null) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(globs);
        }
    }

    public boolean remove(Glob glob) {
        Object value = glob.getValue(this.field);
        List<Glob> globList = indexedGlob.get(value);
        if (globList != null) {
            globList.remove(glob);
            if (globList.isEmpty()) {
                indexedGlob.remove(value);
            }
        }
        return indexedGlob.isEmpty();
    }

    public void removeAll() {
        indexedGlob.clear();
    }

    public GlobRepository.MultiFieldIndexed findByIndex(Field field, final Object value) {
        return new GlobRepository.MultiFieldIndexed() {
            public List<Glob> getGlobs() {
                return NotUniqueLeafLevelIndex.this.findByIndex(value);
            }

            public Stream<Glob> streamByIndex(Object value) {
                return Stream.empty();
            }

            public void saveApply(GlobFunctor functor, GlobRepository repository) {
                try {
                    List<Glob> globs = indexedGlob.get(value);
                    if (globs == null) {
                        return;
                    }
                    for (Glob glob : globs) {
                        functor.run(glob, repository);
                    }
                } catch (Exception e) {
                    throw new UnexpectedApplicationState(e);
                }
            }

            public List<Glob> findByIndex(Object value) {
                return new ArrayList<>();
            }

            public GlobRepository.MultiFieldIndexed findByIndex(Field field, Object value) {
                return null;
            }
        };
    }

    public void add(Field field, Object newValue, Object oldValue, Glob glob) {
        if (field == this.field) {
            List<Glob> oldGlobList = indexedGlob.get(oldValue);
            if (oldGlobList != null) {
                oldGlobList.remove(glob);
                if (oldGlobList.isEmpty()) {
                    indexedGlob.remove(oldValue);
                }
            }
        }
        add(glob);
    }

    public boolean remove(Field field, Object value, Glob glob) {
        if (this.field == field) {
            List<Glob> oldGlob = indexedGlob.get(value);
            if (oldGlob != null) {
                oldGlob.remove(glob);
                if (oldGlob.isEmpty()) {
                    indexedGlob.remove(value);
                }
            }
        } else {
            Object oldValue = glob.getValue(this.field);
            List<Glob> oldGlob = indexedGlob.get(oldValue);
            if (oldGlob != null) {
                oldGlob.remove(glob);
                if (oldGlob.isEmpty()) {
                    indexedGlob.remove(value);
                }
            }
        }
        return indexedGlob.isEmpty();
    }

    public void add(Glob glob) {
        Object value = glob.getValue(field);
        List<Glob> globList = indexedGlob.computeIfAbsent(value, k -> new ArrayList<>());
        globList.add(glob);
    }
}
