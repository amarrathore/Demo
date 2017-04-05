package inputFile;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.gargoylesoftware.htmlunit.javascript.host.Set;

public class WindowHandle {
	public WebDriver driver;
	public void window() {
	 String windowHandle = driver.getWindowHandle();
	    driver.findElement(By.name("opc")).click();
	    Set<String> handles =  driver.getWindowHandles();
	    Iterator<String> iterator = handles.Iterator();
	      while(iterator.hasNext()) {
	    	  String handle = iterator.next();
	           if(!windowHandle.equals(handle)) {
	              driver.switchTo().window(handle).getTitle();
	              }
	       }
	}
}
