package com.visenti.test.automation.runners;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.constants.FrameworkConstants;
import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.EmailSender;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;
import com.visenti.test.automation.hooks.ServiceHooks;
import com.visenti.test.automation.utils.ZipUtils;

import cucumber.api.testng.AbstractTestNGCucumberTests;

public abstract class AbstractBaseRunner extends AbstractTestNGCucumberTests {

	@Override
	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		super.setUpClass();
		Log.configureLoggerToReadFromLog4JProperties();

	}

	@Override
	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		Reporter.loadXMLConfig(new File(FrameworkConstants.REPORT_FILE_PATH));
		Reporter.setSystemInfo("Customer", RuntimeConfigSingleton.getInstance().getCustomerName());
		Reporter.setSystemInfo("Execution Environment", RuntimeConfigSingleton.getInstance().getExecutionEnvironment());
		Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
		Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
		Reporter.setSystemInfo("Machine", System.getProperty("os.name"));
		Reporter.setSystemInfo("Java Version", System.getProperty("java.version"));
		super.tearDownClass();
		Log.info("Failed Scenarios count -" + ServiceHooks.failedScenarioList.size());
		if (!ServiceHooks.failedScenarioList.isEmpty()
				&& ConfigFileReader.getConfigProperty("mail.trigger.after.execution").equalsIgnoreCase("yes")) {
			EmailSender.attachZipFileAndSendMail();
			ZipUtils.cleanUpZipFilesInAFolder(FrameworkConstants.OUTPUT_FOLDER);
		}

	}

}
