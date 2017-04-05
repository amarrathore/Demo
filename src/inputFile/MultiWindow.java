package inputFile;

import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

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
String parent=driver.getWindowHandle();
Set<String>s1=driver.getWindowHandles();
Iterator<String> I1= s1.iterator();
while(I1.hasNext()) {
   String child_window=I1.next();
   if(!parent.equals(child_window)) {
driver.switchTo().window(child_window);

System.out.println(driver.switchTo().window(child_window).getTitle());

driver.close();
}

}
// once all pop up closed now switch to parent window
driver.switchTo().window(parent);

}
}