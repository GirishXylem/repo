package com.visenti.test.automation.api.modules.sew;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
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
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LoadAIMSTheme {

	// private static final String
	// AIMS_USER_PREFERENCES_QUERY_PARAM_CUSTOMER_ID_VALUE = "sewuk";
	// private static final String HEADER_X_SERVICE_CUSTOMER_VALUE = "sewuk";
	private static final String ANOMALY_KEY_VALUE_FOR_ALL_THEMES = "\"transient\",\"pressure\",\"flow\",\"customer_meter\",\"acoustic\",\"wqy\"";

	public static void verifyLoadingAIMS() {
		

	}

	/**
	 * This method makes GET request to the getAIMSUserPreferences api and verifies
	 * the response
	 */
	public static void getUserPreferencesForAIMS() {

		//PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("aims");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/getAIMSUserPreferences";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Invoke AIMS user preference API");

		// HTTP request
		String queryParamUserIdValue = FileReaderManager.getInstance().getConfigReader().getPortalUser();
		ConfigFileReader configFileReader = new ConfigFileReader();
		RequestSpecification httpRequest = RestAssured.given().queryParam("userId", queryParamUserIdValue)
				.queryParam("customerId", configFileReader.getCustomerID())
				.header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer")).request().log().all();

		Log.info("Created the Request for getAIMSUserPreferences API");
		Response response = httpRequest.when().request(Method.GET);
		Log.info("Performing GET request on the getAIMSUserPreferences API ");
		Log.info("getAIMSUserPreferences API response\n" + response.asString());
		// Getting response
		assertEquals(response.getStatusCode(), 200);
		Log.info("Asserted Status code to be " + response.getStatusCode());

		Integer jsonResponseStatusCodeKeyValue = response.jsonPath().get("status_code");
		assertEquals(jsonResponseStatusCodeKeyValue, Integer.valueOf(200));
		Log.info("Asserted the 'status_code' key,value to be equal to " + jsonResponseStatusCodeKeyValue);
		Map<String, String> jsonResponseMetaMap = response.jsonPath().getMap("meta");

		assertFalse(jsonResponseMetaMap.isEmpty());
		assertEquals(jsonResponseMetaMap.get("error_type"), "none");

		Log.info("Asserted meta map in response is non empty");

	}

	/**
	 * @param anomaly
	 * @param days
	 * @return
	 * 
	 * 		This method returns the Response of the /getIncidents api for a given
	 *         anomaly ,for default days
	 */
	public static Response getIncidentsAPIForAGivenAnomalyForDefaultDays(String anomaly, long days) {

		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		//PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("aims");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/getIncidents";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement
				.setAction("Loading incidents for " + anomaly + " anomaly type" + " for " + days + " days");

		RestAssured.useRelaxedHTTPSValidation();

		long endTime = System.currentTimeMillis();
		long startTime = endTime - (24 * 60 * 60 * 1000 * days);
		String payload;

		if (anomaly.equalsIgnoreCase("acoustic") || anomaly.equalsIgnoreCase("customer_meter")
				|| anomaly.equalsIgnoreCase("flow") || anomaly.equalsIgnoreCase("pressure")
				|| anomaly.equalsIgnoreCase("transient") || anomaly.equalsIgnoreCase("wqy")) {
			anomaly = anomaly.toLowerCase();
			payload = "{\"incidentType\":[\"all\"],\"fromDate\":" + startTime + ",\"toDate\":" + endTime
					+ ",\"rank\":[]," + "\"anomaly\":[\"" + anomaly + "\"],\"recommendation\":[],\"status\":[],"
					+ "\"state\":[\"All\"],\"searchByEventTime\":true}";
		} else if (anomaly.equalsIgnoreCase("all")) {
			payload = "{\"incidentType\":[\"all\"],\"fromDate\":" + startTime + ",\"toDate\":" + endTime
					+ ",\"rank\":[]," + "\"anomaly\":[" + ANOMALY_KEY_VALUE_FOR_ALL_THEMES
					+ "],\"recommendation\":[],\"status\":[]," + "\"state\":[\"All\"],\"searchByEventTime\":true}";
		} else {
			Log.error("Wrong anomaly '" + anomaly + "' passed from feature file");
			throw new RuntimeException("Wrong anomaly '" + anomaly + "' passed from feature file");
		}
		RequestSpecification httpRequest = RestAssured.given()
				.header("X-Service-Customer", PortalConfigManagement.getPortalPrefix()).contentType(ContentType.JSON)
				.body(payload).request().log().all();

		Log.info("Created the Request for " + anomaly + " theme getIncidents API for " + days + " days");

		Response response = httpRequest.when().post();
		Log.info("Performing POST request on the " + anomaly.toUpperCase() + " theme getIncidents API ");

		Log.info("getIncidents API response for " + anomaly.toUpperCase() + " theme \n" + response.asString());
		return response;

	}

	/**
	 * @param anomaly
	 * @param days
	 * 
	 *                This method verify getIncidents API Response for default days
	 */
	public static void verifyGetIncidentsAPIForaGivenAnomalyForDefaultDays(String anomaly, long days) {

		TestRailTestManagement.setTestComment(" Updated by Automation Framework! ");

		Response response = getIncidentsAPIForAGivenAnomalyForDefaultDays(anomaly, days);

		assertEquals(response.getStatusCode(), 200);
		Log.info("Asserted Status code to be " + response.getStatusCode());

		// Asserting the status_code key
		assertEquals(response.jsonPath().getInt("status_code"), 200);
		Log.info("Asserted the 'status_code' key value to be " + response.jsonPath().getInt("status_code"));

		Map<String, String> jsonResponseMetaMap = response.jsonPath().getMap("meta");
		assertFalse(jsonResponseMetaMap.isEmpty());

		Log.info("Asserted Meta map in response is non empty");
		// Since WQY theme is currently not having any data ,hence we are not asserting
		// the data to be non empty for wqy
		if (!anomaly.equalsIgnoreCase("wqy")) {

			TestRailTestManagement.setAction("Validating availability of data for AIMS: " + anomaly);

			List<Map<String, Object>> jsonResponseDataList = response.jsonPath().get("data");
			assertFalse(jsonResponseDataList.isEmpty());
			Log.info("Asserted the Json Response Data List is not empty");
			Log.info("The size of the List for " + anomaly + " theme =" + jsonResponseDataList.size());

			// Asserting the keys in Response for each record for a given anomaly
			assertingKeysInResponseForEachRecord(response, anomaly);
			if (!anomaly.equalsIgnoreCase("all")) {
				// Asserting the value of anomalyType key which is a JsonArray of Strings in the
				// Response.
				assertingTheValueOfAnomalyTypeKeyForEachRecord(response, anomaly.toLowerCase());
			} else {
				// Asserting the value of anomalyType key for 'all' themes which is a JsonArray
				// of Strings
				TestRailTestManagement.setAction("Validating attributes within data for AIMS: " + anomaly);

				assertingTheValueOfAnomalyTypeKeyForAllThemesEachRecord(response, ANOMALY_KEY_VALUE_FOR_ALL_THEMES);
			}
		}

	}

	/**
	 * @param response
	 * @param anomaly
	 * 
	 *                 This method asserts the Keys in Response for Each Record for
	 *                 a given anomaly
	 */
	private static void assertingKeysInResponseForEachRecord(Response response, String anomaly) {

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
					Log.info("The Record Map at index " + i + " for " + anomaly + " contains the key " + key);

				} catch (Throwable t) {
					Log.error("The Record Map at index " + i + " for " + anomaly + " do not contain the key " + key);
					Log.error(t.getMessage());
					fail(t.getMessage());
				}
			}
			i = i + 1;
		}
	}

	/**
	 * @param response
	 * @param expectedAnomalyTypeValue For anomaly type other than All ,this method
	 *                                 asserts the 'anomalyType' value for a single
	 *                                 'anomaly'
	 *                                 Example: if anomaly is 'acoustic' ,then in
	 *                                 the Response 'anomalyType' key in each data
	 *                                 record would be a JSONArray of a one element
	 *                                 'acoustic'
	 *
	 *                                 "anamolyType":["acoustic"]
	 *
	 *                                 We will be asserting the value for this key
	 *                                 is as expected Here we will be asserting for
	 *                                 the following anomalies:
	 *                                 acoustic,customer-meter,pressure,transient,flow
	 *                                 ...
	 * 
	 */

	private static void assertingTheValueOfAnomalyTypeKeyForEachRecord(Response response,
			String expectedAnomalyTypeValue) {
		List<Map<String, Object>> jsonResponseDataList = response.jsonPath().get("data");
		int i = 0;
		for (Map<String, Object> recordMap : jsonResponseDataList) {
			@SuppressWarnings("unchecked")
			List<String> anomalyType = (List<String>) recordMap.get(ALERTS_ANOMALY_TYPE_KEY);
			// Asserting the size of anomaly type list to be =1
			assertEquals(anomalyType.size(), 1);
			Log.info("The size of anomalyType list for record at index " + i + " equal to " + anomalyType.size());
			String actualAnomalyTypeValue = anomalyType.get(0);
			try {
				assertEquals(actualAnomalyTypeValue, expectedAnomalyTypeValue);
				Log.info("The value of '" + ALERTS_ANOMALY_TYPE_KEY + "' key to be " + actualAnomalyTypeValue
						+ " for record at index " + i);
			} catch (Throwable t) {
				Log.error("The actual value " + actualAnomalyTypeValue + " of the key:'" + ALERTS_ANOMALY_TYPE_KEY
						+ "' do not match with expected value  " + expectedAnomalyTypeValue + " for record at index "
						+ i);
				Log.error(t.getMessage());
				fail(t.getMessage());
			}

			i++;
		}
	}

	/**
	 * @param response
	 * @param expectedAnomalyTypeString
	 *
	 *                                  Here we will assert the value of the
	 *                                  'anomalyType' key in each record when we are
	 *                                  passing all the themes in the post request
	 *
	 *                                  The response json anomalyType key for each
	 *                                  record value would be a JSONArray of all
	 *                                  anomalies
	 *
	 *
	 *                                  "anamolyType":["transient","pressure","flow","customer_meter","acoustic","wqy"]
	 *
	 */
	private static void assertingTheValueOfAnomalyTypeKeyForAllThemesEachRecord(Response response,
			String expectedAnomalyTypeString) {
		// Splitting the allAnomalyString by , and then replacing the " with empty space
		String[] parts = expectedAnomalyTypeString.split(",");
		for (int i = 0; i < parts.length; i++) {
			parts[i] = parts[i].replaceAll("\"", "");
		}
		// Getting a list from the Array
		List<String> expectedAnomalyTypeValues = Arrays.asList(parts);

		List<Map<String, Object>> jsonResponseDataList = response.jsonPath().get("data");
		int i = 0;
		for (Map<String, Object> recordMap : jsonResponseDataList) {
			@SuppressWarnings("unchecked")
			List<String> actualAnomalyTypeValues = (List<String>) recordMap.get(ALERTS_ANOMALY_TYPE_KEY);
			// Asserting the size of actualAnomalyType values and Expected
			assertEquals(actualAnomalyTypeValues.size(), expectedAnomalyTypeValues.size());
			Log.info("The size of " + ALERTS_ANOMALY_TYPE_KEY + " list for record at index " + i + " equal to "
					+ actualAnomalyTypeValues.size());

			for (int j = 0; j < actualAnomalyTypeValues.size(); j++) {

				try {
					assertEquals(actualAnomalyTypeValues.get(j), expectedAnomalyTypeValues.get(j));
					Log.info("The " + ALERTS_ANOMALY_TYPE_KEY + " value =" + actualAnomalyTypeValues.get(j)
							+ " at index " + j + " in the " + ALERTS_ANOMALY_TYPE_KEY
							+ "list matches the expected value, for the data Record at index " + i);
				} catch (Throwable t) {
					Log.error("The actual value " + actualAnomalyTypeValues.get(j) + " at index " + j
							+ " do not match expected " + expectedAnomalyTypeValues.get(j)
							+ " for the data record at index " + i);
					Log.error(t.getMessage());
					fail(t.getMessage());
				}
			}
			i++;
		}

	}
}
