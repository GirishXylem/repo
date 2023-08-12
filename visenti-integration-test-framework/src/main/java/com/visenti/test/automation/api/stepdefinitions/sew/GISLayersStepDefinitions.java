package com.visenti.test.automation.api.stepdefinitions.sew;

import org.testng.Reporter;

import com.visenti.test.automation.api.modules.sew.LoadGISLayers;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import cucumber.api.java.en.Then;

public class GISLayersStepDefinitions {
	
		@Then("^I verify the Loading GIS API of type \"([^\"]*)\"$")
	public void verifyLoadingGISAPIOfAGivenType(String gisType)  {
			TestRailTestManagement.setTestRun(FileReaderManager.getInstance().getConfigReader().getTestRailTestRun("datavalidation"));
			switch (gisType) {
				case "hydrant":
					TestRailTestManagement.setTestCase(
							FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadhydrantgis"));
					break;

				case "valve":
					TestRailTestManagement.setTestCase(
							FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadvalvegis"));
					break;

				case "junction":
					TestRailTestManagement.setTestCase(
							FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadjunctiongis"));
					break;

				case "pipe":
					TestRailTestManagement.setTestCase(
							FileReaderManager.getInstance().getConfigReader().getTestRailTestCase("loadpipegis"));
					break;

				default:
					throw new RuntimeException("Wrong GIS layer name passed");
			}

			LoadGISLayers.verifyLoadingGISLayerOfAGivenType(gisType);
		Log.info("Loading GIS API of type "+gisType +"successfully verified");
		Reporter.log("Loading GIS API of type "+gisType +"successfully verified");
	}


}
