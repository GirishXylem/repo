package com.visenti.test.automation.api.modules.sew;

import static com.visenti.test.automation.constants.APIConstants.ACOUSTIC_AVERAGE_MIN_NIGHT;
import static com.visenti.test.automation.constants.APIConstants.ACOUSTIC_AVERAGE_NOISE;
import static com.visenti.test.automation.constants.APIConstants.ACOUSTIC_DATE;
import static com.visenti.test.automation.constants.APIConstants.ACOUSTIC_LAST_MIN_NIGHT;
import static com.visenti.test.automation.constants.APIConstants.ACOUSTIC_LATEST_VALUE_OVER_TIME;
import static com.visenti.test.automation.constants.APIConstants.ACOUSTIC_STN_ID;
import static com.visenti.test.automation.constants.APIConstants.ACOUSTIC_THEME_STATISTICS_RESPONSE_COUNT_KEY;
import static com.visenti.test.automation.constants.APIConstants.ACOUSTIC_THEME_STATISTICS_RESPONSE_DATA_KEY;
import static com.visenti.test.automation.constants.APIConstants.ACOUSTIC_WAV_FILE_PATH;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_ANOMALIES_ID_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_ANOMALIES_INFO_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_ANOMALIES_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_ANOMALIES_STATIONS_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_ANOMALY_TYPE_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_ASSIGNED_TO_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_DESCRIPTION_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_GEO_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_INCIDENT_ID_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_INCIDENT_TIME_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_INCIDENT_TYPE_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_LAST_COMMENT_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_RECOMMENDATION_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_REPORTING_TIME_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_SENSOR_CATEGORY_LIST_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_STATE_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_STATION_DISPLAY_NAME_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_STATION_NAME_KEY;
import static com.visenti.test.automation.constants.APIConstants.ALERTS_STATUS_KEY;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LoadAcousticTheme {

	static Long startTime;
	static Long endTime;

	/**
	 * Statistics APIs covered with loading
	 */
	public static void verifyLoadingAcousticTheme(int days) {

		endTime = Instant.now().toEpochMilli();
		startTime = endTime - 24 * 60 * 60 * 1000 * days;

		RestAssured.useRelaxedHTTPSValidation();

		TestRailTestManagement.setAction("Invoke user preferences in acoustic theme load ");
		TestRailTestManagement.setTestComment("Verified user preferences");
		getUserPreferencesForAIMS();

		TestRailTestManagement.setAction("Invoke acoustic incidents in acoustic theme load ");
		TestRailTestManagement.setCompleteTestComment("Verified acoustic incidents");
		loadingAcousticIncidents(days);

		TestRailTestManagement.setAction("Invoke acoustic statistics in acoustic theme load ");
		TestRailTestManagement.setCompleteTestComment("Verified acoustic statistics");
		loadingAcousticStatisticsData();

		TestRailTestManagement.setAction("Invoke acoustic meta data in acoustic theme load ");
		TestRailTestManagement.setCompleteTestComment("Verified acoustic meta data for statistics");
		gettingAcousticStatisticsMetaData();

		TestRailTestManagement.setAction("Invoke acoustic incidents in acoustic theme load ");
		TestRailTestManagement.setCompleteTestComment("Verified acoustic stations related meta data");
		loadingAcousticMetaSearch();

		TestRailTestManagement.setCompleteTestComment("Updated by Automation Framework!");
	}

	/*
	 * public static void verifyLoadingAcousticAlerts() { int days= 7; endTime =
	 * Instant.now().toEpochMilli(); startTime = endTime - 24 * 60 * 60 * 1000 *
	 * days;
	 * 
	 * RestAssured.useRelaxedHTTPSValidation();
	 * 
	 * getUserPreferencesForAIMS(); loadingAcousticIncidents(); getAIMSConfig();
	 * 
	 * }
	 */

	/**
	 * Loading acoustic statistics for given number of days, starting current date§
	 * 
	 * @param days
	 */
	public static void verifyAcousticStatistics(int days) {
		endTime = Instant.now().toEpochMilli();
		startTime = endTime - 24 * 60 * 60 * 1000 * days;

		TestRailTestManagement.setTestComment("Testing Period (in days): " + days + System.lineSeparator()
				+ System.lineSeparator() + " Updated by Automation Framework! ");
		TestRailTestManagement.setAction("Validating Acoustic Statistics API response for default days " + days);

		Response acousticResponse = loadingAcousticStatisticsData();

		// Asserting the Status code
		assertEquals(acousticResponse.getStatusCode(), 200);

		JsonPath jPath = acousticResponse.jsonPath();
		Map<String, Object> responseMap = jPath.getMap("$");
		// Asserting Response Map returned is not empty
		assertTrue(!responseMap.isEmpty());
		Log.info("Asserted Response Map is not empty");

		// Assert having data in the response

		List<Map<String, Object>> responseDataListOfMaps = jPath.getList(ACOUSTIC_THEME_STATISTICS_RESPONSE_DATA_KEY);

		assertTrue(!responseDataListOfMaps.isEmpty());
		Log.info("Asserted  response data List of Maps is not empty");
		Log.info("The size of the data List of Maps =" + responseDataListOfMaps.size());
		// Integer count = acousticResponse.jsonPath().get("count");
		int count = jPath.get(ACOUSTIC_THEME_STATISTICS_RESPONSE_COUNT_KEY);

		int dataListSize = responseDataListOfMaps.size();
		assertEquals(dataListSize, count);

		Log.info("Asserted the data List size is equal to the count key value = " + count);

		assertingKeysInEachDataMapForLoadAcousticStatisticsAPI(responseDataListOfMaps);

	}

	public static Response getUserPreferencesForAIMS() {
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();

		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("aims");

		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/getAIMSUserPreferences";

		ConfigFileReader configFileReader = new ConfigFileReader();
		RequestSpecification httpRequest = RestAssured.given().queryParam("userId", configFileReader.getCustomerID())
				.queryParam("customerId", configFileReader.getCustomerID())
				.header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer")).log().all();
		Response response = httpRequest.when().get();

		assertEquals(response.getStatusCode(), 200);

		// Fetching status_code from response payload key 'status_code'
		Integer jsonResponseStatus = response.jsonPath().get("status_code");
		// 'data' key in the json response is a JsonArray hence returns a list
		List<String> jsonResponseData = response.jsonPath().get("data");
		assertEquals(String.valueOf(jsonResponseStatus), "200");
		assertTrue(!jsonResponseData.isEmpty());

		return response;
	}

	public static void loadingAcousticIncidents(int days) {

		Response response = GetIncidents.getIncidentsAPIForAGivenAnomalyForDefaultDays("acoustic", days);

		assertEquals(response.getStatusCode(), 200);

		Integer jsonResponseStatus = response.jsonPath().get("status_code");
		List<String> jsonResponseData = response.jsonPath().get("data");
		assertEquals(String.valueOf(jsonResponseStatus), "200");
		assertTrue(!jsonResponseData.isEmpty());

	}

	public static Response loadingAcousticStatisticsData() {
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("data");

		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/statistics/acoustic";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);

		ConfigFileReader configFileReader = new ConfigFileReader();
		Header xServiceCust = new Header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer"));
		Header xServiceTicket = new Header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket"));

		Headers headers = new Headers(xServiceCust, xServiceTicket);

		RequestSpecification httpRequest = RestAssured.given().headers(headers).body("{\"request\":{\"st\":" + startTime
				+ ",\"et\":" + endTime
				+ ",\"intervalmin\":15,\"station_ids\":[\"gutr_16\",\"gutr_23\",\"gutr_28\",\"gutr_30\",\"gutr_35\",\"gutr_42\",\"gutr_47\",\"gutr_54\",\"gutr_59\",\"gutr_61\",\"gutr_66\",\"gutr_73\",\"gutr_78\",\"gutr_80\",\"gutr_85\",\"gutr_92\",\"gutr_97\",\"gutr_101\",\"gutr_106\",\"gutr_113\",\"gutr_118\",\"gutr_120\",\"gutr_125\",\"gutr_132\",\"gutr_11\",\"gutr_22\",\"gutr_27\",\"gutr_34\",\"gutr_39\",\"gutr_41\",\"gutr_46\",\"gutr_53\",\"gutr_58\",\"gutr_60\",\"gutr_65\",\"gutr_72\",\"gutr_77\",\"gutr_84\",\"gutr_89\",\"gutr_91\",\"gutr_96\",\"gutr_102\",\"gutr_107\",\"gutr_114\",\"gutr_119\",\"gutr_121\",\"gutr_126\",\"gutr_133\",\"gutr_15\",\"gutr_17\",\"gutr_24\",\"gutr_29\",\"gutr_31\",\"gutr_36\",\"gutr_43\",\"gutr_48\",\"gutr_50\",\"gutr_55\",\"gutr_62\",\"gutr_67\",\"gutr_74\",\"gutr_79\",\"gutr_81\",\"gutr_86\",\"gutr_93\",\"gutr_98\",\"gutr_100\",\"gutr_105\",\"gutr_112\",\"gutr_117\",\"gutr_124\",\"gutr_129\",\"gutr_131\",\"gutr_12\",\"gutr_18\",\"gutr_20\",\"gutr_25\",\"gutr_32\",\"gutr_37\",\"gutr_44\",\"gutr_49\",\"gutr_51\",\"gutr_56\",\"gutr_63\",\"gutr_68\",\"gutr_70\",\"gutr_75\",\"gutr_82\",\"gutr_87\",\"gutr_94\",\"gutr_99\",\"gutr_104\",\"gutr_109\",\"gutr_111\",\"gutr_116\",\"gutr_123\",\"gutr_128\",\"gutr_130\",\"gutr_13\",\"gutr_19\",\"gutr_21\",\"gutr_26\",\"gutr_33\",\"gutr_38\",\"gutr_40\",\"gutr_45\",\"gutr_52\",\"gutr_57\",\"gutr_64\",\"gutr_69\",\"gutr_71\",\"gutr_76\",\"gutr_83\",\"gutr_88\",\"gutr_90\",\"gutr_95\",\"gutr_103\",\"gutr_108\",\"gutr_110\",\"gutr_115\",\"gutr_122\",\"gutr_127\",\"gutr_134\",\"gutr_14\",\"gutr_259\"],\"type\":\"acoustic\"}}\r\n"
				+ "").request().log().all();

		Response response = httpRequest.when().post();

		return response;

	}

	public static Response gettingAcousticStatisticsMetaData() {
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("data");

		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/meta/statistics/acoustic";

		ConfigFileReader configFileReader = new ConfigFileReader();
		Header xServiceCust = new Header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer"));
		Header xServiceTicket = new Header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket"));

		Headers headers = new Headers(xServiceCust, xServiceTicket);

		RequestSpecification httpRequest = RestAssured.given().headers(headers).request().log().all();

		Response response = httpRequest.when().get();
		// Asserting the Status code and Response Payload
		assertEquals(response.getStatusCode(), 200);

		Map<String, Object> jsonResponseData = response.jsonPath().get("data");
		// Asserting the 'data' key in the Response Payload to be not empty
		assertTrue(!jsonResponseData.isEmpty());

		return response;
	}

	public static Response loadingAcousticMetaSearch() {

		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix()
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/dataintelapi/" + PortalConfigManagement.getPortalPrefix() + "/node/meta/search";

		RequestSpecification httpRequest = RestAssured.given().contentType("text/plain").body(
				"query={\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"bool\":{\"must\":[{\"or\":[{\"has_child\":{\"type\":\"sensor_info\",\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"and\":[{\"terms\":{\"sensortype_actual\":[\"hydrophone\",\"acoustic\"]}}]}}}}},{\"has_parent\":{\"type\":\"station_info\",\"query\":{\"filtered\":{\"query\":{\"match_all\":{}}}}}}]}]}}}},\"size\":100000}&collection=station&username=admin")
				.request().log().all();

		Response response = httpRequest.when().post();

		// Asserting the Status code
		assertEquals(response.getStatusCode(), 200);
		String jsonResponseErrorCode = response.jsonPath().get("errorCode");
		Map<String, Object> jsonResponseKey = response.jsonPath().get("response");
		// Asserting the Response Payload keys
		assertEquals(jsonResponseErrorCode, "0");
		assertTrue(!jsonResponseKey.isEmpty());

		return response;
	}

	public static Response getAIMSConfig() {
		RestAssured.baseURI = "https://sewuk-aims-api.eu.cloud.visenti.com";
		RestAssured.basePath = "/getAimsConfig";

		RequestSpecification httpRequest = RestAssured.given().queryParam("customerId", "sewuk")
				.header("X-Service-Customer", "sewuk").log().all();
		Response response = httpRequest.when().get();

		assertEquals(response.getStatusCode(), 200);

		// Fetching status_code from response payload key 'status_code'
		Integer jsonResponseStatus = response.jsonPath().get("status_code");
		// 'data' key in the json response is a JsonArray hence returns a list
		List<String> jsonResponseData = response.jsonPath().get("data");
		assertEquals(String.valueOf(jsonResponseStatus), "200");
		assertTrue(!jsonResponseData.isEmpty());
		return response;
	}

	/*
	 * public static void selectingAcousticTheme(WebDriver driver, String themeName)
	 * {
	 * 
	 * // Providing an explicit wait for the Themes panel button to be clickable
	 * WebElement themesPanelButtonSideMenu = WebCommonUtils
	 * .waitUntilElementClickable(ZoneThemeViewPage.themesPanelButtonSideMenu,
	 * driver, 25L);
	 * 
	 * // Using JavascriptExecutor to click on the Element as we are getting //
	 * ,WebDriverException (Element not clickable) JavascriptExecutor executor =
	 * (JavascriptExecutor) driver; executor.executeScript("arguments[0].click()",
	 * themesPanelButtonSideMenu);
	 * 
	 * Log.info("Clicked on Themes panel side menu button");
	 * 
	 * if (themeName.equalsIgnoreCase("Last Night Min Energy")) {
	 * ThemesPanelPopup.searchTextBox.sendKeys(themeName);
	 * Log.info("Entering the Theme Name " + themeName + " in the Search text box");
	 * ThemesPanelPopup.acousticLastNightMinEnergyCheckbox.click();
	 * Log.info("Clicking on 'Last Night Min Energy' checkbox under Acoustic");
	 * 
	 * }
	 * 
	 * else { Log.error("Wrong Acoustic theme passed"); throw new
	 * RuntimeException("Wrong Acoustic theme passed"); } }
	 */

	public static void verifyAcousticExplorerData(String stationId, String days) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("data");

		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/search/data";

		RestAssured.useRelaxedHTTPSValidation();
		long defaultDays = Long.parseLong(days);
		long endTime = System.currentTimeMillis();
		long startTime = endTime - (24 * 60 * 60 * 1000 * defaultDays);

		TestRailTestManagement
				.setTestComment("Station ID: " + stationId + System.lineSeparator() + "Testing Period (in days):" + days
						+ System.lineSeparator() + System.lineSeparator() + "Updated by Automation Framework! ");
		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction(
				"Validating Acoustic explorer API response for stationId " + stationId + " for default days " + days);

		ConfigFileReader configFileReader = new ConfigFileReader();

		Header xServiceCust = new Header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer"));
		Header xServiceTicket = new Header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket"));
		Headers headers = new Headers(xServiceCust, xServiceTicket);
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.TEXT).headers(headers)
				.body("{\"request\":{\"st\":" + startTime + ",\"et\":" + endTime + ",\"intervalmin\":15,\"include\":[\""
						+ stationId
						+ "\"],\"sensortype\":\"acoustic\",\"timezone\":\"Europe/London\",\"format\":\"csv\",\"hour\":2,\"unit\":\"dB\"}}\r\n"
						+ "")
				.request().log().all();

		Log.info("Created the Request for loading Acoustic Explorer API for stationId " + stationId);

		Log.info("Performing POST request on the API");

		Response response = httpRequest.when().post();

		Log.info("Load Acoustic Explorer API response for" + " StationId " + stationId + "\n" + response.asString());
		// Getting response
		assertEquals(response.getStatusCode(), 200);

		// Asserting jsonResponseData to be non empty
		List<Object> jsonResponseData = response.jsonPath().get("data");
		assertTrue(!jsonResponseData.isEmpty());

		// Asserting jsonResponse csv Data is not empty
		List<List<Object>> jsonResponseCSVData = response.jsonPath().get("data[0].csvData");
		assertNotNull(jsonResponseCSVData);
		assertTrue(!jsonResponseCSVData.isEmpty());
		// Verifying inner list is not empty
		for (List<Object> l1 : jsonResponseCSVData) {
			assertTrue(!l1.isEmpty());
		}

		// Asserting CSVHeader not empty
		List<String> jsonResponseCSVHeader = response.jsonPath().get("data[0].csvHeader");
		assertTrue(!jsonResponseCSVHeader.isEmpty());

		// Asserting metaList is not empty
		List<Object> jsonResponseMetaList = response.jsonPath().get("data[0].metaList");
		assertTrue(!jsonResponseMetaList.isEmpty());

		Log.info("Asserted successfully the Response for Loading Acoustic Explorer API for StationId " + stationId);

	}

	public static Response getIncidentsAPIDataForDefaultDays(long days) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("aims");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/getIncidents";

		RestAssured.useRelaxedHTTPSValidation();

		long endTime = System.currentTimeMillis();
		long startTime = endTime - (24 * 60 * 60 * 1000 * days);

		TestRailTestManagement.setTestComment("Updated by Automation Framework! ");
		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);

		ConfigFileReader configFileReader = new ConfigFileReader();

		RequestSpecification httpRequest = RestAssured.given()
				.header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer"))
				.contentType(ContentType.JSON)
				.body("{\"incidentType\":[\"all\"],\"fromDate\":" + startTime + ",\"toDate\":" + endTime
						+ ",\"rank\":[],\"anomaly\":[\"acoustic\"],\"recommendation\":[],\"status\":[],\"state\":[\"all\"],\"searchByEventTime\":true}")
				.request().log().all();

		Log.info("Created the Request for Acoustic theme getIncidents API for  for " + days + " days");
		Response response = httpRequest.when().post();

		Log.info("Performing POST request on the Acoustic theme getIncidents API ");

		System.out.println(response.asString());

		Log.info("getIncidents API response for Acoustic \n" + response.asString());

		return response;

	}

	public static void verifyGetIncidentsAPIForAcousticThemeForDefaultDays(long days) {

		Response response = getIncidentsAPIDataForDefaultDays(days);
		TestRailTestManagement.setAction("Validating getIncidents API response for Acoustic for default days " + days);
		assertEquals(response.getStatusCode(), 200);
		Log.info("Asserted Status code to be " + response.getStatusCode());

		// Asserting the status_code key
		assertEquals(response.jsonPath().getInt("status_code"), 200);
		Log.info("Asserted the 'status_code' key value to be " + response.jsonPath().getInt("status_code"));

		List<Map<String, Object>> jsonResponseDataList = response.jsonPath().get("data");
		assertTrue(!jsonResponseDataList.isEmpty());
		Log.info("Asserted the Json Response Data List is not empty");
		Log.info("The size of the List =" + jsonResponseDataList.size());

		assertingKeysInResponseForEachRecord(response);

	}

	public static void assertingKeysInResponseForEachRecord(Response response) {

		List<Map<String, Object>> jsonResponseDataList = response.jsonPath().get("data");
		List<String> expectedKeysList = new ArrayList<String>();
		expectedKeysList.add(ALERTS_INCIDENT_TYPE_KEY);
		expectedKeysList.add(ALERTS_ANOMALY_TYPE_KEY);
		expectedKeysList.add(ALERTS_REPORTING_TIME_KEY);
		expectedKeysList.add(ALERTS_INCIDENT_TIME_KEY);
		expectedKeysList.add(ALERTS_DESCRIPTION_KEY);
		expectedKeysList.add(ALERTS_STATUS_KEY);
		expectedKeysList.add(ALERTS_STATE_KEY);
		expectedKeysList.add(ALERTS_ASSIGNED_TO_KEY);
		expectedKeysList.add(ALERTS_LAST_COMMENT_KEY);
		expectedKeysList.add(ALERTS_STATION_NAME_KEY);
		expectedKeysList.add(ALERTS_GEO_KEY);
		expectedKeysList.add(ALERTS_ANOMALIES_KEY);
		expectedKeysList.add(ALERTS_INCIDENT_ID_KEY);
		expectedKeysList.add(ALERTS_RECOMMENDATION_KEY);
		expectedKeysList.add(ALERTS_ANOMALIES_ID_KEY);
		expectedKeysList.add(ALERTS_SENSOR_CATEGORY_LIST_KEY);
		expectedKeysList.add(ALERTS_ANOMALIES_INFO_KEY);
		expectedKeysList.add(ALERTS_ANOMALIES_STATIONS_KEY);
		expectedKeysList.add(ALERTS_STATION_DISPLAY_NAME_KEY);

		int i = 0;
		for (Map<String, Object> recordMap : jsonResponseDataList) {
			for (String key : expectedKeysList) {
				try {
					assertTrue(recordMap.containsKey(key));
					Log.info("The Record Map at index " + i + " contains the key " + key);

				} catch (Throwable t) {
					Log.error("The Record Map at index " + i + " do not contain the key " + key);
					Log.error(t.getMessage());
					fail(t.getMessage());
				}
			}
			i = i + 1;
		}
	}

	public static void assertingKeysInEachDataMapForLoadAcousticStatisticsAPI(List<Map<String, Object>> dataList) {
		List<String> expectedKeysInEachDataMap = new ArrayList<String>();
		expectedKeysInEachDataMap.add(ACOUSTIC_DATE);
		expectedKeysInEachDataMap.add(ACOUSTIC_AVERAGE_NOISE);
		expectedKeysInEachDataMap.add(ACOUSTIC_LATEST_VALUE_OVER_TIME);
		expectedKeysInEachDataMap.add(ACOUSTIC_AVERAGE_MIN_NIGHT);
		expectedKeysInEachDataMap.add(ACOUSTIC_LAST_MIN_NIGHT);
		expectedKeysInEachDataMap.add(ACOUSTIC_STN_ID);
		expectedKeysInEachDataMap.add(ACOUSTIC_WAV_FILE_PATH);

		int i = 0;
		for (Map<String, Object> dataMap : dataList) {
			assertTrue(!dataMap.isEmpty());
			Log.info("Asserted Map at index " + i + " is not empty");
			for (String key : expectedKeysInEachDataMap) {
				try {
					assertTrue(dataMap.containsKey(key));
					Log.info("data map at index " + i + " contains the key '" + key + "'");
				} catch (Throwable t) {
					Log.error("The data map at index " + i + " do not contain the key '" + key + "'");
					Log.error(t.getMessage());
					fail(t.getMessage());
				}
			}
			i = i + 1;
		}
	}

}
