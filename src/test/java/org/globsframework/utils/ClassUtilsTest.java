package org.globsframework.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassUtilsTest {
   @Test
   public void test() throws Exception {
      assertEquals("String", String.class.getSimpleName());
   }
}
