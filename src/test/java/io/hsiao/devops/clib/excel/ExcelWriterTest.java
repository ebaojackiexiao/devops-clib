package io.hsiao.devops.clib.excel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Test;

public final class ExcelWriterTest {
  @Test
  public void test() throws Exception {
    Files.deleteIfExists(new File(excelTestFile).toPath());

    final ExcelWriter writer = new ExcelWriter(excelTestFile);

    assertNull(writer.getSheet("---valid---"));
    writer.createSheet("'?*valid[]'");
    assertNotNull(writer.getSheet("---valid---"));

    // sheet "null"
    assertNull(writer.getSheet(null));

    final String[] headers = {"headline01", "headline02", "headline03"};
    writer.writeHeaders(null, Arrays.asList(headers), writer.getDefaultHeaderCellStyle());

    writer.writeToCell(null, 1, 0, new Date(), writer.getDefaultDateCellStyle());

    final CellStyle newCellStyle = writer.cloneDefaultCellStyle();
    newCellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
    writer.writeToCell(null, 1, 1, "testValue", newCellStyle);

    writer.writeToCell(null, 1, 2, true, writer.getDefaultCellStyle());

    for (int idx = 0; idx < headers.length; ++idx) {
      writer.autoSizeColumn(null, idx, false);
    }

    // sheet "empty"
    assertNull(writer.getSheet(""));

    final CellStyle newDateCellStyle = writer.cloneDefaultDateCellStyle();
    newDateCellStyle.setDataFormat(writer.getCreationHelper().createDataFormat().getFormat("yyyy-mm-dd"));
    writer.writeToCell("", 0, 0, new GregorianCalendar(), newDateCellStyle);
    writer.writeToCell("", 0, 1, null, null);
    writer.writeToCell("", 0, 2, null);
    writer.writeToCell("", 0, 3, 612);
    writer.writeToCell("", 0, 4, 1987.612);

    writer.autoSizeColumn("", 0, false);

    assertNotNull(writer.getSheet(null));
    assertNotNull(writer.getSheet(""));

    writer.close();
  }

  private static final String excelTestFile = "ExcelWriterTest.xlsx";
}
