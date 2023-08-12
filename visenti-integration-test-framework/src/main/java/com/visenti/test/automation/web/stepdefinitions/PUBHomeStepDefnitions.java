package com.visenti.test.automation.web.stepdefinitions;

import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.DriverManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.SikuliHelper;
import com.visenti.test.automation.web.pageobjects.dmatool.AimsListViewPage;
import com.visenti.test.automation.web.pageobjects.dmatool.DatePickerPopup;
import com.visenti.test.automation.web.pageobjects.dmatool.HomePage;
import com.visenti.test.automation.web.pageobjects.dmatool.RightPanelPage;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.Reporter;

import static org.testng.AssertJUnit.assertTrue;

public class PUBHomeStepDefnitions {
    private WebDriver driver;
    HomePage homePage;
    RightPanelPage rightPanelPage;

    public PUBHomeStepDefnitions() {
        driver= DriverManager.getDriver();
        homePage = new HomePage(driver);
    }

    @When("^I search the pub search panel with \"([^\"]*)\"$")
    public void searchStationFromSearchBar(String keyword) throws Exception {
        homePage.searchAndSelectStation(keyword);
        Log.info("Clicked on Themes panel side menu button");
        Reporter.log("Clicked on Themes panel side menu button");
    }

    @When("^I click on the \"([^\"]*)\" from the search$")
    public void clickOnStationOrGisImage(String image) throws FindFailed, InterruptedException {
        SikuliHelper sikuliHelper = new SikuliHelper();
        sikuliHelper.clickOnTheImage(image);
    }

    @When("^Verify the details of the station or gis or zone with \"([^\"]*)\"$")
    public void verifyRightPanelDetailsForStation(String keyword) {
        rightPanelPage = new RightPanelPage(driver);
        String text = rightPanelPage.getStationOrGISTitle(ConfigFileReader.getConfigProperty(keyword));
        assertTrue(text.contains(ConfigFileReader.getConfigProperty(keyword)));
    }

    @When("^I select \"([^\"]*)\" date from the global date picker$")
    public void selectStartAndEndDate(String date) {
        homePage.clickOnTheApp("Date Range");
        Log.info("Successfully clicked on global date picker button");
        DatePickerPopup datePickerPopup = new DatePickerPopup(driver);
        datePickerPopup.enterDateRange(date);
    }

    @When("^I click on \"([^\"]*)\" button in the dma home page$")
    public void clickOnApplications(String app) {
        homePage.clickOnTheApp(app);
        Log.info("Successfully clicked on "+app +" button");
        if(app.equalsIgnoreCase("aims")){
            homePage.clickOnAimsSensorsButton();
        }
    }

    @When("^I Verify all the sensors are selected by default$")
    public void verifyAimsSensorsAreSelectedByDefault() {
        homePage.clickOnAimsSensorsButton();
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);
        Assert.assertTrue(aimsListViewPage.isAllAimsSensorsChecked(),
                    "One or more sensors are not selected by default");
        Log.info("All the sensors are selected by default");
    }

    @When("^I click on Simulation button in the dma home page$")
    public void clickOnSimulationApp() {
        homePage.clickOnSimulationButton();
    }

}
