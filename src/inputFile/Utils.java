package inputFile;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Util class consists wait for page load,page load with user defined max time and is used globally in all classes and methods
 * 
 */
public class Utils extends WebDriverFactory {
	
	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
	public static int maxElementWait = 25;
	
	public static void waitForPageLoad(final WebDriver driver) {
		waitForPageLoad(driver, WebDriverFactory.maxPageLoadWait);
	}

	public static void waitForPageLoad(final WebDriver driver, int maxWait) {
		long startTime = StopWatch.startTime();
		FluentWait <WebDriver> wait = new WebDriverWait(driver, maxWait).pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(StaleElementReferenceException.class).withMessage("Page Load Timed Out");
		try {
			if (WebDriverFactory.includeDocumentLoad.equalsIgnoreCase("true"))
				wait.until(WebDriverFactory.documentLoad);
			if (WebDriverFactory.includeimagesLoad.equalsIgnoreCase("true"))
				wait.until(WebDriverFactory.imagesLoad);
			if (WebDriverFactory.includeframesLoad.equalsIgnoreCase("true"))
				wait.until(WebDriverFactory.framesLoad);
			String title = driver.getTitle().toLowerCase();
			String url = driver.getCurrentUrl().toLowerCase();
			Log.event("Page URL:: " + url);
			if ("the page cannot be found".equalsIgnoreCase(title) || title.contains("is not available") || url.contains("/error/") || url.toLowerCase().contains("/errorpage/")) {
				Assert.fail("Site is down. [Title: " + title + ", URL:" + url + "]");
			}
		} catch (TimeoutException e) {
			driver.navigate().refresh();
			wait.until(WebDriverFactory.documentLoad);
			wait.until(WebDriverFactory.imagesLoad);
			wait.until(WebDriverFactory.framesLoad);
		}
	} 
	
	public static String getTestOrientation() {
		String dataToBeReturned = null;
		boolean checkExecutionOnSauce = false;
		boolean checkDeviceExecution = false;
		checkExecutionOnSauce = (System.getProperty("SELENIUM_DRIVER") != null || System.getenv("SELENIUM_DRIVER") != null) ? true : false;

		if (checkExecutionOnSauce) {
			checkDeviceExecution = ((System.getProperty("runUserAgentDeviceTest") != null) && (System.getProperty("runUserAgentDeviceTest").equalsIgnoreCase("true"))) ? true : false;
			if (checkDeviceExecution) {
				dataToBeReturned = (System.getProperty("deviceOrientation") != null) ? System.getProperty("deviceOrientation") : "no sauce run system variable: deviceOrientation ";
			}
			else {
				dataToBeReturned = "sauce browser test: no orientation";
			}
		}
		else {
			checkDeviceExecution = (configProperty.hasProperty("runUserAgentDeviceTest") && (configProperty.getProperty("runUserAgentDeviceTest").equalsIgnoreCase("true"))) ? true : false;
			if (checkDeviceExecution) {
				dataToBeReturned = configProperty.hasProperty("deviceOrientation") ? configProperty.getProperty("deviceOrientation") : "no local run config variable: deviceOrientation ";
			} else {
				dataToBeReturned = "local browser test: no orientation";
			}
		}
		return dataToBeReturned;
	}
	
	public static boolean waitForElement(WebDriver driver, WebElement element) {
		return waitForElement(driver, element, maxElementWait);
	}
	
	public static boolean waitForElement(WebDriver driver, WebElement element, int maxWait) {
		boolean statusOfElementToBeReturned = false;
		long startTime = StopWatch.startTime();
		WebDriverWait wait = new WebDriverWait(driver, maxWait);
		try {
			if (element.isDisplayed() && element.isEnabled()) {
				statusOfElementToBeReturned = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusOfElementToBeReturned;
	}

	public static WebDriver switchWindows(WebDriver driver, String windowToSwitch, String opt, String closeCurrentDriver) throws Exception {
		WebDriver currentWebDriver = driver;
		WebDriver assingedWebDriver = driver;
		boolean windowFound = false;
		ArrayList <String> multipleWindows = new ArrayList <String>(assingedWebDriver.getWindowHandles());
		for (int i = 0; i < multipleWindows.size(); i++) {
			assingedWebDriver.switchTo().window(multipleWindows.get(i));
			if (opt.equals("title")) {
				if (assingedWebDriver.getTitle().equals(windowToSwitch)) {
					windowFound = true;
					break;
				}
			}
			else if (opt.equals("url")) {
				if (assingedWebDriver.getCurrentUrl().contains(windowToSwitch)) {
					windowFound = true;
					break;
				}
			}
		}
		if (!windowFound)
			throw new Exception("Window: " + windowToSwitch + ", not found");
		else {
			if (closeCurrentDriver.equals("true"))
				currentWebDriver.close();
		}
		return assingedWebDriver;
	}
}
