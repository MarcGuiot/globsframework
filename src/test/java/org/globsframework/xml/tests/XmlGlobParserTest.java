package org.globsframework.xml.tests;

import org.globsframework.metamodel.*;
import org.globsframework.metamodel.annotations.KeyField;
import org.globsframework.metamodel.annotations.Target;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.links.Link;
import org.globsframework.metamodel.GlobTypeLoader;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.model.*;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemAmbiguity;
import org.globsframework.utils.exceptions.ItemNotFound;
import org.junit.Before;
import org.junit.Test;

import static org.globsframework.model.KeyBuilder.newKey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class XmlGlobParserTest {
   private GlobRepository repository;


   @Before
   public void setUp() throws Exception {
      repository = GlobRepositoryBuilder.createEmpty();
   }


   @Test
   public void testStandardCase() throws Exception {
      parse("<dummyObject id='1' name='foo'/>");
      GlobList objects = repository.getAll(DummyObject.TYPE);
      assertEquals(1, objects.size());
      Glob object = objects.get(0);
      assertEquals(1, object.get(DummyObject.ID).intValue());
      assertEquals("foo", object.get(DummyObject.NAME));
   }

   @Test
   public void testThrowsMeaningfulExceptionsForInvalidValues() throws Exception {
      try {
         parse("<dummyObject id='thisIsNotAnInt'/>");
         fail();
      }
      catch (InvalidParameter e) {
         assertEquals("'thisIsNotAnInt' is not a proper value for field 'id' in type 'dummyObject'",
                      e.getMessage());
      }
   }

   @Test
   public void testGeneratesIntegerIdsIfNeeded() throws Exception {
      parse("<dummyObject name='foo'/>");
      GlobList objects = repository.getAll(DummyObject.TYPE);
      assertEquals(1, objects.size());
      Glob object = objects.get(0);
      assertEquals(100, object.get(DummyObject.ID).intValue());
      assertEquals("foo", object.get(DummyObject.NAME));
   }

   @Test
   public void testLinkField() throws Exception {
      parse("<dummyObject id='1' name='foo'/>" +
            "<dummyObject id='2' linkId='1'/>");
      assertEquals(getDummyObject(1), repository.findLinkTarget(getDummyObject(2), DummyObject.LINK));
   }

   @Test
   public void testLinkFieldWithTargetName() throws Exception {
      parse("<dummyObject id='1' name='foo'/>" +
            "<dummyObject id='2' linkName='foo'/>");
      Glob obj2 = getDummyObject(2);
      assertEquals(1, obj2.get(DummyObject.LINK_ID).intValue());
      assertEquals(getDummyObject(1), repository.findLinkTarget(obj2, DummyObject.LINK));
   }

   @Test
   public void testIdPartOfLinkFieldTakesPrecedenceOverNamePart() throws Exception {
      parse("<dummyObject id='1' name='foo'/>" +
            "<dummyObject id='2' name='bar'/>" +
            "<dummyObject id='3' linkId='2' linkName='foo'/>");
      assertEquals(getDummyObject(2), repository.findLinkTarget(getDummyObject(3), DummyObject.LINK));
   }

   @Test
   public void testUsingALinkFieldAsAnId() throws Exception {
      parse("<dummyObject id='1' name='foo'/>" +
            "<dummyObjectWithLinkFieldId linkId='1'/>");
      Glob source = repository.get(newKey(DummyObjectWithLinkFieldId.TYPE, 1));
      assertEquals(getDummyObject(1), repository.findLinkTarget(source, DummyObjectWithLinkFieldId.LINK));
   }

   @Test
   public void testUsingANameLinkAsAnId() throws Exception {
      parse("<dummyObject id='1' name='foo'/>" +
            "<dummyObjectWithLinkFieldId linkName='foo'/>");
      Glob source = repository.get(newKey(DummyObjectWithLinkFieldId.TYPE, 1));
      assertEquals(1, source.get(DummyObjectWithLinkFieldId.LINK_ID).intValue());
      assertEquals(getDummyObject(1), repository.findLinkTarget(source, DummyObjectWithLinkFieldId.LINK));
   }

   @Test
   public void testCompositeLink() throws Exception {
      parse("<dummyObjectWithCompositeKey id1='1' id2='2'/>" +
            "<dummyObjectWithLinks id='1' targetId1='1' targetId2='2'/>");
      Glob source = repository.get(newKey(DummyObjectWithLinks.TYPE, 1));
      Glob target = repository.findLinkTarget(source, DummyObjectWithLinks.COMPOSITE_LINK);
      assertEquals(1, target.get(DummyObjectWithCompositeKey.ID1).intValue());
      assertEquals(2, target.get(DummyObjectWithCompositeKey.ID2).intValue());
   }

   @Test
   public void testNamePartOfCompositeLinkTakesPrecedenceOverIdPart() throws Exception {
      parse("<dummyObjectWithCompositeKey id1='1' id2='2' name='foo'/>" +
            "<dummyObjectWithCompositeKey id1='2' id2='3' name='bar'/>" +
            "<dummyObjectWithLinks id='1' compositeLink='bar' targetId1='1' targetId2='2'/>");
      Glob source = repository.get(newKey(DummyObjectWithLinks.TYPE, 1));
      Glob target = repository.findLinkTarget(source, DummyObjectWithLinks.COMPOSITE_LINK);
      assertEquals(1, target.get(DummyObjectWithCompositeKey.ID1).intValue());
      assertEquals(2, target.get(DummyObjectWithCompositeKey.ID2).intValue());
   }

   @Test
   public void testContainmentWithSingleLink() throws Exception {
      parse("<dummyObject id='1'>" +
            "  <dummyObjectWithLinks id='1'/>" +
            "</dummyObject>");

      Glob links = repository.get(newKey(DummyObjectWithLinks.TYPE, 1));
      assertEquals(1, links.get(DummyObjectWithLinks.PARENT_ID).intValue());
   }

   @Test
   public void testContainmentWithCompositeLink() throws Exception {
      parse("<dummyObjectWithCompositeKey id1='1' id2='2'>" +
            "  <dummyObjectWithLinks id='1'/>" +
            "</dummyObjectWithCompositeKey>");

      Glob links = repository.get(newKey(DummyObjectWithLinks.TYPE, 1));
      assertEquals(1, links.get(DummyObjectWithLinks.TARGET_ID_1).intValue());
      assertEquals(2, links.get(DummyObjectWithLinks.TARGET_ID_2).intValue());
   }

   public static class AnObjectLinkingToATypeWithNoNamingField {
      public static GlobType TYPE;

      @KeyField
      public static IntegerField ID;

      public static IntegerField OBJ2_ID;

      public static Link OBJ2;

      static {
         GlobTypeLoader loader = GlobTypeLoaderFactory.create(AnObjectLinkingToATypeWithNoNamingField.class);
         loader.register(MutableGlobLinkModel.LinkRegister.class,
                         mutableGlobLinkModel ->
                            OBJ2 = mutableGlobLinkModel.getLinkBuilder(OBJ2)
                               .add(OBJ2_ID, DummyObject2.ID).publish());
         loader.load();
      }
   }

   @Test
   public void testUsingALinkFieldWithAnObjectThatHasNoNamingField() throws Exception {
      parse(GlobModelBuilder.create(DummyObject2.TYPE, AnObjectLinkingToATypeWithNoNamingField.TYPE).get(),
            "<dummyObject2 id='11'>" +
            "  <anObjectLinkingToATypeWithNoNamingField id='1'/>" +
            "</dummyObject2>");

      Glob source = repository.get(newKey(AnObjectLinkingToATypeWithNoNamingField.TYPE, 1));
      assertEquals(11, source.get(AnObjectLinkingToATypeWithNoNamingField.OBJ2_ID).intValue());
   }

   @Test
   public void testContainmentWithNoRelationshipError() throws Exception {
      try {
         parse("<dummyObject id='1'>" +
               "  <dummyObject2 id='1'/>" +
               "</dummyObject>");
         fail();
      }
      catch (ItemNotFound e) {
         assertEquals("There are no links from dummyObject2 to dummyObject" +
                      " - XML containment cannot be used", e.getMessage());
      }
   }

   @Test
   public void testReadInvalidContent() throws Exception {
      parseIgnoreError("" +
                       "<dummyObject id='1' count='sdf'/>" +
                       "<dummyUnknownObject id='1' COUNT='titi'/>" +
                       "<dummyObject id='2' undefined='toto'/>" +
                       "");

      GlobList objects = repository.getAll(DummyObject.TYPE);
      assertEquals(2, objects.size());
      repository.get(KeyBuilder.newKey(DummyObject.TYPE, 1));
      repository.get(KeyBuilder.newKey(DummyObject.TYPE, 2));
   }

   @Test
   public void testInvalideReadSetDefaultValues() throws Exception {
      parseIgnoreError("<dummyObjectWithDefaultValues id='1' long='lg' double='dbl'/>");

      Glob glob = repository.get(KeyBuilder.newKey(DummyObjectWithDefaultValues.TYPE, 1));
      assertEquals(3.14159265, glob.get(DummyObjectWithDefaultValues.DOUBLE), 0.001);
      assertEquals(5L, glob.get(DummyObjectWithDefaultValues.LONG).longValue());
   }

   public static class AnObject {
      public static GlobType TYPE;

      @KeyField
      public static IntegerField ID;

      static {
         GlobTypeLoaderFactory.createAndLoad(AnObject.class);
      }
   }

   public static class AnObjectWithTwoLinks {
      public static GlobType TYPE;
      @KeyField
      public static IntegerField ID;

      @Target(AnObject.class)
      public static IntegerField LINK1_ID;

      public static Link LINK1;

      @Target(AnObject.class)
      public static IntegerField LINK2_ID;

      public static Link LINK2;

      static {
         GlobTypeLoader loader = GlobTypeLoaderFactory.create(AnObjectWithTwoLinks.class);
         loader.register(MutableGlobLinkModel.LinkRegister.class,
                         mutableGlobLinkModel ->
                         {
                            mutableGlobLinkModel.getDirectLinkBuilder(LINK1)
                               .add(LINK1_ID, AnObject.ID)
                               .publish();
                            mutableGlobLinkModel.getDirectLinkBuilder(LINK2)
                               .add(LINK2_ID, AnObject.ID)
                               .publish();
                         });
         loader.load();
      }
   }

   @Test
   public void testContainmentWithTooManyRelationshipsError() throws Exception {
      try {
         parse(GlobModelBuilder.create(AnObject.TYPE, AnObjectWithTwoLinks.TYPE).get(),
               "<anObject id='1'>" +
               "  <anObjectWithTwoLinks id='1'/>" +
               "</anObject>");
         fail();
      }
      catch (ItemAmbiguity e) {
         assertEquals("More than one Link from anObjectWithTwoLinks to anObject" +
                      " - XML containment cannot be used", e.getMessage());
      }
   }

   private void parse(String xmlStream) {
      parse(DummyModel.get(), xmlStream);
   }

   private void parse(GlobModel globModel, String xmlStream) {
      GlobTestUtils.parse(globModel, repository, xmlStream);
   }

   private void parseIgnoreError(String xmlStream) {
      parseIgnoreError(DummyModel.get(), xmlStream);
   }

   private void parseIgnoreError(GlobModel globModel, String xmlStream) {
      GlobTestUtils.parseIgnoreError(globModel, repository, xmlStream);
   }

   private Glob getDummyObject(int id) {
      return repository.get(newKey(DummyObject.TYPE, id));
   }
}
