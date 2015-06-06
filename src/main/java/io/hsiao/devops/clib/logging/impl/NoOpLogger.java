package io.hsiao.devops.clib.logging.impl;

import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;

public final class NoOpLogger implements Logger {
  public NoOpLogger(final String name) {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isEnabled(final Level level) {
    return false;
  }

  @Override
  public void log(final Level level, final String message) {}

  @Override
  public void log(final Level level, final String message, final Throwable throwable) {}

  @Override
  public boolean isTraceEnabled() {
    return false;
  }

  @Override
  public void trace(final String message) {}

  @Override
  public void trace(final String message, final Throwable throwable) {}

  @Override
  public boolean isDebugEnabled() {
    return false;
  }

  @Override
  public void debug(final String message) {}

  @Override
  public void debug(final String message, final Throwable throwable) {}

  @Override
  public boolean isInfoEnabled() {
    return false;
  }

  @Override
  public void info(final String message) {}

  @Override
  public void info(final String message, final Throwable throwable) {}

  @Override
  public boolean isWarnEnabled() {
    return false;
  }

  @Override
  public void warn(final String message) {}

  @Override
  public void warn(final String message, final Throwable throwable) {}

  @Override
  public boolean isErrorEnabled() {
    return false;
  }

  @Override
  public void error(final String message) {}

  @Override
  public void error(final String message, final Throwable throwable) {}

  private final String name;
}
