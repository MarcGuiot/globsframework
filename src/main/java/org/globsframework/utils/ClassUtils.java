package org.globsframework.utils;

import org.globsframework.utils.exceptions.InvalidParameter;

import java.util.Arrays;

public class ClassUtils {
  private ClassUtils() {
  }

  public static <T> T createFromClassName(String className) throws InvalidParameter, ClassNotFoundException {
    if ((className == null) || (className.length() == 0)) {
      throw new InvalidParameter("Class name should not be empty");
    }
    Class<?> aClass = Class.forName(className);
    try {
      return (T)aClass.newInstance();
    }
    catch (Exception e) {
      throw new InvalidParameter("Class '" + className + "' should have a " +
                                 "default constructor", e);
    }
  }
}
