package io.hsiao.devops.clib.excel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import io.hsiao.devops.clib.exception.Exception;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public final class ExcelReaderTest {
  @Test
  public void test() throws Exception {
    final InputStream ins = getClass().getResourceAsStream(excelTestFile);
    assertNotEquals("[TEST] excel file [" + excelTestFile + "] does not exist", ins, null);

    final ExcelReader reader = new ExcelReader(ins);

    assertEquals(reader.getCellValueAsString(null, 0, 0), null);
    assertEquals(reader.getCellValueAsString(null, 0, 1), "testString");
    assertEquals(reader.getCellValueAsString(null, 0, 2), new GregorianCalendar(1987, Calendar.JUNE, 12).getTime().toString());
    assertEquals(reader.getCellValueAsString(null, 0, 3), Boolean.toString(true));
    assertEquals(reader.getCellValueAsString(null, 0, 4), "6.12");
    assertEquals(reader.getCellValueAsString(null, 0, 5), "19.87");
    assertEquals(reader.getCellValueAsString(null, 0, 6), "AVERAGE(E1:F1)");

    assertEquals(reader.getCellValue(null, 0, 1).getClass(), String.class);
    assertEquals(reader.getCellValue(null, 0, 2).getClass(), Date.class);
    assertEquals(reader.getCellValue(null, 0, 3).getClass(), Boolean.class);
    assertEquals(reader.getCellValue(null, 0, 4).getClass(), Double.class);
    assertEquals(reader.getCellValue(null, 0, 5).getClass(), Double.class);
    assertEquals(reader.getCellValue(null, 0, 6).getClass(), String.class);

    assertEquals(reader.getCellValueAsString("", 0, 0), null);
    assertEquals(reader.getCellValueAsString("", 0, 1), Boolean.toString(false));
    assertEquals(reader.getCellValueAsString("", 0, 2), "LOG10(D1)");
    assertEquals(reader.getCellValueAsString("", 0, 3), Double.toString(1000));

    assertEquals(reader.getCellValue("", 0, 1).getClass(), Boolean.class);
    assertEquals(reader.getCellValue("", 0, 2).getClass(), String.class);
    assertEquals(reader.getCellValue("", 0, 3).getClass(), Double.class);

    assertEquals(reader.getCellValueAsString("test", 0, 0), null);
    assertEquals(reader.getCellValue("test", 0, 0), null);
  }

  private static final String excelTestFile = "ExcelReaderTest.xlsx";
}
