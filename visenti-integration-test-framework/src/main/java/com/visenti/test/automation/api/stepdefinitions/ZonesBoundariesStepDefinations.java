
package com.visenti.test.automation.api.stepdefinitions;

import com.visenti.test.automation.api.modules.zoneboundaries.GisLoadKMLZoneboundaries;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import cucumber.api.java.en.Then;

public class ZonesBoundariesStepDefinations {
	private GisLoadKMLZoneboundaries objKMLZoneboundaries;
	private RestAssuredHelper helper;

	public ZonesBoundariesStepDefinations(RestAssuredHelper helper) {
		this.helper = helper;
		objKMLZoneboundaries = new GisLoadKMLZoneboundaries(helper);
	}

	@Then("^I setup the request for \"([^\"]*)\" and perform \"([^\"]*)\" request on the endpoint \"([^\"]*)\" and validate the common and specific response$")
	public void setup_the_request_and_perform_request_on_the_endpoint_and_validate_the_common_and_specific_response(
			String api, String requestType, String endPoint) throws Throwable {
		objKMLZoneboundaries.setUpRequestWithQueryParams(api, requestType, endPoint);
	}

}
