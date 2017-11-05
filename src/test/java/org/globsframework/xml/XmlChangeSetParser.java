package org.globsframework.xml;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobModel;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.FieldValuesWithPreviousBuilder;
import org.globsframework.model.Key;
import org.globsframework.model.KeyBuilder;
import org.globsframework.model.delta.DefaultChangeSet;
import org.globsframework.model.delta.MutableChangeSet;
import org.globsframework.saxstack.parser.*;
import org.globsframework.saxstack.utils.XmlUtils;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.ItemNotFound;
import org.xml.sax.Attributes;

import java.io.Reader;

public class XmlChangeSetParser {

    private XmlChangeSetParser() {
    }

    public static MutableChangeSet parse(GlobModel model, Reader reader) {
        RootProxyNode rootNode = new RootProxyNode(model);
        SaxStackParser.parse(XmlUtils.getXmlReader(), new XmlBootstrapNode(rootNode, "changes"), reader);
        return rootNode.getChangeSet();
    }

    private static class RootProxyNode extends DefaultXmlNode {
        private FieldConverter fieldConverter = new FieldConverter();
        private MutableChangeSet changeSet = new DefaultChangeSet();
        private GlobModel model;

        private RootProxyNode(GlobModel model) {
            this.model = model;
        }

        public XmlNode getSubNode(String childName, Attributes xmlAttrs, String uri, String fullName) throws ExceptionHolder {
            String typeName = xmlAttrs.getValue("type");
            if (typeName == null) {
                throw new InvalidParameter("Missing attribute 'type' in tag '" + childName + "'");
            }
            GlobType globType = model.getType(typeName);
            FieldValuesWithPreviousBuilder valuesBuilder = FieldValuesWithPreviousBuilder.init(globType);
            final KeyBuilder keyBuilder = KeyBuilder.init(globType);

            processAttributes(keyBuilder, valuesBuilder, xmlAttrs, globType);

            final Key key = keyBuilder.get();
            if ("create".equals(childName)) {
                valuesBuilder.completeForCreate();
                changeSet.processCreation(key, valuesBuilder.get());
            }
            else if ("update".equals(childName)) {
                valuesBuilder.completeForUpdate();
                changeSet.processUpdate(key, valuesBuilder.get());
            }
            else if ("delete".equals(childName)) {
                valuesBuilder.completeForDelete();
                changeSet.processDeletion(key, valuesBuilder.get().getPreviousValues());
            }

            return super.getSubNode(typeName, xmlAttrs, uri, fullName);
        }

        private void processAttributes(KeyBuilder keyBuilder,
                                       FieldValuesWithPreviousBuilder valuesBuilder,
                                       Attributes xmlAttrs,
                                       GlobType globType) {
            int length = xmlAttrs.getLength();
            for (int i = 0; i < length; i++) {
                String xmlAttrName = xmlAttrs.getQName(i);
                String xmlValue = xmlAttrs.getValue(i);

                if ("type".equals(xmlAttrName)) {
                    continue;
                }

                if (xmlAttrName.startsWith("_")) {
                    final String fieldName = xmlAttrName.substring(1);
                    Field field = globType.findField(fieldName);
                    Object value = getValue(globType, xmlAttrName, xmlValue, field, true);
                    valuesBuilder.setPreviousValue(field, value);
                }
                else {
                    Field field = globType.findField(xmlAttrName);
                    Object value = getValue(globType, xmlAttrName, xmlValue, field, false);
                    if (field.isKeyField()) {
                        keyBuilder.set(field, value);
                    }
                    else {
                        valuesBuilder.setValue(field, value);
                    }
                }
            }
        }

        private Object getValue(GlobType globType, String xmlAttrName, String xmlValue, Field field, boolean isPrevious) {
            if (field == null) {
                throw new ItemNotFound(
                    "Unknown field '" + xmlAttrName + "' for type '" + globType.getName() + "'");
            }
            Object value = fieldConverter.toObject(field, xmlValue);
            if (isPrevious && field.isKeyField()) {
                throw new InvalidParameter("Cannot declare previous value for key field '" + field.getName() +
                                           "' on type: " + globType);
            }
            return value;
        }

        public MutableChangeSet getChangeSet() {
            return changeSet;
        }
    }
}
