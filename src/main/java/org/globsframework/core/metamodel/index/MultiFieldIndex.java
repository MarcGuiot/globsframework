package org.globsframework.core.metamodel.index;

import org.globsframework.core.metamodel.fields.Field;

public interface MultiFieldIndex extends Index {

    Field[] getFields();

}
