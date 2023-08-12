package com.visenti.test.automation.runners;

import static com.visenti.test.automation.constants.FrameworkConstants.WEB_TEST_EXECUTION_REPORT_FOLDER_PATH;

import org.testng.annotations.BeforeClass;

import com.visenti.test.automation.constants.FrameworkConstants;
import com.visenti.test.automation.helpers.ReportingHelper;

import cucumber.api.CucumberOptions;

@CucumberOptions(
		features = FrameworkConstants.WEBUI_FEATURES,
		glue = { "com.visenti.test.automation.hooks","com.visenti.test.automation.web.stepdefinitions" },
		tags = {"@web","@yw"},
		plugin = { "pretty",
				   "com.cucumber.listener.ExtentCucumberFormatter:",
				   "html:" + WEB_TEST_EXECUTION_REPORT_FOLDER_PATH + "/cucumber-reports/html-report",
				   "json:" + WEB_TEST_EXECUTION_REPORT_FOLDER_PATH + "/cucumber-reports/json-report/report.json"
				 },
		monochrome = true)

public class TestWebRunner extends AbstractBaseRunner {

	@Override
	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		super.setUpClass();
		ReportingHelper.setOutputPathForExtentReport(WEB_TEST_EXECUTION_REPORT_FOLDER_PATH);

	}

}
