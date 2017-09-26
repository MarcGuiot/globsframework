package org.globsframework.utils.comparators;

import java.util.Comparator;

/**
 * Compares two strings according to a "natural" order, which is not strictly alphabetical.
 * For instance :
 * <pre>
 * 1 < 10
 * 2 < 10
 * v1 < v10
 * v2 < v10
 * etc.
 * </pre>
 * The sorting applies even to large numbers :
 * <pre>
 * v11111111111111111111999999999911111111111111 < v11111111111111111111999999999911111111111111.3
 * </pre>
 */
public class StringComparator implements Comparator {

  private static StringComparator singleton;

  private StringComparator() {
  }

  public static StringComparator instance() {
    if (singleton == null) {
      singleton = new StringComparator();
    }
    return singleton;
  }

  public int compare(Object o1, Object o2) {
    return compareStatic(o1, o2);
  }

  private static int compareStatic(Object o1, Object o2) {
    if (o1 == o2) {
      return 0;
    }
    if (o1 == null) {
      return -1;
    }
    else if (o2 == null) {
      return 1;
    }
    char[] array1 = o1.toString().toCharArray();
    char[] array2 = o2.toString().toCharArray();
    int length1 = array1.length;
    int length2 = array2.length;
    for (int index1 = 0, index2 = 0; (index1 < array1.length) && (index2 < array2.length);) {
      char char1 = array1[index1];
      char char2 = array2[index2];
      if (Character.isDigit(char1) && Character.isDigit(char2)
          && (char1 != '0') && (char2 != '0')) {
        int digitsLength1 = 0;
        int startIndex1 = index1;
        int startIndex2 = index2;
        while ((index1 < array1.length) && Character.isDigit(array1[index1])) {
          digitsLength1++;
          char1 = array1[index1];
          index1++;
        }

        int digitsLength2 = 0;
        while ((index2 < array2.length) && Character.isDigit(array2[index2])) {
          digitsLength2++;
          char2 = array2[index2];
          index2++;
        }

        if (digitsLength1 < digitsLength2) {
          return -1;
        }
        else if (digitsLength1 > digitsLength2) {
          return 1;
        }
        else {
          for (int i = 0; i < digitsLength1; i++) {
            if (array1[startIndex1 + i] < array2[startIndex2 + i]) {
              return -1;
            }
            else if (array1[startIndex1 + i] > array2[startIndex2 + i]) {
              return 1;
            }
          }
        }
      }
      else if (char1 < char2) {
        return -1;
      }
      else if (char1 > char2) {
        return 1;
      }
      else {
        index1++;
        index2++;
      }
    } // end "for"
    if (length1 < length2) {
      return -1;
    }
    else if (length1 > length2) {
      return 1;
    }
    return 0;
  }
}