package com.visenti.test.automation.web.pageobjects.dmatool;

import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AimsListViewPage {
    private WebDriver driver;

    public AimsListViewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[text()='Incidents']")
    private WebElement aimsRightPanelTitle;

    @FindBy(xpath = "//span[text()='Live']")
    private WebElement liveButton;

    @FindBy(xpath = "//input[@placeHolder='Search...']")
    private WebElement aimsSearchBox;

    @FindBy(xpath = "//div[text()='UnAttended' and @role='tab']")
    private WebElement unAttendedTab;

    @FindBy(xpath = "//div[text()='Flagged' and @role='tab']")
    private WebElement flaggedTab;

    @FindBy(xpath = "//div[text()='MyIncidents' and @role='tab']")
    private WebElement myIncidentsTab;

    @FindBy(xpath = "//div[text()='Overview' and @role='tab']")
    private WebElement overviewTab;

    @FindBy(xpath = "//span[text()='Type' and @class='ant-table-column-title']")
    private WebElement columnType;

    @FindBy(xpath = "//span[text()='Type' and @class='ant-table-column-title']/ancestor::span/following-sibling::i")
    private WebElement typeFilter;

    @FindBy(xpath = "//span[text()='Type']/following-sibling::span/div/i[contains(@aria-label,'caret-up')]")
    private WebElement typeUpSort;

    @FindBy(xpath = "//span[text()='Type']/following-sibling::span/div/i[contains(@aria-label,'caret-down')]")
    private WebElement typeDownSort;

    @FindBy(xpath = "//span[text()='Station' and @class='ant-table-column-title']")
    private WebElement columnStation;

    @FindBy(xpath = "//span[text()='Station' and @class='ant-table-column-title']/ancestor::span/following-sibling::i")
    private WebElement stationFilter;

    @FindBy(xpath = "//span[text()='Station']/following-sibling::span/div/i[contains(@aria-label,'caret-up')]")
    private WebElement stationUpSort;

    @FindBy(xpath = "//span[text()='Station']/following-sibling::span/div/i[contains(@aria-label,'caret-down')]")
    private WebElement stationDownSort;

    @FindBy(xpath = "//span[text()='Rank' and @class='ant-table-column-title']")
    private WebElement columnRank;

    @FindBy(xpath = "//span[text()='Rank' and @class='ant-table-column-title']/ancestor::span/following-sibling::i")
    private WebElement rankFilter;

    @FindBy(xpath = "//span[text()='Rank']/following-sibling::span/div/i[contains(@aria-label,'caret-up')]")
    private WebElement rankUpSort;

    @FindBy(xpath = "//span[text()='Rank']/following-sibling::span/div/i[contains(@aria-label,'caret-down')]")
    private WebElement rankDownSort;

    @FindBy(xpath = "//span[text()='Action' and @class='ant-table-column-title']")
    private WebElement columnAction;

    @FindBy(xpath = "//span[text()='Incident' and @class='ant-table-column-title']")
    private WebElement columnIncident;

    @FindBy(xpath = "//span[text()='Incident']/following-sibling::span/div/i[contains(@aria-label,'caret-up')]")
    private WebElement incidentUpSort;

    @FindBy(xpath = "//span[text()='Incident']/following-sibling::span/div/i[contains(@aria-label,'caret-down')]")
    private WebElement incidentDownSort;

    @FindBy(xpath = "//span[text()='Reported' and @class='ant-table-column-title']")
    private WebElement columnReported;

    @FindBy(xpath = "//span[text()='Reported']/following-sibling::span/div/i[contains(@aria-label,'caret-up')]")
    private WebElement reportedUpSort;

    @FindBy(xpath = "//span[text()='Reported']/following-sibling::span/div/i[contains(@aria-label,'caret-down')]")
    private WebElement reportedDownSort;

    @FindBy(xpath = "//p[text()='No Data' and @class='ant-empty-description']")
    private WebElement noData;

    @FindBy(xpath = "//input[@placeholder='Search...']/ancestor::span/following-sibling::button[1]")
    private WebElement removeFilterButton;

    @FindBy(xpath = "//input[@placeholder='Search...']/ancestor::span/following-sibling::button[2]")
    private WebElement downloadButton;

    @FindBy(xpath = "//input[@placeholder='Search...']/ancestor::span/following-sibling::button[3]")
    private WebElement hideIncidentsButton;

    @FindBy(xpath = "//input[@placeholder='Search...']/ancestor::span/following-sibling::button[4]")
    private WebElement settingsButton;

    @FindBy(xpath = "//span[text()='Type' and @class='ant-table-column-title']/ancestor::div/following-sibling::div/table/tbody/tr/td[2]")
    private WebElement incidentType;

    @FindBy(xpath = "//span[text()='Type' and @class='ant-table-column-title']/ancestor::div/following-sibling::div/table/tbody/tr/td[3]/span/div/span")
    private WebElement incidentStation;

    @FindBy(xpath = "//span[text()='Action' and @class='ant-table-column-title']/ancestor::div/following-sibling::div/table/tbody/tr/td[5]/button[1]")
    private WebElement incidentFlaggedButton;

    @FindBy(xpath = "//span[text()='Action' and @class='ant-table-column-title']/ancestor::div/following-sibling::div/table/tbody/tr/td[5]/button[3]")
    private WebElement incidentLocationButton;

    @FindBy(xpath = "//li[@title='Next Page']/preceding-sibling::li[1]/a")
    private WebElement lastPageOfIncidents;

    @FindBy(xpath = "//h2[@class='esri-popup__header-title']")
    private WebElement popUpStationTitle;

    @FindBy(xpath = "//div[@role='dialog' and contains(@class,'esri-popup')]")
    private WebElement incidentPopUp;

    @FindBy(xpath = "//table[@class='esri-widget__table']/tbody/tr[1]/td")
    private WebElement incidentPopUpAssignedTo;

    @FindBy(xpath = "//table[@class='esri-widget__table']/tbody/tr[5]/td")
    private WebElement incidentPopUpReportingTime;

    @FindBy(xpath = "//table[@class='esri-widget__table']/tbody/tr[8]/td")
    private WebElement incidentPopUpStationName;

    public boolean isLiveMode(){
        String color = liveButton.getCssValue("background-color");
        return !color.equalsIgnoreCase("lightgrey");
    }

    public void toggleLiveMode(){
        SeleniumUtils.performAJavaScriptClick(driver,liveButton);
    }

    public boolean isAllAIMSTabPresent(){
        boolean present;
        if(SeleniumUtils.isElementDisplayed(overviewTab)){
            Log.info("Overview tab is present");
            if (SeleniumUtils.isElementDisplayed(myIncidentsTab)){
                Log.info("My Incidents tab is present");
                if(SeleniumUtils.isElementDisplayed(unAttendedTab)){
                    Log.info("Unattended tab is present");
                    if(SeleniumUtils.isElementDisplayed(flaggedTab)){
                        present = true;
                        Log.info("Flagged tab is present");
                    }else{
                        present= false;
                        Log.info("Flagged tab is not present");
                    }
                }else{
                    present= false;
                    Log.info("Unattended tab is not present");
                }
            }else{
                present= false;
                Log.info("My Incidents tab is not present");
            }
        }else{
            present= false;
            Log.info("Overview tab is not present");
        }

        return present;
    }

    public boolean isAllTheColumnsOfAIMSListViewAvailable(){
        boolean available;
        if(SeleniumUtils.isElementDisplayed(columnType) && SeleniumUtils.isElementDisplayed(columnStation)
            && SeleniumUtils.isElementDisplayed(columnRank) && SeleniumUtils.isElementDisplayed(columnAction)
                && SeleniumUtils.isElementDisplayed(columnIncident) && SeleniumUtils.isElementDisplayed(columnReported)){
            Log.info("All AIMS columns are present on click of AIMS");
            available = true;
        }else{
            available= false;
            Log.info("Some of the columns are not present on click of AIMS");
        }
        return available;
    }

    public boolean isAllControlButtonPresent(){
        boolean available;
        if(SeleniumUtils.isElementDisplayed(removeFilterButton) && SeleniumUtils.isElementDisplayed(downloadButton)
                && SeleniumUtils.isElementDisplayed(hideIncidentsButton) && SeleniumUtils.isElementDisplayed(settingsButton)){
            Log.info("All AIMS columns are present on click of AIMS");
            available = true;
        }else{
            available= false;
            Log.info("Some of the columns are not present on click of AIMS");
        }
        return available;
    }

    public String getAimsRightPanelTitle(){
        return aimsRightPanelTitle.getText();
    }

    public boolean isSearchTextBoxPresent(){
        return SeleniumUtils.isElementDisplayed(aimsSearchBox);
    }

    public boolean isNoDataForTimeRange(){
        return SeleniumUtils.isElementDisplayed(noData);
    }

    public boolean isAllAimsSensorsChecked(){
        boolean allChecked = false;
        List<WebElement> sensorChecked = driver.findElements(By.xpath("//span[text()='AIMS']/ancestor::div[contains(@class,'filter_popover')]//tr//label"));
        int size = sensorChecked.size();
        System.out.println("Size :" + size);
        for (WebElement webElement : sensorChecked) {
            String classValue = webElement.getAttribute("class");
            if (classValue.contains("checked")) {
                allChecked = true;
            } else {
                Log.info("One or more sensors are not selected by default");
                allChecked = false;
                break;
            }
        }
        return allChecked;
    }

    public boolean verifyAimsTabSelected (String tab){
        WebElement selectedElement = null;
        switch(tab.toLowerCase()){
            case "unattended":
                selectedElement = unAttendedTab;
                break;
            case "overview" :
                selectedElement = overviewTab;
                break;
            case "flagged" :
                selectedElement = flaggedTab;
                break;
            case "my incidents":
                selectedElement = myIncidentsTab;
                break;
            default:
                break;
        }

        String attribute = selectedElement.getAttribute("class");
        boolean selected = false;
        if(attribute.contains("ant-tabs-tab-active")){
            selected = true;
            Log.info(tab + " is the selected tab");
        }
        return selected;
    }

    public boolean isIncidentsPresent(){
        SeleniumUtils.waitUntilElementVisible(incidentType,driver,20L);
        return SeleniumUtils.isElementDisplayed(incidentType);
    }

    public void clickOnAnIncident(){
        SeleniumUtils.waitUntilElementVisible(incidentType,driver,20L);
        SeleniumUtils.performAJavaScriptClick(driver,incidentType);
    }

    public String getTextOfTheSearch(){
        SeleniumUtils.waitUntilElementVisible(incidentType,driver,20L);
        return incidentType.getText().toLowerCase();
    }

    public void searchAValueInAims(String search){
        SeleniumUtils.enterTextToAField(aimsSearchBox,search);
        SeleniumUtils.waitUntilElementClickable(incidentType,driver,15L);
    }

    public void clickOnLastPageOfIncidents(){
        SeleniumUtils.performAJavaScriptClick(driver,lastPageOfIncidents);
        Log.info("Clicked on the last page of the aims list view");
    }

    public void flagAnIncident(){
        SeleniumUtils.waitUntilElementVisible(incidentFlaggedButton,driver,20L);
        SeleniumUtils.performAJavaScriptClick(driver,incidentFlaggedButton);

        Log.info("Clicked on the incident flagged button");
    }

    public String getIncidentStationName() {
        Log.info("Incident station name is :" +incidentStation.getText());
        return incidentStation.getText();
    }

    public boolean clickAndVerifyFlaggedIncident(String tab){
        String flaggedIncidentStation = null;
        SeleniumUtils.waitUntilElementVisible(incidentStation,driver,20L);
        tab = getIncidentStationName();
        if(incidentFlaggedButton.getAttribute("style").contains("grey")){
            flagAnIncident();
        }
        clickOnAimsTab("flagged");
        SeleniumUtils.waitUntilElementVisible(incidentStation,driver,5L);
        flaggedIncidentStation = getIncidentStationName();

        return tab.equalsIgnoreCase(flaggedIncidentStation);
    }

    public void clickOnAimsTab(String tab) {
        WebElement aimTab = null;
        switch(tab.toLowerCase()){
            case "unattended":
                aimTab = unAttendedTab;
                break;
            case "overview" :
                aimTab = overviewTab;
                break;
            case "flagged" :
                aimTab = flaggedTab;
                break;
            case "my incidents":
                aimTab = myIncidentsTab;
                break;
            default:
                break;
        }
        SeleniumUtils.performAJavaScriptClick(driver,aimTab);
        Log.info("Clicked on " + tab +" tab");
    }

    public void clickOnIncidentLocationButton(){
        SeleniumUtils.waitUntilElementClickable(incidentLocationButton, driver,5L);
        SeleniumUtils.performAJavaScriptClick(driver,incidentLocationButton);
    }

    public String getStationInThePopUp() {
        String stationText = SeleniumUtils.getTextOfAWebElement(popUpStationTitle);
        String [] listOfStations = stationText.split("List Of Stations:");

        return listOfStations[1];
    }

    public boolean isIncidentPopUpPresent() {
        return SeleniumUtils.isElementDisplayed(incidentPopUp);
    }

    /*public boolean verifyPopUpIncidentDetails() {
        boolean verified = false;

    }*/

}
