package io.hsiao.devops.clib.logging;

public interface ILoggerFactory {
  public Logger getLogger(final String name);
}
