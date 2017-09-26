package org.globsframework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class Dates {
  public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
  private static final SimpleDateFormat dateFormat = DEFAULT_DATE_FORMAT;
  public static final SimpleDateFormat DEFAULT_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  private static final SimpleDateFormat timestampFormat = DEFAULT_TIMESTAMP_FORMAT;
  public static final SimpleDateFormat DEFAULT_MONTH_FORMAT = new SimpleDateFormat("yyyy/MM");
  private static final SimpleDateFormat monthFormat = DEFAULT_MONTH_FORMAT;

  private Dates() {
  }

  public static Date parse(String yyyyMMdd) {
    synchronized (dateFormat) {
      try {
        return dateFormat.parse(yyyyMMdd);
      }
      catch (ParseException e) {
        throw new RuntimeException("Format should be: " + dateFormat.toPattern(), e);
      }
    }
  }

  public static String toString(Date date) {
    if (date == null) {
      return "";
    }
    synchronized (dateFormat) {
      return dateFormat.format(date);
    }
  }

  /**
   * Sample format: "03/10/2002 12:34:20"
   */
  public static Date parseTimestamp(String yyyyMMdd_hhmmss) {
    synchronized (timestampFormat) {
      try {
        return timestampFormat.parse(yyyyMMdd_hhmmss);
      }
      catch (ParseException e) {
        throw new RuntimeException("Format should be: " + timestampFormat.toPattern(), e);
      }
    }
  }

  public static String toTimestampString(Date date) {
    synchronized (timestampFormat) {
      return timestampFormat.format(date);
    }
  }

  public static String getStandardDate(Date date) {
    synchronized (dateFormat) {
      return dateFormat.format(date);
    }
  }

  public static Date parseMonth(String yyyymm) {
    synchronized (monthFormat) {
      try {
        return monthFormat.parse(yyyymm);
      }
      catch (ParseException e) {
        throw new RuntimeException("Format should be: " + monthFormat.toPattern(), e);
      }
    }
  }

  public static String toMonth(Date date) {
    synchronized (monthFormat) {
      return monthFormat.format(date);
    }
  }

  public static boolean isNear(Date now, Date target, long marginInMillis) {
    return millisBetween(now, target) < marginInMillis;
  }

  public static boolean isNear(Calendar now, Calendar target, long marginInMillis) {
    return millisBetween(now, target) < marginInMillis;
  }

  public static long millisBetween(Date date1, Date date2) {
    return Math.abs(date1.getTime() - date2.getTime());
  }

  public static long millisBetween(Calendar date1, Calendar date2) {
    return Math.abs(date1.getTimeInMillis() - date2.getTimeInMillis());
  }
  
  public static Date extractDateDDMMYYYY(String str){
    str = str.replaceAll(".*([0-9]{2}/[0-9]{2}/[0-9]{4}).*", "$1");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    try {
      return simpleDateFormat.parse(str);
    }
    catch (ParseException e) {
      Log.write("No date in " + str);
      return null;
    }
  }

  public static Date last(Date date1, Date date2) {
    if (date1 == null) {
      return date2;
    }
    if (date2 == null) {
      return date1;
    }
    return date1.after(date2) ? date1 : date2;
  }

  public interface DateDecomposed {
    void push(int year, int month, int day);
  }

  public static void decomposeDate(LocalDate localDate, DateDecomposed dateDecomposed){
    dateDecomposed.push(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
  }

}
