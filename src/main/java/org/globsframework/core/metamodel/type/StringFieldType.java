package org.globsframework.core.metamodel.type;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.annotations.*;
import org.globsframework.core.metamodel.fields.GlobArrayUnionField;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.MutableGlob;

public class StringFieldType {
    public static GlobType TYPE;

    public static StringField name;

    @Targets({KeyField.class, IsTarget.class, MaxSize.class, MultiLineText.class, Required.class,
            NamingField.class, FieldName.class, EnumAnnotation.class})
    public static GlobArrayUnionField annotations;

    public static MutableGlob create(String name) {
        return TYPE.instantiate()
                .set(StringFieldType.name, name);
    }

    static {
        GlobTypeLoaderFactory.create(StringFieldType.class).load();
    }
}
