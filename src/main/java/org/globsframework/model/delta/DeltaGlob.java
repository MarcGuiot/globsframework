package org.globsframework.model.delta;

import org.globsframework.metamodel.Field;
import org.globsframework.model.ChangeSetVisitor;
import org.globsframework.model.FieldValues;
import org.globsframework.model.FieldValuesWithPrevious;
import org.globsframework.model.Key;

interface DeltaGlob extends FieldValuesWithPrevious {

    Key getKey();

    void setState(DeltaState state);

    boolean isSet(Field field);

    void setValue(Field field, Object value);

    void setValue(Field field, Object value, Object previousValue);

    void setValueForUpdate(Field field, Object value);

    void setValues(FieldValues values);

    void setPreviousValues(FieldValues values);

    void mergePreviousValues(FieldValues values);

    FieldValues getValues();

    FieldValues getPreviousValues();

    void resetValues();

    void visit(ChangeSetVisitor visitor) throws Exception;

    void safeVisit(ChangeSetVisitor visitor);

    void cleanupChanges();
}
