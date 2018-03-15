package org.globsframework.model.impl;

import org.globsframework.metamodel.DummyObject;
import org.globsframework.metamodel.DummyObject2;
import org.globsframework.metamodel.DummyObjectWithLinks;
import org.globsframework.model.*;

import static org.globsframework.model.FieldValue.value;
import static org.globsframework.model.KeyBuilder.newKey;

public abstract class DefaultGlobRepositoryTestCase {
    protected GlobChecker checker = new GlobChecker();
    protected DummyChangeSetListener changeListener = new DummyChangeSetListener();
    protected GlobRepository repository;
    protected DummyChangeSetListener trigger = new DummyChangeSetListener();

    protected void init(String xml) {
        init(checker.parse(xml));
    }

    protected void initRepository() {
        init(GlobRepositoryBuilder.createEmpty());
    }

    protected void init(GlobRepository repository) {
        this.repository = repository;
        repository.addChangeListener(changeListener);
        repository.addTrigger(trigger);
    }

    protected Key getKey(int value) {
        return newKey(DummyObject.TYPE, value);
    }

    protected Key getKey2(int value) {
        return newKey(DummyObject2.TYPE, value);
    }

    protected Key getLinksKey(int value) {
        return newKey(DummyObjectWithLinks.TYPE, value);
    }
}
