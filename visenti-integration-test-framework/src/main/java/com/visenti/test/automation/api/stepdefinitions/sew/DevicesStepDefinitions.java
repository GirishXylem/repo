package com.visenti.test.automation.api.stepdefinitions.sew;

import org.testng.Reporter;

import com.visenti.test.automation.api.modules.sew.LoadDevices;
import com.visenti.test.automation.api.modules.sew.PlotDevices;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class DevicesStepDefinitions {

	@Then("^I verify the Loading Devices API for \"([^\"]*)\"$")
	public void verifyLoadingDevicesAPIForAGivenDevice(String device) {

		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));

		switch (device.toLowerCase()) {
		case "acoustic":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadacousticdevices"));
			break;

		case "btry":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadbtrydevices"));
			break;

		case "customermeter":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadcustomermeterdevices"));
			break;

		case "flow":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadflowdevices"));
			break;

		case "transient":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadtransientdevices"));
			break;

		case "wqy":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadwqydevices"));
			break;

		default:
			throw new RuntimeException("Wrong Device name passed");

		}

		LoadDevices.verifyLoadDevice(device);
		Log.info("Loading API for " + device + " device successfully verified");
		Reporter.log("Loading API for " + device + " device successfully verified");

	}

	@Given("^I verify the Plotting API for below stations and sensorType for default \"([^\"]*)\" days for \"([^\"]*)\" stations$")
	public void verifyPlottingAPIForStationAndSensorTypesForDays(String days, String device, DataTable plotData) {

		TestRailTestManagement
				.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
		switch (device) {
		case "acoustic":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("plotacousticdevices"));
			break;

		case "btry":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("plotbtrydevices"));
			break;

		case "customermeter":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("plotcustomermeterdevices"));
			break;

		case "flow":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("plotflowdevices"));
			break;

		case "transient":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("plottransientdevices"));
			break;

		case "wqy":
			TestRailTestManagement.setTestCase(
					FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("plotwqydevices"));
			break;

		default:
			throw new RuntimeException("Wrong Device name passed");

		}

		PlotDevices.verifyPlottingSensorsForVariousDevices(days, plotData);

	}
}
