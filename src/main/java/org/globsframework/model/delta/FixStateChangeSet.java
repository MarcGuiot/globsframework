package org.globsframework.model.delta;

import org.globsframework.model.ChangeSet;
import org.globsframework.model.Key;

public interface FixStateChangeSet extends ChangeSet {
    DeltaGlob getForUpdate(Key key);

    DeltaGlob getForCreate(Key key);

    DeltaGlob getForDelete(Key key);
}
