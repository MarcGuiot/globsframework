package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.annotations.*;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.MutableGlob;

import java.math.BigDecimal;

public class DefaultValuesFieldVisitor implements FieldVisitorWithContext<MutableGlob> {
    @Override
    public void visitInteger(IntegerField field, MutableGlob context) throws Exception {
        if (field.hasAnnotation(DefaultIntegerAnnotationType.UNIQUE_KEY)) {
            context.set(field.asIntegerField(), (int) field.getDefaultValue());
        }
    }

    @Override
    public void visitIntegerArray(IntegerArrayField field, MutableGlob context) throws Exception {

    }

    @Override
    public void visitDouble(DoubleField field, MutableGlob context) throws Exception {
        if (field.hasAnnotation(DefaultDoubleAnnotationType.UNIQUE_KEY)) {
            context.set(field.asDoubleField(), (double) field.getDefaultValue());
        }
    }

    @Override
    public void visitDoubleArray(DoubleArrayField field, MutableGlob context) throws Exception {

    }

    @Override
    public void visitString(StringField field, MutableGlob context) throws Exception {
        if (field.hasAnnotation(DefaultStringAnnotationType.UNIQUE_KEY)) {
            context.set(field.asStringField(), (String) field.getDefaultValue());
        }
    }

    @Override
    public void visitStringArray(StringArrayField field, MutableGlob context) throws Exception {

    }

    @Override
    public void visitBoolean(BooleanField field, MutableGlob context) throws Exception {
        if (field.hasAnnotation(DefaultBooleanAnnotationType.UNIQUE_KEY)) {
            context.set(field.asBooleanField(), (boolean) field.getDefaultValue());
        }
    }

    @Override
    public void visitBooleanArray(BooleanArrayField field, MutableGlob context) throws Exception {

    }

    @Override
    public void visitBigDecimal(BigDecimalField field, MutableGlob context) throws Exception {
        if (field.hasAnnotation(DefaultBigDecimalAnnotationType.UNIQUE_KEY)) {
            context.set(field.asBigDecimalField(), (BigDecimal) field.getDefaultValue());
        }

    }

    @Override
    public void visitBigDecimalArray(BigDecimalArrayField field, MutableGlob context) throws Exception {

    }

    @Override
    public void visitLong(LongField field, MutableGlob context) throws Exception {
        if (field.hasAnnotation(DefaultLongAnnotationType.UNIQUE_KEY)) {
            context.set(field.asLongField(), (long) field.getDefaultValue());
        }
    }

    @Override
    public void visitLongArray(LongArrayField field, MutableGlob context) throws Exception {

    }

    @Override
    public void visitDate(DateField field, MutableGlob context) throws Exception {
    }

    @Override
    public void visitDateTime(DateTimeField field, MutableGlob context) throws Exception {

    }

    @Override
    public void visitBlob(BlobField field, MutableGlob context) throws Exception {

    }

    @Override
    public void visitGlob(GlobField field, MutableGlob context) throws Exception {

    }

    @Override
    public void visitGlobArray(GlobArrayField field, MutableGlob context) throws Exception {

    }

    @Override
    public void visitUnionGlob(GlobUnionField field, MutableGlob context) throws Exception {

    }

    @Override
    public void visitUnionGlobArray(GlobArrayUnionField field, MutableGlob context) throws Exception {

    }
}
