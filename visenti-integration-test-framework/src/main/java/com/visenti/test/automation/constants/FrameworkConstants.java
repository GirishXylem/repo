package com.visenti.test.automation.constants;

/**
 * This class holds all Framework constants
 *
 */
public class FrameworkConstants {

	private FrameworkConstants() {
		throw new IllegalStateException("Framework Constants Class");
	}

	public static final String RESOURCES_FOLDER = "src/main/resources/";
	public static final String OUTPUT_FOLDER="target/";
	public static final String DOWNLOAD_FOLDER= OUTPUT_FOLDER + "downloads/";
	public static final String CONFIG_FOLDER = RESOURCES_FOLDER + "configs/";
	public static final String PROPERTIES_CONFIG_FOLDER=CONFIG_FOLDER+"property-configs/";
	public static final String XML_CONFIG_FOLDER=CONFIG_FOLDER+"xml-configs/";
	public static final String DRIVERS_FOLDER = RESOURCES_FOLDER + "drivers/";
	public static final String FEATURES_FOLDER = RESOURCES_FOLDER + "features/";
	 public static final String REQUEST_PAYLOADS_PATH=RESOURCES_FOLDER+"request-payloads/";
	//public static final String PROPERTY_FILE_PATH = CONFIG_FOLDER + "configurations.properties";
	public static final String DATA_STATUS_PROPERTY_FILE_PATH = CONFIG_FOLDER + "data-status-threshold.properties";

	public static final String COMMON_CONFIG_PROPERTY_FILE_PATH=PROPERTIES_CONFIG_FOLDER+"common-config.properties";
	public static final String APPLICATION_DEV_PROPERTY_FILE_PATH=PROPERTIES_CONFIG_FOLDER+"application-dev.properties";
	public static final String APPLICATION_QA_PROPERTY_FILE_PATH=PROPERTIES_CONFIG_FOLDER+"application-qa.properties";
	public static final String APPLICATION_PROD_PROPERTY_FILE_PATH=PROPERTIES_CONFIG_FOLDER+"application-prod.properties";
	
	public static final String LOG4J_FILE_PATH =  PROPERTIES_CONFIG_FOLDER + "log4j.properties";
	public static final String REPORT_FILE_PATH =  XML_CONFIG_FOLDER + "extent-config.xml";
   
	
	public static final String CHROME_DRIVER = DRIVERS_FOLDER + "chromedriver";
	public static final String GECKO_DRIVER = DRIVERS_FOLDER + "geckodriver";

	public static final String API_FEATURES = FEATURES_FOLDER + "/APIFeatures";
	public static final String WEBUI_FEATURES = FEATURES_FOLDER + "/WebUIFeatures";
	public static final String WEB_TEST_EXECUTION_REPORT_FOLDER_PATH=OUTPUT_FOLDER+"Reports-Web";
	public static final String API_TEST_EXECUTION_REPORT_FOLDER_PATH=OUTPUT_FOLDER+"Reports-Api";
	public static final String COMMON_TEST_EXECUTION_REPORT_FOLDER_PATH=OUTPUT_FOLDER+"Reports-Common";


	public static final String EXTENT_REPORT_PARENT_FOLDER_RELATIVE_PATH="cucumber-reports/extent-report";
	public static final String DATE_PATTERN_EXTENT_REPORT_CHILD_FOLDER="MMM-dd-yyyy-HH.mm.ss";
	public static final String EXTENT_REPORT_FILE_NAME="report.html";


	public static final String FAILED_SCENARIO_SCREENSHOT_FOLDER="screenshots-fail";
	public static final String SCREENSHOT_IMG_FILE_FORMAT="png";
	public static final String IMG_PNG_MIME_TYPE="image/png";
	public static final String TEXT_HTML_MIME_TYPE="text/html";
	public static final String ZIPPED_FILE_FORMAT="zip";

}
