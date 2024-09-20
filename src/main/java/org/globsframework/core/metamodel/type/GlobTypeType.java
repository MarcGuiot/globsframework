package org.globsframework.core.metamodel.type;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.annotations.Targets;
import org.globsframework.core.metamodel.fields.GlobArrayUnionField;
import org.globsframework.core.metamodel.fields.StringField;

public class GlobTypeType {
    public static GlobType TYPE;

    public static StringField kind;

    @Targets({
            BooleanFieldType.class, BooleanArrayFieldType.class,
            StringFieldType.class, StringArrayFieldType.class,
            DoubleFieldType.class, DoubleArrayFieldType.class,
            IntegerFieldType.class, IntegerArrayFieldType.class,
            LongFieldType.class, LongArrayFieldType.class,
            DateFieldType.class, DateTimeFieldType.class,
            BytesFieldType.class,
            BigDecimalFieldType.class, BigDecimalArrayFieldType.class,
            GlobFieldType.class, GlobArrayFieldType.class,
            GlobUnionFieldType.class, GlobUnionArrayFieldType.class
    })
    public static GlobArrayUnionField fields;

    @Targets({})
    public static GlobArrayUnionField annotations;

    static {
        GlobTypeLoaderFactory.create(GlobTypeType.class).load();
    }
}
