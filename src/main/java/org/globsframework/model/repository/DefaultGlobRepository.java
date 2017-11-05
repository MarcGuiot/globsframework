package org.globsframework.model.repository;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.IntegerField;
import org.globsframework.metamodel.index.Index;
import org.globsframework.metamodel.index.MultiFieldIndex;
import org.globsframework.metamodel.links.FieldMappingFunction;
import org.globsframework.metamodel.links.Link;
import org.globsframework.model.*;
import org.globsframework.model.delta.DefaultChangeSet;
import org.globsframework.model.delta.MutableChangeSet;
import org.globsframework.model.format.GlobPrinter;
import org.globsframework.model.impl.AbstractGlob;
import org.globsframework.model.impl.DefaultGlob;
import org.globsframework.model.indexing.IndexManager;
import org.globsframework.model.indexing.IndexSource;
import org.globsframework.model.indexing.IndexTables;
import org.globsframework.model.utils.GlobFunctor;
import org.globsframework.model.utils.GlobMatcher;
import org.globsframework.model.utils.GlobMatchers;
import org.globsframework.utils.Utils;
import org.globsframework.utils.collections.MapOfMaps;
import org.globsframework.utils.collections.Pair;
import org.globsframework.utils.exceptions.*;

import java.util.*;

public class DefaultGlobRepository implements GlobRepository, IndexSource {
    private Map<Key, AbstractGlob> pendingDeletions = new HashMap<Key, AbstractGlob>();
    private MapOfMaps<GlobType, Key, Glob> globs = new MapOfMaps<GlobType, Key, Glob>();
    private List<ChangeSetListener> changeListeners = new ArrayList<ChangeSetListener>();
    private List<ChangeSetListener> triggers = new ArrayList<ChangeSetListener>();
    private int bulkDispatchingModeLevel;
    private MutableChangeSet changeSetToDispatch = new DefaultChangeSet();
    private GlobIdGenerator idGenerator = GlobIdGenerator.NONE;

    private IndexManager indexManager;
    private List<InvokeAction> actions = new ArrayList<InvokeAction>();

    public DefaultGlobRepository() {
        indexManager = new IndexManager(this);
    }

    public DefaultGlobRepository(GlobIdGenerator idGenerator) {
        this();
        this.idGenerator = idGenerator;
    }

    public Iterable<Glob> getGlobs(GlobType globType) {
        return globs.values(globType);
    }

    public GlobList getAll(GlobType... types) {
        if (types.length == 0) {
            return new GlobList(globs.values());
        }
        GlobList result = new GlobList();
        for (GlobType type : types) {
            result.addAll(globs.values(type));
        }
        return result;
    }

    public Glob findLinkTarget(Glob source, Link link) {
        if (source == null) {
            return null;
        }
        Key targetKey = source.getTargetKey(link);
        if (targetKey == null) {
            return null;
        }
        return find(targetKey);
    }

    public GlobList findLinkedTo(Key target, Link link) {
        if (target == null) {
            return GlobList.EMPTY;
        }
        GlobList result = new GlobList();
        for (Glob glob : getAll(link.getSourceType())) {
            if (target.equals(glob.getTargetKey(link))) {
                result.add(glob);
            }
        }

        return result;
    }

    public GlobList findLinkedTo(Glob target, Link link) {
        if (target == null) {
            return GlobList.EMPTY;
        }
        return findLinkedTo(target.getKey(), link);
    }

    public boolean contains(Key key) {
        return find(key) != null;
    }

    public Glob find(Key key) {
        if (key == null) {
            return null;
        }
        return globs.get(key.getGlobType(), key);
    }

    public Glob get(Key key) throws ItemNotFound {
        Glob glob = find(key);
        if (glob == null) {
            throw new ItemNotFound("Object " + key + " not found in repository");
        }
        return glob;
    }

    public Glob findUnique(GlobType type, FieldValue... values) {
        Glob result = null;
        for (Glob glob : globs.values(type)) {
            if (glob.matches(values)) {
                if (result != null) {
                    StringBuilder builder = new StringBuilder();
                    for (FieldValue value : values) {
                        builder.append("(").append(value.getField()).append(",").append(value.getValue()).append(")");
                    }
                    throw new ItemAmbiguity("There are several objects of type " + type.getName() +
                                            " with values " + builder.toString());
                }
                result = glob;
            }
        }
        return result;
    }

