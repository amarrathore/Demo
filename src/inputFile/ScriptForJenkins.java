package inputFile;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ScriptForJenkins {
	public WebDriver driver;
	
	  @Test
	  public void test() {
		  System.setProperty("webdriver.gecko.driver", "./Driver/geckodriver.exe");
		driver = new FirefoxDriver();
	    driver.get("https://www.volaris.com/?culture=en-US&Flag=us");	    
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    
	    try {
			driver.findElement(By.name("opc")).click();
			
			String parent = driver.getWindowHandle();			
			Set<String> set = driver.getWindowHandles();
			Iterator<String> iterator = set.iterator();
			while(iterator.hasNext()) {
			   String childWindow = iterator.next();
			   if(!parent.equals(childWindow)) {
				   driver.switchTo().window(childWindow);
			   }
				   
			driver.findElement(By.xpath("//*[@id='input_origin']")).click();
			driver.findElement(By.xpath("//*[@id='btn_origen']")).click();
			driver.findElement(By.linkText("Acapulco")).click();
			//driver.findElement(By.xpath("//*[@id='MX']/div/ul[1]/li[1]/a")).click();
			driver.findElement(By.xpath("//*[@id='input_destination']")).click();
			driver.findElement(By.xpath("//*[@id='btn_origen']")).click();
			//driver.findElement(By.linkText("Mexico City")).click();
			driver.findElement(By.xpath("//*[@id='MX']/div/ul[1]/li[1]/a")).click();
			
			
  }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	  } 
}
