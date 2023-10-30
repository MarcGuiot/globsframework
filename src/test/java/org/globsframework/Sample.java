package org.globsframework;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeBuilderFactory;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.annotations.*;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.model.Glob;
import org.globsframework.model.MutableGlob;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Sample {

    @Test
    public void readmeDynamic() {
        GlobType type = GlobTypeBuilderFactory.create("aType")
                .addIntegerKey("id")
                .addStringField("string", NamingFieldAnnotationType.UNIQUE_GLOB)
                .addIntegerField("anInt", FieldNameAnnotationType.create("int"))
                .addLongField("long")
                .addDoubleField("double")
                .addBlobField("blob")
                .addBooleanField("boolean")
                .get();

        MutableGlob data = type.instantiate();

        StringField stringField = type.getField("string").asStringField();
        data.set(stringField, "Hello");

        assertEquals("Hello", data.get(stringField));

        Field namingField = data.getType().findFieldWithAnnotation(NamingFieldAnnotationType.UNIQUE_KEY);
        assertEquals("Hello", data.getValue(namingField));
        assertEquals("int", FieldNameAnnotationType.getName(type.getField("anInt")));
    }

    public static class AType {
        public static GlobType TYPE;

        @KeyField
        public static IntegerField ID;

        @NamingField()
        public static StringField string;

        @FieldNameAnnotation("int")
        public static IntegerField anInt;

        static {
            GlobTypeLoaderFactory.create(AType.class).load();
        }
    }

    @Test
    public void readStatic() {
        final Glob data = AType.TYPE.instantiate()
                .set(AType.string, "Hello")
                .set(AType.anInt, 2);

        assertEquals("Hello", data.get(AType.string));
        assertEquals(2, data.get(AType.anInt).intValue());

    }
}
