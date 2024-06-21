package org.globsframework.xml;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.ChangeSet;
import org.globsframework.model.ChangeSetVisitor;
import org.globsframework.model.Key;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class XmlChangeSetWriter {
    private XmlChangeSetWriter() {
    }

    public static void write(ChangeSet changeSet, Writer writer) {
        doWrite(changeSet, writer, new ContentDumper() {
            public void process(ChangeSet changeSet, ChangeSetVisitor visitor) {
                changeSet.safeAccept(visitor);
            }
        });
    }

    public static void write(ChangeSet changeSet, final List<Key> keys, Writer writer) {
        doWrite(changeSet, writer, new ContentDumper() {
            public void process(ChangeSet changeSet, ChangeSetVisitor visitor) {
                for (Key key : keys) {
                    changeSet.safeAccept(key, visitor);
                }
            }
        });
    }

    public static void write(ChangeSet changeSet, final GlobType type, Writer writer) {
        doWrite(changeSet, writer, new ContentDumper() {
            public void process(ChangeSet changeSet, ChangeSetVisitor visitor) {
                changeSet.safeAccept(type, visitor);
            }
        });
    }

    public static void prettyWrite(ChangeSet changeSet, Writer writer) {
        XmlChangeSetVisitor visitor = new XmlChangeSetVisitor(writer, 2);
        changeSet.safeAccept(visitor);
        visitor.complete();
        try {
            writer.flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void doWrite(ChangeSet changeSet, Writer writer, ContentDumper dumper) {
        XmlChangeSetVisitor visitor = new XmlChangeSetVisitor(writer);
        dumper.process(changeSet, visitor);
        visitor.complete();
    }

    private interface ContentDumper {
        void process(ChangeSet changeSet, ChangeSetVisitor visitor);
    }
}
