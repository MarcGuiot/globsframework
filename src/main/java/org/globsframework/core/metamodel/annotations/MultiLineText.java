package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.GlobTypeBuilderFactory;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

import java.lang.annotation.Annotation;

public class MultiLineText {
    public static final GlobType TYPE;

    public static final StringField mimeType;

    public static final IntegerField maxSize;

    @InitUniqueKey
    public static final Key UNIQUE_KEY;

    static {
        GlobTypeBuilder typeBuilder = GlobTypeBuilderFactory.create("MultiLineText");
        TYPE = typeBuilder.unCompleteType();
        mimeType = typeBuilder.declareStringField("MIME_TYPE");
        maxSize = typeBuilder.declareIntegerField("MAX_SIZE");
        typeBuilder.register(GlobCreateFromAnnotation.class, MultiLineText::create);
        typeBuilder.complete();
        UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);
//        GlobTypeLoaderFactory.create(MultiLineText.class, "MultiLineText")
//                .register(GlobCreateFromAnnotation.class, MultiLineText::create)
//                .load();
    }

    private static Glob create(Annotation annotation) {
        return TYPE.instantiate()
                .set(mimeType, ((MultiLineText_) annotation).mimeType())
                .set(maxSize, ((MultiLineText_) annotation).maxSize())
                ;
    }
}
