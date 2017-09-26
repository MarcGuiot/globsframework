package org.globsframework.xml;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobModel;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.links.FieldMappingFunction;
import org.globsframework.metamodel.links.Link;
import org.globsframework.metamodel.utils.GlobTypeUtils;
import org.globsframework.model.*;
import org.globsframework.saxstack.parser.*;
import org.globsframework.saxstack.utils.XmlUtils;
import org.globsframework.utils.exceptions.*;
import org.xml.sax.Attributes;

import java.io.Reader;

public class XmlGlobParser {
   private GlobModel model;
   private boolean ignoreError;
   private GlobRepository repository;

   public static void parse(GlobModel model, GlobRepository repository, Reader reader, String rootTag) {
      XmlGlobParser parser = new XmlGlobParser(model, repository, false);
      parser.parse(reader, rootTag);
   }

   public static void parseIgnoreError(GlobModel model, GlobRepository repository, Reader reader, String rootTag) {
      XmlGlobParser parser = new XmlGlobParser(model, repository, true);
      parser.parse(reader, rootTag);
   }

   private XmlGlobParser(GlobModel model, GlobRepository repository, boolean ignoreError) {
      this.repository = repository;
      this.model = model;
      this.ignoreError = ignoreError;
   }

   private void parse(Reader reader, String rootTag) {
      repository.startChangeSet();
      try {
         SaxStackParser.parse(XmlUtils.getXmlReader(), new XmlBootstrapNode(new RootProxyNode(), rootTag), reader);
      }
      finally {
         repository.completeChangeSet();
      }
   }

   private class RootProxyNode extends DefaultXmlNode {
      private FieldConverter fieldConverter = new FieldConverter(ignoreError);
      private Glob parent;

      public RootProxyNode() {
      }

      public RootProxyNode(Glob parent) {
         this.parent = parent;
      }

      public XmlNode getSubNode(String childName, Attributes xmlAttrs, String uri, String fullName) throws ExceptionHolder {
         try {
            Glob glob = parse(childName, xmlAttrs);
            return new RootProxyNode(glob);
         }
         catch (RuntimeException e) {
            throw e;
         }
         catch (Exception e) {
            throw new ExceptionHolder(e);
         }
      }

      private Glob parse(String childName, Attributes xmlAttrs) throws Exception {
         try {
            GlobType globType = model.getType(childName);
            FieldValuesBuilder builder = FieldValuesBuilder.init();
            if (parent != null) {
               processParent(globType, builder);
            }
            processAttributes(builder, xmlAttrs, globType);

            if (hasUnsetIntegerKey(globType, builder)) {
               return repository.create(globType, builder.toArray());
            }
            return repository.findOrCreate(KeyBuilder.createFromValues(globType, builder.get()), builder.toArray());
         }
         catch (ItemNotFound found) {
            if (ignoreError) {
               return null;
            }
            else {
               throw found;
            }
         }
         catch (MissingInfo info) {
            if (ignoreError) {
               return null;
            }
            else {
               throw info;
            }
         }
         catch (ItemAlreadyExists exists) {
            if (ignoreError) {
               return null;
            }
            else {
               throw exists;
            }
         }
      }

      private boolean hasUnsetIntegerKey(GlobType type, FieldValuesBuilder builder) {
         Field[] keyFields = type.getKeyFields();
         if (keyFields.length == 1) {
            Field keyField = keyFields[0];
            if (IntegerField.class.isInstance(keyField)) {
               IntegerField integerField = (IntegerField)keyField;
               if (!builder.contains(integerField)) {
                  return true;
               }
            }
         }
         return false;
      }

