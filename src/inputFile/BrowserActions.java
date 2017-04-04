package inputFile;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import com.sun.deploy.uitoolkit.impl.fx.Utils;

public class BrowserActions extends WebDriverFactory {
	
	public static void typeOnTextField(WebElement text, String textToType, WebDriver driver, String elementDescription) {
		if (!Utils.waitForElement(driver, text, 1))
			throw new SkipException(elementDescription + " field not found in page");
		try {
			text.clear();
			text.click();
			text.sendKeys(textToType);
		} catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " field not found in page");
		}
	}

	public static void typeOnTextField(String txt, String txtToType, WebDriver driver, String elementDescription) {
		WebElement element = driver.findElement(By.cssSelector(txt));
		if (!Wait.waitForElement(driver, element, 1))
			throw new SkipException(elementDescription + " field not found in page");
		try {
			element.clear();
			element.click();
			element.sendKeys(txtToType);
		} catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " field not found in page");
		}
	}
	
	public static void clickOnButton(WebElement btn, WebDriver driver, String elementDescription) {

		if (!Utils.waitForElement(driver, btn, 20))
			throw new SkipException(elementDescription + " not found in page");

		try {
			btn.click();
		} catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page");
		}
	}
	
	public static void clickOnButton(String btn, WebDriver driver, String elementDescription) {
		WebElement element = driver.findElement(By.cssSelector(btn));
		if (!Utils.waitForElement(driver, element, 1))
			throw new SkipException(elementDescription + " not found in page");
		try {			
			element.click();
		} catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page");
		}
	}
	
	public static void clickOnButton( WebDriver driver, String btn, String elementDescription) {
		WebElement element = driver.findElement(By.cssSelector(btn));		
		if (!Utils.waitForElement(driver, element, 1))
			throw new SkipException(elementDescription + " not found in page");
		try {			
			element.click();
		} catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page");
		}
	}
	
	public static boolean elementDisplayed(WebDriver driver, String cssSelectorForWebElement) {
		boolean displayed = false;
		try {
			displayed = driver.findElement(By.cssSelector(cssSelectorForWebElement)).isDisplayed();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return displayed;
	}
	
	public static String getText(WebDriver driver, WebElement fromWhichTxtShldExtract, String elementDescription) {
		String textFromHTMLAttribute = "";
		try {
			textFromHTMLAttribute = fromWhichTxtShldExtract.getText();
			if (textFromHTMLAttribute.isEmpty())
				textFromHTMLAttribute = fromWhichTxtShldExtract.getAttribute("textContent");
		} catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page");
		}
		return textFromHTMLAttribute;
	}
	
	public static String getText(WebDriver driver, String fromWhichTxtShldExtract, String elementDescription) {
		String textFromHTMLAttribute = "";
		WebElement element = driver.findElement(By.cssSelector(fromWhichTxtShldExtract));
		try {
			textFromHTMLAttribute = element.getText();
			if (textFromHTMLAttribute.isEmpty())
				textFromHTMLAttribute = element.getAttribute("textContent");
		} catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page");
		}
		return textFromHTMLAttribute;
	}
	
	public static String getTextFromAttribute(WebDriver driver, WebElement fromWhichTxtShldExtract, String attributeName, String elementDescription) {
		String textFromHTMLAttribute = "";
		try {
			textFromHTMLAttribute = fromWhichTxtShldExtract.getAttribute(attributeName);
		}catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page");
		}
		return textFromHTMLAttribute;
	}

	public static String getTextFromAttribute(WebDriver driver, String fromWhichTxtShldExtract, String attributeName, String elementDescription) {
		String textFromHTMLAttribute = "";
		WebElement element = driver.findElement(By.cssSelector(fromWhichTxtShldExtract));
		try {
			textFromHTMLAttribute = element.getAttribute(attributeName);
		} catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page");
		}
		return textFromHTMLAttribute;
	}
	
	public static void selectFromComboBox(WebElement btn, String optToSelect, WebDriver driver, String elementDescription) {
		if (!Utils.waitForElement(driver, btn, 1))
			throw new SkipException(elementDescription + " not found in page");
		try {
			Select selectBox = new Select(btn);
			selectBox.selectByValue(optToSelect);
		} catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page");
		}
	}

	public static void selectFromComboBox(String btn, String optToSelect, WebDriver driver, String elementDescription) {
		WebElement element = driver.findElement(By.cssSelector(btn));
		if (!Utils.waitForElement(driver, element, 1))
			throw new SkipException(elementDescription + " not found in page");
		try {
			Select selectBox = new Select(element);
			selectBox.selectByValue(optToSelect);
		} catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page");
		}
	}
}