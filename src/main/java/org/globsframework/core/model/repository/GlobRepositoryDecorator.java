package org.globsframework.core.model.repository;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.metamodel.index.MultiFieldIndex;
import org.globsframework.core.metamodel.index.SingleFieldIndex;
import org.globsframework.core.metamodel.links.Link;
import org.globsframework.core.model.*;
import org.globsframework.core.model.utils.GlobFunctor;
import org.globsframework.core.utils.exceptions.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class GlobRepositoryDecorator implements GlobRepository {
    protected GlobRepository repository;

    public GlobRepositoryDecorator(GlobRepository repository) {
        this.setRepository(repository);
    }

    public Glob find(Key key) {
        return getRepository().find(key);
    }

    public Glob get(Key key) throws ItemNotFound {
        return getRepository().get(key);
    }

    public Glob findUnique(GlobType type, FieldValue... values) throws ItemAmbiguity {
        return getRepository().findUnique(type, values);
    }

    public List<Glob> getAll(GlobType... type) {
        return getRepository().getAll(type);
    }

    public List<Glob> getAll(GlobType type, Predicate<Glob> matcher) {
        return getRepository().getAll(type, matcher);
    }

    public void safeApply(GlobFunctor callback) {
        getRepository().safeApply(callback);
    }

    public void apply(GlobType type, Predicate<Glob> matcher, GlobFunctor callback) throws Exception {
        getRepository().apply(type, matcher, callback);
    }

    public void safeApply(GlobType type, Predicate<Glob> matcher, GlobFunctor callback) {
        getRepository().safeApply(type, matcher, callback);
    }

    public Glob findUnique(GlobType type, Predicate<Glob> matcher) throws ItemAmbiguity {
        return getRepository().findUnique(type, matcher);
    }

    public Glob[] getSorted(GlobType type, Comparator<Glob> comparator, Predicate<Glob> matcher) {
        return getRepository().getSorted(type, comparator, matcher);
    }

    public List<Glob> findByIndex(SingleFieldIndex index, Object value) {
        return getRepository().findByIndex(index, value);
    }

    public MultiFieldIndexed findByIndex(MultiFieldIndex multiFieldIndex, Field field, Object value) {
        return getRepository().findByIndex(multiFieldIndex, field, value);
    }

    public Set<GlobType> getTypes() {
        return getRepository().getTypes();
    }

    public Glob findLinkTarget(Glob source, Link link) {
        return getRepository().findLinkTarget(source, link);
    }

    public List<Glob> findLinkedTo(Key target, Link link) {
        return getRepository().findLinkedTo(target, link);
    }

    public List<Glob> findLinkedTo(Glob target, Link link) {
        return getRepository().findLinkedTo(target, link);
    }

    public int size() {
        return getRepository().size();
    }

    public void create(Glob glob) {
        getRepository().create(glob);
    }

    public Glob create(GlobType type, FieldValue... values) throws MissingInfo, ItemAlreadyExists {
        return getRepository().create(type, values);
    }

    public Glob create(Key key, FieldValue... values) throws ItemAlreadyExists {
        return getRepository().create(key, values);
    }

    public Glob findOrCreate(Key key, FieldValue... valuesForCreate) throws MissingInfo {
        return getRepository().findOrCreate(key, valuesForCreate);
    }

    public boolean contains(Key key) {
        return getRepository().contains(key);
    }

    public boolean contains(GlobType type) {
        return getRepository().contains(type);
    }

    public boolean contains(GlobType type, Predicate<Glob> matcher) {
        return getRepository().contains(type, matcher);
    }

    public void update(Glob glob, Field field, Object newValue) throws ItemNotFound {
        getRepository().update(glob, field, newValue);
    }

    public void update(Key key, Field field, Object newValue) throws ItemNotFound {
        getRepository().update(key, field, newValue);
    }

    public void update(Glob glob, FieldValue... values) {
        getRepository().update(glob, values);
    }

    public void update(Key key, FieldValue... values) {
        getRepository().update(key, values);
    }

    public void setTarget(Key source, Link link, Key target) throws ItemNotFound {
        getRepository().setTarget(source, link, target);
    }

    public void delete(Glob glob) throws ItemNotFound, OperationDenied {
        getRepository().delete(glob);
    }

    public void delete(Key key) throws ItemNotFound, OperationDenied {
        getRepository().delete(key);
    }

    public void delete(Collection<Key> keys) throws ItemNotFound, OperationDenied {
        getRepository().delete(keys);
    }

    public void deleteGlobs(Collection<Glob> list) throws OperationDenied {
        getRepository().deleteGlobs(list);
    }

    public void delete(GlobType type, Predicate<Glob> matcher) throws OperationDenied {
        getRepository().delete(type, matcher);
    }

    public void deleteAll(GlobType... types) throws OperationDenied {
        getRepository().deleteAll(types);
    }

    public void apply(ChangeSet changeSet) throws InvalidParameter {
        getRepository().apply(changeSet);
    }

    public void addTrigger(ChangeSetListener listener) {
        getRepository().addTrigger(listener);
    }

    public void addTriggerAtFirst(ChangeSetListener trigger) {
        getRepository().addTriggerAtFirst(trigger);
    }

    public void removeTrigger(ChangeSetListener listener) {
        getRepository().removeTrigger(listener);
    }

    public void addChangeListener(ChangeSetListener listener) {
        getRepository().addChangeListener(listener);
    }

    public void removeChangeListener(ChangeSetListener listener) {
        getRepository().removeChangeListener(listener);
    }

    public void startChangeSet() {
        getRepository().startChangeSet();
    }

    public void completeChangeSet() {
        getRepository().completeChangeSet();
    }

    public void completeChangeSetWithoutTriggers() {
        getRepository().completeChangeSetWithoutTriggers();
    }

    public void reset(Collection<Glob> newGlobs, GlobType... changedTypes) {
        getRepository().reset(newGlobs, changedTypes);
    }

    protected GlobRepository getRepository() {
        return repository;
    }

    protected void setRepository(GlobRepository repository) {
        this.repository = repository;
    }

    public GlobIdGenerator getIdGenerator() {
        return repository.getIdGenerator();
    }

    public void invokeAfterChangeSet(InvokeAction action) {
        getRepository().invokeAfterChangeSet(action);
    }
}
