package org.globsframework.model.repository;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.links.Link;
import org.globsframework.metamodel.index.Index;
import org.globsframework.metamodel.index.MultiFieldIndex;
import org.globsframework.model.*;
import org.globsframework.model.delta.DefaultChangeSet;
import org.globsframework.model.utils.ChangeVisitor;
import org.globsframework.model.utils.GlobFunctor;
import org.globsframework.model.utils.GlobMatcher;
import org.globsframework.utils.Utils;
import org.globsframework.utils.collections.MultiMap;
import org.globsframework.utils.exceptions.*;

import java.util.*;

public class ReplicationGlobRepository extends DefaultGlobRepository implements GlobRepository {
  private Set<GlobType> managedTypes = new HashSet<GlobType>();
  private GlobRepository originalRepository;
  private List<ChangeSetListener> triggers = new ArrayList<ChangeSetListener>();
  private List<ChangeSetListener> listeners = new ArrayList<ChangeSetListener>();
  private ChangeSetListener forwardChangeSetListener;

  public ReplicationGlobRepository(GlobRepository repository, GlobType... managedTypes) {
    super(repository.getIdGenerator());
    originalRepository = repository;
    this.managedTypes.addAll(Arrays.asList(managedTypes));
    forwardChangeSetListener = new ForwardChangeSetListener(this);
    repository.addChangeListener(forwardChangeSetListener);
  }

  public void startChangeSet() {
    super.startChangeSet();
    originalRepository.startChangeSet();
  }

  public void completeChangeSet() {
    originalRepository.completeChangeSet();
    super.completeChangeSet();
  }

  public void completeChangeSetWithoutTriggers() {
    originalRepository.completeChangeSetWithoutTriggers();
    super.completeChangeSetWithoutTriggers();
  }



  protected void finalize() throws Throwable {
    super.finalize();
    originalRepository.removeChangeListener(forwardChangeSetListener);
  }

  public Glob find(Key key) {
    if (key == null) {
      return null;
    }
    if (managedTypes.contains(key.getGlobType())) {
      return super.find(key);
    }
    else {
      return originalRepository.find(key);
    }
  }

  public Glob get(Key key) throws ItemNotFound {
    if (managedTypes.contains(key.getGlobType())) {
      return super.get(key);
    }
    else {
      return originalRepository.get(key);
    }
  }

  public Glob findUnique(GlobType type, FieldValue... values) throws ItemAmbiguity {
    if (managedTypes.contains(type)) {
      return super.findUnique(type, values);
    }
    else {
      return originalRepository.findUnique(type, values);
    }
  }

  public GlobList getAll(GlobType... type) {
    GlobList globs = super.getAll(type);
    globs.addAll(originalRepository.getAll(type));
    return globs;
  }

  public GlobList getAll(GlobType type, final GlobMatcher matcher) {
    GlobMatcher globMatcher = new DecoratedGlobMatcher(matcher);
    if (managedTypes.contains(type)) {
      return super.getAll(type, globMatcher);
    }
    else {
      return originalRepository.getAll(type, globMatcher);
    }
  }

  public void apply(GlobType type, GlobMatcher matcher, GlobFunctor callback) throws Exception {
    GlobMatcher globMatcher = new DecoratedGlobMatcher(matcher);
    if (managedTypes.contains(type)) {
      super.apply(type, globMatcher, callback);
    }
    else {
      originalRepository.apply(type, globMatcher, callback);
    }
  }

  public void safeApply(GlobType type, GlobMatcher matcher, GlobFunctor callback) {
    GlobMatcher globMatcher = new DecoratedGlobMatcher(matcher);
    if (managedTypes.contains(type)) {
      super.safeApply(type, globMatcher, callback);
    }
    else {
      originalRepository.safeApply(type, globMatcher, callback);
    }
  }

  public Glob findUnique(GlobType type, GlobMatcher matcher) throws ItemAmbiguity {
    GlobMatcher globMatcher = new DecoratedGlobMatcher(matcher);
    if (managedTypes.contains(type)) {
      return super.findUnique(type, globMatcher);
    }
    else {
      return originalRepository.findUnique(type, globMatcher);
    }
  }

  public Glob[] getSorted(GlobType type, Comparator<Glob> comparator, GlobMatcher matcher) {
    GlobMatcher globMatcher = new DecoratedGlobMatcher(matcher);

    if (managedTypes.contains(type)) {
      return super.getSorted(type, comparator, globMatcher);
    }
    else {
      return originalRepository.getSorted(type, comparator, globMatcher);
    }
  }

  public GlobList findByIndex(Index index, Object value) {
    if (managedTypes.contains(index.getField().getGlobType())) {
      return super.findByIndex(index, value);
    }
    else {
      return originalRepository.findByIndex(index, value);
    }
  }

