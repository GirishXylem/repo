package com.visenti.test.automation.api.stepdefinitions.sew;

import org.testng.Reporter;

import com.visenti.test.automation.api.modules.sew.AIMSConfig;
import com.visenti.test.automation.api.modules.sew.LoadAcousticTheme;
import com.visenti.test.automation.api.modules.sew.LoadCustomerLeaksTheme;
import com.visenti.test.automation.api.modules.sew.LoadTransientTheme;
import com.visenti.test.automation.api.modules.sew.LoadZoneTheme;
import com.visenti.test.automation.api.modules.sew.ThemesMetaSearch;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import cucumber.api.java.en.Then;

public class CommonThemesStepDefinitions {

	@Then("^I load \"([^\"]*)\" theme with \"([^\"]*)\" days$")
	public void i_load_theme(String arg1, int arg2) {
		// TODO:write assert on loading exact theme TestRailTestManagement
		if (arg1.equals("zone")) {
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadzonetheme"));
			LoadZoneTheme.verifyLoadingZoneTheme();
			Log.info("Loaded zone theme");
		} else if (arg1.equalsIgnoreCase("acoustic")) {
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadacoustictheme"));
			LoadAcousticTheme.verifyLoadingAcousticTheme(arg2);
			Log.info("Loaded Acoustic theme");
			Reporter.log("Loaded Acoustic theme");
		} else if (arg1.equalsIgnoreCase("transient")) {
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadtransienttheme"));
			LoadTransientTheme.verifyLoadingTransientTheme(arg2);
			Log.info("Loaded Transient Theme");
			Reporter.log("Loaded Transient Theme");
		}

	}

	@Then("^I verify the initial loading in \"([^\"]*)\" theme in \"([^\"]*)\" portal with default \"([^\"]*)\" days$$")
	public void i_load_theme_with_default_date_range(String themeName, String portalName, String numberOfDays) {
		switch (themeName) {
		case "zone": {
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadzonetheme"));
			LoadZoneTheme.verifyLoadingZoneTheme();
			Log.info("Loaded zone theme");
			Reporter.log("Loaded Zone theme successfully");
		}
		case "aims": {
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadaimstheme"));
			Log.info("Loaded aims theme");
			Reporter.log("Loaded AIMS theme successfully");
		}
		case "acoustic": {
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadacoustictheme"));
			LoadAcousticTheme.verifyLoadingAcousticTheme(Integer.parseInt(numberOfDays));
			Log.info("Loaded Acoustic theme");
			Reporter.log("Loaded Acoustic theme successfully");
		}
		case "customer_leaks": {
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadcustomermetertheme"));
			LoadCustomerLeaksTheme.verifyLoadingCustomerLeaksTheme(Integer.parseInt(numberOfDays));
			Log.info("Loaded Customer Leaks theme");
			Reporter.log("Loaded Customer leaks theme successfully");
		}
		case "transient": {
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadtransienttheme"));
			LoadTransientTheme.verifyLoadingTransientTheme(Integer.parseInt(numberOfDays));
			Log.info("Loaded Transient Theme");
			Reporter.log("Loaded Transient Theme");
			Reporter.log("Loaded Transient theme successfully");
		}
		default: {
			new RuntimeException("Unidentified theme name found in Feature File");
		}
		}
	}

