package org.globsframework.metamodel;

import org.globsframework.metamodel.links.impl.DefaultMutableGlobLinkModel;

public class DummyModel {
    private static GlobModel globModel;
    private static GlobLinkModel globLinkModel;

    public static GlobModel get() {
        return globModel;
    }

    static {
        globModel = GlobModelBuilder.create(
            DummyObject.TYPE,
            DummyObject2.TYPE,
            DummyObjectWithInner.TYPE,
            DummyObjectWithLinks.TYPE,
            DummyObjectWithCompositeKey.TYPE,
            DummyObjectWithStringKey.TYPE,
            DummyObjectWithLinkFieldId.TYPE,
            DummyObjectIndex.TYPE,
            DummyObjectWithDefaultValues.TYPE,
            DummyObjectWithRequiredLink.TYPE
        ).get();

        globLinkModel = new DefaultMutableGlobLinkModel(globModel);
    }
}
