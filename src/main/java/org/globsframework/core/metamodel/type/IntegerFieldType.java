package org.globsframework.core.metamodel.type;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.annotations.Targets;
import org.globsframework.core.metamodel.fields.GlobArrayUnionField;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.metamodel.impl.DefaultGlobTypeBuilder;
import org.globsframework.core.model.MutableGlob;

import java.util.List;

public class IntegerFieldType {
    public static final GlobType TYPE;

    public static final StringField name;

    @Targets({})
    public static final GlobArrayUnionField annotations;

    static {
        GlobTypeBuilder typeBuilder = new DefaultGlobTypeBuilder("Integer");
        TYPE = typeBuilder.unCompleteType();
        name = typeBuilder.declareStringField(ConstantsName.NAME);
        annotations = typeBuilder.declareGlobUnionArrayField(ConstantsName.ANNOTATIONS, List.of());
        typeBuilder.complete();
//        GlobTypeLoaderFactory.create(IntegerFieldType.class).load();
    }

    public static MutableGlob create(String name) {
        return TYPE.instantiate()
                .set(IntegerFieldType.name, name);
    }
}
