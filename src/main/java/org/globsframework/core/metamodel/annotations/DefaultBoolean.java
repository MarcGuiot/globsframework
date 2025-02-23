package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeBuilder;
import org.globsframework.core.metamodel.GlobTypeBuilderFactory;
import org.globsframework.core.metamodel.fields.BooleanField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

public class DefaultBoolean {
    public static final GlobType TYPE;

    public static final BooleanField VALUE;

    @InitUniqueKey
    public static final Key KEY;

    public static Glob create(DefaultBoolean_ defaultDouble) {
        return TYPE.instantiate().set(VALUE, defaultDouble.value());
    }

    static {
        GlobTypeBuilder typeBuilder = GlobTypeBuilderFactory.create("DefaultBoolean");
        TYPE = typeBuilder.unCompleteType();
        VALUE = typeBuilder.declareBooleanField("VALUE");
        typeBuilder.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultBoolean_) annotation));
        typeBuilder.complete();
        KEY = KeyBuilder.newEmptyKey(TYPE);
//        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DefaultBoolean.class, "DefaultBoolean");
//        loader.register(GlobCreateFromAnnotation.class, annotation -> create((DefaultBoolean_) annotation))
//                .load();
    }
}
