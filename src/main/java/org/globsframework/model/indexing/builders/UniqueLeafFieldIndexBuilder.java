package org.globsframework.model.indexing.builders;

import org.globsframework.metamodel.Field;
import org.globsframework.model.indexing.indices.UniqueLeafLevelIndex;
import org.globsframework.model.indexing.indices.UpdatableMultiFieldIndex;

public class UniqueLeafFieldIndexBuilder implements MultiFieldIndexBuilder {
    private Field field;

    public UniqueLeafFieldIndexBuilder(Field field) {
        this.field = field;
    }

    public UpdatableMultiFieldIndex create() {
        return new UniqueLeafLevelIndex(field);
    }

    public MultiFieldIndexBuilder getSubBuilder() {
        return null;
    }

    public void setChild(MultiFieldIndexBuilder indexBuilder) {
    }

    public Field getField() {
        return field;
    }
}
