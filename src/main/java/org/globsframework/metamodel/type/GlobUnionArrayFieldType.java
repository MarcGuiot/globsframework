package org.globsframework.metamodel.type;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.annotations.IsTargetType_;
import org.globsframework.metamodel.annotations.Targets;
import org.globsframework.metamodel.fields.GlobArrayUnionField;
import org.globsframework.metamodel.fields.StringArrayField;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.model.MutableGlob;

public class GlobUnionArrayFieldType {
    public static GlobType TYPE;

    public static StringField name;

    @IsTargetType_()
    public static StringArrayField targetTypes;

    @Targets({})
    public static GlobArrayUnionField annotations;

    static {
        GlobTypeLoaderFactory.create(GlobUnionArrayFieldType.class).load();
    }

    public static MutableGlob create(String name) {
        return TYPE.instantiate()
                .set(GlobUnionArrayFieldType.name, name);
    }
}
