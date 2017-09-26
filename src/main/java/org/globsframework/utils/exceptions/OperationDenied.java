package org.globsframework.utils.exceptions;

public class OperationDenied extends GlobsException {
  public OperationDenied(Exception e) {
    super(e);
  }

  public OperationDenied(String message) {
    super(message);
  }

  public OperationDenied(String message, Throwable cause) {
    super(message, cause);
  }
}
