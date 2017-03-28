package inputFile;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
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
public class SeleniumUtilitiesDemo {
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
	public static ResultSet result;
	
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
			SeleniumUtilitiesDemo.getFile(path);
			SeleniumUtilitiesDemo.getInputSteam();
			excelWorkBook = new XSSFWorkbook(inputStream);
			excelWorkSheet = excelWorkBook.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterator<Row> iterator = excelWorkSheet.iterator();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				Object value = getCellData(cell);
				System.out.print("\t" + value);
			}
			System.out.println();
        }
		inputStream.close();
	}
	
	public static int getRowCount() {
		int rowCount = excelWorkSheet.getLastRowNum()-1;
		return rowCount;
	}
	
	public static int getColCount() {
		int colCount = excelWorkSheet.getRow(getRowCount()).getLastCellNum();
		return colCount;
	}
    
	public static Object getCellData(Cell cell) throws Exception {
    	switch (cell.getCellType()) {
    	case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
                return getExcelDateData(cell);
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
	
	public static void setCellData(String newResult, XSSFRow newRow, int colNum, String testFileData) throws Exception {
		cell = newRow.createCell(colNum);
		cell.setCellValue(newResult);
		SeleniumUtilitiesDemo.writeData(testFileData);
	}
	
	public static void setStaticCellData(String newResult, int rowNum, int colNum, String newTestFileData) throws Exception {
		try {
			row = excelWorkSheet.getRow(rowNum);
			cell = row.getCell(colNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
			if (cell == null) {
				cell = row.createCell(colNum);
				cell.setCellValue(newResult);
			} else {
				cell.setCellValue(newResult);
			}
			outputStream = new FileOutputStream(newTestFileData);
			excelWorkBook.write(outputStream);
			XSSFFormulaEvaluator.evaluateAllFormulaCells(excelWorkBook);	
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeData(String newTestFileData) throws Exception {
		outputStream = new FileOutputStream(newTestFileData);
		excelWorkBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}
	
	public static double getExcelNumericData(Cell cell) {
		double getExcelNumericData = cell.getNumericCellValue();
		return getExcelNumericData;
	}
	
	private static Date getExcelDateData(Cell cell) {
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
	    
	public static String getProperties(String locatorName) {	
		try {
			SeleniumUtilitiesDemo.getFile(System.getProperty("user.dir") + "/src/config.properties");
			SeleniumUtilitiesDemo.getInputSteam();
			property = new Properties();
			property.load(inputStream);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return property.getProperty(locatorName);
	}
	
	public static void captureScreenshot(ITestResult result, WebDriver driver) throws WebDriverException, Exception {
		String location = SeleniumUtilitiesDemo.getProperties("screenshotLocation");
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
		result = statement.executeQuery(query);
		List<String> values = new ArrayList<String>();
		while(result.next()) {
			values.add(result.getString(1).toString().trim());
			System.out.println(result.getString(1));
		}
		return values;
	}
} 