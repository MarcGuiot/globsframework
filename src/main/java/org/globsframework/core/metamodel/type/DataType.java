package org.globsframework.core.metamodel.type;

public enum DataType {
    String(true, false),
    StringArray(true, true),
    Double(true, false),
    DoubleArray(true, true),
    BigDecimal(true, false),
    BigDecimalArray(true, true),
    Long(true, false),
    LongArray(true, true),
    Integer(true, false),
    IntegerArray(true, true),
    Boolean(true, false),
    BooleanArray(true, true),
    Date(true, false),
    DateTime(true, false),
    Bytes(true, true),
    Glob(false, false),
    GlobArray(false, true),
    GlobUnion(false, false),
    GlobUnionArray(false, true);

    private final boolean isPrimive;
    private final boolean isArray;

    DataType(boolean isPrimive, boolean isArray) {
        this.isPrimive = isPrimive;
        this.isArray = isArray;
    }

    public boolean isPrimive() {
        return isPrimive;
    }

    public boolean isArray() {
        return isArray;
    }
}
