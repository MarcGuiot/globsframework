package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.impl.DefaultGlobModel;
import org.globsframework.core.metamodel.links.impl.DefaultMutableGlobLinkModel;

public class LinkModelTest {

    @org.junit.Test
    public void testName() throws Exception {
        GlobModel globModel = new DefaultGlobModel(DummyObject.TYPE, DummyObject2.TYPE);
        MutableGlobLinkModel linkModel = new DefaultMutableGlobLinkModel(globModel);


    }
}
