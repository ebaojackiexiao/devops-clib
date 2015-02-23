package io.hsiao.devops.clib.logging;

import io.hsiao.devops.clib.exception.Exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class LoggerProxy {
  private LoggerProxy() {}

  private static Properties readConfiguration() throws Exception {
    final Properties props = new Properties();

    try (final InputStream ins = LoggerProxy.class.getResourceAsStream("logging.properties")) {
      if (ins == null) {
        throw new Exception("failed to locate logging properties file");
      }

      props.load(ins);
    }
    catch (IOException ex) {
      final Exception exception = new Exception("failed to load logging properties");
      exception.initCause(ex);
      throw exception;
    }

    return props;
  }

  public static Logger getGlobal() {
    if (global != null) {
      return global;
    }

    // set the global logger level (JDK bug)
    Logger.getGlobal().setLevel(Level.INFO);
    global = Logger.getGlobal();

    return global;
  }

  public static Logger getLogger() throws Exception {
    if (logger != null) {
      return logger;
    }

    logger = Logger.getLogger(LoggerProxy.class.getPackage().getName());

    final Properties props = readConfiguration();

    final Level logLevel = Level.parse(props.getProperty("logger.level", "INFO").toUpperCase());
    logger.setLevel(logLevel);

    final boolean logUseParentHandlers = Boolean.parseBoolean(props.getProperty("logger.useParentHandlers", "false"));
    logger.setUseParentHandlers(logUseParentHandlers);

    final Level hdlLevel = Level.parse(props.getProperty("handler.level", "INFO").toUpperCase());
    final String hdlPattern = props.getProperty("handler.pattern", "devops-clib.log");
    final int hdlLimit = Integer.parseInt(props.getProperty("handler.limit", "0"));
    final int hdlCount = Integer.parseInt(props.getProperty("handler.count", "1"));
    final boolean hdlAppend = Boolean.parseBoolean(props.getProperty("handler.append", "true"));
    final String hdlEncoding = props.getProperty("handler.encoding", "UTF-8");

    try {
      final Handler handler = new FileHandler(hdlPattern, hdlLimit, hdlCount, hdlAppend);
      handler.setLevel(hdlLevel);
      handler.setEncoding(hdlEncoding);
      handler.setFormatter(new SimpleFormatter());
      logger.addHandler(handler);
    }
    catch (IOException ex) {
      final Exception exception = new Exception("failed to create logger file handler");
      exception.initCause(ex);
      throw exception;
    }

    return logger;
  }

  private static Logger global = null;
  private static Logger logger = null;
}
