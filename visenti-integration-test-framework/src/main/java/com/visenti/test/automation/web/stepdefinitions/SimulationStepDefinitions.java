package com.visenti.test.automation.web.stepdefinitions;

import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.DriverManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.utils.SeleniumUtils;
import com.visenti.test.automation.web.pageobjects.dmatool.HomePage;
import com.visenti.test.automation.web.pageobjects.dmatool.SimulationPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.testng.Assert;


public class SimulationStepDefinitions {

    private WebDriver driver;
    SimulationPage simulationPage ;

    public SimulationStepDefinitions() {
        driver= DriverManager.getDriver();
        simulationPage = new SimulationPage(driver);
    }


    @Then("^I verify the title of the right panel to be \"([^\"]*)\"$")
    public void verifySimulationTitle(String title) {
       simulationPage.verifyRightPanelTitle(title);
        Log.info("Verified the panel header to be Simulations");
    }

    @And("^I verify to see the simulation search bar is available$")
    public void verifySimulationSearchBarAvailability() {
        simulationPage.isSimulationSearchBarPresent();
        Log.info("Verified simulations right panel as a search bar for searching the results");
    }

    @And("^I verify all expected radio buttons are available for selection$")
    public void verifySimulationResultFilterButtons() {
        simulationPage.isSimulationResultFilterRadioButtonsPresent();
        Log.info("Verified all the radio buttons for filtering is displayed");
    }

    @And("^I also verify create button is available for simulation creation$")
    public void verifyCreateSimulationButtonIsPresent() {
        simulationPage.isSimulationCreateButtonPresent();
        Log.info("Verified all the radio buttons for filtering is displayed");
    }

    @And("^I click on create button$")
    public void clickOnSimulationCreateButton() {
        simulationPage.clickOnSimulationButtons("createButton");
        Log.info("Clicked on the create button");
    }

    @And("^I verify the simulation pop up box details$")
    public void verifySimulationPopUpForCreation() {
        simulationPage.verifySimulationPopUpDetails();
        Log.info("Verified all the details of the pop up");
    }

    @Then("^I enter the simulation \"([^\"]*)\" name and will click on the create button$")
    public void enterSimulationNameAndClick(String scenario) {
        simulationPage.enterTheSimulationNameAndCreate(scenario);
        Log.info("Entered the name and click on create simulation");
    }

    @And("^I select the \"([^\"]*)\" to create the simulation$")
    public void selectSimulationScenarioFromTheList(String scenarioName) {
        simulationPage.selectSimulationScenario(scenarioName);
        Log.info("Selected the " + scenarioName + "from the different scenario's");
    }

    @Then("^I search the \"([^\"]*)\" for the simulation and click on it$")
    public void searchForGisForSimulation(String gisValve) throws Exception {
        simulationPage.searchAndSelectGIS(gisValve);
    }
    @And("^I verify the fields after selecting the \"([^\"]*)\" like \"([^\"]*)\" selected")
    public void verifyAllTheFieldsAfterScenarioSelection(String scenario, String gisSelected)  {
        simulationPage.verifyTheMandatoryFieldsAfterScenarioSelection(scenario,gisSelected);
        Log.info("Verified all the the fields after the simulation scenario select");
    }

    @And("^I fill in all the fields required for simulation \"([^\"]*)\" with \"([^\"]*)\" and perform the \"([^\"]*)\"")
    public void selectAllTheMandatoryFieldsRequiredForSimulation(String scenario, String gisValue, String action)  {
        simulationPage.fillTheFieldsForSimulationScenario(scenario,gisValue,action);
        Log.info("Completed all the fields for scenario completion");
    }

    @Then("^I click on run simulation button$")
    public void completeSimulationScenario()  {
        simulationPage.clickOnSimulationButton();
        Log.info("Click on run simulation button to complete the scenario");
    }

    @And("^I search for the simulation to be displayed on the search page$")
    public void searchTheSimulationCreated()  {
        simulationPage.searchForTheSimulationCreated();
        Log.info("Search the simulation created -" + ConfigFileReader.getConfigProperty("scenario.name"));
    }

    @Then("^I click on the simulation created$")
    public void clickOnSimulationCreated()  {
        simulationPage.clickOnTheSimulationResult();
        Log.info("Clicked on the simulation result -"+ ConfigFileReader.getConfigProperty("scenario.name"));
    }

    @And("^I verify the components of the simulation result page for the \"([^\"]*)\"$")
    public void verifySimulationComponentsInTheResultPage(String scenario)  {
        simulationPage.verifyTheSimulationResultPageComponents(scenario);
    }

}
