package com.visenti.test.automation.api.stepdefinitions.sew;

import org.testng.Reporter;

import com.visenti.test.automation.api.modules.sew.LoadAIMSTheme;
import com.visenti.test.automation.api.modules.sew.LoadZoneTheme;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;

/**
 * This class will define zone theme functionality related definitions Load zone
 * theme
 */
public class ZoneThemeStepDefinitions {

	@Then("^I verify the getCustomerInfoAPI$")
	public void verifyGetCustomerInfoAPIforAGivenCustomer() {
		LoadZoneTheme.verifyGetCustomerInfoAPIResponseForAGivenCustomer();
		Log.info("Verified successfully the getCustomerInfo API");
		Reporter.log("Verified successfully the getCustomerInfo API");
	}

	@Then("^I verify getThemesLabelAllAPI$")
	public void verifyGetThemesLabelAllAPI() {
		LoadZoneTheme.verifyGetThemesLabelAllAPIResponse();
		Log.info("Verified successfully the getThemesLabelAll API");
		Reporter.log("Verified successfully the getThemesLabelAll API");

	}

	@Then("^I verify APIs up and & running within initial load$")
	public void verifyZoneThemeSpecificAPIsInitialLoad() {

		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		TestRailTestManagement.setTestCase(
				FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadzonethemeapis"));

		TestRailTestManagement.setTestComment("Verify all theme label API" + System.lineSeparator());
		LoadZoneTheme.verifyGetThemesLabelAllAPIResponse();
		Log.info("Verified successfully the getThemesLabelAll API");

		TestRailTestManagement.setCompleteTestComment("Verify get user reference API" + System.lineSeparator());
		LoadAIMSTheme.getUserPreferencesForAIMS();
		Log.info("Successfully verified the getAIMSUserPreferencesAPI ");

		TestRailTestManagement.setCompleteTestComment("Verify customer info  API" + System.lineSeparator());
		LoadZoneTheme.verifyGetCustomerInfoAPIResponseForAGivenCustomer();
		Log.info("Verified successfully the getCustomerInfo API");

		TestRailTestManagement
				.setCompleteTestComment("Verify getting KML API" + System.lineSeparator() + System.lineSeparator());
		LoadZoneTheme.gettingZoneIdsFromGetCustomerInfoAPIResponse();
		LoadZoneTheme.verifyGetKMLAPIResponseForAGivenCustomer();
		Log.info("getKML api is successfully verified for customer ");

		TestRailTestManagement.setCompleteTestComment(" Updated by Automation Framework! " + System.lineSeparator());
		Reporter.log("Verified successfully the initial load APIs of Zone theme");

	}

	/*
	 * @Then("^I verify the water balance metadata api for \"([^\"]*)\"$") public
	 * void verifyWaterBalanceMetaDataAPIForAGivenWaterBalanceModel(String
	 * waterBalanceModel) {
	 * LoadZoneTheme.verifyGetWaterBalanceMetaDataAPIResponseForAWaterBalanceModel(
	 * waterBalanceModel);
	 * Log.info("Verified successfully the water balance metadata api for model "
	 * +waterBalanceModel); Reporter.
	 * log("Verified the successfully water balance metadata api for model "
	 * +waterBalanceModel); }
	 */
	/*
	 * @Then("^I verify the water balance default api for \"([^\"]*)\"$") public
	 * void verifyWaterBalanceDefaultAPIForModel(String waterBalanceModel) {
	 * LoadZoneTheme.verifyGetWaterBalanceDefaultAPIResponseForAWaterBalanceModel(
	 * waterBalanceModel); }
	 */

	@Then("^I verify water balance api for type \"([^\"]*)\" and water balance model \"([^\"]*)\"$")
	public void verifyWaterBalanceAPIForADataTypeAndWaterBalanceModel(String dataType, String waterBalanceModel) {
		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		TestRailTestManagement.setTestCase(
				FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadzonethemewaterbalance"));

		TestRailTestManagement
				.setCompleteTestComment("Verify water balance API for type '" + dataType + "' and water balance model '"
						+ waterBalanceModel + "'" + System.lineSeparator() + "Updated by Automation Framework!");
		LoadZoneTheme.verifyGetWaterBalanceAPIResponseForAGivenTypeAndWaterBalanceModel(dataType, waterBalanceModel);
		Reporter.log("Verified successfully the waterBalance API for type '" + dataType + "' and water balance model '"
				+ waterBalanceModel + "'");
		Log.info("Verified successfully the waterBalance API for type '" + dataType + "' and water balance model '"
				+ waterBalanceModel + "'");
	}

	@Then("^I verify the water balance api$")
	public void verifyWaterBalanceAPIForAGivenDataTypeAndWaterBalanceModel() {
		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		TestRailTestManagement.setTestCase(
				FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadzonethemewaterbalance"));

		TestRailTestManagement
				.setTestComment("Verify water balance API for meta data in bottom up table " + System.lineSeparator());
		LoadZoneTheme.verifyGetWaterBalanceAPIResponseForAGivenTypeAndWaterBalanceModel("metadata", "bottom_up");
		Log.info("Verified Successfully the getWaterBalance API for type meta and water balance model bottom up");
		TestRailTestManagement.setCompleteTestComment(
				"Verify water balance API for type default in bottom up table " + System.lineSeparator());
		LoadZoneTheme.verifyGetWaterBalanceAPIResponseForAGivenTypeAndWaterBalanceModel("default", "bottom_up");
		Log.info("Verified Successfully the getWaterBalance API for type default and water balance model bottom up");

		Reporter.log("Verified Successfully the getWaterBalance API for bottom up table");

		TestRailTestManagement.setCompleteTestComment(
				"Verify water balance API for meta data in top to bottom table " + System.lineSeparator());
		LoadZoneTheme.verifyGetWaterBalanceAPIResponseForAGivenTypeAndWaterBalanceModel("metadata", "top_to_bottom");
		Log.info("Verified Successfully the getWaterBalance API for type meta and water balance model top to bottom");
		TestRailTestManagement.setCompleteTestComment(
				"Verify water balance API for type default in top to bottom table " + System.lineSeparator());
		LoadZoneTheme.verifyGetWaterBalanceAPIResponseForAGivenTypeAndWaterBalanceModel("default", "top_to_bottom");
		Log.info(
				"Verified Successfully the getWaterBalance API for type default and water balance model top to bottom");

		TestRailTestManagement.setCompleteTestComment(" Updated by Automation Framework! " + System.lineSeparator());
		Reporter.log("Verified Successfully the getWaterBalance API for top to bottom table");

		Reporter.log("Verified get water balance API successfully!");

	}

	@Then("^I verify the water balance api for type \"([^\"]*)\" and water balance model \"([^\"]*)\" for default \"([^\"]*)\" days$")
	public void verifyWaterBalanceAPIForAGivenDataTypeAndWaterBalanceModelForDefaultDays(String dataType,
			String waterBalanceModel, String days) {

		TestRailTestManagement.setCompleteTestComment("Verify water balance API for type '" + dataType
				+ "' and water balance model '" + waterBalanceModel + "' for default '" + days + "' days"
				+ System.lineSeparator() + "Updated by Automation Framework!");

		LoadZoneTheme.verifyGetWaterBalanceAPIResponseForAGivenTypeAndWaterBalanceModelForDefaultDays(dataType,
				waterBalanceModel, days);

		Reporter.log("Verified successfully the waterBalance API for type '" + dataType + "' and water balance model '"
				+ waterBalanceModel + "' for default '" + days + "' days ");
		Log.info("Verified successfully the waterBalance API for type '" + dataType + "' and water balance model '"
				+ waterBalanceModel + "' for default '" + days + "' days");

	}

	@Then("^I verify the statistics/all api for the following zone ids for default \"([^\"]*)\" days$")
	public void verifyStatisticsAllApiForTheZoneIdsPassedAsDataTableForDefaultDays(String days, DataTable zoneIds) {
		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		TestRailTestManagement.setTestCase(
				FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadzonethemestatistics"));

		LoadZoneTheme.verifyStatisticsAllAPIResponseForListOfZoneIdsForDefaultDays(days, zoneIds);
		Log.info("statistics/all api is successfully verified for the zone ids: \n" + zoneIds);
		Reporter.log("statistics/all api is successfully verified for the zone ids: \n" + zoneIds);

	}

	@Then("^I fetch the zone Ids from the getCustomerInfoAPI$")
	public void fetchZoneIdsFromGetCustomerInfoAPIForAGivenCustomer() throws Throwable {
		LoadZoneTheme.gettingZoneIdsFromGetCustomerInfoAPIResponse();
	}

	@Then("^I verify the getKML api for all the zone Ids$")
	public void verifyGetKMLAPIForAllZoneIds() {
		LoadZoneTheme.verifyGetKMLAPIResponseForAGivenCustomer();
		Log.info("getKML api is successfully verified for customer ");
		Reporter.log("getKML api is successfully verified for the customer ");

	}

	@Then("^I verify the trends api for zoneId \"([^\"]*)\" and sensorType \"([^\"]*)\" for default \"([^\"]*)\" days$")
	public void verifyTheTrendsAPIForAZoneIdAndSensorType(String zoneId, String sensorType, String days) {
		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		TestRailTestManagement.setTestCase(
				FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadzonethemetrends"));
		LoadZoneTheme.verifyTrendsAPIResponseForAZoneIdAndSensorTypeForDefaultDays(zoneId, sensorType, days);
		Log.info("Trends api is successfully verified for the zoneId-" + zoneId + " sensorType-" + sensorType
				+ " for days-" + days);
		Reporter.log("Trends api is successfully verified for the zoneId-" + zoneId + " sensorType-" + sensorType
				+ " for days-" + days);
	}

}
