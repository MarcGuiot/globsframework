package org.globsframework.core.model;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.Field;

import java.util.Set;

public interface ChangeSet {

    boolean containsChanges(GlobType type);

    boolean containsCreationsOrDeletions(GlobType type);

    boolean containsCreations(GlobType type);

    boolean containsDeletions(GlobType type);

    boolean containsUpdates(Field field);

    boolean containsChanges(Key key);

    /**
     * return true if Glob for key is created, deleted or updated on Field field
     */
    boolean containsChanges(Key key, Field... fields);

    boolean isEmpty();

    GlobType[] getChangedTypes();

    int getChangeCount();

    int getChangeCount(GlobType type);

    Set<Key> getChanged(GlobType type);

    Set<Key> getCreated(GlobType type);

    Set<Key> getUpdated(GlobType type);

    Set<Key> getCreatedOrUpdated(GlobType type);

    Set<Key> getUpdated(Field field);

    Set<Key> getDeleted(GlobType type);

    boolean isCreated(Key key);

    boolean isDeleted(Key key);

    FieldValues getNewValues(Key key);

    FieldValues getPreviousValues(Key key);

    void accept(ChangeSetVisitor visitor) throws Exception;

    void safeAccept(ChangeSetVisitor visitor);

    void accept(GlobType type, ChangeSetVisitor visitor) throws Exception;

    void safeAccept(GlobType type, ChangeSetVisitor visitor);

    void accept(Key key, ChangeSetVisitor visitor) throws Exception;

    void safeAccept(Key key, ChangeSetVisitor visitor);

    ChangeSet reverse();
}
