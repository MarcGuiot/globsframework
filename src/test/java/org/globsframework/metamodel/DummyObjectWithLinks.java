package org.globsframework.metamodel;

import org.globsframework.metamodel.annotations.ContainmentLink;
import org.globsframework.metamodel.annotations.KeyField;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.links.Link;

public class DummyObjectWithLinks {

    public static GlobType TYPE;

    @KeyField
    public static IntegerField ID;

    public static IntegerField TARGET_ID_1;
    public static IntegerField TARGET_ID_2;

    public static IntegerField PARENT_ID;
    public static IntegerField SIBLING_ID;

    public static Link COMPOSITE_LINK;

    @ContainmentLink
    public static Link PARENT_LINK;

    public static Link SIBLING_LINK;

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DummyObjectWithLinks.class, true);
        loader.register(MutableGlobLinkModel.LinkRegister.class,
                        (linkModel) -> {
                            COMPOSITE_LINK = linkModel.getLinkBuilder(COMPOSITE_LINK)
                                .add(TARGET_ID_1, DummyObjectWithCompositeKey.ID1)
                                .add(TARGET_ID_2, DummyObjectWithCompositeKey.ID2)
                                .publish();

                            PARENT_LINK = linkModel.getLinkBuilder(PARENT_LINK)
                                .add(PARENT_ID, DummyObject.ID)
                                .publish();

                            SIBLING_LINK = linkModel.getLinkBuilder(SIBLING_LINK)
                                .add(SIBLING_ID, DummyObjectWithLinks.ID)
                                .publish();
                        });
        loader.load();
    }
}
