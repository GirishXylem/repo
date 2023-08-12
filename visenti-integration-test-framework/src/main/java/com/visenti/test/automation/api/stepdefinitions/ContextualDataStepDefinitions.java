package com.visenti.test.automation.api.stepdefinitions;

import com.visenti.test.automation.api.modules.healthmonitor.DataLoadHealthAPI;
import com.visenti.test.automation.api.modules.othersources.LoadContextualData;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import cucumber.api.java.en.Given;

import java.io.IOException;

public class ContextualDataStepDefinitions {
    private RestAssuredHelper helper;

    public ContextualDataStepDefinitions(RestAssuredHelper helper) {
        this.helper = helper;
    }

    @Given("^I setup the request for contextual data \"([^\"]*)\" api with parameter \"([^\"]*)\" and \"([^\"]*)\"$")
    public void setUpRequestForContextualDataAPIWithParameter(String api, String param, String days) {
        LoadContextualData loadContextualData = new LoadContextualData(this.helper);
        loadContextualData.setUpRequestForContextualData(api,param,days);
    }

    @Given("^I verify the common response for contextual data$")
    public void verifyDefaultResponseContent() throws Exception {
        LoadContextualData loadContextualData = new LoadContextualData(this.helper);
        loadContextualData.verifyCommonResponseForContextualDataApi();
    }

    @Given("^I also verify the specific response for contextual data of type \"([^\"]*)\"$")
    public void verifySpecificResponseContent(String contextualDataType) throws Exception {
        LoadContextualData loadContextualData = new LoadContextualData(this.helper);
        loadContextualData.verifySpecificResponseForContextualApi(contextualDataType);

    }

}
