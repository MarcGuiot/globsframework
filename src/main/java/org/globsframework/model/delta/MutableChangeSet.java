package org.globsframework.model.delta;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.*;
import org.globsframework.utils.exceptions.InvalidState;

import java.util.Collection;

public interface MutableChangeSet extends ChangeSet {
    void processCreation(Key key, FieldsValueScanner values);

    void processUpdate(Key key, Field field, Object newValue, Object previousValue);

    void processUpdate(Key key, FieldsValueWithPreviousScanner values);

    void processDeletion(Key key, FieldsValueScanner values);

    void merge(ChangeSet other) throws InvalidState;

    void clear(Collection<GlobType> globTypes);
}
