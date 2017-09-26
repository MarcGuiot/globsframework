package org.globsframework.model.utils;

import org.globsframework.metamodel.DummyObject;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.model.repository.CachedGlobIdGenerator;
import org.globsframework.model.repository.GlobIdGenerator;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CachedGlobIdGeneratorTest {

   @Test
   public void test() throws Exception {
      DummyGlobIdGenerator generator = new DummyGlobIdGenerator();
      CachedGlobIdGenerator cachedGenerator = new CachedGlobIdGenerator(generator);
      assertEquals(0, cachedGenerator.getNextId(DummyObject.ID, 4));
      int lastRequestedId = CachedGlobIdGenerator.MIN_COUNT + 4;
      assertEquals(lastRequestedId, generator.lastIdByGlobType.get(DummyObject.ID).intValue());
      assertEquals(lastRequestedId, cachedGenerator.getNextId(DummyObject.ID, 7));
      for (int i = lastRequestedId + 7; i < lastRequestedId + 100; i++) {
         assertEquals(i, cachedGenerator.getNextId(DummyObject.ID, 1));
      }
   }

   private static class DummyGlobIdGenerator implements GlobIdGenerator {
      Map<IntegerField, Integer> lastIdByGlobType = new HashMap<IntegerField, Integer>();

      public int getNextId(IntegerField keyField, int idCount) {
         Integer id = lastIdByGlobType.get(keyField);
         if (id == null) {
            lastIdByGlobType.put(keyField, idCount);
            return 0;
         }
         try {
            return id;
         }
         finally {
            lastIdByGlobType.put(keyField, id + idCount);
         }
      }
   }
}
