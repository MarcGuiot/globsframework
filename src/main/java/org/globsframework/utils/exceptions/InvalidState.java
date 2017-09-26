package org.globsframework.utils.exceptions;

public class InvalidState extends GlobsException {
  public InvalidState(Exception e) {
    super(e);
  }

  public InvalidState(String message) {
    super(message);
  }

  public InvalidState(String message, Throwable cause) {
    super(message, cause);
  }
}
