package org.globsframework.core.utils.container;

import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;

import java.util.Iterator;

public interface GlobContainer {
    Glob get(Key key);

    GlobContainer put(Key key, Glob value);

    boolean isEmpty();

    Iterator<Glob> values();

    Glob remove(Key value);

    int size();

    <E extends Functor> E apply(E functor);


    interface Functor {
        void apply(Glob d);
    }

}
