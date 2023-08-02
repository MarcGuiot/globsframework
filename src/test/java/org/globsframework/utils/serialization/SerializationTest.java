package org.globsframework.utils.serialization;

import org.globsframework.metamodel.DummyModel;
import org.globsframework.metamodel.DummyObject;
import org.globsframework.metamodel.DummyObjectInner;
import org.globsframework.metamodel.DummyObjectWithInner;
import org.globsframework.model.*;
import org.globsframework.model.delta.DefaultChangeSet;
import org.globsframework.model.delta.MutableChangeSet;
import org.globsframework.model.utils.GlobBuilder;
import org.globsframework.utils.ArrayTestUtils;
import org.globsframework.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class SerializationTest {
    protected SerializedOutput output;
    protected OutputStream outputStream;
    protected InputStream inputStream;
    protected SerializedInput input;
    private int currentDate;
    private File file;

    @Before
    public void setUp() throws Exception {
        file = TestUtils.getFileName(this, "sample.dat");
//    String fileName = "/tmp/output.bin";
        file.getParentFile().mkdirs();

        outputStream = new BufferedOutputStream(new FileOutputStream(file));
        output = new SerializedOutputChecker(new DefaultSerializationOutput(outputStream));
    }

    private void initInputStream(File file) throws IOException {
        //    inputStream = new FileInputStream(fileName);
//    inputStream = new BufferedInputStream(new FileInputStream(fileName));
        final FileInputStream stream = new FileInputStream(file);
        inputStream = new YANBuffereInputStream(stream);
//        input = new SerializationInputChecker(new DefaultSerializationInput(inputStream));
        input =  new SerializationInputChecker(new ByteBufferSerializationInput(stream.readAllBytes()));
    }

    @Test
    public void testSimpleValues() throws Exception {
        output.write(3);
        output.write(33L);
        output.write(6.33);
        output.write(false);
        output.write(new int[]{3, 5, 9});
        outputStream.close();

        initInputStream(file);

        assertEquals(3, input.readNotNullInt());
        assertEquals(33L, input.readNotNullLong());
        assertEquals(6.33, input.readNotNullDouble(), 0.0001);
        assertEquals(false, input.readBoolean());
        ArrayTestUtils.assertEquals(new int[]{3, 5, 9}, input.readIntArray());
        inputStream.close();
    }

    @Test
    public void testObjects() throws Exception {

        output.writeUtf8String("blah");
        output.writeBoolean(Boolean.TRUE);
        output.writeDouble(6.33);
        output.writeInteger(4);
        output.writeLong(666L);
        outputStream.close();

        initInputStream(file);
        assertEquals("blah", input.readUtf8String());
        assertEquals(Boolean.TRUE, input.readBoolean());
        assertEquals(6.33, input.readDouble(), 0.001);
        assertEquals(4, input.readInteger().intValue());
        assertEquals(666L, input.readLong().longValue());
        inputStream.close();
    }

    @Test
    public void testGlob() throws Exception {
        Glob glob = GlobBuilder.init(DummyObject.TYPE, createSampleValues()).get();

        output.writeGlob(glob);
        output.writeUtf8String("end");
        outputStream.close();

        initInputStream(file);
        Glob newGlob = input.readGlob(DummyModel.get());
        assertNotSame(glob, newGlob);

//        assertEquals(glob.getValues(), newGlob.getValues());
        assertEquals(glob.getKey(), newGlob.getKey());
        assertEquals("end", input.readUtf8String());
        inputStream.close();
    }

    @Test
    public void testChangeSet() throws Exception {
        MutableChangeSet changeSet = new DefaultChangeSet();
        changeSet.processCreation(KeyBuilder.newKey(DummyObject.TYPE, 1),
                                  FieldValuesBuilder.init()
                                      .set(DummyObject.ID, 1)
                                      .set(DummyObject.NAME, "name1")
                                      .get());
        changeSet.processUpdate(KeyBuilder.newKey(DummyObject.TYPE, 2), DummyObject.NAME, "name2", null);
        changeSet.processDeletion(KeyBuilder.newKey(DummyObject.TYPE, 3),
                                  FieldValuesBuilder.init()
                                      .set(DummyObject.ID, 3)
                                      .set(DummyObject.NAME, "name3")
                                      .set(DummyObject.VALUE, 3.14156)
                                      .get());
        output.writeChangeSet(changeSet);
        outputStream.close();

        initInputStream(file);
        changeSet.toString();

        ChangeSet readChangeSet = input.readChangeSet(DummyModel.get());
        GlobTestUtils.assertChangesEqual(readChangeSet,
                                         "<create type='dummyObject' id='1' name='name1'/>" +
                                         "<update type='dummyObject' id='2' name='name2' _name='(null)'/>" +
                                         "<delete type='dummyObject' id='3' _name='name3' _value='3.14'/>");
    }

    @Test
    public void readWriteGlobArray() throws IOException {
        MutableChangeSet changeSet = new DefaultChangeSet();
        changeSet.processCreation(KeyBuilder.newKey(DummyObjectWithInner.TYPE, 1),
                DummyObjectWithInner.TYPE.instantiate()
                        .set(DummyObjectWithInner.VALUE, DummyObjectInner.TYPE.instantiate().set(DummyObjectInner.VALUE, 3.14))
                        .set(DummyObjectWithInner.VALUES, new Glob[]{DummyObjectInner.TYPE.instantiate().set(DummyObjectInner.VALUE, 3.14 * 2),
                                DummyObjectInner.TYPE.instantiate().set(DummyObjectInner.VALUE, 3.14 * 3)})
                        .set(DummyObjectWithInner.VALUE_UNION, DummyObject.TYPE.instantiate().set(DummyObject.VALUE, 3.14))
                        .set(DummyObjectWithInner.VALUES_UNION, new Glob[]{DummyObjectInner.TYPE.instantiate().set(DummyObjectInner.VALUE, 3.14 * 2),
                                DummyObject.TYPE.instantiate().set(DummyObject.VALUE, 3.14 * 3)}));

        changeSet.processUpdate(KeyBuilder.newKey(DummyObjectWithInner.TYPE, 2),
                DummyObjectWithInner.VALUES, new Glob[]{DummyObjectInner.TYPE.instantiate().set(DummyObjectInner.VALUE, 3.14 * 2),
                        DummyObjectInner.TYPE.instantiate().set(DummyObjectInner.VALUE, 3.14 * 3)},
                new Glob[]{DummyObjectInner.TYPE.instantiate().set(DummyObjectInner.VALUE, 3.14 * 1),
                        DummyObjectInner.TYPE.instantiate().set(DummyObjectInner.VALUE, 3.14 * 2)});

        output.writeChangeSet(changeSet);
        outputStream.close();
        initInputStream(file);

        ChangeSet readChangeSet = input.readChangeSet(DummyModel.get());
        Assert.assertTrue(readChangeSet.containsUpdates(DummyObjectWithInner.VALUES));
//        GlobTestUtils.assertChangesEqual(readChangeSet,
//                "<create type='dummyObject' id='1' name='name1'/>" +
//                        "<update type='dummyObject' id='2' name='name2' _name='(null)'/>" +
//                        "<delete type='dummyObject' id='3' _name='name3' _value='3.14'/>");

    }

    @Test
    public void withInnerGlob() throws IOException {
        MutableGlob obj1 = DummyObjectWithInner.TYPE.instantiate()
                .set(DummyObjectWithInner.ID, 1)
                .set(DummyObjectWithInner.byteArrayData, "Some Data".getBytes(StandardCharsets.UTF_8))
                .set(DummyObjectWithInner.VALUE, DummyObjectInner.TYPE.instantiate().set(DummyObjectInner.VALUE, 3.14))
                .set(DummyObjectWithInner.VALUES, new Glob[]{DummyObjectInner.TYPE.instantiate().set(DummyObjectInner.VALUE, 3.14 * 2),
                        DummyObjectInner.TYPE.instantiate().set(DummyObjectInner.VALUE, 3.14 * 3)})
                .set(DummyObjectWithInner.VALUE_UNION, DummyObject.TYPE.instantiate().set(DummyObject.VALUE, 3.14))
                .set(DummyObjectWithInner.VALUES_UNION, new Glob[]{DummyObjectInner.TYPE.instantiate().set(DummyObjectInner.VALUE, 3.14 * 2),
                        DummyObject.TYPE.instantiate().set(DummyObject.VALUE, 3.14 * 3)});

        output.writeKnowGlob(obj1);
        outputStream.close();
        initInputStream(file);

        Glob newGlob = input.readKnowGlob(DummyObjectWithInner.TYPE);
        assertNotSame(obj1, newGlob);
        Assert.assertEquals("Some Data", new String(newGlob.get(DummyObjectWithInner.byteArrayData)));
        Assert.assertEquals(newGlob.get(DummyObjectWithInner.VALUE).get(DummyObjectInner.VALUE), 3.14, 0.01);
        Assert.assertEquals(newGlob.get(DummyObjectWithInner.VALUE_UNION).get(DummyObject.VALUE), 3.14, 0.01);
        Assert.assertEquals(newGlob.get(DummyObjectWithInner.VALUES_UNION)[0].get(DummyObjectInner.VALUE), 3.14 * 2, 0.01);
        Assert.assertEquals(newGlob.get(DummyObjectWithInner.VALUES_UNION)[1].get(DummyObject.VALUE), 3.14 * 3, 0.01);
    }

    //  public void testBigWrite() throws Exception {
//    for (int i = 0; i < 550000; i++){
//      output.writeJavaString("blah");
//      output.writeBoolean(Boolean.TRUE);
//      output.writeDouble(6.33);
//      output.writeDate(currentDate);
//      output.writeInteger(4);
//      output.writeLong(666L);
//      output.write(new long[100]);
//    }
//    outputStream.close();
//  }
//
//
//  public void testBigRead() throws Exception {
//    for (int i = 0; i < 250000; i++){
//      input.readJavaString();
//      input.readBoolean();
//      input.readDouble();
//      input.readDate();
//      input.readInteger();
//      assertEquals(666L, (long)input.readLong());
//      input.readLongArray();
//    }
//  }

    private FieldValues createSampleValues() {
        return FieldValuesBuilder.init()
            .set(DummyObject.ID, 1)
            .set(DummyObject.NAME, "obj1")
            .set(DummyObject.LINK_ID, 7)
            .set(DummyObject.PRESENT, false)
            .set(DummyObject.VALUE, 6.2)
            .get();
    }

    public static final String TMP_OUTPUT_SER = "/tmp/output1G.ser";
    final static long SIZE = 1000;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(TMP_OUTPUT_SER);
        SerializedInput serizalizedInput = SerializedInputOutputFactory.init(new BufferedInputStream(new FileInputStream(file)));
        long start = System.nanoTime();
        for (long i = 0; i < SIZE * (1024l * 1024l / 8l); i++) {
            serizalizedInput.readNotNullDouble();
        }
        long end = System.nanoTime();
        System.out.println("PerfRead.read " + file.length() / ((end - start) / 1000000000.) / 1024 / 1024);

    }
}
