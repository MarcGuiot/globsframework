package org.globsframework.metamodel;

import org.junit.Assert;
import org.junit.Test;

public class InnerGlobTypeTest {
    @Test
    public void load() {
        Assert.assertEquals(DummyObjectWithInner.VALUE.getTargetType(), DummyObjectInner.TYPE);
        Assert.assertEquals(DummyObjectWithInner.VALUES.getTargetType(), DummyObjectInner.TYPE);
    }
}
