package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoader;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

public class CommentType {
    public static GlobType TYPE;

    public static StringField VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(Comment_ comment) {
        return TYPE.instantiate().set(VALUE, comment.value());
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(CommentType.class,"Comment");
        loader.register(GlobCreateFromAnnotation.class, annotation -> create((Comment_) annotation));
        loader.load();
    }
}
