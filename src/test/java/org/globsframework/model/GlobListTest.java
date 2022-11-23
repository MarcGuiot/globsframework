package org.globsframework.model;

import junit.framework.TestCase;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.annotations.KeyField;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.fields.StringField;

import java.util.List;

public class GlobListTest extends TestCase {

    public void testMatches() {
        Glob a = A.create(1, "a");
        Glob b = A.create(1, "b");

        assertFalse(new GlobList(a).matches(List.of(b)));
        assertTrue(new GlobList(a).matches(List.of(a)));
        assertFalse(new GlobList(a).matches(List.of(a, a)));
        assertFalse(new GlobList(a, a).matches(List.of(a)));
        assertTrue(new GlobList(a, b).matches(List.of(b, a)));
    }

    public static class A {

        public static GlobType TYPE;

        @KeyField
        public static IntegerField id;

        public static StringField str;

        static {
            GlobTypeLoaderFactory.create(A.class).load();
        }

        public static Glob create(int idValue, String strValue) {
            return A.TYPE.instantiate()
                    .set(id, idValue)
                    .set(str, strValue);
        }
    }

}