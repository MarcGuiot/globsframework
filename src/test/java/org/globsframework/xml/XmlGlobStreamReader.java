package org.globsframework.xml;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobModel;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.Glob;
import org.globsframework.model.MutableGlob;
import org.globsframework.saxstack.parser.DefaultXmlNode;
import org.globsframework.saxstack.parser.ExceptionHolder;
import org.globsframework.saxstack.parser.SaxStackParser;
import org.globsframework.saxstack.parser.XmlNode;
import org.globsframework.saxstack.utils.XmlUtils;
import org.globsframework.streams.DbStream;
import org.globsframework.streams.accessors.*;
import org.xml.sax.Attributes;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

public class XmlGlobStreamReader {
    private GlobModel globModel;
    private String xml;
    private XmlDbStream xmlMoStream;

    private XmlGlobStreamReader(String xml, final GlobModel globModel) {
        this.xml = xml;
        this.globModel = globModel;
    }

    private DbStream parse() {
        xmlMoStream = new XmlDbStream();
        SaxStackParser.parse(XmlUtils.getXmlReader(), new RootProxyNode(), new StringReader(xml));
        return xmlMoStream;
    }

    private void add(Glob mo) {
        xmlMoStream.add(mo);
    }

    public static DbStream parse(String xml, final GlobModel globModel) {
        return new XmlGlobStreamReader("<root>" + xml + "</root>", globModel).parse();
    }

    private static class XmlDbStream implements DbStream {
        private Set<GlobType> globType = new HashSet<>();
        private List<Glob> globs = new ArrayList<>();
        private Iterator<Glob> iterator;
        private Glob current;
        private Map<Field, Accessor> accessors = new HashMap();

        public XmlDbStream() {
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
            return accessors.keySet();
        }

        public Accessor getAccessor(Field field) {
            return accessors.get(field);
        }

        public void close() {
        }

        public void add(Glob glob) {
            if (globs.isEmpty() || !globType.contains(glob.getType())) {
                globType.add(glob.getType());
                for (Field field : glob.getType().getFields()) {
                    AccessorDataTypeVisitor dataTypeVisitor = new AccessorDataTypeVisitor(this);
                    field.safeAccept(dataTypeVisitor);
                    accessors.put(field, dataTypeVisitor.getAccessor());
                }
            }
            globs.add(glob);
        }

        private static class AccessorDataTypeVisitor extends FieldVisitor.AbstractWithErrorVisitor {
            private XmlDbStream stream;
            private Accessor accessor;

            public AccessorDataTypeVisitor(XmlDbStream stream) {
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

            public void visitDate(DateField field) throws Exception {
                accessor = new XmlDateAccessor(stream, field);
            }

            public void visitDateTime(DateTimeField field) throws Exception {
                accessor = new XmlDateTimeAccessor(stream, field);
            }

            public void visitStringArray(StringArrayField field) throws Exception {
                accessor = new XmlStringArrayAccessor(stream, field);
            }

            public void visitLongArray(LongArrayField field) throws Exception {
                accessor = new XmlLongArrayAccessor(stream, field);
            }

            public Accessor getAccessor() {
                return accessor;
            }


            private static class XmlBlobAccessor implements BlobAccessor {
                private XmlDbStream xmlMoStream;
                private BlobField field;

                public XmlBlobAccessor(XmlDbStream xmlMoStream, BlobField field) {
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
                private XmlDbStream xmlMoStream;
                private StringField field;

                public XmlStringAccessor(XmlDbStream xmlMoStream, StringField field) {
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
                private XmlDbStream xmlMoStream;
                private BooleanField field;

                public XmlBooleanAccessor(XmlDbStream xmlMoStream, BooleanField field) {
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
                private XmlDbStream xmlMoStream;
                private DoubleField field;

                public XmlDoubleAccessor(XmlDbStream xmlMoStream, DoubleField field) {
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
                private XmlDbStream xmlMoStream;
                private IntegerField field;

                public XmlIntegerAccessor(XmlDbStream xmlMoStream, IntegerField field) {
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
                private XmlDbStream xmlMoStream;
                private LongField field;

                public XmlLongAccessor(XmlDbStream xmlMoStream, LongField field) {
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

            private class XmlDateAccessor implements DateAccessor {
                private XmlDbStream xmlMoStream;
                private DateField field;

                public XmlDateAccessor(XmlDbStream xmlMoStream, DateField field) {
                    this.xmlMoStream = xmlMoStream;
                    this.field = field;
                }

                public LocalDate getDate() {
                    return xmlMoStream.current.get(field);
                }

                public boolean wasNull() {
                    return getDate() == null;
                }

                public Object getObjectValue() {
                    return getDate();
                }
            }

            private class XmlDateTimeAccessor implements DateTimeAccessor {
                private XmlDbStream xmlMoStream;
                private DateTimeField field;

                public XmlDateTimeAccessor(XmlDbStream xmlMoStream, DateTimeField field) {
                    this.xmlMoStream = xmlMoStream;
                    this.field = field;
                }

                public ZonedDateTime getDateTime() {
                    return xmlMoStream.current.get(field);
                }

                public boolean wasNull() {
                    return getDateTime() == null;
                }

                public Object getObjectValue() {
                    return getDateTime();
                }
            }

            private class XmlStringArrayAccessor implements StringArrayAccessor {
                private final XmlDbStream xmlMoStream;
                private final StringArrayField field;

                public XmlStringArrayAccessor(XmlDbStream xmlMoStream, StringArrayField field) {
                    this.xmlMoStream = xmlMoStream;
                    this.field = field;
                }

                public String[] getString() {
                    return xmlMoStream.current.get(field);
                }

                public Object getObjectValue() {
                    return getString();
                }
            }

            private class XmlLongArrayAccessor implements LongArrayAccessor {
                private final XmlDbStream xmlMoStream;
                private final LongArrayField field;

                public XmlLongArrayAccessor(XmlDbStream xmlMoStream, LongArrayField field) {
                    this.xmlMoStream = xmlMoStream;
                    this.field = field;
                }

                public long[] getValues() {
                    return xmlMoStream.current.get(field);
                }

                public Object getObjectValue() {
                    return getValues();
                }
            }
        }

    }

    class RootProxyNode extends DefaultXmlNode implements XmlSingleGlobParser.GlobAdder {

        public RootProxyNode() {
        }

        public XmlNode getSubNode(String childName, Attributes xmlAttrs, String uri, String fullName) throws ExceptionHolder {
            try {
                if (childName.equals("root")) {
                    return this;
                } else {
                    XmlSingleGlobParser.parse(childName, xmlAttrs, globModel, this);
                    return this;
                }
            } catch (Exception e) {
                throw new ExceptionHolder(e);
            }
        }

        public void add(MutableGlob mo) {
            XmlGlobStreamReader.this.add(mo);
        }
    }
}
