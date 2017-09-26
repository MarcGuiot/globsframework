package org.globsframework.metamodel;

import org.globsframework.metamodel.links.Link;
import org.globsframework.utils.TestUtils;
import org.junit.Test;

public class DummyModelTest {

   @Test
   public void test() throws Exception {
      DummyModel.get(); // initialize all classes
      GlobLinkModel model = DummyModel.get().getLinkModel();
      Link[] expected = {
         DummyObjectWithRequiredLink.LINK,
         DummyObjectWithLinkFieldId.LINK,
         DummyObject.LINK,
         DummyObjectWithLinks.PARENT_LINK,
      };
      TestUtils.assertContained(model.getInboundLinks(DummyObject.TYPE), expected);
   }
}
