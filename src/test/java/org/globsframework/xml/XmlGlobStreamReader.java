package org.globsframework.xml;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobModel;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobList;
import org.globsframework.model.MutableGlob;
import org.globsframework.saxstack.parser.DefaultXmlNode;
import org.globsframework.saxstack.parser.ExceptionHolder;
import org.globsframework.saxstack.parser.SaxStackParser;
import org.globsframework.saxstack.parser.XmlNode;
import org.globsframework.saxstack.utils.XmlUtils;
import org.globsframework.streams.GlobStream;
import org.globsframework.streams.accessors.*;
import org.xml.sax.Attributes;

import java.io.StringReader;
import java.util.*;

public class XmlGlobStreamReader {
    private GlobModel globModel;
    private String xml;
    private XmlGlobStream xmlMoStream;

    public static GlobStream parse(String xml, final GlobModel globModel) {
        return new XmlGlobStreamReader("<root>" + xml + "</root>", globModel).parse();
    }

    private XmlGlobStreamReader(String xml, final GlobModel globModel) {
        this.xml = xml;
        this.globModel = globModel;
    }

    private GlobStream parse() {
        xmlMoStream = new XmlGlobStream();
        SaxStackParser.parse(XmlUtils.getXmlReader(), new RootProxyNode(), new StringReader(xml));
        return xmlMoStream;
    }

    private void add(Glob mo) {
        xmlMoStream.add(mo);
    }

    class RootProxyNode extends DefaultXmlNode implements XmlSingleGlobParser.GlobAdder {

        public RootProxyNode() {
        }

        public XmlNode getSubNode(String childName, Attributes xmlAttrs, String uri, String fullName) throws ExceptionHolder {
            try {
                if (childName.equals("root")) {
                    return this;
                }
                else {
                    XmlSingleGlobParser.parse(childName, xmlAttrs, globModel, this);
                    return this;
                }
            }
            catch (Exception e) {
                throw new ExceptionHolder(e);
            }
        }

        public void add(MutableGlob mo) {
            XmlGlobStreamReader.this.add(mo);
        }
    }

    private static class XmlGlobStream implements GlobStream {
        private GlobType globType;
        private GlobList globs = new GlobList();
        private List<Field> fields = new ArrayList<Field>();
        private Iterator<Glob> iterator;
        private Glob current;
        private Map<String, Accessor> accessors = new HashMap<String, Accessor>();

        public XmlGlobStream() {
        }

        public boolean next() {
            if (iterator == null) {
                iterator = globs.iterator();
            }
            if (iterator.hasNext()) {
                current = iterator.next();
                return true;
            }
            return false;
        }

        public Collection<Field> getFields() {
            return fields;
        }

        public GlobType getObjectType() {
            return globType;
        }

        public Accessor getAccessor(Field field) {
            return accessors.get(field.getName());
        }

        public void close() {
        }

        public void add(Glob glob) {
            if (globs.isEmpty()) {
                globType = glob.getType();
                for (Field field : glob.getType().getFields()) {
                    AccessorDataTypeVisitor dataTypeVisitor = new AccessorDataTypeVisitor(this);
                    field.safeVisit(dataTypeVisitor);
                    accessors.put(field.getName(), dataTypeVisitor.getAccessor());
                    fields.add(globType.getField(field.getName()));
                }
            }
            globs.add(glob);
        }

        private static class AccessorDataTypeVisitor implements FieldVisitor {
            private XmlGlobStream stream;
            private Accessor accessor;

            public AccessorDataTypeVisitor(XmlGlobStream stream) {
                this.stream = stream;
            }

            public void visitBoolean(BooleanField field) throws Exception {
                accessor = new XmlBooleanAccessor(stream, field);
            }

            public void visitBlob(BlobField field) throws Exception {
                accessor = new XmlBlobAccessor(stream, field);
            }

            public void visitString(StringField field) throws Exception {
                accessor = new XmlStringAccessor(stream, field);
            }

