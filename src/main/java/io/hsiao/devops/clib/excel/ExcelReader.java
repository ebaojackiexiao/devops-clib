package io.hsiao.devops.clib.excel;

import io.hsiao.devops.clib.exception.Exception;
import io.hsiao.devops.clib.exception.RuntimeException;
import io.hsiao.devops.clib.logging.Logger;
import io.hsiao.devops.clib.logging.Logger.Level;
import io.hsiao.devops.clib.logging.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.WorkbookUtil;

public final class ExcelReader {
  public ExcelReader(final String name) throws Exception {
    if (name == null) {
      throw new RuntimeException("argument 'name' is null");
    }

    try {
      workBook = WorkbookFactory.create(new File(name));
    }
    catch (IOException | InvalidFormatException ex) {
      final Exception exception = new Exception("failed to open workbook [" + name + "]", ex);
      logger.log(Level.INFO, "failed to open workbook [" + name + "]", exception);
      throw exception;
    }
  }

  public ExcelReader(final InputStream ins) throws Exception {
    if (ins == null) {
      throw new RuntimeException("argument 'ins' is null");
    }

    try {
      workBook = WorkbookFactory.create(ins);
    }
    catch (IOException | InvalidFormatException ex) {
      final Exception exception = new Exception("failed to open workbook [" + ins + "]", ex);
      logger.log(Level.INFO, "failed to open workbook [" + ins + "]", exception);
      throw exception;
    }
  }

  public Sheet getSheet(final String name) {
    return workBook.getSheet(WorkbookUtil.createSafeSheetName(name, sheetNameReplaceChar));
  }

  public String getCellValueAsString(final String name, final int rowNum, final int colNum) throws Exception {
    final Sheet sheet = getSheet(name);
    if (sheet == null) {
      return null;
    }

    final Row row = sheet.getRow(rowNum);
    if (row == null) {
      return null;
    }

    final Cell cell = row.getCell(colNum, Row.RETURN_NULL_AND_BLANK);
    if (cell == null) {
      return null;
    }

    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_BLANK:
        return "";
      case Cell.CELL_TYPE_BOOLEAN:
        return Boolean.toString(cell.getBooleanCellValue());
      case Cell.CELL_TYPE_ERROR:
        return Byte.toString(cell.getErrorCellValue());
      case Cell.CELL_TYPE_FORMULA:
        return cell.getCellFormula();
      case Cell.CELL_TYPE_STRING:
        return cell.getRichStringCellValue().getString();
      case Cell.CELL_TYPE_NUMERIC:
        if (DateUtil.isCellDateFormatted(cell)) {
          return cell.getDateCellValue().toString();
        }
        else {
          return Double.toString(cell.getNumericCellValue());
        }
      default:
        final Exception exception = new Exception("failed to get cell value, invalid cell type [" + cell.getCellType() + "]");
        logger.log(Level.INFO, "failed to get cell value, invalid cell type [" + cell.getCellType() + "]", exception);
        throw exception;
    }
  }

  public Object getCellValue(final String name, final int rowNum, final int colNum) throws Exception {
    final Sheet sheet = getSheet(name);
    if (sheet == null) {
      return null;
    }

    final Row row = sheet.getRow(rowNum);
    if (row == null) {
      return null;
    }

    final Cell cell = row.getCell(colNum, Row.RETURN_NULL_AND_BLANK);
    if (cell == null) {
      return null;
    }

    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_BLANK:
        return "";
      case Cell.CELL_TYPE_BOOLEAN:
        return cell.getBooleanCellValue();
      case Cell.CELL_TYPE_ERROR:
        return cell.getErrorCellValue();
      case Cell.CELL_TYPE_FORMULA:
        return cell.getCellFormula();
      case Cell.CELL_TYPE_STRING:
        return cell.getRichStringCellValue().getString();
      case Cell.CELL_TYPE_NUMERIC:
        if (DateUtil.isCellDateFormatted(cell)) {
          return cell.getDateCellValue();
        }
        else {
          return cell.getNumericCellValue();
        }
      default:
        final Exception exception = new Exception("failed to get cell value, invalid cell type [" + cell.getCellType() + "]");
        logger.log(Level.INFO, "failed to get cell value, invalid cell type [" + cell.getCellType() + "]", exception);
        throw exception;
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);
  private static final char sheetNameReplaceChar = '-';

  private final Workbook workBook;
}
