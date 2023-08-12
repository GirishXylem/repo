package com.visenti.test.automation.api.stepdefinitions;

import com.visenti.test.automation.api.modules.healthmonitor.DataLoadHealthAPI;
import com.visenti.test.automation.api.modules.simulation.SimulationLoadDataAPI;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class SimulationStepDefinitions {
    private RestAssuredHelper helper;
    SimulationLoadDataAPI simulationLoadDataAPI;

    public SimulationStepDefinitions(RestAssuredHelper helper) {
        this.helper = helper;
    }

    @Given("^I setup the request for the simulation api with the base path \"([^\"]*)\"$")
    public void setUpRequestForSimAPI(String basePath) {
        simulationLoadDataAPI = new SimulationLoadDataAPI(helper);
        simulationLoadDataAPI.setUpRequestForSimulationApi("",basePath);
    }
    @And("^I setup the query param's for the simulation result list$")
    public void setUpRequestForSimResultAPIWithParameter() {
        simulationLoadDataAPI = new SimulationLoadDataAPI(helper);
        simulationLoadDataAPI.setUpQueryParamToSimResultAPIs();
    }

    @And("^I verify the response for Simulations results API$")
    public void verifySimulationResultAPI() throws Exception{
        simulationLoadDataAPI = new SimulationLoadDataAPI(helper);
        simulationLoadDataAPI.verifyTheResponseOfSimulationResultAPi();
    }

    @And("^I verify the response for create simulations API$")
    public void verifyCreateSimulationAPI() throws Exception{
        simulationLoadDataAPI = new SimulationLoadDataAPI(helper);
        simulationLoadDataAPI.verifyTheResponseOfCreateSimulationAPi();
    }

    @And("^I verify the response for simulations settings API$")
    public void verifySimulationSettingsAPI() throws Exception{
        simulationLoadDataAPI = new SimulationLoadDataAPI(helper);
        simulationLoadDataAPI.verifyTheResponseOfSimulationSettingsAPi();
    }

    @And("^I add payload to the request with \"([^\"]*)\" for \"([^\"]*)\"$")
    public void includePayloadInTheRequest(String gisId, String gisType) throws Exception{
        simulationLoadDataAPI = new SimulationLoadDataAPI(helper);
        simulationLoadDataAPI.addPayloadToTheSimulationCreateRequest(gisId,gisType);
    }
}
