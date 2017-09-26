package org.globsframework.utils;

import java.io.PrintStream;

public class Log {

  private static boolean loggingEnabled = true;

  private static PrintStream stream = System.out;
  private static int indentation = 0;
  private static String BANNER =
    "=================================================================";

  public static void init(PrintStream stream) {
    Log.stream = stream;
    System.setOut(stream);
    System.setErr(stream);
  }

  public static void reset() {
    indentation = 0;
  }

  public static void space(int lineCount) {
    if (loggingEnabled) {
      for (int i = 0; i < lineCount; i++) {
        stream.append(Strings.LINE_SEPARATOR);
      }
    }
  }

  public static void enter(String text) {
    doWrite(">> ", text, true);
    indentation++;
  }

  public static void leave(String text) {
    indentation--;
    doWrite("<< ", text, true);
  }

  public static void write(String text) {
    doWrite(".. ", text, true);
  }

  public static void write(String text, Throwable e) {
    doWrite(".. ", text, true);
    e.printStackTrace(stream);
  }

  public static void doWrite(String prefix, String text, boolean indent) {
    if (loggingEnabled) {
      if (indent) {
        indent();
      }
      stream.append(prefix);
      stream.append(text);
      stream.append(Strings.LINE_SEPARATOR);
    }
  }

  private static void indent() {
    for (int i = 0; i < indentation; i++) {
      stream.append(".  ");
    }
  }

  public static void banner(String text) {
    space(3);
    doWrite("", BANNER, false);
    doWrite("==     ", text, false);
    doWrite("", BANNER, false);
  }

  public static void setEnabled(boolean enabled) {
    loggingEnabled = enabled;
  }

}
