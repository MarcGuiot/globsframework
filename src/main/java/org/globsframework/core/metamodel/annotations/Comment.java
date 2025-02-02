package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.*;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

public class Comment {
    public static GlobType TYPE;

    public static StringField VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(Comment_ comment) {
        return TYPE.instantiate().set(VALUE, comment.value());
    }

    static {
        GlobTypeBuilder typeBuilder = GlobTypeBuilderFactory.create("Comment");
        TYPE = typeBuilder.unCompleteType();
        VALUE = typeBuilder.declareStringField("VALUE");
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> create((Comment_) annotation));
        typeBuilder.complete();
        UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);

//        GlobTypeLoader loader = GlobTypeLoaderFactory.create(Comment.class, "Comment");
//        loader.register(GlobCreateFromAnnotation.class, annotation -> create((Comment_) annotation));
//        loader.load();
    }
}
