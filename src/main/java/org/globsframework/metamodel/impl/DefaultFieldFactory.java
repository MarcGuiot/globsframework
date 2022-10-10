package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.Annotations;
import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.annotations.DefaultFieldValueType;
import org.globsframework.metamodel.fields.impl.*;
import org.globsframework.metamodel.index.MultiFieldNotUniqueIndex;
import org.globsframework.metamodel.index.MultiFieldUniqueIndex;
import org.globsframework.metamodel.index.NotUniqueIndex;
import org.globsframework.metamodel.index.UniqueIndex;
import org.globsframework.metamodel.index.impl.DefaultMultiFieldNotUniqueIndex;
import org.globsframework.metamodel.index.impl.DefaultMultiFieldUniqueIndex;
import org.globsframework.metamodel.index.impl.DefaultNotUniqueIndex;
import org.globsframework.metamodel.index.impl.DefaultUniqueIndex;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

import java.math.BigDecimal;
import java.util.Collection;

public class DefaultFieldFactory {
    private DefaultGlobType type;

    public DefaultFieldFactory(DefaultGlobType type) {
        this.type = type;
    }

    public DefaultIntegerField addInteger(String name,
                                          boolean isKeyField,
                                          int keyIndex, int index,
                                          Integer defaultValue) {
        return add(new DefaultIntegerField(name, type, index, isKeyField, keyIndex, defaultValue), isKeyField);
    }

    public DefaultIntegerArrayField addIntegerArray(String name,
                                                    boolean isKeyField,
                                                    int keyIndex, int index) {
        return add(new DefaultIntegerArrayField(name, type, index, isKeyField, keyIndex, null), false);
    }

    public DefaultBooleanArrayField addBooleanArray(String name,
                                                    boolean isKeyField,
                                                    int keyIndex, int index) {
        return add(new DefaultBooleanArrayField(name, type, index, isKeyField, keyIndex, null), false);
    }

    public DefaultLongField addLong(String name,
                                    boolean isKeyField,
                                    int keyIndex, int index,
                                    Long defaultValue) {
        return add(new DefaultLongField(name, type, index, isKeyField, keyIndex, defaultValue), isKeyField);
    }

    public DefaultLongArrayField addLongArray(String name,
                                              boolean isKeyField,
                                              int keyIndex, int index) {
        return add(new DefaultLongArrayField(name, type, index, isKeyField, keyIndex, null), false);
    }

    public DefaultDoubleField addDouble(String name,
                                        boolean isKeyField,
                                        final int keyIndex, int index,
                                        Double defaultValue) {
        return add(new DefaultDoubleField(name, type, index, isKeyField, keyIndex, defaultValue), isKeyField);
    }

    public DefaultDoubleArrayField addDoubleArray(String name,
                                                  boolean isKeyField,
                                                  int keyIndex, int index) {
        return add(new DefaultDoubleArrayField(name, type, index, isKeyField, keyIndex, null), false);
    }

    public DefaultStringField addString(String name,
                                        boolean isKeyField,
                                        int keyIndex, int index,
                                        String defaultValue) {
        return add(new DefaultStringField(name, type, index, isKeyField, keyIndex, defaultValue), isKeyField);
    }

    public DefaultStringArrayField addStringArray(String name,
                                                  boolean isKeyField,
                                                  int keyIndex, int index) {
        return add(new DefaultStringArrayField(name, type, index, isKeyField, keyIndex, null), false);
    }

    public DefaultBooleanField addBoolean(String name,
                                          boolean isKeyField,
                                          final int keyIndex, int index,
                                          Boolean defaultValue) {
        return add(new DefaultBooleanField(name, type, index, isKeyField, keyIndex, defaultValue), isKeyField);
    }

    public DefaultBigDecimalField addBigDecimal(String name,
                                                boolean isKeyField,
                                                int keyIndex, int index,
                                                BigDecimal defaultValue) {
        return add(new DefaultBigDecimalField(name, type, index, isKeyField, keyIndex, defaultValue), false);
    }

    public DefaultBigDecimalArrayField addBigDecimalArray(String name,
                                                          boolean isKeyField,
                                                          int keyIndex, int index) {
        return add(new DefaultBigDecimalArrayField(name, type, index, isKeyField, keyIndex, null), false);
    }

    public DefaultDateTimeField addDateTime(String name,
                                            boolean isKeyField,
                                            int keyIndex, int index) {
        return add(new DefaultDateTimeField(name, type, index, isKeyField, keyIndex, null), isKeyField);
    }

    public DefaultDateField addDate(String name,
                                    boolean isKeyField,
                                    int keyIndex, int index) {
        return add(new DefaultDateField(name, type, index, isKeyField, keyIndex, null), isKeyField);
    }

    public DefaultBlobField addBlob(String name, int index) {
        return add(new DefaultBlobField(name, type, index), false);
    }

    public DefaultGlobField addGlob(String name, GlobType globType, boolean isKeyField,
                                    int keyIndex, int index) {
        return add(new DefaultGlobField(name, type, globType, index, isKeyField, keyIndex), isKeyField);
    }

    public DefaultGlobArrayField addGlobArray(String name, GlobType globType, boolean isKeyField,
                                              int keyIndex, int index) {
        return add(new DefaultGlobArrayField(name, type, globType, index, isKeyField, keyIndex), isKeyField);
    }

    public DefaultGlobUnionField addGlobUnion(String fieldName, Collection<GlobType> types, int index) {
        return add(new DefaultGlobUnionField(fieldName, type, types, index, false, 0), false);
    }

