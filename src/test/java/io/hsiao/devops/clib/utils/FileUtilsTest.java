package io.hsiao.devops.clib.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

import org.junit.Test;

public final class FileUtilsTest {
  @Test
  public void test() throws Exception {
    final String dirname = "io/hsiao/test";
    final File dir = new File(dirname);

    FileUtils.mkdir(dir);
    FileUtils.mkdir(dir);

    assertTrue(FileUtils.isEmptyDirectory(dir));
    assertFalse(FileUtils.isEmptyDirectory(dir.getParentFile()));

    final String filename = "test.txt";
    Files.createFile(new File(dirname + File.separator + filename).toPath());
    assertFalse(FileUtils.isEmptyDirectory(dir));

    final String propsname = "test.properties";
    InputStream ins = FileUtils.loadFileAsStream("/" + getClass().getPackage().getName().replaceAll("\\.", "/") + "/" + propsname);
    assertNotNull(ins);
    ins.close();

    ins = FileUtils.loadFileAsStream(propsname);
    assertNotNull(ins);
    ins.close();

    Files.createFile(new File(dirname + File.separator + propsname).toPath());
    ins = FileUtils.loadFileAsStream(dirname + File.separator + propsname);
    assertNotNull(ins);
    ins.close();

    FileUtils.rmdir(dir.getParentFile().getParentFile(), false);
    FileUtils.rmdir(dir.getParentFile().getParentFile(), true);
  }
}
