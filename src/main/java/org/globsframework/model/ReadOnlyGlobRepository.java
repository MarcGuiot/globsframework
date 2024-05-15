package org.globsframework.model;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.index.MultiFieldIndex;
import org.globsframework.metamodel.index.SingleFieldIndex;
import org.globsframework.metamodel.links.Link;
import org.globsframework.model.utils.GlobFunctor;
import org.globsframework.utils.exceptions.ItemAmbiguity;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public interface ReadOnlyGlobRepository {

//    GlobModel getModel();

    boolean contains(Key key);

    boolean contains(GlobType type);

    boolean contains(GlobType type, Predicate<Glob> matcher);

    Glob find(Key key);

    Glob get(Key key) throws ItemNotFound;

    List<Glob> getAll(GlobType... type);

    List<Glob> getAll(GlobType type, Predicate<Glob> matcher);

    void apply(GlobType type, Predicate<Glob> matcher, GlobFunctor callback) throws Exception;

    void safeApply(GlobType type, Predicate<Glob> matcher, GlobFunctor callback);

    void safeApply(GlobFunctor callback);

    Glob findUnique(GlobType type, FieldValue... values)
            throws ItemAmbiguity;

    Glob findUnique(GlobType type, Predicate<Glob> matcher)
            throws ItemAmbiguity;

    Glob[] getSorted(GlobType type, Comparator<Glob> comparator, Predicate<Glob> matcher);

    List<Glob> findByIndex(SingleFieldIndex index, Object value);

    MultiFieldIndexed findByIndex(MultiFieldIndex uniqueIndex, Field field, Object value);

    Set<GlobType> getTypes();

    Glob findLinkTarget(Glob source, Link link);

    List<Glob> findLinkedTo(Key target, Link link);

    List<Glob> findLinkedTo(Glob target, Link link);

    int size();

    interface MultiFieldIndexed {
        List<Glob> getGlobs();

//        Stream<Glob> streamByIndex(Object value);

        List<Glob> findByIndex(Object value);

        MultiFieldIndexed findByIndex(Field field, Object value);

        void saveApply(GlobFunctor functor, GlobRepository repository);
    }
}
