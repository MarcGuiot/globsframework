package org.globsframework.core.utils.container.specific;

import junit.framework.TestCase;
import org.globsframework.core.metamodel.GlobType;
import org.globsframework.core.metamodel.GlobTypeLoaderFactory;
import org.globsframework.core.metamodel.annotations.AutoIncrement;
import org.globsframework.core.metamodel.annotations.KeyField;
import org.globsframework.core.metamodel.fields.IntegerField;
import org.globsframework.core.model.Glob;
import org.globsframework.core.model.Key;
import org.globsframework.core.model.MutableGlob;
import org.globsframework.core.utils.NanoChrono;
import org.globsframework.core.utils.container.hash.HashContainer;
import org.junit.Assert;

import java.util.*;

public class HashMapGlobKeyContainerTest extends TestCase {

    static public class DummyObject {

        public static GlobType TYPE;

        @KeyField
        @AutoIncrement
        public static IntegerField ID1;

        @KeyField
        @AutoIncrement
        public static IntegerField ID2;

        @KeyField
        @AutoIncrement
        public static IntegerField ID3;

        static {
            GlobTypeLoaderFactory.createAndLoad(DummyObject.class);
        }
    }

    public void testInsert() throws Exception {
        List<Glob> globs = new ArrayList<>();
        for (int i = 1; i < 100000; i++) {
            MutableGlob instantiate = DummyObject.TYPE.instantiate().set(DummyObject.ID1, i % 10).set(DummyObject.ID2, i % 100).set(DummyObject.ID3, i);
            globs.add(instantiate);
        }
        HashContainer<Key, Glob> container = HashContainer.EMPTY_INSTANCE;
        Map<Key, Glob> expected = new HashMap<>();
        NanoChrono nanoChrono = new NanoChrono();
        int i = 0;
        for (Glob glob : globs) {
            container = container.put(glob.getKey(), glob);
            expected.put(glob.getKey(), glob);
            if ((++i) % 500 == 0) {
                checkSame(container, expected);
            }
        }
        System.out.println("HashMapGlobKeyContainerTest.testInsert " + nanoChrono.getElapsedTimeInMS() + " " + container.size());
    }

    public void testGet() throws Exception {
        List<Glob> globs = new ArrayList<>();
        for (int i = 1; i < 1000000; i++) {
            MutableGlob instantiate = DummyObject.TYPE.instantiate().set(DummyObject.ID1, i % 10).set(DummyObject.ID2, i % 100).set(DummyObject.ID3, i);
            globs.add(instantiate);
        }
        HashContainer<Key, Glob> container = new HashOneGlobKeyContainer();
//        Map<Key, Glob> container = new HashMap<>();
        for (Glob glob : globs) {
            container = container.put(glob.getKey(), glob);
//            container.put(glob.getKey(), glob);
        }
        NanoChrono nanoChrono = new NanoChrono();
        for (Glob glob : globs) {
            assertSame(glob, container.get(glob.getKey()));
        }
        System.out.println("HashMapGlobKeyContainerTest.testGet " + nanoChrono.getElapsedTimeInMS());
    }

    public void testRemove() throws Exception {
        List<Glob> globs = new ArrayList<>();
        for (int i = 1; i < 10000; i++) {
            MutableGlob instantiate = DummyObject.TYPE.instantiate().set(DummyObject.ID1, i % 10).set(DummyObject.ID2, i % 10).set(DummyObject.ID3, i);
            globs.add(instantiate);
        }
        HashContainer<Key, Glob> container = new HashOneGlobKeyContainer();
        Map<Key, Glob> ref = new HashMap<>();
        for (Glob glob : globs) {
            container = container.put(glob.getKey(), glob);
            ref.put(glob.getKey(), glob);
        }
        NanoChrono nanoChrono = new NanoChrono();
        int i = 0;
        for (Glob glob : globs) {
            Key key = glob.getKey();
            assertSame(glob, ref.remove(key));
            assertSame(glob, container.get(key));
            assertSame(glob, container.remove(key));
            assertNull(container.get(key));
            assertNull(container.remove(key));
            if ((++i) % 500 == 0) {
                checkSame(container, ref);
            }
        }
    }

    public void testRemoveOnApply() {
        runRamdomTest(10);
        runRamdomTest(100);
        runRamdomTest(1000);
        runRamdomTest(10000);
    }

    private void runRamdomTest(int size) {
        List<Glob> globs = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            MutableGlob instantiate = DummyObject.TYPE.instantiate().set(DummyObject.ID1, i % 10).set(DummyObject.ID2, i % 10).set(DummyObject.ID3, i);
            globs.add(instantiate);
        }
        HashContainer<Key, Glob> container = new HashOneGlobKeyContainer();
        Map<Key, Glob> ref = new HashMap<>();
        for (Glob glob : globs) {
            container = container.put(glob.getKey(), glob);
            ref.put(glob.getKey(), glob);
        }
        NanoChrono nanoChrono = new NanoChrono();
        int count = ref.size();
        for (int i = 0; i < 100; i++) {
            KeyGlobFunctorAndRemove functor = new KeyGlobFunctorAndRemove(ref);
            container.applyAndRemoveIfTrue(functor);
            Assert.assertEquals(count, functor.count);
            Assert.assertEquals(container.size(), functor.count - functor.removed);
            checkSame(container, ref);
            count = ref.size();
        }
    }

    private void checkSame(HashContainer<Key, Glob> container, Map<Key, Glob> expected) {
        assertEquals(container.size(), expected.size());
        for (Map.Entry<Key, Glob> keyGlobEntry : expected.entrySet()) {
            assertSame(keyGlobEntry.getValue(), container.get(keyGlobEntry.getKey()));
        }
        HashContainer.TwoElementIterator<Key, Glob> keyGlobTwoElementIterator = container.entryIterator();
        int i = 0;
        Set<Key> added = new HashSet<>();
        while (keyGlobTwoElementIterator.next()) {
            assertSame(expected.get(keyGlobTwoElementIterator.getKey()), keyGlobTwoElementIterator.getValue());
            assertTrue(added.add(keyGlobTwoElementIterator.getKey()));
            i++;
        }
        assertEquals(expected.size(), i);
    }

    private static class KeyGlobFunctorAndRemove implements HashContainer.FunctorAndRemove<Key, Glob> {
        int count;
        int removed;
        private Map<Key, Glob> ref;

        public KeyGlobFunctorAndRemove(Map<Key, Glob> ref) {
            this.ref = ref;
        }

        public boolean apply(Key key, Glob glob) {
            count++;
            boolean b = Math.random() > 0.9;
            if (b) {
                if (ref.remove(key) == null) {
                    throw new RuntimeException("Bug " + key + " already removed.");
                }
                removed++;
            }
            return b;
        }
    }
}
