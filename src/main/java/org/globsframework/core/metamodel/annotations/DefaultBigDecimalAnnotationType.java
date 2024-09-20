package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoader;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.fields.BigDecimalField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

import java.math.BigDecimal;

public class DefaultBigDecimalAnnotationType {

    public static GlobType DESC;

    @DefaultFieldValue
    public static BigDecimalField DEFAULT_VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(String defaultBigDecimal) {
        return DESC.instantiate().set(DEFAULT_VALUE, new BigDecimal(defaultBigDecimal));
    }

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultBigDecimalAnnotationType.class);
        loader.register(GlobCreateFromAnnotation.class, annotation -> create(((DefaultBigDecimal) annotation).value()))
                .load();
    }
}
