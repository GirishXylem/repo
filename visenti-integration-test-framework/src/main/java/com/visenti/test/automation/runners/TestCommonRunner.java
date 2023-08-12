package com.visenti.test.automation.runners;

import static com.visenti.test.automation.constants.FrameworkConstants.COMMON_TEST_EXECUTION_REPORT_FOLDER_PATH;

import org.testng.annotations.BeforeClass;

import com.visenti.test.automation.constants.FrameworkConstants;
import com.visenti.test.automation.helpers.ReportingHelper;

import cucumber.api.CucumberOptions;

@CucumberOptions(features = FrameworkConstants.FEATURES_FOLDER, glue = { "com.visenti.test.automation" }, tags = {
		"@api","@uu" }, plugin = { "pretty", "com.cucumber.listener.ExtentCucumberFormatter:",
				"html:" + COMMON_TEST_EXECUTION_REPORT_FOLDER_PATH + "/cucumber-reports/html-report",
				"json:" + COMMON_TEST_EXECUTION_REPORT_FOLDER_PATH
						+ "/cucumber-reports/json-report/report.json" }, monochrome = true)

public class TestCommonRunner extends AbstractBaseRunner {
	
	
	@Override
	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		
		super.setUpClass();
		ReportingHelper.setOutputPathForExtentReport(COMMON_TEST_EXECUTION_REPORT_FOLDER_PATH);
		
	}

}
