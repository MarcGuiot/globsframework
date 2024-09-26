package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobModel;
import org.globsframework.core.metamodel.impl.DefaultGlobModel;

public class AllAnnotations {
    static final public GlobModel MODEL =
            new DefaultGlobModel(AutoIncrementAnnotationType.TYPE,
                    ContainmentLinkAnnotationType.DESC, DefaultBooleanAnnotationType.TYPE,
                    DefaultDoubleAnnotationType.DESC, DefaultFieldValueType.TYPE,
                    DefaultIntegerAnnotationType.DESC, DefaultLongAnnotationType.DESC,
                    DefaultStringAnnotationType.DESC, DoublePrecisionAnnotationType.DESC,
                    FieldNameAnnotationType.TYPE, KeyAnnotationType.TYPE,
                    EnumAnnotationType.TYPE,
                    LinkModelNameAnnotationType.TYPE, MaxSizeType.TYPE, MultiLineTextType.TYPE,
                    IgnoredAnnotationType.TYPE,
                    CommentType.TYPE,
                    NamingFieldAnnotationType.TYPE, RequiredAnnotationType.TYPE,
                    NotUniqueIndexType.TYPE, FunctionalFieldOrderType.TYPE);
}
