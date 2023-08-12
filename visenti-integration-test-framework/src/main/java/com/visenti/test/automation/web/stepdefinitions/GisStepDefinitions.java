package com.visenti.test.automation.web.stepdefinitions;

import com.visenti.test.automation.helpers.DriverManager;
import com.visenti.test.automation.web.pageobjects.dmatool.GisPopUpDetails;
import com.visenti.test.automation.web.pageobjects.dmatool.HomePage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class GisStepDefinitions {

    private final WebDriver driver;
    HomePage homePage;
    GisPopUpDetails gis;

    public GisStepDefinitions() {
        driver = DriverManager.getDriver();
        homePage = new HomePage(driver);
    }

    @Then("^I verify row header details available under GIS tab$")
    public void verifyRowHeaderDetailsAvailableUnderGISTab() {
        gis = new GisPopUpDetails(driver);
        gis.isRowHeaderDetailsAvailableUnderGisTab();
    }

    @Then("^I verify all tabs available under GIS button")
    public void verifyAllTabsPresents() {
        gis = new GisPopUpDetails(driver);
        gis.isAllGISTabsPresent();
    }

    @And("^I verify GIS tab selected by default under GIS tab$")
    public void verifyGISTabSelectedByDefault() {
        gis = new GisPopUpDetails(driver);
        gis.isGISTabSelectedByDefault();
    }

    @And("^I verify each customer have the applicable list of GIS layers$")
    public void verifyGiSLayersIsFromTheSpecifiedCustomerList() {
        gis = new GisPopUpDetails(driver);
        gis.isGISTabLayerListAvailable();
    }

    @Then("^I verify GiS layers name sorted in Ascending order by default$")
    public void verifyGiSLayersNameSortedInAscendingOrderByDefault() {
        gis = new GisPopUpDetails(driver);
        gis.isGisLayersSortedInAscendingOrderByDefault();
    }

    @And("^I verify GIS layers name sorted in Descending order on click of down sort button$")
    public void verifyGISLayersNameSortedInDescendingOrderOnClickOfDownSortButton() {
        gis = new GisPopUpDetails(driver);
        gis.isGisLayersSortedInDescendingOrderOnClickOfDownSortButton();
    }

    @Then("^I verify all GIS layers toggle button present under GIS tab$")
    public void verifyAllGISLayersToggleButtonUnderGISTab() {
        gis = new GisPopUpDetails(driver);
        gis.isAllGisLayersToggleButtonPresentUnderGISTab();
    }

    @And("^I verify \"([^\"]*)\" having heat map button under GIS tab$")
    public void verifyLayerHavingHeatMapButtonUnderGISTab(String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.isLayersHavingHeatMapButtonUnderGISTab(layerName);
    }

    @Then("^I verify \"([^\"]*)\" having expand row button under GIS tab$")
    public void verifyExpandRowButtonAvailableForGisLayer(String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.isLayerHavingExpandRowButtonUnderGISTab(layerName);
    }

    @And("^I click on SubZones tab under GIS button$")
    public void clickOnSubZonesButtonUnderGISTab() {
        gis = new GisPopUpDetails(driver);
        gis.clickOnSubZonesTab();

    }

    @Then("^I verify all sub zones tab layers available$")
    public void verifyAllSubZonesTabLayersAvailableUnderGISButton() {
        gis = new GisPopUpDetails(driver);
        gis.isAllSubZonesTabLayersAvailable();
    }

    @Then("^I verify all sub zones layers toggle button available$")
    public void verifyAllSubZonesLayersToggleButtonAvailable() {
        gis = new GisPopUpDetails(driver);
        gis.isAllSubZonesTabLayersToggleButtonAvailable();
    }

    @Then("^I verify all sub zones layers expand row button available$")
    public void verifyAllSubZonesLayersExpandRowButtonAvailable() {
        gis = new GisPopUpDetails(driver);
        gis.isAllSubZonesTabLayersExpandRowButtonAvailable();
    }

    @Then("^I verify download button link available for sub zones tab layer -WQ 10MLD Subzones$")
    public void verifySubZonesLayersHavingDownloadButtonLink() {
        gis = new GisPopUpDetails(driver);
        gis.isDownLoadButtonLinkAvailableForSubZonesLayer();
    }

    @And("^I verify file downloaded on click of WQ 10MLD Subzones download button link$")
    public void verifyFileDownloadedOnClickOfWQMLDSubzonesDownloadButtonLink() throws IOException {
        gis = new GisPopUpDetails(driver);
        gis.isFileDownloadedOnClickOfDownloadButtonLink();
    }

    @And("^I verify tool tip available for subzones layer -WQ 10MLD Subzones download button link$")
    public void verifyToolTipForSubzonesLayerWQMLDSubzonesDownloadButtonLink() {
        gis = new GisPopUpDetails(driver);
        gis.isToolTipAvailableForSubzonesLayerWQMLDSubzones();
    }

    @And("^I verify all GIS tab layers toggle buttons enabled on click of name checkbox & ok button from pop up$")
    public void clickOnNameCheckBoxForEnablingAllGISLayers() {
        gis = new GisPopUpDetails(driver);
        gis.clickOnNameCheckBoxAndOkButtonForEnablingAllGisLayers();
    }


    @Then("^I click on name checkbox under GIS tab$")
    public void clickOnNameCheckboxUnderGISTab() {
        gis = new GisPopUpDetails(driver);
        gis.clickOnNameCheckBoxUnderGisTab();
    }

    @And("^I verify popup pop up displayed with it's details$")
    public void verifyPopupPopUpDisplayedWithItSDetails() {
        gis = new GisPopUpDetails(driver);
        gis.verifyPopUpDisplayed();
    }

    @Then("^I verify GIS app tray details on enabled all GIS tab layers$")
    public void verifyAllGISLayersToggleButtonsEnabled() {
        gis = new GisPopUpDetails(driver);
        gis.isGisAppTrayDetailsAvailable();
    }

    @And("^I verify GIS layers Legends available once enabled all GIS tab layers$")
    public void verifyGISLayersLegendsAndThereIconsAvailableOnceEnabledAllGISTabLayers() {
        gis = new GisPopUpDetails(driver);
        gis.isGisLegendsDetailsAvailable();
    }

    @When("^I click on \"([^\"]*)\" expand row button under GIS tab$")
    public void clickOnLayerExpandRowButtonUnderGISTab(String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.clickOnLayerExpandRowButtonUnderGISTab(layerName);
    }

    @Then("^I verify \"([^\"]*)\" filter details available under GIS tab$")
    public void verifyPipeFilterDetailsAvailableUnderGISTab(String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.isLayerFilterDetailsAvailableUnderGISTab(layerName);
    }

    @Then("^I click on toggle button for the \"([^\"]*)\"$")
    public void clickOnToggleButtonForTheLayer(String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.clickOnToggleButtonForLayer(layerName);
    }

    @And("^I click on \"([^\"]*)\" button if pop up is displayed$")
    public void clickOnTheButtonFromThePopUp(String buttonName) {
        gis = new GisPopUpDetails(driver);
        gis.clickOnPopUpButton(buttonName);
    }

    @Then("^I verify the toggle button enabled for the GIS \"([^\"]*)\"$")
    public void verifyTheToggleButtonEnabledForTheGISLayer(String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.isGisLayerEnabled(layerName);
    }

    @And("^I verify GIS app tray details for enabled GIS \"([^\"]*)\"$")
    public void verifyGISAppTrayDetailsForEnabledGISLayer(String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.isGisAppTrayDisplayedEnabledGisLayer(layerName);
    }

    @And("^I verify GIS legend details for enabled GIS \"([^\"]*)\"$")
    public void verifyGISLegendDetailsForEnabledGISLayer(String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.isGisLegendHavingGisLayerEnabled(layerName);
    }

    @And("^I verify the legend flow direction fuctionality$")
    public void verifyTheLegendFlowDirectionFuctionality() {
        gis = new GisPopUpDetails(driver);
        gis.verifyLegendFlowDirectionFunctionality();
    }

    @And("^I verify \"([^\"]*)\" add new filter functionality$")
    public void verifyAddNewFilterFunctionality(String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.verifyAddNewFilterFuctionality(layerName);
    }

    @And("^I verify \"([^\"]*)\" delete filter functionality$")
    public void verifyDeleteFilterFunctionality(String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.isVerifyDeleteFilterFuctionality(layerName);
    }

    @And("^I click and select \"([^\"]*)\" \"([^\"]*)\" from drop down for the layer \"([^\"]*)\" And click on apply button$")
    public void clickAndSelectTheFilterTypeAndValueTypeOptionFromDropDownForTheLayerAndClickOnApplyBtn(String filterType,String valueType, String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.isClickAndSelectFilterTypeAndValueTypeOptionFromDropDownAndClickOnApplyBtn(filterType,valueType, layerName);
    }

    @And("^I click on \"([^\"]*)\" button from the filter pop up for \"([^\"]*)\" and \"([^\"]*)\"$")
    public void clickOnButtonFromTheFilterPopUpForLayer(String buttonName,String filterType,String valueType) {
        gis = new GisPopUpDetails(driver);
        gis.isClickOnFilterPopUpButtonForFilterTypeAndValueType(buttonName,filterType,valueType);
    }

    @And("^I verify GIS app tray details contains \"([^\"]*)\" and \"([^\"]*)\" for layer \"([^\"]*)\"$")
    public void verifyGISAppTrayDetailsContainsFilterTypeAndValueTypeForLayerValve(String filterType, String valueType, String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.isGisAppTrayContainsValveFilterDetails(filterType, valueType, layerName);
    }

    @And("^I verify GIS legend details contains \"([^\"]*)\" for layer \"([^\"]*)\" filters$")
    public void verifyGISLegendDetailsContainsAndForLayerFilters(String filterType, String layerName) {
        gis = new GisPopUpDetails(driver);
        gis.isGisLegendContainsValveFilterDetails(filterType, layerName);
    }


}
