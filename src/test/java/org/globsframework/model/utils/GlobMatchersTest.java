package org.globsframework.model.utils;

import org.globsframework.metamodel.DummyObject;
import org.globsframework.metamodel.DummyObjectWithCompositeKey;
import org.globsframework.metamodel.DummyObjectWithLinks;
import org.globsframework.model.*;
import org.globsframework.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.globsframework.model.utils.GlobMatchers.*;

public class GlobMatchersTest {
   protected Glob a;
   protected Glob b;
   protected Glob c;
   protected Glob d;
   protected Glob unknown;
   protected GlobRepository repository;
   protected GlobList list;

   @Before
   public void setUp() throws Exception {
      GlobChecker checker = new GlobChecker();
      repository = checker.parse(
         "<dummyObject id='0' name='obj_a' value='1' present='true'/>" +
         "<dummyObject id='1' name='obj_b' value='1' present='false'/>" +
         "<dummyObject id='2' name='obj_c' value='2' present='true'/>" +
         "<dummyObject id='3' name='obj_d' value='2'/>"
      );

      a = repository.get(KeyBuilder.newKey(DummyObject.TYPE, 0));
      b = repository.get(KeyBuilder.newKey(DummyObject.TYPE, 1));
      c = repository.get(KeyBuilder.newKey(DummyObject.TYPE, 2));
      d = repository.get(KeyBuilder.newKey(DummyObject.TYPE, 3));

      list = new GlobList(a, b, c, d);
   }

   @Test
   public void testFieldEquals() throws Exception {
      check(fieldEquals(DummyObject.NAME, "obj_a"), a);
      check(fieldEqualsIgnoreCase(DummyObject.NAME, "OBJ_a"), a);
      check(fieldEquals(DummyObject.PRESENT, true), a, c);
      check(isTrue(DummyObject.PRESENT), a, c);
   }

   @Test
   public void testAnd() throws Exception {
      check(and(fieldEquals(DummyObject.NAME, "obj_b"), fieldEquals(DummyObject.ID, 1)),
            b);

      check(and(fieldEquals(DummyObject.NAME, "obj_b"), fieldEquals(DummyObject.ID, 1), GlobMatchers.ALL),
            b);

      check(and(fieldEquals(DummyObject.NAME, "obj_b"), fieldEquals(DummyObject.ID, 1), GlobMatchers.NONE));
   }

   @Test
   public void testOr() throws Exception {
      check(or(fieldEquals(DummyObject.NAME, "obj_a"), fieldEquals(DummyObject.NAME, "obj_c")),
            a, c);

      check(or(fieldEquals(DummyObject.NAME, "obj_a"), GlobMatchers.ALL),
            a, b, c, d);

      check(or(fieldEquals(DummyObject.NAME, "obj_a"), GlobMatchers.NONE),
            a);
   }

   @Test
   public void testNot() throws Exception {
      GlobMatcher matcher = not(fieldEquals(DummyObject.NAME, "obj_c"));
      check(matcher, a, b, d);
   }

   @Test
   public void testNull() throws Exception {
      check(isNull(DummyObject.PRESENT), d);
      check(isNotNull(DummyObject.PRESENT), a, b, c);
   }

   @Test
   public void testFieldIn() throws Exception {
      check(fieldIn(DummyObject.ID, new Integer[0]));
      check(fieldIn(DummyObject.ID, 0, 2), a, c);
   }

   private void check(GlobMatcher matcher, Glob... result) {
      GlobList actual = list.filter(matcher, repository);
      TestUtils.assertEquals(actual, result);
   }

   @Test
   public void testGreaterOrEqual() throws Exception {
      check(GlobMatchers.fieldGreaterOrEqual(DummyObject.ID, 2), c, d);
   }

   @Test
   public void testStricklyLesser() throws Exception {
      check(GlobMatchers.fieldStrictlyLessThan(DummyObject.ID, 2), a, b);
   }

   @Test
   public void testLink() throws Exception {
      GlobChecker checker = new GlobChecker();
      repository = checker.parse(
         "<dummyObjectWithLinks id='0' targetId1='1' targetId2='2'/>" +
         "<dummyObjectWithLinks id='1' targetId1='1' targetId2='1'/>" +
         "<dummyObjectWithLinks id='2' targetId1='1' targetId2='2'/>" +
         "<dummyObjectWithCompositeKey id1='1' id2='1'/>" +
         "<dummyObjectWithCompositeKey id1='1' id2='2'/>" +
         ""
      );
      Glob a = repository.get(KeyBuilder.newKey(DummyObjectWithLinks.TYPE, 0));
      Glob b = repository.get(KeyBuilder.newKey(DummyObjectWithLinks.TYPE, 1));
      Glob c = repository.get(KeyBuilder.newKey(DummyObjectWithLinks.TYPE, 2));
      list.clear();
      list.add(a);
      list.add(b);
      list.add(c);
      check(GlobMatchers.linkedTo(repository.get(KeyBuilder.newKey(DummyObjectWithCompositeKey.ID1, 1, DummyObjectWithCompositeKey.ID2, 2)),
                                  DummyObjectWithLinks.COMPOSITE_LINK), a, c);
   }

   @Test
   public void testLinkField() throws Exception {
      GlobChecker checker = new GlobChecker();
      repository = checker.parse(
         "<dummyObject id='0' linkId='0'/>" +
         "<dummyObject id='1' linkId='0'/>" +
         "<dummyObject id='2' linkId='0'/>" +
         "<dummyObject id='3' linkId='2'/>" +
         "<dummyObject id='4' linkId='2'/>" +
         ""
      );
      a = repository.get(KeyBuilder.newKey(DummyObject.TYPE, 0));
      b = repository.get(KeyBuilder.newKey(DummyObject.TYPE, 1));
      c = repository.get(KeyBuilder.newKey(DummyObject.TYPE, 2));
      d = repository.get(KeyBuilder.newKey(DummyObject.TYPE, 3));

      Glob e = repository.get(KeyBuilder.newKey(DummyObject.TYPE, 4));
      list = new GlobList(a, b, c, d, e);
      check(GlobMatchers.linkedTo(a, DummyObject.LINK), a, b, c);
      check(GlobMatchers.linkedTo(c, DummyObject.LINK), d, e);
   }

}
