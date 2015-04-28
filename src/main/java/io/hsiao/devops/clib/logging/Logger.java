package io.hsiao.devops.clib.logging;

import io.hsiao.devops.clib.exception.RuntimeException;

public interface Logger {
  public enum Level {
    TRACE(1), DEBUG(2), INFO(3), WARN(4), ERROR(5);

    public boolean isEnabled(final Level level) {
      if (level == null) {
        throw new RuntimeException("argument 'level' is null");
      }

      return value >= level.value;
    }

    private Level(final int value) {
      this.value = value;
    }

    private final int value;
  }

  public String getName();

  public boolean isEnabled(final Level level);

  public void log(final Level level, final String message);
  public void log(final Level level, final String message, final Throwable throwable);

  public boolean isTraceEnabled();
  public void trace(final String message);
  public void trace(final String message, final Throwable throwable);

  public boolean isDebugEnabled();
  public void debug(final String message);
  public void debug(final String message, final Throwable throwable);

  public boolean isInfoEnabled();
  public void info(final String message);
  public void info(final String message, final Throwable throwable);

  public boolean isWarnEnabled();
  public void warn(final String message);
  public void warn(final String message, final Throwable throwable);

  public boolean isErrorEnabled();
  public void error(final String message);
  public void error(final String message, final Throwable throwable);
}
