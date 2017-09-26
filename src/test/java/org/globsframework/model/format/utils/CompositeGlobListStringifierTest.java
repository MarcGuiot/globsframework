package org.globsframework.model.format.utils;

import org.globsframework.metamodel.DummyObject;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.repository.DefaultGlobRepository;
import org.junit.Before;
import org.junit.Test;

import static org.globsframework.model.FieldValue.value;
import static org.globsframework.model.format.GlobListStringifiers.fieldValue;
import static org.globsframework.model.utils.GlobListMatchers.contains;
import static org.junit.Assert.assertEquals;

public class CompositeGlobListStringifierTest {
   private GlobRepository repository;
   private Glob glob1;
   private Glob glob2;

   @Before
   public void setUp() throws Exception {
      repository = new DefaultGlobRepository();
      glob1 = repository.create(DummyObject.TYPE, value(DummyObject.ID, 1));
      glob2 = repository.create(DummyObject.TYPE, value(DummyObject.ID, 2));
   }

   @Test
   public void test() throws Exception {

      CompositeGlobListStringifier stringifier = new CompositeGlobListStringifier(" - ");
      stringifier.add(fieldValue(DummyObject.ID, "<>", "<...>"));
      stringifier.add(contains(glob1), fieldValue(DummyObject.ID, "", "<.1.>"));
      stringifier.add(contains(glob2), fieldValue(DummyObject.ID, "", "<.2.>"));

      check(stringifier, "<>");
      check(stringifier, "1 - 1", glob1);
      check(stringifier, "2 - 2", glob2);
      check(stringifier, "<...> - <.1.> - <.2.>", glob1, glob2);
   }

   private void check(CompositeGlobListStringifier stringifier, String expected, Glob... globs) {
      String text = stringifier.toString(new GlobList(globs), repository);
      assertEquals(expected, text);
   }
}
