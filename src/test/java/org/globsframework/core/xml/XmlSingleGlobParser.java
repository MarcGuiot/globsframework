package org.globsframework.core.xml;

import org.globsframework.core.metamodel.GlobModel;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.utils.exceptions.InvalidParameter;
import org.xml.sax.Attributes;

public class XmlSingleGlobParser {

    private XmlSingleGlobParser() {
    }

    public interface GlobAdder {
        void add(MutableGlob mo);
    }

    public static void parse(String tagName,
                             Attributes xmlAttrs,
                             GlobModel globModel,
                             GlobAdder globAdder) throws Exception {
        GlobType globType = globModel.getType(tagName);
        MutableGlob glob = globType.instantiate();
        processFields(glob, xmlAttrs, globType);
        globAdder.add(glob);
    }

    private static void processFields(MutableGlob glob, Attributes xmlAttrs, GlobType globType) {
        FieldConverter fieldConverter = new FieldConverter();
        int length = xmlAttrs.getLength();
        for (int i = 0; i < length; i++) {
            String name = xmlAttrs.getQName(i);
            String xmlValue = xmlAttrs.getValue(i);
            Field field = globType.findField(name);
            if (field == null) {
                throw new InvalidParameter("Unknown field '" + name + "' for type '" + globType.getName() + "'");
            }
            glob.setValue(field, fieldConverter.toObject(field, xmlValue));
        }
    }
}
