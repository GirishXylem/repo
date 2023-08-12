package com.visenti.test.automation.api.modules.sew;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ThemesMetaSearch {

	private static final String ACOUSTIC_SENSORTYPE_ACTUAL_VALUE = "\"acoustic\",\"hydrophone\"";
	private static final String CUSTOMERMETER_SENSORTYPE_ACTUAL_VALUE = "\"consumption\",\"reading\"";
	private static final String TRANSIENT_SENSORTYPE_ACTUAL_VALUE = "\"pressure\"";

	public static Response getMetaSearchAPIResponseForAGivenTheme(String theme) {

		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix()
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "dataintelapi/{customer}/node/meta/search";

		RestAssured.useRelaxedHTTPSValidation();

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Loading metaSearch API response for theme " + theme);

		String customer = FileReaderManager.getInstance().getConfigReader().getCustomerID();

		String payload = "query={\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"bool\":{\"must\":[{\"or\":[{\"has_child\":{\"type\":\"sensor_info\",\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"and\":[{\"terms\":{\"sensortype_actual\":[actual-sensors]}}]}}}}},{\"has_parent\":{\"type\":\"station_info\""
				+ ",\"query\":{\"filtered\":{\"query\":{\"match_all\":{}}}}}}]}]}}}},\"size\":100000}&collection=station&username=admin";

		switch (theme.toLowerCase()) {
		case "acoustic":
			payload = payload.replace("actual-sensors", ACOUSTIC_SENSORTYPE_ACTUAL_VALUE);

			break;

		case "customer_meter":
			payload = payload.replace("actual-sensors", CUSTOMERMETER_SENSORTYPE_ACTUAL_VALUE);

			break;

		case "transient":
			payload = payload.replace("actual-sensors", TRANSIENT_SENSORTYPE_ACTUAL_VALUE);
			break;
		default:
			throw new RuntimeException("Wrong theme name passed");
		}
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.TEXT)
				.pathParam("customer", customer).body(payload).request().log().all();
		Log.info("Created the Request for meta search api for '" + theme.toLowerCase() + "' theme");

		Response response = httpRequest.when().post();
		Log.info("Performing POST request on the meta search api for '" + theme.toLowerCase() + "' theme");
		Log.info("meta search  API response for theme '" + theme.toLowerCase() + "'\n" + response.asString());

		return response;
	}

	public static void verifyMetaSearchAPIForAGivenTheme(String theme) {
		TestRailTestManagement.setTestComment(" Updated by Automation Framework! ");
		Response response = getMetaSearchAPIResponseForAGivenTheme(theme);
		TestRailTestManagement.setAction("Validating the status code to be " + STATUS_CODE_200);
		assertEquals(response.getStatusCode(), STATUS_CODE_200);
		Log.info("Asserted Status code for meta/search api  to be " + response.getStatusCode() + " for theme "
				+ theme.toLowerCase());

		JsonPath jPath = response.jsonPath();

		Map<String, Object> objectMap = jPath.getMap("$");
		// Asserting object Map returned is not empty
		TestRailTestManagement.setAction("Validating the object map to be non empty");
		assertTrue(!objectMap.isEmpty());
		Log.info("Asserted object Map is not empty ");

		TestRailTestManagement.setAction("Validating keys within 'object' map for theme " + theme.toLowerCase());
		// Asserting keys in Response 'object' map
		assertingKeysInObjectMap(objectMap, theme.toLowerCase());

		// Asserting the error code to be 0

		TestRailTestManagement
				.setAction("Validating '" + META_SEARCH_OBJECT_ERROR_CODE_KEY + "' key ,value: as expected");
		assertEquals(objectMap.get(META_SEARCH_OBJECT_ERROR_CODE_KEY), RESPONSE_ERRORCODE_0);
		Log.info("Asserted the '" + META_SEARCH_OBJECT_ERROR_CODE_KEY + "' value to be:" + RESPONSE_ERRORCODE_0);

		// Getting the response key Map
		@SuppressWarnings("unchecked")
		Map<String, Object> responseMap = (Map<String, Object>) objectMap.get(META_SEARCH_OBJECT_RESPONSE_KEY);
		TestRailTestManagement.setAction("Validating the response Map is not empty ");
		assertTrue(!responseMap.isEmpty());
		Log.info("Asserted the '" + META_SEARCH_OBJECT_RESPONSE_KEY + "' Map is not empty");

		TestRailTestManagement.setAction("Validating keys within 'response' map for the theme " + theme.toLowerCase());
		// Asserting the response Map contains the key : 'hits'
		assertTrue(responseMap.containsKey(META_SEARCH_RESPONSE_HITS_KEY));
		Log.info("response map contains the key '" + META_SEARCH_RESPONSE_HITS_KEY + "'");

		// Asserting the response.hits Map is not empty
		@SuppressWarnings("unchecked")
		Map<String, Object> hitsMap = (Map<String, Object>) responseMap.get(META_SEARCH_RESPONSE_HITS_KEY);
		TestRailTestManagement.setAction("Validating the response.hits Map is not empty ");
		assertTrue(!hitsMap.isEmpty());
		Log.info("Asserted the '" + META_SEARCH_RESPONSE_HITS_KEY + "' Map is not empty");

		// Asserting the hits Map contains the key 'hits'
		TestRailTestManagement.setAction("Validating keys within 'response.hits' map for theme " + theme.toLowerCase());
		assertTrue(hitsMap.containsKey(META_SEARCH_HITS_HITS_KEY));
		Log.info("hits map contains the key '" + META_SEARCH_HITS_HITS_KEY + "'");

		// Asserting the hits List is not empty

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> hitsList = (List<Map<String, Object>>) hitsMap.get(META_SEARCH_HITS_HITS_KEY);
		TestRailTestManagement.setAction("Validating the response.hits.hits List is not empty ");
		assertTrue(!hitsList.isEmpty());
		Log.info("Asserted the '" + META_SEARCH_HITS_HITS_KEY + "' list is not empty");
		Log.info("The size of '" + META_SEARCH_HITS_HITS_KEY + "' list for the theme '" + theme.toLowerCase() + "'  ="
				+ hitsList.size());

		// Asserting each hits Map not empty and containing keys
		TestRailTestManagement
				.setAction("Validating keys within each 'hits Map in the list for the theme " + theme.toLowerCase());
		assertingKeysInEachHitsMap(hitsList, theme.toLowerCase());

		// Asserting the source map inside each hits List is not empty
		TestRailTestManagement
				.setAction("Validating response.hits.hits._source map within each hits map in the list is not empty");
		assertingSourceMapForEachHitsMapInListIsNotEmpty(hitsList, theme);
	}

	private static void assertingKeysInObjectMap(Map<String, Object> responseObjectMap, String theme) {
		List<String> expectedKeys = new ArrayList<String>();
		expectedKeys.add(META_SEARCH_OBJECT_ERROR_CODE_KEY);
		expectedKeys.add(META_SEARCH_OBJECT_RESPONSE_KEY);

		for (String key : expectedKeys) {
			try {
				assertTrue(responseObjectMap.containsKey(key));
				Log.info("The object map contains the key '" + key + "' for '" + theme + "' theme");
			} catch (Throwable t) {
				Log.error("The object map do not contain the key '" + key + "' for '" + theme + " 'theme");
				Log.error(t.getMessage());
				TestRailTestManagement.setAction(
						"Error -The object map do not contain the key '" + key + "' for '" + theme + " 'theme");
				fail(t.getMessage());
			}
		}

	}

	private static void assertingKeysInEachHitsMap(List<Map<String, Object>> hitsList, String theme) {

		List<String> expectedKeys = new ArrayList<String>();
		expectedKeys.add(META_SEARCH_HITS_TYPE_KEY);
		expectedKeys.add(META_SEARCH_HITS_ID_KEY);
		expectedKeys.add(META_SEARCH_HITS_LEVEL_KEY);
		expectedKeys.add(META_SEARCH_HITS_SOURCE_KEY);

		int i = 0;
		for (Map<String, Object> hitsMap : hitsList) {
			assertTrue(!hitsMap.isEmpty());
			Log.info("hits Map at index " + i + " is not empty for the theme '" + theme + "'");

			for (String key : expectedKeys) {
				try {
					assertTrue(hitsMap.containsKey(key));
					Log.info("The hits map,at index " + i + " contains the key '" + key + "' for '" + theme
							+ "' theme ");
				} catch (Throwable t) {
					Log.error("The hits map,at index " + i + " do not contain the key '" + key + "' for '" + theme
							+ "' theme ");
					Log.error(t.getMessage());
					TestRailTestManagement.setAction("Error -The hits map,at index " + i + " do not contain the key '"
							+ key + "' for '" + theme + "' theme");
					fail(t.getMessage());
				}

			}
			i++;

		}

	}

	private static void assertingSourceMapForEachHitsMapInListIsNotEmpty(List<Map<String, Object>> hitsList,
			String theme) {
		int i = 0;
		for (Map<String, Object> map : hitsList) {
			@SuppressWarnings("unchecked")
			Map<String, Object> sourceMap = (Map<String, Object>) map.get(META_SEARCH_HITS_SOURCE_KEY);
			try {
				assertTrue(!sourceMap.isEmpty());
				Log.info("The source map,at index " + i + " is not empty for the theme " + theme.toLowerCase());
			} catch (Throwable t) {
				Log.error("The source map,at index " + i + "is empty for '" + theme + "' theme ");
				Log.error(t.getMessage());
				TestRailTestManagement
						.setAction("The source map,at index " + i + "is empty for '" + theme + "' theme ");
				fail(t.getMessage());
			}
			i++;
		}

	}

}
