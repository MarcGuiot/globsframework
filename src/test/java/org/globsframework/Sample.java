package org.globsframework;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeBuilderFactory;
import org.globsframework.metamodel.annotations.NamingFieldAnnotationType;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.model.MutableGlob;

import static org.junit.Assert.assertEquals;

public class Sample {

    void readme() {
        GlobType type = GlobTypeBuilderFactory.create("aType")
                .addIntegerKey("id")
                .addStringField("string", NamingFieldAnnotationType.UNIQUE_GLOB)
                .addIntegerField("int")
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
    }
}
