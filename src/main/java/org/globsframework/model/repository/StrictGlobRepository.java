package org.globsframework.model.repository;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.*;
import org.globsframework.model.repository.GlobRepositoryDecorator;
import org.globsframework.model.utils.GlobFunctor;
import org.globsframework.model.utils.GlobMatcher;
import org.globsframework.utils.Log;
import org.globsframework.utils.exceptions.ExceptionHandler;
import org.globsframework.utils.exceptions.*;

import java.util.Collection;

public class StrictGlobRepository extends GlobRepositoryDecorator {
  private ExceptionHandler exceptionHandler;

  public StrictGlobRepository(GlobRepository repository, ExceptionHandler exceptionHandler) {
    super(repository);
    this.exceptionHandler = exceptionHandler;
  }

  public void apply(GlobType type, GlobMatcher matcher, GlobFunctor callback) throws Exception {
    try {
      super.apply(type, matcher, callback);
    }
    catch (Throwable e) {
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }
  }

  public Glob create(GlobType type, FieldValue... values) throws MissingInfo, ItemAlreadyExists {
    try {
      return super.create(type, values);
    }
    catch (Throwable e) {
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }
  }

  public Glob create(Key key, FieldValue... values) throws ItemAlreadyExists {
    try {
      return super.create(key, values);
    }
    catch (Throwable e) {
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }

  }

  public void update(Key key, Field field, Object newValue) throws ItemNotFound {
    try {
      super.update(key, field, newValue);
    }
    catch (Throwable e) {
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }
  }

  public void update(Key key, FieldValue... values) {
    try {
      super.update(key, values);
    }
    catch (Throwable e) {
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }
  }

  public void delete(Key key) throws ItemNotFound, OperationDenied {
    try {
      super.delete(key);
    }
    catch (Throwable e) {
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }
  }

  public void delete(Collection<Key> keys) throws ItemNotFound, OperationDenied {
    try {
      super.delete(keys);
    }
    catch (Throwable e) {
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }
  }

  public void delete(GlobList list) throws OperationDenied {
    try {
      super.delete(list);
    }
    catch (Throwable e) {
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }
  }

  public void deleteAll(GlobType... types) throws OperationDenied {
    try {
      super.deleteAll(types);
    }
    catch (Throwable e) {
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }
  }

  public void apply(ChangeSet changeSet) throws InvalidParameter {
    try {
      super.apply(changeSet);
    }
    catch (Throwable e) {
      Log.write("in strict glob repository", e);
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }
  }

  public void completeChangeSet() {
    try {
      super.completeChangeSet();
    }
    catch (Throwable e) {
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }
  }

  public void completeChangeSetWithoutTriggers() {
    try {
      super.completeChangeSetWithoutTriggers();
    }
    catch (Throwable e) {
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }
  }

  public void reset(GlobList newGlobs, GlobType... changedTypes) {
    try {
      super.reset(newGlobs, changedTypes);
    }
    catch (Throwable e) {
      exceptionHandler.onException(e);
      throw new RuntimeException(e);
    }
  }
}
