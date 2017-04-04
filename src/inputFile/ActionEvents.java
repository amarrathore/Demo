package inputFile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.sun.corba.se.spi.orbutil.fsm.Action;

public class ActionEvents {
	public static WebDriver driver;
	public static Actions builder;
	public static WebElement element;
	public static Select select;
	public static String text = null;
		
	public static WebElement options(String inspectID) {
		try {
			element = driver.findElement(By.linkText(inspectID));
			builder = new Actions(driver);
			 mousehover = builder.moveToElement(element).click().build().perform();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mousehover;
	}
	
	public static void keys() {
		
	}
}
