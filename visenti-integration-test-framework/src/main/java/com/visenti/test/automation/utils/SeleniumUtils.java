package com.visenti.test.automation.utils;

import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * This class contains all the Selenium Wrapper methods to perform various actions on the WebPage
 *
 */
public class SeleniumUtils {

	/**
	 * @param url This method we are navigating to an url.
	 */

	public static void navigateToUrl(WebDriver driver, String portalType) {
		//String portalUrlForCustomer = FileReaderManager.getInstance().getConfigReader().getPortalUrl(RuntimeConfigSingleton.getInstance().getCustomerName());
		String portalUrl = null;
		String browserName;
		
		if(portalType.equalsIgnoreCase("view"))
		{	portalUrl=ConfigFileReader.getConfigProperty("portal");
			browserName = ConfigFileReader.getConfigProperty("web.browser.name");
			assert browserName != null;
			if(browserName.equalsIgnoreCase("chrome")) {

				driver.get("chrome://settings/");
				((JavascriptExecutor) driver).executeScript("chrome.settingsPrivate.setDefaultZoom(0.8);");
			}
			else if (browserName.equalsIgnoreCase("firefox")) {
				driver.get("about:preferences");
				JavascriptExecutor js = (JavascriptExecutor) driver;
				/*  In future here we need to set the firefox browser zoom level to 80%  */
//				js.executeScript("document.body.style.MozTransform='scale(0.8)';");
//				js.executeScript("document.getElementById(\"defaultZoom\").value = 80%;");
				((JavascriptExecutor) driver).executeScript("document.getElementById(\"defaultZoom\").value = 80;");
			}
			else{
				Log.info("Wrong browser selected & launched");
			}
		}
		else if(portalType.equalsIgnoreCase("ums"))
		{
			portalUrl=ConfigFileReader.getConfigProperty("ums.portal");
		}
		driver.get(portalUrl);
		Log.info("Navigated to the "+portalType+" url for the customer "+RuntimeConfigSingleton.getInstance().getCustomerName() );
	}

	/**
	 * @param element This method clears the input text
	 */
	public static void clearInputText(WebElement element) {
		element.clear();
	}

	/**
	 * @param element
	 * @return This method gets us the text of an element.
	 */
	public static String getTextOfAWebElement(WebElement element) {
		return element.getText();
	}

	/**
	 * @param element
	 * @return This method gets us the text of an element.
	 */
	public static String getTextOfAnElement(WebDriver driver, String element) {
		String text = driver.findElement(By.xpath("//span[contains(text(),'" + element + "')]")).getText();
		return text;
	}

	/**
	 * @param element
	 * @param keysToSend
	 * 
	 * We are entering text to an input field by passing in the
	 * WebElement and the text to enter.
	 */
	public static void enterTextToAField(WebElement element, CharSequence... keysToSend) {
		clearInputText(element);
		element.sendKeys(keysToSend);
	}

	public static boolean isElementDisplayed(WebElement element)
	{
		boolean displayed;
		try{
			displayed = element.isDisplayed();
		}catch (NoSuchElementException e){
			displayed = false;
		}
		return displayed;
	}

	public static boolean isElementEnabled(WebElement element)
	{
		boolean Enabled;
		try{
			Enabled = element.isEnabled();
		}catch (NoSuchElementException e){
			Enabled = false;
		}
		return Enabled;
	}

	
	/**
	 * @param element We are performing click operation on a WebElement.
	 */
	public static void clickOnAnElement(WebElement element) {
		element.click();
	}

