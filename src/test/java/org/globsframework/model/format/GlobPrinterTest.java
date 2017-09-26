package org.globsframework.model.format;

import org.globsframework.metamodel.DummyObject;
import org.globsframework.metamodel.DummyObject2;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.GlobRepositoryBuilder;
import org.globsframework.utils.Dates;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;

import static org.globsframework.model.FieldValue.value;
import static org.globsframework.utils.Strings.LINE_SEPARATOR;
import static org.junit.Assert.assertEquals;

public class GlobPrinterTest {
   private GlobRepository repository;

   @Before
   public void setUp() throws Exception {
      repository = GlobRepositoryBuilder.createEmpty();
   }

   @Test
   public void testStandardCase() throws Exception {
      createObj2(1, "a");
      createObj2(0, "b");
      createObj2(2, "c");
      checkOutput("===== dummyObject2 ======" + LINE_SEPARATOR +
                  "| id | label | value |" + LINE_SEPARATOR +
                  "| 0  | b     |       |" + LINE_SEPARATOR +
                  "| 1  | a     |       |" + LINE_SEPARATOR +
                  "| 2  | c     |       |" + LINE_SEPARATOR +
                  LINE_SEPARATOR);
   }

   @Test
   public void testTwoTypes() throws Exception {
      createObj2(1, "a");

      createObj(0, "n1", 20060223);
      createObj(1, "n2", 20060221);

      checkOutput("===== dummyObject ======" + LINE_SEPARATOR +
                  "| id | name | value | count | present | date     | password | linkId | link2Id |" + LINE_SEPARATOR +
                  "| 0  | n1   |       |       |         | 20060223 |          |        |         |" + LINE_SEPARATOR +
                  "| 1  | n2   |       |       |         | 20060221 |          |        |         |" + LINE_SEPARATOR +
                  "" + LINE_SEPARATOR +
                  "===== dummyObject2 ======" + LINE_SEPARATOR +
                  "| id | label | value |" + LINE_SEPARATOR +
                  "| 1  | a     |       |" + LINE_SEPARATOR +
                  LINE_SEPARATOR);
   }

   @Test
   public void testFiltering() throws Exception {
      createObj2(1, "a");
      createObj(0, "n1", 20060223);

      checkOutput("===== dummyObject ======" + LINE_SEPARATOR +
                  "| id | name | value | count | present | date     | password | linkId | link2Id |" + LINE_SEPARATOR +
                  "| 0  | n1   |       |       |         | 20060223 |          |        |         |" + LINE_SEPARATOR +
                  "" + LINE_SEPARATOR,
                  DummyObject.TYPE);

      checkOutput("===== dummyObject2 ======" + LINE_SEPARATOR +
                  "| id | label | value |" + LINE_SEPARATOR +
                  "| 1  | a     |       |" + LINE_SEPARATOR +
                  "" + LINE_SEPARATOR,
                  DummyObject2.TYPE);
   }

   @Test
   public void testExcludingColumns() throws Exception {
      createObj2(1, "a");

      String actual =
         GlobPrinter.init(repository)
            .showOnly(DummyObject2.TYPE)
            .exclude(DummyObject2.ID, DummyObject2.VALUE)
            .toString();

      assertEquals("===== dummyObject2 ======" + LINE_SEPARATOR +
                   "| label |" + LINE_SEPARATOR +
                   "| a     |" + LINE_SEPARATOR +
                   "" + LINE_SEPARATOR, actual);
   }

   @Test
   public void testLinkFieldsUseTheTargetName() throws Exception {
      repository.create(DummyObject.TYPE,
                        value(DummyObject.ID, 1),
                        value(DummyObject.NAME, "obj1"),
                        value(DummyObject.LINK_ID, 2));
      repository.create(DummyObject.TYPE,
                        value(DummyObject.ID, 2),
                        value(DummyObject.NAME, "obj2"));

      checkRepositoryOutput("===== dummyObject ======" + LINE_SEPARATOR +
                            "| id | name | value | count | present | date | password | linkId | link2Id |" + LINE_SEPARATOR +
                            "| 1  | obj1 |       |       |         |      |          | 2      |         |" + LINE_SEPARATOR +
                            "| 2  | obj2 |       |       |         |      |          |        |         |" + LINE_SEPARATOR +
                            "" + LINE_SEPARATOR, DummyObject.TYPE);
   }

   @Test
   public void testSingleGlob() throws Exception {
      Glob glob = repository.create(DummyObject.TYPE,
                                    value(DummyObject.ID, 1),
                                    value(DummyObject.NAME, "obj1"),
                                    value(DummyObject.LINK_ID, 2));

      checkOutput(glob,
                  "===== dummyObject[id=1] ======\n" +
                  "| Field    | Value |\n" +
                  "| count    |       |\n" +
                  "| date     |       |\n" +
                  "| id       | 1     |\n" +
                  "| link2Id  |       |\n" +
                  "| linkId   | 2     |\n" +
                  "| name     | obj1  |\n" +
                  "| password |       |\n" +
                  "| present  |       |\n" +
                  "| value    |       |");
   }

   private void checkOutput(Glob glob, String expected) {
      StringWriter writer = new StringWriter();
      GlobPrinter.print(glob, writer);
      assertEquals(expected, writer.getBuffer().toString().trim());
   }

   private void checkOutput(String expected, GlobType... types) {
      checkRepositoryOutput(expected, types);
      assertEquals(expected, GlobPrinter.init(repository.getAll()).showOnly(types).toString());
   }

   private void checkRepositoryOutput(String expected, GlobType... types) {
      assertEquals(expected, GlobPrinter.init(repository).showOnly(types).toString());
   }

   private void createObj(int id, String name, int date) {
      repository.create(DummyObject.TYPE,
                        value(DummyObject.ID, id),
                        value(DummyObject.NAME, name),
                        value(DummyObject.DATE, date));
   }

   private void createObj2(int id, String value) {
      repository.create(DummyObject2.TYPE,
                        value(DummyObject2.ID, id),
                        value(DummyObject2.LABEL, value));
   }
}
