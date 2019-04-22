package org.globsframework.utils.container;

import org.globsframework.model.Glob;
import org.globsframework.model.Key;

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
