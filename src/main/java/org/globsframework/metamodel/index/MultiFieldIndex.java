package org.globsframework.metamodel.index;

import org.globsframework.metamodel.fields.Field;

public interface MultiFieldIndex extends Index {

    Field[] getFields();

}
