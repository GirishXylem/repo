package com.visenti.test.automation.api.modules.sew;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.visenti.test.automation.constants.APIConstants.*;

import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AIMSConfig {

	/**
	 * @return
	 * 
	 * 		This method returns the AIMS Config API Response
	 */
	public static Response getAimsConfigAPIResponse() {
		
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("aims");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api + PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/getAimsConfig";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Invoke AIMS config API");

		ConfigFileReader configFileReader = new ConfigFileReader();

		RequestSpecification httpRequest = RestAssured.given()
				.queryParam("customerId", configFileReader.getCustomerID())
				.header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer")).request().log().all();

		Log.info("Created the Request for getAIMSConfig API");
		Response response = httpRequest.when().get();
		Log.info("Performing GET request on the getAIMSConfig API");

		Log.info("getAIMSConfig API response \n" + response.asString());

		return response;

	}

	public static void verifyGetAIMSConfigAPIResponse() {
		Response response = getAimsConfigAPIResponse();

		TestRailTestManagement.setAction("Verify AIMS config API response");

		assertEquals(response.getStatusCode(), 200);

		Log.info("Asserted Status code to be " + response.getStatusCode());

		// Asserting the status_code key
		assertEquals(response.jsonPath().getInt("status_code"), 200);
		Log.info("Asserted the 'status_code' key value to be " + response.jsonPath().getInt("status_code"));

		List<Map<String, Object>> jsonResponseDataList = response.jsonPath().get("data");

		assertTrue(!jsonResponseDataList.isEmpty());
		Log.info("Asserted the JsonResponse Data List is not empty");

		Map<String, Object> jsonResponseDataMap = jsonResponseDataList.get(0);
		// Asserting Map is not empty
		assertTrue(!jsonResponseDataMap.isEmpty());

		Log.info("Asserted Map inside the List is not empty");

		// Verify Expected Keys in Map
		assertKeysInDataMap(jsonResponseDataMap);

		// Verify title values of 'recommendation' key in expected Recommendation Values
		// list
		List<Map<String, String>> recommendationListOfMaps = response.jsonPath()
				.getList("data[0]." + AIMS_CONFIG_DATA_RECOMMENDATION_KEY);
		List<String> expectedRecommendationValuesList = gettingExpectedTitleValuesOfAGivenKeyInDataMap(
				AIMS_CONFIG_DATA_RECOMMENDATION_KEY);
		assertValuesInMaps(AIMS_CONFIG_DATA_RECOMMENDATION_KEY, recommendationListOfMaps,
				expectedRecommendationValuesList);

		// Verify title values of 'status' key in expected Status Values list
		List<Map<String, String>> statusListOfMaps = response.jsonPath()
				.getList("data[0]." + AIMS_CONFIG_DATA_STATUS_KEY);
		List<String> expectedStatusValuesList = gettingExpectedTitleValuesOfAGivenKeyInDataMap(
				AIMS_CONFIG_DATA_STATUS_KEY);
		assertValuesInMaps(AIMS_CONFIG_DATA_STATUS_KEY, statusListOfMaps, expectedStatusValuesList);

		// Verify title values of 'attend' key in expected Attend Values list
		List<Map<String, String>> attendListOfMaps = response.jsonPath()
				.getList("data[0]." + AIMS_CONFIG_DATA_ATTEND_KEY);
		List<String> expectedAttendValuesList = gettingExpectedTitleValuesOfAGivenKeyInDataMap(
				AIMS_CONFIG_DATA_ATTEND_KEY);
		assertValuesInMaps(AIMS_CONFIG_DATA_ATTEND_KEY, attendListOfMaps, expectedAttendValuesList);

		// Verify title values of 'category' key in expected Category Values list
		List<Map<String, String>> categoryListOfMaps = response.jsonPath()
				.getList("data[0]." + AIMS_CONFIG_DATA_CATEGORY_KEY);
		List<String> expectedCategoryValuesList = gettingExpectedTitleValuesOfAGivenKeyInDataMap(
				AIMS_CONFIG_DATA_CATEGORY_KEY);
		assertValuesInMaps(AIMS_CONFIG_DATA_CATEGORY_KEY, categoryListOfMaps, expectedCategoryValuesList);

		// Verify title values of 'rank' key in expected Rank Values list
		List<Map<String, String>> rankListOfMaps = response.jsonPath().getList("data[0]." + AIMS_CONFIG_DATA_RANK_KEY);
		List<String> expectedRankValuesList = gettingExpectedTitleValuesOfAGivenKeyInDataMap(AIMS_CONFIG_DATA_RANK_KEY);
		assertValuesInMaps(AIMS_CONFIG_DATA_RANK_KEY, rankListOfMaps, expectedRankValuesList);

	}

	/**
	 * @param jsonResponseDataMap This method asserts all the expected keys in the
	 *                            Json Response Data map
	 */
	private static void assertKeysInDataMap(Map<String, Object> jsonResponseDataMap) {
		List<String> expectedKeysInMap = new ArrayList<String>();
		expectedKeysInMap.add(AIMS_CONFIG_DATA_RECOMMENDATION_KEY);
		expectedKeysInMap.add(AIMS_CONFIG_DATA_STATUS_KEY);
		expectedKeysInMap.add(AIMS_CONFIG_DATA_SENSOR_KEY);
		expectedKeysInMap.add(AIMS_CONFIG_DATA_TYPE_KEY);
		expectedKeysInMap.add(AIMS_CONFIG_DATA_GROUP_BY_KEY);
		expectedKeysInMap.add(AIMS_CONFIG_DATA_RANK_KEY);
		expectedKeysInMap.add(AIMS_CONFIG_DATA_CATEGORY_KEY);
		expectedKeysInMap.add(AIMS_CONFIG_DATA_ATTEND_KEY);
		expectedKeysInMap.add(AIMS_CONFIG_DATA_SENSOR_TYPES_LIST_KEY);
		expectedKeysInMap.add(AIMS_CONFIG_DATA_ALERT_TYPES_LIST_KEY);

		for (String key : expectedKeysInMap) {
			try {
				assertTrue(jsonResponseDataMap.containsKey(key));
				Log.info("The key " + key + " is present in the Data map");
			} catch (Throwable t) {
				Log.error("The key " + key + " is not present in the Data map");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}
		}
	}

	/**
	 * @param key
	 * @return This method returns the List of expected 'title' Values for a given
	 *         key in the Data Map Say the key in data Map is 'status' ,the expected
	 *         'title' values for status are :'Open' and 'Close'
	 */
	private static List<String> gettingExpectedTitleValuesOfAGivenKeyInDataMap(String key) {
		List<String> expectedValues = new ArrayList<String>();
		if (key.equals(AIMS_CONFIG_DATA_RECOMMENDATION_KEY)) {
			expectedValues.add(AIMS_CONFIG_RECOMMENDATION_HIGH_VALUE);
			expectedValues.add(AIMS_CONFIG_RECOMMENDATION_MEDIUM_VALUE);
			expectedValues.add(AIMS_CONFIG_RECOMMENDATION_LOW_VALUE);
		} else if (key.equals(AIMS_CONFIG_DATA_STATUS_KEY)) {
			expectedValues.add(AIMS_CONFIG_STATUS_OPEN_VALUE);
			expectedValues.add(AIMS_CONFIG_STATUS_CLOSE_VALUE);
		} else if (key.equals(AIMS_CONFIG_DATA_ATTEND_KEY)) {
			expectedValues.add(AIMS_CONFIG_ATTEND_ALL_VALUE);
			expectedValues.add(AIMS_CONFIG_ATTEND_ATTENDED_VALUE);
			expectedValues.add(AIMS_CONFIG_ATTEND_UNATTENDED_VALUE);
			expectedValues.add(AIMS_CONFIG_ATTEND_INVESTIGATION_COMPLETE_VALUE);
			expectedValues.add(AIMS_CONFIG_ATTEND_UNDER_INVESTIGATION_VALUE);
		} else if (key.equals(AIMS_CONFIG_DATA_CATEGORY_KEY)) {
			expectedValues.add(AIMS_CONFIG_CATEGORY_ALL_VALUE);
			expectedValues.add(AIMS_CONFIG_CATEGORY_PUMP_START_VALUE);
			expectedValues.add(AIMS_CONFIG_CATEGORY_VALVE_SHUT_VALUE);
			expectedValues.add(AIMS_CONFIG_CATEGORY_LEAK_VALUE);
			expectedValues.add(AIMS_CONFIG_CATEGORY_NORMAL_VALUE);
			expectedValues.add(AIMS_CONFIG_CATEGORY_PUMP_STOP_VALUE);
			expectedValues.add(AIMS_CONFIG_CATEGORY_CONSUMPTION_START_VALUE);
			expectedValues.add(AIMS_CONFIG_CATEGORY_VIBRATION_VALUE);
			expectedValues.add(AIMS_CONFIG_CATEGORY_SUSPECTED_LEAK_VALUE);
			expectedValues.add(AIMS_CONFIG_CATEGORY_UNKNOWN_BUT_IGNORE_VALUE);
			expectedValues.add(AIMS_CONFIG_CATEGORY_VALVE_OPEN_VALUE);
			expectedValues.add(AIMS_CONFIG_CATEGORY_CONSUMPTION_END_VALUE);
			expectedValues.add(AIMS_CONFIG_CATEGORY_NOISE_VALUE);
		}
		// Since 'rank' key, 'title' values are 0 to 10 both inclusive ,hence adding the
		// expectedValues
		// using a for loop
		else if (key.equals(AIMS_CONFIG_DATA_RANK_KEY)) {
			for (int i = 0; i <= 10; i++) {
				expectedValues.add(String.valueOf(i));
			}

		}
		return expectedValues;
	}

	/**
	 * @param key
	 * @param listOfMaps
	 * @param expectedValuesList
	 * 
	 *                           This method asserts all the actual 'title' values
	 *                           for a given key is contained in the expectedValues
	 *                           List
	 */
	private static void assertValuesInMaps(String key, List<Map<String, String>> listOfMaps,
			List<String> expectedValuesList) {
		// Asserting the expectedValues size and ListOfMaps size are equal

		assertEquals(listOfMaps.size(), expectedValuesList.size());
		Log.info("Asserted the size of " + key + "ListOf Maps and expectedValues List for " + key + " to be equal ="
				+ listOfMaps.size());

		int i = 0;
		for (Map<String, String> map : listOfMaps) {
			try {
				// Asserting expectedValuesList contains the 'title' key value
				assertTrue(expectedValuesList.contains(map.get("title")));
				Log.info("The 'title' key, value '" + map.get("title") + "' in " + key + " map at index " + i
						+ " is present in expectedValuesList ");
			} catch (Throwable t) {
				Log.error("The 'title' key, value '" + map.get("title") + "' in " + key + " map at index " + i
						+ " is Not present in expectedStatusValuesList ");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}
			i++;

		}
	}
}
