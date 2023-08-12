package com.visenti.test.automation.api.stepdefinitions.sew;

import com.visenti.test.automation.api.modules.sew.ContextualData;
import com.visenti.test.automation.helpers.FileReaderManager;

import org.testng.Reporter;

import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import cucumber.api.java.en.Then;

public class ContextualDataStepDefinitions {

	@Then("^I verify the Contextual Data API for \"([^\"]*)\" for default \"([^\"]*)\" days$")
	public void verifyTheAPIForGivenContextualDataTypeForDefaultDays(String type, String days) {

		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		if (type.equals("CustomerCalls")) {
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadcustomercalls"));
		} else {
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadscheduledjobs"));

		}
		ContextualData.verifyContextualDataResponseForAGivenType(type, days);
		Log.info("Successfully verified the Contextual Data API Response for " + type + " for default " + days
				+ " days");
		Reporter.log("Successfully verified the Contextual Data API Response for " + type + " for default " + days
				+ " days");
	}

}
