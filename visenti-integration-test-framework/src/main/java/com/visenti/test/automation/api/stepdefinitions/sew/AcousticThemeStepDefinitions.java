package com.visenti.test.automation.api.stepdefinitions.sew;

import org.testng.Reporter;

import com.visenti.test.automation.api.modules.sew.LoadAcousticTheme;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import cucumber.api.java.en.Given;

public class AcousticThemeStepDefinitions {
	
	//Explorer Step Definition
	@Given("^I verify the Explorer API data for the station \"([^\"]*)\" for default \"([^\"]*)\" days from current date$")
	public void verifyExplorerAPIDataForAStationForDefaultNDaysFromCurrentDate(String stationId, String days) {
		TestRailTestManagement.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		TestRailTestManagement.setTestCase(FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadacousticthemeexplorer"));
		LoadAcousticTheme.verifyAcousticExplorerData(stationId, days);
		Log.info("Successfully verified Acoustic Explorer API");
		Reporter.log("Successfully verified Acoustic Explorer API");
	}

}
