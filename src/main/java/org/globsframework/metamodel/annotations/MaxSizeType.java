package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoader;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;
import org.globsframework.model.MutableGlob;
import org.globsframework.utils.Strings;

public class MaxSizeType {
    static public GlobType TYPE;

    @DefaultFieldValue
    static public IntegerField VALUE;

    static public BooleanField ALLOW_TRUNCATE;

    @InitUniqueKey
    static public Key KEY;

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(MaxSizeType.class);
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((MaxSize) annotation))
                .load();
    }

    static class StringToLongException extends RuntimeException {
        public StringToLongException(String value) {
            super(value);
        }
    }

    static public String cut(Field field, String value) {
        Glob annotation = field.findAnnotation(KEY);
        if (annotation != null && Strings.isNotEmpty(value)) {
            if (value.length() > annotation.get(VALUE)) {
                if (annotation.get(ALLOW_TRUNCATE, false)) {
                    return value.substring(0, annotation.get(VALUE));
                }
                else {
                    throw new StringToLongException(field.getFullName() + " => " + value);
                }
            }
            else {
                return value;
            }
        }
        return value;
    }

    static public boolean checkSize(Field field, String value) {
        Glob annotation = field.findAnnotation(KEY);
        if (annotation != null && Strings.isNotEmpty(value)) {
            if (value.length() > annotation.get(VALUE)) {
                return false;
            }
        }
        return true;
    }

    public static Glob create(MaxSize size) {
        return TYPE.instantiate().set(VALUE, size.value())
                .set(ALLOW_TRUNCATE, size.allow_truncate());
    }

    public static MutableGlob deepInPlaceTruncate(MutableGlob glob) {
        if (glob != null) {
            GlobType type = glob.getType();
            for (Field field : type.getFields()) {
                if (field instanceof StringField) {
                    StringField stringField = field.asStringField();
                    glob.set(stringField, cut(field, glob.get(stringField)));
                } else if (field instanceof StringArrayField) {
                    String[] strings = glob.get((StringArrayField) field);
                    if (strings != null) {
                        for (int i = 0; i < strings.length; i++) {
                            strings[i] = cut(field, strings[i]);
                        }
                    }
                } else if (field instanceof GlobField) {
                    deepInPlaceTruncate((MutableGlob) glob.get((GlobField) field));
                } else if (field instanceof GlobArrayField) {
                    Glob[] globs = glob.get(((GlobArrayField) field));
                    for (Glob value : globs) {
                        deepInPlaceTruncate((MutableGlob) value);
                    }
                } else if (field instanceof GlobUnionField) {
                    deepInPlaceTruncate((MutableGlob) glob.get((GlobUnionField) field));
                } else if (field instanceof GlobArrayUnionField) {
                    Glob[] globs = glob.get(((GlobArrayUnionField) field));
                    for (Glob value : globs) {
                        deepInPlaceTruncate((MutableGlob) value);
                    }
                }
            }
        }
        return glob;
    }
}
