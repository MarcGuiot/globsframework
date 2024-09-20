package org.globsframework.core.metamodel.impl;

import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.fields.*;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.GlobFactory;
import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.model.globaccessor.get.GlobGetAccessor;
import org.globsframework.core.model.globaccessor.get.impl.*;
import org.globsframework.core.model.globaccessor.set.GlobSetAccessor;
import org.globsframework.core.model.globaccessor.set.impl.*;
import org.globsframework.core.model.impl.DefaultGlob;
import org.globsframework.core.model.impl.DefaultGlob128;
import org.globsframework.core.model.impl.DefaultGlob64;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class DefaultGlobFactory implements GlobFactory {
    private final GlobType type;
    private GlobGetAccessor[] getAccessor;
    private GlobSetAccessor[] setAccessor;

    public DefaultGlobFactory(GlobType type) {
        this.type = type;
    }

    private void initAccessor(GlobType type) {
        GlobGetAccessor[] getAccessor1 = new GlobGetAccessor[type.getFieldCount()];
        GetAccessorValueVisitor getAccessorValueVisitor = new GetAccessorValueVisitor();
        SetAccessorValueVisitor setAccessorValueVisitor = new SetAccessorValueVisitor();
        GlobSetAccessor[] setAccessor1 = new GlobSetAccessor[type.getFieldCount()];
        for (Field field : type.getFields()) {
            getAccessor1[field.getIndex()] = field.safeAccept(getAccessorValueVisitor).getAccessor;
            setAccessor1[field.getIndex()] = field.safeAccept(setAccessorValueVisitor).setAccessor;
        }

        synchronized (this) {  //I don't know if this enough
            getAccessor = getAccessor1;
            setAccessor = setAccessor1;
        }
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
        if (setAccessor == null) {
            initAccessor(type);
        }
        return setAccessor[field.getIndex()];
    }

    public GlobGetAccessor getGetValueAccessor(Field field) {
        if (getAccessor == null) {
            initAccessor(type);
        }
        return getAccessor[field.getIndex()];
    }

    private static class GetAccessorValueVisitor implements FieldVisitor {
        public GlobGetAccessor getAccessor;

        public void visitInteger(IntegerField field) {
            getAccessor = new AbstractGlobGetIntAccessor() {
                public Integer get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitIntegerArray(IntegerArrayField field) {
            getAccessor = new AbstractGlobGetIntArrayAccessor() {
                public int[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitDouble(DoubleField field) {
            getAccessor = new AbstractGlobGetDoubleAccessor() {
                public Double get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitDoubleArray(DoubleArrayField field) {
            getAccessor = new AbstractGlobGetDoubleArrayAccessor() {
                public double[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitBigDecimal(BigDecimalField field) {
            getAccessor = new AbstractGlobGetBigDecimalAccessor() {
                public BigDecimal get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitBigDecimalArray(BigDecimalArrayField field) {
            getAccessor = new AbstractGlobGetBigDecimalArrayAccessor() {
                public BigDecimal[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitString(StringField field) {
            getAccessor = new AbstractGlobGetStringAccessor() {
                public String get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitStringArray(StringArrayField field) {
            getAccessor = new AbstractGlobGetStringArrayAccessor() {
                public String[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitBoolean(BooleanField field) {
            getAccessor = new AbstractGlobGetBooleanAccessor() {
                public Boolean get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitBooleanArray(BooleanArrayField field) {
            getAccessor = new AbstractGlobGetBooleanArrayAccessor() {
                public boolean[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitLong(LongField field) {
            getAccessor = new AbstractGlobGetLongAccessor() {
                public Long get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitLongArray(LongArrayField field) {
            getAccessor = new AbstractGlobGetLongArrayAccessor() {
                public long[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitDate(DateField field) {
            getAccessor = new AbstractGlobGetDateAccessor() {
                public LocalDate get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitDateTime(DateTimeField field) {
            getAccessor = new AbstractGlobGetDateTimeAccessor() {
                public ZonedDateTime get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitBlob(BlobField field) {
            getAccessor = new AbstractGlobGetBytesAccessor() {
                public byte[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitGlob(GlobField field) {
            getAccessor = new AbstractGlobGetGlobAccessor() {
                public Glob get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitGlobArray(GlobArrayField field) {
            getAccessor = new AbstractGlobGetGlobArrayAccessor() {
                public Glob[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitUnionGlob(GlobUnionField field) throws Exception {
            getAccessor = new AbstractGlobGetGlobUnionAccessor() {
                public Glob get(Glob glob) {
                    return glob.get(field);
                }
            };
        }

        public void visitUnionGlobArray(GlobArrayUnionField field) throws Exception {
            getAccessor = new AbstractGlobGetGlobUnionArrayAccessor() {
                public Glob[] get(Glob glob) {
                    return glob.get(field);
                }
            };
        }
    }

    private static class SetAccessorValueVisitor implements FieldVisitor {
        public GlobSetAccessor setAccessor;

        public void visitInteger(IntegerField field) {
            setAccessor = new AbstractGlobSetIntAccessor() {
                public void set(MutableGlob glob, Integer value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitIntegerArray(IntegerArrayField field) {
            setAccessor = new AbstractGlobSetIntArrayAccessor() {
                public void set(MutableGlob glob, int[] value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitDouble(DoubleField field) {
            setAccessor = new AbstractGlobSetDoubleAccessor() {
                public void set(MutableGlob glob, Double value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitDoubleArray(DoubleArrayField field) {
            setAccessor = new AbstractGlobSetDoubleArrayAccessor() {
                public void set(MutableGlob glob, double[] value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitBigDecimal(BigDecimalField field) {
            setAccessor = new AbstractGlobSetBigDecimalAccessor() {
                public void set(MutableGlob glob, BigDecimal value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitBigDecimalArray(BigDecimalArrayField field) {
            setAccessor = new AbstractGlobSetBigDecimalArrayAccessor() {
                public void set(MutableGlob glob, BigDecimal[] value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitString(StringField field) {
            setAccessor = new AbstractGlobSetStringAccessor() {
                public void set(MutableGlob glob, String value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitStringArray(StringArrayField field) {
            setAccessor = new AbstractGlobSetStringArrayAccessor() {
                public void set(MutableGlob glob, String[] value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitBoolean(BooleanField field) {
            setAccessor = new AbstractGlobSetBooleanAccessor() {
                public void set(MutableGlob glob, Boolean value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitBooleanArray(BooleanArrayField field) {
            setAccessor = new AbstractGlobSetBooleanArrayAccessor() {
                public void set(MutableGlob glob, boolean[] value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitLong(LongField field) {
            setAccessor = new AbstractGlobSetLongAccessor() {
                public void set(MutableGlob glob, Long value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitLongArray(LongArrayField field) {
            setAccessor = new AbstractGlobSetLongArrayAccessor() {
                public void set(MutableGlob glob, long[] value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitDate(DateField field) {
            setAccessor = new AbstractGlobSetDateAccessor() {
                public void set(MutableGlob glob, LocalDate value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitDateTime(DateTimeField field) {
            setAccessor = new AbstractGlobSetDateTimeAccessor() {
                public void set(MutableGlob glob, ZonedDateTime value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitBlob(BlobField field) {
            setAccessor = new AbstractGlobSetBytesAccessor() {
                public void set(MutableGlob glob, byte[] value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitGlob(GlobField field) {
            setAccessor = new AbstractGlobSetGlobAccessor() {
                public void set(MutableGlob glob, Glob value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitGlobArray(GlobArrayField field) {
            setAccessor = new AbstractGlobSetGlobArrayAccessor() {
                public void set(MutableGlob glob, Glob[] value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitUnionGlob(GlobUnionField field) throws Exception {
            setAccessor = new AbstractGlobSetGlobUnionAccessor() {
                public void set(MutableGlob glob, Glob value) {
                    glob.set(field, value);
                }
            };
        }

        public void visitUnionGlobArray(GlobArrayUnionField field) throws Exception {
            setAccessor = new AbstractGlobSetGlobUnionArrayAccessor() {
                public void set(MutableGlob glob, Glob[] value) {
                    glob.set(field, value);
                }
            };
        }
    }
}
