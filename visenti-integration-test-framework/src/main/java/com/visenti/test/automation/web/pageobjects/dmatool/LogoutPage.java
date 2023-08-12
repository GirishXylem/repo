package com.visenti.test.automation.web.pageobjects.dmatool;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.utils.SeleniumUtils;

public class LogoutPage {

	private WebDriver driver;

	public LogoutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.XPATH, using = "//div[@class='alert alert-success']/h2")
	private WebElement logoutMessageTxt;

	public String getLogoutSuccessText() {
		String logoutSuccessText = SeleniumUtils.getTextOfAWebElement(logoutMessageTxt);
		Log.info("The Logout success message on the Page :--" + logoutSuccessText);
		return logoutSuccessText;
	}

}
