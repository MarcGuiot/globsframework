package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.KeyField;
import org.globsframework.core.metamodel.annotations.LinkModelName;
import org.globsframework.core.metamodel.annotations.Target;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.links.Link;

public class DummyObjectWithLinkFieldId {
    public static GlobType TYPE;

    @KeyField
    public static IntegerField LINK_ID;

    @LinkModelName("ANY")
    @Target(DummyObject.class)
    public static Link LINK;

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DummyObjectWithLinkFieldId.class, true);
        loader.register(MutableGlobLinkModel.LinkRegister.class,
                        (linkModel) ->
                                LINK = linkModel.getDirectLinkBuilder(LINK).add(LINK_ID, DummyObject.ID).publish())
                .load();
    }
}
