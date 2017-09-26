package org.globsframework.utils;

import junit.framework.AssertionFailedError;
import org.globsframework.metamodel.GlobType;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class TestUtils {

   public final static byte[] SAMPLE_BYTE_ARRAY;
   public static final String TMP_DIR = "tmp";

   private TestUtils() {
   }

   static {
      SAMPLE_BYTE_ARRAY = new byte[12];
      for (int i = 0; i < SAMPLE_BYTE_ARRAY.length; i++) {
         SAMPLE_BYTE_ARRAY[i] = 1;
      }
   }

   public static void assertFails(Runnable functor, Class<? extends Exception> expectedException) {
      try {
         functor.run();
      }
      catch (Exception e) {
         if (!e.getClass().isAssignableFrom(expectedException)) {
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            Assert.fail(expectedException.getName() + " expected but was " + e.getClass().getName() + "\n" +
                        writer.toString());
         }
      }
   }

   public static <T> void assertEquals(T[] expected, T... actual) {
      if (!Arrays.equals(expected, actual)) {
         Assert.fail("expected: \n" + Arrays.toString(expected) + "\n   but was: " + Arrays.toString(actual));
      }
   }

   public static void assertEquals(double precision, double[] actual, double... expected) {
      if (actual.length != expected.length) {
         Assert.fail("invalid length - expected: " + Arrays.toString(expected) + " but was: " + Arrays.toString(actual));
      }
      for (int i = 0; i < expected.length; i++) {
         if (Math.abs(expected[i] - actual[i]) > precision) {
            Assert.fail("error at position " + i + " - expected: " + Arrays.toString(expected) + " but was: " + Arrays.toString(actual));
         }
      }
   }

   public static void assertEquals(double[] actual, double... expected) {
      if (!Arrays.equals(expected, actual)) {
         Assert.fail("expected: " + Arrays.toString(expected) + " but was: " + Arrays.toString(actual));
      }
   }

   public static void assertEquals(int[] actual, int... expected) {
      if (!Arrays.equals(expected, actual)) {
         Assert.fail("expected: " + Arrays.toString(expected) + " but was: " + Arrays.toString(actual));
      }
   }

   public static <T> void assertIteratorContains(Iterator<T> iterator, T... values) throws Exception {
      if ((values.length == 0) && iterator.hasNext()) {
         Assert.fail("Expected empty iterator, but contains at least: " + iterator.next());
      }
      for (int i = 0; i < values.length; i++) {
         if (!iterator.hasNext()) {
            Assert.fail("Iterator has " + i + " elements instead of " + values.length);
         }
         T value = values[i];
         T nextValue = iterator.next();
         if (!Utils.equal(nextValue, value)) {
            Assert.fail("Error at index " + i + ": expected: " + value + " but was: " + nextValue);
         }
      }
      if (iterator.hasNext()) {
         Assert.fail("Iterator has more than " + values.length + " elements, at least: " + iterator.next());
      }
   }

   public static <T> void assertEmpty(Collection<T> list) {
      if (!list.isEmpty()) {
         Assert.fail("Expected an empty list, but contains: " + list);
      }
   }

   public static <T> void assertEmpty(Object[] array) {
      if (array.length != 0) {
         Assert.fail("Expected an empty array, but contains: " + Arrays.toString(array));
      }
   }

   public static void assertEquals(GlobType[] expected, GlobType[] actual) {
      assertEquals(Arrays.asList(expected), actual);
   }

   public static <T> void assertEquals(Collection<T> expected, Collection<T> actual) {
      if ((expected.isEmpty())) {
         assertEmpty(actual);
      }
      if (expected.size() != actual.size()) {
         showFailures("Invalid number of items", actual, expected);
      }
      Iterator actualIterator = actual.iterator();
      int index = 0;
      for (T anExpected : expected) {
         if (!Utils.equal(actualIterator.next(), anExpected)) {
            showFailures("Error at item " + index, actual, expected);
         }
         index++;
      }
   }

   public static <T> void assertEquals(Collection<T> actual, T... expected) {
      assertEquals(Arrays.asList(expected), actual);
   }

   public static <T> void assertSetEquals(T[] actual, T... expected) {
      assertSetEquals(Arrays.asList(actual), Arrays.asList(expected));
   }

   public static <T> void assertSetEquals(Collection<T> actual, T... expected) {
      assertSetEquals(actual, Arrays.asList(expected));
   }

   public static <T> void assertSetEquals(Collection<T> actual, Collection<T> expected) {
      Assert.assertEquals(new HashSet(expected), new HashSet(actual));
   }

   public static <T> void assertSetEquals(Iterator<T> actual, T... expected) {
      Set actualSet = new HashSet();
      while (actual.hasNext()) {
         actualSet.add(actual.next());
      }
      assertEquals(actualSet, new HashSet(Arrays.asList(expected)));
   }

   public static <T> void assertContained(T[] actualArray, T[] expectedItems) {
      List<T> actual = Arrays.asList(actualArray);
      List<T> expected = Arrays.asList(expectedItems);
      if (!actual.containsAll(expected)) {
         showFailures("Expected list is not contained in actual", actual, expected);
      }
   }

   public static <T> void assertContains(Collection<T> actual, T... expectedItems) {
      if (!actual.containsAll(Arrays.asList(expectedItems))) {
         Assert.fail("Collection: " + actual + "\n does not contain: " + Arrays.toString(expectedItems));
      }
   }

   public static <T> void assertNotContains(Collection<T> actual, T... expectedItems) {
      for (T item : expectedItems) {
         if (actual.contains(item)) {
            Assert.fail("Item '" + item + "' was found - actual collection: " + actual);
         }
      }
   }

   public static void assertDatesEqual(Date date1, Date date2, int margin) throws Exception {
      if (Math.abs(date1.getTime() - date2.getTime()) > margin) {
         Assert.assertEquals(date1, date2);
      }
   }

   private static <T> void showFailures(String message, Collection<T> actual, Collection<T> expected) {
      Assert.fail(message + "\n" +
                  "expected: " + expected + "\n" +
                  "but was:  " + actual);
   }

   public static void clearTmpDir() {
      File tmpDir = new File(TMP_DIR);
      tmpDir.mkdirs();
      for (File file : tmpDir.listFiles()) {
         file.delete();
      }
   }

   public static File getFileName(Object test, String extension) {
      try {
         return File.createTempFile(test.getClass().getSimpleName(), extension, new File(TMP_DIR));
      }
      catch (IOException e) {
         throw new RuntimeException("Unable to create file", e);
      }
   }

   public static <T> void checkEmpty(Collection<T> keys, String message) {
      if (!keys.isEmpty()) {
         StringBuilder text = new StringBuilder();
         text.append(message);
         for (Object key : keys) {
            text.append("\n").append(key);
         }
         Assert.fail(text.toString());
      }
   }

   public static void retry(Runnable runnable) throws Exception {
      long end = System.currentTimeMillis() + 3000;
      AssertionFailedError exception = null;
      while (System.currentTimeMillis() < end) {
         try {
            runnable.run();
            Thread.sleep(200);
            return;
         }
         catch (AssertionFailedError e) {
            exception = e;
         }
      }
      throw exception;
   }
}
