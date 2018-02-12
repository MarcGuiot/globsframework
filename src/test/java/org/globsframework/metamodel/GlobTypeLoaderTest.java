package org.globsframework.metamodel;

import org.globsframework.metamodel.annotations.*;
import org.globsframework.metamodel.fields.*;
import org.globsframework.metamodel.index.UniqueIndex;
import org.globsframework.metamodel.links.Link;
import org.globsframework.metamodel.links.impl.UnInitializedLink;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.model.utils.GlobBuilder;
import org.globsframework.utils.ArrayTestUtils;
import org.globsframework.utils.TestUtils;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemAlreadyExists;
import org.globsframework.utils.exceptions.MissingInfo;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.Assert.*;

public class GlobTypeLoaderTest {

    static GlobModel globModel =
        GlobModelBuilder.create(AnObjectWithALinkField.TYPE, AnObjectWithASingleIntegerFieldUsedAsALink.TYPE,
                                AnObjectWithRequiredLinks.TYPE, AnObjectWithRequiredLinkField.TYPE).get();

    public static class AnObject {

        public static GlobType TYPE;

        @KeyField
        public static IntegerField ID;

        public static StringField STRING;
        public static DoubleField DOUBLE;
        public static BooleanField BOOLEAN;
        public static LongField LONG;
        public static BlobField BLOB;
        public static BigDecimalField A;
        public static BigDecimalArrayField B;
        public static DoubleArrayField C;
        public static IntegerArrayField D;
        public static LongArrayField E;
        public static BooleanArrayField F;
        public static StringArrayField G;

        public static UniqueIndex ID_INDEX;

        static {
            GlobTypeLoaderFactory.create(AnObject.class).load()
                .defineUniqueIndex(ID_INDEX, ID);
        }

        private static Glob glob =
            GlobBuilder.init(TYPE)
                .set(ID, 1)
                .set(STRING, "string1")
                .set(DOUBLE, 1.1)
                .set(BOOLEAN, false)
                .set(LONG, 15L)
                .set(BLOB, TestUtils.SAMPLE_BYTE_ARRAY)
                .set(A, BigDecimal.valueOf(3.3))
                .set(B, new BigDecimal[]{BigDecimal.ONE, BigDecimal.ZERO})
                .set(C, new double[]{2.2, 3.3})
                .set(D, new int[]{2, 3})
                .set(E, new long[]{3l, 5l})
                .set(F, new boolean[]{false, true})
                .set(G, new String[]{"one", "un"})
                .get();
    }

    @Test
    public void testDefaultCase() throws Exception {
        assertEquals("anObject", AnObject.TYPE.getName());
        assertEquals(1, AnObject.glob.get(AnObject.ID).intValue());
        assertEquals("string1", AnObject.glob.get(AnObject.STRING));
        assertEquals(1.1, AnObject.glob.get(AnObject.DOUBLE), 0.00001);
        assertEquals(Boolean.FALSE, AnObject.glob.get(AnObject.BOOLEAN));
        assertFalse(AnObject.glob.isTrue(AnObject.BOOLEAN));
        assertEquals(new Long(15), AnObject.glob.get(AnObject.LONG));
        assertEquals(TestUtils.SAMPLE_BYTE_ARRAY, AnObject.glob.get(AnObject.BLOB));

        assertTrue(AnObject.A.valueEqual(AnObject.glob.get(AnObject.A), BigDecimal.valueOf(3.3)));
        assertTrue(AnObject.B.valueEqual(AnObject.glob.get(AnObject.B), new BigDecimal[]{BigDecimal.ONE, BigDecimal.ZERO}));
        assertTrue(AnObject.C.valueEqual(AnObject.glob.get(AnObject.C), new double[]{2.2, 3.3}));
        assertTrue(AnObject.D.valueEqual(AnObject.glob.get(AnObject.D), new int[]{2, 3}));
        assertTrue(AnObject.E.valueEqual(AnObject.glob.get(AnObject.E), new long[]{3l, 5l}));
        assertTrue(AnObject.F.valueEqual(AnObject.glob.get(AnObject.F), new boolean[]{false, true}));
        assertTrue(AnObject.G.valueEqual(AnObject.glob.get(AnObject.G), new String[]{"one", "un"}));

        assertFalse(AnObject.A.valueEqual(AnObject.glob.get(AnObject.A), BigDecimal.valueOf(3.4)));
        assertFalse(AnObject.B.valueEqual(AnObject.glob.get(AnObject.B), new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE}));
        assertFalse(AnObject.C.valueEqual(AnObject.glob.get(AnObject.C), new double[]{2.2, 4.3}));
        assertFalse(AnObject.D.valueEqual(AnObject.glob.get(AnObject.D), new int[]{3, 2}));
        assertFalse(AnObject.E.valueEqual(AnObject.glob.get(AnObject.E), new long[]{2l, 5l}));
        assertFalse(AnObject.F.valueEqual(AnObject.glob.get(AnObject.F), new boolean[]{true, true}));
        assertFalse(AnObject.G.valueEqual(AnObject.glob.get(AnObject.G), new String[]{"two", "un"}));

