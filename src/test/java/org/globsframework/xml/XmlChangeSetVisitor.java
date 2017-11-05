package org.globsframework.xml;

import org.globsframework.model.ChangeSetVisitor;
import org.globsframework.model.FieldValues;
import org.globsframework.model.FieldValuesWithPrevious;
import org.globsframework.model.Key;
import org.globsframework.saxstack.writer.PrettyPrintRootXmlTag;
import org.globsframework.saxstack.writer.RootXmlTag;
import org.globsframework.saxstack.writer.XmlTag;

import java.io.IOException;
import java.io.Writer;

public class XmlChangeSetVisitor implements ChangeSetVisitor {
    private final XmlTag changesTag;
    private final FieldConverter converter = new FieldConverter();

    public XmlChangeSetVisitor(XmlTag changesTag) {
        this.changesTag = changesTag;
    }

    public XmlChangeSetVisitor(Writer writer, int indent) {
        try {
            XmlTag root = new PrettyPrintRootXmlTag(writer, indent);
            changesTag = root.createChildTag("changes");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public XmlChangeSetVisitor(Writer writer) {
        try {
            XmlTag root = new RootXmlTag(writer);
            changesTag = root.createChildTag("changes");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void visitCreation(Key key, FieldValues values) throws Exception {
        dumpChanges("create", key, values, false);
    }

    public void visitUpdate(Key key, FieldValuesWithPrevious values) throws Exception {
        dumpChanges("update", key, values);
    }

    public void visitDeletion(Key key, FieldValues values) throws Exception {
        dumpChanges("delete", key, values, true);
    }

    private void dumpChanges(String change, Key key, FieldValues values, boolean previousValues) throws IOException {
        XmlTag tag = changesTag.createChildTag(change);
        tag.addAttribute("type", key.getGlobType().getName());
        dumpFieldValues(tag, key.asFieldValues(), false);
        dumpFieldValues(tag, values, previousValues);
        tag.end();
    }

    private void dumpChanges(String change, Key key, FieldValuesWithPrevious values) throws IOException {
        XmlTag tag = changesTag.createChildTag(change);
        tag.addAttribute("type", key.getGlobType().getName());
        dumpFieldValues(tag, key.asFieldValues(), false);
        dumpFieldValues(tag, values);
        tag.end();
    }

    private void dumpFieldValues(final XmlTag tag, FieldValues values, final boolean previousValues) throws IOException {
        values.safeApply((field, value) -> {
            if (value != null) {
                final String name = previousValues ? "_" + field.getName() : field.getName();
                tag.addAttribute(name, converter.toString(field, value));
            }
        });
    }

    private void dumpFieldValues(final XmlTag tag, FieldValuesWithPrevious values) throws IOException {
        values.safeApply((field, value, previousValue) -> {
            if ((value != null) || (previousValue != null)) {
                tag.addAttribute(field.getName(), converter.toString(field, value));
                tag.addAttribute("_" + field.getName(), converter.toString(field, previousValue));
            }
        });
    }

    public void complete() {
        try {
            changesTag.end();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
