package com.visenti.test.automation.runners;

import com.visenti.test.automation.constants.FrameworkConstants;
import com.visenti.test.automation.helpers.ReportingHelper;
import cucumber.api.CucumberOptions;
import org.testng.annotations.BeforeClass;

import static com.visenti.test.automation.constants.FrameworkConstants.API_TEST_EXECUTION_REPORT_FOLDER_PATH;

@CucumberOptions(
        features = FrameworkConstants.API_FEATURES,
        glue = {"com.visenti.test.automation.hooks", "com.visenti.test.automation.api.stepdefinitions"},
        tags = {"@api","@yantai"},
        plugin = {"pretty", "com.cucumber.listener.ExtentCucumberFormatter:",
                "html:" + API_TEST_EXECUTION_REPORT_FOLDER_PATH + "/cucumber-reports/html-report",
                "json:" + API_TEST_EXECUTION_REPORT_FOLDER_PATH + "/cucumber-reports/json-report/report.json"},
        monochrome = true)

public class APIRunner_China extends AbstractBaseRunner {

    @Override
    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        super.setUpClass();

        System.out.println("i am yesssssssssssssssssssssssss");

        ReportingHelper.setOutputPathForExtentReport(API_TEST_EXECUTION_REPORT_FOLDER_PATH);
    }
}
