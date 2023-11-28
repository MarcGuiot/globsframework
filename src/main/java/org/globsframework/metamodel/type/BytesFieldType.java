package org.globsframework.metamodel.type;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.annotations.Targets;
import org.globsframework.metamodel.fields.GlobArrayUnionField;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.model.MutableGlob;

public class BytesFieldType {
    public static GlobType TYPE;

    public static StringField name;

    @Targets({})
    public static GlobArrayUnionField annotations;

    static {
        GlobTypeLoaderFactory.create(BytesFieldType.class).load();
    }

    public static MutableGlob create(String name) {
        return TYPE.instantiate()
                .set(BytesFieldType.name, name);
    }
}
