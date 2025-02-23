package org.globsframework.core.utils;

import org.globsframework.core.metamodel.DummyObjectWithDefaultValues;
import org.globsframework.core.metamodel.fields.Field;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@Warmup(iterations = 2, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 3, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class FastStringToFieldContainerTest {
    private FastStringToFieldContainer fastStringToFieldContainer;

    @Setup
    public void setUp() throws Exception {
        fastStringToFieldContainer = new FastStringToFieldContainer(DummyObjectWithDefaultValues.TYPE.getFields());
    }

    @Benchmark
    public void test(Blackhole bh) {
        for (Field field : DummyObjectWithDefaultValues.TYPE.getFields()) {
            bh.consume(fastStringToFieldContainer.get(field.getName()));
//                bh.consume(DummyObjectWithDefaultValues.TYPE.getField(field.getName()));
        }
    }
}
