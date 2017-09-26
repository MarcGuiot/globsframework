package org.globsframework.utils;

import org.globsframework.utils.collections.Range;
import org.junit.Test;

import static org.junit.Assert.*;

public class RangeTest {

   @Test
   public void testGetters() throws Exception {
      Range<Integer> both = new Range<Integer>(5, 9);
      assertEquals(5, both.getMin().intValue());
      assertEquals(9, both.getMax().intValue());
   }

   @Test
   public void testContainsElement() throws Exception {
      Range<Integer> both = new Range<Integer>(5, 9);
      assertFalse(both.contains(4));
      assertTrue(both.contains(5));
      assertTrue(both.contains(7));
      assertTrue(both.contains(9));
      assertFalse(both.contains(10));

      Range<Integer> upper = new Range<Integer>(null, 9);
      assertTrue(upper.contains(0));
      assertTrue(upper.contains(9));
      assertFalse(upper.contains(10));

      Range<Integer> lower = new Range<Integer>(5, null);
      assertFalse(lower.contains(0));
      assertTrue(lower.contains(5));
      assertTrue(lower.contains(10));

      Range<Integer> all = new Range<Integer>(null, null);
      assertTrue(all.contains(0));
      assertTrue(all.contains(9));
   }

   @Test
   public void testOverlaps() throws Exception {
      Range<Integer> both = new Range<Integer>(5, 9);
      assertTrue(both.overlaps(new Range<Integer>(null, null)));
      assertTrue(both.overlaps(new Range<Integer>(8, null)));
      assertTrue(both.overlaps(new Range<Integer>(null, 9)));
      assertTrue(both.overlaps(new Range<Integer>(null, 10)));
      assertFalse(both.overlaps(new Range<Integer>(null, 4)));
      assertFalse(both.overlaps(new Range<Integer>(10, null)));

      Range<Integer> upper = new Range<Integer>(null, 9);
      assertTrue(upper.overlaps(new Range<Integer>(null, null)));
      assertTrue(upper.overlaps(new Range<Integer>(8, null)));
      assertTrue(upper.overlaps(new Range<Integer>(9, null)));
      assertFalse(upper.overlaps(new Range<Integer>(10, null)));

      Range<Integer> lower = new Range<Integer>(5, null);
      assertTrue(lower.overlaps(new Range<Integer>(null, null)));
      assertTrue(lower.overlaps(new Range<Integer>(8, null)));
      assertTrue(lower.overlaps(new Range<Integer>(9, null)));
      assertFalse(lower.overlaps(new Range<Integer>(null, 4)));

      Range<Integer> all = new Range<Integer>(null, null);
      assertTrue(all.overlaps(new Range<Integer>(null, null)));
      assertTrue(all.overlaps(new Range<Integer>(8, null)));
      assertTrue(all.overlaps(new Range<Integer>(null, 9)));
      assertTrue(all.overlaps(new Range<Integer>(8, 9)));
   }

   @Test
   public void testContainsRange() throws Exception {
      Range<Integer> both = new Range<Integer>(5, 9);
      assertTrue(both.contains(new Range<Integer>(5, 9)));
      assertTrue(both.contains(new Range<Integer>(6, 8)));
      assertFalse(both.contains(new Range<Integer>(null, null)));
      assertFalse(both.contains(new Range<Integer>(4, null)));
      assertFalse(both.contains(new Range<Integer>(10, null)));
      assertFalse(both.contains(new Range<Integer>(null, 4)));
      assertFalse(both.contains(new Range<Integer>(null, 9)));

      Range<Integer> upper = new Range<Integer>(null, 9);
      assertTrue(upper.contains(new Range<Integer>(5, 9)));
      assertTrue(upper.contains(new Range<Integer>(null, 9)));
      assertTrue(upper.contains(new Range<Integer>(null, 8)));
      assertFalse(upper.contains(new Range<Integer>(8, null)));
      assertFalse(upper.contains(new Range<Integer>(9, null)));
      assertFalse(upper.contains(new Range<Integer>(10, null)));
      assertFalse(upper.contains(new Range<Integer>(null, null)));

      Range<Integer> lower = new Range<Integer>(5, null);
      assertTrue(lower.contains(new Range<Integer>(5, 9)));
      assertTrue(lower.contains(new Range<Integer>(5, null)));
      assertTrue(lower.contains(new Range<Integer>(6, null)));
      assertFalse(lower.contains(new Range<Integer>(null, 9)));
      assertFalse(lower.contains(new Range<Integer>(null, 4)));
      assertFalse(lower.contains(new Range<Integer>(null, null)));

      Range<Integer> all = new Range<Integer>(null, null);
      assertTrue(all.contains(new Range<Integer>(null, null)));
      assertTrue(all.contains(new Range<Integer>(8, null)));
      assertTrue(all.contains(new Range<Integer>(null, 9)));
      assertTrue(all.contains(new Range<Integer>(8, 9)));
   }

   @Test
   public void testAfter() throws Exception {
      Range<Integer> both = new Range<Integer>(5, 9);
      assertTrue(both.after(4));
      assertFalse(both.after(10));
      assertFalse(both.after(5));
      assertFalse(both.after(9));

      Range<Integer> upper = new Range<Integer>(null, 9);
      assertFalse(upper.after(5));
      assertFalse(upper.after(9));
      assertFalse(upper.after(10));

      Range<Integer> lower = new Range<Integer>(5, null);
      assertTrue(lower.after(4));
      assertFalse(lower.after(5));
      assertFalse(lower.after(10));

      Range<Integer> all = new Range<Integer>(null, null);
      assertFalse(all.after(0));
      assertFalse(all.after(10));
   }

   @Test
   public void testBefore() throws Exception {
      Range<Integer> both = new Range<Integer>(5, 9);
      assertTrue(both.before(10));
      assertFalse(both.before(7));
      assertFalse(both.before(5));
      assertFalse(both.before(4));

      Range<Integer> upper = new Range<Integer>(null, 9);
      assertTrue(both.before(10));
      assertFalse(upper.before(5));
      assertFalse(upper.before(9));
      assertFalse(upper.before(4));

      Range<Integer> lower = new Range<Integer>(5, null);
      assertFalse(lower.before(4));
      assertFalse(lower.before(5));
      assertFalse(lower.before(10));

      Range<Integer> all = new Range<Integer>(null, null);
      assertFalse(all.before(0));
      assertFalse(all.before(10));
   }

}