	/**
	 * @return We are getting the page title and returning it
	 */
	public static String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}
	
	public static String getCurrentUrl(WebDriver driver)
	{
		return driver.getCurrentUrl();
	}

	/**
	 * @param dropdownList
	 * @param itemName     Using Select class we are ,selecting an Element from the
	 *                     dropdown by visible text
	 */

	public static void selectItemFromDropdownByText(WebElement dropdownList, String itemName) {
		try {
			Select dropdown = new Select(dropdownList);
			dropdown.selectByVisibleText(itemName);
			Log.info("Selected the list item " + itemName + " from the dropdown");
		} catch (Exception e) {
			Log.error("Wrong value " + itemName + " Not found");
			throw e;

		}
	}

	/**
	 * @return We are capturing the screenshots as bytes
	 */
	public static byte[] getScreenshotAsBytes(WebDriver driver) {
		try {
			byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			Log.info("Successfully Taken screenshot ");
			return screenshotBytes;
		} catch (Exception e) {
			Log.error("Some Error in taking screenshot");
			throw e;
		}
	}

	/**
	 * @param targetFile Taking the screenshot and saving it in file.
	 */
	public static void takeScreenshotAndSaveInFile(WebDriver driver, File targetFile) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, targetFile);
			Log.info("Captured Screenshot and saved it in the file '" + targetFile.getAbsolutePath() + "'");
		} catch (IOException e) {
			Log.error("Failed to capture the screenshot");
		}

	}

	// Explicit Wait
	/**
	 * This is an explicit wait method for a WebElement to be clickable
	 * 
	 * @param element
	 * @param driver
	 * @param timeOut
	 * @return
	 */
	public static WebElement waitUntilElementClickable(WebElement element, WebDriver driver, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		WebElement foo = wait.until(ExpectedConditions.elementToBeClickable(element));
		return foo;

	}

	public static WebElement waitUntilElementVisible(WebElement element, WebDriver driver, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		WebElement foo;

		foo = wait.until(ExpectedConditions.visibilityOf(element));

		return foo;
	}

	public static List<WebElement> waitForElementsToBeVisible(List<WebElement> element, WebDriver driver,
			long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElements(element));
		return elements;

	}

	public static void waitForElementToBeInvisible(WebElement element, WebDriver driver, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.invisibilityOf(element));

	}

	public static List<WebElement> waitForAllElementsToBePresent(By by, WebDriver driver, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		return elements;
	}

	public static String GetValueByCookie(WebDriver driver, String cookieName) {
		Set<Cookie> cookies = driver.manage().getCookies();
		String cookieValue = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					cookieValue = cookie.getValue();
				}
			}
		} else {
			Log.error("There is no any available cookie");
		}
		return cookieValue;
	}

	public static void performAJavaScriptClick(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click()", element);
	}

	/**
	 * @param driver, element, text
	 * @return This method enters a text and does an Enter.
	 */
	public static void typeAndEnterTheSearch(WebDriver driver, WebElement element, String text) throws Exception {
		try {
			waitUntilElementVisible(element, driver, 2L);
			clearInputText(element);
			enterTextToAField(element,text);

			CommonUtils.wait(2);

			Actions builder = new Actions(driver);
			builder.sendKeys(Keys.ENTER).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("No such element" + element);
			throw new Exception("No such element" + element);
		}
	}

	/**
	 * @param driver, element, text
	 * @return This method clicks on an element and selects the dropdown element.
	 */
	public static void javaScriptClickAndSelect(WebDriver driver, WebElement element, String text) throws Exception {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			SeleniumUtils.clickOnAnElement(element);

			CommonUtils.wait(1);
			WebElement dropDownElement = null ;
			try {
				dropDownElement = driver.findElement(By.xpath("//li[text()='" + text + "']"));
			}catch (Exception e){
				dropDownElement = driver.findElement(By.xpath("//div[text()='" + text + "']"));
			}

			executor.executeScript("arguments[0]. click();", dropDownElement);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("No such element" + element);
			throw new Exception("No such element" + element);
		}
	}

	public static void untilPageLoadComplete(WebDriver driver, Long timeoutInSeconds){
		until(driver, (d) ->
			{
				Boolean isPageLoaded = (Boolean)((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
				if (!isPageLoaded) System.out.println("Document is loading");
				return isPageLoaded;
			}, timeoutInSeconds);
	}
	
	private static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition, Long timeoutInSeconds){
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds);
		webDriverWait.withTimeout(Duration.ofSeconds(timeoutInSeconds));
		try{
			webDriverWait.until(waitCondition);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}          
	}

}
