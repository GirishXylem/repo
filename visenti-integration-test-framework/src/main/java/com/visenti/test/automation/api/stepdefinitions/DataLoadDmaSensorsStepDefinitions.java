package com.visenti.test.automation.api.stepdefinitions;

import com.visenti.test.automation.api.modules.initialportalload.*;
import com.visenti.test.automation.api.modules.plotter.dma.DataLoadDmaSensors;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class DataLoadDmaSensorsStepDefinitions {

	private RestAssuredHelper helper;
	public DataLoadDmaSensorsStepDefinitions(RestAssuredHelper helper) {
		this.helper = helper;
	}


	@Given("^I setup the Request of \"([^\"]*)\" data api \"([^\"]*)\" with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\" also verify the response$")
	public void setUpRequestForDmaDataSensorsApi(String api, String endPoint,String sensorType,
												 String units, String dataType,String interval) throws Exception {
		DataLoadDmaSensors dataLoadDmaSensors = new DataLoadDmaSensors(this.helper);
		dataLoadDmaSensors.setUpRequestForDmaDataApi(api,endPoint,sensorType,units,dataType,interval);
	}

}