    public GlobList getAll(GlobType type, GlobMatcher matcher) {
        if (GlobMatchers.NONE.equals(matcher)) {
            return new GlobList();
        }
        if (GlobMatchers.ALL.equals(matcher)) {
            return getAll(type);
        }
        GlobList result = new GlobList();
        for (Glob glob : globs.values(type)) {
            if (matcher.matches(glob, this)) {
                result.add(glob);
            }
        }
        return result;
    }

    public void apply(GlobType type, GlobMatcher matcher, GlobFunctor callback) throws Exception {
        if (GlobMatchers.ALL.equals(matcher)) {
            for (Glob glob : globs.values(type)) {
                callback.run(glob, this);
            }
        }
        else {
            for (Glob glob : globs.values(type)) {
                if (matcher.matches(glob, this)) {
                    callback.run(glob, this);
                }
            }
        }
    }

    public void safeApply(GlobType type, GlobMatcher matcher, GlobFunctor callback) {
        try {
            apply(type, matcher, callback);
        }
        catch (Exception e) {
            throw new UnexpectedApplicationState(e);
        }
    }


    public boolean contains(GlobType type) {
        return !globs.values(type).isEmpty();
    }

    public boolean contains(GlobType type, GlobMatcher matcher) {
        if (GlobMatchers.NONE.equals(matcher)) {
            return false;
        }
        for (Glob glob : globs.values(type)) {
            if (matcher.matches(glob, this)) {
                return true;
            }
        }
        return false;
    }

    public Glob findUnique(GlobType type, GlobMatcher matcher) throws ItemAmbiguity {
        Glob result = null;
        for (Glob glob : globs.values(type)) {
            if (matcher.matches(glob, this)) {
                if (result != null) {
                    throw new ItemAmbiguity("There are several objects of type " + type.getName() +
                                            " matching: " + matcher);
                }
                result = glob;
            }
        }
        return result;
    }

    Glob[] tmp = new Glob[10];

    public Glob[] getSorted(GlobType type, Comparator<Glob> comparator, GlobMatcher matcher) {
        int i = 0;
        for (Glob glob : globs.values(type)) {
            if (matcher.matches(glob, this)) {
                if (i >= tmp.length) {
                    Glob[] temporary = new Glob[tmp.length * 2];
                    System.arraycopy(tmp, 0, temporary, 0, tmp.length);
                    tmp = temporary;
//          tmp = Arrays.copyOf(tmp, tmp.length * 2);
                }
                tmp[i++] = glob;
            }
        }
        Arrays.sort(tmp, 0, i, comparator);
        Glob[] newTmp = new Glob[i];
        System.arraycopy(tmp, 0, newTmp, 0, i);
        return newTmp;
//    return Arrays.copyOf(tmp, i);
    }

    public Set<GlobType> getTypes() {
        return globs.keys();
    }

    public Integer getNextId(IntegerField field, int count) {
        return idGenerator.getNextId(field, count);
    }

    public Glob create(GlobType type, FieldValue... values) throws MissingInfo {
        Object[] globValuesArray = completeValues(type, values);
        addKeyValues(type, globValuesArray);
        Key key = KeyBuilder.createFromValues(type, globValuesArray);
        return create(type, key, globValuesArray);
    }

    public Glob create(Key key, FieldValue... values) throws ItemAlreadyExists {
        Object[] globValuesArray = completeValues(key.getGlobType(), values);
        for (Field field : key.getGlobType().getKeyFields()) {
            globValuesArray[field.getIndex()] = key.getValue(field);
        }
        return create(key.getGlobType(), key, globValuesArray);
    }

    private Glob create(GlobType type, Key key, Object[] globValuesArray) {
        checkKeyDoesNotExist(key);
        AbstractGlob glob = pendingDeletions.remove(key);
        if (glob == null) {
            glob = new DefaultGlob(type, globValuesArray);
        }
        else {
            glob.setValues(globValuesArray);
        }

        IndexTables indexTables = indexManager.getAssociatedTable(type);
        globs.put(key.getGlobType(), key, glob);

        if (indexTables != null) {
            indexTables.add(glob);
        }

        changeSetToDispatch.processCreation(key, glob);
        notifyListeners(true);
        return glob;
    }

    private Object[] completeValues(GlobType type, FieldValue[] values) {
        Object[] completedArray = new Object[type.getFieldCount()];
        addDefaultValues(type, completedArray);
        copyValues(values, completedArray);
        return completedArray;
    }

