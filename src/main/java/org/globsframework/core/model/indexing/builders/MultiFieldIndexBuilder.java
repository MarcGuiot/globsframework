package org.globsframework.core.model.indexing.builders;

import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.model.indexing.indices.UpdatableMultiFieldIndex;

public interface MultiFieldIndexBuilder {
    UpdatableMultiFieldIndex create();

    MultiFieldIndexBuilder getSubBuilder();

    void setChild(MultiFieldIndexBuilder indexBuilder);

    Field getField();
}
