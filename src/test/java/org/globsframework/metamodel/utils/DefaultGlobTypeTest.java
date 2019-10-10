package org.globsframework.metamodel.utils;

import org.globsframework.metamodel.*;
import org.globsframework.metamodel.annotations.FieldNameAnnotation;
import org.globsframework.metamodel.annotations.FieldNameAnnotationType;
import org.globsframework.metamodel.annotations.KeyField;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.impl.DefaultGlobModel;
import org.globsframework.metamodel.impl.DefaultGlobType;
import org.globsframework.metamodel.properties.Property;
import org.globsframework.utils.Ref;
import org.globsframework.utils.TestUtils;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultGlobTypeTest {
    private GlobType globType;
    private Field field;
    private GlobModel globModel;

    @Test
    public void testGlobTypeProperty() throws Exception {
        initGlobType();

        Property<GlobType, Ref<Integer>> globTypeProperty =
            globModel.createGlobTypeProperty("globType info", value -> new Ref<Integer>());
        assertEquals("globType info", globTypeProperty.getName());
        assertEquals(0, globTypeProperty.getId());

        globType.getProperty(globTypeProperty).set(3);
        assertEquals(3, globType.getProperty(globTypeProperty).get().intValue());

        globType.getProperty(globTypeProperty).set(4);
        assertEquals(4, globType.getProperty(globTypeProperty).get().intValue());

        Property<Field, Integer> property = globModel.createFieldProperty("field info", value -> 0);
        assertEquals(0, property.getId());
        field.updateProperty(property, 2);
        assertEquals(2, field.getProperty(property).intValue());

        field.updateProperty(property, 4);
        assertEquals(4, field.getProperty(property).intValue());
        assertEquals("field info", property.getName());
    }

    @Test
    public void testFields() {
        initGlobType();
        assertEquals("type", globType.getName());
        assertNotNull(globType.findField("field1"));
        assertNull(globType.findField("Field1"));
        assertNotNull(field);
        TestUtils.assertFails(() -> globType.getFields(), UnexpectedApplicationState.class);
    }

    @Test
    public void testFindFieldByAnnotation() {
        Field qty = GlobTypeUtils.findFieldWithAnnotation(TypeWithAnnotation.TYPE, FieldNameAnnotationType.create("qty"));
        Assert.assertSame(qty, TypeWithAnnotation.F1);

        Field sku = GlobTypeUtils.findFieldWithAnnotation(TypeWithAnnotation.TYPE, FieldNameAnnotationType.create("ean"));
        Assert.assertSame(sku, TypeWithAnnotation.F2);
    }

    @Test
    public void testFieldProperty() throws Exception {
        initGlobType();
        Property<Field, Ref<Integer>> property = globModel.createFieldProperty("field property",
                                                                               value -> new Ref<Integer>());
        field.getProperty(property).set(3);
        assertEquals(3, field.getProperty(property).get().intValue());
    }

    public static class Type {
        public static GlobType TYPE;

        @KeyField
        public static IntegerField FIELD1;
    }

    private void initGlobType() {
        Type.TYPE = null;
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(Type.class).load();
        globType = loader.getType();
        field = globType.getField("field1");
        globModel = new DefaultGlobModel(globType);
    }


    public static class TypeWithAnnotation {
        public static GlobType TYPE;

        @FieldNameAnnotation("qty")
        public static IntegerField F1;

        @FieldNameAnnotation("ean")
        public static IntegerField F2;

        static {
            GlobTypeLoaderFactory.create(TypeWithAnnotation.class)
            .load();
        }
    }

}
