package org.globsframework.core.metamodel.links;

import org.globsframework.core.metamodel.fields.Field;

public interface DirectSingleLink extends DirectLink {

    Field getSourceField();

    Field getTargetField();

}
