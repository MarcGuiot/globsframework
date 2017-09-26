package org.globsframework.utils.comparators;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertTrue;

public class StringComparatorTest {

   private Comparator comparator = StringComparator.instance();

   @Test
   public void testComparisonWithNumbersOnly() {
      checkFirstIsLessThanSecond("0", "1");
      checkFirstIsLessThanSecond("1", "10");
      checkFirstIsLessThanSecond("2", "10");
      checkFirstIsLessThanSecond("0", "00001");
      checkFirstIsLessThanSecond("00001", "1");
      checkFirstIsLessThanSecond("123", "456");
      checkFirstIsLessThanSecond("104", "993");
      checkFirstIsLessThanSecond("407", "777");
      checkFirstIsLessThanSecond("56", "123");
      checkFirstIsLessThanSecond("407", "777");
      checkFirstIsLessThanSecond("000564", "0123");
   }

   @Test
   public void testComparisonWithLettersOnly() {
      checkFirstIsLessThanSecond("", "a");
      checkFirstIsLessThanSecond("aa", "ab");
      checkFirstIsLessThanSecond("aa", "bb");
      checkFirstIsLessThanSecond("A", "a");
   }

   @Test
   public void testComparisonWithLettersAtEnd() {
      checkFirstIsLessThanSecond("0a", "1a");
      checkFirstIsLessThanSecond("10aa", "10aaa");
      checkFirstIsLessThanSecond("2a", "11a");
   }

   @Test
   public void testComparisonWithDecimalValues() {
      checkFirstIsLessThanSecond("1.32", "1.33");
      checkFirstIsLessThanSecond("2.32", "2.111");
      checkFirstIsLessThanSecond("2.32", "10.32");
      checkFirstIsLessThanSecond("3.01", "3.12");
      checkFirstIsLessThanSecond("3.012345", "3.0123456");
      checkFirstIsLessThanSecond("3.0005", "3.0123456");
      checkFirstIsLessThanSecond("3.000564", "3.0123");
   }

   @Test
   public void testComparisonWithVersionNumbers() throws Exception {
      checkFirstIsLessThanSecond("v1.2a", "v1.2b");
      checkFirstIsLessThanSecond("v1.2e", "v2.0.3b");
   }

   @Test
   public void testComparisonWithNumbersAtEnd() {
      checkFirstIsLessThanSecond("toto0", "toto1");
      checkFirstIsLessThanSecond("toto1", "toto10");
      checkFirstIsLessThanSecond("toto2", "toto10");
      checkFirstIsLessThanSecond("toto", "toto1");
      checkFirstIsLessThanSecond("TOTO56", "TOTO143");
      checkFirstIsLessThanSecond("TOTO0001", "TOTO143");
      checkFirstIsLessThanSecond("T0012", "T12");
      checkFirstIsLessThanSecond("T0012A", "T12A");
      checkFirstIsLessThanSecond("T0012B", "T12A");
      checkFirstIsLessThanSecond("T0012B", "T13B");
   }

   @Test
   public void testBigStrings() throws Exception {
      checkFirstIsLessThanSecond("toto11111111111111111111999999999911111111111111",
                                 "toto11111111111111111111999999999911111111111111.3");
      checkFirstIsLessThanSecond("toto111111111111111111119999999999111111a11111111.2",
                                 "toto111111111111111111119999999999111111a11111111.3");
   }

   @Test
   public void testComparisonWithBrackets() throws Exception {
      checkFirstIsLessThanSecond("AntennaAccess [02_00_01/BTS]", "CCP [02_00_01/BTS]");
   }

   @Test
   public void testComparisonWithNullValues() throws Exception {
      checkFirstIsLessThanSecond(null, "a");
      assertTrue(comparator.compare(null, null) == 0);
   }

   private void checkFirstIsLessThanSecond(String first, String second) {
      assertTrue(comparator.compare(first, second) < 0);
      assertTrue(comparator.compare(second, first) > 0);
   }
}
