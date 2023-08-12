package com.visenti.test.automation.api.stepdefinitions;

import com.visenti.test.automation.api.modules.aims.AimsGetIncidents;
import com.visenti.test.automation.api.modules.aims.AimsLoadConfig;
import com.visenti.test.automation.api.modules.aims.AimsLoadUserPreferences;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.io.IOException;

public class AimsStepDefinitions {

	private AimsLoadConfig objAimsConfig;
	private AimsLoadUserPreferences objAimsUserPreferences;
	private AimsGetIncidents objAimsGetIncidents;
	private RestAssuredHelper helper;

	public AimsStepDefinitions(RestAssuredHelper helper) {

		this.helper = helper;
		objAimsConfig = new AimsLoadConfig(this.helper);
		objAimsUserPreferences = new AimsLoadUserPreferences(helper);
		objAimsGetIncidents = new AimsGetIncidents(helper);
	}

	@Given("^I setup the request for \"([^\"]*)\" /getAimsConfig Api$")
	public void setUpRequestForAimsGetConfigApi(String api) {
		objAimsConfig.setUpRequestWithQueryParam(api);
	}

	@Then("^I verify the full response for the Aims getConfig Api$")
	public void verifyFullResponseForAimsGetConfigApi() throws Exception {
		objAimsConfig.verifyFullResponseForAimsGetConfigApi();
	}

	@Given("^I setup the request for \"([^\"]*)\" /getAIMSUserPreferences Api$")
	public void setUpRequestForAIMSGetUserPreferencesApi(String api) {
		objAimsUserPreferences.setUpRequestWithQueryParams(api);
	}

	@Given("^I setup the request for \"([^\"]*)\" /saveAIMSUserPreferences Api$")
	public void setUpRequestForAIMSSaveUserPreferencesApi(String api) throws IOException {
		objAimsUserPreferences.setUpRequestForSaveUserPreference(api);
	}

	@Given("^I setup the request for \"([^\"]*)\" /getIncidentsDoc Api with Incident type \"([^\"]*)\" and anomaly \"([^\"]*)\" and zones \"([^\"]*)\"$")
	public void setUpRequestForAIMSGetIncidentsDocsApi(String api, String incidentType, String anomaly, String zone) throws IOException {
		objAimsConfig.setUpRequestForGetIncidentsDocApi(api,incidentType,anomaly,zone);
	}

	@Then("^I verify the full response for Aims getUserPreferences Api$")
	public void verifyFullResponseForAimsGetUserPreferencesApi() throws Exception {
		objAimsUserPreferences.verifyFullResponseForAimsGetUserPreferencesApi();
	}

	@Then("^I verify the full response for Aims saveUserPreferences Api$")
	public void verifyFullResponseForAimsSaveUserPreferencesApi() throws Exception {
		objAimsUserPreferences.verifyResponseForAIMSSaveUserPreferenceAPI();
	}

	@Given("^I setup the request for the \"([^\"]*)\" api with Incident type \"([^\"]*)\" and anomaly \"([^\"]*)\" also the zones \"([^\"]*)\"$")
	public void setUpRequestForAIMSGetIncidentsApi(String api, String incidentType, String anomaly, String zones) throws IOException {
		objAimsGetIncidents.setUpRequestForGetIncidentsApi(api,incidentType,anomaly,zones);
	}

	@Then("^I verify the full response for the Aims getIncident Api$")
	public void verifyFullResponseForAimsGetIncidentApi() throws Exception {
		objAimsGetIncidents.verifyCommonResponseForAimsApi();
	}

	@Then("^I verify specific details in getIncident Api response like incident type \"([^\"]*)\" and others$")
	public void verifySpecificDetailsInAimsGetIncidentApi(String incidentType) {
		objAimsGetIncidents.verifySpecificDetailsInGetIncidentApi(incidentType);
	}

	@Given("^I setup the request for the \"([^\"]*)\" api for incident details$")
	public void setUpRequestForAIMSGetIncidentsDetailsApi(String api) throws IOException {
		objAimsGetIncidents.requestForGetIncidentsDetailsApi(api);
	}

	@Given("^I retrieve the incident ID$")
	public void getIncidentIDFromGetIncidentAPI() {
		objAimsGetIncidents.getIncidentId();
	}

	@Then("^I verify the full response for the Aims get incident details Api$")
	public void verifyFullResponseForAimsGetIncidentDetailsApi() throws Exception {
		objAimsGetIncidents.verifyCommonResponseForAimsApi();
	}

	@Then("^I retrieve few details from get incidents API$")
	public void getFewValuesFromGetIncidents() {
		objAimsGetIncidents.getValuesFromIncidentList();
	}

	@Then("^I verify details in the incident list is same as in the incident details$")
	public void verifyValuesWithIncidentListAndDetails() {
		objAimsGetIncidents.verifyIncidentListAndDetailsHaveSameValues();
	}

	@Then("^I verify the response content type of getIncidentsDoc Api$")
	public void verifyResponseForAimsGetIncidentsDocApi() {
		objAimsConfig.verifyResponseContentTypeIsCSV();
	}

}
