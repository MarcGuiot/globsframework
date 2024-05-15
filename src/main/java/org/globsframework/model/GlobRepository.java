package org.globsframework.model;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.links.Link;
import org.globsframework.model.repository.GlobIdGenerator;
import org.globsframework.utils.exceptions.*;

import java.util.Collection;
import java.util.function.Predicate;

public interface GlobRepository extends ReadOnlyGlobRepository {

    void create(Glob glob);

    Glob create(GlobType type, FieldValue... values)
        throws MissingInfo, ItemAlreadyExists;

    // todo
//  Glob create(GlobType type, FieldValues values)
//     throws MissingInfo, ItemAlreadyExists;

    Glob create(Key key, FieldValue... values)
        throws ItemAlreadyExists;

    Glob findOrCreate(Key key, FieldValue... valuesForCreate)
        throws MissingInfo;

    void update(Glob glob, Field field, Object newValue)
        throws ItemNotFound;

    void update(Key key, Field field, Object newValue)
        throws ItemNotFound;

    void update(Glob glob, FieldValue... values)
        throws ItemNotFound;

    void update(Key key, FieldValue... values)
        throws ItemNotFound;

    void setTarget(Key source, Link link, Key target)
        throws ItemNotFound;

    void delete(Glob glob)
        throws ItemNotFound, OperationDenied;

    void delete(Key key)
        throws ItemNotFound, OperationDenied;

    void delete(Collection<Key> keys)
        throws ItemNotFound, OperationDenied;

    void deleteGlobs(Collection<Glob> list)
        throws OperationDenied;

    void delete(GlobType type, Predicate<Glob> matcher)
        throws OperationDenied;

    void deleteAll(GlobType... types)
        throws OperationDenied;

    /**
     * Replaces all the globs of given types with those of the provided list.
     * If no types are given, all globs will be replaced.
     * A {@link ChangeSetListener#globsReset(GlobRepository, java.util.Set)} notification
     * is sent after the reset is performed.
     */
    void reset(Collection<Glob> newGlobs, GlobType... changedTypes);

    void apply(ChangeSet changeSet) throws InvalidParameter;

    void addTriggerAtFirst(ChangeSetListener listener);

    void addTrigger(ChangeSetListener listener);

    void removeTrigger(ChangeSetListener listener);

    void addChangeListener(ChangeSetListener listener);

    void removeChangeListener(ChangeSetListener listener);

    void startChangeSet();

    void completeChangeSet();

    void completeChangeSetWithoutTriggers();

    GlobIdGenerator getIdGenerator();

    interface InvokeAction {
        void run();
    }

    void invokeAfterChangeSet(InvokeAction action);
}
