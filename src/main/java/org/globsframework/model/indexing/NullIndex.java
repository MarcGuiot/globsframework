package org.globsframework.model.indexing;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.utils.GlobFunctor;

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
