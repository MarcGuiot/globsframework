package org.globsframework.utils;

public class Millis {
  public static final long ONE_SECOND = 1000;
  public static final long ONE_MINUTE = 60 * ONE_SECOND;
  public static final long ONE_HOUR = 60 * ONE_MINUTE;
  public static final long ONE_DAY = 24 * ONE_HOUR;

  public static long secondsToMillis(int seconds) {
    return seconds * ONE_SECOND;
  }

  public static long minutesToMillis(int minutes) {
    return minutes * ONE_MINUTE;
  }

  public static long hoursToMillis(int hours) {
    return hours * ONE_HOUR;
  }

  public static long daysToMillis(int days) {
    return days * ONE_DAY;
  }
}