    public DefaultGlobUnionArrayField addGlobArrayUnion(String fieldName, Collection<GlobType> types, int index) {
        return add(new DefaultGlobUnionArrayField(fieldName, type, types, index, false, 0), false);
    }


    private <T extends AbstractField> T add(T field, boolean isKeyField) {
        type.addField(field);
        if (isKeyField) {
            type.addKey(field);
        }
        return field;
    }

    public UniqueIndex addUniqueIndex(String name) {
        return new DefaultUniqueIndex(name);
    }

    public NotUniqueIndex addNotUniqueIndex(String name) {
        return new DefaultNotUniqueIndex(name);
    }

    public MultiFieldNotUniqueIndex addMultiFieldNotUniqueIndex(String name) {
        return new DefaultMultiFieldNotUniqueIndex(name);
    }

    public MultiFieldUniqueIndex addMultiFieldUniqueIndex(String name) {
        return new DefaultMultiFieldUniqueIndex(name);
    }

    //    private static class DefaultLinkField extends AbstractField implements LinkField {
//        private GlobType targetType;
//        private IntegerField targetKeyField;
//
//        private DefaultLinkField(String name, GlobType globType,
//                                 Annotations annotations) {
//            super(name, globType, Integer.class, annotations);
//
//            Glob annotation = annotations.findAnnotation(TargetAnnotationType.KEY);
//            if (annotation == null) {
//                throw new MissingInfo("Annotation " + Target.class.getName() +
//                        " must be specified for LinkField '" + name +
//                        "' for type: " + globType.getName());
//            }
//            Class targetClass = ((Target) annotations.get(Target.class)).value();
//            try {
//                targetType = GlobTypeUtils.getType(targetClass);
//            } catch (InvalidParameter e) {
//                throw new InvalidParameter("LinkField '" + name + "' in type '" + globType.getName() +
//                        "' cannot reference target class '" + targetClass.getName() + "' because "
//                        +
//                        "it does not define a Glob type");
//            }
//            if (targetType == null) {
//                throw new InvalidParameter("Is GlobTypeLoader.init() of " + targetClass + " called from static init?");
//            }
//            Field[] keyFields = targetType.getKeyFields();
//            if (keyFields.length != 1) {
//                throw new InvalidParameter("LinkField '" + name + "' in type '" + globType.getName() +
//                        "' cannot reference target type '" + targetType.getName() +
//                        "' because it uses a composite key");
//            }
//            Field field = keyFields[0];
//            if (!(field instanceof IntegerField)) {
//                throw new InvalidParameter("LinkField '" + name + "' in type '" + globType.getName() +
//                        "' cannot reference target type '" + targetType.getName() +
//                        "' because it does not use an integer key");
//            }
//            targetKeyField = (IntegerField) field;
//            setDefaultValue(computeDefaultValue(this, annotations, DefaultInteger.class));
//
//        }
//
//        public IntegerField getTargetKeyField() {
//            return targetKeyField;
//        }
//
//        public void visit(FieldVisitor visitor) throws Exception {
//            visitor.visitLink(this);
//        }
//
//        public void safeVisit(FieldVisitor visitor) {
//            try {
//                visitor.visitLink(this);
//            } catch (RuntimeException e) {
//                throw new RuntimeException("On " + this, e);
//            } catch (Exception e) {
//                throw new UnexpectedApplicationState("On " + this, e);
//            }
//        }
//
//        public void safeVisit(FieldValueVisitor visitor, Object value) {
//            try {
//                visitor.visitInteger(this, (Integer) value);
//            } catch (RuntimeException e) {
//                throw new RuntimeException("On " + this, e);
//            } catch (Exception e) {
//                throw new UnexpectedApplicationState("On " + this, e);
//            }
//        }
//
//        public GlobType getSourceType() {
//            return getGlobType();
//        }
//
//        public GlobType getTargetType() {
//            return targetType;
//        }
//
//        public void apply(FieldMappingFunctor functor) {
//            functor.process(this, targetKeyField);
//        }
//
//        public void visit(LinkVisitor linkVisitor) {
//            linkVisitor.visitLink(this);
//        }
//
//        public String toString() {
//            return DefaultLink.toString(getName(), getSourceType(), getTargetType());
//        }
//    }


    private static Object computeDefaultValue(Field field,
                                              Annotations annotations,
                                              Key targetAnnotationClass) {
        Glob glob = annotations.findAnnotation(targetAnnotationClass);
        if (glob != null) {
            Field fieldWithValue = glob.getType().getFieldWithAnnotation(DefaultFieldValueType.key);
            return glob.getValue(fieldWithValue);
        }
//        for (Class annotationClass : defaultValuesAnnotations) {
//            Annotation annotation = annotations.get(annotationClass);
//            if (annotation == null) {
//                continue;
//            }
//            if (!annotationClass.equals(targetAnnotationClass)) {
//                throw new InvalidParameter("Field " + field.getGlobType().getName() + "." + field.getName() +
//                        " should declare a default value with annotation @" +
//                        targetAnnotationClass.getSimpleName() +
//                        " instead of @" + annotationClass.getSimpleName());
//            }
//            if (targetAnnotationClass.equals(DefaultDate.class)) {
//                return new Date();
//            }
//            try {
//                return annotationClass.getMethod("value").invoke(annotation);
//            } catch (Exception e) {
//                System.out.println("on type : '" + field.getGlobType().getName() + "'; on field '" + field.getName() + "'");
//                e.printStackTrace();
//                throw new InvalidParameter("Cannot determine default value for field: " + field, e);
//            }
//        }
        return null;
    }
}
