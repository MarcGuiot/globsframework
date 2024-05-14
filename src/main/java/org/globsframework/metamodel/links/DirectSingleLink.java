package org.globsframework.metamodel.links;

import org.globsframework.metamodel.fields.Field;

public interface DirectSingleLink extends DirectLink {

    Field getSourceField();

    Field getTargetField();

}
