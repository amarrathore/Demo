package inputFile;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class ScriptForJenkins {
	public WebDriver driver;
	public Actions action;
	
	@Test
	public void test() throws Exception {	
	
	System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
	driver = new ChromeDriver();
	driver.get("https://www.volaris.com/?culture=en-US&Flag=us");
	Thread.sleep(5000);	
	driver.findElement(By.id("btn_origen")).click();
	Thread.sleep(5000);
	driver.findElement(By.linkText("Cancun")).click();			
	driver.findElement(By.xpath("//*[@id='input_destination']")).click();		
	driver.findElement(By.linkText("Mexico City")).click();
	driver.findElement(By.xpath("(//button[@type='button'])[5]")).click();		
	WebElement oneWay = driver.findElement(By.cssSelector("[id='ulTrips'] li:nth-child(2)"));		
	action = new Actions(driver);
	action.moveToElement(oneWay).click().build().perform();
	driver.findElement(By.id("btnNext")).click();
	Thread.sleep(5000);
	driver.findElement(By.id("btnPassenger")).click();
	Thread.sleep(5000);
	driver.findElement(By.id("btnSearchFlight")).click();
	Thread.sleep(5000);
	driver.findElement(By.xpath("//*[@id='sortedAvailability0']/div[1]/div[1]/div[1]/div[2]")).click();
	Thread.sleep(5000);
	driver.findElement(By.id("submit_search_button")).click();
	Thread.sleep(5000);
	driver.findElement(By.xpath("//*[@id='modcont-combodeals']/div[2]/a")).click();
	
	
	
	
	}
}
