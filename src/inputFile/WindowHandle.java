package inputFile;

import java.util.Iterator;

import java.util.Set;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

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
	

	
	@Test(enabled = false)
	public void switchTab() {
		try {
			String parentWindow = driver.getWindowHandle();	    
			Set<String> set =  driver.getWindowHandles();
			Iterator<String> iterator = set.iterator();
			while(iterator.hasNext()) {
				String childWindow = iterator.next();
				if(!parentWindow.equals(childWindow)) {
					driver.switchTo().window(childWindow).getTitle();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
