package io.hsiao.devops.clib.utils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.util.Properties;

import org.junit.Test;

public final class CommonUtilsTest {
  @Test
  public void test() throws Exception {
    final String propsname = "test.properties";

    Properties props = CommonUtils.loadProperties(propsname);
    assertEquals(CommonUtils.getProperty(props, "foo", false), "bar");

    props = CommonUtils.loadProperties("/" + getClass().getPackage().getName().replaceAll("\\.", "/") + "/" + propsname);
    assertEquals(CommonUtils.getProperty(props, "foo", false), "bar");

    Files.createFile(new File(propsname).toPath());
    props = CommonUtils.loadProperties(propsname);
    assertEquals(CommonUtils.getProperty(props, "foo", true), "");
    Files.delete(new File(propsname).toPath());

    props = CommonUtils.loadProperties(getClass(), propsname);
    assertEquals(CommonUtils.getProperty(props, "foo", false), "bar");
  }
}
