package io.hsiao.devops.clib.excel;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;
import io.hsiao.devops.clib.logging.Logger.Level;
import io.hsiao.devops.clib.logging.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelWriter {
  public ExcelWriter(final String name) {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    workBook = new XSSFWorkbook();
    workBookName = name;

    cellStyle = null;
    dateCellStyle = null;
    headerCellStyle = null;

    creationHelper = workBook.getCreationHelper();
  }

  public Sheet getSheet(final String name) {
    return workBook.getSheet(WorkbookUtil.createSafeSheetName(name, sheetNameReplaceChar));
  }

  public Sheet createSheet(final String name) throws Exception {
    final String safeName = WorkbookUtil.createSafeSheetName(name, sheetNameReplaceChar);

    if (workBook.getSheet(safeName) != null) {
      final Exception exception = new Exception("failed to create sheet (sheet already exists) [" + name + "]");
      logger.log(Level.INFO, "failed to create sheet (sheet already exists) [" + name + "]", exception);
      throw exception;
    }

    return workBook.createSheet(safeName);
  }

  private Row getRow(final String name, final int rowNum) throws Exception {
    final Sheet sheet = getSheet(name);

    if (sheet == null) {
      final Exception exception = new Exception("failed to get row (sheet not found) [" + name + "]");
      logger.log(Level.INFO, "failed to get row (sheet not found) [" + name + "]", exception);
      throw exception;
    }

    return sheet.getRow(rowNum);
  }

  public void autoSizeColumn(final String name, final int colNum, final boolean useMergedCells) throws Exception {
    final Sheet sheet = getSheet(name);

    if (sheet == null) {
      final Exception exception = new Exception("failed to autosize column (sheet not found) [" + name + "]");
      logger.log(Level.INFO, "failed to autosize column (sheet not found) [" + name + "]", exception);
      throw exception;
    }

    sheet.autoSizeColumn(colNum, useMergedCells);
  }

  public CreationHelper getCreationHelper() {
    return creationHelper;
  }

  public CellStyle createCellStyle() {
    return workBook.createCellStyle();
  }

  public CellStyle cloneDefaultCellStyle() {
    final CellStyle style = workBook.createCellStyle();
    style.cloneStyleFrom(getDefaultCellStyle());
    return style;
  }

  public CellStyle getDefaultCellStyle() {
    if (cellStyle != null) {
      return cellStyle;
    }

    cellStyle = workBook.createCellStyle();

    cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

    cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
    cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
    cellStyle.setBorderRight(CellStyle.BORDER_THIN);
    cellStyle.setBorderTop(CellStyle.BORDER_THIN);

    cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());

    cellStyle.setShrinkToFit(true);
    cellStyle.setWrapText(true);

    return cellStyle;
  }

  public CellStyle cloneDefaultDateCellStyle() {
    final CellStyle style = workBook.createCellStyle();
    style.cloneStyleFrom(getDefaultDateCellStyle());
    return style;
  }

  public CellStyle getDefaultDateCellStyle() {
    if (dateCellStyle != null) {
      return dateCellStyle;
    }

    dateCellStyle = workBook.createCellStyle();

    dateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    dateCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

    dateCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
    dateCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
    dateCellStyle.setBorderRight(CellStyle.BORDER_THIN);
    dateCellStyle.setBorderTop(CellStyle.BORDER_THIN);

    dateCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    dateCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());

    dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy/mm/dd"));

    dateCellStyle.setShrinkToFit(true);
    dateCellStyle.setWrapText(true);

    return dateCellStyle;
  }

  public CellStyle cloneDefaultHeaderCellStyle() {
    final CellStyle style = workBook.createCellStyle();
    style.cloneStyleFrom(getDefaultHeaderCellStyle());
    return style;
  }

  public CellStyle getDefaultHeaderCellStyle() {
    if (headerCellStyle != null) {
      return headerCellStyle;
    }

    headerCellStyle = workBook.createCellStyle();

    headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

    headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
    headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
    headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
    headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);

    headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    headerCellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());

    final Font font = workBook.createFont();
    font.setBold(true);
    headerCellStyle.setFont(font);

    headerCellStyle.setWrapText(false);

    return headerCellStyle;
  }

  public void writeHeaders(final String name, final List<String> headers, final CellStyle style) throws Exception {
    if (headers == null) {
      throw new RuntimeException("argument 'headers' is null");
    }

    for (int colNum = 0; colNum < headers.size(); ++colNum) {
      writeToCell(name, 0, colNum, headers.get(colNum).trim(), style);
    }

    final Row row = getRow(name, 0);
    if (row == null) {
      final Exception exception = new Exception("failed to get row [" + name + "] [" + 0 + "]");
      logger.log(Level.INFO, "failed to get row [" + name + "] [" + 0 + "]", exception);
      throw exception;
    }

    row.setHeightInPoints(row.getHeightInPoints() * 1.5F);
  }

  public void writeHeaders(final String name, final List<String> headers) throws Exception {
    writeHeaders(name, headers, getDefaultHeaderCellStyle());
  }

  public Cell writeToCell(final String name, final int rowNum, final int colNum, final Object value, final CellStyle style) throws Exception {
    Sheet sheet = getSheet(name);
    if (sheet == null) {
      sheet = createSheet(name);
    }

    Row row = sheet.getRow(rowNum);
    if (row == null) {
      row = sheet.createRow(rowNum);
    }

    Cell cell = row.getCell(colNum);
    if (cell == null) {
      cell = row.createCell(colNum);
    }

    cell.setCellStyle(style);

    if (value == null) {
      cell.setCellType(CELL_TYPE_STRING);
      cell.setCellValue("");
    }
    else if (value instanceof Boolean) {
      cell.setCellType(CELL_TYPE_BOOLEAN);
      cell.setCellValue((Boolean) value);
    }
    else if (value instanceof Calendar) {
      cell.setCellType(CELL_TYPE_NUMERIC);
      cell.setCellValue((Calendar) value);
    }
    else if (value instanceof Date) {
      cell.setCellType(CELL_TYPE_NUMERIC);
      cell.setCellValue((Date) value);
    }
    else if (value instanceof Integer) {
      cell.setCellType(CELL_TYPE_NUMERIC);
      cell.setCellValue(Double.valueOf(((Integer) value)));
    }
    else if (value instanceof Double) {
      cell.setCellType(CELL_TYPE_NUMERIC);
      cell.setCellValue((Double) value);
    }
    else if (value instanceof String) {
      cell.setCellType(CELL_TYPE_STRING);
      cell.setCellValue((String) value);
    }
    else {
      final Exception exception = new Exception("invalid cell value type, found [" + value.getClass().getName() + "]");
      logger.log(Level.INFO, "invalid cell value type, found [" + value.getClass().getName() + "]", exception);
      throw exception;
    }

    return cell;
  }

  public Cell writeToCell(final String name, final int rowNum, final int colNum, final Object value) throws Exception {
    return writeToCell(name, rowNum, colNum, value, getDefaultCellStyle());
  }

  public void close() throws Exception {
    try (final FileOutputStream fos = new FileOutputStream(workBookName)) {
      workBook.write(fos);
    }
    catch (IOException ex) {
      final Exception exception = new Exception("failed to close workbook [" + workBookName + "]", ex);
      logger.log(Level.INFO, "failed to close workbook [" + workBookName + "]", exception);
      throw exception;
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(ExcelWriter.class);

  private final Workbook workBook;
  private final String workBookName;
  private final CreationHelper creationHelper;

  private CellStyle cellStyle;
  private CellStyle dateCellStyle;
  private CellStyle headerCellStyle;

  private static final char sheetNameReplaceChar = '-';

  public static final int CELL_TYPE_BLANK = Cell.CELL_TYPE_BLANK;
  public static final int CELL_TYPE_BOOLEAN = Cell.CELL_TYPE_BOOLEAN;
  public static final int CELL_TYPE_ERROR = Cell.CELL_TYPE_ERROR;
  public static final int CELL_TYPE_FORMULA = Cell.CELL_TYPE_FORMULA;
  public static final int CELL_TYPE_NUMERIC = Cell.CELL_TYPE_NUMERIC;
  public static final int CELL_TYPE_STRING = Cell.CELL_TYPE_STRING;
}
