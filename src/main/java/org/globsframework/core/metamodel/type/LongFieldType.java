package org.globsframework.core.metamodel.type;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.annotations.Targets;
import org.globsframework.core.metamodel.fields.GlobArrayUnionField;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.MutableGlob;

public class LongFieldType {
    public static GlobType TYPE;

    public static StringField name;

    @Targets({})
    public static GlobArrayUnionField annotations;

    static {
        GlobTypeLoaderFactory.create(LongFieldType.class).load();
    }

    public static MutableGlob create(String name) {
        return TYPE.instantiate()
                .set(LongFieldType.name, name);
    }
}
