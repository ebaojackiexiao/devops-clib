package io.hsiao.devops.clib.utils;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;
import io.hsiao.devops.clib.logging.Logger.Level;
import io.hsiao.devops.clib.logging.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.Set;

public final class FileUtils {
  private FileUtils() {}

  public static boolean isEmptyDirectory(final File dir) throws Exception {
    if (dir == null) {
      throw new RuntimeException("argument 'dir' is null");
    }

    try (final DirectoryStream<Path> entries = Files.newDirectoryStream(dir.toPath())) {
      return !entries.iterator().hasNext();
    }
    catch (IOException ex) {
      final Exception exception = new Exception("failed to check directory empty status [" + dir + "]", ex);
      logger.log(Level.INFO, "failed to check directory empty status [" + dir + "]", exception);
      throw exception;
    }
  }

  public static File mkdir(final File dir) throws Exception {
    if (dir == null) {
      throw new RuntimeException("argument 'dir' is null");
    }

    try {
      return Files.createDirectories(dir.toPath()).toFile();
    }
    catch (IOException ex) {
      final Exception exception = new Exception("failed to make directory [" + dir + "]", ex);
      logger.log(Level.INFO, "failed to make directory [" + dir + "]", exception);
      throw exception;
    }
  }

  public static void rmdir(final File dir, final boolean followLinks) throws Exception {
    if (dir == null) {
      throw new RuntimeException("argument 'dir' is null");
    }

    final boolean exists;
    final Set<FileVisitOption> options;
    if (followLinks) {
      exists = Files.isDirectory(dir.toPath());
      options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
    }
    else {
      exists = Files.isDirectory(dir.toPath(), LinkOption.NOFOLLOW_LINKS);
      options = EnumSet.noneOf(FileVisitOption.class);
    }

    if (!exists) {
      return;
    }

    try {
      Files.walkFileTree(dir.toPath(), options, Integer.MAX_VALUE,
        new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
          }
      });
    }
    catch (IOException ex) {
      final Exception exception = new Exception("failed to remove directory [" + dir + "]", ex);
      logger.log(Level.INFO, "failed to remove directory [" + dir + "]", exception);
      throw exception;
    }
  }

  public static InputStream loadFileAsStream(final String name) throws Exception {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    final File file = new File(name);
    if (file.isFile()) {
      try {
        return new FileInputStream(file);
      }
      catch (FileNotFoundException ex) {
        final Exception exception = new Exception("failed to load file [" + name + "]", ex);
        logger.log(Level.INFO, "failed to load file [" + name + "]", exception);
        throw exception;
      }
    }

    final InputStream ins = FileUtils.class.getResourceAsStream(name);
    if (ins != null) {
      return ins;
    }

    final Exception exception = new Exception("failed to load file (not found on both filesystem and classpath) [" + name + "]");
    logger.log(Level.INFO, "failed to load file (not found on both filesystem and classpath) [" + name + "]", exception);
    throw exception;
  }

  private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
}