    private void copyValues(FieldValue[] values, Object[] completedArray) {
        for (FieldValue value : values) {
            completedArray[value.getField().getIndex()] = value.getValue();
        }
    }

    private void copyValues(FieldValues values, final Object[] completedArray) {
        values.safeApply(new FieldValues.Functor() {
            public void process(Field field, Object value) throws Exception {
                completedArray[field.getIndex()] = value;
            }
        });
    }

    private void addKeyValues(GlobType type, Object[] values) {
        Field[] keyFields = type.getKeyFields();
        if (keyFields.length != 1) {
            return;
        }
        Field field = keyFields[0];
        if ((values[field.getIndex()] != null) || !(field instanceof IntegerField)) {
            return;
        }
        IntegerField keyField = (IntegerField)field;
        values[keyField.getIndex()] = idGenerator.getNextId(keyField, 1);
    }

    private void addDefaultValues(GlobType type, Object[] values) {
        for (Field field : type.getFields()) {
            values[field.getIndex()] = field.getDefaultValue();
        }
    }

    public Glob findOrCreate(Key key, FieldValue... valuesForCreate) {
        GlobType type = key.getGlobType();
        Glob glob = globs.get(type, key);
        if (glob != null) {
            return glob;
        }

        Object[] completedArray = new Object[type.getFieldCount()];
        addDefaultValues(type, completedArray);
        copyValues(valuesForCreate, completedArray);
        copyValues(key.asFieldValues(), completedArray);
        return create(key.getGlobType(), key, completedArray);
    }

    public void update(Glob glob, Field field, Object newValue) throws InvalidParameter, ItemNotFound {
        if (glob == null) {
            throw new ItemNotFound("Update called for null object");
        }
        update(glob.getKey(), field, newValue);
    }

    public void update(Key key, Field field, Object newValue) throws ItemNotFound {
        MutableGlob mutableGlob = getGlobForUpdate(key);
        if (doUpdate(mutableGlob, key, field, field.normalize(newValue))) {
            notifyListeners(true);
        }
    }

    public void update(final Glob glob, FieldValue... values) throws ItemNotFound {
        if (glob == null) {
            throw new ItemNotFound("Update called for null object");
        }
        update(glob.getKey(), values);
    }

    public void update(final Key key, FieldValue... values) throws ItemNotFound {
        final MutableGlob mutableGlob = getGlobForUpdate(key);
        startChangeSet();
        try {
            for (FieldValue value : values) {
                doUpdate(mutableGlob, key, value.getField(), value.getValue());
            }
        }
        finally {
            completeChangeSet();
        }
    }

    private boolean doUpdate(MutableGlob mutableGlob, Key key, Field field, Object newValue) {
        GlobType globType = mutableGlob.getType();
        if (!field.getGlobType().equals(globType)) {
            throw new InvalidParameter("'" + field + "' is not a field of type '" +
                                       globType.getName() + "'");
        }
        Object previousValue = mutableGlob.getValue(field);
        if (Utils.equal(previousValue, newValue)) {
            return false;
        }
        if (field.isKeyField()) {
            throw new OperationDenied("Field '" + field.getName() + "' of object '" +
                                      key + "' is a key and cannot be changed");
        }

        mutableGlob.setValue(field, newValue);
        IndexTables indexTables = indexManager.getAssociatedTable(field);
        if (indexTables != null) {
            indexTables.add(newValue, mutableGlob, field, previousValue);
        }
        changeSetToDispatch.processUpdate(key, field, newValue, previousValue);
        return true;
    }

    public GlobList findByIndex(Index index, Object value) {
        return indexManager.getAssociatedTable(index).findByIndex(value);
    }

    public MultiFieldIndexed findByIndex(MultiFieldIndex multiFieldIndex, Field field, Object value) {
        MultiFieldIndexed table = indexManager.getAssociatedTable(multiFieldIndex);
        if (table != null) {
            return table.findByIndex(field, value);
        }
        return null;
    }

