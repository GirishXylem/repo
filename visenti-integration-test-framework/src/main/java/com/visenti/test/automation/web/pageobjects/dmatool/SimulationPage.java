package com.visenti.test.automation.web.pageobjects.dmatool;

import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.SikuliHelper;
import com.visenti.test.automation.utils.CommonUtils;
import com.visenti.test.automation.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SimulationPage {
    private WebDriver driver;

    public SimulationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div/h6[text()='Simulations']")
    private WebElement simulationHeader;

    @FindBy(xpath = "//input[@placeholder='Search simulations']")
    private WebElement searchSimulationsTextBox;

    @FindBy(xpath = "//label/*/p[text()='Show all']")
    private WebElement showAllRadioButton;

    @FindBy(xpath = "//label/*/p[text()='Completed']")
    private WebElement completedRadioButton;

    @FindBy(xpath = "//label/*/p[text()='In Progress']")
    private WebElement inProgressRadioButton;

    @FindBy(xpath = "//button/span[text()='Create']")
    private WebElement simulationCreateButton;

    @FindBy(xpath = "//button/span[text()='Create one now']")
    private WebElement createLink;

    @FindBy(xpath = "//div[text()='You have not ran a simulation yet.']")
    private WebElement noSimulationResultsText;

    @FindBy(xpath = "//div[@id='rcDialogTitle0' and text()='Create Simulation']")
    private WebElement createSimulationTitlePopUp;

    @FindBy(xpath = "//input[@placeholder='Enter simulation name']")
    private WebElement simulationNameTextBox;

    @FindBy(xpath = "//button/span[text()='Cancel']")
    private WebElement simulationCancelDialogButton;

    @FindBy(xpath = "//h6[text()='Select a scenario to start']")
    private WebElement scenarioSelectionTitle;

    @FindBy(xpath = "//h6[text()='CREATE SIMULATION']")
    private WebElement createSimulationTitle;

    @FindBy(xpath = "//button/span[text()='Exit']")
    private WebElement exitButton;

    @FindBy(xpath = "//div[@class='ant-select-selection__placeholder' and text()='Select Scenario']")
    private WebElement selectScenarioDropdown;

    @FindBy(xpath = "//button/span[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//span/p[text()='Check Discoloration']")
    private WebElement checkDiscoloration;

    @FindBy(xpath = "//span/p[text()='Check Water Age']")
    private WebElement checkWaterAge;

    @FindBy(xpath = "//span/p[text()='Check Multi-Zone']")
    private WebElement checkMultiZone;

    @FindBy(xpath = "//span[@class='ant-alert-message' and text()='No Multi zone impact identified']")
    private WebElement multiZoneAlertMessage;

    @FindBy(xpath = "//span[@class='ant-calendar-picker']")
    private WebElement calendarPicker;

    @FindBy(xpath = "//input[@placeholder='Start Date & Time']")
    private WebElement flushingCalendarPicker;

    @FindBy(xpath = "//input[@placeholder='Opening (in %)']")
    private WebElement flushingOpeningPercentTextBox;


    @FindBy(xpath = "//input[@placeholder='Start Time' and @class='ant-calendar-range-picker-input']")
    private WebElement simulationStartTime;

    @FindBy(xpath = "//input[@placeholder='Start Time' and @class='ant-calendar-input ']")
    private WebElement calendarStartTime;

    @FindBy(xpath = "//input[@placeholder='End Time' and @class='ant-calendar-range-picker-input']")
    private WebElement simulationEndTime;
    @FindBy(xpath = "//input[@placeholder='End Time' and @class='ant-calendar-input ']")
    private WebElement calendarEndTime;

    @FindBy(xpath = "//a[@class='ant-calendar-ok-btn' and text()='Ok']")
    private WebElement calendarOkButton;

    @FindBy(xpath = "//div[@class='ant-select-selection__placeholder' and text()='Enter valve action']")
    private WebElement valveActionDropdown;

    @FindBy(xpath = "//div[@class='ant-select-selection__placeholder' and text()='Duration (hr)']")
    private WebElement durationPerHourDropdown;
    @FindBy(xpath = "//span[text()='Cancel']/parent::button/following-sibling::button/span[text()='Create']")
    private WebElement simulationCreateDialogButton;

    @FindBy(xpath = "//button/span[text()='Back']")
    private WebElement backButton;
    @FindBy(xpath = "//button/span[text()='Next']")
    private WebElement nextButton;
    @FindBy(xpath = "//button/span[text()='Run Simulation']")
    private WebElement runSimulationButton;

    @FindBy(xpath = "//input[@placeholder = 'Type to search']")
    private WebElement searchTextBox;
    @FindBy(xpath = "//input[@placeholder = 'Type to search']/following-sibling::button")
    private WebElement searchType;

    @FindBy(xpath = "//h6[text()='Select at least one valve from the map to build the Valve Operation']")
    private WebElement valveScenarioHeaderMessage;

    @FindBy(xpath = "//h6[text()='Select at least one Junction or Hydrant from the map to help identify the flushing']")
    private WebElement flushingScenarioHeaderMessage;

    @FindBy(xpath = "//h6[text()='Select at least one junction from the map']")
    private WebElement nwdOrSHeaderMessage;

    @FindBy(xpath = "//h6[text()='Select at least one Pipe from the map to build the Pipe Isolation']")
    private WebElement pipeIsolationHeaderMessage;

    @FindBy(xpath = "//h6[text()='(Optional) Add one or more Inoperable Valves from the map to build the Pipe Isolation']")
    private WebElement pipeIsolationSecondHeaderMessage;

    @FindBy(xpath = "//h6[text()='Select a hydrant or a junction from the map']")
    private WebElement fireFlowHeaderMessage;

    @FindBy(xpath = "//span/p[text()='New Water Demand']")
    private WebElement newWaterDemandRadioButton;

    @FindBy(xpath = "//span/p[text()='New Water Source']")
    private WebElement newWaterSourceRadioButton;

    @FindBy(xpath = "//p[text()='Volume of Water']/parent::div/following-sibling::div/descendant::input[@class='ant-input-number-input']")
    private WebElement volumeOfWaterTextBox;

    @FindBy(xpath = "//input[@type='number' and @placeholder='Minimum Acceptable Pressure (m)']")
    private WebElement fireFlowMinAcceptablePressure;

    @FindBy(xpath = "//input[@role='spinbutton' and @placeholder='Enter Flow (l/s)']")
    private WebElement fireFlowMaxFlowValueTextBox;

    @FindBy(xpath = "//input[@type='text' and @placeholder='Start Time']")
    private WebElement fireFlowStartTime;

    @FindBy(xpath = "//span/p[text()='Find maximum duration for following flow']")
    private WebElement fireFlowMaximumDurationRadioButton;

    @FindBy(xpath = "//span/p[text()='Find maximum flow for following duration']")
    private WebElement fireFlowMaximumFlowRadioButton;

    @FindBy(xpath = "//button[@title='Duplicate']")
    private WebElement duplicateButton;

    @FindBy(xpath = "//button[@title='Edit']")
    private WebElement editButton;

    @FindBy(xpath = "//button[@title='Recenter']")
    private WebElement recenterButton;

    @FindBy(xpath = "//button[@title='Locator']")
    private WebElement locatorButton;

    @FindBy(xpath = "//button[@title='Info']")
    private WebElement infoButton;

    @FindBy(xpath = "//button[@title='Download']")
    private WebElement downloadButton;

    @FindBy(xpath = "//button[@title='GIS']")
    private WebElement gisButton;

    @FindBy(xpath = "//button[@title='Zone Boundaries']")
    private WebElement zoneBoundariesButton;

    @FindBy(xpath = "//button[@title='Devices']")
    private WebElement devicesButton;

    @FindBy(xpath = "//button[@title='Other Sources']")
    private WebElement otherSourcesButton;

    @FindBy(xpath = "//div[@id='scrubber-component']")
    private WebElement scrubberComponent;

    @FindBy(xpath = "//div/span[@class,'ant-spin-dot ant-spin-dot-spin']")
    private WebElement spinner;

    @FindBy(xpath = "//h6[text()='SIMULATION RESULTS']")
    private WebElement simulationResultsTitle;

    @FindBy(xpath = "//div[@role='tab' and text()='Overview']")
    private WebElement overviewTab;

    @FindBy(xpath = "//div[@role='tab' and text()='Actions']")
    private WebElement actionsTab;

    @FindBy(xpath = "//div[@role='tab' and text()='Mitigations']")
    private WebElement mitigationsTab;

    @FindBy(xpath = "//div[@role='tab' and text()='Scenario']")
    private WebElement scenarioTab;
    public void verifyRightPanelTitle(String expectedTitle){
        SeleniumUtils.waitUntilElementVisible(simulationHeader,driver,1l);
        String header = SeleniumUtils.getTextOfAWebElement(simulationHeader);
        Assert.assertEquals(header,expectedTitle,
                "The expected title of the right panel is not simulations and is "+header);
    }

    public void isSimulationSearchBarPresent(){
        boolean elementPresent = SeleniumUtils.isElementDisplayed(searchSimulationsTextBox);
        Assert.assertTrue(elementPresent,
                "The search bar to search the result is not available");
    }

    public void isSimulationResultFilterRadioButtonsPresent(){
        List<WebElement> elementsList = new ArrayList<>();

        elementsList.add(completedRadioButton);
        elementsList.add(inProgressRadioButton);
        elementsList.add(showAllRadioButton);

        for(WebElement element : elementsList){
            Assert.assertTrue(SeleniumUtils.isElementDisplayed(element),"" +
                    SeleniumUtils.getTextOfAWebElement(element)+" is not displayed on the right panel");
        }
    }

    public void isSimulationCreateButtonPresent(){
        boolean elementPresent = SeleniumUtils.isElementDisplayed(simulationCreateButton);
        Assert.assertTrue(elementPresent,
                "Create button is not displayed");
    }

    public void clickOnSimulationButtons(String buttonName){
        if(buttonName.equalsIgnoreCase("createButton")){
            SeleniumUtils.waitUntilElementVisible(simulationCreateButton,driver,2L);
            //SeleniumUtils.clickOnAnElement(simulationCreateButton);
            SeleniumUtils.performAJavaScriptClick(driver,simulationCreateButton);
        } else if(buttonName.equalsIgnoreCase("cancelDialogButton")){
            SeleniumUtils.waitUntilElementVisible(simulationCancelDialogButton,driver,1L);
            SeleniumUtils.clickOnAnElement(simulationCancelDialogButton);
        } else if(buttonName.equalsIgnoreCase("createDialogButton")){
            SeleniumUtils.waitUntilElementVisible(simulationCreateDialogButton,driver,1L);
            SeleniumUtils.clickOnAnElement(simulationCreateDialogButton);
        }
    }

    public void verifySimulationPopUpDetails(){
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(createSimulationTitlePopUp),
                "The pop up does not have the title named as create simulation");
        Assert.assertEquals(SeleniumUtils.getTextOfAWebElement(createSimulationTitlePopUp),
                "Create Simulation", "The value of the tile is not create simulation");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(simulationNameTextBox),
                "The simulation name text box is not present");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(simulationCancelDialogButton),
                "The pop up does not have cancel button");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(simulationCreateDialogButton),
                "The pop up does not have create button");
    }

    public void enterTheSimulationNameAndCreate(String scenario){
        String scenarioName = scenario +"-" + CommonUtils.getSystemLocalDateTimeInAGivenFormat("dd/MM/yyyy hh:mm:ss",LocalDateTime.now());
        ConfigFileReader.setConfigProperty("scenario.name",scenarioName);
        SeleniumUtils.waitUntilElementVisible(simulationNameTextBox,driver,1L);
        SeleniumUtils.enterTextToAField(simulationNameTextBox,scenarioName);

        SeleniumUtils.waitUntilElementVisible(simulationCreateDialogButton,driver,1L);
        SeleniumUtils.performAJavaScriptClick(driver,simulationCreateDialogButton);
    }

    public void selectSimulationScenario(String scenario) {
        SeleniumUtils.waitUntilElementVisible(selectScenarioDropdown,driver,1L);
        SeleniumUtils.clickOnAnElement(selectScenarioDropdown);

        CommonUtils.wait(1);

        WebElement dropdown = driver.findElement(
                By.xpath("//li[@label='"+scenario+"' and text()='"+scenario+"']"));
        SeleniumUtils.clickOnAnElement(dropdown);
    }

    public void toggleDiscoloration(){
        SeleniumUtils.waitUntilElementVisible(checkDiscoloration,driver,1L);
        SeleniumUtils.clickOnAnElement(checkDiscoloration);
    }

    public void toggleWaterAge(){
        SeleniumUtils.waitUntilElementVisible(checkWaterAge,driver,1L);
        SeleniumUtils.clickOnAnElement(checkWaterAge);
    }

    public void toggleMultiZone(){
        SeleniumUtils.waitUntilElementVisible(checkWaterAge,driver,1L);
        SeleniumUtils.clickOnAnElement(checkWaterAge);
    }

    public void selectValveActionToBePerformed(String splitValue,String valveAction) {
        WebElement valveActionElement = driver.findElement(
                    By.xpath("//span[text()='"+splitValue+"']/parent::div/following-sibling::div[2]/" +
                            "descendant::div[text()='Enter valve action']"));


        SeleniumUtils.waitUntilElementVisible(valveActionElement,driver,1L);
        SeleniumUtils.clickOnAnElement(valveActionElement);

        CommonUtils.wait(1);

        WebElement dropdown = driver.findElement(By.xpath("//li[@class='ant-select-dropdown-menu-item' and text()='"+valveAction+"']"));
        SeleniumUtils.clickOnAnElement(dropdown);
    }

    public void selectDurationToBePerformed(String durationHour) {
        SeleniumUtils.waitUntilElementVisible(durationPerHourDropdown,driver,1L);
        SeleniumUtils.clickOnAnElement(durationPerHourDropdown);

        CommonUtils.wait(1);

        WebElement dropdown = driver.findElement(By.xpath("//li[@class='ant-select-dropdown-menu-item' and text()='"+durationHour+"']"));
        SeleniumUtils.clickOnAnElement(dropdown);
    }

    public void searchAndSelectGIS(String gisValue) throws Exception {
        if(gisValue.contains(",")){
            String [] value = gisValue.split(",");
            for (String s : value) {
                SeleniumUtils.javaScriptClickAndSelect(driver, searchType, getGISType(s));
                SeleniumUtils.typeAndEnterTheSearch(driver, searchTextBox, s);
                clickOnGISSearched(getGISType(s));
                Log.info("Searched " + s + " of type "+getGISType(s) +" for simulation");
                Log.info("Clicked on the "+ getGISType(s) + "for simulation");
            }
        } else {
            SeleniumUtils.waitUntilElementClickable(searchType,driver,2L);
            SeleniumUtils.javaScriptClickAndSelect(driver,searchType,getGISType(gisValue));
            SeleniumUtils.typeAndEnterTheSearch(driver,searchTextBox,gisValue);
            clickOnGISSearched(getGISType(gisValue));
            Log.info("Searched " + gisValue + " of type "+getGISType(gisValue) +" for simulation");
            Log.info("Clicked on the "+ getGISType(gisValue) + "for simulation");
        }
    }

    public String getGISType(String gisValue){
        String gis = "";
        if(gisValue.startsWith("J")){
            gis = "Junction";
        } else if(gisValue.startsWith("V")){
            gis = "Valve";
        } else if(gisValue.contains("H")){
            gis = "Hydrant";
        } else if (gisValue.startsWith("P")) {
            gis = "Pipe";
        }
        return gis;
    }
    public void clickOnSimulationButton(){
        SeleniumUtils.waitUntilElementClickable(runSimulationButton,driver,1L);
        SeleniumUtils.performAJavaScriptClick(driver,runSimulationButton);
    }

    public void clickOnGISSearched(String gis) throws FindFailed {
        SikuliHelper sikuliHelper = new SikuliHelper();
        sikuliHelper.clickOnTheImage(gis);
    }

    public void verifyTheMandatoryFieldsAfterScenarioSelection(String scenario, String gisSelected){
        WebElement element ;
        String [] gis = gisSelected.split(",");
        for(String s : gis){
            element= driver.findElement(By.xpath("//span[contains(@class,'ant-tag') and text()='"+s+"']"));
            Assert.assertTrue(SeleniumUtils.isElementDisplayed(element),"" +
                    "The selected gis is not displayed");
        }
        if(!(scenario.equals("Flushing")||scenario.equals("Fire Flow")
                ||scenario.equals("Pipe Isolation"))){
            Assert.assertTrue(SeleniumUtils.isElementDisplayed(checkDiscoloration),
                    "Check discoloration is not available for selection");
            Assert.assertTrue(SeleniumUtils.isElementDisplayed(checkWaterAge),
                    "Check water age is not available for selection");
            Assert.assertTrue(SeleniumUtils.isElementDisplayed(checkMultiZone),
                    "Check multi zone option is not available for selection");
            Assert.assertTrue(SeleniumUtils.isElementDisplayed(cancelButton),
                    "Simulation cancel button is not displayed");
        }

        switch (scenario) {
            case "Valve Operation":
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(simulationStartTime),
                        "Simulation start time is not available for selection");
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(simulationEndTime),
                        "Simulation end time is not available for selection");
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(valveActionDropdown),
                        "Valve operation selection dropdown is not available for selection");
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(valveScenarioHeaderMessage),
                        "Valve scenario title message is not displayed");
                break;
            case "Flushing":
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(flushingScenarioHeaderMessage),
                        "Flushing scenario title message is not displayed");
                break;
            case "New Water Demand or Source":
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(simulationStartTime),
                        "Simulation start time is not available for selection");
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(simulationEndTime),
                        "Simulation end time is not available for selection");
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(nwdOrSHeaderMessage),
                        "New water demand and source scenario title message is not displayed");
                break;
            case "Pipe Isolation":
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(pipeIsolationHeaderMessage),
                        "Pipe Isolation scenario title message is not displayed");
                break;
            case "Fire Flow":
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(fireFlowHeaderMessage),
                        "Fire flow scenario title message is not displayed");
                break;
        }
    }

    public void fillTheFieldsForSimulationScenario(String scenario, String gisValue, String action){
        Log.info("Enabling the Discoloration of source");
        if(!(scenario.equals("Pipe Isolation")||scenario.equals("Fire Flow"))){
            toggleDiscoloration();
        }
        String [] gis;

        switch (scenario){
            case "Valve Operation":
                if(gisValue.contains(",")){
                    gis = gisValue.split(",");
                    for(String s : gis){
                        WebElement valveElement = driver.findElement(
                                By.xpath("//span[text()='"+ s +"']/parent::div/" +
                                        "following-sibling::div[2]/" +
                                        "descendant::span[@class='ant-calendar-picker']"));
                        SeleniumUtils.clickOnAnElement(valveElement);
                        CommonUtils.wait(1);
                        Log.info("Enter the simulation start time");
                        setSimulationStartTime();
                        Log.info("Enter the simulation end time");
                        setSimulationEndTime();
                        Log.info("Performing closing of the value for simulation");
                        selectValveActionToBePerformed(s,action);
                    }
                } else {
                    SeleniumUtils.clickOnAnElement(calendarPicker);
                    CommonUtils.wait(1);
                    Log.info("Enter the simulation start time");
                    setSimulationStartTime();
                    Log.info("Enter the simulation end time");
                    setSimulationEndTime();
                    Log.info("Performing closing of the value for simulation");
                    selectValveActionToBePerformed(gisValue,action);
                }
                break;
            case "Flushing":
                SeleniumUtils.clickOnAnElement(flushingCalendarPicker);
                Log.info("Enter the simulation start time");
                setSimulationStartTime();
                Log.info("Select the duration per hour to be performed");
                selectDurationToBePerformed(action);
                Log.info("Enter the Opening % for flushing");
                SeleniumUtils.enterTextToAField(flushingOpeningPercentTextBox,"0.001");
                break;
            case "New Water Demand or Source":
                if(action.equalsIgnoreCase("nwd")){
                    SeleniumUtils.waitUntilElementClickable(newWaterDemandRadioButton,driver,1L);
                    SeleniumUtils.clickOnAnElement(newWaterDemandRadioButton);
                } else {
                    SeleniumUtils.waitUntilElementClickable(newWaterSourceRadioButton,driver,1L);
                    SeleniumUtils.clickOnAnElement(newWaterSourceRadioButton);
                }
                SeleniumUtils.enterTextToAField(volumeOfWaterTextBox,"0.001");
                SeleniumUtils.clickOnAnElement(calendarPicker);
                CommonUtils.wait(1);
                Log.info("Enter the simulation start time");
                setSimulationStartTime();
                Log.info("Enter the simulation end time");
                setSimulationEndTime();
                break;
            case "Pipe Isolation":
                SeleniumUtils.waitUntilElementClickable(nextButton,driver,1L);
                SeleniumUtils.performAJavaScriptClick(driver,nextButton);
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(pipeIsolationSecondHeaderMessage),
                        "Pipe isolation optional title message is not displayed");
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(backButton),
                        "Back button is not available to click");
                SeleniumUtils.waitUntilElementClickable(nextButton,driver,1L);
                SeleniumUtils.performAJavaScriptClick(driver,nextButton);
                toggleDiscoloration();
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(simulationStartTime),
                        "Simulation start time is not available for selection");
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(simulationEndTime),
                        "Simulation end time is not available for selection");
                SeleniumUtils.clickOnAnElement(calendarPicker);
                CommonUtils.wait(1);
                Log.info("Enter the simulation start time");
                setSimulationStartTime();
                Log.info("Enter the simulation end time");
                setSimulationEndTime();
                break;
            case "Fire Flow":
                SeleniumUtils.waitUntilElementClickable(nextButton,driver,1L);
                SeleniumUtils.performAJavaScriptClick(driver,nextButton);
                SeleniumUtils.waitUntilElementClickable(fireFlowMinAcceptablePressure,driver,1L);
                SeleniumUtils.enterTextToAField(fireFlowMinAcceptablePressure,action);
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(fireFlowMaximumDurationRadioButton),
                        "Find maximum duration radio button is not present");
                Assert.assertTrue(SeleniumUtils.isElementDisplayed(fireFlowMaximumFlowRadioButton),
                        "Find maximum flow radio button is not present");
                SeleniumUtils.waitUntilElementClickable(fireFlowMaxFlowValueTextBox,driver,1L);
                SeleniumUtils.enterTextToAField(fireFlowMaxFlowValueTextBox,action);
                SeleniumUtils.enterTextToAField(fireFlowStartTime,"12");
                break;
        }

    }

    public void setSimulationStartTime(){
        String startTime;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, 1);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        int day = now.get(Calendar.DAY_OF_MONTH);

        Date date = now.getTime();
        if(ValueRange.of(1, 9).isValidIntValue(day)){
            startTime = CommonUtils.getSystemDateInAGivenFormat("MMMM d, yyyy" , date);
            System.out.println("Start time -" + startTime);
        } else {
            startTime = CommonUtils.getSystemDateInAGivenFormat("MMMM dd, yyyy" , date);
            System.out.println("Start time -" + startTime);
        }
        CommonUtils.wait(1);
        WebElement element = driver.findElement(
                By.xpath("//td[@role='gridcell' and @title ='"+startTime+"']"));
        SeleniumUtils.clickOnAnElement(element);
    }

    public void setSimulationEndTime(){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, 2);
        String endTime ="";
        int day = now.get(Calendar.DAY_OF_MONTH);
        Date date = now.getTime();
        if(ValueRange.of(1, 9).isValidIntValue(day)){
            endTime = CommonUtils.getSystemDateInAGivenFormat("MMMM d, yyyy" , date);
            System.out.println("End time -" + endTime);
        } else {
            endTime = CommonUtils.getSystemDateInAGivenFormat("MMMM dd, yyyy" , date);
            System.out.println("End time -" + endTime);
        }

        CommonUtils.wait(1);
        WebElement element = driver.findElement(
                By.xpath("//td[@role='gridcell' and @title ='"+endTime+"']"));
        SeleniumUtils.clickOnAnElement(element);
        clickOnCalendarOkButton();
    }

    public void clickOnCalendarOkButton(){
        SeleniumUtils.clickOnAnElement(calendarOkButton);
    }

    public void searchForTheSimulationCreated(){
        SeleniumUtils.waitUntilElementVisible(searchSimulationsTextBox,driver,3L);
        SeleniumUtils.enterTextToAField(searchSimulationsTextBox,
                ConfigFileReader.getConfigProperty("scenario.name"));
    }

    public void clickOnTheSimulationResult(){
        SeleniumUtils.waitUntilElementVisible(duplicateButton,driver,500L);
        SeleniumUtils.waitUntilElementVisible(editButton,driver,500L);

        String scenario = ConfigFileReader.getConfigProperty("scenario.name");
        WebElement element = driver.findElement(By.xpath("//h6[text()='"+scenario+"']"));
        SeleniumUtils.clickOnAnElement(element);
    }

    public void verifyTheSimulationResultPageComponents(String scenario){
        verifyModulesButtonAvailability();
        verifySimulationResultPageButtons();
        verifySimulationResultTabs();
        verifySimulationResultTitles();
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(scrubberComponent),
                "Scrubber component is not displayed");
        //Commenting till finding a solution to extract the data
        //validateTheSimulationResult(scenario);
    }

    public void verifyModulesButtonAvailability(){
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(gisButton),
                "GIS button is not displayed");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(zoneBoundariesButton),
                "Zone boundaries button is not displayed");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(devicesButton),
                "Devices button is not displayed");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(otherSourcesButton),
                "Other sources button is not displayed");
    }
    public void verifySimulationResultPageButtons(){
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(duplicateButton),
                "Duplicate button is not displayed");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(editButton),
                "Edit button is not displayed");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(recenterButton),
                "Recenter button is not displayed");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(locatorButton),
                "Locator button is not displayed");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(infoButton),
                "Info button is not displayed");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(downloadButton),
                "Download button is not displayed");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(exitButton),
                "Exit button is not displayed");
    }

    public void verifySimulationResultTabs(){
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(overviewTab),
                "Overview tab is not present");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(actionsTab),
                "Actions tab is not present");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(mitigationsTab),
                "Mitigations tab is not present");
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(scenarioTab),
                "Scenario tab is not present");
    }

    public void verifySimulationResultTitles(){
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(simulationResultsTitle),
                "Simulation result title is not present in the page");
        WebElement element = driver.findElement(
                By.xpath("//h6[text()='"+ConfigFileReader.getConfigProperty("scenario.name")+"']"));
        Assert.assertTrue(SeleniumUtils.isElementDisplayed(element),"Scenario name is not displayed");
    }

    public void validateTheSimulationResult(String scenario){
        SeleniumUtils.clickOnAnElement(actionsTab);
        SoftAssert softAssert = new SoftAssert();
        List<WebElement> timeLineList = driver.findElements(
                By.xpath("//div[contains(@class,'tabpane-active')]/descendant::h6[contains(@class,'jss1pbyrsb')]"));
        System.out.println("Size of the list - " + timeLineList.size());

        for(WebElement element : timeLineList){
            String elementText = element.getText();
            System.out.println("Time line element text is - "+ elementText);

            softAssert.assertTrue(listOfTimelineTypes(scenario).contains(elementText),
                    "The time line event title "+ elementText +" is not from the defined list");
        }
        softAssert.assertAll();
    }

    public List<String> listOfTimelineTypes(String scenario){
        List<String> list = new ArrayList<>();
        list.add("Simulation Start");
        if(scenario.equalsIgnoreCase("Valve Operation")){
            list.add("Valve Operation");
            list.add("Selected Valves");
            list.add("Valve Operation End");
        }

        list.add("Reverse Flow");
        list.add("Low Velocity Change");
        list.add("No Flows");
        list.add("Contain Source of Discoloration");


        list.add("Simulation End");

        return list;
    }
}
