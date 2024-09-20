package org.globsframework.core.model.utils;

import org.globsframework.core.metamodel.fields.DoubleField;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.metamodel.fields.StringField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.GlobRepository;

public class GlobFunctors {

    public static GlobFunctor update(final IntegerField field, final Integer value) {
        return updateValue(field, value);
    }

    public static GlobFunctor update(final DoubleField field, final Double value) {
        return updateValue(field, value);
    }

    public static GlobFunctor update(final StringField field, final String value) {
        return updateValue(field, value);
    }

    public static GlobFunctor updateValue(final Field field, final Object value) {
        return new GlobFunctor() {
            public void run(Glob glob, GlobRepository repository) throws Exception {
                repository.update(glob.getKey(), field, value);
            }
        };
    }
}
