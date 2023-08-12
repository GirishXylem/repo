package com.visenti.test.automation.helpers;

import com.visenti.test.automation.testrail.APIException;
import com.visenti.test.automation.utils.TestRailUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class TestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult iTestResult) {

	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		if (FileReaderManager.getInstance().getConfigReader().isUpdateTestRail("testcase")) {
			try {
				TestRailUtils.updateTestCaseResultPass(TestRailTestManagement.getTestRun(),
						TestRailTestManagement.getTestCase());

			} catch (IOException e) {
				e.printStackTrace();
			} catch (APIException e) {
				e.printStackTrace();
			}
			System.out.println("*********************This test Passed and Listened");
		}

	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		if (FileReaderManager.getInstance().getConfigReader().isUpdateTestRail("testcase")) {
			try {
				TestRailUtils.updateTestCaseResultFail(TestRailTestManagement.getTestRun(),
						TestRailTestManagement.getTestCase());

				/*EmailSender emailSender = new EmailSender();
				String[] toEmails = emailSender.getReceiversEmailList(
						FileReaderManager.getInstance().getConfigReader().getEndResultUpdateEmails());
				emailSender.setMailServerProperties();
				// emailSender.createErrorEmailMessage("failure", "SEW Prod", toEmails,
				// iTestResult.getThrowable());
				emailSender.sendEmail();*/

			} catch (IOException e) {
				e.printStackTrace();
			} catch (APIException e) {
				e.printStackTrace();

			}
			System.out.println("*******************This test Failed and Listened");
		}

	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		/*
		 * try { TestRailTestManagement testRailTestManagement = new
		 * TestRailTestManagement();
		 * //System.out.println(testRailTestManagement.getTestRun());
		 * UpdateTestRailTest.updateTestCaseResultSkipped(testRailTestManagement.
		 * getTestRun(),testRailTestManagement.getTestCase()); } catch (IOException e) {
		 * e.printStackTrace(); } catch (APIException e) { e.printStackTrace(); }
		 */
		// System.out.println("*******************This test Skipped and Listened");

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

	}

	@Override
	public void onStart(ITestContext iTestContext) {

	}

	@Override
	public void onFinish(ITestContext iTestContext) {

		System.out.println(">>>>>>>>>> " + iTestContext.getName() + " test case finished.........");
	}
}