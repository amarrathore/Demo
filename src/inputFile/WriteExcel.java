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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class WriteExcel {
	public static File file;
	public static FileInputStream inputStream;
	public static XSSFWorkbook excelWorkBook;
	public static XSSFSheet excelWorkSheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static FileOutputStream outputStream;
	
	public static void readExcel() throws IOException {
		String fileLocation = "./inputFiles/testData.xlsx";
		file = new File(fileLocation);
		inputStream = new FileInputStream(file);
		excelWorkBook = new XSSFWorkbook(inputStream);
		excelWorkSheet = excelWorkBook.getSheetAt(0);
		Iterator<Row> iterator = excelWorkSheet.iterator();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch(cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						System.out.print(cell.getBooleanCellValue() + "\t");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						System.out.print(cell.getNumericCellValue() + "\t");
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue() + "\t");
						break;
				}
			}
			System.out.println("");
		}
		inputStream.close();
		outputStream = new FileOutputStream(fileLocation + "test.xlsx");
		excelWorkBook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		}
	
	public static void main(String[] args) throws IOException {
		WriteExcel.readExcel();
	}
}
