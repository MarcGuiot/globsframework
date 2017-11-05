package org.globsframework.model.delta;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.ChangeSet;
import org.globsframework.model.FieldValues;
import org.globsframework.model.FieldValuesWithPrevious;
import org.globsframework.model.Key;
import org.globsframework.utils.exceptions.InvalidState;

import java.util.Collection;

public interface MutableChangeSet extends ChangeSet {
    void processCreation(Key globKey, FieldValues values);

    void processUpdate(Key key, Field field, Object newValue, Object previousValue);

    void processUpdate(Key key, FieldValuesWithPrevious values);

    void processDeletion(Key key, FieldValues values);

    void merge(ChangeSet other) throws InvalidState;

    void clear(Collection<GlobType> globTypes);
}
