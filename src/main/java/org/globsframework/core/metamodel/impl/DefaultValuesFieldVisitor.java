package org.globsframework.core.metamodel.impl;

import org.globsframework.core.metamodel.annotations.*;
import org.globsframework.core.metamodel.fields.*;
import org.globsframework.core.model.MutableGlob;

import java.math.BigDecimal;

public class DefaultValuesFieldVisitor extends FieldVisitorWithContext.AbstractFieldVisitor<MutableGlob> {
    public void visitInteger(IntegerField field, MutableGlob context) throws Exception {
        if (field.hasAnnotation(DefaultIntegerAnnotationType.UNIQUE_KEY)) {
            context.set(field.asIntegerField(), (int) field.getDefaultValue());
        }
    }

    public void visitDouble(DoubleField field, MutableGlob context) throws Exception {
        if (field.hasAnnotation(DefaultDoubleAnnotationType.UNIQUE_KEY)) {
            context.set(field.asDoubleField(), (double) field.getDefaultValue());
        }
    }

    public void visitString(StringField field, MutableGlob context) throws Exception {
        if (field.hasAnnotation(DefaultStringAnnotationType.UNIQUE_KEY)) {
            context.set(field.asStringField(), (String) field.getDefaultValue());
        }
    }

    public void visitBoolean(BooleanField field, MutableGlob context) throws Exception {
        if (field.hasAnnotation(DefaultBooleanAnnotationType.UNIQUE_KEY)) {
            context.set(field.asBooleanField(), (boolean) field.getDefaultValue());
        }
    }

    public void visitBigDecimal(BigDecimalField field, MutableGlob context) throws Exception {
        if (field.hasAnnotation(DefaultBigDecimalAnnotationType.UNIQUE_KEY)) {
            context.set(field.asBigDecimalField(), (BigDecimal) field.getDefaultValue());
        }

    }

    public void visitLong(LongField field, MutableGlob context) throws Exception {
        if (field.hasAnnotation(DefaultLongAnnotationType.UNIQUE_KEY)) {
            context.set(field.asLongField(), (long) field.getDefaultValue());
        }
    }
}
