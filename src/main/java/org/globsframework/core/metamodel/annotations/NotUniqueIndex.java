package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.metamodel.impl.DefaultGlobTypeBuilder;

public class NotUniqueIndex {
    public static final GlobType TYPE;

    public static final StringField NAME;

    static {
        GlobTypeBuilder typeBuilder = new DefaultGlobTypeBuilder("NotUniqueIndex");
        TYPE = typeBuilder.unCompleteType();
        NAME = typeBuilder.declareStringField("NAME");
        typeBuilder.complete();

//        GlobTypeLoaderFactory.create(NotUniqueIndex.class, "NotUniqueIndex")
//                .load();
    }
}
