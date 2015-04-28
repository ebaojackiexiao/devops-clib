package io.hsiao.devops.clib.logging.impl;

import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.ILoggerFactory;
import io.hsiao.devops.clib.logging.Logger;
import io.hsiao.devops.clib.logging.Logger.Level;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class SimpleConsoleLoggerFactory implements ILoggerFactory {
  public SimpleConsoleLoggerFactory(final Level level) {
    if (level == null) {
      throw new RuntimeException("argument 'level' is null");
    }

    this.level = level;
  }

  public Level getLevel() {
    return level;
  }

  @Override
  public Logger getLogger(final String name) {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    return new SimpleConsoleLogger(this, name);
  }

  public String getCurrentDateTime() {
    return new SimpleDateFormat("EEE HH:mm:ss yyyy/MM/dd").format(new Date());
  }

  private final Level level;
}
