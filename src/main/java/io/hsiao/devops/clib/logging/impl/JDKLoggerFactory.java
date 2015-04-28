package io.hsiao.devops.clib.logging.impl;

import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.ILoggerFactory;
import io.hsiao.devops.clib.logging.Logger;

public final class JDKLoggerFactory implements ILoggerFactory {
  public JDKLoggerFactory() {}

  @Override
  public Logger getLogger(final String name) {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    return new JDKLogger(java.util.logging.Logger.getLogger(name));
  }
}
