package com.visenti.test.automation.web.pageobjects.dmatool;

import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	private WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.ID, using = "username")
	private WebElement usernameTextBox;

	@FindBy(how = How.ID, using = "password")
	private WebElement passwordTextBox;

	@FindBy(how = How.NAME, using = "submit")
	private WebElement signinButton;

	@FindBy(how = How.XPATH, using = "//div[@class='alert alert-danger']/span")
	private WebElement invalidCredentialTxt;

	@FindBy(how = How.XPATH, using = "//div[@class='alert alert-danger']/span[2]")
	private WebElement emptyPasswordWarningTxt;

	public void setUserName(String username) {
		SeleniumUtils.enterTextToAField(usernameTextBox, username);
		Log.info("Entered " + username + " in the username text box");

	}

	public void setPassword(String password) {
		SeleniumUtils.enterTextToAField(passwordTextBox, password);
		Log.info("Entered password in the password text box");
	}

	public void clickOnLoginButton() {
		SeleniumUtils.performAJavaScriptClick(driver, signinButton);
		Log.info("Click performed on Login button");
	}

	public void enterLoginCredentials(String username, String password) {
		setUserName(username);
		setPassword(password);
	}

	public void performLoginAction(String username, String password) {
		enterLoginCredentials(username, password);
		clickOnLoginButton();
	}

	public String getLoginPageTitle() {
		String pageTitle = SeleniumUtils.getPageTitle(driver);
		Log.info("The Login Page title :--" + pageTitle);
		return pageTitle;
	}

}
