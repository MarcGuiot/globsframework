package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobModel;
import org.globsframework.metamodel.impl.DefaultGlobModel;

public class AnnotationModel {
  static final public GlobModel MODEL = new DefaultGlobModel(DefaultStringAnnotationType.DESC, AutoIncrementAnnotationType.TYPE,
                                                       ContainmentLinkAnnotationType.DESC, DefaultBooleanAnnotationType.TYPE,
                                                       DefaultDoubleAnnotationType.DESC, DefaultFieldValueType.TYPE,
                                                       DefaultIntegerAnnotationType.DESC, DefaultLongAnnotationType.DESC,
                                                       DefaultStringAnnotationType.DESC, DoublePrecisionAnnotationType.DESC,
                                                       FieldNameAnnotationType.TYPE, KeyAnnotationType.TYPE,
                                                       LinkModelNameAnnotationType.TYPE, MaxSizeType.TYPE,
                                                       NamingFieldAnnotationType.TYPE, RequiredAnnotationType.TYPE);
}
