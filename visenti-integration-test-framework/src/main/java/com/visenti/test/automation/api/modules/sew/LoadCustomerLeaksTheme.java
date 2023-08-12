package com.visenti.test.automation.api.modules.sew;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.ArrayList;
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
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LoadCustomerLeaksTheme {

	private static final String STATISTICS_QUERYPARAM_ANOMALY_VALUE = "[%22customer_meter%22]";
	private static final String STATISTICS_QUERYPARAM_INCIDENT_TYPE_VALUE = "[%22customer_leak_drip%22,%22customer_leak_major%22]";
	private static final String TRENDS_QUERYPARAM_CATEGORY_VALUE = "customer_meter";

	public static void verifyLoadingCustomerLeaksTheme(int days) {

		TestRailTestManagement.setTestComment("Updated by Automation Framework!");

		TestRailTestManagement.setAction("Invoke user preferences in customer leaks theme  ");
		TestRailTestManagement.setTestComment("Verified user preferences");
		// Add API validation

		TestRailTestManagement.setAction("Invoke customer leaks incidents in acoustic theme load ");
		TestRailTestManagement.setCompleteTestComment("Verified customer leaks incidents");
		loadingCustomerLeaksIncidents(days);

		TestRailTestManagement.setAction("Invoke customer leaks statistics in theme load ");
		TestRailTestManagement.setCompleteTestComment("Verified customer leaks statistics");
		verifyCustomerLeaksStatisticsResponse(days);

		TestRailTestManagement.setAction("Invoke customer leaks meta data in theme load ");
		TestRailTestManagement.setCompleteTestComment("Verified customer meter stations related meta data");
		loadingCustomerLeaksMetaSearch();

		TestRailTestManagement.setCompleteTestComment("Updated by Automation Framework!");
	}

	public static void verifyCustomerLeaksStatisticsResponse(long days) {

		Response response = getCustomerLeaksStatisticsResponseForDefaultDays(days);
		assertEquals(response.getStatusCode(), 200);
		Log.info("Asserted Status code to be " + response.getStatusCode());

		TestRailTestManagement
				.setAction("Customer leaks theme Statistics - Asserting jsonResponseData to be non empty! ");
		// Asserting jsonResponseData to be non empty
		List<Map<String, Object>> jsonResponseData = response.jsonPath().get("data");
		assertTrue(!jsonResponseData.isEmpty());

		TestRailTestManagement
				.setAction("Customer leaks theme Statistics - Verifying each Map in the List is not empty! ");
		// verifying each Map in the List is not empty
		for (Map<String, Object> map : jsonResponseData) {
			assertTrue(!map.isEmpty());
		}
		Log.info("Asserted each map inside the Data List is not empty");

		TestRailTestManagement
				.setAction("Customer leaks theme Statistics - Verifying  response data having all expected statistics");
		// Asserting the count key equal to total elements in the jsonResponseDataList
		assertEquals(response.jsonPath().getInt("count"), jsonResponseData.size());
		Log.info("Asserted that the count key matches the jsonResponseData List size ="
				+ response.jsonPath().getInt("count"));
		assertingKeysInStatisticsJsonResponse(response);

	}

	public static void loadingCustomerLeaksMetaSearch() {

		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix()
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/dataintelapi/" + PortalConfigManagement.getPortalPrefix() + "/node/meta/search";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Search meta data in Transient theme loading");

		RequestSpecification httpRequest = RestAssured.given().contentType("text/plain").body(
				"query={\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"bool\":{\"must\":[{\"or\":[{\"has_child\":{\"type\":\"sensor_info\",\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"and\":[{\"terms\":{\"sensortype_actual\":[\"consumption\",\"reading\"]}}]}}}}},{\"has_parent\":{\"type\":\"station_info\",\"query\":{\"filtered\":{\"query\":{\"match_all\":{}}}}}}]}]}}}},\"size\":100000}&collection=station&username=admin")
				.log().all();
		Response response = httpRequest.when().post();
		// Asserting the Status code to be 200
		assertEquals(response.getStatusCode(), 200);

		String jsonResponseErrorCode = response.jsonPath().get("errorCode");
		// Asserting the Response Payload keys
		assertEquals(jsonResponseErrorCode, "0");

		Map<String, Object> jsonResponseKey = response.jsonPath().get("response");
		assertTrue(!jsonResponseKey.isEmpty());

	}

	public static void loadingCustomerLeaksIncidents(int days) {

		Response response = GetIncidents.getIncidentsAPIForAGivenAnomalyForDefaultDays("acoustic", days);

		assertEquals(response.getStatusCode(), 200);

		Integer jsonResponseStatus = response.jsonPath().get("status_code");
		List<String> jsonResponseData = response.jsonPath().get("data");
		assertEquals(String.valueOf(jsonResponseStatus), "200");
		assertTrue(!jsonResponseData.isEmpty());

	}

	public static Response getCustomerLeaksStatisticsResponseForDefaultDays(long days) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("data");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/statistics/meterLeaks";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Get Customer Leaks theme statistics for " + days + " days");

		RestAssured.useRelaxedHTTPSValidation();
		// Since the queryParams Anomaly and Incident Type ,already URL encoded hence
		// disabling urlEncoding to prevent double encoding
		RestAssured.urlEncodingEnabled = false;

		long endTime = System.currentTimeMillis();
		long startTime = endTime - (24 * 60 * 60 * 1000 * days);

		Map<String, Object> queryParamsMap = new HashMap<String, Object>();
		queryParamsMap.put("fromDate", startTime);
		queryParamsMap.put("toDate", endTime);
		queryParamsMap.put("anomaly", STATISTICS_QUERYPARAM_ANOMALY_VALUE);
		queryParamsMap.put("incidentType", STATISTICS_QUERYPARAM_INCIDENT_TYPE_VALUE);

		RequestSpecification httpRequest = RestAssured.given().queryParams(queryParamsMap).request().log().all();
		Log.info("Created the Request for Customer Leaks Statistics API for " + days + " days");

		Response response = httpRequest.when().get();
		Log.info("Performing GET request on the Statistics Data API for Customer Leaks");
		System.out.println(response.asString());
		Log.info("Statistics Data API response for " + days + " days \n" + response.asString());
		return response;

	}

	public static void verifyCustomerLeaksStatisticsResponseForDefaultDays(long days) {
		TestRailTestManagement.setTestComment("Updated by Automation Framework!");
		Response response = getCustomerLeaksStatisticsResponseForDefaultDays(days);
		assertEquals(response.getStatusCode(), 200);
		Log.info("Asserted Status code to be " + response.getStatusCode());

		TestRailTestManagement
				.setAction("Customer leaks theme Statistics - Asserting jsonResponseData to be non empty! ");
		// Asserting jsonResponseData to be non empty
		List<Map<String, Object>> jsonResponseData = response.jsonPath().get("data");
		assertTrue(!jsonResponseData.isEmpty());

		TestRailTestManagement
				.setAction("Customer leaks theme Statistics - Verifying each Map in the List is not empty! ");
		// verifying each Map in the List is not empty
		for (Map<String, Object> map : jsonResponseData) {
			assertTrue(!map.isEmpty());
		}
		Log.info("Asserted each map inside the Data List is not empty");

		TestRailTestManagement
				.setAction("Customer leaks theme Statistics - Verifying  response data having all expected statistics");
		// Asserting the count key equal to total elements in the jsonResponseDataList
		assertEquals(response.jsonPath().getInt("count"), jsonResponseData.size());
		Log.info("Asserted that the count key matches the jsonResponseData List size ="
				+ response.jsonPath().getInt("count"));
		assertingKeysInStatisticsJsonResponse(response);

	}

	private static void assertingKeysInStatisticsJsonResponse(Response response) {
		// Adding all the Customer Leaks 'Key' Constants to a List
		List<String> expectedKeysList = new ArrayList<String>();

		expectedKeysList.add(CUSTOMER_LEAKS_STATISTICS_METER_SIZE_KEY);
		expectedKeysList.add(CUSTOMER_LEAKS_STATISTICS_PREMISE_ID_KEY);
		expectedKeysList.add(CUSTOMER_LEAKS_STATISTICS_METER_NUMBER_KEY);
		expectedKeysList.add(CUSTOMER_LEAKS_STATISTICS_METER_ADDRESS_KEY);
		expectedKeysList.add(CUSTOMER_LEAKS_STATISTICS_LEAK_CATEGORY_KEY);
		expectedKeysList.add(CUSTOMER_LEAKS_STATISTICS_LEAK_STATUS_KEY);
		expectedKeysList.add(CUSTOMER_LEAKS_STATISTICS_FLAG_START_KEY);
		expectedKeysList.add(CUSTOMER_LEAKS_STATISTICS_WATER_LOSS_KEY);
		expectedKeysList.add(CUSTOMER_LEAKS_STATISTICS_CERTAINTY_KEY);
		// expectedKeysList.add(CUSTOMER_LEAKS_STATISTICS_LEAK_ACTIONS_KEY);

		System.out.println(expectedKeysList);

		// Verifying each map in the jsonResponseData contains the expected keys

		List<Map<String, Object>> jsonResponseData = response.jsonPath().get("data");
		int i = 0;
		// System.out.println(jsonResponseData.get(32));
		for (Map<String, Object> dataMap : jsonResponseData) {
			for (String expectedKey : expectedKeysList) {
				try {
					assertTrue(dataMap.containsKey(expectedKey));
					Log.info("The Record at Index " + i + " contains the Key " + expectedKey);
				} catch (Throwable t) {
					Log.error("The Record at Index " + i + " does not contain the Expected Key " + expectedKey);
					Log.error(t.getMessage());
					TestRailTestManagement
							.setAction("Error - Customer Leaks Theme expected " + expectedKey + " key is not present ");
					System.out.println(dataMap.get("flagStart"));
					fail(t.getMessage());

				}
			}
			// System.out.println(dataMap.keySet());

			i = i + 1;

		}

	}

	public static Response getTrendsAPIResponseForGivenIncidentTypeAndWaterLossForDefaultDays(String incidentType,
			String waterLoss, String days) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("aims");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/anomalystatistics";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);

		RestAssured.useRelaxedHTTPSValidation();
		long defaultDays = Long.parseLong(days);
		long endTime = System.currentTimeMillis();

		long startTime = endTime - (24 * 60 * 60 * 1000 * defaultDays);

		Map<String, Object> queryParamsMap = new HashMap<String, Object>();
		queryParamsMap.put("fromDate", startTime);
		queryParamsMap.put("toDate", endTime);
		queryParamsMap.put("category", TRENDS_QUERYPARAM_CATEGORY_VALUE);
		queryParamsMap.put("incidentType", incidentType);

		boolean isWaterLoss = Boolean.parseBoolean(waterLoss);
		if (isWaterLoss) {
			queryParamsMap.put("waterLoss", isWaterLoss);
		}

		ConfigFileReader configFileReader = new ConfigFileReader();
		Header header = new Header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer"));
		RequestSpecification httpRequest = RestAssured.given().queryParams(queryParamsMap).header(header).request()
				.log().all();
		Log.info("Created the Request for Customer Leaks Trends API for incidentType '" + incidentType
				+ "' and waterLoss '" + waterLoss + "' for " + days + " days");
		Response response = httpRequest.when().get();

		Log.info("Performing GET request on the Customer Leaks Trends API for incidentType '" + incidentType
				+ "' and waterLoss '" + waterLoss);

		Log.info("Trends API Response for incidentType '" + incidentType + "' and waterLoss '" + waterLoss + "' \n"
				+ response.asString());

		return response;

	}

	public static void verifyTrendsAPIResponseForGivenIncidentTypeAndWaterLossForDefaultDays(String incidentType,
			String waterLoss, String days) {
		Response response = getTrendsAPIResponseForGivenIncidentTypeAndWaterLossForDefaultDays(incidentType, waterLoss,
				days);
		assertEquals(response.getStatusCode(), 200);
		Log.info("Asserted Status code to be " + response.getStatusCode());

		Map<String, List<Object>> jsonResponseDataMap = response.jsonPath().get("data");
		assertTrue(!jsonResponseDataMap.isEmpty());
		Log.info("Asserted the Response Data Map is not empty");

		// Asserting status_code key value:
		assertEquals(response.jsonPath().getInt("status_code"), 200);

		Log.info("Asserted the 'status_code' key value to be " + response.jsonPath().getInt("status_code"));

		// Asserting the keys in Json DataResponse
		assertingKeysInTrendsJsonResponse(response, incidentType, waterLoss);

		// Asserting csvData not empty for 'customer_leak_drip'
		if (incidentType.contains("drip")) {
			List<List<Long>> csvDataList = response.jsonPath().get("data.csvData");
			System.out.println(csvDataList);
			assertTrue(!csvDataList.isEmpty());
			// verifying inner list not empty
			Log.info("Asserted the CSV data list is not empty for incident type: " + incidentType);
			for (List<Long> innerList : csvDataList) {
				System.out.println(innerList);
				assertTrue(!innerList.isEmpty());
			}
			Log.info("Asserted the CSV data inner list not empty for incident type: " + incidentType);
		}

		// Major leak validations ?
	}

	private static void assertingKeysInTrendsJsonResponse(Response response, String incidentType, String waterLoss) {
		Map<String, List<Object>> jsonResponseDataMap = response.jsonPath().get("data");
		boolean isWaterLoss = Boolean.parseBoolean(waterLoss);

		List<String> expectedKeyList = new ArrayList<String>();
		expectedKeyList.add(CUSTOMER_LEAKS_TRENDS_CSV_DATA_KEY);
		expectedKeyList.add(CUSTOMER_LEAKS_TRENDS_CSV_HEADER_KEY);
		expectedKeyList.add(CUSTOMER_LEAKS_TRENDS_META_LIST_KEY);

		// For API without waterLoss as the QueryParameter ,the JsonResponse dataMap
		// doesnt contain
		// the key
		// 'metaList' hence removing.
		if (!isWaterLoss) {
			expectedKeyList.remove(CUSTOMER_LEAKS_TRENDS_META_LIST_KEY);

		}

		for (String key : expectedKeyList) {
			try {
				assertTrue(jsonResponseDataMap.containsKey(key));
				Log.info("Json Response Data Map contains the key " + key + " for IncidentType '" + incidentType
						+ "' for Water loss = " + isWaterLoss);
			} catch (Throwable t) {
				Log.error("Json Response Data Map do not contain the key " + key + " for Incident Type '" + incidentType
						+ " ' for Water loss =" + isWaterLoss);
				Log.error(t.getMessage());
				fail(t.getMessage());
			}

		}
	}

	public static Response getIncidentsAPIDataForDefaultDays(long days) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("aims");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/getIncidents";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);

		RestAssured.useRelaxedHTTPSValidation();

		long endTime = System.currentTimeMillis();
		long startTime = endTime - (24 * 60 * 60 * 1000 * days);

		ConfigFileReader configFileReader = new ConfigFileReader();
		RequestSpecification httpRequest = RestAssured.given()
				.header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer"))
				.contentType(ContentType.JSON)
				.body("{\"incidentType\":[\"all\"],\"fromDate\":" + startTime + ",\"toDate\":" + endTime
						+ ",\"rank\":[],\"anomaly\":[\"customer_meter\"],\"recommendation\":[],\"status\":[],\"state\":[\"all\"],\"searchByEventTime\":true}")
				.request().log().all();

		Log.info("Created the Request for Customer Leaks getIncidents API for  for " + days + " days");
		Response response = httpRequest.when().post();

		Log.info("Performing POST request on the Customer Leaks Trends getIncidents API ");

		System.out.println(response.asString());

		Log.info("getIncidents API response for Customer Leaks \n" + response.asString());

		return response;

	}

	public static void verifyGetIncidentsAPIForCustomerLeaksThemeForDefaultDays(long days) {
		TestRailTestManagement.setTestComment("Updated by Automation Framework!");
		TestRailTestManagement.setAction("Validating get incident API for default days " + days);

		Response response = getIncidentsAPIDataForDefaultDays(days);

		assertEquals(response.getStatusCode(), 200);
		Log.info("Asserted Status code to be " + response.getStatusCode());

		// Asserting the status_code key
		assertEquals(response.jsonPath().getInt("status_code"), 200);
		Log.info("Asserted the 'status_code' key value to be " + response.jsonPath().getInt("status_code"));

		List<Map<String, Object>> jsonResponseDataList = response.jsonPath().get("data");
		assertTrue(!jsonResponseDataList.isEmpty());
		Log.info("Asserted the Json Response Data List is not empty");
		Log.info("The size of the List =" + jsonResponseDataList.size());

		TestRailTestManagement.setAction("Validating statistics values in each record ");

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
					Log.info("The Record Map at index " + i + "contains the key " + key);

				} catch (Throwable t) {
					Log.error("The Record Map at index " + i + " do not contain the key " + key);
					Log.error(t.getMessage());
					fail(t.getMessage());
				}
			}
			i = i + 1;
		}
	}
}
