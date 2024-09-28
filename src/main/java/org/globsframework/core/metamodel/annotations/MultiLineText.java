package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

import java.lang.annotation.Annotation;

public class MultiLineText {
    public static GlobType TYPE;

    public static StringField mimeType;

    public static IntegerField maxSize;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    static {
        GlobTypeLoaderFactory.create(MultiLineText.class, "MultiLineText")
                .register(GlobCreateFromAnnotation.class, MultiLineText::create)
                .load();
    }

    private static Glob create(Annotation annotation) {
        return TYPE.instantiate()
                .set(mimeType, ((MultiLineText_) annotation).mimeType())
                .set(maxSize, ((MultiLineText_) annotation).maxSize())
                ;
    }
}
