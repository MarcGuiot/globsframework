package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.KeyField;
import org.globsframework.core.metamodel.annotations.Required;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.metamodel.links.Link;

public class DummyObjectWithRequiredLink {
    public static GlobType TYPE;

    @KeyField
    public static IntegerField ID;

    public static IntegerField TARGET_ID;

    public static StringField NAME;

    @Required
    public static Link LINK;

    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DummyObjectWithRequiredLink.class, true);
        loader.register(MutableGlobLinkModel.LinkRegister.class, mutableGlobLinkModel -> {
            LINK = mutableGlobLinkModel.getDirectLinkBuilder(LINK)
                    .add(TARGET_ID, DummyObject.ID)
                    .publish();
        });
        loader.load();

    }
}
