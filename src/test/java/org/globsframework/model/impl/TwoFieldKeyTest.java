package org.globsframework.model.impl;

import org.globsframework.metamodel.DummyObjectWithCompositeKey;
import org.globsframework.model.Key;
import org.globsframework.model.KeyBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TwoFieldKeyTest {

   private TwoFieldKey k1a = new TwoFieldKey(DummyObjectWithCompositeKey.ID1, 1,
                                             DummyObjectWithCompositeKey.ID2, 2);
   private TwoFieldKey k1b = new TwoFieldKey(DummyObjectWithCompositeKey.ID2, 2,
                                             DummyObjectWithCompositeKey.ID1, 1);
   private TwoFieldKey k2 = new TwoFieldKey(DummyObjectWithCompositeKey.ID1, 2,
                                            DummyObjectWithCompositeKey.ID2, 1);

   @Test
   public void test() throws Exception {
      assertEquals(k1a, k1b);
      assertFalse(k1a.equals(k2));
      assertFalse(k2.equals(k1a));
      assertFalse(k1b.equals(k2));

      assertTrue(k1a.hashCode() == k1b.hashCode());
      assertTrue(k1a.hashCode() != k2.hashCode());
      assertTrue(k1b.hashCode() != k2.hashCode());
   }

   @Test
   public void testComparingWithAnotherType() throws Exception {
      Key other = KeyBuilder.init(DummyObjectWithCompositeKey.TYPE)
         .set(DummyObjectWithCompositeKey.ID1, 1)
         .set(DummyObjectWithCompositeKey.ID2, 2)
         .get();

      assertEquals(k1a, other);
      assertEquals(other, k1a);
      assertFalse(other.equals(k2));
      assertFalse(k2.equals(other));

      Map<Key, String> map = new HashMap<Key, String>();
      map.put(k1a, "k1");
      map.put(other, "other");
      map.put(k2, "k2");

      assertEquals("other", map.get(k1a));
      assertEquals("other", map.get(other));
      assertEquals("k2", map.get(k2));
   }
}
