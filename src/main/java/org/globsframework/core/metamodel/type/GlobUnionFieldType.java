package org.globsframework.core.metamodel.type;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.annotations.IsTarget_;
import org.globsframework.core.metamodel.annotations.Targets;
import org.globsframework.core.metamodel.fields.GlobArrayUnionField;
import org.globsframework.core.metamodel.fields.StringArrayField;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.MutableGlob;

public class GlobUnionFieldType {
    public static GlobType TYPE;

    public static StringField name;

    @IsTarget_
    public static StringArrayField targetTypes;

    @Targets({})
    public static GlobArrayUnionField annotations;

    static {
        GlobTypeLoaderFactory.create(GlobUnionFieldType.class).load();
    }

    public static MutableGlob create(String name) {
        return TYPE.instantiate().set(GlobUnionFieldType.name, name);
    }
}
