package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.*;
import org.globsframework.core.model.FieldValuesAccessor;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.utils.Strings;

import java.nio.charset.Charset;

public class MaxSize {
    static public GlobType TYPE;

    static public IntegerField VALUE;

    static public BooleanField ALLOW_TRUNCATE;

    static public StringField CHARSET;

    @InitUniqueKey
    static public Key KEY;

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(MaxSize.class, "MaxSize");
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((MaxSize_) annotation))
                .load();
    }

    static public String cut(Field field, FieldValuesAccessor value) {
        return value.getOpt(field.asStringField())
                .map(s -> cut(field, s))
                .orElse(null);
    }

    static public String cut(Field field, String value) {
        Glob annotation = field.findAnnotation(KEY);
        if (annotation != null && Strings.isNotEmpty(value) && annotation.get(VALUE) > 0) {
            Charset charsetName = Charset.forName(annotation.get(CHARSET));
            if (value.getBytes(charsetName).length > annotation.get(VALUE)) {
                if (annotation.get(ALLOW_TRUNCATE, false)) {
                    String substring = value.substring(0, Math.min(value.length(), annotation.get(VALUE)));
                    while (substring.getBytes(charsetName).length > annotation.get(VALUE)) {
                        substring = substring.substring(0, substring.length() - 1);
                    }
                    return substring;
                } else {
                    throw new StringToLongException(field.getFullName() + " => " + value);
                }
            } else {
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

    public static Glob create(MaxSize_ size) {
        return TYPE.instantiate().set(VALUE, size.value())
                .set(ALLOW_TRUNCATE, size.allow_truncate())
                .set(CHARSET, size.charSet());
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

    static class StringToLongException extends RuntimeException {
        public StringToLongException(String value) {
            super(value);
        }
    }
}
