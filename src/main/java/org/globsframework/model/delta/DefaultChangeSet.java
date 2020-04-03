package org.globsframework.model.delta;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.model.*;
import org.globsframework.model.utils.BreakException;
import org.globsframework.model.utils.ChangeVisitor;
import org.globsframework.utils.Strings;
import org.globsframework.utils.collections.LinksMapOfMaps;
import org.globsframework.utils.collections.MapOfMaps;
import org.globsframework.utils.exceptions.InvalidState;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public class DefaultChangeSet implements MutableChangeSet {
    private MapOfMaps<GlobType, Key, DefaultDeltaGlob> deltaGlobsByKey;

    public DefaultChangeSet() {
        this(new LinksMapOfMaps<>());
    }

    private DefaultChangeSet(MapOfMaps<GlobType, Key, DefaultDeltaGlob> deltaGlobsByKey) {
        this.deltaGlobsByKey = deltaGlobsByKey;
    }

    static public DefaultChangeSet createOrdered() {
        return new DefaultChangeSet(new LinksMapOfMaps<>());
    }

    public void processCreation(Key key, FieldsValueScanner values) {
        DefaultDeltaGlob delta = getGlob(key);
        delta.processCreation(values);
        removeIfUnchanged(delta);
    }

    public void processCreation(GlobType type, FieldValues values) {
        processCreation(KeyBuilder.createFromValues(type, values), FieldValuesBuilder.removeKeyFields(values));
    }

    public void processUpdate(Key key, Field field, Object newValue, Object previousValue) {
        DefaultDeltaGlob delta = getGlob(key);
        delta.processUpdate(field, newValue, previousValue);
        removeIfUnchanged(delta);
    }

    public void processUpdate(Key key, FieldsValueWithPreviousScanner values) {
        final DefaultDeltaGlob delta = getGlob(key);
        FieldValuesWithPrevious.FunctorWithPrevious functor = new FieldValuesWithPrevious.FunctorWithPrevious() {
            public void process(Field field, Object value, Object previousValue) throws Exception {
                delta.processUpdate(field, value, previousValue);
                removeIfUnchanged(delta);
            }
        };
        values.safeApplyWithPrevious(functor.previousWithoutKey());
    }

    public void processDeletion(Key key, FieldsValueScanner values) {
        DefaultDeltaGlob delta = getGlob(key);
        delta.processDeletion(values);
        removeIfUnchanged(delta);
    }

    private void removeIfUnchanged(DefaultDeltaGlob delta) {
        if (!delta.isModified()) {
            deltaGlobsByKey.remove(delta.getKey().getGlobType(), delta.getKey());
        }
    }

    protected DefaultDeltaGlob getGlob(Key key) {
        DefaultDeltaGlob glob = deltaGlobsByKey.get(key.getGlobType(), key);
        if (glob == null) {
            glob = createDeltaGlob(key);
            deltaGlobsByKey.put(key.getGlobType(), key, glob);
        }
        return glob;
    }

    protected DefaultDeltaGlob createDeltaGlob(Key key) {
        return new DefaultDeltaGlob(key);
    }

    public void visit(ChangeSetVisitor visitor) throws Exception {
        Collection<DefaultDeltaGlob> values = deltaGlobsByKey.values();
        visit(visitor, values, DeltaState.DELETED);
        visit(visitor, values, DeltaState.UPDATED);
        visit(visitor, values, DeltaState.CREATED);
    }

    public void safeVisit(ChangeSetVisitor visitor) {
        try {
            visit(visitor);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void visit(GlobType type, ChangeSetVisitor visitor) throws Exception {
        Collection<DefaultDeltaGlob> values = deltaGlobsByKey.get(type).values();
        visit(visitor, values, DeltaState.DELETED);
        visit(visitor, values, DeltaState.UPDATED);
        visit(visitor, values, DeltaState.CREATED);
    }

    private void visit(ChangeSetVisitor visitor, Collection<DefaultDeltaGlob> globs, DeltaState state) throws Exception {
        for (DefaultDeltaGlob deltaGlob : globs) {
            if (deltaGlob.getState() == state) {
                deltaGlob.visit(visitor);
            }
        }
    }

    public void safeVisit(GlobType type, ChangeSetVisitor visitor) {
        try {
            visit(type, visitor);
        } catch (BreakException e) {
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void visit(Key key, ChangeSetVisitor visitor) throws Exception {
        DefaultDeltaGlob deltaGlob = deltaGlobsByKey.get(key.getGlobType(), key);
        if (deltaGlob != null) {
            deltaGlob.visit(visitor);
        }
    }

    public void safeVisit(Key key, ChangeSetVisitor visitor) {
        try {
            visit(key, visitor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean containsChanges(GlobType type) {
        return deltaGlobsByKey.contains(type) && !deltaGlobsByKey.get(type).isEmpty();
    }

    public GlobType[] getChangedTypes() {
        Set<GlobType> result = new HashSet<GlobType>();
        for (GlobType globType : deltaGlobsByKey.keys()) {
            if (!deltaGlobsByKey.get(globType).isEmpty()) {
                result.add(globType);
            }
        }
        return result.toArray(new GlobType[result.size()]);
    }

    public int getChangeCount() {
        return deltaGlobsByKey.size();
    }

    public int getChangeCount(GlobType type) {
        return deltaGlobsByKey.get(type).size();
    }

    public Set<Key> getChanged(GlobType type) {
        if (!deltaGlobsByKey.contains(type)) {
            return Collections.emptySet();
        }
        Set<Key> result = new HashSet<Key>();
        for (Map.Entry entry : deltaGlobsByKey.get(type).entrySet()) {
            result.add((Key) entry.getKey());
        }
        return result;
    }

    public Set<Key> getCreated(GlobType type) {
        if (!deltaGlobsByKey.contains(type)) {
            return Collections.emptySet();
        }
        Set<Key> result = new HashSet<Key>();
        for (Map.Entry entry : deltaGlobsByKey.get(type).entrySet()) {
            DefaultDeltaGlob delta = (DefaultDeltaGlob) entry.getValue();
            if (delta.isCreated()) {
                result.add((Key) entry.getKey());
            }
        }
        return result;
    }

    public Set<Key> getUpdated(GlobType type) {
        if (!deltaGlobsByKey.contains(type)) {
            return Collections.emptySet();
        }
        Set<Key> result = new HashSet<Key>();
        for (DefaultDeltaGlob delta : deltaGlobsByKey.get(type).values()) {
            if (delta.isUpdated()) {
                result.add(delta.getKey());
            }
        }
        return result;
    }

    public Set<Key> getCreatedOrUpdated(GlobType type) {
        if (!deltaGlobsByKey.contains(type)) {
            return Collections.emptySet();
        }
        Set<Key> result = new HashSet<Key>();
        for (DefaultDeltaGlob delta : deltaGlobsByKey.get(type).values()) {
            if (delta.isCreated() || delta.isUpdated()) {
                result.add(delta.getKey());
            }
        }
        return result;
    }

    public Set<Key> getUpdated(Field field) {
        if (!deltaGlobsByKey.contains(field.getGlobType())) {
            return Collections.emptySet();
        }
        Set<Key> result = new HashSet<Key>();
        for (DefaultDeltaGlob delta : deltaGlobsByKey.get(field.getGlobType()).values()) {
            if (delta.isUpdated(field)) {
                result.add(delta.getKey());
            }
        }
        return result;
    }

    public Set<Key> getDeleted(GlobType type) {
        if (!deltaGlobsByKey.contains(type)) {
            return Collections.emptySet();
        }
        Set<Key> result = new HashSet<Key>();
        for (DefaultDeltaGlob delta : deltaGlobsByKey.get(type).values()) {
            if (delta.isDeleted()) {
                result.add(delta.getKey());
            }
        }
        return result;
    }

    public boolean isCreated(Key key) {
        if (!deltaGlobsByKey.contains(key.getGlobType())) {
            return false;
        }
        DefaultDeltaGlob defaultDeltaGlob = deltaGlobsByKey.get(key.getGlobType(), key);
        return defaultDeltaGlob != null && defaultDeltaGlob.isCreated();
    }

    public boolean isDeleted(Key key) {
        if (!deltaGlobsByKey.contains(key.getGlobType())) {
            return false;
        }
        DefaultDeltaGlob defaultDeltaGlob = deltaGlobsByKey.get(key.getGlobType(), key);
        return defaultDeltaGlob != null && defaultDeltaGlob.isDeleted();
    }

    public FieldValues getPreviousValues(Key key) {
        Map<Key, DefaultDeltaGlob> keyDefaultDeltaGlobMap = deltaGlobsByKey.get(key.getGlobType());
        if (keyDefaultDeltaGlobMap != null) {
            DefaultDeltaGlob defaultDeltaGlob = keyDefaultDeltaGlobMap.get(key);
            return defaultDeltaGlob.getPreviousValues();
        }
        return null;
    }

    public FieldValues getNewValues(Key key) {
        Map<Key, DefaultDeltaGlob> keyDefaultDeltaGlobMap = deltaGlobsByKey.get(key.getGlobType());
        if (keyDefaultDeltaGlobMap != null) {
            DefaultDeltaGlob defaultDeltaGlob = keyDefaultDeltaGlobMap.get(key);
            return defaultDeltaGlob;
        }
        return null;
    }

    public boolean containsCreationsOrDeletions(GlobType type) {
        if (deltaGlobsByKey.contains(type)) {
            for (DefaultDeltaGlob deltaGlob : deltaGlobsByKey.get(type).values()) {
                if (deltaGlob.isCreated() || deltaGlob.isDeleted()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsCreations(GlobType type) {
        if (deltaGlobsByKey.contains(type)) {
            for (DefaultDeltaGlob deltaGlob : deltaGlobsByKey.get(type).values()) {
                if (deltaGlob.isCreated()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsDeletions(GlobType type) {
        if (deltaGlobsByKey.contains(type)) {
            for (DefaultDeltaGlob deltaGlob : deltaGlobsByKey.get(type).values()) {
                if (deltaGlob.isDeleted()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsUpdates(Field field) {
        GlobType type = field.getGlobType();
        if (deltaGlobsByKey.contains(type)) {
            for (DefaultDeltaGlob deltaGlob : deltaGlobsByKey.get(type).values()) {
                if (deltaGlob.isUpdated(field)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsChanges(Key key) {
        return deltaGlobsByKey.containsKey(key.getGlobType(), key);
    }

    public boolean containsChanges(Key key, Field... fields) {
        if (deltaGlobsByKey.contains(key.getGlobType())) {
            DefaultDeltaGlob glob = deltaGlobsByKey.get(key.getGlobType(), key);
            if (glob == null) {
                return false;
            }
            if (glob.isCreated() || glob.isDeleted()) {
                return true;
            }
            for (Field field : fields) {
                if (glob.isUpdated(field)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return deltaGlobsByKey.isEmpty();
    }

    public int size() {
        return deltaGlobsByKey.size();
    }

    public void merge(ChangeSet other) throws InvalidState {
        other.safeVisit(new ChangeSetVisitor() {
            public void visitCreation(Key key, FieldsValueScanner values) throws Exception {
                processCreation(key, values);
            }

            public void visitUpdate(final Key key, FieldsValueWithPreviousScanner values) throws Exception {
                FieldValuesWithPrevious.FunctorWithPrevious functor = new FieldValuesWithPrevious.FunctorWithPrevious() {
                    public void process(Field field, Object value, Object previousValue) throws IOException {
                        processUpdate(key, field, value, previousValue);
                    }
                };
                values.applyWithPrevious(functor.previousWithoutKey());
            }

            public void visitDeletion(Key key, FieldsValueScanner values) throws Exception {
                processDeletion(key, values);
            }
        });
    }

    public String toString() {
        try {
            StringWriter writer = new StringWriter();
            visit(new PrintChangeVisitor(writer));
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clear(Collection<GlobType> globTypes) {
        for (GlobType type : globTypes) {
            deltaGlobsByKey.removeAll(type);
        }
    }

    public ChangeSet reverse() {
        DefaultChangeSet result = new DefaultChangeSet();
        for (DefaultDeltaGlob delta : deltaGlobsByKey.values()) {
            DefaultDeltaGlob reverseDelta = delta.reverse();
            result.deltaGlobsByKey.put(reverseDelta.getType(), reverseDelta.getKey(), reverseDelta);
        }
        return result;
    }

    private static class PrintChangeVisitor implements ChangeVisitor, FieldValues.Functor {
        private final StringWriter writer;
        private String prefix = "";

        public PrintChangeVisitor(StringWriter writer) {
            this.writer = writer;
        }

        public void complete() {
        }

        public void visitCreation(Key key, FieldsValueScanner values) throws Exception {
            writer.write("create :");
            key.safeApplyOnKeyField(this);
            values.safeApply(this.withoutKeyField());
            writer.write("\n");
        }

        public void visitUpdate(Key key, FieldsValueWithPreviousScanner values) throws Exception {
            int startOffSet = writer.getBuffer().length();
            writer.write("update :");
            key.safeApplyOnKeyField(this);
            int endOffset = writer.getBuffer().length();
            values.safeApply(this.withoutKeyField());
            writer.append("\n");
            for (int i = 0; i < endOffset - startOffSet; i++) {
                writer.write(" ");
            }
            prefix = "_";
            values.applyOnPrevious(this.withoutKeyField());
            writer.write("\n");
            prefix = "";
        }

        public void visitDeletion(Key key, FieldsValueScanner previousValues) throws Exception {
            writer.write("delete :");
            key.safeApplyOnKeyField(this);
            previousValues.safeApply(this.withoutKeyField());
            writer.write("\n");
        }

        public void process(Field field, Object value) throws Exception {
            writer.write(" ");
            writer.write(prefix);
            writer.write(field.getName());
            writer.write("='");
            writer.write(Strings.toString(value));
            writer.write("'");
        }
    }
}