package org.globsframework.model;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.fields.StringField;
import org.globsframework.model.utils.GlobMatchers;
import org.globsframework.utils.Utils;
import org.junit.Assert;

public class GlobRepositoryChecker {
    private GlobRepository repository;

    public GlobRepositoryChecker(GlobRepository repository) {
        this.repository = repository;
    }

    public Glob findUnique(StringField field, String value) {
        return doFindUnique(field, value);
    }

    public Glob doFindUnique(Field field, Object value) {
        Glob glob = repository.findUnique(field.getGlobType(), GlobMatchers.fieldEqualsObject(field, value));
        if (glob == null) {
            Assert.fail("No object found with " + field.getName() + " = " + value);
        }
        return glob;
    }

    public void checkFields(Glob glob, FieldValue... values) {
        for (FieldValue value : values) {
            if (!Utils.equal(glob.getValue(value.getField()), value.getValue())) {
                Assert.fail("Invalid value for " + value.getField() + " - expected: " + value.getValue() +
                            " but was: " + glob.getValue(value.getField()));
            }
        }
    }
}