	@Then("^I should see \"([^\"]*)\" theme \"([^\"]*)\" data for \"([^\"]*)\" days$")
	public void i_should_see_data(String arg1, String arg2, int arg3) {
		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		switch (arg1) {
		case "acoustic":
			if (arg2.equals("statistics")) {
				TestRailTestManagement.setTestCase(FileReaderManager.getInstance().getConfigReader()
						.getTestRailTestCase("loadacousticthemestatistics"));
				LoadAcousticTheme.verifyAcousticStatistics(arg3);
				Log.info(arg1 + " theme statistics data loaded successfully");
			}
			if (arg2.equals("alerts")) {
				TestRailTestManagement.setTestCase(FileReaderManager.getInstance().getConfigReader()
						.getTestRailTestCase("loadacousticthemealerts"));
				LoadAcousticTheme.verifyGetIncidentsAPIForAcousticThemeForDefaultDays(arg3);
				Log.info("Verified " + arg1 + " " + arg2 + " api Response for days " + arg3);
				Reporter.log("Verified " + arg1 + " " + arg2 + " api Response for days " + arg3);
			}
			break;
		case "customer_leaks":
			if (arg2.equals("statistics")) {
				TestRailTestManagement.setTestCase(FileReaderManager.getInstance().getConfigReader()
						.getTestRailTestCase("loadcustomerleaksthemestatistics"));
				LoadCustomerLeaksTheme.verifyCustomerLeaksStatisticsResponseForDefaultDays(arg3);
				Log.info("Verified " + arg1 + " Customer Leaks " + arg2 + " api Response for days " + arg3);
				Reporter.log("Verified " + arg1 + " Customer Leaks " + arg2 + " api Response for days " + arg3);
			}

			if (arg2.equals("alerts")) {
				TestRailTestManagement.setTestCase(FileReaderManager.getInstance().getConfigReader()
						.getTestRailTestCase("loadcustomerleaksthemealerts"));
				LoadCustomerLeaksTheme.verifyGetIncidentsAPIForCustomerLeaksThemeForDefaultDays(arg3);
				Log.info("Verified " + arg1 + " " + arg2 + " api Response for days " + arg3);
				Reporter.log("Verified " + arg1 + " " + arg2 + " api Response for days " + arg3);
			}
			break;
		case "transient":

			if (arg2.equals("statistics")) {
				TestRailTestManagement.setTestCase(FileReaderManager.getInstance().getConfigReader()
						.getTestRailTestCase("loadtransientthemestatistic"));
				// TODO; NEED TO UPDATE
			}

			if (arg2.equalsIgnoreCase("alerts")) {
				TestRailTestManagement.setTestCase(FileReaderManager.getInstance().getConfigReader()
						.getTestRailTestCase("loadtransientthemealerts"));
				LoadTransientTheme.verifyGetIncidentsAPIForTransientThemeForDefaultDays(arg3);
				Log.info("Verified " + arg1 + " " + arg2 + " api Response for days " + arg3);
				Reporter.log("Verified " + arg1 + " " + arg2 + " api Response for days " + arg3);
			}
			break;

		case "zone":
			break;

		}

	}

	@Then("^I verify AIMS config API response$")
	public void verifyAIMSConfigAPI() throws Throwable {
		AIMSConfig.verifyGetAIMSConfigAPIResponse();
		Log.info("Verified successfully the AIMSConfig API");
		Reporter.log("Verified successfully the AIMSConfig API");
	}

	@Then("^I verify the \"([^\"]*)\" theme metaSearch API$")
	public void verifyMetaSearchAPIForAGivenTheme(String theme) {
		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		switch (theme.toLowerCase()) {
		case "acoustic":
			TestRailTestManagement.setTestCase(FileReaderManager.getInstance().getConfigReader()
					.getTestRailTestCase("loadacousticthemestatistics"));
			break;
		case "customer_meter":
			TestRailTestManagement.setTestCase(FileReaderManager.getInstance().getConfigReader()
					.getTestRailTestCase("loadcustomerleaksthemestatistics"));
			break;
		case "transient":
			TestRailTestManagement.setTestCase(FileReaderManager.getInstance().getConfigReader()
					.getTestRailTestCase("loadtransientthemestatistics"));
			break;
		default:
			throw new RuntimeException("Wrong Device name passed");
		}
		ThemesMetaSearch.verifyMetaSearchAPIForAGivenTheme(theme);
		Log.info("Verified the meta/search API for the theme " + theme);
		Reporter.log("Verified the meta/search API for the theme " + theme);
	}

}
