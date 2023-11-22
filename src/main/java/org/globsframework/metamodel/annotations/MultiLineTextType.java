package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

import java.lang.annotation.Annotation;

public class MultiLineTextType {
    public static GlobType TYPE;

    public static StringField MIME_TYPE;

    public static IntegerField MAX_SIZE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    static {
        GlobTypeLoaderFactory.create(MultiLineTextType.class)
            .register(GlobCreateFromAnnotation.class, MultiLineTextType::create)
            .load();
    }

    private static Glob create(Annotation annotation) {
        return TYPE.instantiate()
                .set(MIME_TYPE, ((MultiLineText)annotation).mimeType())
                .set(MAX_SIZE, ((MultiLineText)annotation).maxSize())
                ;
    }
}
