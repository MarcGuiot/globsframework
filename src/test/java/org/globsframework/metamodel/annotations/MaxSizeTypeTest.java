package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.fields.GlobField;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.model.MutableGlob;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class MaxSizeTypeTest {

    @Test
    public void deepSize() {
        {
            MutableGlob g1 = Dummy_Level1.TYPE.instantiate()
                    .set(Dummy_Level1.VALUE_1, "123")
                    .set(Dummy_Level1.VALUE_2, "12345");
            MutableGlob mutableGlob = MaxSizeType.deepInPlaceTruncate(g1);
            Assert.assertEquals(mutableGlob.get(Dummy_Level1.VALUE_2), "123");
            Assert.assertEquals(mutableGlob.get(Dummy_Level1.VALUE_1), "123");

            MutableGlob g2 = Dummy_Level1.TYPE.instantiate()
                    .set(Dummy_Level1.VALUE_1, "12345")
                    .set(Dummy_Level1.VALUE_2, "12345");

            try {
                MaxSizeType.deepInPlaceTruncate(g2);
                fail();
            } catch (MaxSizeType.StringToLongException e) {
            }
        }
        {
            MutableGlob g1 = Dummy_Level1.TYPE.instantiate()
                    .set(Dummy_Level1.VALUE_1, "123")
                    .set(Dummy_Level1.VALUE_2, "12345");

            MutableGlob g2 = Dummy_Level1.TYPE.instantiate()
                    .set(Dummy_Level1.UNDER, g1);

            MaxSizeType.deepInPlaceTruncate(g2);

            Assert.assertEquals(g2.get(Dummy_Level1.UNDER).get(Dummy_Level1.VALUE_2), "123");

        }
        {
            MutableGlob g1 = Dummy_Level1.TYPE.instantiate()
                    .set(Dummy_Level1.VALUE_2, "éà");

            MutableGlob g2 = Dummy_Level1.TYPE.instantiate()
                    .set(Dummy_Level1.UNDER, g1);

            MaxSizeType.deepInPlaceTruncate(g2);

            Assert.assertEquals(g2.get(Dummy_Level1.UNDER).get(Dummy_Level1.VALUE_2), "é");

        }
    }

    public static class Dummy_Level1 {
        public static GlobType TYPE;

        @MaxSize(3)
        public static StringField VALUE_1;

        @MaxSize(value = 3, allow_truncate = true)
        public static StringField VALUE_2;

        @MaxSize(value = 50, allow_truncate = true)
        public static StringField VALUE_3;

        @Target(Dummy_Level1.class)
        public static GlobField UNDER;

        static {
            GlobTypeLoaderFactory.create(Dummy_Level1.class).load();
        }
    }
}