    public void setTarget(final Key sourceKey, Link link, final Key targetKey) throws ItemNotFound {
        final MutableGlob sourceGlob = getGlobForUpdate(sourceKey);
        GlobType sourceType = sourceKey.getGlobType();
        if (!link.getSourceType().equals(sourceType)) {
            throw new InvalidParameter(
                "Type '" + sourceType.getName() + "' is not a valid source for link  '" + link + "'");
        }
        if (targetKey != null) {
            GlobType targetType = targetKey.getGlobType();
            if (!link.getTargetType().equals(targetType)) {
                throw new InvalidParameter("Key '" + targetKey + "' is not a valid target for link '" +
                                           link + "'");
            }
        }
        LinkFieldMappingFunction fieldMappingFunctor = new LinkFieldMappingFunction(targetKey, sourceGlob, sourceKey);
        link.apply(fieldMappingFunctor);
        if (fieldMappingFunctor.hasChange()) {
            notifyListeners(true);
        }
    }

    public void delete(Glob glob) throws ItemNotFound, OperationDenied {
        delete(glob.getKey());
    }

    public void delete(Key key) throws ItemNotFound, OperationDenied {
        MutableGlob glob = getGlobForUpdate(key);
        GlobType type = glob.getType();
        IndexTables indexTables = indexManager.getAssociatedTable(type);
        if (indexTables != null) {
            indexTables.remove(glob);
        }
        disable(glob);
        globs.remove(key.getGlobType(), key);
        changeSetToDispatch.processDeletion(key, glob);
        notifyListeners(true);
    }

    public void delete(Collection<Key> keys) throws ItemNotFound, OperationDenied {
        for (Key key : keys) {
            MutableGlob glob = getGlobForUpdate(key);
            GlobType type = glob.getType();
            IndexTables indexTables = indexManager.getAssociatedTable(type);
            if (indexTables != null) {
                indexTables.remove(glob);
            }
            disable(glob);
            globs.remove(key.getGlobType(), key);
            changeSetToDispatch.processDeletion(key, glob);
        }
        notifyListeners(true);
    }

    public void delete(GlobList list) throws OperationDenied {
        if (list.isEmpty()) {
            return;
        }
        startChangeSet();
        OperationDenied exception = null;
        try {
            List<Pair<Key, FieldValues>> toBeRemoved = new ArrayList<Pair<Key, FieldValues>>();
            for (Glob glob : list) {
                if (glob == null) {
                    continue;
                }
                if (!MutableGlob.class.isInstance(glob)) {
                    throw new OperationDenied("Object '" + glob + "' cannot be modified");
                }
                GlobType type = glob.getType();
                IndexTables indexTables = indexManager.getAssociatedTable(type);
                if (indexTables != null) {
                    indexTables.remove(glob);
                }
                disable(glob);
                toBeRemoved.add(new Pair<Key, FieldValues>(glob.getKey(), glob));
            }
            for (Pair<Key, FieldValues> pair : toBeRemoved) {
                Key key = pair.getFirst();
                globs.remove(key.getGlobType(), key);
                changeSetToDispatch.processDeletion(key, pair.getSecond());
            }
        }
        catch (OperationDenied e) {
            exception = e;
        }
        finally {
            completeChangeSet();
        }
        if (exception != null) {
            throw exception;
        }
    }

    public void delete(GlobType type, GlobMatcher matcher) throws OperationDenied {
        delete(getAll(type, matcher));
    }

    public void deleteAll(GlobType... types) throws OperationDenied {
        startChangeSet();
        if (types.length == 0) {
            Set<GlobType> allTypes = getTypes();
            types = allTypes.toArray(new GlobType[allTypes.size()]);
        }
        OperationDenied exception = null;
        try {
            List<Pair<Key, FieldValues>> toBeRemoved = new ArrayList<Pair<Key, FieldValues>>();
            for (GlobType type : types) {
                for (Map.Entry<Key, Glob> entry : globs.get(type).entrySet()) {
                    if (!MutableGlob.class.isInstance(entry.getValue())) {
                        throw new OperationDenied("Object '" + entry.getKey() + "' cannot be modified");
                    }
                    Glob glob = entry.getValue();
                    disable(glob);
                    toBeRemoved
                        .add(new Pair<Key, FieldValues>(entry.getKey(), entry.getValue()));
                }
                IndexTables indexTables = indexManager.getAssociatedTable(type);
                if (indexTables != null) {
                    indexTables.removeAll();
                }
            }
            for (Pair<Key, FieldValues> pair : toBeRemoved) {
                Key key = pair.getFirst();
                globs.remove(key.getGlobType(), key);
                changeSetToDispatch.processDeletion(key, pair.getSecond());
            }
        }
        catch (OperationDenied e) {
            exception = e;
        }
        finally {
            completeChangeSet();
        }
        if (exception != null) {
            throw exception;
        }
    }

