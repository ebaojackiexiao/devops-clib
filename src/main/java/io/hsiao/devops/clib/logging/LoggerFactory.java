package io.hsiao.devops.clib.logging;

import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.impl.NoOpLoggerFactory;

public final class LoggerFactory {
  private LoggerFactory() {}

  public static Logger getLogger(final Class<?> clazz) {
    if (clazz == null) {
      throw new RuntimeException("argument 'clazz' is null");
    }

    return loggerFactory.getLogger(clazz.getName());
  }

  public static Logger getLogger(final String name) {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    return loggerFactory.getLogger(name);
  }

  public static void setLoggerFactory(final ILoggerFactory loggerFactory) {
    if (loggerFactory == null) {
      throw new RuntimeException("argument 'loggerFactory' is null");
    }

    LoggerFactory.loggerFactory = loggerFactory;
  }

  private static ILoggerFactory loggerFactory = new NoOpLoggerFactory();
}
