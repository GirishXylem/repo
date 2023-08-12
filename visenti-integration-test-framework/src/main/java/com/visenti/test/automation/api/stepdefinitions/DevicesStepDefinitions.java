package com.visenti.test.automation.api.stepdefinitions;

import java.io.IOException;

import com.visenti.test.automation.api.modules.devices.LoadDevices;
import com.visenti.test.automation.api.modules.devices.search.DeviceLoadMetaSearch;
import com.visenti.test.automation.api.modules.gis.search.GISLoadMetaSearch;
import com.visenti.test.automation.helpers.RestAssuredHelper;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class DevicesStepDefinitions {
	
	
	private LoadDevices objLoadDevices;
	private RestAssuredHelper helper; 
	public DevicesStepDefinitions(RestAssuredHelper helper)
	{
		this.helper=helper;
		objLoadDevices=new LoadDevices(this.helper);
	}
	
	@Given("^I setup the Request for device \"([^\"]*)\" and network \"([^\"]*)\"$")
	public void setUpRequestForAGivenDeviceAndNetwork(String device, String network) throws IOException  {
	    objLoadDevices.setUpRequestForDevicesMetaSearchApi(device, network);
	}
	
	@Given("^I setup the Request for device \"([^\"]*)\"$")
	public void setUpRequestForAGivenDevice(String device) throws IOException  {
		
		objLoadDevices.setUpRequestForDevicesMetaSearchApi(device);
	   
	}
	@Then("^I verify the full Response for device Load Devices Api$")
	public void verifyFullResponseForLoadDevicesAPI() throws Exception  {
	    objLoadDevices.verifyFullResponseForTheLoadDevicesApi();
	}

	@Then("^I verify specific details of devices Api response for the network \"([^\"]*)\"$")
	public void verifySpecificResponseForLoadDevicesAPI(String network) throws Exception  {
		objLoadDevices.verifySpecificResponseForTheLoadDevicesApi(network);
	}

	@Given("^I setup the request for device meta search api with \"([^\"]*)\" , \"([^\"]*)\" and \"([^\"]*)\"$")
	public void setUpRequestForGisIdSearchApi(String searchType, String searchString, String searchField) throws IOException {
		DeviceLoadMetaSearch deviceLoadMetaSearch = new DeviceLoadMetaSearch(helper);
		deviceLoadMetaSearch.setUpRequestForDeviceSearch(searchType, searchString, searchField);
	}

	@Given("^I verify the default response for the GIS meta search Api for devices$")
	public void verifyDefaultContentOfDeviceMetaSearchApi() throws Exception {
		DeviceLoadMetaSearch deviceLoadMetaSearch = new DeviceLoadMetaSearch(helper);
		deviceLoadMetaSearch.verifyDefaultResponseContentForDeviceMetaSearchApi();
	}

	@Given("^I verify meta details of station search api with \"([^\"]*)\" and \"([^\"]*)\"$")
	public void verifySpecificContentOfDeviceMetaSearchApi(String searchSTring, String field)  {
		DeviceLoadMetaSearch deviceLoadMetaSearch = new DeviceLoadMetaSearch(helper);
		deviceLoadMetaSearch.verifySpecificContentsOfDeviceMetaSearch(searchSTring,field);
	}

	@Given("^I setup the request for \"([^\"]*)\" api to get the latest data for station \"([^\"]*)\"$")
	public void setUpRequestForDeviceLatestData(String api, String station)   {
		objLoadDevices.setUpRequestForDevicesLatestDataApi(api,station);
	}

	@And("^I verify the response for get latest data Api$")
	public void verifyResponseForLatestDataForDevicesAPI() throws Exception  {
		objLoadDevices.verifyResponseForDevicesLatestDataApi();
	}
}
