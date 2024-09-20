package org.globsframework.core.metamodel;

import org.globsframework.core.model.MutableGlob;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class GlobTypeInstantiateWithDefaultsTest {
    @Test
    public void test() {
        MutableGlob globWithDefaults = DummyObjectWithDefaultValues.TYPE.instantiateWithDefaults();

        assertEquals(globWithDefaults.get(DummyObjectWithDefaultValues.STRING), "Hello");
        assertEquals(globWithDefaults.get(DummyObjectWithDefaultValues.DOUBLE), Double.valueOf(3.14159265));
        assertEquals(globWithDefaults.get(DummyObjectWithDefaultValues.INTEGER), Integer.valueOf(7));
        assertEquals(globWithDefaults.get(DummyObjectWithDefaultValues.LONG), Long.valueOf(5));
        assertEquals(globWithDefaults.get(DummyObjectWithDefaultValues.BIG_DECIMAL), new BigDecimal("1.61803398875"));
        assertNull(globWithDefaults.get(DummyObjectWithDefaultValues.ID));
        assertNull(globWithDefaults.get(DummyObjectWithDefaultValues.LINK));
        assertTrue(globWithDefaults.get(DummyObjectWithDefaultValues.BOOLEAN));

        MutableGlob glob = DummyObjectWithDefaultValues.TYPE.instantiate();
        assertNull(glob.get(DummyObjectWithDefaultValues.STRING));
        assertNull(glob.get(DummyObjectWithDefaultValues.DOUBLE));
        assertNull(glob.get(DummyObjectWithDefaultValues.INTEGER));
        assertNull(glob.get(DummyObjectWithDefaultValues.LONG));
        assertNull(glob.get(DummyObjectWithDefaultValues.BIG_DECIMAL));
        assertNull(glob.get(DummyObjectWithDefaultValues.ID));
        assertNull(glob.get(DummyObjectWithDefaultValues.LINK));
        assertNull(glob.get(DummyObjectWithDefaultValues.BOOLEAN));

    }
}
