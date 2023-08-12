package com.visenti.test.automation.api.stepdefinitions;

import com.visenti.test.automation.helpers.RestAssuredHelper;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RestCommonStepDefinitions {
	
	RestAssuredHelper helper;
	
	public RestCommonStepDefinitions(RestAssuredHelper helper)
	{
		this.helper=helper;
	}
	
	@Then("^I verify the status code to be \"([^\"]*)\"$")
	public void verifyStatusCode(String expectedStatusCode)  {

		helper.verifyStatusCodeAsExpected(expectedStatusCode);

	}
	
	@Then("^I verify the response time to be less than \"([^\"]*)\" milliseconds$")
	public void verifyResponseTimeToBeLessThanExpectedMilliseconds(String expectedResponseTimeMilliseconds) {
	   helper.verifyResponseTimeLessThanExpectedMilliseconds(expectedResponseTimeMilliseconds);
	}
	
	@When("^I perform \"([^\"]*)\" request on the endpoint \"([^\"]*)\"$")
	public void performRequestOnAnEndPoint(String requestType, String endPoint) throws Exception  {
	    helper.performRequestOnAnEndPoint(requestType, endPoint);
	}
}
