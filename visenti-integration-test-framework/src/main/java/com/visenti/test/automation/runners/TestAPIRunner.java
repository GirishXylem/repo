package com.visenti.test.automation.runners;

import static com.visenti.test.automation.constants.FrameworkConstants.API_TEST_EXECUTION_REPORT_FOLDER_PATH;

import org.testng.annotations.BeforeClass;

import com.visenti.test.automation.constants.FrameworkConstants;
import com.visenti.test.automation.helpers.ReportingHelper;

import cucumber.api.CucumberOptions;

@CucumberOptions(
        features = FrameworkConstants.API_FEATURES,
        glue = {"com.visenti.test.automation.hooks", "com.visenti.test.automation.api.stepdefinitions"},
        tags = {"@api"},
        plugin = {"pretty", "com.cucumber.listener.ExtentCucumberFormatter:",
                "html:" + API_TEST_EXECUTION_REPORT_FOLDER_PATH + "/cucumber-reports/html-report",
                "json:" + API_TEST_EXECUTION_REPORT_FOLDER_PATH + "/cucumber-reports/json-report/report.json"},
        monochrome = true)

public class TestAPIRunner extends AbstractBaseRunner {

    @Override
    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        super.setUpClass();
        ReportingHelper.setOutputPathForExtentReport(API_TEST_EXECUTION_REPORT_FOLDER_PATH);
    }
}
