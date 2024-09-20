package org.globsframework.core.model.delta;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.*;

public interface DeltaGlob extends FieldValuesWithPrevious {

    Key getKey();

    void setState(DeltaState state);

    boolean isSet(Field field);

    void setValue(Field field, Object value);

    void setPreviousValue(Field field, Object value);

    void setValue(Field field, Object value, Object previousValue);

    void setValueForUpdate(Field field, Object value);

    void setValues(FieldsValueScanner values);

    void setPreviousValues(FieldsValueScanner values);

    void mergePreviousValues(FieldsValueScanner values);

    FieldValues getValues();

    FieldValues getPreviousValues();

    void resetValues();

    void visit(ChangeSetVisitor visitor) throws Exception;

    void safeVisit(ChangeSetVisitor visitor);

    void cleanupChanges();
}
