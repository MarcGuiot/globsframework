package org.globsframework.xml.tests;

import org.globsframework.saxstack.writer.RootXmlTag;
import org.globsframework.saxstack.writer.XmlTag;

import java.io.IOException;
import java.io.StringWriter;

public class XmlTestLogger {
    private StringWriter writer;
    private LoggerTag logTag;

    public XmlTestLogger() {
        reset();
    }

    public final LoggerTag log(String tag) throws IOException {
        return logTag.createChildTag(tag);
    }

    public final void reset() {
        writer = new StringWriter();
        RootXmlTag rootXmlTag = new RootXmlTag(writer);
        try {
            logTag = new LoggerTag(rootXmlTag.createChildTag("log"));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void assertEquals(String expectedLog) {
        String actual = writer.toString();
        XmlTestUtils.assertEquivalent(expectedLog, "<log".equals(actual) ? "<log/>" : actual + "</log>");
        reset();
    }

    public final void assertEmpty() {
        assertEquals("<log/>");
    }

    public static class LoggerTag {
        private XmlTag xmlTag;

        public LoggerTag(XmlTag xmlTag) {
            this.xmlTag = xmlTag;
        }

        public LoggerTag addAttribute(String string, int i) {
            try {
                return new LoggerTag(xmlTag.addAttribute(string, i));
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String getTagName() {
            return xmlTag.getTagName();
        }

        public LoggerTag addAttribute(String string, Object object) {
            try {
                return new LoggerTag(xmlTag.addAttribute(string, object));
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public LoggerTag createChildTag(String string) {
            try {
                return new LoggerTag(xmlTag.createChildTag(string));
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public LoggerTag end() throws IOException {
            return new LoggerTag(xmlTag.end());
        }
    }
}
