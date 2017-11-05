package org.globsframework.model.indexing.builders;

import org.globsframework.metamodel.Field;
import org.globsframework.model.indexing.indices.UpdatableMultiFieldIndex;

public interface MultiFieldIndexBuilder {
    UpdatableMultiFieldIndex create();

    MultiFieldIndexBuilder getSubBuilder();

    void setChild(MultiFieldIndexBuilder indexBuilder);

    Field getField();
}
