package io.hsiao.devops.clib.utils;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;
import io.hsiao.devops.clib.logging.Logger.Level;
import io.hsiao.devops.clib.logging.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipUtils {
  public static void pack(final File source, final File dest, final boolean verbose, final boolean zipEmpty) throws Exception {
    if (source == null) {
      throw new RuntimeException("argument 'source' is null");
    }

    if (dest == null) {
      throw new RuntimeException("argument 'dest' is null");
    }

    if (FileUtils.isEmptyDirectory(source) && !zipEmpty) {
      if (verbose) {
        logger.log(Level.INFO, "packing [" + source + "] skipped: no entries found");
      }
      return;
    }

    if (verbose) {
      logger.log(Level.INFO, "packing [" + source + "] to [" + dest + "]");
    }

    try (final FileOutputStream fos = new FileOutputStream(dest);
      final ZipOutputStream zos = new ZipOutputStream(fos)) {

      Files.walkFileTree(source.toPath(), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
        new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (verbose) {
              logger.log(Level.INFO, "packing file entry [" + file + "] into [" + dest + "]");
            }

            final Path relative = source.toPath().toAbsolutePath().relativize(file.toAbsolutePath());
            zos.putNextEntry(new ZipEntry(relative.toString()));

            final byte[] buffer = new byte[1024];
            int len;
            try (final FileInputStream fis = new FileInputStream(file.toFile())) {
              while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
              }
            }

            zos.closeEntry();

            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            if (exc != null) {
              throw exc;
            }

            final Path relative = source.toPath().toAbsolutePath().relativize(dir.toAbsolutePath());
            if (!relative.toString().isEmpty()) {
              if (verbose) {
                logger.log(Level.INFO, "packing dir entry [" + dir + "] into [" + dest + "]");
              }

              zos.putNextEntry(new ZipEntry(relative.toString() + "/"));
              zos.closeEntry();
            }

            return FileVisitResult.CONTINUE;
          }
      });
    }
    catch (IOException ex) {
      final Exception exception = new Exception("failed to pack [" + source + "] to [" + dest + "]", ex);
      logger.log(Level.INFO, "failed to pack [" + source + "] to [" + dest + "]", exception);
      throw exception;
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);
}
