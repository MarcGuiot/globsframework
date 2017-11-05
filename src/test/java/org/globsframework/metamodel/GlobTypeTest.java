package org.globsframework.metamodel;

import org.globsframework.metamodel.annotations.NamingFieldAnnotationType;
import org.globsframework.utils.TestUtils;
import org.junit.Test;

public class GlobTypeTest {

    @Test
    public void testRetrieveFieldFromAnnotation() {
        TestUtils.assertContains(DummyObjectWithMaxSizeString.TYPE
                                     .getFieldsWithAnnotation(NamingFieldAnnotationType.UNIQUE_KEY), DummyObjectWithMaxSizeString.TEXT);

    }

}
