package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.NamingFieldAnnotationType;
import org.globsframework.core.utils.TestUtils;
import org.junit.Test;

public class GlobTypeTest {

    @Test
    public void testRetrieveFieldFromAnnotation() {
        TestUtils.assertContains(DummyObjectWithMaxSizeString.TYPE
                .getFieldsWithAnnotation(NamingFieldAnnotationType.UNIQUE_KEY), DummyObjectWithMaxSizeString.TEXT);

    }

}
