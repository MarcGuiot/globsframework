package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

public class CommentType {
    public static GlobType TYPE;

    public static StringField VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(Comment_ comment) {
        return TYPE.instantiate().set(VALUE, comment.value());
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(CommentType.class, "Comment");
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((Comment_) annotation));
        loader.load();
    }
}
