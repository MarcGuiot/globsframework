package org.globsframework.xml.tests;

import org.globsframework.metamodel.DummyObject;
import org.globsframework.model.FieldValuesBuilder;
import static org.globsframework.model.KeyBuilder.newKey;
import org.globsframework.model.delta.DefaultChangeSet;
import org.globsframework.xml.XmlChangeSetWriter;
import org.junit.Test;

import java.io.StringWriter;

public class XmlChangeSetWriterTest {

  @Test
  public void test() throws Exception {
    DefaultChangeSet changeSet = new DefaultChangeSet();
    changeSet.processCreation(DummyObject.TYPE,
                              FieldValuesBuilder.init()
                                .set(DummyObject.ID, 1)
                                .set(DummyObject.NAME, "obj1")
                                .get());

    changeSet.processUpdate(newKey(DummyObject.TYPE, 2), DummyObject.VALUE, 2.3, 1.7);
    changeSet.processUpdate(newKey(DummyObject.TYPE, 3), DummyObject.VALUE, null, 1.5);
    changeSet.processDeletion(newKey(DummyObject.TYPE, 4), FieldValuesBuilder.init(DummyObject.NAME, "obj3").get());

    StringWriter writer = new StringWriter();
    XmlChangeSetWriter.write(changeSet, writer);
    XmlTestUtils.assertEquivalent("<changes>" +
                                  "  <create type='dummyObject' id='1' name='obj1'/>" +
                                  "  <update type='dummyObject' id='2' value='2.3' _value='1.7'/>" +
                                  "  <update type='dummyObject' id='3' value='(null)' _value='1.5'/>" +
                                  "  <delete type='dummyObject' id='4' _name='obj3'/>" +
                                  "</changes>",
                                  writer.toString());
  }
}
