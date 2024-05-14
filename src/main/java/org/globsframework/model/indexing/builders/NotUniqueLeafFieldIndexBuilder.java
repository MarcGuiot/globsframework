package org.globsframework.model.indexing.builders;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.model.indexing.indices.NotUniqueLeafLevelIndex;
import org.globsframework.model.indexing.indices.UpdatableMultiFieldIndex;

public class NotUniqueLeafFieldIndexBuilder implements MultiFieldIndexBuilder {
    private Field field;

    public NotUniqueLeafFieldIndexBuilder(Field field) {
        this.field = field;
    }

    public UpdatableMultiFieldIndex create() {
        return new NotUniqueLeafLevelIndex(field);
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
