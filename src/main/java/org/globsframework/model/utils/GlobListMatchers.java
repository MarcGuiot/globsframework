package org.globsframework.model.utils;

import org.globsframework.model.Glob;
import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;

public class GlobListMatchers {
    public static final GlobListMatcher ALL = new GlobListMatcher() {
        public boolean matches(GlobList list, GlobRepository repository) {
            return true;
        }

        public String toString() {
            return "ALL";
        }
    };

    public static final GlobListMatcher NONE = new GlobListMatcher() {
        public boolean matches(GlobList list, GlobRepository repository) {
            return false;
        }

        public String toString() {
            return "NONE";
        }
    };

    public static final GlobListMatcher EMPTY = new GlobListMatcher() {
        public boolean matches(GlobList list, GlobRepository repository) {
            return list.isEmpty();
        }

        public String toString() {
            return "EMPTY";
        }
    };

    public static final GlobListMatcher AT_LEAST_ONE = new GlobListMatcher() {
        public boolean matches(GlobList list, GlobRepository repository) {
            return list.size() > 0;
        }

        public String toString() {
            return "AT_LEAST_ONE";
        }
    };

    public static final GlobListMatcher AT_LEAST_TWO = new GlobListMatcher() {
        public boolean matches(GlobList list, GlobRepository repository) {
            return list.size() > 1;
        }

        public String toString() {
            return "AT_LEAST_TWO";
        }
    };

    public static final GlobListMatcher EXACTLY_ONE = new GlobListMatcher() {
        public boolean matches(GlobList list, GlobRepository repository) {
            return list.size() == 1;
        }

        public String toString() {
            return "EXACTLY_ONE";
        }
    };

    public static GlobListMatcher atLeast(final int number) {
        return new GlobListMatcher() {
            public boolean matches(GlobList list, GlobRepository repository) {
                return list.size() > number;
            }
        };
    }

    public static GlobListMatcher contains(final Glob glob) {
        return new GlobListMatcher() {
            public boolean matches(GlobList list, GlobRepository repository) {
                return list.contains(glob);
            }
        };
    }
}
