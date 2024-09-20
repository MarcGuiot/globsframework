package org.globsframework.core.model.indexing;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.GlobRepository;
import org.globsframework.core.model.utils.GlobFunctor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NullIndex implements GlobRepository.MultiFieldIndexed {
    public static GlobRepository.MultiFieldIndexed INSTANCE = new NullIndex();

    public List<Glob> getGlobs() {
        return Collections.emptyList();
    }

    public List<Glob> findByIndex(Object value) {
        return new ArrayList<>();
    }

    public GlobRepository.MultiFieldIndexed findByIndex(Field field, Object value) {
        return this;
    }

    public void saveApply(GlobFunctor functor, GlobRepository repository) {
    }
}
