package io.hsiao.devops.clib.logging.impl;

import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;

public final class SimpleConsoleLogger implements Logger {
  public SimpleConsoleLogger(final SimpleConsoleLoggerFactory simpleConsoleLoggerFactory, final String name) {
    if (simpleConsoleLoggerFactory == null) {
      throw new RuntimeException("argument 'simpleConsoleLoggerFactory' is null");
    }

    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    this.sclf = simpleConsoleLoggerFactory;
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isEnabled(final Level level) {
    if (level == null) {
      throw new RuntimeException("argument 'level' is null");
    }

    return level.isEnabled(sclf.getLevel());
  }

  @Override
  public void log(final Level level, final String message) {
    if (level == null) {
      throw new RuntimeException("argument 'level' is null");
    }

    print(level, message, null);
  }

  @Override
  public void log(final Level level, final String message, final Throwable throwable) {
    if (level == null) {
      throw new RuntimeException("argument 'level' is null");
    }

    print(level, message, throwable);
  }

  @Override
  public boolean isTraceEnabled() {
    return Level.TRACE.isEnabled(sclf.getLevel());
  }

  @Override
  public void trace(final String message) {
    print(Level.TRACE, message, null);
  }

  @Override
  public void trace(final String message, final Throwable throwable) {
    print(Level.TRACE, message, throwable);
  }

  @Override
  public boolean isDebugEnabled() {
    return Level.DEBUG.isEnabled(sclf.getLevel());
  }

  @Override
  public void debug(final String message) {
    print(Level.DEBUG, message, null);
  }

  @Override
  public void debug(final String message, final Throwable throwable) {
    print(Level.DEBUG, message, throwable);
  }

  @Override
  public boolean isInfoEnabled() {
    return Level.INFO.isEnabled(sclf.getLevel());
  }

  @Override
  public void info(final String message) {
    print(Level.INFO, message, null);
  }

  @Override
  public void info(final String message, final Throwable throwable) {
    print(Level.INFO, message, throwable);
  }

  @Override
  public boolean isWarnEnabled() {
    return Level.WARN.isEnabled(sclf.getLevel());
  }

  @Override
  public void warn(final String message) {
    print(Level.WARN, message, null);
  }

  @Override
  public void warn(final String message, final Throwable throwable) {
    print(Level.WARN, message, throwable);
  }

  @Override
  public boolean isErrorEnabled() {
    return Level.ERROR.isEnabled(sclf.getLevel());
  }

  @Override
  public void error(final String message) {
    print(Level.ERROR, message, null);
  }

  @Override
  public void error(final String message, final Throwable throwable) {
    print(Level.ERROR, message, throwable);
  }

  private void print(final Level level, final String message, final Throwable throwable) {
    if (level == null) {
      throw new RuntimeException("argument 'level' is null");
    }

    if (!isEnabled(level)) {
      return;
    }

    final StringBuilder sb = new StringBuilder();
    sb.append("[" + level + "] ");
    sb.append("[" + sclf.getCurrentDateTime() + "] ");
    sb.append("{" + message +"}");

    System.out.println(sb.toString());

    if (throwable != null) {
      throwable.printStackTrace(System.out);
    }
  }

  private final SimpleConsoleLoggerFactory sclf;
  private final String name;
}
