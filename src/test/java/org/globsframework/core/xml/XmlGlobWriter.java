package org.globsframework.core.xml;

import org.globsframework.core.metamodel.GlobLinkModel;
import org.globsframework.core.metamodel.annotations.FieldName;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.metamodel.links.Link;
import org.globsframework.core.metamodel.links.impl.DefaultDirectSingleLink;
import org.globsframework.core.metamodel.utils.EmptyGlobLinkModel;
import org.globsframework.core.metamodel.utils.GlobTypeUtils;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.GlobRepository;
import org.globsframework.core.utils.Strings;
import org.globsframework.core.utils.exceptions.ResourceAccessFailed;
import org.globsframework.saxstack.utils.XmlUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class XmlGlobWriter {
    private FieldConverter fieldConverter = new FieldConverter();
    private List<Glob> globsToWrite;
    private List<Glob> writtenGlobs = new ArrayList<>();
    private GlobRepository repository;
    private Writer writer;
    private GlobLinkModel globLinkModel;

    private XmlGlobWriter(Collection<Glob> globs, GlobRepository repository, Writer writer, GlobLinkModel globLinkModel) {
        this.globsToWrite = new ArrayList<>(globs);
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
        } catch (IOException e) {
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
        } else {
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
        writeAttribute(writer, FieldName.getName(field), fieldConverter.toString(field, value));
    }

    public static void write(Collection<Glob> globs, GlobRepository repository, Writer writer, GlobLinkModel globLinkModel) throws ResourceAccessFailed {
        XmlGlobWriter xmlWriter = new XmlGlobWriter(globs, repository, writer, globLinkModel);
        xmlWriter.doWrite();
    }

    public static void write(Collection<Glob> globs, GlobRepository repository, Writer writer) throws ResourceAccessFailed {
        XmlGlobWriter xmlWriter = new XmlGlobWriter(globs, repository, writer, EmptyGlobLinkModel.EMPTY);
        xmlWriter.doWrite();
    }

    public static void write(Collection<Glob> globs, GlobRepository repository, OutputStream stream) throws ResourceAccessFailed {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(stream);
        write(globs, repository, outputStreamWriter);
        try {
            outputStreamWriter.flush();
        } catch (IOException e) {
            throw new ResourceAccessFailed(e);
        }
    }

    private static List<Glob> getChildren(final Glob target, final GlobRepository repository, GlobLinkModel globLinkModel) {
        Link[] links = globLinkModel.getLinks(target.getType());
//        Link[] inLinks = target.getType().getInboundLinks();
        List<Glob> children = new ArrayList<>();
        for (Link link : links) {
            if (link.isContainment()) {
                Collection<Glob> list = repository.findLinkedTo(target, link);
                children.addAll(list);
            }
        }
        return children;
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
                } catch (IOException e) {
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
