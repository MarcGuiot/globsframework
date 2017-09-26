package org.globsframework.utils.exceptions;

public class ItemAmbiguity extends GlobsException {
  public ItemAmbiguity(Exception e) {
    super(e);
  }

  public ItemAmbiguity(String message) {
    super(message);
  }

  public ItemAmbiguity(String message, Throwable cause) {
    super(message, cause);
  }
}
