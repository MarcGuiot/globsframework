package org.globsframework.core.model.delta;

import org.globsframework.core.model.ChangeSet;
import org.globsframework.core.model.Key;

public interface FixStateChangeSet extends ChangeSet {
    DeltaGlob getForUpdate(Key key);

    DeltaGlob getForCreate(Key key);

    DeltaGlob getForDelete(Key key);
}
