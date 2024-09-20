package org.globsframework.core.metamodel;

import org.globsframework.core.metamodel.annotations.KeyField;
import org.globsframework.core.metamodel.annotations.NamingField;
import org.globsframework.core.metamodel.fields.DoubleField;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.metamodel.index.MultiFieldNotUniqueIndex;
import org.globsframework.core.metamodel.index.MultiFieldUniqueIndex;
import org.globsframework.core.metamodel.index.NotUniqueIndex;
import org.globsframework.core.metamodel.index.UniqueIndex;

public class DummyObjectIndex {

    public static GlobType TYPE;

    @KeyField
    public static IntegerField ID;

    public static DoubleField VALUE;
    public static IntegerField VALUE_1;
    public static IntegerField VALUE_2;
    public static IntegerField DATE;

    @NamingField
    public static StringField NAME;

    public static StringField UNIQUE_NAME;

    public static MultiFieldNotUniqueIndex VALUES_INDEX;
    public static MultiFieldUniqueIndex VALUES_AND_NAME_INDEX;
    public static UniqueIndex UNIQUE_NAME_INDEX;
    public static NotUniqueIndex DATE_INDEX;


    static {
        GlobTypeLoader loader = GlobTypeLoaderFactory.create(DummyObjectIndex.class, true).load();
        loader.defineMultiFieldNotUniqueIndex(VALUES_INDEX, VALUE_1, VALUE_2);
        loader.defineMultiFieldUniqueIndex(VALUES_AND_NAME_INDEX, VALUE_1, VALUE_2, NAME);
        loader.defineUniqueIndex(UNIQUE_NAME_INDEX, UNIQUE_NAME);
        loader.defineNonUniqueIndex(DATE_INDEX, DATE);

    }
}
