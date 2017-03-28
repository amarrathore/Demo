package inputFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	public static File file;
	public static FileInputStream inputStream;
	public static FileOutputStream outputStream;
	private static XSSFSheet excelWorkSheet;
	private static XSSFWorkbook excelWorkBook;
	private static XSSFCell cell;
	private static XSSFRow row;
	
	public static File getFile(String path) {
		try {
			file = new File(path);
		} catch (Exception e) {		
			e.printStackTrace();
		}
		return file;
	}
	
	public static FileInputStream getInputSteam() {
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	public static void setExcelFile(String path, String sheetName) throws Exception {

		try {
			ExcelUtility.getFile(path);
			ExcelUtility.getInputSteam();
			excelWorkBook = new XSSFWorkbook(inputStream);
			excelWorkSheet = excelWorkBook.getSheet(sheetName);
		} catch (Exception e) {
			throw (e);
		}
	}	

	public static XSSFRow getRow(int rowNum) {
		return excelWorkSheet.createRow(rowNum);
	}
	
	public static int getRowCount() {
		int row = excelWorkSheet.getLastRowNum();
		return row;
	}
	
	public static String getCellData(int rowNum, int colNum) throws Exception {
		try {
			cell = excelWorkSheet.getRow(rowNum).getCell(colNum);
			String cellData = cell.getStringCellValue();
			return cellData;
		} catch (Exception e) {
			return "";
		}
	}

	public static void setCellData(String newResult, XSSFRow newRow, int colNum, String fileTestData) throws Exception {
		cell = newRow.createCell(colNum);
		cell.setCellValue(newResult);
		ExcelUtility.writeData(fileTestData);
	}

	public static void writeData(String newFileTestData) throws Exception {
		outputStream = new FileOutputStream(newFileTestData);
		excelWorkBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}
	
	public static void setStaticCellData(String newResult, int rowNum, int colNum, String newFileTestData) throws Exception {
		try {
			row = excelWorkSheet.getRow(rowNum);
			cell = row.getCell(colNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
			if (cell == null) {
				cell = row.createCell(colNum);
				cell.setCellValue(newResult);
			} else {
				cell.setCellValue(newResult);
			}
			outputStream = new FileOutputStream(newFileTestData);
			excelWorkBook.write(outputStream);
			XSSFFormulaEvaluator.evaluateAllFormulaCells(excelWorkBook);			
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

