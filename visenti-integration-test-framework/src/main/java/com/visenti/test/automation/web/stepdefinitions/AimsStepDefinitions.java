package com.visenti.test.automation.web.stepdefinitions;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.DriverManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.SikuliHelper;
import com.visenti.test.automation.utils.CommonUtils;
import com.visenti.test.automation.utils.SeleniumUtils;
import com.visenti.test.automation.web.pageobjects.dmatool.*;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import org.testng.Assert;

public class AimsStepDefinitions {
    private WebDriver driver;

    public AimsStepDefinitions() {
        driver= DriverManager.getDriver();
    }

    @When("^I also verify aims right panel list view details$")
    public void verifyAimsListViewDetails() {
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);

        Reporter.addStepLog("Verify the right panel page is titled as Incidents");
        Assert.assertTrue(aimsListViewPage.getAimsRightPanelTitle().equals("Incidents"),
                        "The page title is not Incidents");
        Reporter.addStepLog("Verify search box is present");
        Assert.assertTrue(aimsListViewPage.isSearchTextBoxPresent(),
                        "Search box is not present");
        Reporter.addStepLog("Verify all the 4 buttons are available by default");
        Assert.assertTrue(aimsListViewPage.isAllControlButtonPresent(),
                        "One of the 4 buttons are not available by default");
        Reporter.addStepLog("Verify Different tabs of AIMS list view are present");
        Assert.assertTrue(aimsListViewPage.isAllAIMSTabPresent(),
                        "Some of the Aims tab are not present");
        Reporter.addStepLog("Verify Different Columns of AIMS list view are present");
        Assert.assertTrue(aimsListViewPage.isAllTheColumnsOfAIMSListViewAvailable(),
                        "Some of the columns in AIMS list view are not available");
    }

    @When("^I verify there is no data available for selected date range$")
    public void verifyNoData() throws Exception {
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);
        Assert.assertTrue(aimsListViewPage.isNoDataForTimeRange(),
                    "Data is available for selected date range");
        Log.info("Verified specified date range doesn't have any data");
    }

    @When("^I verify that live mode is \"([^\"]*)\"$")
    public void verifyLiveModeOff(String status) throws Exception {
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);
        Assert.assertTrue(aimsListViewPage.isLiveMode());
        Log.info("Verified live mode is "+ status);
    }

    @When("^I toggle the live mode$")
    public void toggleLiveMode() throws Exception {
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);
        aimsListViewPage.toggleLiveMode();
    }

    @When("^I verify \"([^\"]*)\" tab is selected and have incidents$")
    public void verifyTabAndIncidents(String tab) {
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);
        Assert.assertTrue(aimsListViewPage.verifyAimsTabSelected(tab),
                                tab + " is not the selected tab");
        Assert.assertTrue(aimsListViewPage.isIncidentsPresent(),
                                "No incidents are present");
    }

    @When("^I click on \"([^\"]*)\" tab$")
    public void clickOnTheTab(String tab) {
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);
        aimsListViewPage.clickOnAimsTab(tab);
    }

    @When("^I search the incident with \"([^\"]*)\"$")
    public void aimsSearch(String incidentType) {
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);
        aimsListViewPage.searchAValueInAims(incidentType);
    }

    @When("^I verify the \"([^\"]*)\" is present in first and last page of the list$")
    public void verifySearchIsPresent(String incidentType) {
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);
        Assert.assertTrue(aimsListViewPage.getTextOfTheSearch().contains(incidentType),
                                "Search doesn't match with the search result");
        aimsListViewPage.clickOnLastPageOfIncidents();
        Assert.assertTrue(aimsListViewPage.getTextOfTheSearch().contains(incidentType),
                "Search doesn't match with the search result");
    }

    @When("^I click on the incident and verify the incident details$")
    public void clickAndVerifyIncidentDetails() {
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);
        aimsListViewPage.clickOnAnIncident();
        AimsDetailsViewPage aimsDetailsViewPage = new AimsDetailsViewPage(driver);
        Assert.assertTrue(aimsDetailsViewPage.verifyElementsOfAimsDetailsPage(),
                            "Incident details page is missing some fields");
        /*Assert.assertTrue(aimsDetailsViewPage.verifyHeaderAndDetailsMatch(),
                            "Incident Header and details doesn't match");*/
    }

    @When("^I click on the location button$")
    public void clickOnLocationButton() {
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);
        aimsListViewPage.clickOnIncidentLocationButton();
    }

    @When("^I verify the \"([^\"]*)\" incident details by clicking the marker on the map$")
    public void verifyIncidentDetailsInMap(String incident) throws FindFailed {
        SikuliHelper sikuliHelper = new SikuliHelper();
        CommonUtils.wait(2);
        sikuliHelper.clickOnTheImage(incident);
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);
        Assert.assertTrue(aimsListViewPage.isIncidentPopUpPresent(),"Incident pop up is not present");
        Assert.assertTrue(aimsListViewPage.getIncidentStationName().contains(aimsListViewPage.getStationInThePopUp().trim()),
                "Station details doesn't match with list view and in the pop up");
    }

    @When("^I flag the \"([^\"]*)\" incident and verify the incident is flagged in flagged tab$")
    public void validateAnIncidentIsFlagged(String tab) {
        AimsListViewPage aimsListViewPage = new AimsListViewPage(driver);
        Assert.assertTrue(aimsListViewPage.clickAndVerifyFlaggedIncident(tab),
                "Flagged stations are different");
        Log.info("Incident got flagged and verified");
    }

}