    public void apply(ChangeSet changeSet) throws InvalidParameter {
        changeSet.safeVisit(new ChangeSetVisitor() {
            public void visitCreation(Key key, FieldValues values) {
                if (contains(key)) {
                    throw new InvalidParameter("Object " + key + " already exists\n" +
                                               "-- New object values:\n" + GlobPrinter.toString(values) +
                                               "-- Existing object:\n" + GlobPrinter.toString(find(key))
                    );
                }
            }

            public void visitUpdate(Key key, FieldValuesWithPrevious values) {
                if (!contains(key)) {
                    throw new InvalidParameter("Object " + key + " not found - cannot apply update");
                }
            }

            public void visitDeletion(Key key, FieldValues values) {
                if (!contains(key)) {
                    throw new InvalidParameter("Object " + key + " not found - cannot apply deletion");
                }
            }
        });

        try {
            startChangeSet();
            changeSet.safeVisit(new ChangeSetExecutor());
        }
        finally {
            completeChangeSet();
        }
    }

    private void disable(Glob glob) {
        if (glob instanceof AbstractGlob) {
            pendingDeletions.put(glob.getKey(), (AbstractGlob)glob);
            ((AbstractGlob)glob).dispose();
        }
    }

    public void startChangeSet() {
        bulkDispatchingModeLevel++;
    }

    public void completeChangeSet() {
        completeBulkDispatchingMode(true);
    }

    public void completeChangeSetWithoutTriggers() {
        if (bulkDispatchingModeLevel > 1) {
            throw new InvalidState("This method must be called for the outermost enterBulkDispatchingMode call");
        }
        completeBulkDispatchingMode(false);
    }

    private void completeBulkDispatchingMode(boolean applyTriggers) {
        if (bulkDispatchingModeLevel < 0) {
            return;
        }
        bulkDispatchingModeLevel--;
        if (bulkDispatchingModeLevel == 0) {
            notifyListeners(applyTriggers);
        }
    }

    public void reset(GlobList newGlobs, GlobType... changedTypes) {
        startChangeSet();
        try {
            Set<GlobType> typesList = new HashSet<GlobType>(Arrays.asList(changedTypes));
            for (GlobType type : changedTypes) {
                for (Map.Entry<Key, Glob> entry : globs.get(type).entrySet()) {
                    IndexTables indexTables = indexManager.getAssociatedTable(type);
                    if (indexTables != null) {
                        indexTables.remove(entry.getValue());
                    }
                    disable(entry.getValue());
                }
                globs.removeAll(type);
            }
            for (Glob glob : newGlobs) {
                Key key = glob.getKey();
                if (typesList.contains(key.getGlobType())) {
                    Glob duplicatedGlob = glob.duplicate();
                    IndexTables indexTables = indexManager.getAssociatedTable(key.getGlobType());
                    globs.put(key.getGlobType(), key, duplicatedGlob);
                    if (indexTables != null) {
                        indexTables.add(duplicatedGlob);
                    }
                }
            }
            for (ChangeSetListener listener : triggers) {
                listener.globsReset(this, typesList);
            }
            for (ChangeSetListener listener : changeListeners) {
                listener.globsReset(this, typesList);
            }
        }
        finally {
            completeChangeSet();
        }
    }

    public GlobIdGenerator getIdGenerator() {
        return idGenerator;
    }

    public void invokeAfterChangeSet(InvokeAction action) {
        actions.add(action);
    }

    public void add(GlobList globs) {
        for (Glob glob : globs) {
            add(glob);
        }
    }

    public void add(Glob glob) {
        Key key = glob.getKey();
        checkKeyDoesNotExist(key);
        IndexTables indexTables = indexManager.getAssociatedTable(key.getGlobType());
        this.globs.put(key.getGlobType(), key, glob);
        if (indexTables != null) {
            indexTables.add(glob);
        }
    }

    public void add(Glob... globs) {
        for (Glob glob : globs) {
            add(glob);
        }
    }

    public void addChangeListener(ChangeSetListener listener) {
        changeListeners = new ArrayList<ChangeSetListener>(changeListeners);
        changeListeners.add(listener);
    }

    public void removeChangeListener(ChangeSetListener listener) {
        changeListeners = new ArrayList<ChangeSetListener>(changeListeners);
        boolean isRemoved = changeListeners.remove(listener);
        Utils.beginRemove();
        if (!isRemoved) {
            throw new RuntimeException("Listener not found, cannot be removed: " + listener);
        }
        Utils.endRemove();
    }

