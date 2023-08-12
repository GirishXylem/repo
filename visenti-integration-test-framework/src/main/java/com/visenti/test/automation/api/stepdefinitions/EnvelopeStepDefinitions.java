package com.visenti.test.automation.api.stepdefinitions;

import com.visenti.test.automation.api.modules.envelope.PressureEnv;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class EnvelopeStepDefinitions {
    private RestAssuredHelper helper;
    private final PressureEnv pressureEnv;

    public EnvelopeStepDefinitions(RestAssuredHelper helper) {
        this.helper = helper;
        pressureEnv = new PressureEnv(this.helper);
    }

    @Given("^I setup the request for \"([^\"]*)\" /pressureEnvConfig Api$")
    public void iSetupTheRequestForPressureEnvConfigApi(String api) {
        pressureEnv.setUpRequest(api);
    }

    @And("^I verify the full Response for Get pressureEnvConfig Api$")
    public void iVerifyTheFullResponseForGetPressureEnvConfigApi() throws Exception {
        pressureEnv.verifyTheFullResponseForPressureEnvConfigApi();
    }

    @Given("^I setup the request for \"([^\"]*)\" /pressureEnvData Api$")
    public void iSetupTheRequestForPressureEnvDataApi(String api)  {
        pressureEnv.setUpRequest(api);
    }

    @And("^I verify the full Response for Get pressureEnvData Api$")
    public void iVerifyTheFullResponseForGetPressureEnvDataApi() throws Exception {
        pressureEnv.verifyTheFullResponseForPressureEnvDataApi();
    }


    @And("^I setup the query param's for /pressureEnvData Api with startTime \"([^\"]*)\"$")
    public void iSetupTheQueryParamSForPressureEnvDataApiWithStartTime(String startTime) {
        pressureEnv.setQueryParamForPressureEnvDataApi(Long.valueOf(startTime));
    }
}
