package com.visenti.test.automation.api.stepdefinitions;

import com.visenti.test.automation.api.modules.surges.SurgesLoadConfig;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.io.IOException;

public class SurgesStepsDefinitions {
    private RestAssuredHelper helper;
    public SurgesStepsDefinitions(RestAssuredHelper helper) {
        this.helper = helper;
    }

    @Then("^I verify the full response for the surges config Api$")
    public void verifyTheResponseForSurgesConfigApi() throws Exception {
        SurgesLoadConfig surgesLoadConfig = new SurgesLoadConfig(helper);
        surgesLoadConfig.verifyResponseDetailsForSurgesConfigAPI();
    }

    @Given("^I setup the request for the \"([^\"]*)\" api for surges config$")
    public void setUpRequestForSurgesConfigAPI(String api) {
        SurgesLoadConfig surgesLoadConfig = new SurgesLoadConfig(helper);
        surgesLoadConfig.setUpRequest(api);
    }

    @Given("^I setup the request for the \"([^\"]*)\" api for network performance config with \"([^\"]*)\"$")
    public void setUpRequestForNetworkConfigAPI(String api, String id) {
        SurgesLoadConfig surgesLoadConfig = new SurgesLoadConfig(helper);
        surgesLoadConfig.setUpRequestWithParamForAPI(api, id);
    }

    @Then("^I verify the full response for the network config Api with \"([^\"]*)\"$")
    public void verifyTheResponseForNetworkConfigApi(String id) throws Exception {
        SurgesLoadConfig surgesLoadConfig = new SurgesLoadConfig(helper);
        surgesLoadConfig.verifyResponseDetailsForNetworkConfigAPI(id);
    }
    @Given("^I setup the request for the \"([^\"]*)\" api to get risk of zones$")
    public void setUpRequestForRiskByZoneAPI(String api) throws Exception {
        SurgesLoadConfig surgesLoadConfig = new SurgesLoadConfig(helper);
        surgesLoadConfig.setUpRequestForRiskByZone(api);
    }

    @Given("^I setup the request for the \"([^\"]*)\" api to get surges metrics$")
    public void setUpRequestForMetricsAPI(String api) throws IOException {
        SurgesLoadConfig surgesLoadConfig = new SurgesLoadConfig(helper);
        surgesLoadConfig.setUpRequestForMetrics(api);
    }

    @Given("^I setup the request for the \"([^\"]*)\" api to get damaging transients$")
    public void setUpRequestForDamagingTransientsAPI(String api) throws IOException {
        SurgesLoadConfig surgesLoadConfig = new SurgesLoadConfig(helper);
        surgesLoadConfig.setUpRequestForDamagingMetrics(api);
    }

    @Given("^I setup the request for the \"([^\"]*)\" api to get transients$")
    public void setUpRequestToGetTransients(String api) throws IOException {
        SurgesLoadConfig surgesLoadConfig = new SurgesLoadConfig(helper);
        surgesLoadConfig.setUpRequestToGetTransients(api);
    }
    @And("^I verify the full response for the risk by zones Api$")
    public void verifyTheResponseForRiskByZonesApi() throws Exception {
        SurgesLoadConfig surgesLoadConfig = new SurgesLoadConfig(helper);
        surgesLoadConfig.verifyResponseDetailsForGetRiskZonesAPI();
    }

    @And("^I verify the full response for the surges metrics Api$")
    public void verifyTheResponseForMetricsApi() throws Exception {
        SurgesLoadConfig surgesLoadConfig = new SurgesLoadConfig(helper);
        surgesLoadConfig.verifyResponseDetailsForSurgesMetricsAPI();
    }

    @And("^I verify the response for the damaging transients Api$")
    public void verifyTheResponseForDamagingTransientApi() throws Exception {
        SurgesLoadConfig surgesLoadConfig = new SurgesLoadConfig(helper);
        surgesLoadConfig.verifyResponseDetailsForDamagingTransientsAPI();
    }

    @And("^I verify the response for transients Api$")
    public void verifyTheResponseForTransientApi() throws Exception {
        SurgesLoadConfig surgesLoadConfig = new SurgesLoadConfig(helper);
        surgesLoadConfig.verifyResponseDetailsForGettingTransientsAPI();
    }
}