  public MultiFieldIndexed findByIndex(MultiFieldIndex multiFieldIndex, Field field, Object value) {
    if (managedTypes.contains(field.getGlobType())) {
      return super.findByIndex(multiFieldIndex, field, value);
    }
    else {
      return originalRepository.findByIndex(multiFieldIndex, field, value);
    }
  }

  public Set<GlobType> getTypes() {
    HashSet<GlobType> types = new HashSet<GlobType>();
    types.addAll(this.managedTypes);
    types.addAll(originalRepository.getTypes());
    return types;
  }

  public Glob findLinkTarget(Glob source, Link link) {
    if (managedTypes.contains(link.getTargetType())) {
      return super.findLinkTarget(source, link);
    }
    else {
      return originalRepository.findLinkTarget(source, link);
    }
  }

  public GlobList findLinkedTo(Key target, Link link) {
    if (managedTypes.contains(link.getSourceType())) {
      return super.findLinkedTo(target, link);
    }
    else {
      return originalRepository.findLinkedTo(target, link);
    }
  }

  public GlobList findLinkedTo(Glob target, Link link) {
    if (managedTypes.contains(link.getSourceType())) {
      return super.findLinkedTo(target, link);
    }
    else {
      return originalRepository.findLinkedTo(target, link);
    }
  }

  public Glob create(GlobType type, FieldValue... values) throws MissingInfo, ItemAlreadyExists {
    if (managedTypes.contains(type)) {
      return super.create(type, values);
    }
    else {
      return originalRepository.create(type, values);
    }
  }

  public Glob create(Key key, FieldValue... values) throws ItemAlreadyExists {
    if (managedTypes.contains(key.getGlobType())) {
      return super.create(key, values);
    }
    else {
      return originalRepository.create(key, values);
    }
  }

  public Glob findOrCreate(Key key, FieldValue... valuesForCreate) throws MissingInfo {
    if (managedTypes.contains(key.getGlobType())) {
      return super.findOrCreate(key, valuesForCreate);
    }
    else {
      return originalRepository.findOrCreate(key, valuesForCreate);
    }
  }

  public boolean contains(GlobType type) {
    return super.contains(type) || originalRepository.contains(type);
  }

  public boolean contains(GlobType type, GlobMatcher matcher) {
    GlobMatcher globMatcher = new DecoratedGlobMatcher(matcher);

    return super.contains(type, globMatcher) || originalRepository.contains(type, globMatcher);
  }

  public void update(Key key, Field field, Object newValue) throws ItemNotFound {
    if (managedTypes.contains(key.getGlobType())) {
      super.update(key, field, newValue);
    }
    else {
      originalRepository.update(key, field, newValue);
    }
  }

  public void update(Key key, FieldValue... values) {
    if (managedTypes.contains(key.getGlobType())) {
      super.update(key, values);
    }
    else {
      originalRepository.update(key, values);
    }
  }

  public void setTarget(Key source, Link link, Key target) throws ItemNotFound {
    if (managedTypes.contains(source.getGlobType())) {
      super.setTarget(source, link, target);
    }
    else {
      originalRepository.setTarget(source, link, target);
    }
  }

  public void delete(Key key) throws ItemNotFound, OperationDenied {
    if (managedTypes.contains(key.getGlobType())) {
      super.delete(key);
    }
    else {
      originalRepository.delete(key);
    }
  }

  public void delete(Collection<Key> keys) throws ItemNotFound, OperationDenied {
    MultiMap<GlobType, Key> map = new MultiMap<GlobType, Key>();
    for (Key key : keys) {
      map.put(key.getGlobType(), key);
    }
    for (GlobType type : map.keySet()) {
      if (managedTypes.contains(type)) {
        super.delete(map.get(type));
      }
      else {
        originalRepository.delete(map.get(type));
      }
    }
  }

  public void delete(GlobList list) throws OperationDenied {
    GlobList localDelete = list.filter(new GlobMatcher() {
      public boolean matches(Glob item, GlobRepository repository) {
        return managedTypes.contains(item.getType());
      }
    }, this);
    super.delete(localDelete);
    GlobList remoteDelete = list.filter(new GlobMatcher() {
      public boolean matches(Glob item, GlobRepository repository) {
        return !managedTypes.contains(item.getType());
      }
    }, originalRepository);

    originalRepository.delete(remoteDelete);
  }

  public void deleteAll(GlobType... types) throws OperationDenied {
    for (GlobType type : types) {
      if (this.managedTypes.contains(type)) {
        super.deleteAll(type);
      }
      else {
        originalRepository.deleteAll(types);
      }
    }
  }

