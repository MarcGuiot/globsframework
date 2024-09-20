package org.globsframework.core.model.delta;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.ChangeSet;
import org.globsframework.core.model.FieldsValueScanner;
import org.globsframework.core.model.FieldsValueWithPreviousScanner;
import org.globsframework.core.model.Key;
import org.globsframework.core.utils.exceptions.InvalidState;

import java.util.Collection;

public interface MutableChangeSet extends ChangeSet {
    void processCreation(Key key, FieldsValueScanner values);

    void processUpdate(Key key, Field field, Object newValue, Object previousValue);

    void processUpdate(Key key, FieldsValueWithPreviousScanner values);

    void processDeletion(Key key, FieldsValueScanner values);

    void merge(ChangeSet other) throws InvalidState;

    void clear(Collection<GlobType> globTypes);
}
