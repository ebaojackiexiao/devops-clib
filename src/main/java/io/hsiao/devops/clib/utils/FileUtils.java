package io.hsiao.devops.clib.utils;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.logging.LoggerProxy;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

public final class FileUtils {
  public static boolean isEmptyDirectory(final File dir) throws Exception {
    try (final DirectoryStream<Path> entries = Files.newDirectoryStream(dir.toPath())) {
      return !entries.iterator().hasNext();
    }
    catch (IOException ex) {
      final Exception exception = new Exception("failed to check directory empty status [" + dir + "]");
      exception.initCause(ex);
      LoggerProxy.getLogger().log(Level.INFO, "failed to check directory empty status [" + dir + "]", exception);
      throw exception;
    }
  }
}
