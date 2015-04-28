package io.hsiao.devops.clib.logging.impl;

import io.hsiao.devops.clib.logging.ILoggerFactory;
import io.hsiao.devops.clib.logging.Logger;

public final class NoOpLoggerFactory implements ILoggerFactory {
  public NoOpLoggerFactory() {}

  @Override
  public Logger getLogger(final String name) {
    return logger;
  }

  private final Logger logger = new NoOpLogger("*");
}
