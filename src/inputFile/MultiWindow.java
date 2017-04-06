package inputFile;

import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class MultiWindow {

@Test
public void TestPopUp() throws InterruptedException{
// Open browser
WebDriver driver=new FirefoxDriver();

// Maximize browser
driver.manage().window().maximize();

// Load app
driver.get("http://www.naukri.com/");

// It will return the parent window name as a String
driver.findElement(By.name("opc")).click();
String parent = driver.getWindowHandle();		
Set<String> set = driver.getWindowHandles();
Iterator<String> iterator = set.iterator();
while(iterator.hasNext()) {
   String childWindow = iterator.next();
   if(!parent.equals(childWindow)) {
	   driver.switchTo().window(childWindow);
   }
System.out.println(driver.switchTo().window(childWindow).getTitle());
}

driver.close();
}

// once all pop up closed now switch to parent window
driver.switchTo().window(parent);

}
}