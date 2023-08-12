package com.visenti.test.automation.helpers;

import com.visenti.test.automation.constants.FrameworkConstants;
import com.visenti.test.automation.utils.CommonUtils;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.*;
import java.util.Properties;

import static com.visenti.test.automation.constants.FrameworkConstants.*;

public class ConfigFileReader {
	private Properties properties;
	static Properties prop = new Properties();
	static Properties prop1 = new Properties();
	private static String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
	private static String executionEnvironment = RuntimeConfigSingleton.getInstance().getExecutionEnvironment();
	
	public ConfigFileReader() {
		BufferedReader reader;
		try {

			reader = new BufferedReader(new FileReader(COMMON_CONFIG_PROPERTY_FILE_PATH));
			properties = new Properties();
			Properties applicationEnvProperties = new Properties();
			try {

				properties.load(reader);

				if (executionEnvironment.equalsIgnoreCase("qa") || executionEnvironment.equalsIgnoreCase("test")) {
					applicationEnvProperties
							.load(new BufferedReader(new FileReader(APPLICATION_QA_PROPERTY_FILE_PATH)));
					properties.putAll(applicationEnvProperties);
				} else if (executionEnvironment.contains("dev")) {
					applicationEnvProperties
							.load(new BufferedReader(new FileReader(APPLICATION_DEV_PROPERTY_FILE_PATH)));
					properties.putAll(applicationEnvProperties);
				} else if (executionEnvironment.equalsIgnoreCase("prod")
						|| executionEnvironment.equalsIgnoreCase("production")) {
					applicationEnvProperties
							.load(new BufferedReader(new FileReader(APPLICATION_PROD_PROPERTY_FILE_PATH)));
					properties.putAll(applicationEnvProperties);
				} else {
					throw new RuntimeException("Wrong Execution Environment " + executionEnvironment + " passed");
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("common-config.properties not found at " + COMMON_CONFIG_PROPERTY_FILE_PATH);
		}
	}

	private static void loadConfigDetails() throws IOException {
		/*
		 * File configFile = null; InputStream input;
		 * 
		 * if (null == configFile) configFile = new
		 * File(FrameworkConstants.PROPERTY_FILE_PATH);
		 * 
		 * input = new FileInputStream(configFile); prop.load(input);
		 */
		prop.load(new BufferedReader(new FileReader(FrameworkConstants.COMMON_CONFIG_PROPERTY_FILE_PATH)));
		Properties applicationEnvProp = new Properties();
		if (executionEnvironment.equalsIgnoreCase("qa") || executionEnvironment.equalsIgnoreCase("test")) {
			applicationEnvProp.load(new BufferedReader(new FileReader(APPLICATION_QA_PROPERTY_FILE_PATH)));
			prop.putAll(applicationEnvProp);
		} else if (executionEnvironment.contains("dev")) {
			applicationEnvProp.load(new BufferedReader(new FileReader(APPLICATION_DEV_PROPERTY_FILE_PATH)));
			prop.putAll(applicationEnvProp);
		} else if (executionEnvironment.equalsIgnoreCase("prod")
				|| executionEnvironment.equalsIgnoreCase("production")) {
			applicationEnvProp.load(new BufferedReader(new FileReader(APPLICATION_PROD_PROPERTY_FILE_PATH)));
			prop.putAll(applicationEnvProp);
		} else {
			throw new RuntimeException("Wrong Execution Environment " + executionEnvironment + " passed");
		}

	}

	/**
	 * By Passing the config key it provides the value from
	 * configurations.properties
	 */
	public static String getConfigProperty(String key) {

		if (prop.isEmpty()) {
			try {
				loadConfigDetails();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String keyPrefix = customerName + ".";
		//return prop.getProperty(key) != null ? prop.getProperty(key).trim() : prop.getProperty(keyPrefix + key).trim();
		return prop.getProperty(key) != null ? prop.getProperty(key).trim() : prop.getProperty(keyPrefix + key) !=null ?prop.getProperty(keyPrefix+key).trim():null;
		
	}

	public static void setConfigProperty(String key, String value){
		if (prop.isEmpty()){
			try{
				loadConfigDetails();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		prop.setProperty(key, value) ;
	}

	private static void loadDataStatusConfigDetails() throws IOException {
		File statusConfigFile = null;
		InputStream input;

		if(null == statusConfigFile)
			statusConfigFile = new File(FrameworkConstants.DATA_STATUS_PROPERTY_FILE_PATH);

		input = new FileInputStream(statusConfigFile);
		prop1.load(input);
	}

	public static String getDataStatusConfigProperty(String key) {

		if (prop1.isEmpty()) {
			try {
				loadDataStatusConfigDetails();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return prop1.getProperty(key) != null ? prop1.getProperty(key).trim() : prop1.getProperty("GLOBAL_THRESHOLD").trim();
	}


	/**
	 * @param propertyKey
	 * @param propertyValue
	 * @param storeInEnvProperties
	 * @throws Exception
	 * 
	 * This method stores a property key and value at runtime either to the env properties or common-config.properties
	 * based on the 3rd boolean parameter 'storeInEnvProperties' ,If this is passed as 'true' ,the property Key
	 * and value will be added/updated in the corresponding env properties,based on the value of executionEnvironment
	 * passed ,if 'storeInEnvProperties' boolean is false,the property key and value will be stored and updated in
	 * common-config.properties
	 */
	public static void storePropertyKeyAndValue(String propertyKey, String propertyValue, boolean storeInEnvProperties) throws Exception {

		String filePath;
		if (storeInEnvProperties) {
			if (executionEnvironment.equalsIgnoreCase("dev")) {
				filePath=APPLICATION_DEV_PROPERTY_FILE_PATH;
			} else if (executionEnvironment.equalsIgnoreCase("qa") || executionEnvironment.equalsIgnoreCase("test")) {
				filePath=APPLICATION_QA_PROPERTY_FILE_PATH;
			} else if (executionEnvironment.equalsIgnoreCase("prod")
					|| executionEnvironment.equalsIgnoreCase("production")) {
				filePath=APPLICATION_PROD_PROPERTY_FILE_PATH;
			} else {
				throw new RuntimeException("Wrong Execution Environmnent " + executionEnvironment + " passed");
			}
		}
		else {
			filePath=COMMON_CONFIG_PROPERTY_FILE_PATH;
		}
		
		//Using PropertiesConfiguration class of apache commons-configuration .
		//With this class whenever a new property is added to a '.properties' file the order is maintained 
		
		PropertiesConfiguration propConf=new PropertiesConfiguration(filePath);
		
		if(storeInEnvProperties)
		{
			propertyKey=customerName+"."+propertyKey;
		}
		
		long currentTime=System.currentTimeMillis();
		String timeInFormat =CommonUtils.convertTimeInMillisecondsToADateFormat(currentTime, "MMM-dd-yyyy-HH.mm.ss");
		//Setting the comment for the Property
		propConf.getLayout().setComment(propertyKey, "Property '"+propertyKey+"' added/updated at '"+timeInFormat+"' during execution");
		//propConf.setEncoding("UTF-8");
		propConf.setProperty(propertyKey,propertyValue);
		
		propConf.save();
		//Once the property is saved in .properties file ,calling the loadConfigDetails() method
		//so that the newly added property is loaded into Java Properties object and can be
		//retrieved at runtime using getConfigProperty(String key) method
		loadConfigDetails();
		
			
	}


	/**
	 * Selenium driver configs
	 */
	public String getChromeDriverPath() {
		String driverPath = properties.getProperty("chrome.driver.path").trim();
		String OS = System.getProperty("os.name").toLowerCase();
		if (driverPath != null) {
			if (OS.contains("win")) {
				driverPath = System.getProperty("user.dir") + File.separator + driverPath + ".exe";
			} else {
				driverPath = System.getProperty("user.dir") + File.separator + driverPath;
			}
			return driverPath;
		} else {
			throw new RuntimeException("driverPath not specified in the configuration file");
		}
	}

	public String getFirefoxDriverPath() {
		String driverPath = properties.getProperty("firefox.driver.path").trim();
		String OS = System.getProperty("os.name").toLowerCase();
		if (driverPath != null) {
			if (OS.contains("win")) {
				driverPath = System.getProperty("user.dir") + File.separator + driverPath + ".exe";
			} else {
				driverPath = System.getProperty("user.dir") + File.separator + driverPath;
			}
			return driverPath;
		} else {
			throw new RuntimeException("driverPath not specified in the configuration file");
		}
	}

	/**
	 * Framework related configs
	 */
	public long getImplicitlyWait() {
		String implicitlyWait = properties.getProperty("implicitly.wait").trim();
		if (implicitlyWait != null)
			return Long.parseLong(implicitlyWait);
		else
			throw new RuntimeException("implicitlyWait not specified in the configuration file");
	}

	/**
	 * Testing portal configs
	 */
	/*
	 * public String getTestPortalName() { String portalName =
	 * properties.getProperty("test.portal.name"); if (portalName != null) { return
	 * portalName; } else throw new
	 * RuntimeException("Portal name 'key' not specified in config file");
	 * 
	 * }
	 */

	public String getPortalUrl(String customer) {
		System.out.println("Customer:" + customer);
		String url = properties.getProperty(customer + ".portal");
		if (url != null)
			return url.trim();
		else
			throw new RuntimeException("Portal url not specified in the configuration file");
	}

	public String getPortalUrlValidation(String customer) {
		String validationUrl = properties.getProperty(customer + ".url.validation");
		if (validationUrl != null)
			return validationUrl.trim();
		else
			throw new RuntimeException("Portal validation url not specified in the configuration file");
	}

	/**
	 * Portal url related configs
	 */
	public String getPortalURLPrefix(String portal) {
		// String url = properties.getProperty("com.visenti.test." + portal +
		// ".prefix");
		String url = properties.getProperty(portal + ".prefix");
		if (url != null)
			return url.trim();
		else
			throw new RuntimeException("API url prefix not specified in the configuration file");
	}

	public String getPortalURLDomain(String portal) {
		// String url = properties.getProperty("com.visenti.test." + portal +
		// ".domain");
		String url = properties.getProperty(portal + ".domain");
		if (url != null)
			return url.trim();
		else
			throw new RuntimeException("API url domain not specified in the configuration file");
	}

	public String getUMSPortalURLDomain(String portal) {
		String url = properties.getProperty(portal + ".ums.domain");
		if (url != null)
			return url.trim();
		else
			throw new RuntimeException("API ums domain not specified in the configuration file");
	}

	public String getUMSPortalPrefix(String portal) {
		String umsPrefix = properties.getProperty(portal + ".ums.prefix");
		if (umsPrefix != null)
			return umsPrefix.trim();
		else
			throw new RuntimeException("UMS Portal Prefix is not specified in Configuration file");
	}

	public String getPortalUser() {
		String user = properties.getProperty("com.visenti.test.portal.username");
		if (user != null)
			return user.trim();
		else
			throw new RuntimeException("Portal user not specified in the configuration file");
	}

	/**
	 * API specifics pn URLs
	 */
	public String getAPISpecificURLs(String api) {
		String url = properties.getProperty("api.specific.string." + api);
		if (url != null)
			return url.trim();
		else
			throw new RuntimeException("API url prefix not specified in the configuration file");
	}

	/**
	 * Status update email related Configs
	 */
	public String getEmailHost() {
		String emailHost = properties.getProperty("com.visenti.test.mail.host");
		if (emailHost != null)
			return emailHost.trim();
		else
			throw new RuntimeException("Email host not specified in the configuration file");
	}

	public String getEmailPort() {
		String emailPort = properties.getProperty("com.visenti.test.mail.port");
		if (emailPort != null)
			return emailPort.trim();
		else
			throw new RuntimeException("Email port not specified in the configuration file");
	}

	public String getEmailSender() {
		String emailSender = properties.getProperty("com.visenti.test.mail.sender");
		if (emailSender != null)
			return emailSender.trim();
		else
			throw new RuntimeException("Email sender not specified in the configuration file");
	}

	public String getEmailSenderPW() {
		String emailPW = properties.getProperty("com.visenti.test.mail.pw");
		if (emailPW != null)
			return emailPW.trim();
		else
			throw new RuntimeException("Email senders' password not specified in the configuration file");
	}

	public String getEndResultUpdateEmails() {
		String emailReceiversList = properties.getProperty("com.visenti.test.status.receivers");
		if (emailReceiversList != null)
			return emailReceiversList.trim();
		else
			throw new RuntimeException("End result receivers email list not specified in the configuration file");
	}

	public String getTesRunUpdateEmails() {
		String emailList = properties.getProperty("com.visenti.test.testrun.receivers");
		if (emailList != null)
			return emailList.trim();
		else
			throw new RuntimeException("Test run status receivers email list not specified in the configuration file");
	}

	/**
	 * TestRail configurations
	 */
	public String getTestRailURL() {
		String testrailUrl = properties.getProperty("com.visenti.test.testrail.url");
		if (testrailUrl != null)
			return testrailUrl.trim();
		else
			throw new RuntimeException("Testrail URL not specified in the configuration file");
	}

	public String getTestRailUser() {
		String testrailUser = properties.getProperty("com.visenti.test.testrail.username");
		if (testrailUser != null)
			return testrailUser.trim();
		else
			throw new RuntimeException("Testrail user email not specified in the configuration file");
	}

	public String getTestRailPassword() {
		String testrailPW = properties.getProperty("com.visenti.test.testrail.pw");
		if (testrailPW != null)
			return testrailPW.trim();
		else
			throw new RuntimeException("Testrail user password not specified in the configuration file");
	}

	public int getTestRailTestRun(String run) {
		String testrun = properties.getProperty("com.visenti.test.testrail.testrun." + run);
		if (testrun != null)
			return Integer.parseInt(testrun.trim());
		else
			throw new RuntimeException("TestRail test run not specified in the configuration file");
	}

	public int getTestRailTestCase(String test) {
		String testcase = properties.getProperty("com.visenti.test.testrail.testcase." + test);
		if (testcase != null)
			return Integer.parseInt(testcase.trim());
		else
			throw new RuntimeException("TestRail test case not specified in the configuration file");
	}

	public String getEnvironment() {
		String env = properties.getProperty("com.visenti.environment");
		if (env != null)
			return env.trim();
		else
			throw new RuntimeException("Environment is not specified in the configuration file");
	}

	public Boolean isUpdateTestRail(String test) {
		String updateInTestRail = properties.getProperty("update." + test + ".testrail");
		if (updateInTestRail != null)
			return Boolean.parseBoolean(updateInTestRail.trim());
		// Default value returned is false if the value for the property key
		// update.in.testrail is not provided
		else
			return false;
	}

	public String getAPIHeaderValues(String value) {
		// String xServiceValue =
		// properties.getProperty("com.visenti.test.api.x-service-" + values);
		String xServiceValue = properties.getProperty(customerName+".api.header.x-service-" + value);
		if (xServiceValue != null)
			return xServiceValue.trim();
		else
			throw new RuntimeException("X Service " + value + " not specified in the configuration file");
	}

	public String getCustomerID() {
		String customerID = properties.getProperty("com.visenti.test.api.customerid");
		if (customerID != null)
			return customerID.trim();
		else
			throw new RuntimeException("Customer ID is not specified in the configuration file");
	}

	public String getGISData(String gis) {
		String zone = properties.getProperty("com.visenti.test.gis." + gis);
		if (zone != null)
			return zone.trim();
		else
			throw new RuntimeException("GIS " + zone + " is not specified in the configuration file");
	}

	public String getGISZone() {
		String zone = properties.getProperty("com.visenti.test.gis.zone");
		if (zone != null)
			return zone.trim();
		else
			throw new RuntimeException("GIS zone is not specified in the configuration file");
	}

}
