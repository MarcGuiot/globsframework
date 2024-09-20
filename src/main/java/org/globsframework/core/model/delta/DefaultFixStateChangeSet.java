package org.globsframework.core.model.delta;

import org.globsframework.core.model.Key;

public class DefaultFixStateChangeSet extends DefaultChangeSet implements FixStateChangeSet {

    public DeltaGlob getForUpdate(Key key) {
        return getDefaultDeltaGlob(key, DeltaState.UPDATED);
    }

    public DeltaGlob getForCreate(Key key) {
        return getDefaultDeltaGlob(key, DeltaState.CREATED);
    }

    public DeltaGlob getForDelete(Key key) {
        return getDefaultDeltaGlob(key, DeltaState.DELETED);
    }

    private DefaultDeltaGlob getDefaultDeltaGlob(Key key, DeltaState state) {
        DefaultDeltaGlob deltaGlob = getGlob(key);
        if (deltaGlob.isModified()) {
            throw new RuntimeException("Bug " + key + " should not be set");
        }
        deltaGlob.setState(state);
        return deltaGlob;
    }
}
