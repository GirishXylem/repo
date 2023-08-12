package com.visenti.test.automation.api.stepdefinitions;

import com.visenti.test.automation.api.modules.healthmonitor.DataLoadHealthAPI;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

import java.io.IOException;

public class HealthMonitorStepDefinitions {
    private RestAssuredHelper helper;

    public HealthMonitorStepDefinitions(RestAssuredHelper helper) {
        this.helper = helper;
    }

    @Given("^I setup the request for the \"([^\"]*)\" api with parameter$")
    public void setUpRequestForDataAPIWithParameter(String api) {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.setUpRequestForInstrumentApi(api);
    }

    @And("^I setup the query param's for HM instrument tab stations with the \"([^\"]*)\"$")
    public void setUpInstrumentStationAPIQueryParams(String vendor) {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.setUpQueryParamToHMInstrumentStationAPI(vendor);
    }

    @And("^I setup the query param's for HM instrument tab sensors with the \"([^\"]*)\"$")
    public void setUpInstrumentSensorsAPIQueryParams(String vendor) {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.setUpQueryParamToHMInstrumentSensorsAPI(vendor);
    }

    @Given("^I setup the request for the \"([^\"]*)\" api$")
    public void setUpRequestForDataAPI(String api) {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.setUpRequestForStatusApi(api);
    }

    @Given("^I setup the request for the \"([^\"]*)\" api with the base path \"([^\"]*)\"$")
    public void setUpRequestForDataAPIwithBasePath(String api, String basePath) {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.setUpRequestForChartsApiWithBasePath(api, basePath);
    }

    @Given("I verify the full response for Station/Sensor health API$")
    public void verifyResponseForStationSensorHealth() throws Exception {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifyFullResponseForInstrumentHealthApi();
    }

    @Given("I verify the station is online when at least one sensor is online$")
    public void verifyOnlineStatusOfStation() {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifyOnlineStations();
    }

    @Given("I also verify the station is offline when all the sensors are offline$")
    public void verifyOfflineStatusOfStation() {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifyOfflineStations();
    }

    @Given("I also verify if station has received data within \"([^\"]*)\" hours$")
    public void verifyDataReceivedWithInHours(String hours) {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.findDifferenceInHours(hours);
    }

    @Given("I verify the full response for Data status health API$")
    public void verifyResponseForDataStatus() throws Exception {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifyDataStatusHealthApiResponse();
    }

    @Given("I verify the common response for data module for all the health API's$")
    public void verifyCommonResponseForAllHealthApi() throws Exception {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifyCommonResponseForHealthMonitorApi();
    }

    @Given("I also verify the data availability of all the Vendors$")
    public void verifyDataAvailabilityOfVendors() {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifyDataAvailability();
    }

    @And("^I setup the query param's for HM widget API with the \"([^\"]*)\"$")
    public void setQueryParam(String vendor) {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.setUpQueryParamToHMAPIs(vendor);
    }

    @And("^I also verify the widget statistics from widget API$")
    public void verifyWidgetSpecificResponse() throws Exception {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifyWidgetStatsFromWidgetApi();
    }

    @And("^I setup the query param's for issue table API with the \"([^\"]*)\"$")
    public void setUpHMAPIQueryParams(String vendor) {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.setUpQueryParamToHMAPIs(vendor);
    }

    @And("^I also verify the issue type distribution table API for specific \"([^\"]*)\"$")
    public void verifyIssueTypeDistributionSpecificResponse(String vendor) throws Exception {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifyIssueTypeDistributionTableDetailsInTheApi(vendor);
    }

    @And("^I setup the query param's for all the charts of HM with the \"([^\"]*)\"$")
    public void setUpHMChartsAPIQueryParams(String vendor) {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.setUpQueryParamToHMChartsAPIs(vendor);
    }

    @Given("I verify the specific response for daily issues chart health API$")
    public void verifySpecificDetailsOfDailyIssuesChartResponse() {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifySpecificDetailsForDailyIssuesChartAPI();
    }

    @And("I verify the specific response for issues resolved,new issues and backlog chart health API$")
    public void verifySpecificDetailsOfIssuesResolvedNewAndBacklogChartResponse() {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifySpecificDetailsForIssuesResolvedNewAndBacklogChartAPI();
    }

    @And("I verify the specific response for hour on hour chart health API$")
    public void verifySpecificDetailsOfHourOnHourChartResponse() {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifySpecificDetailsForHourOnHourChartAPI();
    }

    @And("I verify the specific response for pie chart health API$")
    public void verifySpecificDetailsOfPieChartResponse() throws Exception {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifySpecificDetailsForTrendsPieChartAPI();
    }

    @And("I verify the specific response for issue distribution chart health API$")
    public void verifySpecificDetailsOfIssueDistributionChartResponse() {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifySpecificDetailsForIssueDistributionChartAPI();
    }

    @And("I verify specific details of sensor in instrument tab$")
    public void verifySpecificDetailsOfSensorDetailsInInstrumentTab() {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifySpecificDetailsForInstrumentTabSensorDetails();
    }

    @Given("^I setup the request for \"([^\"]*)\" api for user preferences$")
    public void setUpRequestToGetUserPreferences(String api) {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.setUpRequestForUserPreferences(api);
    }

    @And("I verify the full response for HM user preferences Api$")
    public void verifyResponseOfUserPreferences() throws Exception {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifyResponseDetailsForUserPreference();
    }
    @Given("^I setup the request for \"([^\"]*)\" api for updating user preferences for \"([^\"]*)\"$")
    public void setUpRequestToUpdateUserPreferences(String api,String columns) throws IOException {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.setUpRequestForUpdateUserPreferences(api,columns);
    }
    @And("I verify the full response for HM user preferences post Api$")
    public void verifyResponseOfUpdateUserPreferences() throws Exception {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifyResponseDetailsToUpdateUserPreference();
    }

    @Given("^I setup the request for \"([^\"]*)\" api for getting detections$")
    public void setUpRequestToGetDetections(String api)  {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.setUpRequestForGettingDetections(api);
    }

    @And("I verify the full response for getting HM detections$")
    public void verifyResponseOfHMDetections() throws Exception {
        DataLoadHealthAPI dataLoadHealthAPI = new DataLoadHealthAPI(this.helper);
        dataLoadHealthAPI.verifyResponseDetailsOfHMDetections();
    }
}
