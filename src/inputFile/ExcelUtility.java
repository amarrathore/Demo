package inputFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;

	public static void setExcelFile(String path, String sheetName) throws Exception {

		try {
			FileInputStream ExcelFile = new FileInputStream(path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
		} catch (Exception e) {
			throw (e);
		}
	}

	public static XSSFRow getRow(int rowNum) {
		return ExcelWSheet.createRow(rowNum);
	}
	
	public static String getCellData(int rowNum, int colNum) throws Exception {
		try {
			Cell = ExcelWSheet.getRow(rowNum).getCell(colNum);
			String cellData = Cell.getStringCellValue();
			return cellData;
		} catch (Exception e) {
			return "";
		}
	}

	public static void setCellData(String newResult, XSSFRow newRow, int colNum, String fileTestData) throws Exception {
		XSSFCell Cell = newRow.createCell(colNum);
		Cell.setCellValue(newResult);
		ExcelUtility.writeData(fileTestData);
	}

	public static void writeData(String newFileTestData) throws Exception {
		FileOutputStream fileOut = new FileOutputStream(newFileTestData);
		ExcelWBook.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
	
	public static void setStaticCellData(String newResult, int rowNum, int colNum, String newFileTestData) throws Exception {
		try {
			Row = ExcelWSheet.getRow(rowNum);
			Cell = Row.getCell(colNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(colNum);
				Cell.setCellValue(newResult);
			} else {
				Cell.setCellValue(newResult);
			}
			FileOutputStream fileOut = new FileOutputStream(newFileTestData);
			ExcelWBook.write(fileOut);
			XSSFFormulaEvaluator.evaluateAllFormulaCells(ExcelWBook);			
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getRowCount() {
		int row = ExcelWSheet.getLastRowNum();
		return row;
	}
}

