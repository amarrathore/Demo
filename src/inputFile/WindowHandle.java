package inputFile;

import java.util.Iterator;

import java.util.Set;
import org.openqa.selenium.WebDriver;

public class WindowHandle {
	public WebDriver driver;
	public void window() {
		String parentWindow = driver.getWindowHandle();	    
	    Set<String> set =  driver.getWindowHandles();
	    Iterator<String> iterator = set.iterator();
	      while(iterator.hasNext()) {
	    	  String childWindow = iterator.next();
	    	  if(!parentWindow.equals(childWindow)) {
	        	  driver.switchTo().window(childWindow).getTitle();
	        	  }
	      }
	}
}
