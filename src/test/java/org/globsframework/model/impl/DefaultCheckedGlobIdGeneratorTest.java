package org.globsframework.model.impl;

import org.globsframework.metamodel.DummyObject;
import org.globsframework.metamodel.DummyObject2;
import org.globsframework.model.GlobChecker;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.GlobRepositoryBuilder;
import org.globsframework.model.repository.DefaultCheckedGlobIdGenerator;
import org.globsframework.model.utils.GlobBuilder;
import org.junit.Test;

import static org.globsframework.model.FieldValue.value;
import static org.junit.Assert.assertEquals;

public class DefaultCheckedGlobIdGeneratorTest {
   private GlobChecker checker = new GlobChecker();
   private GlobRepository repository;

   @Test
   public void testStandardCase() throws Exception {
      initRepository();
      createWithNoId("obj0");
      createWithNoId("obj1");

      repository.create(DummyObject2.TYPE, value(DummyObject2.LABEL, "other"));

      checker.assertEquals(repository,
                           "<dummyObject id='0' name='obj0'/>" +
                           "<dummyObject id='1' name='obj1'/>" +
                           "<dummyObject2 id='0' label='other'/>");
   }

   @Test
   public void testInitWithExistingGlobs() throws Exception {
      DefaultCheckedGlobIdGenerator generator = new DefaultCheckedGlobIdGenerator();
      repository = GlobRepositoryBuilder.init(generator)
         .add(GlobBuilder.init(DummyObject.TYPE).set(DummyObject.ID, 12)
                 .set(DummyObject.NAME, "obj12").get())
         .add(GlobBuilder.init(DummyObject.TYPE).set(DummyObject.ID, 14)
                 .set(DummyObject.NAME, "obj14").get())
         .get();
      generator.setRepository(repository);

      createWithNoId("obj15");

      checker.assertEquals(repository,
                           "<dummyObject id='12' name='obj12'/>" +
                           "<dummyObject id='14' name='obj14'/>" +
                           "<dummyObject id='15' name='obj15'/>");
   }

   @Test
   public void testMixingHardcodedAndGeneratedIds() throws Exception {
      initRepository();
      createWithNoId("obj0");

      create(1, "obj1");
      create(2, "obj2");

      createWithNoId("obj3");

      checker.assertEquals(repository,
                           "<dummyObject id='0' name='obj0'/>" +
                           "<dummyObject id='1' name='obj1'/>" +
                           "<dummyObject id='2' name='obj2'/>" +
                           "<dummyObject id='3' name='obj3'/>");
   }

   @Test
   public void testTakesTheFirstAvailableId() throws Exception {
      initRepository();

      createWithNoId("obj0");

      create(1, "obj1");
      create(4, "obj4");

      createWithNoId("obj2");

      checker.assertEquals(repository,
                           "<dummyObject id='0' name='obj0'/>" +
                           "<dummyObject id='1' name='obj1'/>" +
                           "<dummyObject id='2' name='obj2'/>" +
                           "<dummyObject id='4' name='obj4'/>");
   }

   @Test
   public void testChecksForAllSteps() throws Exception {
      DefaultCheckedGlobIdGenerator generator = initRepository();

      create(0, "obj1");
      create(4, "obj4");

      assertEquals(5, generator.getNextId(DummyObject.ID, 10));
   }

   private DefaultCheckedGlobIdGenerator initRepository() {
      DefaultCheckedGlobIdGenerator generator = new DefaultCheckedGlobIdGenerator();
      repository = GlobRepositoryBuilder.init(generator).get();
      generator.setRepository(repository);
      return generator;
   }

   private void create(int id, String name) {
      repository.create(DummyObject.TYPE,
                        value(DummyObject.ID, id),
                        value(DummyObject.NAME, name));
   }

   private void createWithNoId(String name) {
      repository.create(DummyObject.TYPE, value(DummyObject.NAME, name));
   }
}
