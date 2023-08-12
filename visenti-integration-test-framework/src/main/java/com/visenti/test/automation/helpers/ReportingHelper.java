package com.visenti.test.automation.helpers;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import com.cucumber.listener.ExtentProperties;
import com.visenti.test.automation.constants.FrameworkConstants;
import com.visenti.test.automation.utils.SeleniumUtils;
import com.visenti.test.automation.utils.ZipUtils;

import static com.visenti.test.automation.constants.FrameworkConstants.*;
import static com.visenti.test.automation.utils.CommonUtils.*;

public class ReportingHelper {

	private static String executionTypeReportFolderPath;
	private static String extentReportsDateFolder;

	public static String getExecutionTypeReportFolderPath() {
		return executionTypeReportFolderPath;
	}

	public static String getExtentReportsDateFolder() {
		return extentReportsDateFolder;
	}

	public static String generateExtentReportsFileDynamicPath(String executionTypeFolderPath) {
		executionTypeReportFolderPath = executionTypeFolderPath;

		Date date = new Date();
		extentReportsDateFolder = getSystemDateInAGivenFormat(DATE_PATTERN_EXTENT_REPORT_CHILD_FOLDER, date);
		String extentReportsHtmlFilePath = System.getProperty("user.dir") + File.separator
				+ executionTypeReportFolderPath + File.separator + EXTENT_REPORT_PARENT_FOLDER_RELATIVE_PATH
				+ File.separator + extentReportsDateFolder + File.separator + EXTENT_REPORT_FILE_NAME;
		System.out.println("Extent Reports HTML Full File path " + extentReportsHtmlFilePath);
		return extentReportsHtmlFilePath;

	}

	public static void setOutputPathForExtentReport(String executionTypeReportFolderPath) {
		ExtentProperties extentProperties = ExtentProperties.INSTANCE;
		extentProperties.setReportPath(generateExtentReportsFileDynamicPath(executionTypeReportFolderPath));
	}

	/**
	 * @param scenarioName
	 * @return Get FailedScenario Screenshot image full path.
	 */
	public static String generateFailedScenarioScreenshotFileDynamicPath(String scenarioName) {

		// Removing all white spaces
		scenarioName = scenarioName.replaceAll("\\s", "");

		String failedScenarioDynamicFilePath = System.getProperty("user.dir") + File.separator
				+ executionTypeReportFolderPath + File.separator + EXTENT_REPORT_PARENT_FOLDER_RELATIVE_PATH
				+ File.separator + extentReportsDateFolder + File.separator + FAILED_SCENARIO_SCREENSHOT_FOLDER
				+ File.separator + scenarioName + File.separator + "failed." + SCREENSHOT_IMG_FILE_FORMAT;

		return failedScenarioDynamicFilePath;
	}

	/**
	 * @param scenarioName
	 * @return
	 * 
	 * 		We are saving the Failed scenarios in a File and returning the
	 *         fullPath of the file where scenarios are saved
	 */
	public static String captureScreenshotForFailedScenariosAndReturnRelativePath(WebDriver driver,
			String scenarioName) {
		try {
			String failedScenarioFullFilePath = generateFailedScenarioScreenshotFileDynamicPath(scenarioName);
			System.out.println("Failed Scenario Full File Path" + failedScenarioFullFilePath);

			File file = new File(failedScenarioFullFilePath);
			SeleniumUtils.takeScreenshotAndSaveInFile(driver, file);
			String absolutePath = file.getAbsolutePath();
			System.out.println("Absolute path:" + absolutePath);

			int beginningIndex = failedScenarioFullFilePath.indexOf(FAILED_SCENARIO_SCREENSHOT_FOLDER);
			System.out.println(beginningIndex);
			System.out.println(failedScenarioFullFilePath.substring(beginningIndex));
			return failedScenarioFullFilePath.substring(beginningIndex);
		} catch (Exception e) {
			Log.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * @return
	 * @throws IOException This method will create a zipped file of the Generated
	 *                     Report folder(Date folder) and returns the Zipped filePath relative to current directory
	 */
	public static String createZippedFileOfTheGeneratedReportsFolderAndReturnPath() throws IOException {
		String folderToBeZippedPath = executionTypeReportFolderPath + File.separator
				+ EXTENT_REPORT_PARENT_FOLDER_RELATIVE_PATH + File.separator + extentReportsDateFolder;
		String zipFileName = extentReportsDateFolder;
		String destinationFolder=FrameworkConstants.OUTPUT_FOLDER;
		String zipFilePath = ZipUtils.zipAFolderOrFileAndReturnFilePath(folderToBeZippedPath, destinationFolder,zipFileName);
		return zipFilePath;
	}
}
