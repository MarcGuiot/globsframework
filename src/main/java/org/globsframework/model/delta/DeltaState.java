package org.globsframework.model.delta;

import org.globsframework.metamodel.Field;
import org.globsframework.model.ChangeSetVisitor;
import org.globsframework.model.FieldValues;
import org.globsframework.utils.exceptions.InvalidState;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.Unset;
import org.globsframework.utils.Utils;

interface DeltaState {
  void processCreation(DeltaGlob glob, FieldValues values);

  void processUpdate(DeltaGlob glob, Field field, Object value, Object previousValue);

  void processDeletion(DeltaGlob glob, FieldValues values);

  void visit(DeltaGlob glob, ChangeSetVisitor visitor) throws Exception;

  DeltaState reverse();

  DeltaState UNCHANGED = new UnchangedDeltaState();
  DeltaState CREATED = new CreatedDeltaState();
  DeltaState UPDATED = new UpdatedDeltaState();
  DeltaState DELETED = new DeletedDeltaState();

  public static class UnchangedDeltaState implements DeltaState {
    public void processCreation(DeltaGlob delta, FieldValues values) {
      delta.setState(CREATED);
      delta.setValues(values);
    }

    public void processUpdate(DeltaGlob delta, Field field, Object value, Object previousValue) {
      delta.setState(UPDATED);
      delta.setValue(field, value, previousValue);
    }

    public void processDeletion(DeltaGlob delta, FieldValues values) {
      delta.setState(DELETED);
      delta.setPreviousValues(values);
    }

    public void visit(DeltaGlob glob, ChangeSetVisitor visitor) throws Exception {
    }

    public DeltaState reverse() {
      return DeltaState.UNCHANGED;
    }

    public String toString() {
      return "unchanged";
    }
  }

  public static class CreatedDeltaState implements DeltaState {
    public void processCreation(DeltaGlob delta, FieldValues values) {
      throw new InvalidState("Object " + delta.getKey() + " already exists");
    }

    public void processUpdate(DeltaGlob delta, Field field, Object value, Object previousValue) {
      delta.setValue(field, value);
    }

    public void processDeletion(DeltaGlob delta, FieldValues values) {
      delta.setState(UNCHANGED);
    }

    public void visit(DeltaGlob delta, ChangeSetVisitor visitor) throws Exception {
      visitor.visitCreation(delta.getKey(), delta);
    }

    public DeltaState reverse() {
      return DeltaState.DELETED;
    }

    public String toString() {
      return "created";
    }
  }

  public static class UpdatedDeltaState implements DeltaState {
    public void processCreation(DeltaGlob delta, FieldValues values) {
      throw new InvalidState("Object " + delta.getKey() + " already exists");
    }

    public void processUpdate(DeltaGlob delta, Field field, Object value, Object previousValue) {
      if (previousValue == Unset.VALUE){
        throw new InvalidParameter("Cannot use unset previous value in Updated state for field " + field.getName() +
                                   " on object: " +  delta.getKey());
      }
      if (delta.contains(field)) {
        delta.setValueForUpdate(field, value);
      }
      else {
        delta.setValue(field, value, previousValue);
      }
    }

    public void processDeletion(DeltaGlob delta, FieldValues values) {
      delta.setState(DELETED);
      delta.mergePreviousValues(values);
    }

    public void visit(DeltaGlob delta, ChangeSetVisitor visitor) throws Exception {
      visitor.visitUpdate(delta.getKey(), delta);
    }

    public DeltaState reverse() {
      return DeltaState.UPDATED;
    }

    public String toString() {
      return "updated";
    }
  }

  public static class DeletedDeltaState implements DeltaState {
    public void processCreation(DeltaGlob delta, FieldValues values) {
      delta.setState(UPDATED);
      delta.setValues(values);
      delta.cleanupChanges();
    }

    public void processUpdate(DeltaGlob delta, Field field, Object value, Object previousValue) {
      throw new InvalidState("Object " + delta.getKey() + " was deleted and cannot be updated");
    }

    public void processDeletion(DeltaGlob delta, FieldValues values) {
      throw new InvalidState("Object " + delta.getKey() + " was already deleted");
    }

    public void visit(DeltaGlob delta, ChangeSetVisitor visitor) throws Exception {
      visitor.visitDeletion(delta.getKey(), delta.getPreviousValues());
    }

    public DeltaState reverse() {
      return DeltaState.CREATED;
    }

    public String toString() {
      return "deleted";
    }
  }
}
