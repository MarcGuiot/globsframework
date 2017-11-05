package org.globsframework.metamodel.properties.impl;

import org.globsframework.metamodel.properties.Property;
import org.globsframework.utils.Ref;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PropertyBuilderTest {

    static class SomeClass extends InheritedPropertyHolder<SomeClass> {
        int id;

        public SomeClass(int id) {
            this.id = id;
        }

        public String getName() {
            return "SomeName";
        }
    }

    @Test
    public void testSetGet() throws Exception {
        PropertiesBuilder<SomeClass> builder = new PropertiesBuilder<>();

        Property<SomeClass, Ref<Integer>> prop =
            builder.createProperty("test", myClass -> new Ref<>(myClass.id));
        Property<SomeClass, Ref<Integer>> prop2 =
            builder.createProperty("test2", myClass -> new Ref<>(myClass.id));
        Property<SomeClass, Ref<Integer>> prop3 =
            builder.createProperty("test3", myClass -> new Ref<>(myClass.id));

        SomeClass c1 = new SomeClass(1);
        SomeClass c2 = new SomeClass(2);
        assertEquals(c1.id, c1.getProperty(prop).get().intValue());
        assertEquals(c1.id, c1.getProperty(prop2).get().intValue());
        assertEquals(c2.id, c2.getProperty(prop3).get().intValue());
        c1.getProperty(prop).set(12);
        assertEquals(12, c1.getProperty(prop).get().intValue());
    }
}