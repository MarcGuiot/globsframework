package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.junit.Assert;
import org.junit.Test;

public class AnnotationsHelperTest {

    static public class Type1 {
        public static GlobType TYPE;

        static {
            GlobTypeLoaderFactory.createAndLoad(Type1.class);
        }
    }

    static public class Type2 {
        public static GlobType OTHER_TYPE;
    }

    static public class Type3 {
        public static GlobType TYPE;
        public static GlobType OTHER_TYPE;
    }

    @Test
    public void findGlobType() {
        AnnotationsHelper.getType(Type1.class);
        try {
            AnnotationsHelper.getType(Type2.class);
            Assert.fail();
        } catch (RuntimeException e) {
        }
        try {
            AnnotationsHelper.getType(Type3.class);
            Assert.fail();
        } catch (RuntimeException e) {
        }
    }
}