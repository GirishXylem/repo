package com.visenti.test.automation.web.pageobjects.ums;

import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.utils.CommonUtils;
import com.visenti.test.automation.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UMSHomePage {

	private WebDriver driver;

	public UMSHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//button[@title = 'Account']")
	private WebElement accountButton;

	@FindBy(xpath = "//button[@type = 'button' and @aria-label = 'open drawer']")
	private WebElement openDrawerButton;

	@FindBy(xpath= "//span[text()='Users']")
	private WebElement usersButton;

	@FindBy(xpath= "//span[text()='Dashboard']")
	private WebElement dashboardButton;

	@FindBy(xpath= "//span[text()='Profiles']")
	private WebElement profileButton;

	@FindBy(xpath = "//a[@href= '#/logout']")
	private WebElement logoutButton;

	@FindBy(xpath = "//h1[text() = 'User Management System']")
	private WebElement umsTitle;

	@FindBy(xpath = "//input[@placeholder = 'Search...']")
	private WebElement searchTextBox;

	@FindBy(xpath = "//span[contains(text(),'Username')]/ancestor::thead/following-sibling::tbody//tr/td[5]")
	private WebElement userNameFromTheSearch;

	@FindBy(xpath = "//h1[text()='User Management System']/ancestor::header/" +
			"following-sibling::div/div/div/div/div/div/button/span/span")
	private WebElement umsVersion;


	public void performLogoutAction() {
		clickOnUmsAccountButton();
		clickOnLogoutInAccountMenu();
	}

	public void clickOnUmsAccountButton() {
		WebElement accntButton = SeleniumUtils.waitUntilElementClickable(accountButton, driver, 20L);
		Log.info("Waited for Account Button to be clickable");

		//SeleniumUtils.clickOnAnElement(accntButton);
		SeleniumUtils.clickOnAnElement(accntButton);
		Log.info("Clicked on Account button");
	}

	public void clickOptionFromTheUmsDrawer(String option){
		SeleniumUtils.clickOnAnElement(openDrawerButton);
		CommonUtils.wait(1);
		SeleniumUtils.clickOnAnElement(
			driver.findElement(By.xpath("//span[text()='"+ option +"']")));

		Log.info("Clicked on "+ option + "button");
	}

	public void searchTheUser() {
		SeleniumUtils.untilPageLoadComplete(driver,2L);
		SeleniumUtils.enterTextToAField(searchTextBox,
				ConfigFileReader.getConfigProperty("common.username"));

		Log.info("searched with the user" + ConfigFileReader.getConfigProperty("common.username"));
	}

	public String verifyTheUserPresenceByText() {
		String userValue = SeleniumUtils.getTextOfAWebElement(userNameFromTheSearch);
		Log.info("User retrieved from the search is " +
				ConfigFileReader.getConfigProperty("common.username"));

		return userValue.toLowerCase();
	}

	public String getUmsTitle(){
		return SeleniumUtils.getTextOfAWebElement(umsTitle);
	}

	public String getUmsVersion(){
		SeleniumUtils.clickOnAnElement(openDrawerButton);
		String version = SeleniumUtils.getTextOfAWebElement(umsVersion);
		Log.info("UMS application version : " + version);

		return version;
	}

	public boolean isAccountButtonDisplayed(){
		return SeleniumUtils.isElementDisplayed(accountButton);
	}

	public void clickOnLogoutInAccountMenu() {
		SeleniumUtils.performAJavaScriptClick(driver,logoutButton);
		Log.info("Clicked on Logout menu item");
	}
}