  public void apply(ChangeSet changeSet) throws InvalidParameter {
    final DefaultChangeSet localChangeSet = new DefaultChangeSet();
    final DefaultChangeSet remoteChangeSet = new DefaultChangeSet();
    changeSet.safeVisit(new ChangeVisitor() {
      public void complete() {
      }

      public void visitCreation(Key key, FieldValues values) throws Exception {
        if (managedTypes.contains(key.getGlobType())) {
          localChangeSet.processCreation(key, values);
        }
        else {
          remoteChangeSet.processCreation(key, values);
        }
      }

      public void visitUpdate(Key key, FieldValuesWithPrevious values) throws Exception {
        if (managedTypes.contains(key.getGlobType())) {
          localChangeSet.processUpdate(key, values);
        }
        else {
          remoteChangeSet.processUpdate(key, values);
        }
      }

      public void visitDeletion(Key key, FieldValues previousValues) throws Exception {
        if (managedTypes.contains(key.getGlobType())) {
          localChangeSet.processDeletion(key, previousValues);
        }
        else {
          remoteChangeSet.processDeletion(key, previousValues);
        }
      }
    });
    super.apply(localChangeSet);
    originalRepository.apply(remoteChangeSet);
  }

  public void addTrigger(ChangeSetListener listener) {
    triggers.add(listener);
    super.addTrigger(listener);
  }

  public void addTriggerAtFirst(ChangeSetListener trigger) {
    triggers.add(0, trigger);
    super.addTriggerAtFirst(trigger);
  }

  public void removeTrigger(ChangeSetListener listener) {
    triggers.remove(listener);
    super.removeTrigger(listener);
  }

  public void addChangeListener(ChangeSetListener listener) {
    listeners = new ArrayList<ChangeSetListener>(listeners);
    listeners.add(listener);
    super.addChangeListener(listener);
//    orgRepository.addChangeListener(listener);
  }

  public void removeChangeListener(ChangeSetListener listener) {
    super.removeChangeListener(listener);
    listeners = new ArrayList<ChangeSetListener>(listeners);
    boolean isRemoved = listeners.remove(listener);
    Utils.beginRemove();
    if (!isRemoved) {
      throw new RuntimeException("BUG");
    }
    Utils.endRemove();
//    orgRepository.removeChangeListener(listener);
  }

  public void reset(GlobList newGlobs, GlobType... changedTypes) {
    GlobList localList = new GlobList();
    GlobList remoteList = new GlobList();
    for (Glob glob : newGlobs) {
      if (managedTypes.contains(glob.getType())) {
        localList.add(glob);
      }
      else {
        remoteList.add(glob);
      }
    }
    List<GlobType> localTypes = new ArrayList<GlobType>();
    List<GlobType> remoteTypes = new ArrayList<GlobType>();
    for (GlobType type : changedTypes) {
      if (managedTypes.contains(type)) {
        localTypes.add(type);
      }
      else {
        remoteTypes.add(type);
      }
    }
    super.reset(localList, localTypes.toArray(new GlobType[localTypes.size()]));
    originalRepository.reset(remoteList, remoteTypes.toArray(new GlobType[remoteTypes.size()]));
  }

  public GlobIdGenerator getIdGenerator() {
    return super.getIdGenerator();
  }

  private class DecoratedGlobMatcher implements GlobMatcher {
    private final GlobMatcher matcher;

    public DecoratedGlobMatcher(GlobMatcher matcher) {
      this.matcher = matcher;
    }

    public boolean matches(Glob item, GlobRepository repository) {
      return matcher.matches(item, ReplicationGlobRepository.this);
    }
  }

  private static class ForwardChangeSetListener implements ChangeSetListener {
    private ReplicationGlobRepository repository;

    public ForwardChangeSetListener(ReplicationGlobRepository repository) {
      this.repository = repository;
    }

    public void globsChanged(ChangeSet changeSet, GlobRepository repository) {
      this.repository.startChangeSet();
      for (ChangeSetListener trigger : this.repository.triggers) {
        trigger.globsChanged(changeSet, repository);
      }
      try {
        for (ChangeSetListener listener : this.repository.listeners) {
          listener.globsChanged(changeSet, this.repository);
        }
      }
      finally {
        this.repository.completeChangeSet();
      }
    }

    public void globsReset(GlobRepository repository, Set<GlobType> changedTypes) {
      this.repository.startChangeSet();
      try {
        for (ChangeSetListener trigger : this.repository.triggers) {
          trigger.globsReset(repository, changedTypes);
        }
        for (ChangeSetListener listener : this.repository.listeners) {
          listener.globsReset(this.repository, changedTypes);
        }
      }
      finally {
        this.repository.completeChangeSet();
      }
    }
  }
}
