package com.visenti.test.automation.api.modules.sew;

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
import static com.visenti.test.automation.constants.APIConstants.RESPONSE_ERRORCODE_0;
import static com.visenti.test.automation.constants.APIConstants.STATUS_CODE_200;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_DELTA_STATS_COVERAGE_OBJECT_ERROR_CODE_KEY;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_DELTA_STATS_COVERAGE_OBJECT_RESPONSE_KEY;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_DELTA_STATS_COVERAGE_RESPONSE_DELTA_COVERAGE_KEY;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_DELTA_STATS_COVERAGE_RESPONSE_DELTA_STATS_KEY;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_DELTA_STATS_LOC_KEY;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_DELTA_STATS_STATION_KEY;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_DELTA_STATS_STATISTICS_KEY;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_DELTA_STATS_STN_ID_KEY;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_DELTA_STATS_SUPPLY_ZONE_KEY;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_DELTA_STATS_UNIT_KEY;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_DELTA_STATS_ZONE_ID_KEY;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_NEAR_BY_LEAKS_OBJECT_ERROR_CODE_KEY;
import static com.visenti.test.automation.constants.APIConstants.TRANSIENT_NEAR_BY_LEAKS_OBJECT_RESPONSE_KEY;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LoadTransientTheme {

	private static final String QUERYPARAM_STN_ID_VALUE = "syri_19,syri_14,syri_21,syri_234,syri_233,syri_15,syri_22,syri_18,syri_20"
			+ ",syri_25,syri_13,syri_235,syri_24,syri_17,syri_12,syri_23,syri_16,syri_11";
	private static final boolean QUERYPARAM_DAILY_STAT_NEEDED_VALUE = true;
	private static final boolean QUERYPARAM_KEEP_2_MAGNITUDE_VALUE = true;
	private static final String QUERYPARAM_ZONE_ID_VALUE = FileReaderManager.getInstance().getConfigReader()
			.getGISZone();
	private static final int QUERYPARAM_DISTANCE_VALUE = 1;
	private static final int COVERAGE_MAP_EXPECTED_SIZE = 15;

	public static void verifyLoadingTransientTheme(int days) {
		getUserPreferencesForAIMS();
		loadingTransientMetaSearch();
		loadingTransientIncidents(days);
		getRangeDeltaStatsAndCoverage();
		getNearbyLeaksByStationIds();
	}

	public static void getUserPreferencesForAIMS() {

		//PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("aims");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/getAIMSUserPreferences";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Invoke AIMS user preference API in Transient theme loading");

		ConfigFileReader configFileReader = new ConfigFileReader();
		RequestSpecification httpRequest = RestAssured.given().queryParam("userId", configFileReader.getCustomerID())
				.queryParam("customerId", configFileReader.getCustomerID())
				.header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer")).log().all();

		Response response = httpRequest.when().get();

		// Asserting response status code to be 200
		assertEquals(response.getStatusCode(), 200);

		// Fetching status_code from response payload key 'status_code'
		Integer jsonResponseStatus = response.jsonPath().get("status_code");
		// 'data' key in the json response is a JsonArray hence returns a list
		List<String> jsonResponseData = response.jsonPath().get("data");
		assertEquals(jsonResponseStatus, Integer.valueOf(200));
		assertFalse(jsonResponseData.isEmpty());

	}

	public static void loadingTransientMetaSearch() {

		//PortalConfigManagement portalConfigs = new PortalConfigManagement();
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/dataintelapi/" + PortalConfigManagement.getPortalPrefix() + "/node/meta/search";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Search meta data in Transient theme loading");

		RequestSpecification httpRequest = RestAssured.given().contentType("text/plain").body(
				"query={\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"bool\":{\"must\":[{\"or\":[{\"has_child\":{\"type\":\"sensor_info\",\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"and\":[{\"terms\":{\"sensortype_actual\":[\"pressure\"]}}]}}}}},{\"has_parent\":{\"type\":\"station_info\",\"query\":{\"filtered\":{\"query\":{\"match_all\":{}}}}}}]}]}}}},\"size\":100000}&collection=station&username=admin")
				.log().all();
		Response response = httpRequest.when().post();
		// Asserting the Status code to be 200
		assertEquals(response.getStatusCode(), 200);

		String jsonResponseErrorCode = response.jsonPath().get("errorCode");
		Map<String, Object> jsonResponseKey = response.jsonPath().get("response");

		// Asserting the Response Payload keys
		assertEquals(jsonResponseErrorCode, "0");

		assertFalse(jsonResponseKey.isEmpty());

	}

	public static void loadingTransientIncidents(int days) {

		Response response = getIncidentsAPIDataForDefaultDays(days);
		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Get transient incidents in Transient theme loading");

		assertEquals(response.getStatusCode(), 200);

		Integer jsonResponseStatus = response.jsonPath().get("status_code");
		List<String> jsonResponseData = response.jsonPath().get("data");

		// Asserting the Response Payload keys

		assertEquals(String.valueOf(jsonResponseStatus), "200");
		assertFalse(jsonResponseData.isEmpty());

	}

	public static void getRangeDeltaStatsAndCoverage() {

		//PortalConfigManagement portalConfigs = new PortalConfigManagement();

		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/dataintelapi/" + PortalConfigManagement.getPortalPrefix()
				+ "/pascalview/getrangedeltastatsmultiwithcoverage";

		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("zoneid", "sewuk");
		queryMap.put("stationid", "syri_19,syri_21,syri_22,syri_25,syri_18,syri_20,syri_17,syri_24,syri_23");
		queryMap.put("starttime", "1559775600000");
		queryMap.put("endtime", "1560380400000");
		queryMap.put("dailystatneeded", "true");
		queryMap.put("keep2Magnitude", "true");

		RequestSpecification httpRequest = RestAssured.given().queryParams(queryMap).log().all();
		Response response = httpRequest.when().get();

		// Asserting the Status code to be 200
		assertEquals(response.getStatusCode(), 200);
		// Getting the errorCode key value
		String jsonResponseErrorCode = response.jsonPath().get("errorCode");
		// Getting the response key Map
		Map<String, Object> jsonResponseKey = response.jsonPath().get("response");
		// The value datatype of response.deltastats is a JSONArray hence get method
		// returns a List
		List<String> jsonDeltaStats = response.jsonPath().get("response.deltastats");
		// The value datatype of response.coverage is a JSONObject hence get method
		// returns a Map
		Map<String, Object> jsonCoverage = response.jsonPath().get("response.coverage");

		// Asserting the Response Payload keys
		assertEquals(jsonResponseErrorCode, "0");
		assertFalse(jsonResponseKey.isEmpty());
		assertFalse(jsonDeltaStats.isEmpty());
		assertFalse(jsonCoverage.isEmpty());

	}

	public static void getNearbyLeaksByStationIds() {

		//PortalConfigManagement portalConfigs = new PortalConfigManagement();
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/dataintelapi/" + PortalConfigManagement.getPortalPrefix()
				+ "/pascalview/getnearbyleaksbystationids";

		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("zone", "sewuk");
		queryMap.put("stationids", "syri_19,syri_21,syri_22,syri_25,syri_18,syri_20,syri_17,syri_24,syri_23");
		queryMap.put("distance", "1");
		queryMap.put("starttime", "1559775600000");
		queryMap.put("endtime", "1560380400000");

		RequestSpecification httpRequest = RestAssured.given().queryParams(queryMap).log().all();
		Response response = httpRequest.when().get();
		// Asserting the Status code to be 200
		assertEquals(response.getStatusCode(), 200);

		// Getting the errorCode key value
		String jsonResponseErrorCode = response.jsonPath().get("errorCode");
		// Getting the response key Map
		Map<String, Object> jsonResponseKey = response.jsonPath().get("response");
		// Asserting the Response Payload keys
		assertEquals(jsonResponseErrorCode, "0");
		assertFalse(jsonResponseKey.isEmpty());

	}

	public static Response getIncidentsAPIDataForDefaultDays(long days) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		//PortalConfigManagement portalConfigs = new PortalConfigManagement();
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
						+ ",\"rank\":[],\"anomaly\":[\"transient\"],\"recommendation\":[],\"status\":[],\"state\":[\"all\"],\"searchByEventTime\":true}")
				.request().log().all();

		Log.info("Created the Request for Transient theme getIncidents API for  for " + days + " days");
		Response response = httpRequest.when().post();

		Log.info("Performing POST request on the Transient theme getIncidents API ");

		System.out.println(response.asString());

		Log.info("getIncidents API response for Transient \n" + response.asString());

		return response;

	}

	public static void verifyGetIncidentsAPIForTransientThemeForDefaultDays(long days) {

		Response response = getIncidentsAPIDataForDefaultDays(days);

		TestRailTestManagement.setAction("Validating getIncidents API response for Transient for default days " + days);
		assertEquals(response.getStatusCode(), 200);
		Log.info("Asserted Status code to be " + response.getStatusCode());

		// Asserting the status_code key
		assertEquals(response.jsonPath().getInt("status_code"), 200);
		Log.info("Asserted the 'status_code' key value to be " + response.jsonPath().getInt("status_code"));

		List<Map<String, Object>> jsonResponseDataList = response.jsonPath().get("data");
		assertFalse(jsonResponseDataList.isEmpty());
		Log.info("Asserted the Json Response Data List is not empty");
		Log.info("The size of the List =" + jsonResponseDataList.size());

		assertingKeysInResponseForEachRecord(response);

	}

	public static void assertingKeysInResponseForEachRecord(Response response) {

		List<Map<String, Object>> jsonResponseDataList = response.jsonPath().get("data");
		List<String> expectedKeysList = new ArrayList<>();
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

	public static Response getRangedDeltaStatsAndCoverageAPIResponse(String days) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		//PortalConfigManagement portalConfigs = new PortalConfigManagement();
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "dataintelapi/{customer}/pascalview/getrangedeltastatsmultiwithcoverage";

		RestAssured.useRelaxedHTTPSValidation();

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement
				.setAction("Loading getRangedDeltaStatsAndCoverage API response for default " + days + " days");

		long defaultDays = Long.parseLong(days);
		long endTime = System.currentTimeMillis();
		long startTime = endTime - (24 * 60 * 60 * 1000 * defaultDays);

		TestRailTestManagement.setTestComment("Testing Period (in days): " + days + System.lineSeparator()
				+ System.lineSeparator() + "Updated by Automation Framework! ");

		String customer = FileReaderManager.getInstance().getConfigReader().getCustomerID();

		Map<String, Object> queryMap = new HashMap<>();

		queryMap.put("zoneid", QUERYPARAM_ZONE_ID_VALUE);
		queryMap.put("stationid", QUERYPARAM_STN_ID_VALUE);
		queryMap.put("starttime", startTime);
		queryMap.put("endtime", endTime);
		queryMap.put("dailystatneeded", QUERYPARAM_DAILY_STAT_NEEDED_VALUE);
		queryMap.put("keep2Magnitude", QUERYPARAM_KEEP_2_MAGNITUDE_VALUE);

		RequestSpecification httpRequest = RestAssured.given().pathParam("customer", customer).queryParams(queryMap)
				.log().all();
		Log.info("Created the Request for getRangedDeltaStatsAndCoverage API for " + days + " days");

		Response response = httpRequest.when().get();
		Log.info("Performing GET request on the getRangedDeltaStatsAndCoverage API ");
		Log.info(" getRangedDeltaStatsAndCoverage API Response" + "\n" + response.asString());
		return response;

	}

	public static void verifyGetRangedDeltaStatsAndCoverageAPIForDefaultDays(String days) {
		Response response = getRangedDeltaStatsAndCoverageAPIResponse(days);

		TestRailTestManagement.setAction("Validating the status code to be " + STATUS_CODE_200);
		assertEquals(response.getStatusCode(), STATUS_CODE_200);

		Log.info("Asserted Status code for getRangedDeltaStatsAndCoverage api  to be " + response.getStatusCode());

		JsonPath jPath = response.jsonPath();

		Map<String, Object> objectMap = jPath.getMap("$");
		// Asserting object Map returned is not empty
		TestRailTestManagement.setAction("Validating the object map to be non empty");
		assertFalse(objectMap.isEmpty());
		Log.info("Asserted object Map is not empty ");

		TestRailTestManagement.setAction("Validating keys within 'object' map ");
		// Asserting keys in Response object map

		assertingKeysInObjectMapForDeltaStatsAndCoverageAPI(objectMap);

		// Asserting the error code to be 0

		TestRailTestManagement.setAction(
				"Validating '" + TRANSIENT_DELTA_STATS_COVERAGE_OBJECT_ERROR_CODE_KEY + "' key ,value: as expected");
		assertEquals(objectMap.get(TRANSIENT_DELTA_STATS_COVERAGE_OBJECT_ERROR_CODE_KEY), RESPONSE_ERRORCODE_0);
		Log.info("Asserted the '" + TRANSIENT_DELTA_STATS_COVERAGE_OBJECT_ERROR_CODE_KEY + "' value to be:"
				+ RESPONSE_ERRORCODE_0);

		// Getting the response key Map
		@SuppressWarnings("unchecked")
		Map<String, Object> responseMap = (Map<String, Object>) objectMap
				.get(TRANSIENT_DELTA_STATS_COVERAGE_OBJECT_RESPONSE_KEY);
		TestRailTestManagement.setAction("Validating the response Map is not empty ");
		assertFalse(responseMap.isEmpty());
		Log.info("Asserted the '" + TRANSIENT_DELTA_STATS_COVERAGE_OBJECT_RESPONSE_KEY + "' Map is not empty");

		// Validating the keys within the response Map

		TestRailTestManagement.setAction("Validating the keys within 'response' map");
		assertingKeysInResponseMapForDeltaStatsCoverageAPI(responseMap);
		// Getting the deltastats list

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> deltaStatsList = (List<Map<String, Object>>) responseMap
				.get(TRANSIENT_DELTA_STATS_COVERAGE_RESPONSE_DELTA_STATS_KEY);
		TestRailTestManagement.setAction("Validating response.deltastats list is not empty");
		assertFalse(deltaStatsList.isEmpty());
		Log.info("Asserted the response.deltastats list is not empty");
		Log.info("The size of the response.deltastats list =" + deltaStatsList.size());

		// Asserting the common keys in each deltastats map as some map have 14 keys
		// ,some have 7 so here
		// asserting only common keys
		TestRailTestManagement.setAction("Validating keys within each deltastats map in the List");
		assertingKeysInEachDeltaStatsMapForDeltaStatsCoverageAPI(deltaStatsList);
		// Asserting the coverage map is not empty
		TestRailTestManagement.setAction("Validating the response.coverage map is not empty");
		@SuppressWarnings("unchecked")
		Map<String, Object> coverageMap = (Map<String, Object>) responseMap
				.get(TRANSIENT_DELTA_STATS_COVERAGE_RESPONSE_DELTA_COVERAGE_KEY);
		assertFalse(coverageMap.isEmpty());
		Log.info("Asserted the response.coverage map is not empty");

		// Asserting the size of coverage map to be equal to expected size
		TestRailTestManagement.setAction("Validating the size of response.coverage map");
		assertEquals(coverageMap.size(), COVERAGE_MAP_EXPECTED_SIZE);
		Log.info(
				"Asserted the actual coverage map size to be equal to the expected size=" + COVERAGE_MAP_EXPECTED_SIZE);

		// Asserting the keys(stn ids) in coverage map present in deltastats list
		// Each deltastats map has a key 'station' ,value of this key is the stn id
		// We will get all the 'station' values from each 'deltastats' map and assert
		// coverage map key(stnId) to be present in
		// station values List

		TestRailTestManagement.setAction("Validating the coverage map keys present in deltastats List");
		assertingCoverageMapKeysInDeltaStatsListForDeltaStatsCoverageAPI(coverageMap, jPath);

	}

	private static void assertingKeysInObjectMapForDeltaStatsAndCoverageAPI(Map<String, Object> objectMap) {
		List<String> expectedKeys = new ArrayList<>();
		expectedKeys.add(TRANSIENT_DELTA_STATS_COVERAGE_OBJECT_ERROR_CODE_KEY);
		expectedKeys.add(TRANSIENT_DELTA_STATS_COVERAGE_OBJECT_RESPONSE_KEY);

		for (String key : expectedKeys) {
			try {
				assertTrue(objectMap.containsKey(key));
				Log.info("The object map contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The object map do not contain the key '" + key + "'");
				Log.error(t.getMessage());
				TestRailTestManagement.setAction("Error -The object map do not contain the key '" + key + "' ");
				fail(t.getMessage());
			}
		}

	}

	private static void assertingKeysInResponseMapForDeltaStatsCoverageAPI(Map<String, Object> responseMap) {
		List<String> expectedKeys = new ArrayList<>();
		expectedKeys.add(TRANSIENT_DELTA_STATS_COVERAGE_RESPONSE_DELTA_STATS_KEY);
		expectedKeys.add(TRANSIENT_DELTA_STATS_COVERAGE_RESPONSE_DELTA_COVERAGE_KEY);

		for (String key : expectedKeys) {
			try {
				assertTrue(responseMap.containsKey(key));
				Log.info("The response map contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The response map do not contain the key '" + key + "'");
				Log.error(t.getMessage());
				TestRailTestManagement.setAction("Error -The response map do not contain the key '" + key + "' ");
				fail(t.getMessage());
			}
		}

	}

	private static void assertingKeysInEachDeltaStatsMapForDeltaStatsCoverageAPI(
			List<Map<String, Object>> deltaStatsList) {
		List<String> expectedKeys = new ArrayList<>();
		expectedKeys.add(TRANSIENT_DELTA_STATS_STN_ID_KEY);
		expectedKeys.add(TRANSIENT_DELTA_STATS_ZONE_ID_KEY);
		expectedKeys.add(TRANSIENT_DELTA_STATS_STATISTICS_KEY);
		expectedKeys.add(TRANSIENT_DELTA_STATS_UNIT_KEY);
		expectedKeys.add(TRANSIENT_DELTA_STATS_SUPPLY_ZONE_KEY);
		expectedKeys.add(TRANSIENT_DELTA_STATS_LOC_KEY);
		expectedKeys.add(TRANSIENT_DELTA_STATS_STATION_KEY);

		int i = 0;
		for (Map<String, Object> deltaStatsMap : deltaStatsList) {
			// Asserting each deltastats map is not empty
			assertFalse(deltaStatsMap.isEmpty());
			Log.info("deltastats Map at index " + i + " is not empty ");

			for (String key : expectedKeys) {
				try {
					assertTrue(deltaStatsMap.containsKey(key));
					Log.info("The deltastats map,at index " + i + " contains the key '" + key + "' ");
				} catch (Throwable t) {
					Log.error("The deltastats map map,at index " + i + " do not contain the key '" + key + "' ");
					Log.error(t.getMessage());
					TestRailTestManagement
							.setAction("The deltastats map,at index " + i + " do not contain the key '" + key + "' ");
					fail(t.getMessage());
				}

			}
			i++;

		}
	}

	private static void assertingCoverageMapKeysInDeltaStatsListForDeltaStatsCoverageAPI(
			Map<String, Object> coverageMap, JsonPath jPath) {
		List<String> coverageMapKeys = new ArrayList<>(coverageMap.keySet());
		// Getting each deltastats map station key value as a List

		List<String> stationValueFromDeltaStats = jPath.getList("response.deltastats.station");

		for (String stnID : coverageMapKeys) {

			try {
				assertTrue(stationValueFromDeltaStats.contains(stnID));
				Log.info("The deltastats station value List contains the coverage map station Id " + stnID);
			} catch (Throwable t) {
				Log.error("The deltastats station value List do not contain the coverage map station Id " + stnID);
				Log.error(t.getMessage());
				TestRailTestManagement.setAction(
						"The deltastats station value List do not contain the coverage map station Id " + stnID);
				fail(t.getMessage());
			}

		}

	}

	public static Response getNearByLeaksByStationIdsAPIResponse(String days) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		//PortalConfigManagement portalConfigs = new PortalConfigManagement();
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "dataintelapi/{customer}/pascalview/getnearbyleaksbystationids";

		RestAssured.useRelaxedHTTPSValidation();

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement
				.setAction("Loading getNearByLeaksByStationIds API response for default " + days + " days");

		long defaultDays = Long.parseLong(days);
		long endTime = System.currentTimeMillis();
		long startTime = endTime - (24 * 60 * 60 * 1000 * defaultDays);

		TestRailTestManagement.setTestComment("Testing Period (in days) " + days + System.lineSeparator()
				+ System.lineSeparator() + "Updated by Automation Framework! ");

		String customer = FileReaderManager.getInstance().getConfigReader().getCustomerID();

		Map<String, Object> queryMap = new HashMap<>();

		queryMap.put("zone", QUERYPARAM_ZONE_ID_VALUE);
		queryMap.put("stationids", QUERYPARAM_STN_ID_VALUE);
		queryMap.put("starttime", startTime);
		queryMap.put("endtime", endTime);
		queryMap.put("distance", QUERYPARAM_DISTANCE_VALUE);

		RequestSpecification httpRequest = RestAssured.given().pathParam("customer", customer).queryParams(queryMap)
				.log().all();
		Log.info("Created the Request for getNearByLeaksByStationIds API for " + days + " days");

		Response response = httpRequest.when().get();
		Log.info("Performing GET request on the getNearByLeaksByStationIds API ");
		Log.info(" getNearByLeaksByStationIds API Response" + "\n" + response.asString());
		return response;

	}

	public static void verifyGetNearByLeaksByStationIdsAPIForDefaultDays(String days) {
		Response response = getNearByLeaksByStationIdsAPIResponse(days);

		TestRailTestManagement.setAction("Validating the status code to be " + STATUS_CODE_200);
		assertEquals(response.getStatusCode(), STATUS_CODE_200);

		Log.info("Asserted Status code for getNearByLeaksByStationIds api  to be " + response.getStatusCode());

		JsonPath jPath = response.jsonPath();

		Map<String, Object> objectMap = jPath.getMap("$");

		// Asserting object Map returned is not empty
		TestRailTestManagement.setAction("Validating the object map to be non empty");
		assertFalse(objectMap.isEmpty());
		Log.info("Asserted object Map is not empty ");

		TestRailTestManagement.setAction("Validating keys within 'object' map ");
		// Asserting keys in Response object map
		assertingKeysInObjectMapForGetNearByLeaksAPI(objectMap);

		// Asserting the error code to be 0

		TestRailTestManagement.setAction(
				"Validating '" + TRANSIENT_NEAR_BY_LEAKS_OBJECT_ERROR_CODE_KEY + "' key ,value: as expected");
		assertEquals(objectMap.get(TRANSIENT_NEAR_BY_LEAKS_OBJECT_ERROR_CODE_KEY), RESPONSE_ERRORCODE_0);
		Log.info("Asserted the '" + TRANSIENT_NEAR_BY_LEAKS_OBJECT_ERROR_CODE_KEY + "' value to be:"
				+ RESPONSE_ERRORCODE_0);

		// Getting the response key Map
		@SuppressWarnings("unchecked")
		Map<String, Object> responseMap = (Map<String, Object>) objectMap
				.get(TRANSIENT_NEAR_BY_LEAKS_OBJECT_RESPONSE_KEY);
		TestRailTestManagement.setAction("Validating the response Map is not empty ");
		assertFalse(responseMap.isEmpty());
		Log.info("Asserted the '" + TRANSIENT_NEAR_BY_LEAKS_OBJECT_RESPONSE_KEY + "' Map is not empty");

		List<String> stationIds = gettingStationIdsFromQueryStringAsList();
		// Asserting the size of response Map same as the station ids

		TestRailTestManagement.setAction("Validating the size of Response map same as the station ids in QueryParam");

		assertEquals(responseMap.size(), stationIds.size());
		Log.info("Successfully asserted the size of response Map same as total station ids in QueryParam= "
				+ responseMap.size());

		// Asserting the keys in Response Map same as the stationIds in queryParam
		// String

		TestRailTestManagement
				.setAction("Validating the keys with response map same as station id in queryParam String");
		assertingKeysInResponseMapForGetNearByLeaksAPI(responseMap);

	}

	private static void assertingKeysInObjectMapForGetNearByLeaksAPI(Map<String, Object> objectMap) {
		List<String> expectedKeys = new ArrayList<>();
		expectedKeys.add(TRANSIENT_NEAR_BY_LEAKS_OBJECT_ERROR_CODE_KEY);
		expectedKeys.add(TRANSIENT_NEAR_BY_LEAKS_OBJECT_RESPONSE_KEY);

		for (String key : expectedKeys) {
			try {
				assertTrue(objectMap.containsKey(key));
				Log.info("The object map contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The object map do not contain the key '" + key + "'");
				Log.error(t.getMessage());
				TestRailTestManagement.setAction("Error -The object map do not contain the key '" + key + "' ");
				fail(t.getMessage());
			}
		}
	}

	private static List<String> gettingStationIdsFromQueryStringAsList() {
		String[] stnIds = QUERYPARAM_STN_ID_VALUE.split(",");

		return Arrays.asList(stnIds);
	}

	private static void assertingKeysInResponseMapForGetNearByLeaksAPI(Map<String, Object> responseMap) {
		// Expected keys would be all the station ids as defined in QueryParam String
		List<String> expectedKeys = gettingStationIdsFromQueryStringAsList();

		for (String key : expectedKeys) {

			try {
				assertTrue(responseMap.containsKey(key));
				Log.info("The response map contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The response map do not contain the key '" + key + "'");
				Log.error(t.getMessage());
				TestRailTestManagement.setAction("Error -The response map do not contain the key '" + key + "' ");
				fail(t.getMessage());
			}

		}

	}
}
