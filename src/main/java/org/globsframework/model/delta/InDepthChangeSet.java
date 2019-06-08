package org.globsframework.model.delta;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.GlobArrayField;
import org.globsframework.metamodel.fields.GlobArrayUnionField;
import org.globsframework.metamodel.fields.GlobField;
import org.globsframework.metamodel.fields.GlobUnionField;
import org.globsframework.model.FieldValues;
import org.globsframework.model.FieldValuesWithPrevious;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

public class InDepthChangeSet extends DefaultChangeSet {

    protected DefaultDeltaGlob createDeltaGlob(Key key) {
        return new DefaultDeltaGlob(key);
    }

    public void processCreation(Key key, FieldValues values) {
        super.processCreation(key, values);
        process(values, key.getGlobType());
    }

    public void processCreation(GlobType type, FieldValues values) {
        super.processCreation(type, values);
        process(values, type);
    }

    private void process(FieldValues values, GlobType globType) {
        Field[] fields = globType.getFields();
        for (Field field : fields) {
            if (field instanceof GlobField) {
                Glob glob = values.get(((GlobField) field));
                if (glob != null) {
                    DefaultDeltaGlob glob1 = getGlob(glob.getKey());
                    if (!glob1.isModified()) {
                        processCreation(glob1.getKey(), glob);
                    }
                }
            } else if (field instanceof GlobUnionField) {
                Glob glob = values.get(((GlobUnionField) field));
                if (glob != null) {
                    DefaultDeltaGlob glob1 = getGlob(glob.getKey());
                    if (!glob1.isModified()) {
                        processCreation(glob1.getKey(), glob);
                    }
                }
            } else if (field instanceof GlobArrayUnionField) {
                Glob[] globs = values.get(((GlobArrayUnionField) field));
                if (globs != null) {
                    for (Glob glob : globs) {
                        DefaultDeltaGlob glob1 = getGlob(glob.getKey());
                        if (!glob1.isModified()) {
                            processCreation(glob1.getKey(), glob);
                        }
                    }
                }
            } else if (field instanceof GlobArrayField) {
                Glob[] globs = values.get(((GlobArrayField) field));
                if (globs != null) {
                    for (Glob glob : globs) {
                        DefaultDeltaGlob glob1 = getGlob(glob.getKey());
                        if (!glob1.isModified()) {
                            processCreation(glob1.getKey(), glob);
                        }
                    }
                }
            }
        }
    }

    public void processUpdate(Key key, Field field, Object newValue, Object previousValue) {
        super.processUpdate(key, field, newValue, previousValue);

        propagateChanges(field, newValue, previousValue);
    }

    private void propagateChanges(Field field, Object newValue, Object previousValue) {
        if (field instanceof GlobField) {
            if (previousValue != null) {
                DefaultDeltaGlob glob1 = getGlob(((Glob) previousValue).getKey());
                if (!glob1.isModified()) {
                    processDeletion(glob1.getKey(), ((Glob) previousValue));
                }
            }
            if (newValue != null) {
                DefaultDeltaGlob glob1 = getGlob(((Glob) newValue).getKey());
                if (!glob1.isModified()) {
                    processCreation(glob1.getKey(), ((Glob) newValue));
                }
            }
        } else if (field instanceof GlobUnionField) {
            if (previousValue != null) {
                DefaultDeltaGlob glob1 = getGlob(((Glob) previousValue).getKey());
                if (!glob1.isModified()) {
                    processDeletion(glob1.getKey(), (Glob) previousValue);
                }
            }
            if (newValue != null) {
                DefaultDeltaGlob glob1 = getGlob(((Glob) newValue).getKey());
                if (!glob1.isModified()) {
                    processCreation(glob1.getKey(), (Glob) newValue);
                }
            }
        } else if (field instanceof GlobArrayUnionField) {
            if (previousValue != null) {
                for (Glob glob : ((Glob[]) previousValue)) {
                    DefaultDeltaGlob glob1 = getGlob(glob.getKey());
                    if (!glob1.isModified()) {
                        processDeletion(glob1.getKey(), glob);
                    }
                }
            }
            if (newValue != null) {
                for (Glob glob : ((Glob[]) newValue)) {
                    DefaultDeltaGlob glob1 = getGlob(glob.getKey());
                    if (!glob1.isModified()) {
                        processCreation(glob1.getKey(), glob);
                    }
                }
            }
        } else if (field instanceof GlobArrayField) {
            if (previousValue != null) {
                for (Glob glob : ((Glob[]) previousValue)) {
                    DefaultDeltaGlob glob1 = getGlob(glob.getKey());
                    if (!glob1.isModified()) {
                        processDeletion(glob1.getKey(), glob);
                    }
                }
            }
            if (newValue != null) {
                for (Glob glob : ((Glob[]) newValue)) {
                    DefaultDeltaGlob glob1 = getGlob(glob.getKey());
                    if (!glob1.isModified()) {
                        processCreation(glob1.getKey(), glob);
                    }
                }
            }
        }
    }

    public void processUpdate(Key key, FieldValuesWithPrevious values) {
        super.processUpdate(key, values);
        values.safeApplyWithPreviousButKey(new FieldValuesWithPrevious.FunctorWithPrevious() {
            public void process(Field field, Object value, Object previousValue) throws Exception {
                propagateChanges(field, value, previousValue);
            }
        });
    }

    public void processDeletion(Key key, FieldValues values) {
        super.processDeletion(key, values);

        Field[] fields = key.getGlobType().getFields();
        for (Field field : fields) {
            if (field instanceof GlobField) {
                Glob glob = values.get(((GlobField) field));
                if (glob != null) {
                    DefaultDeltaGlob glob1 = getGlob(glob.getKey());
                    if (!glob1.isModified()) {
                        processDeletion(glob1.getKey(), glob);
                    }
                }
            } else if (field instanceof GlobUnionField) {
                Glob glob = values.get(((GlobUnionField) field));
                if (glob != null) {
                    DefaultDeltaGlob glob1 = getGlob(glob.getKey());
                    if (!glob1.isModified()) {
                        processDeletion(glob1.getKey(), glob);
                    }
                }
            } else if (field instanceof GlobArrayUnionField) {
                Glob[] globs = values.get(((GlobArrayUnionField) field));
                if (globs != null) {
                    for (Glob glob : globs) {
                        DefaultDeltaGlob glob1 = getGlob(glob.getKey());
                        if (!glob1.isModified()) {
                            processDeletion(glob1.getKey(), glob);
                        }
                    }
                }
            } else if (field instanceof GlobArrayField) {
                Glob[] globs = values.get(((GlobArrayField) field));
                if (globs != null) {
                    for (Glob glob : globs) {
                        DefaultDeltaGlob glob1 = getGlob(glob.getKey());
                        if (!glob1.isModified()) {
                            processDeletion(glob1.getKey(), glob);
                        }
                    }
                }
            }
        }
    }
}
