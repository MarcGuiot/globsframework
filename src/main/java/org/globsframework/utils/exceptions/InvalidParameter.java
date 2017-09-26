package org.globsframework.utils.exceptions;

public class InvalidParameter extends GlobsException {
  public InvalidParameter(Exception e) {
    super(e);
  }

  public InvalidParameter(String message) {
    super(message);
  }

  public InvalidParameter(String message, Throwable cause) {
    super(message, cause);
  }
}
