package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;

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

  FieldValues getPreviousValues(Key key);

  void visit(ChangeSetVisitor visitor) throws Exception;

  void safeVisit(ChangeSetVisitor visitor);

  void visit(GlobType type, ChangeSetVisitor visitor) throws Exception;

  void safeVisit(GlobType type, ChangeSetVisitor visitor);

  void visit(Key key, ChangeSetVisitor visitor) throws Exception;

  void safeVisit(Key key, ChangeSetVisitor visitor);

  ChangeSet reverse();
}
