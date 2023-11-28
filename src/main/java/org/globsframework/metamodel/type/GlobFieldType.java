package org.globsframework.metamodel.type;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.GlobTypeLoaderFactory;
import org.globsframework.metamodel.annotations.IsTargetType_;
import org.globsframework.metamodel.annotations.Targets;
import org.globsframework.metamodel.fields.GlobArrayUnionField;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.model.MutableGlob;

public class GlobFieldType {
    public static GlobType TYPE;

    public static StringField name;

    @IsTargetType_()
    public static StringField targetType;

    @Targets({})
    public static GlobArrayUnionField annotations;

    static {
        GlobTypeLoaderFactory.create(GlobFieldType.class).load();
    }

    public static MutableGlob create(String name) {
        return GlobFieldType.TYPE.instantiate()
                .set(GlobFieldType.name, name);
    }
}
