package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.*;
import org.globsframework.core.metamodel.fields.*;

public class DummyObjectWithDefaultValues {
    public static GlobType TYPE;

    @KeyField_
    @AutoIncrement_
    public static IntegerField ID;

    @DefaultInteger_(7)
    public static IntegerField INTEGER;

    @DefaultBigDecimal_("1.61803398875")
    public static BigDecimalField BIG_DECIMAL;

    @DefaultLong_(5L)
    public static LongField LONG;

    @DefaultDouble_(3.14159265)
    public static DoubleField DOUBLE;

    @DefaultBoolean_(true)
    public static BooleanField BOOLEAN;

    @Target(DummyObject.class)
    public static IntegerField LINK;

    @DefaultString_("Hello")
    public static StringField STRING;

    static {
        GlobTypeLoaderFactory.create(DummyObjectWithDefaultValues.class, true).load();
    }
}
