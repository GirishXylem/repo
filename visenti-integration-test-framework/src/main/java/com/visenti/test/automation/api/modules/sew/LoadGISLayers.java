package com.visenti.test.automation.api.modules.sew;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import static com.visenti.test.automation.constants.APIConstants.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LoadGISLayers {

	/**
	 * @param gisType
	 * @return
	 *
	 * 		This method returns the Response object when POST request is
	 *         performed on the API for a given GIS layer type
	 *
	 *         Get the latitude and longitude with the default window screen on the
	 *         portal
	 */
	public static Response getGISAPIResponseForAGivenType(String gisType) {

		//PortalConfigManagement portalConfigs = new PortalConfigManagement();
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/dataintelapi/sewuk/node/meta/search";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Loading GIS layer: " + gisType);

		// {"location":{"top_left":{"lat":"51.44756621613155","lon":"0.03034179687536499"},"bottom_right":{"lat":"51.273922056482824","lon":"0.5796582031246248"}}}}]}]}}}}

		RestAssured.useRelaxedHTTPSValidation();
		String payload;
		if (gisType.equalsIgnoreCase("pipe")) {
			payload = "query={\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"and\":[{\"geo_bounding_box\":{\"location\":{\"top_left\":{\"lat\":\"51.44756621613155\","
					+ "\"lon\":\"0.03034179687536499\"},\"bottom_right\":{\"lat\":\"51.273922056482824\",\"lon\":\"0.5796582031246248\"}}}}]}}},"
					+ "\"size\":10000000}&collection=dataintelpipes_sewuk&username=admin";

		} else if (gisType.equalsIgnoreCase("hydrant") || gisType.equalsIgnoreCase("junction")
				|| gisType.equalsIgnoreCase("valve")) {

			gisType = gisType.toLowerCase();
			payload = "query={\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"bool\":{\"must\":[{\"and\":[{\"or\":[{\"term\":{\"TYPE\":\""
					+ gisType + "\"}}]},"
					+ "{\"geo_bounding_box\":{\"location\":{\"top_left\":{\"lat\":\"51.44756621613155\",\"lon\":\"0.03034179687536499\"},"
					+ "\"bottom_right\":{\"lat\":\"51.273922056482824\",\"lon\":\"0.5796582031246248\"}}}}]}]}}}},\"size\":10000000}&collection=layers_sewuk&username=admin";
		} else {
			throw new RuntimeException("Wrong gis Type '" + gisType + "' passed");
		}
		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.TEXT).body(payload).request()
				.log().all();
		Log.info("Created the Request for loading " + gisType + " gisLayer API");

		Response response = httpRequest.when().post();
		Log.info("Performing POST request on the loading " + gisType + " gisLayer API");
		Log.info("Load " + gisType + " gisLayer API response \n" + response.asString());

		return response;
	}

	/**
	 * @param gisType
	 * 
	 *                This method verifies the API response when a Given GIS layer
	 *                is loaded
	 *
	 */
	public static void verifyLoadingGISLayerOfAGivenType(String gisType) {

		TestRailTestManagement.setTestComment("Updated by Automation Framework!");

		Response response = getGISAPIResponseForAGivenType(gisType);
		// Asserting the Status code to be 200
		assertEquals(response.getStatusCode(), 200);

		// Getting the response key Map
		Map<String, Object> jsonResponseKeyMap = response.jsonPath().get("response");
		assertTrue(!jsonResponseKeyMap.isEmpty());

		// Getting the hits list and asserting it is not empty
		List<Map<String, Object>> jsonHitsList = response.jsonPath().get("response.hits.hits");
		System.out.println("Size\n " + jsonHitsList.size());
		Log.info("The total count of " + gisType + " GIS layer =" + jsonHitsList.size());
		assertTrue(!jsonHitsList.isEmpty());

		ConfigFileReader configFileReader = new ConfigFileReader();
		assertingHitsMapKeysInEachRecord(jsonHitsList, gisType);
		assertingLocationMapKeysInEachRecord(jsonHitsList, gisType);
		assertingMetaMapKeysInEachRecord(jsonHitsList, gisType);
		assertingValueForAGivenKeyInMetaMap(jsonHitsList, gisType, GIS_HITS_META_ZONE_KEY,
				configFileReader.getGISData("zone"));
		// Asserting the TYPE key in meta map ,for GIS layers other than pipe
		// as TYPE key is not present in all the records for pipe
		if (!gisType.equalsIgnoreCase("pipe")) {
			assertingValueForAGivenKeyInMetaMap(jsonHitsList, gisType, GIS_HITS_META_TYPE_KEY, gisType);
		}
	}

	/**
	 * @param jsonResponseHitsListOfMaps
	 * @param gisType
	 * 
	 *                                   hits key value is a JSON Array of Maps
	 *                                   ,here we are asserting that each Map is
	 *                                   having the 'location' and 'meta' keys for
	 *                                   any GIS type
	 */
	private static void assertingHitsMapKeysInEachRecord(List<Map<String, Object>> jsonResponseHitsListOfMaps,
			String gisType) {
		List<String> expectedKeysInHitsMap = new ArrayList<String>();
		expectedKeysInHitsMap.add(GIS_HITS_LOCATION_KEY);
		expectedKeysInHitsMap.add(GIS_HITS_META_KEY);

		// Verifying each Map in the Hits List contains the expected keys

		int i = 0;
		for (Map<String, Object> map : jsonResponseHitsListOfMaps) {
			for (String expectedKey : expectedKeysInHitsMap) {
				try {
					// Asserting each Map containing expected Keys
					assertTrue(map.containsKey(expectedKey));
					Log.info("The hits Record at Index " + i + " for GIS-" + gisType + " contains the Key "
							+ expectedKey);
				} catch (Throwable t) {
					Log.error("The hits Record at Index " + i + " for GIS-" + gisType
							+ " does not contain the Expected Key " + expectedKey);
					Log.error(t.getMessage());
					fail(t.getMessage());
				}
			}
			i++;
		}

	}

	/**
	 * @param jsonResponseHitsListOfMaps
	 * @param gisType
	 * 
	 *                                   location key inside the hits map is a
	 *                                   JsonArray of Maps . Here we assert each
	 *                                   location map contains the following keys:
	 *                                   'lon ', ' lat' and 'sequence'
	 */
	private static void assertingLocationMapKeysInEachRecord(List<Map<String, Object>> jsonResponseHitsListOfMaps,
			String gisType) {
		List<String> expectedKeysInHitsLocationMap = new ArrayList<String>();
		expectedKeysInHitsLocationMap.add(GIS_HITS_LOCATION_LONGITUDE_KEY);
		expectedKeysInHitsLocationMap.add(GIS_HITS_LOCATION_LATITUDE_KEY);
		expectedKeysInHitsLocationMap.add(GIS_HITS_LOCATION_SEQUENCE_KEY);

		for (int i = 0; i < jsonResponseHitsListOfMaps.size(); i++) {

			@SuppressWarnings("unchecked")
			List<Map<String, String>> jsonHitsLocationListOfMaps = (List<Map<String, String>>) jsonResponseHitsListOfMaps
					.get(i).get(GIS_HITS_LOCATION_KEY);
			for (int j = 0; j < jsonHitsLocationListOfMaps.size(); j++) {
				Map<String, String> locationMap = jsonHitsLocationListOfMaps.get(j);
				for (String expectedKey : expectedKeysInHitsLocationMap) {
					try {
						assertTrue(locationMap.containsKey(expectedKey));
						Log.info("The location Map at index " + j + " for the hits record at index " + i + " for GIS-"
								+ gisType + " contains the key " + expectedKey);

					} catch (Throwable t) {
						Log.error("The location Map at index " + j + " for the hits record at index " + i + " for GIS-"
								+ gisType + " do not contain the key " + expectedKey);
						Log.error(t.getMessage());
						fail(t.getMessage());
					}

				}
			}

		}
	}

	/**
	 * @param jsonResponseHitsListOfMaps
	 * @param gisType
	 * 
	 *                                   Here we assert the meta Map keys for each
	 *                                   record
	 * 
	 *                                   All pipe records do not contain 'TYPE' key
	 *                                   hence we are only validating the 'zone' key
	 *                                   for pipe records ,whereas for other gis
	 *                                   layers we are validating 'zone' ,TYPE','id'
	 *                                   and 'DESCRIPTION' keys
	 * 
	 */
	private static void assertingMetaMapKeysInEachRecord(List<Map<String, Object>> jsonResponseHitsListOfMaps,
			String gisType) {

		List<String> expectedKeysInHitsMetaMap = new ArrayList<String>();
		expectedKeysInHitsMetaMap.add(GIS_HITS_META_ID_KEY);
		expectedKeysInHitsMetaMap.add(GIS_HITS_META_TYPE_KEY);
		expectedKeysInHitsMetaMap.add(GIS_HITS_META_ZONE_KEY);

		// Asserting only the zone key value for pipe as all the pipe records meta Map
		// do not contain the key
		// 'pipeDiameter' ,'pipeMaterial' and 'TYPE'
		if (gisType.equalsIgnoreCase("pipe")) {
			expectedKeysInHitsMetaMap.remove(GIS_HITS_META_TYPE_KEY);
		} // Asserting the TYPE key value and Zone key value for other GIS layers as all
			// their
			// records contain the TYPE key and zone key
		else {
			expectedKeysInHitsMetaMap.add(GIS_HITS_META_DESCRIPTION_key);
		}

		for (int i = 0; i < jsonResponseHitsListOfMaps.size(); i++) {

			@SuppressWarnings("unchecked")
			Map<String, Object> jsonHitsMetaMap = (Map<String, Object>) jsonResponseHitsListOfMaps.get(i)
					.get(GIS_HITS_META_KEY);
			for (String expectedKey : expectedKeysInHitsMetaMap) {
				try {
					assertTrue(jsonHitsMetaMap.containsKey(expectedKey));
					Log.info("The meta Map for the hits record at index " + i + " for GIS-" + gisType
							+ " contains the key " + expectedKey);

				} catch (Throwable t) {
					Log.error("The meta Map for the hits record at index " + i + " for GIS-" + gisType
							+ " do not contain the key " + expectedKey);
					Log.error(t.getMessage());
					fail(t.getMessage());
				}
			}

		}

	}

	/**
	 * @param jsonResponseHitsListOfMaps
	 * @param gisType
	 * @param key
	 * @param expectedValue
	 * 
	 *                                   Here we are asserting the value for a key
	 *                                   on meta map returned in response For pipe
	 *                                   GIS layers we are asserting the value of
	 *                                   the 'zone' key values For other GIS layers
	 *                                   we are asserting the value of 'zone' and
	 *                                   'TYPE' keys
	 * 
	 */
	private static void assertingValueForAGivenKeyInMetaMap(List<Map<String, Object>> jsonResponseHitsListOfMaps,
			String gisType, String key, String expectedValue) {

		for (int i = 0; i < jsonResponseHitsListOfMaps.size(); i++) {
			@SuppressWarnings("unchecked")
			Map<String, String> metaMap = (Map<String, String>) jsonResponseHitsListOfMaps.get(i)
					.get(GIS_HITS_META_KEY);

			try {
				assertEquals(metaMap.get(key), expectedValue);
				Log.info("The actual value=" + metaMap.get(key) + " for the key:" + key
						+ " in Meta map matches the expected value for record" + " at index " + i
						+ ",for GIS layer of type:" + gisType);
			} catch (Throwable t) {
				Log.error("The actual value=" + metaMap.get(key) + " for the key:" + key
						+ " in Meta map do not match the " + "expected value=" + expectedValue + "  for record"
						+ " at index " + i + ",for GIS layer of type:" + gisType);

				Log.error(t.getMessage());
				fail(t.getMessage());
			}

		}
	}

}
