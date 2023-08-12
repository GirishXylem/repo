package com.visenti.test.automation.api.stepdefinitions.sew;

import com.visenti.test.automation.api.modules.sew.LoadCustomerLeaksTheme;
import com.visenti.test.automation.helpers.FileReaderManager;

import org.testng.Reporter;

import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import cucumber.api.java.en.Then;

public class CustomerLeaksThemeStepDefinitions {

	@Then("^I verify the Trends API for given incidentType and waterLoss for \"([^\"]*)\" days$")
	public void verifyTrendsAPIForGivenIncidentTypeAndWaterLossForDefaultDays(String incidentType, String waterLoss, String days) throws Throwable {
		TestRailTestManagement.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		TestRailTestManagement.setTestCase(FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadcustomerleaksthemetrends"));

		TestRailTestManagement.setAction("Customer leaks Theme - Invoking trends data ");

		TestRailTestManagement.setTestComment("Validating drip leaks with water loss");
		TestRailTestManagement.setAction("Customer leaks Theme - Validating trends water loss & drip leaks data");
		LoadCustomerLeaksTheme.verifyTrendsAPIResponseForGivenIncidentTypeAndWaterLossForDefaultDays("customer_leak_drip", "true", days);

		TestRailTestManagement.setTestComment("Validating drip leaks");
		TestRailTestManagement.setAction("Customer leaks Theme - Validating trends drip leaks data");
		LoadCustomerLeaksTheme.verifyTrendsAPIResponseForGivenIncidentTypeAndWaterLossForDefaultDays("customer_leak_drip", "false", days);

		TestRailTestManagement.setTestComment("Validating major leaks with water loss");
		TestRailTestManagement.setAction("Customer leaks Theme - Validating trends water loss & major leaks data");
		LoadCustomerLeaksTheme.verifyTrendsAPIResponseForGivenIncidentTypeAndWaterLossForDefaultDays("customer_leak_major", "true", days);

		TestRailTestManagement.setTestComment("Validating major leaks");
		TestRailTestManagement.setAction("Customer leaks Theme - Validating trends major leaks data");
		LoadCustomerLeaksTheme.verifyTrendsAPIResponseForGivenIncidentTypeAndWaterLossForDefaultDays("customer_leak_major", "false", days);

		Log.info("Successfully asserted the Trends API for given incidentType: "+incidentType+" waterLoss: "+waterLoss +"for "+ days+" days");
	    Reporter.log("Successfully asserted the Trends API for given incidentType: "+incidentType+" and waterLoss: "+waterLoss +" for "+ days+" days");
	}
}
