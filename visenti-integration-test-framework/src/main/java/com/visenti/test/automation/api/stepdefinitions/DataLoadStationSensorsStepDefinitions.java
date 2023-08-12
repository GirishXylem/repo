package com.visenti.test.automation.api.stepdefinitions;

import com.visenti.test.automation.api.modules.plotter.dma.DataLoadDmaSensors;
import com.visenti.test.automation.api.modules.plotter.station.DataLoadStationSensors;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class DataLoadStationSensorsStepDefinitions {

	private RestAssuredHelper helper;
	public DataLoadStationSensorsStepDefinitions(RestAssuredHelper helper) {
		this.helper = helper;
	}


	@Given("^I setup the request for the \"([^\"]*)\" api with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\" for \"([^\"]*)\"$")
	public void setUpRequestForStationDataSensorsApi(String api, String sensor,
												 String units, String dataType,String interval,String station) throws Exception {
		DataLoadStationSensors dataLoadStationSensors = new DataLoadStationSensors(this.helper);
		dataLoadStationSensors.setUpRequestForStationDataApi(api,sensor,units,dataType,interval,station);
	}

	@Then("^I verify the mandatory data in the response for Station data API$")
	public void verifyMandateResponseForLoadStationAPI() throws Exception {
		DataLoadStationSensors dataLoadStationSensors = new DataLoadStationSensors(this.helper);
		dataLoadStationSensors.verifyResponseForStationDataApi();
	}

	@Then("^I verify the station \"([^\"]*)\" meta in the response with \"([^\"]*)\" and \"([^\"]*)\"$")
	public void verifyMetaDataResponseForLoadStationAPI(String station, String units, String sensor)  {
		DataLoadStationSensors dataLoadStationSensors = new DataLoadStationSensors(this.helper);
		dataLoadStationSensors.verifyMetaDataResponseForStationDataApi(station,units,sensor);
	}

}
