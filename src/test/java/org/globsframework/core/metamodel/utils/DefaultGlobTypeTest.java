package org.globsframework.core.metamodel.utils;

import org.globsframework.core.metamodel.GlobModel;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.annotations.FieldNameAnnotation;
import org.globsframework.core.metamodel.annotations.FieldNameAnnotationType;
import org.globsframework.core.metamodel.annotations.KeyField;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.impl.DefaultGlobModel;
import org.globsframework.core.utils.TestUtils;
import org.globsframework.core.utils.exceptions.UnexpectedApplicationState;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultGlobTypeTest {
    private GlobType globType;
    private Field field;
    private GlobModel globModel;

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

    public static class Type {
        public static GlobType TYPE;

        @KeyField
        public static IntegerField FIELD1;
    }

    private void initGlobType() {
        Type.TYPE = null;
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(Type.class, true).load();
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
            GlobTypeLoaderFactory.create(TypeWithAnnotation.class, true)
                    .load();
        }
    }

}