            public void visitDouble(DoubleField field) throws Exception {
                accessor = new XmlDoubleAccessor(stream, field);
            }

            public void visitInteger(IntegerField field) throws Exception {
                accessor = new XmlIntegerAccessor(stream, field);
            }

            public void visitLong(LongField field) throws Exception {
                accessor = new XmlLongAccessor(stream, field);
            }

            public Accessor getAccessor() {
                return accessor;
            }


            private static class XmlBlobAccessor implements BlobAccessor {
                private XmlGlobStream xmlMoStream;
                private BlobField field;

                public XmlBlobAccessor(XmlGlobStream xmlMoStream, BlobField field) {
                    this.xmlMoStream = xmlMoStream;
                    this.field = field;
                }

                public Object getObjectValue() {
                    return getValue();
                }

                public byte[] getValue() {
                    return xmlMoStream.current.get(field);
                }
            }

            private static class XmlStringAccessor implements StringAccessor {
                private XmlGlobStream xmlMoStream;
                private StringField field;

                public XmlStringAccessor(XmlGlobStream xmlMoStream, StringField field) {
                    this.xmlMoStream = xmlMoStream;
                    this.field = field;
                }

                public String getString() {
                    return xmlMoStream.current.get(field);
                }

                public Object getObjectValue() {
                    return getString();
                }
            }

            private static class XmlBooleanAccessor implements BooleanAccessor {
                private XmlGlobStream xmlMoStream;
                private BooleanField field;

                public XmlBooleanAccessor(XmlGlobStream xmlMoStream, BooleanField field) {
                    this.xmlMoStream = xmlMoStream;
                    this.field = field;
                }

                public Boolean getBoolean() {
                    return xmlMoStream.current.get(field);
                }

                public boolean getValue(boolean valueIfNull) {
                    return getBoolean();
                }

                public Object getObjectValue() {
                    return getBoolean();
                }
            }

            private static class XmlDoubleAccessor implements DoubleAccessor {
                private XmlGlobStream xmlMoStream;
                private DoubleField field;

                public XmlDoubleAccessor(XmlGlobStream xmlMoStream, DoubleField field) {
                    this.xmlMoStream = xmlMoStream;
                    this.field = field;
                }

                public Double getDouble() {
                    return xmlMoStream.current.get(field);
                }

                public double getValue(double valueIfNull) {
                    Double value = getDouble();
                    return value == null ? valueIfNull : value;
                }

                public boolean wasNull() {
                    return getDouble() == null;
                }

                public Object getObjectValue() {
                    return getDouble();
                }
            }

            private class XmlIntegerAccessor implements IntegerAccessor {
                private XmlGlobStream xmlMoStream;
                private IntegerField field;

                public XmlIntegerAccessor(XmlGlobStream xmlMoStream, IntegerField field) {
                    this.xmlMoStream = xmlMoStream;
                    this.field = field;
                }

                public Integer getInteger() {
                    return xmlMoStream.current.get(field);
                }

                public int getValue(int valueIfNull) {
                    Integer value = getInteger();
                    return value == null ? valueIfNull : value;
                }

                public boolean wasNull() {
                    return getInteger() == null;
                }

                public Object getObjectValue() {
                    return getInteger();
                }
            }

            private class XmlLongAccessor implements LongAccessor {
                private XmlGlobStream xmlMoStream;
                private LongField field;

                public XmlLongAccessor(XmlGlobStream xmlMoStream, LongField field) {
                    this.xmlMoStream = xmlMoStream;
                    this.field = field;
                }

                public Long getLong() {
                    return xmlMoStream.current.get(field);
                }

                public long getValue(long valueIfNull) {
                    Long value = getLong();
                    return value == null ? valueIfNull : value;
                }

                public boolean wasNull() {
                    return getLong() == null;
                }

                public Object getObjectValue() {
                    return getLong();
                }
            }
        }

    }

}
