package io.hsiao.devops.clib.logging.impl;

import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;

public final class JDKLogger implements Logger {
  public JDKLogger(final java.util.logging.Logger logger) {
    if (logger == null) {
      throw new RuntimeException("argument 'logger' is null");
    }

    this.logger = logger;
  }

  private java.util.logging.Level map2jdk(final Level level) {
    switch (level) {
      case TRACE:
        return java.util.logging.Level.FINEST;
      case DEBUG:
        return java.util.logging.Level.FINE;
      case INFO:
        return java.util.logging.Level.INFO;
      case WARN:
        return java.util.logging.Level.WARNING;
      case ERROR:
        return java.util.logging.Level.SEVERE;
      default:
        throw new RuntimeException("invalid logging level [" + level + "]");
    }
  }

  @Override
  public String getName() {
    return logger.getName();
  }

  @Override
  public boolean isEnabled(final Level level) {
    if (level == null) {
      throw new RuntimeException("argument 'level' is null");
    }

    return logger.isLoggable(map2jdk(level));
  }

  @Override
  public void log(final Level level, final String message) {
    if (level == null) {
      throw new RuntimeException("argument 'level' is null");
    }

    logger.log(map2jdk(level), message);
  }

  @Override
  public void log(final Level level, final String message, final Throwable throwable) {
    if (level == null) {
      throw new RuntimeException("argument 'level' is null");
    }

    logger.log(map2jdk(level), message, throwable);
  }

  @Override
  public boolean isTraceEnabled() {
    return logger.isLoggable(map2jdk(Level.TRACE));
  }

  @Override
  public void trace(final String message) {
    logger.log(map2jdk(Level.TRACE), message);
  }

  @Override
  public void trace(final String message, final Throwable throwable) {
    logger.log(map2jdk(Level.TRACE), message, throwable);
  }

  @Override
  public boolean isDebugEnabled() {
    return logger.isLoggable(map2jdk(Level.DEBUG));
  }

  @Override
  public void debug(final String message) {
    logger.log(map2jdk(Level.DEBUG), message);
  }

  @Override
  public void debug(final String message, final Throwable throwable) {
    logger.log(map2jdk(Level.DEBUG), message, throwable);
  }

  @Override
  public boolean isInfoEnabled() {
    return logger.isLoggable(map2jdk(Level.INFO));
  }

  @Override
  public void info(final String message) {
    logger.log(map2jdk(Level.INFO), message);
  }

  @Override
  public void info(final String message, final Throwable throwable) {
    logger.log(map2jdk(Level.INFO), message, throwable);
  }

  @Override
  public boolean isWarnEnabled() {
    return logger.isLoggable(map2jdk(Level.WARN));
  }

  @Override
  public void warn(final String message) {
    logger.log(map2jdk(Level.WARN), message);
  }

  @Override
  public void warn(final String message, final Throwable throwable) {
    logger.log(map2jdk(Level.WARN), message, throwable);
  }

  @Override
  public boolean isErrorEnabled() {
    return logger.isLoggable(map2jdk(Level.ERROR));
  }

  @Override
  public void error(final String message) {
    logger.log(map2jdk(Level.ERROR), message);
  }

  @Override
  public void error(final String message, final Throwable throwable) {
    logger.log(map2jdk(Level.ERROR), message, throwable);
  }

  private final java.util.logging.Logger logger;
}
