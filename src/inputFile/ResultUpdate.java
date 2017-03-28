package inputFile;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class ResultUpdate {
	public static String newPath = null;
	public static String newFileName = null;
	private static String reportPath = null;
	private static String newSheetName = "Details";
	final static Logger Log = Logger.getLogger(ResultUpdate.class);
	
	public static void setPathForResultUpdate() throws Exception {
		reportPath = "C:\\Users\\amarnath.rathore\\Desktop\\Automation\\workspace\\Demo\\inputFiles\\testData.xlsx";
		ExcelUtility.setExcelFile(reportPath, newSheetName);
		Log.info("Excel path set at " + reportPath);
		return;
	}
	
	public static void setPathForResultUpdate(String sheetName, String testStartDate, String timeZone, String browserInfo, String OSInfo) {
		reportPath = newPath + newFileName;
		try {
			ExcelUtility.setExcelFile(reportPath, sheetName);
			Log.info("Test Details in " + sheetName);
			int rowNo = 2, colNo = 6;
			ExcelUtility.setStaticCellData(testStartDate, rowNo, colNo, reportPath);
			rowNo = rowNo + 1;
			ExcelUtility.setStaticCellData(timeZone, rowNo, colNo, reportPath);
			rowNo = rowNo + 1;
			ExcelUtility.setStaticCellData(browserInfo, rowNo, colNo, reportPath);
			rowNo = rowNo + 1;
			ExcelUtility.setStaticCellData(OSInfo, rowNo, colNo, reportPath);
			Log.info("Sucess Updating Details for " + sheetName);
		} catch (Exception e) {
			Log.error("Error Updating Details for " + sheetName + e.toString().substring(0,10));
		}
		return;
	}

	public static void updateResult(String URL, String section, boolean result, String comments, int priority) {
		String newResult, number, newPriority = null;
		try {
			int rowNo = ExcelUtility.getRowCount() + 1;
			XSSFRow newRow = ExcelUtility.getRow(rowNo);
			int newNumber = 0;
			number = ExcelUtility.getCellData(rowNo - 1, 0);
			if(number.equalsIgnoreCase("Sl No")) {
				newNumber = newNumber + 1;
			} else {
				newNumber = Integer.parseInt(number);
				newNumber = newNumber + 1;
			}
			number = Integer.toString(newNumber);
			int colNo = 0;
			ExcelUtility.setCellData(number, newRow, colNo, reportPath);
			colNo = colNo + 1;
			ExcelUtility.setStaticCellData(URL, rowNo, colNo, reportPath);
			colNo = colNo + 1;
			ExcelUtility.setStaticCellData(section, rowNo, colNo, reportPath);
			if(result == true) {
				newResult = "Pass";
				newPriority = "Pass";
			} else {
				newResult="FAIL";
				//newPriority = config.Priority.get(priority).toString();
				Log.info(newResult + " with " + newPriority + " for " + section);
			}
			colNo = colNo + 1;
			ExcelUtility.setStaticCellData(newResult, rowNo, colNo, reportPath);
			colNo = colNo + 1;
			ExcelUtility.setStaticCellData(comments, rowNo, colNo, reportPath);
			if(!newPriority.equalsIgnoreCase("Pass")){
				colNo = colNo + 1;
				ExcelUtility.setStaticCellData(newPriority, rowNo, colNo, reportPath);
			}
			Log.info("Success updating results for " + section);
		} catch ( Exception e){
			Log.info("Error updating results for " + section);
			Log.error(e.toString());
		}
	}
}
