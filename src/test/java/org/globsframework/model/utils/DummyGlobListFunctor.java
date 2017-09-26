package org.globsframework.model.utils;

import org.globsframework.model.Glob;
import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;
import org.globsframework.utils.TestUtils;
import org.junit.Assert;

public class DummyGlobListFunctor implements GlobListFunctor {
   private GlobList lastList;

   public void run(GlobList list, GlobRepository repository) {
      this.lastList = list;
   }

   public void checkNothingReceived() {
      Assert.assertNull(lastList);
   }

   public void checkEmpty() {
      Assert.assertTrue(lastList.isEmpty());
   }

   public void checkReceived(Glob... globs) {
      TestUtils.assertEquals(lastList, globs);
   }
}
