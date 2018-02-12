package org.globsframework.metamodel;

import org.globsframework.metamodel.annotations.DefaultDoubleAnnotationType;
import org.globsframework.metamodel.annotations.NamingFieldAnnotationType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.type.DataType;
import org.globsframework.metamodel.utils.GlobTypeUtils;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemAlreadyExists;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class GlobTypeBuilderTest {

    @Test
    public void test() throws Exception {
        GlobType type = GlobTypeBuilderFactory.create("aType")
            .addIntegerKey("id")
            .addStringField("string")
            .addIntegerField("int")
            .addLongField("long")
            .addDoubleField("double")
            .addBlobField("blob")
            .addBooleanField("boolean")
            .addBooleanArrayField("booleanArray")
            .addBigDecimalField("bigDecimal")
            .addBigDecimalArrayField("bigArrayDecimal")
            .addDoubleArrayField("doubleArray")
            .addIntegerArrayField("intArray")
            .addLongArrayField("longArray")
            .addDateField("date")
            .addDateTimeField("time")
            .get();

        assertEquals("aType", type.getName());

        Field[] keyFields = type.getKeyFields();
        assertEquals(1, keyFields.length);
        Field key = keyFields[0];
        assertTrue(key instanceof IntegerField);
        assertEquals("id", key.getName());

        checkField(type, "string", StringField.class, DataType.String);
        checkField(type, "int", IntegerField.class, DataType.Integer);
        checkField(type, "intArray", IntegerArrayField.class, DataType.IntegerArray);
        checkField(type, "long", LongField.class, DataType.Long);
        checkField(type, "longArray", LongArrayField.class, DataType.LongArray);
        checkField(type, "double", DoubleField.class, DataType.Double);
        checkField(type, "doubleArray", DoubleArrayField.class, DataType.DoubleArray);
        checkField(type, "blob", BlobField.class, DataType.Bytes);
        checkField(type, "boolean", BooleanField.class, DataType.Boolean);
        checkField(type, "booleanArray", BooleanArrayField.class, DataType.BooleanArray);
        checkField(type, "bigDecimal", BigDecimalField.class, DataType.BigDecimal);
        checkField(type, "bigArrayDecimal", BigDecimalArrayField.class, DataType.BigDecimalArray);
        checkField(type, "date", DateField.class, DataType.Date);
        checkField(type, "time", DateTimeField.class, DataType.DateTime);
    }

    private void checkField(GlobType type, String fieldName, Class<? extends Field> fieldClass, DataType dataType) {
        Field field = type.getField(fieldName);
        assertTrue(fieldClass.isAssignableFrom(field.getClass()));
        assertEquals(dataType, field.getDataType());
    }

    @Test
    public void testCannotUseTheSameNameTwice() throws Exception {
        try {
            GlobTypeBuilderFactory.create("aType")
                .addIntegerKey("id")
                .addStringField("field")
                .addIntegerField("field");
            fail();
        }
        catch (ItemAlreadyExists e) {
            assertEquals("Field field declared twice for type aType", e.getMessage());
        }
    }

    @Ignore
    @Test
    public void testAtLeastOneKeyMustBeDefined() throws Exception {
        try {
            GlobTypeBuilderFactory.create("type").get();
            fail();
        }
        catch (InvalidParameter e) {
            assertEquals("GlobType type has no key field", e.getMessage());
        }
    }

    @Test
    public void testNamingField() throws Exception {
        GlobType type = GlobTypeBuilderFactory.create("aType")
            .addIntegerKey("id")
            .addStringField("name", NamingFieldAnnotationType.UNIQUE_GLOB)
            .get();

        StringField field = GlobTypeUtils.findNamingField(type);
        assertNotNull(field);
        assertEquals("name", field.getName());
    }

    @Test
    public void testWithAnnotations() throws Exception {
        GlobTypeBuilderFactory.create("aType")
            .addDoubleField("aDouble", DefaultDoubleAnnotationType.create(2.2));

    }
}
