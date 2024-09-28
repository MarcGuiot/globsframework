package org.globsframework.core.metamodel.annotations;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.impl.DefaultFieldFactory;
import org.globsframework.core.metamodel.impl.DefaultGlobType;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.KeyBuilder;

import java.util.LinkedHashMap;

public class KeyField {
    public static GlobType TYPE;

    public static IntegerField INDEX;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob UNINITIALIZED;

    public static Glob ZERO;

    public static Glob ONE;

    public static Glob TWO;

    public static Glob THREE;

    public static Glob FOUR;

    public static Glob create(int indexOfKeyField) {
        switch (indexOfKeyField) {
            case 0:
                return ZERO;
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
            case 4:
                return FOUR;
            default:
                return TYPE.instantiate().set(INDEX, indexOfKeyField);
        }
    }

    static {
        DefaultGlobType globType = new DefaultGlobType("KeyField");
        DefaultFieldFactory factory = new DefaultFieldFactory(globType);
        TYPE = globType;
        INDEX = factory.addInteger("index", false, 0, 0, null, null);
        UNIQUE_KEY = KeyBuilder.newEmptyKey(TYPE);
        globType.completeInit();
        UNINITIALIZED = globType.instantiate().set(INDEX, -1);
        ZERO = globType.instantiate().set(INDEX, 0);
        ONE = globType.instantiate().set(INDEX, 1);
        TWO = globType.instantiate().set(INDEX, 2);
        THREE = globType.instantiate().set(INDEX, 3);
        FOUR = globType.instantiate().set(INDEX, 4);
    }
}
