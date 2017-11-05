package org.globsframework.functional.impl;

import org.globsframework.functional.FunctionalKeyBuilder;
import org.globsframework.functional.FunctionalKeyBuilderFactory;
import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DefaultFunctionalKeyBuilderFactory implements FunctionalKeyBuilderFactory {
    private GlobType globType;
    private List<Field> keys = new ArrayList<>();

    public DefaultFunctionalKeyBuilderFactory(GlobType globType) {
        this.globType = globType;
    }

    public FunctionalKeyBuilderFactory add(Field field) {
        if (globType != field.getGlobType()) {
            throw new RuntimeException("Bug : " + field.getFullName() + " is not own by " + globType.getName());
        }
        keys.add(field);
        return this;
    }

    public FunctionalKeyBuilder create() {
        if (keys.size() == 0) {
            throw new RuntimeException("No key in functional key for type " + globType);
        }
        if (keys.size() == 1) {
            return new OneFunctionalKeyBuilder(keys.get(0));
        }
        keys.sort(Comparator.comparingInt(Field::getIndex));
        if (keys.size() == 2) {
            return new TwoFunctionalKeyBuilder(keys.get(0), keys.get(1));
        }
        return new ManyFunctionalKeyBuilder(keys.toArray(new Field[0]));
    }
}
