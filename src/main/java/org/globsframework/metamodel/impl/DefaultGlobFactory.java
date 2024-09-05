package org.globsframework.metamodel.impl;

import org.globsframework.metamodel.fields.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobFactory;
import org.globsframework.model.MutableGlob;
import org.globsframework.model.globaccessor.get.GlobGetAccessor;
import org.globsframework.model.globaccessor.get.impl.*;
import org.globsframework.model.globaccessor.set.GlobSetAccessor;
import org.globsframework.model.globaccessor.set.impl.*;
import org.globsframework.model.impl.DefaultGlob;
import org.globsframework.model.impl.DefaultGlob128;
import org.globsframework.model.impl.DefaultGlob64;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class DefaultGlobFactory implements GlobFactory {
    private final GlobType type;
    private final GlobGetAccessor[] getAccessor;
    private final GlobSetAccessor[] setAccessor;

    public DefaultGlobFactory(GlobType type) {
        this.type = type;
        getAccessor = new GlobGetAccessor[type.getFieldCount()];
        setAccessor = new GlobSetAccessor[type.getFieldCount()];
        GetAccessorValueVisitor getAccessorValueVisitor = new GetAccessorValueVisitor();
        type.streamFields().forEach(f -> getAccessor[f.getIndex()] = f.safeAccept(getAccessorValueVisitor).getAccessor);

        SetAccessorValueVisitor setAccessorValueVisitor = new SetAccessorValueVisitor();
        type.streamFields().forEach(f -> setAccessor[f.getIndex()] = f.safeAccept(setAccessorValueVisitor).setAccessor);
    }

    public MutableGlob create() {
        if (type.getFieldCount() <= 64) {
            return new DefaultGlob64(type);
        }
        if (type.getFieldCount() <= 128) {
            return new DefaultGlob128(type);
        }
        return new DefaultGlob(type);
    }

    public <T extends FieldVisitor> T accept(T visitor) throws Exception {
        Field[] fields = type.getFields();
        for (Field field : fields) {
            field.accept(visitor);
        }
        return visitor;
    }

    public <T extends FieldVisitorWithContext<C>, C> T accept(T visitor, C context) throws Exception {
        Field[] fields = type.getFields();
        for (Field field : fields) {
            field.accept(visitor, context);
        }
        return visitor;
    }

    public <T extends FieldVisitorWithTwoContext<C, D>, C, D> T accept(T visitor, C ctx1, D ctx2) throws Exception {
        Field[] fields = type.getFields();
        for (Field field : fields) {
            field.accept(visitor, ctx1, ctx2);
        }
        return visitor;
    }

    public GlobSetAccessor getSetValueAccessor(Field field) {
        return setAccessor[field.getIndex()];
    }

    public GlobGetAccessor getGetValueAccessor(Field field) {
        return getAccessor[field.getIndex()];
    }

    private static class GetAccessorValueVisitor implements FieldVisitor {
        public GlobGetAccessor getAccessor;

        @Override
        public void visitInteger(IntegerField field) {
            getAccessor = new AbstractGlobGetIntAccessor() {
                @Override
                public Integer get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitIntegerArray(IntegerArrayField field) {
            getAccessor = new AbstractGlobGetIntArrayAccessor() {
                @Override
                public int[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitDouble(DoubleField field) {
            getAccessor = new AbstractGlobGetDoubleAccessor() {
                @Override
                public Double get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitDoubleArray(DoubleArrayField field) {
            getAccessor = new AbstractGlobGetDoubleArrayAccessor() {
                @Override
                public double[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitBigDecimal(BigDecimalField field) {
            getAccessor = new AbstractGlobGetBigDecimalAccessor() {
                @Override
                public BigDecimal get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitBigDecimalArray(BigDecimalArrayField field) {
            getAccessor = new AbstractGlobGetBigDecimalArrayAccessor() {
                @Override
                public BigDecimal[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitString(StringField field) {
            getAccessor = new AbstractGlobGetStringAccessor() {
                @Override
                public String get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitStringArray(StringArrayField field) {
            getAccessor = new AbstractGlobGetStringArrayAccessor() {
                @Override
                public String[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitBoolean(BooleanField field) {
            getAccessor = new AbstractGlobGetBooleanAccessor() {
                @Override
                public Boolean get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitBooleanArray(BooleanArrayField field) {
            getAccessor = new AbstractGlobGetBooleanArrayAccessor() {
                @Override
                public boolean[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitLong(LongField field) {
            getAccessor = new AbstractGlobGetLongAccessor() {
                public Long get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitLongArray(LongArrayField field) {
            getAccessor = new AbstractGlobGetLongArrayAccessor() {
                @Override
                public long[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitDate(DateField field) {
            getAccessor = new AbstractGlobGetDateAccessor() {
                @Override
                public LocalDate get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitDateTime(DateTimeField field) {
            getAccessor = new AbstractGlobGetDateTimeAccessor() {
                @Override
                public ZonedDateTime get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitBlob(BlobField field) {
            getAccessor = new AbstractGlobGetBytesAccessor() {
                @Override
                public byte[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitGlob(GlobField field) {
            getAccessor = new AbstractGlobGetGlobAccessor() {
                @Override
                public Glob get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitGlobArray(GlobArrayField field) {
            getAccessor = new AbstractGlobGetGlobArrayAccessor() {
                @Override
                public Glob[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitUnionGlob(GlobUnionField field) throws Exception {
            getAccessor = new AbstractGlobGetGlobUnionAccessor() {
                @Override
                public Glob get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        @Override
        public void visitUnionGlobArray(GlobArrayUnionField field) throws Exception {
            getAccessor = new AbstractGlobGetGlobUnionArrayAccessor() {
                @Override
                public Glob[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }
    }

    private static class SetAccessorValueVisitor implements FieldVisitor {
        public GlobSetAccessor setAccessor;

        @Override
        public void visitInteger(IntegerField field) {
            setAccessor = new AbstractGlobSetIntAccessor() {
                @Override
                public void set(MutableGlob glob, Integer value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitIntegerArray(IntegerArrayField field) {
            setAccessor = new AbstractGlobSetIntArrayAccessor() {
                @Override
                public void set(MutableGlob glob, int[] value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitDouble(DoubleField field) {
            setAccessor = new AbstractGlobSetDoubleAccessor() {
                @Override
                public void set(MutableGlob glob, Double value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitDoubleArray(DoubleArrayField field) {
            setAccessor = new AbstractGlobSetDoubleArrayAccessor() {
                @Override
                public void set(MutableGlob glob, double[] value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitBigDecimal(BigDecimalField field) {
            setAccessor = new AbstractGlobSetBigDecimalAccessor() {

                @Override
                public void set(MutableGlob glob, BigDecimal value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitBigDecimalArray(BigDecimalArrayField field) {

            setAccessor = new AbstractGlobSetBigDecimalArrayAccessor() {
                @Override
                public void set(MutableGlob glob, BigDecimal[] value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitString(StringField field) {
            setAccessor = new AbstractGlobSetStringAccessor() {
                @Override
                public void set(MutableGlob glob, String value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitStringArray(StringArrayField field) {
            setAccessor = new AbstractGlobSetStringArrayAccessor() {
                @Override
                public void set(MutableGlob glob, String[] value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitBoolean(BooleanField field) {
            setAccessor = new AbstractGlobSetBooleanAccessor() {
                @Override
                public void set(MutableGlob glob, Boolean value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitBooleanArray(BooleanArrayField field) {
            setAccessor = new AbstractGlobSetBooleanArrayAccessor() {
                @Override
                public void set(MutableGlob glob, boolean[] value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitLong(LongField field) {
            setAccessor = new AbstractGlobSetLongAccessor() {
                @Override
                public void set(MutableGlob glob, Long value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitLongArray(LongArrayField field) {
            setAccessor = new AbstractGlobSetLongArrayAccessor() {
                @Override
                public void set(MutableGlob glob, long[] value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitDate(DateField field) {
            setAccessor = new AbstractGlobSetDateAccessor() {
                @Override
                public void set(MutableGlob glob, LocalDate value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitDateTime(DateTimeField field) {
            setAccessor = new AbstractGlobSetDateTimeAccessor() {
                @Override
                public void set(MutableGlob glob, ZonedDateTime value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitBlob(BlobField field) {
            setAccessor = new AbstractGlobSetBytesAccessor() {
                @Override
                public void set(MutableGlob glob, byte[] value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitGlob(GlobField field) {
            setAccessor = new AbstractGlobSetGlobAccessor() {
                @Override
                public void set(MutableGlob glob, Glob value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitGlobArray(GlobArrayField field) {
            setAccessor = new AbstractGlobSetGlobArrayAccessor() {
                @Override
                public void set(MutableGlob glob, Glob[] value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitUnionGlob(GlobUnionField field) throws Exception {
            setAccessor = new AbstractGlobSetGlobUnionAccessor() {
                @Override
                public void set(MutableGlob glob, Glob value) {
                    glob.set(field, value);
                }
            };
        }

        @Override
        public void visitUnionGlobArray(GlobArrayUnionField field) throws Exception {
            setAccessor = new AbstractGlobSetGlobUnionArrayAccessor() {
                @Override
                public void set(MutableGlob glob, Glob[] value) {
                    glob.set(field, value);
                }
            };
        }
    }
}
