package org.globsframework.metamodel.type;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.annotations.*;
import org.globsframework.metamodel.fields.GlobArrayUnionField;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.model.MutableGlob;

public class StringFieldType {
    public static GlobType TYPE;

    public static StringField name;

    @Targets({KeyAnnotationType.class, IsTargetType.class, MaxSizeType.class, MultiLineTextType.class, RequiredAnnotationType.class,
            NamingFieldAnnotationType.class, FieldNameAnnotationType.class, EnumAnnotationType.class})
    public static GlobArrayUnionField annotations;

    public static MutableGlob create(String name) {
        return TYPE.instantiate()
                .set(StringFieldType.name, name);
    }

    static {
        GlobTypeLoaderFactory.create(StringFieldType.class).load();
    }
}
