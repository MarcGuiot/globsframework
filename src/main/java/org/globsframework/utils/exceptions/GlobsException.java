package org.globsframework.utils.exceptions;

public class GlobsException extends RuntimeException {
  public GlobsException(Exception e) {
    super(e);
  }

  public GlobsException(String message) {
    super(message);
  }

  public GlobsException(String message, Throwable cause) {
    super(message, cause);
  }
}
