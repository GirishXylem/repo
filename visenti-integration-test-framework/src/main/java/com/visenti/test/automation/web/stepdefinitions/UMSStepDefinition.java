package com.visenti.test.automation.web.stepdefinitions;

import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.DriverManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.web.pageobjects.ums.UMSHomePage;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

public class UMSStepDefinition {
    private WebDriver driver;

    public UMSStepDefinition() {
        driver= DriverManager.getDriver();
    }

    @When("^I perform Logout action for ums portal$")
    public void performLogoutAction() throws InterruptedException {
        UMSHomePage umsHomePage = new UMSHomePage(driver);
        umsHomePage.performLogoutAction();
        Log.info("Successfully Performed Logged out from UMS");
        Reporter.log("Successfully Performed Logged out from UMS");
    }

    @When("^I click on the \"([^\"]*)\" from the drawer list$")
    public void clickOnTheDrawerOption(String option) {
        UMSHomePage umsHomePage = new UMSHomePage(driver);
        umsHomePage.clickOptionFromTheUmsDrawer(option);
    }

    @When("^I verify the details in the page$")
    public void verifyPageDetails(){
        UMSHomePage umsHomePage = new UMSHomePage(driver);
        Assert.assertEquals(umsHomePage.getUmsTitle(),"User Management System",
                "Title is not teh same");
        Assert.assertEquals(umsHomePage.getUmsVersion(),ConfigFileReader.getConfigProperty("ums.version"),
                "Ums version is not the actual version");
        Assert.assertTrue(umsHomePage.isAccountButtonDisplayed(),"Account button not visible");
    }

    @When("^I search the logged in user to verify if user is present$")
    public void searchAndVerifyUser(){
        UMSHomePage umsHomePage = new UMSHomePage(driver);
        Reporter.log("search the user" + ConfigFileReader.getConfigProperty("common.username"));
        umsHomePage.searchTheUser();

        Assert.assertEquals(umsHomePage.verifyTheUserPresenceByText(),"visentiqa@gmail.com","User is not available from the search");
        Log.info("User " + ConfigFileReader.getConfigProperty("common.username") +" is available from the search");
        Reporter.log("User " + ConfigFileReader.getConfigProperty("common.username") +" is available from the search");
    }

}
