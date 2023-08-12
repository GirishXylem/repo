package com.visenti.test.automation.api.stepdefinitions.sew;

import org.testng.Reporter;

import com.visenti.test.automation.api.modules.sew.LoadAIMSTheme;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import cucumber.api.java.en.Then;

/**
 * This class will define Aims theme functionality related definitions Load Aims
 * theme
 */
public class AIMSThemeStepDefinitions {

	@Then("^I verify the loading aims theme for \"([^\"]*)\" anomaly for \"([^\"]*)\" days$")
	public void verifyLoadingAimsThemeForAGivenAnomalyForDays(String anomaly, long days) {

		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		switch (anomaly.toLowerCase()) {
		case "acoustic":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadaimsacoustic"));
			break;
		case "customer_meter":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadaimscustomermeter"));
			break;

		case "flow":
			TestRailTestManagement
					.setTestCase(FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadaimsflow"));
			break;

		case "pressure":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadaimspressure"));
			break;

		case "transient":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadaimstransient"));
			break;

		case "wqy":
			TestRailTestManagement
					.setTestCase(FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadaimswqy"));
			break;

		case "all":
			TestRailTestManagement
					.setTestCase(FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadaimsall"));
			break;

		default:
			throw new RuntimeException("Wrong Device name passed");
		}

		LoadAIMSTheme.verifyGetIncidentsAPIForaGivenAnomalyForDefaultDays(anomaly, days);

		Log.info("Verified the getIncidents API for a given Anomaly " + anomaly.toLowerCase());
		Reporter.log("Verified the getIncidents API for a given Anomaly " + anomaly.toLowerCase());
	}

	@Then("^I verify the getAIMSUserPreferencesAPI$")
	public void verifyGetAimsUserPreferencesAPI() {
		LoadAIMSTheme.getUserPreferencesForAIMS();
		Log.info("Successfully verified the getAIMSUserPreferencesAPI ");
		Reporter.log("Successfully verified the getAIMSUserPreferencesAPI ");
	}

}
