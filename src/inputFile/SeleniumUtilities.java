package inputFile;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
/**
 * @author BCS Technology
 * 
 */
public class SeleniumUtilities {
	public static File file;
	public static FileInputStream inputStream;
	public static XSSFWorkbook excelWorkBook;
	public static XSSFSheet excelWorkSheet;
	public static Properties property;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static FileOutputStream outputStream;
	public static Connection connect;
	public static Statement statement;
	public static ResultSet resultSet;
	private static String reportPath = null;
	
	public static File getFile(String fileLocation) {
		try {
			file = new File(fileLocation);
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

	public static void setExcelFile(String fileLocation, int excelSheetNumber) throws IOException {
		SeleniumUtilities.getFile(fileLocation);
		SeleniumUtilities.getInputSteam();
		excelWorkBook = new XSSFWorkbook(inputStream);
		excelWorkSheet = excelWorkBook.getSheetAt(excelSheetNumber);
		Iterator<Row> iterator = excelWorkSheet.iterator();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				Object value = cellToType(cell);
				System.out.print("\t" + value);
			}
			System.out.println();
        }
		inputStream.close();
	}
	
	public static int getRowCount() {
		int rowCount = excelWorkSheet.getLastRowNum();
		return rowCount;
	}
	
	public static XSSFRow getRow(int rowNum) {
		return excelWorkSheet.createRow(rowNum);
	}
	
	public static int getColCount() {
		int colCount = excelWorkSheet.getRow(getRowCount()).getLastCellNum();
		return colCount;
	}
    
	public static Object cellToType(Cell cell) {
    	switch (cell.getCellType()) {
    	case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
                return getExcelDateValue(cell);
            } else {
            	return getExcelNumericData(cell);
            }		
		case Cell.CELL_TYPE_STRING:
	        return getExcelStringData(cell);
		case Cell.CELL_TYPE_BOOLEAN:
	        return getExcelBooleanData(cell);
	        default:
	        return null;
	    }
	}    
	
	public static double getExcelNumericData(Cell cell) {
		double getExcelNumericData = cell.getNumericCellValue();
		return getExcelNumericData;
	}
	
	private static Date getExcelDateValue(Cell cell) {
		Date getExcelDateData = cell.getDateCellValue();
		return getExcelDateData;
	}

	public static String getExcelStringData(Cell cell) {
		String getExcelStringData = cell.getStringCellValue();
		return getExcelStringData;
	}
	
	public static boolean getExcelBooleanData(Cell cell) {
		boolean getExcelBooleanData = cell.getBooleanCellValue();
		return getExcelBooleanData;
	}
	
	public static void setExcel() throws IOException {
		try {
			reportPath = SeleniumUtilities.getProperties("excelReportLocation");		
			SeleniumUtilities.getFile(reportPath);
			SeleniumUtilities.getInputSteam();
			excelWorkBook = new XSSFWorkbook(inputStream);
			excelWorkSheet = excelWorkBook.getSheetAt(0);
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
			SeleniumUtilities.writeData(newFileTestData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setCellData(String newResult, XSSFRow newRow, int colNum) throws Exception {
		XSSFCell Cell = newRow.createCell(colNum);
		Cell.setCellValue(newResult);		
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
			SeleniumUtilities.setExcel();
			int rowNo = SeleniumUtilities.getRowCount()+1;
			System.out.println("rowcount" + rowNo);
			XSSFRow newRow = SeleniumUtilities.getRow(rowNo);
			int newSlNumber = 0;
			slNumber = SeleniumUtilities.getCellData(rowNo-1, 0);
			if(slNumber.equalsIgnoreCase("Sl No")) {
				newSlNumber = newSlNumber + 1;
			} else {
				newSlNumber = Integer.parseInt(slNumber);
				newSlNumber = newSlNumber + 1;
			}
			slNumber = Integer.toString(newSlNumber);
			int colNo = 0;
			SeleniumUtilities.setCellData(slNumber, newRow, colNo);
			colNo = colNo + 1;
			SeleniumUtilities.setCellData(description, newRow, colNo);			
			colNo = colNo + 1;
			SeleniumUtilities.setCellData(moduleName, newRow, colNo);
			if(boolResult == true) {
				newResult = "Pass";
			} else {
				newResult = "FAIL";
			}
			colNo = colNo + 1;
			SeleniumUtilities.setCellData(newResult, newRow, colNo);
			colNo = colNo + 1;
			SeleniumUtilities.setCellData(attachment, newRow, colNo);
			SeleniumUtilities.writeData(reportPath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	public static String getProperties(String locatorName) {	
		try {
			SeleniumUtilities.getFile(System.getProperty("user.dir") + "/src/config.properties");
			SeleniumUtilities.getInputSteam();
			property = new Properties();
			property.load(inputStream);		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return property.getProperty(locatorName);
	}
	
	public static void captureScreenshot(ITestResult result, WebDriver driver) throws WebDriverException, Exception {
		String location = SeleniumUtilities.getProperties("screenshotLocation");
    	String methodName = result.getName().toString().trim();
    	String timeStamp = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss").format(new Date()) + "_";
		File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	 	try {
	 		FileUtils.copyFile(sourceFile, new File(location + timeStamp + methodName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setDBConnection(String DBConnectionURL, String DBUserName, String DBPassword) throws Exception {
		try {
			String DBClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(DBClass).newInstance();
			connect = DriverManager.getConnection(DBConnectionURL, DBUserName, DBPassword);
			if(connect != null) {
				System.out.println("Database connection is established");
			}
			else {
				System.out.println("Database connection is not established");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> getSQLQuery(String query) throws Exception {
		statement = connect.createStatement();
		resultSet = statement.executeQuery(query);
		List<String> values = new ArrayList<String>();
		while(resultSet.next()) {
			values.add(resultSet.getString(1).toString().trim());
			System.out.println(resultSet.getString(1));
		}
		return values;
	}
} 