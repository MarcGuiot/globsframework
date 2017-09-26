package org.globsframework.model.format.utils;

import org.globsframework.metamodel.DummyObject;
import org.globsframework.metamodel.DummyObjectWithLinks;
import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.links.Link;
import org.globsframework.model.*;
import org.globsframework.model.format.Formats;
import org.globsframework.model.format.GlobListStringifier;
import org.globsframework.model.format.GlobStringifier;
import org.junit.Before;
import org.junit.Test;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import static org.globsframework.metamodel.DummyObject.*;
import static org.globsframework.model.FieldValue.value;
import static org.globsframework.model.KeyBuilder.newKey;
import static org.junit.Assert.assertEquals;

public class DefaultDescriptionServiceTest {

   private DefaultDescriptionService descriptionService;
   private GlobRepository repository;
   private Glob emptyObject;
   private Glob dummyObject;

   @Before
   public void setUp() throws Exception {
      descriptionService = new DefaultDescriptionService();
      GlobChecker checker = new GlobChecker();
      repository = checker.parse("<dummyObject id='0'/>" +
                                 "<dummyObject id='1' name='name1' value='1.23456' date='20061224'" +
                                 "             present='true' linkId='2'/>" +
                                 "<dummyObject id='2' name='name2'/>");
      emptyObject = repository.get(newKey(TYPE, 0));
      dummyObject = repository.get(newKey(TYPE, 1));
   }

   @Test
   public void testFieldStringifier() throws Exception {
      checkToString(NAME, "name1");
      checkToString(VALUE, "1.23");
      checkToString(DATE, "20061224");
      checkToString(PRESENT, Formats.DEFAULT_YES_VALUE);
      checkToString(LINK, "name2");
   }

   @Test
   public void testLinkStringifier() throws Exception {
      checkToString(LINK, "name2");
   }

   @Test
   public void testGlobTypeStringifier() throws Exception {
      GlobStringifier stringifier = descriptionService.getStringifier(TYPE);
      assertEquals("", stringifier.toString(null, repository));
      assertEquals("", stringifier.toString(emptyObject, repository));
      assertEquals("name1", stringifier.toString(dummyObject, repository));
   }

   @Test
   public void testGlobTypeListStringifier() throws Exception {
      GlobListStringifier stringifier = descriptionService.getListStringifier(TYPE);
      GlobList list = new GlobList(emptyObject, dummyObject);
      checkListStringification(stringifier, list);
   }

   @Test
   public void testFieldListStringfier() throws Exception {
      GlobListStringifier stringifier = descriptionService.getListStringifier(DummyObject.NAME);
      GlobList list = new GlobList(emptyObject, dummyObject);
      checkListStringification(stringifier, list);
   }

   @Test
   public void testLinkListStringifier() throws Exception {
      GlobListStringifier stringifier = descriptionService.getListStringifier(DummyObjectWithLinks.PARENT_LINK);
      Glob source1 = repository.create(newKey(DummyObjectWithLinks.TYPE, 1),
                                       value(DummyObjectWithLinks.PARENT_ID, emptyObject.get(DummyObject.ID)));
      Glob source2 = repository.create(newKey(DummyObjectWithLinks.TYPE, 2),
                                       value(DummyObjectWithLinks.PARENT_ID, dummyObject.get(DummyObject.ID)));
      GlobList list = new GlobList(source1, source2);
      checkListStringification(stringifier, list);
   }

   private void checkListStringification(GlobListStringifier stringifier, GlobList list) {
      assertEquals("", stringifier.toString(GlobList.EMPTY, repository));

      repository.update(emptyObject.getKey(), DummyObject.NAME, "name");
      repository.update(dummyObject.getKey(), DummyObject.NAME, "name");
      assertEquals("name", stringifier.toString(list, repository));

      repository.update(emptyObject.getKey(), DummyObject.NAME, "anotherName");
      assertEquals("...", stringifier.toString(list, repository));
   }

   @Test
   public void testResourceBundle() throws Exception {
      ResourceBundle bundle = new ListResourceBundle() {
         protected Object[][] getContents() {
            return new Object[][]{
               {"dummyObject", "Dummy Object"},
               {"dummyObject.name", "Name of the dummy object"},
               {"dummyObject.link", "Link to a dummy object"},
            };
         }
      };
      descriptionService = new DefaultDescriptionService(Formats.DEFAULT, bundle);
      assertEquals("Dummy Object", descriptionService.getLabel(DummyObject.TYPE));
      assertEquals("Name of the dummy object", descriptionService.getLabel(DummyObject.NAME));
      assertEquals("Link to a dummy object", descriptionService.getLabel(DummyObject.LINK));

      assertEquals("value", descriptionService.getLabel(DummyObject.VALUE));
   }

   private void checkToString(Field field, String result) {
      GlobStringifier stringifier = descriptionService.getStringifier(field);
      assertEquals("", stringifier.toString(null, repository));
      assertEquals("", stringifier.toString(emptyObject, repository));
      assertEquals(result, stringifier.toString(dummyObject, repository));
   }

   private void checkToString(Link link, String result) {
      GlobStringifier stringifier = descriptionService.getStringifier(link);
      assertEquals("", stringifier.toString(null, repository));
      assertEquals("", stringifier.toString(emptyObject, repository));
      assertEquals(result, stringifier.toString(dummyObject, repository));
   }
}
