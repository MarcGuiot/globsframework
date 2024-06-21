package org.globsframework.model;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.fields.FieldValueVisitor;

public interface FieldsValueScanner {
    static FieldsValueScanner from(FieldValue[] fieldValues) {
        return new FieldsValueScanner() {

            public <T extends FieldValues.Functor> T apply(T functor) throws Exception {
                for (FieldValue fieldValue : fieldValues) {
                    functor.process(fieldValue.getField(), fieldValue.getValue());
                }
                return functor;
            }

            public <T extends FieldValueVisitor> T accept(T functor) throws Exception {
                for (FieldValue fieldValue : fieldValues) {
                    fieldValue.getField().safeAccept(functor, fieldValue.getValue());
                }
                return functor;
            }
        };
    }

    <T extends FieldValues.Functor> T apply(T functor) throws Exception;

    default <T extends FieldValues.Functor> T safeApply(T functor) {
        try {
            return apply(functor);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    <T extends FieldValueVisitor> T accept(T functor) throws Exception;

    default <T extends FieldValueVisitor> T safeAccept(T functor) {
        try {
            return accept(functor);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default FieldsValueScanner withoutKey() {
        if (this instanceof WithoutKeyFieldsValueScanner) {
            return this;
        }
        return new WithoutKeyFieldsValueScanner(this);
    }

    default int size() {
        return safeApply(new FieldValues.Functor() {
            int count;

            public void process(Field field, Object value) throws Exception {
                count++;
            }
        }).count;
    }

    class WithoutKeyFieldsValueScanner implements FieldsValueScanner {
        private FieldsValueScanner valueScanner;

        public WithoutKeyFieldsValueScanner(FieldsValueScanner valueScanner) {
            this.valueScanner = valueScanner;
        }

        public <T extends FieldValues.Functor> T apply(T functor) throws Exception {
            valueScanner.apply(functor.withoutKeyField());
            return functor;
        }

        public <T extends FieldValues.Functor> T safeApply(T functor) {
            valueScanner.safeApply(functor.withoutKeyField());
            return functor;
        }


        public <T extends FieldValueVisitor> T accept(T functor) throws Exception {
            valueScanner.accept(functor.withoutKey());
            return functor;
        }

        public <T extends FieldValueVisitor> T safeAccept(T functor) {
            valueScanner.safeAccept(functor.withoutKey());
            return functor;
        }
    }

    default Glob toGlob(Key key) {
        return safeApply(new FieldValues.Functor() {
            MutableGlob glob;

            {
                glob = key.getGlobType().instantiate();
                key.safeApplyOnKeyField(this);
            }

            public void process(Field field, Object value) throws Exception {
                glob.setValue(field, value);
            }
        }).glob;
    }
}
