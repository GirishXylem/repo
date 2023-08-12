package com.visenti.test.automation.web.stepdefinitions;

import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.WebDriver;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.helpers.DriverManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.hooks.ServiceHooks;
import com.visenti.test.automation.web.pageobjects.dmatool.HomePage;
import com.visenti.test.automation.web.pageobjects.dmatool.LogoutPage;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LogoutStepDefinitions {

	private WebDriver driver;
	HomePage objHomePage;
	LogoutPage objLogoutPage;

	public LogoutStepDefinitions() {
		//driver = ServiceHooks.driver;
		driver=DriverManager.getDriver();
		objHomePage = new HomePage(driver);
		objLogoutPage = new LogoutPage(driver);
	}

	@When("^I perform Logout action$")
	public void performLogoutAction() throws InterruptedException {
		objHomePage.performLogoutAction();
		Log.info("Successfully Performed Logout Action");
		Reporter.addStepLog("Successfully Performed Logout Action");
	}

	@Then("^I should see a message \"([^\"]*)\"$")
	public void verifyLogoutSuccessfulMessage(String expectedMessage) {
		String actualMessage = objLogoutPage.getLogoutSuccessText();
		System.out.println("Actual" + actualMessage);
		System.out.println("Expected " + expectedMessage);
		assertEquals(actualMessage, expectedMessage);
		Log.info("Logout is successful and user has seen the success message");
		Reporter.addStepLog("Logout is successful and user has seen the success message");

	}

}
