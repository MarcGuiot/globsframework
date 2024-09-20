package org.globsframework.core.model;

import org.globsframework.core.metamodel.DummyObject;
import org.globsframework.core.utils.exceptions.InvalidParameter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FieldValuesBuilderTest {
    @Test
    public void testValuesMustComplyWithTheFieldType() throws Exception {
        try {
            FieldValuesBuilder.init().setValue(DummyObject.PRESENT, "a");
            fail();
        } catch (InvalidParameter e) {
            assertEquals("Value 'a' (java.lang.String) is not authorized for field: " +
                    DummyObject.PRESENT.getName() + " (expected java.lang.Boolean)", e.getMessage());
        }
    }
}
