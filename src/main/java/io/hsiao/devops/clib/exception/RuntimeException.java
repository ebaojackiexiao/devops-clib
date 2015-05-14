package io.hsiao.devops.clib.exception;

@SuppressWarnings("serial")
public final class RuntimeException extends java.lang.RuntimeException {
  public RuntimeException() {
    super();
  }

  public RuntimeException(final String message) {
    super(message);
  }

  public RuntimeException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
