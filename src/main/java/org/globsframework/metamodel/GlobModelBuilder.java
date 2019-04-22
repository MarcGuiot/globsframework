package org.globsframework.metamodel;

import org.globsframework.metamodel.impl.DefaultGlobModel;

import java.util.Arrays;

public class GlobModelBuilder {
    private MutableGlobModel globModel;

    static public GlobModelBuilder create(GlobType... types) {
        return new GlobModelBuilder(types);
    }

    static public GlobModelBuilder create(GlobModel inner, GlobType... types) {
        return new GlobModelBuilder(inner, types);
    }

    public GlobModelBuilder add(GlobType type) {
        globModel.add(type);
        return this;
    }

    public GlobModelBuilder add(GlobModel model) {
        model.getAll().forEach(globType -> globModel.add(globType));
    return this;
    }



    public GlobModel get() {
        globModel.complete();
        return globModel;
    }

    private GlobModelBuilder(GlobModel inner, GlobType[] types) {
        globModel = new DefaultGlobModel(inner, types);
    }

    private GlobModelBuilder(GlobType[] types) {
        globModel = new DefaultGlobModel();
        Arrays.stream(types).forEach(this::add);
    }
}