    static boolean dump = false;

    private void notifyListeners(boolean applyTriggers) {
        if (bulkDispatchingModeLevel > 0) {
            return;
        }
        if (!this.changeSetToDispatch.isEmpty()) {
            MutableChangeSet currentChangeSetToDispatch = this.changeSetToDispatch;

            try {
                bulkDispatchingModeLevel++;

                if (applyTriggers) {
//          Thread.dumpStack();
                    if (dump) {
                        System.err.println("DefaultGlobRepository.notifyListeners " + changeSetToDispatch);
                    }
                    for (ChangeSetListener trigger : triggers) {
                        this.changeSetToDispatch = new DefaultChangeSet();
                        trigger.globsChanged(currentChangeSetToDispatch, this);
                        currentChangeSetToDispatch.merge(changeSetToDispatch);
                        if (dump && !changeSetToDispatch.isEmpty()) {
                            System.err.println(trigger + " : " + changeSetToDispatch);
                        }
                    }
                    if (dump) {
                        System.err.println("------------------------------------------------------------");
                    }
                }
            }
            finally {
                bulkDispatchingModeLevel--;
            }

            this.changeSetToDispatch = new DefaultChangeSet();
            for (ChangeSetListener listener : changeListeners) {
                listener.globsChanged(currentChangeSetToDispatch, this);
            }
            pendingDeletions.clear();
        }
        for (InvokeAction action : actions) {
            action.run();
        }
        actions.clear();
    }

    public void addTrigger(ChangeSetListener trigger) {
        triggers.add(trigger);
    }

    public void addTriggerAtFirst(ChangeSetListener trigger) {
        triggers.add(0, trigger);
    }

    public void removeTrigger(ChangeSetListener trigger) {
        triggers.remove(trigger);
    }

    private MutableGlob getGlobForUpdate(Key key) throws ItemNotFound, OperationDenied {
        Glob glob = globs.get(key.getGlobType(), key);
        if (glob == null) {
            throw new ItemNotFound("Object '" + key + "' does not exist");
        }

        try {
            return (MutableGlob)glob;
        }
        catch (ClassCastException e) {
            throw new OperationDenied("Object '" + key + "' cannot be modified");
        }
    }

    private void checkKeyDoesNotExist(Key key) throws ItemAlreadyExists {
        if (globs.containsKey(key.getGlobType(), key)) {
            throw new ItemAlreadyExists("An object with key " + key + " already exists");
        }
    }

//  public String toString() {
//    StringWriter writer = new StringWriter();
//    GlobList globs = getAll();
//    Collections.sort(globs, new Comparator<Glob>() {
//      public int compare(Glob glob1, Glob glob2) {
//        return glob1.getKey().toString().compareTo(glob2.getKey().toString());
//      }
//    });
//    XmlGlobWriter.write(globs, this, writer);
//    return writer.toString();
//  }

    private class ChangeSetExecutor implements ChangeSetVisitor {
        public void visitCreation(Key key, FieldValues values) {
            create(key, values.toArray());
        }

        public void visitUpdate(Key key, FieldValuesWithPrevious values) {
            update(key, values.toArray());
        }

        public void visitDeletion(Key key, FieldValues values) {
            delete(key);
        }
    }

    private class LinkFieldMappingFunction implements FieldMappingFunction {
        private final Key targetKey;
        private final MutableGlob sourceGlob;
        private final Key sourceKey;
        private boolean hasChange = false;

        public LinkFieldMappingFunction(Key targetKey, MutableGlob sourceGlob, Key sourceKey) {
            this.targetKey = targetKey;
            this.sourceGlob = sourceGlob;
            this.sourceKey = sourceKey;
        }

        public void process(Field sourceField, Field targetField) {
            Object value = targetKey != null ? targetKey.getValue(targetField) : null;
            Object previousValue = sourceGlob.getValue(sourceField);
            if (Utils.equal(previousValue, value)) {
                return;
            }
            sourceGlob.setValue(sourceField, value);
            hasChange = true;
            IndexTables indexTables = indexManager.getAssociatedTable(sourceField);
            if (indexTables != null) {
                indexTables.add(value, sourceGlob, sourceField, previousValue);
            }
            changeSetToDispatch.processUpdate(sourceKey, sourceField, value, previousValue);
        }

        public boolean hasChange() {
            return hasChange;
        }
    }
}
