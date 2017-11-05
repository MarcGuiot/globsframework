package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.index.Index;
import org.globsframework.metamodel.index.MultiFieldIndex;
import org.globsframework.metamodel.links.Link;
import org.globsframework.model.utils.GlobFunctor;
import org.globsframework.model.utils.GlobMatcher;
import org.globsframework.utils.exceptions.ItemAmbiguity;
import org.globsframework.utils.exceptions.ItemNotFound;

import java.util.Comparator;
import java.util.Set;

public interface ReadOnlyGlobRepository {

//    GlobModel getModel();

    boolean contains(Key key);

    boolean contains(GlobType type);

    boolean contains(GlobType type, GlobMatcher matcher);

    Glob find(Key key);

    Glob get(Key key) throws ItemNotFound;

    GlobList getAll(GlobType... type);

    GlobList getAll(GlobType type, GlobMatcher matcher);

    void apply(GlobType type, GlobMatcher matcher, GlobFunctor callback) throws Exception;

    void safeApply(GlobType type, GlobMatcher matcher, GlobFunctor callback);

    Glob findUnique(GlobType type, FieldValue... values)
        throws ItemAmbiguity;

    Glob findUnique(GlobType type, GlobMatcher matcher)
        throws ItemAmbiguity;

    Glob[] getSorted(GlobType type, Comparator<Glob> comparator, GlobMatcher matcher);

    GlobList findByIndex(Index index, Object value);

    MultiFieldIndexed findByIndex(MultiFieldIndex uniqueIndex, Field field, Object value);

    Set<GlobType> getTypes();

    Glob findLinkTarget(Glob source, Link link);

    GlobList findLinkedTo(Key target, Link link);

    GlobList findLinkedTo(Glob target, Link link);

    interface MultiFieldIndexed {
        GlobList getGlobs();

//        Stream<Glob> streamByIndex(Object value);

        GlobList findByIndex(Object value);

        MultiFieldIndexed findByIndex(Field field, Object value);

        void saveApply(GlobFunctor functor, GlobRepository repository);
    }
}
