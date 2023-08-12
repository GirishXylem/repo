package com.visenti.test.automation.hooks;

import static com.visenti.test.automation.constants.FrameworkConstants.IMG_PNG_MIME_TYPE;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.WebDriver;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.helpers.DriverManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.ReportingHelper;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;
import com.visenti.test.automation.utils.SeleniumUtils;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.runtime.ScenarioImpl;
import gherkin.formatter.model.Result;

public class ServiceHooks {
	private WebDriver driver;
	private static String Error;
	public static List<Scenario> failedScenarioList=new ArrayList<Scenario>();
	public static int totalCount=0;

	@Before
	public void initializeTest(Scenario scenario) {
		Log.startLog(scenario.getName());
		Collection<String> tagNames = scenario.getSourceTagNames();
		// Creating WebDriver instance ,for all Web Scenarios and performing initial
		// setup
		
		//Adding scenarios to the List
		
		totalCount++;
				
		if (tagNames.contains("@web") || tagNames.contains("@Web")) {

			driver = DriverManager.getDriver();
			DriverManager.initialDriverSetUp();
		}
		// Setting up the Portal configs after fetching the CustomerName from
		// RuntimeConfigSingleton instance
		else if (tagNames.contains("@api") || tagNames.contains("@Api")) {

			String portalName = RuntimeConfigSingleton.getInstance().getCustomerName();
			System.out.println("Portal name "+portalName);

			PortalConfigManagement.setPortal(portalName);
			PortalConfigManagement.setPortalPrefix(portalName);
			PortalConfigManagement.setPortalDomain(portalName);
			PortalConfigManagement.setUMSPortalDomain(portalName);
			PortalConfigManagement.setUMSPortaPrefix(portalName);
		}
	}

	@After
	public void tearDown(Scenario scenario) {

		if(scenario.isFailed())
		{
			failedScenarioList.add(scenario);
		}
		Collection<String> tagNames = scenario.getSourceTagNames();
		if (tagNames.contains("@web") || tagNames.contains("@Web")) {
			if (scenario.isFailed()) {
				// Attaching Screenshot for Failed Scenarios in Cucumber HTML Report
				byte[] screenshotBytes = SeleniumUtils.getScreenshotAsBytes(driver);
				scenario.embed(screenshotBytes, IMG_PNG_MIME_TYPE);
				// Attaching screenshot for Failed Scenarios in Extent Report
				String scenarioName = scenario.getName();
				String relativeFilePath = ReportingHelper.captureScreenshotForFailedScenariosAndReturnRelativePath(driver,
						scenarioName);
				System.out.println("Relative File path -"+relativeFilePath);
				try {
					//Reporter.addScreenCaptureFromPath("../../../../"+fullFilePath);
					Reporter.addScreenCaptureFromPath(relativeFilePath);
				} catch (IOException e) {
					Log.error("Some issue in attaching screenshot from path:\n" + e);
					e.printStackTrace();
				}
			}
			DriverManager.quitDriver();
		}
		/*if (FileReaderManager.getInstance().getConfigReader().isUpdateTestRail("testscenario")) {
			if (scenario.isFailed()) {

				try {
					String emailBody = logError(scenario);

					TestRailTestManagement
							.setErrorTestComment(System.lineSeparator() + "Error message in Tests : " + Error);

					TestRailUtils.updateTestScenarioResultFail(TestRailTestManagement.getTestRun(),
							TestRailTestManagement.getTestCase(), TestRailTestManagement.getTestComment());
					EmailSender emailSender = new EmailSender();
					String[] toEmails = emailSender.getReceiversEmailList(
							FileReaderManager.getInstance().getConfigReader().getEndResultUpdateEmails());
					emailSender.setMailServerProperties();
					emailSender.createErrorEmailMessageForScenario("failure", "SEW Prod", toEmails, emailBody,
							TestRailTestManagement.getTestComment());
					emailSender.sendEmail();

				} catch (IOException e) {
					e.printStackTrace();
				} catch (APIException e) {
					e.printStackTrace();

				} catch (MessagingException e) {
					e.printStackTrace();
				}
				System.out.println("*******************This test Failed and Listened");

			} else {
				try {

					TestRailUtils.updateTestScenarioResultPass(TestRailTestManagement.getTestRun(),
							TestRailTestManagement.getTestCase(), TestRailTestManagement.getTestComment());

				} catch (IOException e) {
					e.printStackTrace();
				} catch (APIException e) {
					e.printStackTrace();
				}

				System.out.println("*********************This test Passed and Listened");
			}
		}
*/
		Log.endLog(scenario.getName());

	}

	private static String logError(Scenario scenario) {
		String emailBody = "";
		Field field = FieldUtils.getField(((ScenarioImpl) scenario).getClass(), "stepResults", true);
		field.setAccessible(true);
		try {
			ArrayList<Result> results = (ArrayList<Result>) field.get(scenario);
			for (Result result : results) {
				if (result.getError() != null) {
					emailBody = emailBody + " Scenario ID: " + scenario.getId() + System.lineSeparator()
							+ System.lineSeparator() + "Error message: " + result.getErrorMessage();
					System.out.println("Error Scenario: {}" + scenario.getId() + "     " + result.getError());
					Error = result.getErrorMessage();
				}

			}
		} catch (Exception e) {
			// Log.error("Error while logging error", e);
			return "Error while logging error" + e;
		}
		return emailBody;
	}

}
