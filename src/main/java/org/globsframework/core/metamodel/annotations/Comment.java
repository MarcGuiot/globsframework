package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.GlobTypeBuilderFactory;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

public class Comment {
    public static final GlobType TYPE;

    public static final StringField VALUE;

    @InitUniqueKey
    public static final Key UNIQUE_KEY;

    public static Glob create(Comment_ comment) {
        return TYPE.instantiate().set(VALUE, comment.value());
    }

    static {
        GlobTypeBuilder typeBuilder = GlobTypeBuilderFactory.create("Comment");
        TYPE = typeBuilder.unCompleteType();
        VALUE = typeBuilder.declareStringField("VALUE");
        typeBuilder.complete();
        UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> create((Comment_) annotation));

//        GlobTypeLoader loader = GlobTypeLoaderFactory.create(Comment.class, "Comment");
//        loader.register(GlobCreateFromAnnotation.class, annotation -> create((Comment_) annotation));
//        loader.load();
    }
}