      private void processParent(GlobType globType, final FieldValuesBuilder builder) {
         Link linkToUse = null;
         for (Link link : model.getLinkModel().getLinks(globType)) {
            if (link.getTargetType().equals(parent.getType())) {
               if (linkToUse != null) {
                  throw new ItemAmbiguity("More than one Link from " + globType.getName() + " to " +
                                          parent.getType().getName() + " - XML containment cannot be used");
               }
               linkToUse = link;
            }
         }
         if (linkToUse == null) {
            throw new ItemNotFound("There are no links from " + globType.getName() + " to " +
                                   parent.getType().getName() + " - XML containment cannot be used");
         }
         linkToUse.apply(new FieldMappingFunction() {
            public void process(Field sourceField, Field targetField) {
               builder.setValue(sourceField, parent.getValue(targetField));
            }
         });
      }

      private void processAttributes(FieldValuesBuilder fieldValuesBuilder,
                                     Attributes xmlAttrs,
                                     GlobType globType) {
         int length = xmlAttrs.getLength();
         for (int i = 0; i < length; i++) {
            String xmlAttrName = xmlAttrs.getQName(i);
            String xmlValue = xmlAttrs.getValue(i);

            Field field = globType.findField(xmlAttrName);
            if (field != null) {
               processFieldValue(fieldValuesBuilder, field, xmlValue);
               continue;
            }

            Link link = model.getLinkModel().getLink(globType, xmlAttrName);
            if (link != null) {
               processLinkValue(link, xmlValue, fieldValuesBuilder);
               continue;
            }

            Link linkField = findLinkFieldByTargetName(globType, xmlAttrName);
            if (linkField != null) {
               if(!linkField.apply(new FieldMappingFunction() {
                  boolean hasLinkId;
                  public void process(Field sourceField, Field targetField) {
                     if (xmlAttrs.getIndex(sourceField.getName()) >= 0){
                        hasLinkId = true;
                     }
                  }
               }).hasLinkId) {
                  processLinkValue(linkField, xmlValue, fieldValuesBuilder);
               }
               continue;
            }

            if (ignoreError) {
               continue;
            }

            throw new InvalidParameter(
               "Unknown field or link '" + xmlAttrName + "' for type '" + globType.getName() + "'");
         }
      }

      private void processFieldValue(FieldValuesBuilder fieldValuesBuilder, Field field, String xmlValue) {
         fieldValuesBuilder.setValue(field, fieldConverter.toObject(field, xmlValue));
      }

      private void processLinkValue(Link link,
                                    final String xmlValue,
                                    final FieldValuesBuilder fieldValuesBuilder) {
         final GlobType targetType = link.getTargetType();

         final Field namingField = GlobTypeUtils.findNamingField(targetType);
         if (namingField == null) {
            throw new InvalidParameter("Target type '" + targetType.getName() +
                                       "' for link '" + link.getName() + "' has no naming field");
         }
         final Glob targetGlob =
            repository.findUnique(targetType, new FieldValue(namingField, xmlValue));
         if (targetGlob == null) {
            throw new InvalidParameter("No target of type '" + targetType.getName() + "' found with "
                                       + namingField.getName() + "=" + xmlValue + " for link " + link);
         }

         link.apply((sourceField, targetField) -> fieldValuesBuilder.setValue(sourceField, targetGlob.getValue(targetField)));
      }

      private Link findLinkFieldByTargetName(GlobType globType, String xmlName) {
         if (!xmlName.endsWith("Name")) {
            return null;
         }
         String attrName = xmlName.substring(0, xmlName.length() - "Name".length());

         Link link = model.getLinkModel().getLink(globType, attrName);
         if (link != null) {
            Field namingField = GlobTypeUtils.findNamingField(link.getTargetType());
            if (namingField != null) {
               return link;
            }
         }
         return null;

//       Link[] links = model.getLinkModel().getLinks(globType);
//       for (Link link : links) {
//          if (link instanceof DirectSingleLink) {
//             DirectSingleLink singleLink = (DirectSingleLink)link;
//             Field namingField = GlobTypeUtils.findNamingField(singleLink.getTargetType());
//             if (namingField != null) {
//                String targetName = singleLink.getName() + "Name";
//                if (xmlName.equals(targetName)) {
//                   return singleLink;
//                }
//             }
//          }
//      }
//      return null;
      }
   }
}