        assertEquals(0, AnObject.ID.getIndex());
        assertEquals(3, AnObject.BOOLEAN.getIndex());
        assertEquals(5, AnObject.BLOB.getIndex());

        assertTrue(AnObject.ID.isKeyField());
        assertFalse(AnObject.STRING.isKeyField());
        assertFalse(AnObject.BOOLEAN.isKeyField());

        Link[] expecteLinks = {AnObjectWithASingleIntegerFieldUsedAsALink.LINK, AnObjectWithALinkField.LINK};
        TestUtils.assertSetEquals(globModel.getLinkModel().getInboundLinks(AnObject.TYPE), expecteLinks);
    }

    private static class AnObjectWithNoTypeDef {
        public static IntegerField ID;
    }

    @Test
    public void testObjectWithNoTypeDef() throws Exception {
        try {
            GlobTypeLoaderFactory.create(AnObjectWithNoTypeDef.class).load();
            fail();
        }
        catch (MissingInfo e) {
            assertEquals("Class " + AnObjectWithNoTypeDef.class.getName() +
                         " must have a TYPE field of class " + GlobType.class.getName(), e.getMessage());
        }
    }

    public static class AnObjectWithSeveralTypeDefs {
        public static GlobType TYPE1;
        public static GlobType TYPE2;
        public static IntegerField ID;
    }

    @Test
    public void testObjectWithSeveralTypeDefs() throws Exception {
        try {
            GlobTypeLoaderFactory.create(AnObjectWithSeveralTypeDefs.class).load();
            fail();
        }
        catch (ItemAlreadyExists e) {
            assertEquals("Class " + AnObjectWithSeveralTypeDefs.class.getName() +
                         " must have only one TYPE field of class " + GlobType.class.getName(), e.getMessage());
        }
    }

    public static class AnObjectForDoubleInit {
        public static GlobType TYPE;
        @KeyField
        public static IntegerField ID;
    }

    @Test
    public void testCannotInitializeTheSameClassTwice() throws Exception {
        GlobTypeLoaderFactory.create(AnObjectForDoubleInit.class).load();
        try {
            GlobTypeLoaderFactory.createAndLoad(AnObjectForDoubleInit.class);
            fail();
        }
        catch (UnexpectedApplicationState e) {
        }
    }

    public static class AnObjectWithNoKey {
        public static GlobType TYPE;
        public static IntegerField ID;
    }

    @Ignore
    @Test
    public void testObjectWithNoKey() throws Exception {
        try {
            GlobTypeLoaderFactory.createAndLoad(AnObjectWithNoKey.class);
            fail();
        }
        catch (InvalidParameter e) {
            assertEquals("GlobType anObjectWithNoKey has no key field", e.getMessage());
        }
    }

    public static class AnObjectWithACompositeKey {
        public static GlobType TYPE;
        @KeyField
        public static IntegerField ID1;
        @KeyField
        public static IntegerField ID2;

        static {
            GlobTypeLoaderFactory.createAndLoad(AnObjectWithACompositeKey.class);
        }
    }

    @Test
    public void testAnObjectWithACompositeKey() throws Exception {
        TestUtils.assertSetEquals(AnObjectWithACompositeKey.TYPE.getKeyFields(),
                                  AnObjectWithACompositeKey.ID1, AnObjectWithACompositeKey.ID2);
    }

    public static class AnObjectWithALinkField {
        public static GlobType TYPE;

        @KeyField
        public static IntegerField ID;

        public static IntegerField LINK_ID;

        public static Link LINK;

        static {
            GlobTypeLoader loader = GlobTypeLoaderFactory.create(AnObjectWithALinkField.class);
            loader.register(MutableGlobLinkModel.LinkRegister.class, mutableGlobLinkModel ->
                LINK = mutableGlobLinkModel.getDirectLinkBuilder(LINK)
                    .add(AnObjectWithALinkField.LINK_ID, AnObject.ID)
                    .publish());
            loader.load();
        }
    }

    @Test
    public void testLinkField() throws Exception {
        GlobType type = AnObjectWithALinkField.TYPE;

        assertEquals(AnObjectWithALinkField.LINK, globModel.getLinkModel().getLink(type, "link"));

        Link link = AnObjectWithALinkField.LINK;
        assertNotNull(link);
        assertFalse(link instanceof UnInitializedLink);
        assertEquals(type, link.getSourceType());
        assertEquals(AnObject.TYPE, link.getTargetType());

        assertEquals(AnObject.TYPE, AnObjectWithALinkField.LINK.getTargetType());
        assertEquals(type, AnObjectWithALinkField.LINK.getSourceType());
    }

    public static class AnObjectWithALinkFieldWithoutTheTargetAnnotation {
        public static GlobType TYPE;
        @KeyField
        public static IntegerField ID;

        public static IntegerField LINK_ID;

        public static Link link;
    }

    @Test
    public void testAnObjectWithALinkFieldWithoutTheTargetAnnotation() throws Exception {
        GlobTypeLoaderFactory.createAndLoad(AnObjectWithALinkFieldWithoutTheTargetAnnotation.class);
        try {
            AnObjectWithALinkFieldWithoutTheTargetAnnotation.link.getSourceType();
            fail();
        }
        catch (UnInitializedLink.LinkNotInitialized e) {
            assertEquals("link anObjectWithALinkFieldWithoutTheTargetAnnotation:link not initialized. (missing code is something like : loader.register(MutableGlobLinkModel.LinkRegister.class,\n" +
                         "                      (linkModel) -> LINK = linkModel.getLinkBuilder(\"ModelName\", \"linkName\").add(sourceFieldId, targetFieldId).publish()); )",
                         e.getMessage());
        }
    }

    public static class AnObjectWithALinkFieldTargettingAMultiKeyObject {
        public static GlobType TYPE;
        @KeyField
        public static IntegerField ID;

        @Target(AnObjectWithACompositeKey.class)
        public static Link LINK;
    }

    @Test
    public void testAnObjectWithALinkFieldTargettingAMultiKeyObject() throws Exception {
        try {
            GlobTypeLoader loader = GlobTypeLoaderFactory.create(AnObjectWithALinkFieldTargettingAMultiKeyObject.class);
            loader.register(MutableGlobLinkModel.LinkRegister.class, mutableGlobLinkModel ->
                AnObjectWithALinkFieldTargettingAMultiKeyObject.LINK =
                    mutableGlobLinkModel.getDirectLinkBuilder(AnObjectWithALinkFieldTargettingAMultiKeyObject.LINK)
                        .add(AnObjectWithALinkFieldTargettingAMultiKeyObject.ID, AnObjectWithACompositeKey.ID1).publish());
            loader.load();
            GlobModelBuilder.create(AnObjectWithALinkFieldTargettingAMultiKeyObject.TYPE).get();
            fail();
        }
        catch (InvalidParameter e) {
            assertEquals("All key field of target must be references. Missing : [anObjectWithACompositeKey.id2]",
                         e.getMessage());
        }
    }

    public static class AnObjectWithALinkFieldTargettingANonGlobsObject {
        public static GlobType TYPE;
        @KeyField
        public static IntegerField ID;

        public static IntegerField LINK;
    }

    @Ignore
    @Test
    public void testAnObjectWithALinkFieldTargettingANonGlobsObject() throws Exception {
        try {
            GlobTypeLoaderFactory.createAndLoad(AnObjectWithALinkFieldTargettingANonGlobsObject.class);
            fail();
        }
        catch (InvalidParameter e) {
            assertEquals("LinkField 'link' in type '" +
                         AnObjectWithALinkFieldTargettingANonGlobsObject.TYPE.getName() +
                         "' cannot reference target class '" + String.class.getName() +
                         "' because it does not define a Glob type",
                         e.getMessage());
        }
    }

    public static class AnObjectWithAStringId {
        public static GlobType TYPE;
        @KeyField
        public static StringField ID;

        static {
            GlobTypeLoaderFactory.createAndLoad(AnObjectWithAStringId.class);
        }
    }

    public static class AnObjectWithALinkFieldTargettingAnObjectWithAStringId {
        public static GlobType TYPE;
        @KeyField
        public static IntegerField ID;

        public static StringField LINK_ID;

        public static Link LINK;

    }

    @Test
    public void testAnObjectWithALinkFieldTargettingAnObjectWithAStringId() throws Exception {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(AnObjectWithALinkFieldTargettingAnObjectWithAStringId.class);
        loader.register(MutableGlobLinkModel.LinkRegister.class, mutableGlobLinkModel ->
            AnObjectWithALinkFieldTargettingAnObjectWithAStringId.LINK = mutableGlobLinkModel.getDirectLinkBuilder(AnObjectWithALinkFieldTargettingAnObjectWithAStringId.LINK)
                .add(AnObjectWithALinkFieldTargettingAnObjectWithAStringId.LINK_ID, AnObjectWithAStringId.ID)
                .publish());

    }

    public static class AnObjectWithASingleIntegerFieldUsedAsALink {
        public static GlobType TYPE;
        @KeyField
        public static IntegerField ID;
        public static IntegerField LINK_ID;
        public static Link LINK;

        static {
            GlobTypeLoader loader = GlobTypeLoaderFactory.create(AnObjectWithASingleIntegerFieldUsedAsALink.class);

            loader.register(MutableGlobLinkModel.LinkRegister.class, mutableGlobLinkModel -> {
                LINK = mutableGlobLinkModel.getLinkBuilder(LINK)
                    .add(LINK_ID, AnObject.ID)
                    .publish();
            });
            loader.load();
        }
    }

    @Test
    public void testLinkFieldManagedWithAnIntegerField() throws Exception {
        Link link = globModel.getLinkModel().getLink(AnObjectWithASingleIntegerFieldUsedAsALink.TYPE, "link");
        assertNotNull(link);
        assertEquals(AnObjectWithASingleIntegerFieldUsedAsALink.TYPE, link.getSourceType());
        assertEquals(AnObject.TYPE, link.getTargetType());
    }

    @Test
    public void testKeyFields() throws Exception {
        Field[] fields = AnObject.TYPE.getKeyFields();
        ArrayTestUtils.assertContentEquals(Arrays.asList(fields), AnObject.ID);
    }

    @Retention(RUNTIME)
    public @interface MyAnnotation {
        String value();

        GlobType TYPE = MyAnnotationType.TYPE;

    }

    public static class MyAnnotationType {
        static public GlobType TYPE;

        static public StringField VALUE;

        @InitUniqueKey
        static public Key UNIQUE_KEY;

        public static Glob create(Annotation annotation) {
            return TYPE.instantiate().set(VALUE, ((MyAnnotation)annotation).value());
        }

        static {
            GlobTypeLoader loader = GlobTypeLoaderFactory.create(MyAnnotationType.class);
            loader.register(GlobCreateFromAnnotation.class, MyAnnotationType::create);
            loader.load();
        }
    }

    public static class AnObjectWithCustomAnnotations {

        @MyAnnotation("class annotations")
        public static GlobType TYPE;
        @KeyField
        @MyAnnotation("field annotations")
        public static IntegerField ID;

        static {
            GlobTypeLoaderFactory.createAndLoad(AnObjectWithCustomAnnotations.class);
        }
    }

    @Test
    public void testAnnotationsAreAccessible() throws Exception {
        Glob fieldAnnotation = AnObjectWithCustomAnnotations.ID.getAnnotation(MyAnnotationType.UNIQUE_KEY);
        assertEquals("field annotations", fieldAnnotation.get(MyAnnotationType.VALUE));
        Glob classAnnotation = AnObjectWithCustomAnnotations.TYPE.getAnnotation(MyAnnotationType.UNIQUE_KEY);
        assertEquals("class annotations", classAnnotation.get(MyAnnotationType.VALUE));
        Collection<Glob> globs = AnObjectWithCustomAnnotations.TYPE.streamAnnotations(MyAnnotationType.TYPE)
            .collect(Collectors.toList());
        Assert.assertEquals(1, globs.size());
    }

    @Test
    public void testRetrievingAnnotatedFields() throws Exception {
        assertEquals(AnObjectWithCustomAnnotations.ID,
                     AnObjectWithCustomAnnotations.TYPE.getFieldWithAnnotation(
                         MyAnnotationType.UNIQUE_KEY));
    }

    public static class AnObjectWithCustomLinkAnnotations {
        public static GlobType TYPE;

        @KeyField
        public static IntegerField ID;

        @MyAnnotation("link annotation")
        public static Link LINK;

        static {
            GlobTypeLoaderFactory.createAndLoad(AnObjectWithCustomLinkAnnotations.class);
        }
    }

    @Test
    public void testLinkAnnotationsAreAccessible() throws Exception {
        Glob annotation = AnObjectWithCustomLinkAnnotations.LINK.getAnnotation(MyAnnotationType.UNIQUE_KEY);
        assertEquals("link annotation", annotation.get(MyAnnotationType.VALUE));
    }


    public static class AnObjectWithRequiredFields {

        public static GlobType TYPE;

        @Required
        @KeyField
        public static IntegerField ID;

        @Required
        public static StringField STRING;

        public static DoubleField DOUBLE;

        static {
            GlobTypeLoaderFactory.createAndLoad(AnObjectWithRequiredFields.class);
        }
    }

    @Test
    public void testAnObjectWithRequiredFields() throws Exception {
        assertTrue(AnObjectWithRequiredFields.ID.isRequired());
        assertTrue(AnObjectWithRequiredFields.STRING.isRequired());
        assertFalse(AnObjectWithRequiredFields.DOUBLE.isRequired());
    }

    @Test
    public void testAnObjectWithDefaultValues() throws Exception {
        assertEquals(7, DummyObjectWithDefaultValues.INTEGER.getDefaultValue());
        assertEquals(7, DummyObjectWithDefaultValues.INTEGER.getAnnotation(DefaultIntegerAnnotationType.UNIQUE_KEY)
            .get(DefaultIntegerAnnotationType.DEFAULT_VALUE).intValue());
        assertEquals(3.14159265, DummyObjectWithDefaultValues.DOUBLE.getDefaultValue());
        assertEquals(5l, DummyObjectWithDefaultValues.LONG.getDefaultValue());
        assertEquals(true, DummyObjectWithDefaultValues.BOOLEAN.getDefaultValue());
        assertEquals("Hello", DummyObjectWithDefaultValues.STRING.getDefaultValue());
//      assertEquals(1, DummyObjectWithDefaultValues.LINK.getDefaultValue());
//      TestUtils.assertDatesEqual(new Date(), (Date)DummyObjectWithDefaultValues.DATE.getDefaultValue(), 360000);
//      TestUtils.assertDatesEqual(new Date(), (Date)DummyObjectWithDefaultValues.TIMESTAMP.getDefaultValue(), 360000);
    }

    public static class AnObjectWithADefaultValueTypeError {
        public static GlobType TYPE;
        @KeyField
        public static IntegerField ID;

        @DefaultBoolean(true)
        public static IntegerField COUNT;
    }

    @Test
    @Ignore
    public void testAnObjectWithADefaultValueTypeError() throws Exception {
        try {
            GlobTypeLoaderFactory.createAndLoad(AnObjectWithADefaultValueTypeError.class);
            fail();
        }
        catch (InvalidParameter e) {
            assertEquals("Field anObjectWithADefaultValueTypeError.count should declare a default value " +
                         "with annotation @DefaultInteger instead of @DefaultBoolean",
                         e.getMessage());
        }
    }

    public static class AnObjectWithRequiredLinks {

        public static GlobType TYPE;

        @KeyField
        public static IntegerField ID;

        public static IntegerField LINK_ID;

        @Required
        public static Link LINK;

        static {
            GlobTypeLoader loader = GlobTypeLoaderFactory.create(AnObjectWithRequiredLinks.class);
            loader.register(MutableGlobLinkModel.LinkRegister.class, mutableGlobLinkModel -> {
                LINK = mutableGlobLinkModel.getDirectLinkBuilder(LINK)
                    .add(LINK_ID, DummyObject.ID).publish();
            });
            loader.load();
        }
    }

    @Test
    public void testAnObjectWithRequiredLinks() throws Exception {
        assertTrue(AnObjectWithRequiredLinks.LINK.isRequired());
    }

    public static class AnObjectWithRequiredLinkField {

        public static GlobType TYPE;

        @KeyField
        public static IntegerField ID;

        @Target(DummyObject.class)
        public static IntegerField LINK_ID;

        @Required
        public static Link LINK;


        static {
            GlobTypeLoader loader = GlobTypeLoaderFactory.create(AnObjectWithRequiredLinkField.class);
            loader.register(MutableGlobLinkModel.LinkRegister.class, mutableGlobLinkModel ->
                LINK = mutableGlobLinkModel.getDirectLinkBuilder(LINK)
                    .add(LINK_ID, DummyObject.ID).publish());
            loader.load();
        }
    }

    @Test
    public void testAnObjectWithRequiredLinkField() throws Exception {
        assertTrue(AnObjectWithRequiredLinkField.LINK.isRequired());
    }
}
