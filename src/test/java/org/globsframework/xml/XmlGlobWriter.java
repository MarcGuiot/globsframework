package org.globsframework.xml;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobLinkModel;
import org.globsframework.metamodel.links.Link;
import org.globsframework.metamodel.links.impl.DefaultDirectSingleLink;
import org.globsframework.metamodel.utils.EmptyGlobLinkModel;
import org.globsframework.metamodel.utils.GlobTypeUtils;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;
import org.globsframework.saxstack.utils.XmlUtils;
import org.globsframework.utils.Strings;
import org.globsframework.utils.exceptions.ResourceAccessFailed;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class XmlGlobWriter {
    private FieldConverter fieldConverter = new FieldConverter();
    private GlobList globsToWrite;
    private GlobList writtenGlobs = new GlobList();
    private GlobRepository repository;
    private Writer writer;
    private GlobLinkModel globLinkModel;

    public static void write(List<Glob> globs, GlobRepository repository, Writer writer, GlobLinkModel globLinkModel) throws ResourceAccessFailed {
        XmlGlobWriter xmlWriter = new XmlGlobWriter(globs, repository, writer, globLinkModel);
        xmlWriter.doWrite();
    }

    public static void write(List<Glob> globs, GlobRepository repository, Writer writer) throws ResourceAccessFailed {
        XmlGlobWriter xmlWriter = new XmlGlobWriter(globs, repository, writer, EmptyGlobLinkModel.EMPTY);
        xmlWriter.doWrite();
    }

    public static void write(List<Glob> globs, GlobRepository repository, OutputStream stream) throws ResourceAccessFailed {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(stream);
        write(globs, repository, outputStreamWriter);
        try {
            outputStreamWriter.flush();
        }
        catch (IOException e) {
            throw new ResourceAccessFailed(e);
        }
    }

    private XmlGlobWriter(List<Glob> globs, GlobRepository repository, Writer writer, GlobLinkModel globLinkModel) {
        this.globsToWrite = new GlobList(globs);
        this.repository = repository;
        this.writer = writer;
        this.globLinkModel = globLinkModel;
    }

    private void doWrite() {
        try {
            writer.write("<globs>");
            for (Glob glob : globsToWrite) {
                writeGlob(glob);
            }
            writer.write("</globs>");
        }
        catch (IOException e) {
            throw new ResourceAccessFailed(e);
        }
    }

    private void writeGlob(Glob glob) throws IOException {
        if (writtenGlobs.contains(glob)) {
            return;
        }
        writtenGlobs.add(glob);
        writer.write("<");
        writer.write(glob.getType().getName());
        writeFields(glob, writer);
        writeLinks(glob, repository, writer, globLinkModel);
        List<Glob> children = getChildren(glob, repository, globLinkModel);
        if (children.isEmpty()) {
            writer.write("/>");
        }
        else {
            writer.write(">");
            for (Glob child : children) {
                writeGlob(child);
            }
            writer.write("</");
            writer.write(glob.getType().getName());
            writer.write(">");
        }
        writer.write(Strings.LINE_SEPARATOR);
    }

    private static List<Glob> getChildren(final Glob target, final GlobRepository repository, GlobLinkModel globLinkModel) {
        Link[] links = globLinkModel.getLinks(target.getType());
//        Link[] inLinks = target.getType().getInboundLinks();
        GlobList children = new GlobList();
        for (Link link : links) {
            if (link.isContainment()) {
                GlobList list = repository.findLinkedTo(target, link);
                children.addAll(list);
            }
        }
        return children;
    }

    private void writeFields(Glob glob, Writer writer) throws IOException {
        for (Field field : glob.getType().getFields()) {
            Object value = glob.getValue(field);
            if (value == null) {
                continue;
            }
            writeFieldValue(writer, field, value);
        }
    }

    private void writeFieldValue(Writer writer, Field field, Object value) throws IOException {
        writeAttribute(writer, field.getName(), fieldConverter.toString(field, value));
    }

    private static void writeAttribute(Writer writer,
                                       String attributeName,
                                       Object attributeValue) throws IOException {
        writer.write(" ");
        writer.write(attributeName);
        writer.write("=\"");
        writer.write(XmlUtils.convertEntities(Strings.toString(attributeValue)));
        writer.write("\"");
    }

    private static void writeLinks(final Glob glob, final GlobRepository repository,
                                   final Writer writer, GlobLinkModel globLinkModel) throws IOException {
        for (Link link : globLinkModel.getLinks(glob.getType())) {
            Glob target = repository.findLinkTarget(glob, link);
            if (target != null) {
                Field namingField = GlobTypeUtils.findNamingField(link.getTargetType());
                if (namingField == null) {
                    return;
                }

                Object value = target.getValue(namingField);
                if (value == null) {
                    return;
                }
                try {
                    writeAttribute(writer, getLinkName(link, namingField), value);
                }
                catch (IOException e) {
                    throw new ResourceAccessFailed(e);
                }
            }
        }
    }

    private static String getLinkName(Link link, Field targetNamingField) {
        if (link instanceof DefaultDirectSingleLink) {
            return link.getName() + Strings.capitalize(targetNamingField.getName());
        }
        return link.getName();
    }
}
