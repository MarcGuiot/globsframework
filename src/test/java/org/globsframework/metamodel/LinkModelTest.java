package org.globsframework.metamodel;

import org.globsframework.metamodel.links.impl.DefaultMutableGlobLinkModel;
import org.globsframework.metamodel.impl.DefaultGlobModel;

public class LinkModelTest {

   @org.junit.Test
   public void testName() throws Exception {
      GlobModel globModel = new DefaultGlobModel(DummyObject.TYPE, DummyObject2.TYPE);
      MutableGlobLinkModel linkModel = new DefaultMutableGlobLinkModel(globModel);


   }
}
