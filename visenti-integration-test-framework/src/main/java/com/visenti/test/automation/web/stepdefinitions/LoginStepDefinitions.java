package com.visenti.test.automation.web.stepdefinitions;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.cucumber.listener.Reporter;
import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.DriverManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;
import com.visenti.test.automation.utils.CommonUtils;
import com.visenti.test.automation.utils.SeleniumUtils;
import com.visenti.test.automation.web.pageobjects.dmatool.HomePage;
import com.visenti.test.automation.web.pageobjects.dmatool.LoginPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * This class will define login feature files related definitions LoginProfile,
 * LoginUMS
 */
public class LoginStepDefinitions {

	private WebDriver driver;
	LoginPage objLoginPage;
	
	String customerName=RuntimeConfigSingleton.getInstance().getCustomerName();

	public LoginStepDefinitions() {
		driver=DriverManager.getDriver();
		objLoginPage = new LoginPage(driver);
	}

	@Given("^I am on the \"([^\"]*)\" portal Login Page$")
	public void navigatingToLoginPage(String portalType) {
				
		SeleniumUtils.navigateToUrl(driver, portalType);
		Log.info("User navigated to the '"+portalType+"' Portal Login page for the customer "+customerName);
		Reporter.addStepLog("User navigated to the '"+portalType+"' Portal Login page for the customer "+customerName);
	}
	
	@When("^I enter valid credentials$")
	public void enteringValidCredentials() {

		objLoginPage.enterLoginCredentials(ConfigFileReader.getConfigProperty("username"),
				ConfigFileReader.getConfigProperty("password"));

		Log.info("Entered Valid username and password");
		Reporter.addStepLog("Entered Valid username and password");
		
	}

	@When("^I click on Login button$")
	public void clickLoginButton() {
		objLoginPage.clickOnLoginButton();
		Log.info("Successfully clicked on Login button");
		Reporter.addStepLog("Successfully clicked on Login button");
		}

	@Then("^I should be on the Home Page of \"([^\"]*)\" portal$")
	public void verifyNavigationToPortalHomePage(String portalType) {
		// Wait for Page to load Completely
		SeleniumUtils.untilPageLoadComplete(driver,
				Long.parseLong(ConfigFileReader.getConfigProperty("implicitly.wait")));

		

		if(portalType.equalsIgnoreCase("View")){
			
			HomePage homePage=new HomePage(driver);
			
			//Validating all the Mandatory Elements in the View portal Page are visible
			List<WebElement>elements=homePage.getMandatoryElementsOnPageLoad();
			
			for(WebElement ele:elements)
			{
				assertTrue(ele.isDisplayed());
				
			}
			
			//Waiting for 5 seconds after Page load and Mandatory Elements validation
			CommonUtils.wait(5);
			//In Homepage to mimic the actual map dimension
			homePage.clickOnZoomButton("minus");
			}
		
		if(portalType.equalsIgnoreCase("Ums"))
		{
		// Asserting the current url with expected portal url
		assertEquals(ConfigFileReader.getConfigProperty("ums.url.validation"),
				SeleniumUtils.getCurrentUrl(driver));
		}
		Log.info("User successfully navigated to " + portalType + " portal Home Page for the customer "+customerName);
		Reporter.addStepLog("User successfully navigated to " + portalType + " portal Home Page for the customer "+customerName);
		}
	
	@Given("^I have successfully logged in to the \"([^\"]*)\" portal$")
	public void verifySuccessfulLoginToThePortal(String portal) {
		navigatingToLoginPage(portal);
		enteringValidCredentials();
		clickLoginButton();
		verifyNavigationToPortalHomePage(portal);
	}

}
