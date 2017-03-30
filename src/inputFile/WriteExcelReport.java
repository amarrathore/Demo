package inputFile;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteExcelReport {
	public static XSSFWorkbook excelWorkBook;
	public static XSSFSheet excelWorkSheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static FileOutputStream outputStream;
	private static String reportPath = null;

	public static int getRowCount() {
		int rowCount = excelWorkSheet.getLastRowNum()-1;
		return rowCount;
	}
	
	public static XSSFRow getRow(int rowNum) {
		return excelWorkSheet.createRow(rowNum);
	}
	
	public static int getColCount() {
		int colCount = excelWorkSheet.getRow(getRowCount()).getLastCellNum();
		return colCount;
	}
	
	public static void setExcel() throws IOException {
		try {
			reportPath = SeleniumUtilities.getProperties("excelReportLocation");
			SeleniumUtilities.getFile(reportPath);
			SeleniumUtilities.getInputSteam();
			excelWorkBook = new XSSFWorkbook(SeleniumUtilities.getInputSteam());
			excelWorkSheet = excelWorkBook.getSheet("Details");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public static String getCellData(int rowNum, int colNum) throws Exception {
		try {
			cell = excelWorkSheet.getRow(rowNum).getCell(colNum);
			String CellData = cell.getStringCellValue();
			return CellData;
		} catch (Exception e) {
			return "";
		}
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
	
	public static void setCellData(String newResult, XSSFRow newRow, int colNum, String fileTestData) throws Exception {
		XSSFCell Cell = newRow.createCell(colNum);
		Cell.setCellValue(newResult);
		WriteExcelReport.writeData(fileTestData);
	}
	
	public static void writeData(String newFileTestData) throws Exception {
		outputStream = new FileOutputStream(newFileTestData);
		excelWorkBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}
	
	public static void updateResult(String description, String moduleName, boolean boolResult, String attachment) {
		String newResult, slNumber;
		try {
			WriteExcelReport.setExcel();
			int rowNo = SeleniumUtilities.getRowCount() + 1;
			System.out.println("rowcount" + rowNo);
			XSSFRow newRow = WriteExcelReport.getRow(rowNo);
			int newSlNumber = 0;
			slNumber = WriteExcelReport.getCellData(rowNo, 0);
			if(slNumber.equalsIgnoreCase("Sl No")) {
				newSlNumber = newSlNumber + 1;
			} else {
				newSlNumber = Integer.parseInt(slNumber);
				newSlNumber = newSlNumber + 1;
			}
			slNumber = Integer.toString(newSlNumber);
			int colNo = 0;
			WriteExcelReport.setCellData(slNumber, newRow, colNo, reportPath);
			colNo = colNo + 1;
			WriteExcelReport.setStaticCellData(description, rowNo, colNo, reportPath);
			colNo = colNo + 1;
			WriteExcelReport.setStaticCellData(moduleName, rowNo, colNo, reportPath);
			if(boolResult == true) {
				newResult = "Pass";
			} else {
				newResult = "FAIL";
			}
			colNo = colNo + 1;
			WriteExcelReport.setStaticCellData(newResult, rowNo, colNo, reportPath);
			colNo = colNo + 1;
			WriteExcelReport.setStaticCellData(attachment, rowNo, colNo, reportPath);			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
