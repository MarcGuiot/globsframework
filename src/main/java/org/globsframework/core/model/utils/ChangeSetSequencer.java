package org.globsframework.core.model.utils;

import org.globsframework.core.metamodel.GlobModel;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.Field;
import org.globsframework.core.metamodel.utils.GlobTypeDependencies;
import org.globsframework.core.model.*;
import org.globsframework.core.utils.collections.MultiMap;

public class ChangeSetSequencer {

    public static void process(ChangeSet changeSet, GlobModel model, ChangeSetVisitor visitor)
            throws Exception {

        GlobTypeDependencies dependencies = model.getDependencies();

        final MultiMap<GlobType, Change> creations = new MultiMap<GlobType, Change>();
        final MultiMap<GlobType, ChangeWithPrevious> updates = new MultiMap<GlobType, ChangeWithPrevious>();
        final MultiMap<GlobType, Change> deletions = new MultiMap<GlobType, Change>();

        dispatchChanges(changeSet, creations, updates, deletions, dependencies);

        for (GlobType type : dependencies.getCreationSequence()) {
            for (Change change : creations.get(type)) {
                visitor.visitCreation(change.getKey(), change.getValues());
            }
        }

        for (GlobType type : dependencies.getUpdateSequence()) {
            for (ChangeWithPrevious change : updates.get(type)) {
                visitor.visitUpdate(change.getKey(), change.getValues());
            }
        }

        for (GlobType type : dependencies.getDeletionSequence()) {
            for (Change change : deletions.get(type)) {
                visitor.visitDeletion(change.getKey(), change.getValues());
            }
        }
    }

    private static void dispatchChanges(ChangeSet changeSet,
                                        final MultiMap<GlobType, Change> creations,
                                        final MultiMap<GlobType, ChangeWithPrevious> updates,
                                        final MultiMap<GlobType, Change> deletions,
                                        final GlobTypeDependencies dependencies) {
        changeSet.safeAccept(new ChangeSetVisitor() {
            public void visitCreation(Key key, FieldsValueScanner values) throws Exception {
                if (!dependencies.needsPostUpdate(key.getGlobType())) {
                    creations.put(key.getGlobType(), new Change(key, values));
                    return;
                }
                final FieldValuesBuilder builderForCreation = FieldValuesBuilder.init();
                final FieldValuesWithPreviousBuilder builderForUpdate = FieldValuesWithPreviousBuilder.init(key.getGlobType());
                values.apply(new FieldValues.Functor() {
                    public void process(Field field, Object value) throws Exception {
                        if (field.isRequired()) {
                            builderForCreation.setValue(field, value);
                        } else {
                            builderForUpdate.setValue(field, value, field.getDefaultValue());
                        }
                    }
                });
                creations.put(key.getGlobType(), new Change(key, builderForCreation.get()));
                FieldValuesWithPrevious valuesForUpdate = builderForUpdate.get();
                if (valuesForUpdate.size() > 0) {
                    updates.put(key.getGlobType(), new ChangeWithPrevious(key, valuesForUpdate));
                }
            }

            public void visitUpdate(Key key, FieldsValueWithPreviousScanner values) throws Exception {
                updates.put(key.getGlobType(), new ChangeWithPrevious(key, values));
            }

            public void visitDeletion(Key key, FieldsValueScanner values) throws Exception {
                deletions.put(key.getGlobType(), new Change(key, values));
            }
        });
    }

    private static class Change {
        private Key key;
        private FieldsValueScanner values;

        private Change(Key key, FieldsValueScanner values) {
            this.key = key;
            this.values = values;
        }

        public Key getKey() {
            return key;
        }

        public FieldsValueScanner getValues() {
            return values;
        }
    }

    private static class ChangeWithPrevious {

        private Key key;
        private FieldsValueWithPreviousScanner values;

        private ChangeWithPrevious(Key key, FieldsValueWithPreviousScanner values) {
            this.key = key;
            this.values = values;
        }

        public Key getKey() {
            return key;
        }

        public FieldsValueWithPreviousScanner getValues() {
            return values;
        }
    }
}
