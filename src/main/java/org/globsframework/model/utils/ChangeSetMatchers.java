package org.globsframework.model.utils;

import org.globsframework.metamodel.GlobType;
import org.globsframework.model.ChangeSet;
import org.globsframework.model.GlobRepository;
import org.globsframework.model.Key;

public class ChangeSetMatchers {
    public static ChangeSetMatcher ALL = new ChangeSetMatcher() {
        public boolean matches(ChangeSet changeSet, GlobRepository repository) {
            return true;
        }
    };

    public static ChangeSetMatcher NONE = new ChangeSetMatcher() {
        public boolean matches(ChangeSet changeSet, GlobRepository repository) {
            return false;
        }
    };

    public static ChangeSetMatcher changesForType(final GlobType type) {
        return new ChangeSetMatcher() {
            public boolean matches(ChangeSet changeSet, GlobRepository repository) {
                return changeSet.containsChanges(type);
            }
        };
    }

    public static ChangeSetMatcher changesForTypes(final GlobType... types) {
        return new ChangeSetMatcher() {
            public boolean matches(ChangeSet changeSet, GlobRepository repository) {
                for (GlobType type : types) {
                    if (changeSet.containsChanges(type)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public static ChangeSetMatcher changesForKey(final Key key) {
        return new ChangeSetMatcher() {
            public boolean matches(ChangeSet changeSet, GlobRepository repository) {
                return changeSet.containsChanges(key);
            }
        };
    }
}
