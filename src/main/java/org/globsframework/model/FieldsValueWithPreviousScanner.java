package org.globsframework.model;

import org.globsframework.metamodel.fields.FieldValueVisitor;

public interface FieldsValueWithPreviousScanner extends FieldsValueScanner {
    <T extends FieldValueVisitor> T acceptOnPrevious(T functor) throws Exception;

    <T extends FieldValueVisitor> T safeAcceptOnPrevious(T functor);

    <T extends FieldValuesWithPrevious.FunctorWithPrevious> T applyWithPreviousButKey(T functor) throws Exception;

    <T extends FieldValuesWithPrevious.FunctorWithPrevious> T safeApplyWithPrevious(T functor);

    <T extends FieldValues.Functor> T applyOnPreviousButKey(T functor) throws Exception;

    default FieldsValueWithPreviousScanner withoutKey(){
        if (this instanceof WithoutKeyFieldsValueWithPreviousScanner) {
            return this;
        }
        return new WithoutKeyFieldsValueWithPreviousScanner(this);
    }

    class WithoutKeyFieldsValueWithPreviousScanner implements FieldsValueWithPreviousScanner {
        private FieldsValueWithPreviousScanner scanner;

        public WithoutKeyFieldsValueWithPreviousScanner(FieldsValueWithPreviousScanner scanner) {
            this.scanner = scanner;
        }

        public <T extends FieldValueVisitor> T acceptOnPrevious(T functor) throws Exception {
            scanner.acceptOnPrevious(functor.withoutKey());
            return functor;
        }

        public <T extends FieldValueVisitor> T safeAcceptOnPrevious(T functor) {
            scanner.safeAcceptOnPrevious(functor.withoutKey());
            return functor;
        }

        public <T extends FieldValuesWithPrevious.FunctorWithPrevious> T applyWithPreviousButKey(T functor) throws Exception {
            scanner.applyWithPreviousButKey(functor.previousWithoutKey());
            return functor;
        }

        public <T extends FieldValuesWithPrevious.FunctorWithPrevious> T safeApplyWithPrevious(T functor) {
            scanner.safeApplyWithPrevious(functor.previousWithoutKey());
            return functor;
        }

        public <T extends FieldValues.Functor> T applyOnPreviousButKey(T functor) throws Exception {
            scanner.applyOnPreviousButKey(functor.withoutKeyField());
            return functor;
        }

        public <T extends FieldValues.Functor> T apply(T functor) throws Exception {
            scanner.apply(functor.withoutKeyField());
            return functor;
        }

        public <T extends FieldValues.Functor> T safeApply(T functor) {
            scanner.safeApply(functor.withoutKeyField());
            return functor;
        }

        public <T extends FieldValueVisitor> T accept(T functor) throws Exception {
            scanner.accept(functor.withoutKey());
            return functor;
        }

        public <T extends FieldValueVisitor> T safeAccept(T functor) {
            scanner.safeAccept(functor.withoutKey());
            return functor;
        }
    }
}
