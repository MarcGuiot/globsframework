package org.globsframework.core.model.indexing.indices;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.GlobRepository;
import org.globsframework.core.model.indexing.NullIndex;
import org.globsframework.core.model.indexing.builders.MultiFieldIndexBuilder;
import org.globsframework.core.model.utils.GlobFunctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiddleLevelIndex implements UpdatableMultiFieldIndex, GlobRepository.MultiFieldIndexed {
    private Map<Object, UpdatableMultiFieldIndex> downLevels = new HashMap<Object, UpdatableMultiFieldIndex>();
    private MultiFieldIndexBuilder multiFieldIndexBuilder;

    public MiddleLevelIndex(MultiFieldIndexBuilder multiFieldIndexBuilder) {
        this.multiFieldIndexBuilder = multiFieldIndexBuilder;
    }

    public void add(Field field, Object newValue, Object oldValue, Glob glob) {
        if (field == multiFieldIndexBuilder.getField()) {
            UpdatableMultiFieldIndex oldIndexed = downLevels.get(oldValue);
            if (oldIndexed != null) {
                if (oldIndexed.remove(field, oldValue, glob)) {
                    downLevels.remove(oldValue);
                }
            }
            UpdatableMultiFieldIndex newIndexed = downLevels.get(newValue);
            if (newIndexed == null) {
                newIndexed = multiFieldIndexBuilder.getSubBuilder().create();
                downLevels.put(newValue, newIndexed);
            }
            newIndexed.add(field, newValue, oldValue, glob);
        } else {
            Object value = glob.getValue(multiFieldIndexBuilder.getField());
            UpdatableMultiFieldIndex index = downLevels.get(value);
            if (index == null) {
                index = multiFieldIndexBuilder.getSubBuilder().create();
                downLevels.put(value, index);
            }
            index.add(field, newValue, oldValue, glob);
        }
    }

    public boolean remove(Field field, Object value, Glob glob) {
        if (field == multiFieldIndexBuilder.getField()) {
            UpdatableMultiFieldIndex updatableMultiFieldIndex = downLevels.get(value);
            if (updatableMultiFieldIndex != null) {
                if (updatableMultiFieldIndex.remove(field, value, glob)) {
                    downLevels.remove(value);
                }
            }
        } else {
            Object currentValue = glob.getValue(multiFieldIndexBuilder.getField());
            UpdatableMultiFieldIndex updatableMultiFieldIndex = downLevels.get(currentValue);
            if (updatableMultiFieldIndex != null) {
                if (updatableMultiFieldIndex.remove(field, value, glob)) {
                    downLevels.remove(currentValue);
                }
            }
        }
        return downLevels.isEmpty();
    }

    public List<Glob> getGlobs() {
        List<Glob> globs = new ArrayList<>();
        for (GlobRepository.MultiFieldIndexed multiFieldIndexed : downLevels.values()) {
            globs.addAll(multiFieldIndexed.getGlobs());
        }
        return globs;
    }

    public void saveApply(GlobFunctor functor, GlobRepository repository) {
        for (GlobRepository.MultiFieldIndexed multiFieldIndexed : downLevels.values()) {
            multiFieldIndexed.saveApply(functor, repository);
        }
    }

    public List<Glob> findByIndex(Object value) {
        GlobRepository.MultiFieldIndexed multiFieldIndexed = downLevels.get(value);
        if (multiFieldIndexed != null) {
            return multiFieldIndexed.getGlobs();
        }
        return new ArrayList<>();
    }

    public boolean remove(Glob glob) {
        Object currentValue = glob.getValue(multiFieldIndexBuilder.getField());
        UpdatableMultiFieldIndex updatableMultiFieldIndex = downLevels.get(currentValue);
        if (updatableMultiFieldIndex != null) {
            if (updatableMultiFieldIndex.remove(glob)) {
                downLevels.remove(currentValue);
            }
        }
        return downLevels.isEmpty();
    }

    public void removeAll() {
        downLevels.clear();
    }

    public GlobRepository.MultiFieldIndexed findByIndex(Field field, Object value) {
        GlobRepository.MultiFieldIndexed multiFieldIndexed = downLevels.get(value);
        if (multiFieldIndexed != null) {
            return multiFieldIndexed;
        } else {
            return NullIndex.INSTANCE;
        }
    }

    public void add(Glob glob) {
        Object value = glob.getValue(multiFieldIndexBuilder.getField());
        UpdatableMultiFieldIndex updatableMultiFieldIndex = downLevels.get(value);
        if (updatableMultiFieldIndex == null) {
            updatableMultiFieldIndex = multiFieldIndexBuilder.getSubBuilder().create();
            downLevels.put(value, updatableMultiFieldIndex);
        }
        updatableMultiFieldIndex.add(glob);
    }
}
