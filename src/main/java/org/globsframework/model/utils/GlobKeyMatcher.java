package org.globsframework.model.utils;

import org.globsframework.model.Glob;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.Key;

public class GlobKeyMatcher implements GlobMatcher {
    private Key key;

    public GlobKeyMatcher(Key key) {
        this.key = key;
    }

    public boolean matches(Glob item, GlobRepository repository) {
        return (item != null) && item.getKey().equals(key);
    }
}
