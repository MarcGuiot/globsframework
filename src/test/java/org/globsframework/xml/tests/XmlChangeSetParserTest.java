package org.globsframework.xml.tests;

import org.globsframework.metamodel.DummyModel;
import org.globsframework.metamodel.DummyObject;
import org.globsframework.model.*;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemNotFound;
import org.globsframework.xml.XmlChangeSetParser;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

public class XmlChangeSetParserTest {
    @Test
    public void testStandardCase() throws Exception {
        ChangeSet changeSet = XmlChangeSetParser.parse(DummyModel.get(), new StringReader(
            "<changes>"
            + "  <create type='dummyObject' id='1' name='name1' value='2.0' present='true'/>"
            + "  <update type='dummyObject' id='2' name='newName' _name='previousName'/>"
            + "  <delete type='dummyObject' id='3' _name='name3'/>"
            + "</changes>"
        ));

        assertEquals(3, changeSet.getChangeCount(DummyObject.TYPE));
        changeSet.visit(new ChangeSetVisitor() {
            public void visitCreation(Key key, FieldValues values) throws Exception {
                assertEquals(1, key.get(DummyObject.ID).intValue());
                assertEquals(8, values.size());
                assertEquals("name1", values.get(DummyObject.NAME));
                assertEquals(2.0, values.get(DummyObject.VALUE), 0.01);
                assertTrue(values.get(DummyObject.PRESENT));
                assertNull(values.get(DummyObject.DATE));
            }

            public void visitUpdate(Key key, FieldValuesWithPrevious values) throws Exception {
                assertEquals(2, key.get(DummyObject.ID).intValue());
                assertEquals(1, values.size());
                assertEquals("newName", values.get(DummyObject.NAME));
                assertEquals("previousName", values.getPrevious(DummyObject.NAME));
                assertFalse(values.contains(DummyObject.DATE));
                assertFalse(values.contains(DummyObject.VALUE));
            }

            public void visitDeletion(Key key, FieldValues values) throws Exception {
                assertEquals(3, key.get(DummyObject.ID).intValue());
                assertEquals(8, values.size());
                assertEquals("name3", values.get(DummyObject.NAME));
                assertNull(values.get(DummyObject.VALUE));
                assertNull(values.get(DummyObject.PRESENT));
                assertNull(values.get(DummyObject.DATE));
            }
        });
    }

    public void testMissingType() throws Exception {
        try {
            XmlChangeSetParser.parse(DummyModel.get(), new StringReader(
                "<changes>"
                + "  <create id='2' name='name1'/>"
                + "</changes>"
            ));
            fail();
        }
        catch (InvalidParameter e) {
            assertEquals("Missing attribute 'type' in tag 'create'", e.getMessage());
        }
    }

    public void testWrongType() throws Exception {
        try {
            XmlChangeSetParser.parse(DummyModel.get(), new StringReader(
                "<changes>"
                + "  <create type='unknown' id='2' name='name1'/>"
                + "</changes>"
            ));
            fail();
        }
        catch (ItemNotFound e) {
            assertEquals("No object type found with name: unknown", e.getMessage());
        }
    }

    public void testUnknownField() throws Exception {
        try {
            XmlChangeSetParser.parse(DummyModel.get(), new StringReader(
                "<changes>"
                + "  <create type='dummyObject' id='2' toto='name1'/>"
                + "</changes>"
            ));
            fail();
        }
        catch (ItemNotFound e) {
            assertEquals("Unknown field 'toto' for type 'dummyObject'", e.getMessage());
        }
    }

    public void testInvalidValue() throws Exception {
        try {
            XmlChangeSetParser.parse(DummyModel.get(), new StringReader(
                "<changes>"
                + "  <create type='dummyObject' id='2' value='toto'/>"
                + "</changes>"
            ));
            fail();
        }
        catch (InvalidParameter e) {
            assertEquals("'toto' is not a proper value for field 'value' in type 'dummyObject'", e.getMessage());
        }
    }
}
