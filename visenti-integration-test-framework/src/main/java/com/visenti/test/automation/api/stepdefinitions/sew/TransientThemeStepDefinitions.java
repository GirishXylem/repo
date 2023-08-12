package com.visenti.test.automation.api.stepdefinitions.sew;

import org.testng.Reporter;

import com.visenti.test.automation.api.modules.sew.LoadTransientTheme;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import cucumber.api.java.en.Then;

public class TransientThemeStepDefinitions {

	@Then("^I verify the rangeDeltaStatsAndCoverage API for \"([^\"]*)\" days$")
	public void verifyRangeDeltaStatsAndCoverageAPIForDays(String days) {

		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		TestRailTestManagement.setTestCase(
				FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadtransientthemestatistics"));
		LoadTransientTheme.verifyGetRangedDeltaStatsAndCoverageAPIForDefaultDays(days);
		Log.info("Verified successfully the getRangedDeltaStatsAndCoverage api for days " + days);
		Reporter.log("Verified successfully the getRangedDeltaStatsAndCoverage api for days " + days);
	}

	@Then("^I verify nearByLeaksByStationIds API for \"([^\"]*)\" days$")
	public void verifyNearByLeaksByStationIdsAPIForDays(String days) {
		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		TestRailTestManagement.setTestCase(
				FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadtransientthemestatistics"));

		LoadTransientTheme.verifyGetNearByLeaksByStationIdsAPIForDefaultDays(days);
		Log.info("Verified successfully the getNearByStationLeaksByStationIds API for days " + days);
		Reporter.log("Verified successfully the getNearByStationLeaksByStationIds API for days " + days);
	}
}
