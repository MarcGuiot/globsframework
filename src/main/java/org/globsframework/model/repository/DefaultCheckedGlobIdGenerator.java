package org.globsframework.model.repository;

import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.KeyBuilder;
import org.globsframework.model.repository.GlobIdGenerator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultCheckedGlobIdGenerator implements GlobIdGenerator {
  private Map<IntegerField, Integer> fieldToCurrentId = new HashMap<IntegerField, Integer>();
  private GlobRepository repository;

  public DefaultCheckedGlobIdGenerator() {
  }

  public void setRepository(GlobRepository repository) {
    this.repository = repository;
  }

  public int getNextId(IntegerField keyField, int idCount) {
    Integer currentId = getNextCurrentId(keyField, idCount);
    try {
      return currentId;
    }
    finally {
      fieldToCurrentId.put(keyField, currentId + idCount);
    }
  }

  private Integer getNextCurrentId(IntegerField keyField, int idCount) {
    Integer currentId = fieldToCurrentId.get(keyField);
    if (currentId == null) {
      return maxId(keyField) + 1;
    }
    return getNextCurrentId(keyField, currentId, idCount);
  }

  private Integer getNextCurrentId(IntegerField keyField, Integer currentId, int idCount) {
    int next = currentId;

    if (repository == null) {
      return next;
    }

    for (; ;) {
      boolean ok = true;
      for (int i = 0; i < idCount; i++) {
        Glob alreadyExistingGlob = repository.find(KeyBuilder.init(keyField, next + i).get());
        if (alreadyExistingGlob != null) {
          next = next + i + 1;
          ok = false;
          break;
        }
      }

      if (ok) {
        return next;
      }
    }
  }

  private Integer maxId(IntegerField keyField) {
    if (repository == null) {
      return 0;
    }

    GlobList globs = repository.getAll(keyField.getGlobType());
    if (globs.isEmpty()) {
      return -1;
    }
    return Collections.max(globs.getValueSet(keyField));
  }

  public void update(IntegerField field, Integer lastAllocatedId) {
    fieldToCurrentId.put(field, lastAllocatedId + 1);
  }
}
