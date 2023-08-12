package com.visenti.test.automation.api.modules.sew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.visenti.test.automation.helpers.ConfigFileReader;

import org.testng.Assert;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import cucumber.api.DataTable;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.*;

public class LoadZoneTheme {

	private static final List<String> zoneIdsFinalList = new ArrayList<>();

	public static void verifyLoadingZoneTheme() {
		/**
		 * disable url encoding to make sure API call will do as intended
		 */
		RestAssured.urlEncodingEnabled = false;

		RestAssured.useRelaxedHTTPSValidation();

		// Loading Pipe GIS data
		// loadingPipeData();

		// Loading theme APIs data
		// loadingAttributes();
		// loadingLabels();
		loadingThemes();

		// Loading Statistics
		// loadingStatistics();

		// Loading GIS info
		// loadingGISData();

		// Loading Trends - Data
		// loadingTrendsData();

	}

	// Theme API Call for getting all attributes

	public static void loadingAttributes() {
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("dma");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		// RestAssured.baseURI = "https://sewuk-dma-api.eu.cloud.visenti.com/";
		RestAssured.basePath = "/themes/commattributes/all";

		ConfigFileReader configFileReader = new ConfigFileReader();

		// HTTP request
		RequestSpecification httpRequest = RestAssured.given()
				.header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer"))
				.header("X-Service-User", configFileReader.getAPIHeaderValues("user"))
				.header("X-Service-Url", configFileReader.getAPIHeaderValues("url"))
				.header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket")).request().log().all();
		Response response = httpRequest.request(Method.GET);
		// Getting response Assert.assertEquals(response.getStatusCode(), 200);
		Integer jsonResponseStatus = response.jsonPath().get("status");
		Assert.assertEquals(String.valueOf(jsonResponseStatus), "202");
	}

	// Theme API call to get all labels

	public static void loadingLabels() {
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("dma");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		// RestAssured.baseURI = "https://sewuk-dma-api.eu.cloud.visenti.com/";
		RestAssured.basePath = "/themes/label/all";

		ConfigFileReader configFileReader = new ConfigFileReader();
		// HTTP request
		RequestSpecification httpRequest = RestAssured.given()
				.header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer"))
				.header("X-Service-User", configFileReader.getAPIHeaderValues("user"))
				.header("X-Service-Url", configFileReader.getAPIHeaderValues("url"))
				.header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket")).request().log().all();
		Response response = httpRequest.request(Method.GET);
		// Getting response Assert.assertEquals(response.getStatusCode(), 200);
		Integer jsonResponseStatus = response.jsonPath().get("status");
		Assert.assertEquals(String.valueOf(jsonResponseStatus), "202");

	}

	// Theme API call to get all themes data

	public static void loadingThemes() {

		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("dma");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();

		// RestAssured.baseURI = "https://sewuk-dma-api.eu.cloud.visenti.com/";
		RestAssured.basePath = "/themes/all";

		ConfigFileReader configFileReader = new ConfigFileReader();
		// HTTP request
		RequestSpecification httpRequest = RestAssured.given()
				.header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer"))
				.header("X-Service-User", configFileReader.getAPIHeaderValues("user"))
				.header("X-Service-Url", configFileReader.getAPIHeaderValues("url"))
				.header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket")).request().log().all();
		Response response = httpRequest.request(Method.GET);
		// Getting response Assert.assertEquals(response.getStatusCode(), 200);
		Integer jsonResponseStatus = response.jsonPath().get("status");
		Assert.assertEquals(String.valueOf(jsonResponseStatus), "202");

	}

	// API call to get all statistics - 24/05/2019 00:00 - 24/05/2019 23:59

	public static void loadingStatistics() {
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("dma");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		// RestAssured.baseURI = "https://sewuk-dma-api.eu.cloud.visenti.com/";
		RestAssured.basePath = "/statistics/all";

		ConfigFileReader configFileReader = new ConfigFileReader();

		// HTTP request
		RequestSpecification httpRequest = RestAssured.given()
				.header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer"))
				.header("X-Service-User", configFileReader.getAPIHeaderValues("user"))
				.header("X-Service-Url", configFileReader.getAPIHeaderValues("url"))
				.header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket"))
				.body("{\"request\":{\"st\":1558627200000,\"et\":1558713540000,\"zone_ids\":[\"5c9882d1cff47e00018753b0\",\"5c988351cff47e00018753b2\",\"5c988421cff47e00018753b4\",\"5c988443cff47e00018753b6\",\"5c9884abcff47e00018753b8\"]}}")
				.request().log().all();
		Response response = httpRequest.request(Method.POST);
		// Getting response Assert.assertEquals(response.getStatusCode(), 200);
		Integer jsonResponseStatus = response.jsonPath().get("status");
		Assert.assertEquals(String.valueOf(jsonResponseStatus), "202");

	}

	// API call to load GIS data

	public static void loadingGISData() {

		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("gis");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/get/customerInfo";

		// HTTP request
		RequestSpecification httpRequest = RestAssured.given().queryParam("customer", "sewuk").request().log().all();
		Response response = httpRequest.request(Method.GET); // Getting response
		Assert.assertEquals(response.getStatusCode(), 200);
		Integer jsonResponseStatus = response.jsonPath().get("status_code");
		Assert.assertEquals(String.valueOf(jsonResponseStatus), "200");

	}

	// API call to load pipe GIS data

	public static void loadingPipeData() {

		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix()
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/dataintelapi/" + PortalConfigManagement.getPortalPrefix() + "/node/meta/search";

		RequestSpecification httpRequest = RestAssured.given().body(
				"query={\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"and\":[{\"geo_bounding_box\":{\"location\":{\"top_left\":{\"lat\":\"51.44753984162416\",\"lon\":\"0.030341796875370152\"},\"bottom_right\":{\"lat\":\"51.27389558179671\",\"lon\":\"0.5796582031246298\"}}}}]}}},\"size\":10000000}&collection=dataintelpipes_sewuk&username=admin")
				.request().log().all();
		Response response = httpRequest.request(Method.POST);
		// Getting response Assert.assertEquals(response.getStatusCode(), 200);
		HashMap<String, String> jsonResponse = response.jsonPath().get("response");
		assertFalse(jsonResponse.isEmpty());

	}

	// API call to load data for trends - 24/05/2019 00:00 - 24/05/2019 23:59

	public static void loadingTrendsData() {
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("dma");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		// RestAssured.baseURI = "https://sewuk-dma-api.eu.cloud.visenti.com";
		RestAssured.basePath = "/search/data";

		ConfigFileReader configFileReader = new ConfigFileReader();
		RequestSpecification httpRequest = RestAssured.given()
				.header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer"))
				.header("X-Service-User", configFileReader.getAPIHeaderValues("user"))
				.header("X-Service-Url", configFileReader.getAPIHeaderValues("url"))
				.header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket"))
				.body("{\"request\":{\"st\":1558627200000,\"et\":1558713540000,\"intervalmin\":15,\"include\":[\"5c9882d1cff47e00018753b0\"],\"sensortype\":\"current_demand\",\"timezone\":\"Europe/London\",\"format\":\"csv\"}}")
				.request().log().all();
		Response response = httpRequest.request(Method.POST);
		// Getting response Assert.assertEquals(response.getStatusCode(), 200);
		Integer jsonResponseStatus = response.jsonPath().get("status");
		ArrayList<String> jsonResponse = response.jsonPath().get("data");
		Assert.assertEquals(String.valueOf(jsonResponseStatus), "202");
		assertFalse(jsonResponse.isEmpty());
	}

	public static Response getCustomerInfoAPIResponseForAGivenCustomer() {

		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("gis");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/get/customerInfo";
		RestAssured.useRelaxedHTTPSValidation();

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);

		// Checking if the customer is not in lowercase ,converting it into lower case
		ConfigFileReader configFileReader = new ConfigFileReader();
		String customer = configFileReader.getGISData("customer");

		if (!customer.equals(customer.toLowerCase())) {
			customer = customer.toLowerCase();
		}
		RequestSpecification httpRequest = RestAssured.given().queryParam("customer", customer).request().log().all();
		Log.info("Created the Request for getCustomerInfo API");
		Response response = httpRequest.when().get();
		Log.info("Performing GET request on the getCustomerInfo API ");
		Log.info("getCustomerInfo API response\n" + response.asString());
		System.out.println("Response\n" + response.asString());
		return response;

	}

	public static void verifyGetCustomerInfoAPIResponseForAGivenCustomer() {
		TestRailTestManagement.setAction("Zone theme - Verify Customer Info API");
		TestRailTestManagement.setTestComment("Verify Customer Info API" + System.lineSeparator()
				+ System.lineSeparator() + " Updated by Automation Framework! ");

		Response response = getCustomerInfoAPIResponseForAGivenCustomer();
		// Getting response
		assertEquals(response.getStatusCode(), STATUS_CODE_200);
		Log.info("Asserted Status code to be " + response.getStatusCode());
		// Getting the response Map
		JsonPath jPath = response.jsonPath();
		Map<String, Object> responseMap = jPath.getMap("$");
		// Asserting Response Map returned is not empty
		assertFalse(responseMap.isEmpty());
		Log.info("Asserted Response Map is not empty");

		// Asserting keys in Response Map is as expected
		assertingKeysInResponseMapForGetCustomerInfoAPI(responseMap);

		Integer jsonResponseStatusCodeKeyValue = jPath.get(ZONE_CUSTOMER_INFO_RESPONSE_STATUS_CODE_KEY);
		assertEquals(jsonResponseStatusCodeKeyValue, Integer.valueOf(STATUS_CODE_200));
		Log.info("Asserted the 'status_code' key,value to be equal to " + jsonResponseStatusCodeKeyValue);

		// Asserting Data Map is not empty
		Map<String, Object> responseDataMap = jPath.get(ZONE_CUSTOMER_INFO_RESPONSE_DATA_KEY);
		assertFalse(responseDataMap.isEmpty());
		Log.info("Asserted Data map in response is not empty");
		// Asserting keys in Response Data map
		assertingKeysInResponseDataMapForGetCustomerInfoAPI(responseDataMap);

		// Asserting the '_id' map in Response data is not empty
		Map<String, String> dataIdMap = jPath
				.get(ZONE_CUSTOMER_INFO_RESPONSE_DATA_KEY + "." + ZONE_CUSTOMER_INFO_DATA_ID_KEY);

		assertFalse(dataIdMap.isEmpty());
		Log.info("Asserted the '" + ZONE_CUSTOMER_INFO_DATA_ID_KEY + "' map inside '"
				+ ZONE_CUSTOMER_INFO_RESPONSE_DATA_KEY + "' Map is not empty");

		// Asserting the network List in Response data is not empty

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> networkListOfMap = (List<Map<String, Object>>) responseDataMap
				.get(ZONE_CUSTOMER_INFO_DATA_NETWORK_KEY);
		assertFalse(networkListOfMap.isEmpty());
		Log.info("Asserted the '" + ZONE_CUSTOMER_INFO_DATA_NETWORK_KEY + "' List inside '"
				+ ZONE_CUSTOMER_INFO_RESPONSE_DATA_KEY + "' map is not empty");

		// Asserting the keys in network map
		assertingKeysInNetworkMapForGetCustomerInfoAPI(networkListOfMap);

		// Asserting the DMA List inside network Map is not empty

		@SuppressWarnings("unchecked")
		List<String> dmaList = (List<String>) networkListOfMap.get(0).get(ZONE_CUSTOMER_INFO_NETWORK_DMA_KEY);
		assertFalse(dmaList.isEmpty());
		Log.info("Asserted the '" + ZONE_CUSTOMER_INFO_NETWORK_DMA_KEY + "' list inside '"
				+ ZONE_CUSTOMER_INFO_DATA_NETWORK_KEY + "' Map is not empty");
		Log.info("Size of '" + ZONE_CUSTOMER_INFO_NETWORK_DMA_KEY + "' list=" + dmaList.size());

		// Asserting the zones List of Maps in Response data is not empty
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> zonesListOfMaps = (List<Map<String, Object>>) responseDataMap
				.get(ZONE_CUSTOMER_INFO_DATA_ZONES_KEY);
		assertFalse(zonesListOfMaps.isEmpty());
		Log.info("Asserted the '" + ZONE_CUSTOMER_INFO_DATA_ZONES_KEY + "' List inside '"
				+ ZONE_CUSTOMER_INFO_RESPONSE_DATA_KEY + "' map is not empty");
		Log.info("Size of '" + ZONE_CUSTOMER_INFO_DATA_ZONES_KEY + " 'list=" + zonesListOfMaps.size());
		// Asserting keys in each zones map in the List

		assertingKeysInEachMapInZonesListForGetCustomerInfoAPI(zonesListOfMaps);
		// Asserting Meta Map is not empty
		Map<String, String> jsonResponseMetaMap = jPath.getMap(ZONE_CUSTOMER_INFO_RESPONSE_META_KEY);
		assertFalse(jsonResponseMetaMap.isEmpty());
		Log.info("Asserted Meta map in response is non empty");

	}

	private static void assertingKeysInResponseMapForGetCustomerInfoAPI(Map<String, Object> responseMap) {

		List<String> expectedKeysInResponseMap = new ArrayList<>();
		expectedKeysInResponseMap.add(ZONE_CUSTOMER_INFO_RESPONSE_STATUS_CODE_KEY);
		expectedKeysInResponseMap.add(ZONE_CUSTOMER_INFO_RESPONSE_DATA_KEY);
		expectedKeysInResponseMap.add(ZONE_CUSTOMER_INFO_RESPONSE_META_KEY);
		expectedKeysInResponseMap.add(ZONE_CUSTOMER_INFO_RESPONSE_STATUS_KEY);

		for (String key : expectedKeysInResponseMap) {
			try {
				assertTrue(responseMap.containsKey(key));
				Log.info("The Response map contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The Response map do not contain the key '" + key + "'");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}

		}
	}

	private static void assertingKeysInResponseDataMapForGetCustomerInfoAPI(Map<String, Object> responseDataMap) {
		List<String> expectedKeysInResponseDataMap = new ArrayList<>();
		expectedKeysInResponseDataMap.add(ZONE_CUSTOMER_INFO_DATA_ID_KEY);
		expectedKeysInResponseDataMap.add(ZONE_CUSTOMER_INFO_DATA_NAME_KEY);
		expectedKeysInResponseDataMap.add(ZONE_CUSTOMER_INFO_DATA_NETWORK_KEY);
		expectedKeysInResponseDataMap.add(ZONE_CUSTOMER_INFO_DATA_ZONES_KEY);

		for (String key : expectedKeysInResponseDataMap) {
			try {
				assertTrue(responseDataMap.containsKey(key));
				Log.info("The Response Data map contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The Response Data map do not contain the key '" + key + "'");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}
		}
	}

	private static void assertingKeysInNetworkMapForGetCustomerInfoAPI(List<Map<String, Object>> networkListOfMap) {
		List<String> expectedKeysInNetworkMap = new ArrayList<>();
		expectedKeysInNetworkMap.add(ZONE_CUSTOMER_INFO_NETWORK_ID_KEY);
		expectedKeysInNetworkMap.add(ZONE_CUSTOMER_INFO_NETWORK_NAME_KEY);
		expectedKeysInNetworkMap.add(ZONE_CUSTOMER_INFO_NETWORK_CUSTOMER_ID_KEY);
		expectedKeysInNetworkMap.add(ZONE_CUSTOMER_INFO_NETWORK_DMA_KEY);

		int i = 0;
		for (Map<String, Object> networkMap : networkListOfMap) {
			// Asserting network Map not empty
			assertFalse(networkMap.isEmpty());
			Log.info("network Map at index " + i + " is not empty");
			for (String key : expectedKeysInNetworkMap) {
				try {
					assertTrue(networkMap.containsKey(key));
					Log.info("The network map at index " + i + " contains the key '" + key + "'");
				} catch (Throwable t) {
					Log.error("The network map at index " + i + " do not contain the key '" + key + "'");
					Log.error(t.getMessage());
					fail(t.getMessage());
				}

			}
			i = i + 1;
		}
	}

	private static void assertingKeysInEachMapInZonesListForGetCustomerInfoAPI(
			List<Map<String, Object>> zonesListOfMaps) {
		List<String> expectedKeysInZonesMap = new ArrayList<>();
		expectedKeysInZonesMap.add(ZONE_CUSTOMER_INFO_ZONES_ID_KEY);
		expectedKeysInZonesMap.add(ZONE_CUSTOMER_INFO_ZONES_NETWORK_ID_KEY);
		expectedKeysInZonesMap.add(ZONE_CUSTOMER_INFO_ZONES_ZONE_TYPE_KEY);
		expectedKeysInZonesMap.add(ZONE_CUSTOMER_INFO_ZONES_ZONE_NAME_KEY);
		expectedKeysInZonesMap.add(ZONE_CUSTOMER_INFO_ZONES_HAS_PARENT_KEY);
		expectedKeysInZonesMap.add(ZONE_CUSTOMER_INFO_ZONES_PARENT_ZONE_KEY);

		int i = 0;
		for (Map<String, Object> zonesMap : zonesListOfMaps) {
			// Asserting each zonesMap in the List not empty
			assertFalse(zonesMap.isEmpty());
			Log.info("zones Map at index " + i + " is not empty");
			for (String key : expectedKeysInZonesMap) {
				try {
					assertTrue(zonesMap.containsKey(key));
					Log.info("The zones map at index " + i + " contains the key '" + key + "'");
				} catch (Throwable t) {
					Log.error("The zones map at index " + i + " do not contain the key '" + key + "'");
					Log.error(t.getMessage());
					fail(t.getMessage());
				}

			}
			i = i + 1;
		}
	}

	public static Response getThemesLabelAllAPIResponse() {
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("dma");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/themes/label/all";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Zone theme - get theme labels ");

		RestAssured.useRelaxedHTTPSValidation();
		// String headerXServiceUserValue =
		// FileReaderManager.getInstance().getConfigReader().getPortalUser();
		ConfigFileReader configFileReader = new ConfigFileReader();

		Header xServiceUrl = new Header("X-Service-Url", configFileReader.getAPIHeaderValues("url"));
		Header xServiceTicket = new Header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket"));

		Header xServiceUser = new Header("X-Service-User", configFileReader.getAPIHeaderValues("user"));

		Headers headers = new Headers(xServiceUrl, xServiceTicket, xServiceUser);

		RequestSpecification httpRequest = RestAssured.given().headers(headers).request().log().all();
		Log.info("Created the Request for getThemesLabelAll API");

		Response response = httpRequest.when().get();
		Log.info("Performing GET request on the getThemesLabelAll API ");
		Log.info("getThemesLabelAll API response\n" + response.asString());
		return response;
	}

	public static void verifyGetThemesLabelAllAPIResponse() {

		Response response = getThemesLabelAllAPIResponse();
		// Getting response
		assertEquals(response.getStatusCode(), STATUS_CODE_200);
		Log.info("Asserted Status code to be " + response.getStatusCode());
		JsonPath jPath = response.jsonPath();
		Map<String, Object> responseMap = jPath.getMap("$");
		// Asserting Response Map returned is not empty
		assertFalse(responseMap.isEmpty());
		Log.info("Asserted Response Map is not empty");

		// Asserting keys in Response Maps
		assertingKeysInResponseMapForGetThemesLabelAllAPI(responseMap);

		// Asserting Response data List of Maps is not empty
		List<Map<String, Object>> responseDataListOfMaps = jPath.getList(ZONE_THEMES_LABEL_ALL_RESPONSE_DATA_KEY);
		assertFalse(responseDataListOfMaps.isEmpty());
		Log.info("Asserted  response data List of Maps is not empty");
		Log.info("The size of the data List of Maps =" + responseDataListOfMaps.size());

		TestRailTestManagement.setAction(" Zone theme - Validate data list of response in get theme label API");

		// Asserting the size of data List Of Maps is same as 'count' key
		assertEquals(responseDataListOfMaps.size(), jPath.getInt(ZONE_THEMES_LABEL_ALL_RESPONSE_COUNT_KEY),
				"The response data List of Maps size" + "=" + responseDataListOfMaps.size() + " do not match with the '"
						+ ZONE_THEMES_LABEL_ALL_RESPONSE_COUNT_KEY + "' key value=" + ""
						+ jPath.getInt(ZONE_THEMES_LABEL_ALL_RESPONSE_COUNT_KEY));

		Log.info("Asserted the size of data List of Maps matches with '" + ZONE_THEMES_LABEL_ALL_RESPONSE_COUNT_KEY
				+ "' key :value= " + responseDataListOfMaps.size());
		assertingKeysInEachDataMapForGetThemesLabelAllAPI(responseDataListOfMaps);
		assertingCustomerKeysInDataMapForGetThemesLabelAllAPI(responseDataListOfMaps);

	}

	private static void assertingKeysInResponseMapForGetThemesLabelAllAPI(Map<String, Object> responseMap) {
		List<String> expectedKeysInResponseMap = new ArrayList<>();
		expectedKeysInResponseMap.add(ZONE_THEMES_LABEL_ALL_RESPONSE_DATA_KEY);
		expectedKeysInResponseMap.add(ZONE_THEMES_LABEL_ALL_RESPONSE_STATUS_KEY);
		expectedKeysInResponseMap.add(ZONE_THEMES_LABEL_ALL_RESPONSE_COUNT_KEY);
		expectedKeysInResponseMap.add(ZONE_THEMES_LABEL_ALL_RESPONSE_REQUEST_ID_KEY);
		expectedKeysInResponseMap.add(ZONE_THEMES_LABEL_ALL_RESPONSE_REQUEST_TIME_KEY);
		expectedKeysInResponseMap.add(ZONE_THEMES_LABEL_ALL_RESPONSE_RESPONSE_TIME_KEY);
		expectedKeysInResponseMap.add(ZONE_THEMES_LABEL_ALL_RESPONSE_STATUS_TEXT_KEY);
		expectedKeysInResponseMap.add(ZONE_THEMES_LABEL_ALL_RESPONSE_TIME_KEY);
		for (String key : expectedKeysInResponseMap) {
			try {
				assertTrue(responseMap.containsKey(key));
				Log.info("The Response map contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The Response map do not contain the key '" + key + "'");
				Log.error(t.getMessage());
				fail(t.getMessage());

			}
		}
	}

	private static void assertingKeysInEachDataMapForGetThemesLabelAllAPI(
			List<Map<String, Object>> responseDataListOfMaps) {
		List<String> expectedKeysInEachDataMap = new ArrayList<>();
		expectedKeysInEachDataMap.add(ZONE_THEMES_LABEL_ALL_DATA_CATEGORY_KEY);
		expectedKeysInEachDataMap.add(ZONE_THEMES_LABEL_ALL_DATA_CUSTOMER_KEY);
		expectedKeysInEachDataMap.add(ZONE_THEMES_LABEL_ALL_DATA_CUSTOMER_ID_KEY);
		expectedKeysInEachDataMap.add(ZONE_THEMES_LABEL_ALL_DATA_ID_KEY);
		expectedKeysInEachDataMap.add(ZONE_THEMES_LABEL_ALL_DATA_KEY_KEY);
		expectedKeysInEachDataMap.add(ZONE_THEMES_LABEL_ALL_DATA_NAME_KEY);

		int i = 0;
		for (Map<String, Object> dataMap : responseDataListOfMaps) {
			assertFalse(dataMap.isEmpty());
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

	private static void assertingCustomerKeysInDataMapForGetThemesLabelAllAPI(
			List<Map<String, Object>> responseDataListOfMaps) {
		List<String> expectedKeysInEachCustomerMap = new ArrayList<>();
		expectedKeysInEachCustomerMap.add(ZONE_THEMES_LABEL_ALL_CUSTOMER_ID_KEY);
		expectedKeysInEachCustomerMap.add(ZONE_THEMES_LABEL_ALL_CUSTOMER_NAME_KEY);

		int i = 0;
		for (Map<String, Object> dataMap : responseDataListOfMaps) {
			@SuppressWarnings("unchecked")
			Map<String, Object> customerMap = (Map<String, Object>) dataMap
					.get(ZONE_THEMES_LABEL_ALL_DATA_CUSTOMER_KEY);
			// Asserting each customer Map is not empty
			assertFalse(customerMap.isEmpty());
			Log.info("Asserted customer Map, inside data Map at index " + i + " is not empty");
			for (String key : expectedKeysInEachCustomerMap) {
				try {
					assertTrue(customerMap.containsKey(key));
					Log.info("customer Map,inside data map at index " + i + " contains the key '" + key + "'");
				} catch (Throwable t) {
					Log.error("customer MThe data map at index " + i + " do not contain the key '" + key + "'");
					Log.error(t.getMessage());
					fail(t.getMessage());
				}
			}
			i = i + 1;
		}

	}

	private static RequestSpecification addingPathParamsAndHeaderToWaterBalanceAPIRequestForAGivenDataTypeAndModel(
			String dataType, String waterBalanceModel) {
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("dma");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/waterbalance/{waterBalanceModel}/{dataType}";
		RestAssured.useRelaxedHTTPSValidation();

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement
				.setAction("Get water balance data for " + waterBalanceModel + " table with " + dataType + " data");

		ConfigFileReader configFileReader = new ConfigFileReader();
		Header xServiceUrl = new Header("X-Service-Url", configFileReader.getAPIHeaderValues("url"));
		Header xServiceTicket = new Header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket"));
		Header xServiceUser = new Header("X-Service-User", configFileReader.getAPIHeaderValues("user"));
		Map<String, Object> pathParamsMap = new HashMap<>();
		pathParamsMap.put("dataType", dataType);
		pathParamsMap.put("waterBalanceModel", waterBalanceModel);

		Headers headers = new Headers(xServiceUrl, xServiceTicket, xServiceUser);
		RequestSpecification httpRequest = RestAssured.given().headers(headers)
				.pathParams("waterBalanceModel", waterBalanceModel).pathParams(pathParamsMap);
		return httpRequest;
	}

	public static Response getWaterBalanceAPIResponseForADataTypeAndWaterBalanceModel(String dataType,
			String waterBalanceModel) {
		RequestSpecification httpRequest = addingPathParamsAndHeaderToWaterBalanceAPIRequestForAGivenDataTypeAndModel(
				dataType, waterBalanceModel);

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Zone theme - Validate Water Balance  Api for type: " + dataType
				+ " and waterBalanceModel: " + waterBalanceModel);
		httpRequest.request().log().all();
		Log.info("Created the Request for waterBalance '" + dataType + "' API for water balance model '"
				+ waterBalanceModel + "'");

		Response response = httpRequest.when().get();
		Log.info("Performing GET request on the waterBalance '" + dataType + "' API for water balance model '"
				+ waterBalanceModel + "'");
		Log.info("waterbalance '" + dataType + "' API response for water balance model '" + waterBalanceModel + "':\n"
				+ response.asString());
		return response;
	}

	public static void verifyGetWaterBalanceAPIResponseForAGivenTypeAndWaterBalanceModel(String dataType,
			String waterBalanceModel) {
		Response response = getWaterBalanceAPIResponseForADataTypeAndWaterBalanceModel(dataType, waterBalanceModel);
		assertingWaterBalanceAPIResponseForAGivenTypeAndWaterModel(response, dataType, waterBalanceModel);

	}

	public static Response getWaterBalanceAPIResponseForADataTypeAndWaterBalanceModelForDefaultDays(String dataType,
			String waterBalanceModel, String days) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		RequestSpecification httpRequest = addingPathParamsAndHeaderToWaterBalanceAPIRequestForAGivenDataTypeAndModel(
				dataType, waterBalanceModel);

		long defaultDays = Long.parseLong(days);
		long endTime = System.currentTimeMillis();
		long startTime = endTime - (24 * 60 * 60 * 1000 * defaultDays);

		List<String> zoneIdsListInQuotes = new ArrayList<>();

		for (String zoneId : zoneIdsFinalList) {
			String zoneIdAfterAddingQuote = "\"" + zoneId + "\"";
			zoneIdsListInQuotes.add(zoneIdAfterAddingQuote);
		}
		System.out.println(zoneIdsListInQuotes);

		String payload = "{\"st\":" + startTime + ",\"et\":" + endTime + ",\"dms_ids\":"
				+ zoneIdsListInQuotes + "}";

		httpRequest = httpRequest.body(payload).request().log().all();
		Log.info("Created the Request for waterBalance '" + dataType + "' API for water balance model '"
				+ waterBalanceModel + "' for default '" + days + "' days");

		Response response = httpRequest.when().post();

		Log.info("Performing POST request on the waterBalance '" + dataType + "' API for water balance model '"
				+ waterBalanceModel + "' for default '" + days + " ' days");
		Log.info("waterbalance '" + dataType + "' API response for water balance model '" + waterBalanceModel + "':\n"
				+ response.asString());

		return response;

	}

	public static void verifyGetWaterBalanceAPIResponseForAGivenTypeAndWaterBalanceModelForDefaultDays(String dataType,
			String waterBalanceModel, String days) {

		Response response = getWaterBalanceAPIResponseForADataTypeAndWaterBalanceModelForDefaultDays(dataType,
				waterBalanceModel, days);

		assertingWaterBalanceAPIResponseForAGivenTypeAndWaterModel(response, dataType, waterBalanceModel);
	}

	private static void assertingWaterBalanceAPIResponseForAGivenTypeAndWaterModel(Response response, String dataType,
			String waterBalanceModel) {

		TestRailTestManagement.setAction("Validating the status code to be " + STATUS_CODE_200);
		assertEquals(response.getStatusCode(), STATUS_CODE_200);
		Log.info("Asserted Status code for type '" + dataType + "' and water balance model '" + waterBalanceModel
				+ "' to be " + response.getStatusCode());
		JsonPath jPath = response.jsonPath();
		Map<String, Object> responseMap = jPath.getMap("$");
		// Asserting Response Map returned is not empty
		TestRailTestManagement.setAction("Validating response map is not empty");
		assertFalse(responseMap.isEmpty());
		Log.info("Asserted Response Map is not empty for getWaterBalance " + dataType
				+ " API  ,having water balance model: " + waterBalanceModel);

		// Asserting keys in Response map for a given Water balance model for get
		// given dataType ,example:waterBalanceModel :'top_to_bottom'
		// ,dataType:'metadata'

		TestRailTestManagement.setAction("Validating keys in response map for type '" + dataType
				+ "' and water balance model'" + waterBalanceModel + "'");
		assertKeysInResponseForGetWaterBalanceAPIForAWaterModel(responseMap, dataType, waterBalanceModel);

		@SuppressWarnings("unchecked")
		Map<String, Object> dataMap = (Map<String, Object>) responseMap.get(ZONE_WATER_BALANCE_RESPONSE_DATA_KEY);
		// Asserting data Map is not empty

		TestRailTestManagement.setAction("Validating the data Map is not empty");
		assertFalse(dataMap.isEmpty());
		Log.info("Asserted the Response data Map is not empty for type '" + dataType + "' and water balance model: '"
				+ waterBalanceModel + "'");

		TestRailTestManagement.setAction("Validating keys in data map for a given type '" + dataType
				+ "' and water balance model'" + waterBalanceModel + "'");
		assertingKeysInDataMapForAGivenTypeAndWaterBalanceModel(dataMap, dataType, waterBalanceModel);
		// Asserting the value of the data.key equal to 'top_to_bottom' or 'bottom_up'
		// depending upon the waterBalanceModel

		String keyValueDataMap = (String) dataMap.get(ZONE_WATER_BALANCE_DATA_COMMON_KEY_KEY);
		TestRailTestManagement
				.setAction("Validating key value for a given water balance model in data map is as expected");
		assertEquals(keyValueDataMap, waterBalanceModel);
		Log.info("Asserted the value of key ,''key' for type '" + dataType + "',for waterBalanceModel: '"
				+ waterBalanceModel + "' in data map equal to =" + keyValueDataMap);

		// For type=metadata for getWaterBalanceAPI for any model we have a key
		// ui_element in dataMap
		// Now for Type metadata we will perform assertions on ui_element Maps

		if (dataType.equalsIgnoreCase("metadata")) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> uiElementListOfMaps = (List<Map<String, Object>>) dataMap
					.get(ZONE_WATER_BALANCE_DATA_METADATA_UI_ELEMENT_KEY);
			TestRailTestManagement.setAction("Validating uiElementsListOfMaps is not empty for type " + dataType);
			assertFalse(uiElementListOfMaps.isEmpty());
			Log.info("Asserted that the ui_element list of maps for type '" + dataType + "',for water balance model: '"
					+ waterBalanceModel + "' is not empty");
			Log.info("Size of 'ui_element' list of maps=" + uiElementListOfMaps.size());

			// Asserting keys in each ui_element map for a water balance model of dataType
			// metadata
			TestRailTestManagement.setAction("Validating keys in each uiElement for type " + dataType);
			assertingKeysInUIElementListOfMapsForAWaterBalanceModelForMetaDataAPI(uiElementListOfMaps,
					waterBalanceModel);
		}

		if (dataType.equalsIgnoreCase("default") || dataType.equalsIgnoreCase("data")) {
			// top_to_bottom_data or bottom_up_data is a key in data map for default and
			// data
			//
			// our waterBalanceModel
			// is top_to_bottom or bottom_up,The value of the key is a JSONObject(Map)

			// Here we are getting the top_to_bottom_data map /bottom_up_data map based on
			// our water balance model passed from feature file

			Map<String, Object> waterBalanceModelDataMap = null;
			if (waterBalanceModel.equalsIgnoreCase("bottom_up")) {
				waterBalanceModelDataMap = jPath.getMap(
						ZONE_WATER_BALANCE_RESPONSE_DATA_KEY + "." + ZONE_WATER_BALANCE_DATA_COMMON_BOTTOM_UP_DATA_KEY);
			} else if (waterBalanceModel.equalsIgnoreCase("top_to_bottom")) {
				waterBalanceModelDataMap = jPath.getMap(ZONE_WATER_BALANCE_RESPONSE_DATA_KEY + "."
						+ ZONE_WATER_BALANCE_DATA_COMMON_TOP_TO_BOTTOM_DATA_KEY);
			}

			// Asserting Keys in top_to_bottom_data Map or bottom_up_data map based on
			// waterBalanceModel
			// for type default and data
			TestRailTestManagement.setAction("Validating keys in water balance model data map for type " + dataType
					+ " and water balance model " + waterBalanceModel);
			assertingKeysInWaterBalanceModelDataMapForAGivenWaterModelForaGivenDataType(waterBalanceModelDataMap,
					waterBalanceModel, dataType);
		}
		// Here for type data we are performing assertion specific to data
		if (dataType.equalsIgnoreCase("data")) {
			// Asserting dms_ids List is not empty
			@SuppressWarnings("unchecked")
			List<String> dmsIdsList = (List<String>) dataMap.get(ZONE_WATER_BALANCE_DATA_DMS_IDS_KEY);
			// Verifying List is not empty
			TestRailTestManagement.setAction("Validating dms_ids list is not empty for type " + dataType);
			assertFalse(dmsIdsList.isEmpty());
			Log.info("Asserted the dmsIdsList is not empty for type '" + dataType + " and water balance model: '"
					+ waterBalanceModel + "'");
			TestRailTestManagement.setAction("Validating dms_ids list contain all the zone ids for type " + dataType
					+ " and water balance model " + waterBalanceModel);
			assertingDmsIdsListContainsAllZoneIdsForDataApiForAGivenWaterBalanceModel(dmsIdsList, waterBalanceModel);
		}

		// Asserting the 'status' key value in response equal to 202
		assertEquals(jPath.getInt(ZONE_WATER_BALANCE_RESPONSE_STATUS_KEY), STATUS_CODE_202);

		Log.info("'" + ZONE_WATER_BALANCE_RESPONSE_STATUS_KEY + "' key: value successfully asserted to be "
				+ STATUS_CODE_202);

		// Asserting the statusText key value in response equal to 'Accepted'

		assertEquals(jPath.getString(ZONE_WATER_BALANCE_RESPONSE_STATUS_TEXT_KEY), STATUS_TEXT_ACCEPTED);
		Log.info("'" + ZONE_WATER_BALANCE_RESPONSE_STATUS_TEXT_KEY + "' key: value successfully asserted to be "
				+ STATUS_TEXT_ACCEPTED);
	}

	private static void assertKeysInResponseForGetWaterBalanceAPIForAWaterModel(Map<String, Object> responseMap,
			String dataType, String waterBalanceModel) {
		List<String> expectedKeysInResponseMap = new ArrayList<>();
		expectedKeysInResponseMap.add(ZONE_WATER_BALANCE_RESPONSE_DATA_KEY);
		expectedKeysInResponseMap.add(ZONE_WATER_BALANCE_RESPONSE_REQUEST_ID_KEY);
		expectedKeysInResponseMap.add(ZONE_WATER_BALANCE_RESPONSE_REQUEST_TIME_KEY);
		expectedKeysInResponseMap.add(ZONE_WATER_BALANCE_RESPONSE_RESPONSE_TIME_KEY);
		expectedKeysInResponseMap.add(ZONE_WATER_BALANCE_RESPONSE_STATUS_KEY);
		expectedKeysInResponseMap.add(ZONE_WATER_BALANCE_RESPONSE_STATUS_TEXT_KEY);
		expectedKeysInResponseMap.add(ZONE_WATER_BALANCE_RESPONSE_TIME_KEY);

		for (String key : expectedKeysInResponseMap) {
			try {
				assertTrue(responseMap.containsKey(key));
				Log.info("The Response map contains the key '" + key + "'for type '" + dataType
						+ "', water balance model: '" + waterBalanceModel + "'");
			} catch (Throwable t) {
				Log.error("The Response  map do not contain the key '" + key + "' for type '" + dataType
						+ " water balance model: '" + waterBalanceModel + "'");
				TestRailTestManagement.setAction("The Response  map do not contain the key '" + key + "' for type '"
						+ dataType + " water balance model: '" + waterBalanceModel + "'");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}
		}
	}

	private static void assertingKeysInUIElementListOfMapsForAWaterBalanceModelForMetaDataAPI(
			List<Map<String, Object>> uiElementListOfMaps, String waterBalanceModel) {
		List<String> expectedKeysInUIELementMap = new ArrayList<>();
		expectedKeysInUIELementMap.add(ZONE_WATER_BALANCE_METADATA_UI_ELEMENT_KEY_KEY);
		expectedKeysInUIELementMap.add(ZONE_WATER_BALANCE_METADATA_UI_ELEMENT_LABEL_KEY);
		expectedKeysInUIELementMap.add(ZONE_WATER_BALANCE_METADATA_UI_ELEMENT_ROW_KEY);
		expectedKeysInUIELementMap.add(ZONE_WATER_BALANCE_METADATA_UI_ELEMENT_STYLE_KEY);
		expectedKeysInUIELementMap.add(ZONE_WATER_BALANCE_METADATA_UI_ELEMENT_UNIT_KEY);

		int i = 0;
		for (Map<String, Object> uiElementMap : uiElementListOfMaps) {
			assertFalse(uiElementMap.isEmpty());
			Log.info("Asserted ui_Element Map at index " + i + " is not empty for " + "' water balance model: '"
					+ waterBalanceModel + "'");
			for (String key : expectedKeysInUIELementMap) {
				try {
					assertTrue(uiElementMap.containsKey(key));
					Log.info("The ui_element map at index " + i + " contains the key '" + key
							+ "' for water balance model: " + waterBalanceModel);
				} catch (Throwable t) {
					Log.error("The ui_element map at index " + i + " do not contain the key '" + key
							+ "' for water balance model: " + waterBalanceModel);
					Log.error(t.getMessage());
					fail(t.getMessage());
				}
			}
			i = i + 1;
		}
	}

	private static void assertingKeysInDataMapForAGivenTypeAndWaterBalanceModel(Map<String, Object> dataMap,
			String dataType, String waterBalanceModel) {
		List<String> expectedKeysInDataMap = new ArrayList<>();
		expectedKeysInDataMap.add(ZONE_WATER_BALANCE_DATA_COMMON_CLASS_KEY);
		expectedKeysInDataMap.add(ZONE_WATER_BALANCE_DATA_COMMON_KEY_KEY);
		if (dataType.equalsIgnoreCase("metadata")) {
			expectedKeysInDataMap.add(ZONE_WATER_BALANCE_DATA_METADATA_ID_KEY);
			expectedKeysInDataMap.add(ZONE_WATER_BALANCE_DATA_METADATA_UI_ELEMENT_KEY);
		} else if (dataType.equalsIgnoreCase("default") || dataType.equalsIgnoreCase("data")) {

			expectedKeysInDataMap.add(ZONE_WATER_BALANCE_DATA_COMMON_CUSTOMER_ID_KEY);
			if (waterBalanceModel.equalsIgnoreCase("bottom_up")) {
				expectedKeysInDataMap.add(ZONE_WATER_BALANCE_DATA_COMMON_BOTTOM_UP_DATA_KEY);
			} else if (waterBalanceModel.equalsIgnoreCase("top_to_bottom")) {
				expectedKeysInDataMap.add(ZONE_WATER_BALANCE_DATA_COMMON_TOP_TO_BOTTOM_DATA_KEY);
			}
			if (dataType.equalsIgnoreCase("default")) {
				expectedKeysInDataMap.add(ZONE_WATER_BALANCE_DATA_DEFAULT_REQUEST_TYPE_KEY);
			} else if (dataType.equalsIgnoreCase("data")) {
				expectedKeysInDataMap.add(ZONE_WATER_BALANCE_DATA_DMS_IDS_KEY);
				expectedKeysInDataMap.add(ZONE_WATER_BALANCE_DATA_ET_KEY);
				expectedKeysInDataMap.add(ZONE_WATER_BALANCE_DATA_ST_KEY);
			}

		}
		for (String key : expectedKeysInDataMap) {
			try {
				assertTrue(dataMap.containsKey(key));
				Log.info("The data map contains the key '" + key + "' for dataType '" + dataType
						+ "' and water balance model:" + "'" + waterBalanceModel + "'");
			} catch (Throwable t) {
				Log.error("The data map do not contain the key '" + key + "' dataType '" + dataType
						+ "' for water balance model: '" + waterBalanceModel + "'");
				TestRailTestManagement.setAction("The data map do not contain the key '" + key + "' dataType '"
						+ dataType + "' for water balance model: '" + waterBalanceModel + "'");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}
		}
	}

	private static void assertingKeysInWaterBalanceModelDataMapForAGivenWaterModelForaGivenDataType(
			Map<String, Object> waterBalanceModelDataMap, String waterBalanceModel, String dataType) {
		List<String> expectedKeysInWaterModelDataMap = new ArrayList<>();
		String mapName = null;
		// Adding the expected keys present in bottom_up_data map in
		// response($.data.bottom_up_data)
		if (dataType.equalsIgnoreCase("default") || dataType.equalsIgnoreCase("data")) {
			if (waterBalanceModel.equalsIgnoreCase("bottom_up")) {
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_ASSESSED_NON_RESIDENTIAL_NIGHT_USE_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_ASSESSED_RESIDENTIAL_NIGHT_USE_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_EXCEPTIONAL_NIGHT_USE_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_INSIDE_BUILDINGS_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_MINIMUM_NIGHT_FLOW_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_NIGHT_DAY_FACTOR_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_OUTSIDE_BUILDINGS_KEY);
				mapName = ZONE_WATER_BALANCE_DATA_COMMON_BOTTOM_UP_DATA_KEY;
			}
			// Adding the expected keys present in top_to_bottom_data map in response
			// ($.data.top_to_bottom_data)
			else if (waterBalanceModel.equalsIgnoreCase("top_to_bottom")) {
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_BILLED_METERED_CONSUMPTION_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_BILLED_UNMETERED_CONSUMPTION_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_CUSTOMER_METER_INNACURACIES_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_SYSTEM_INPUT_VOLUME_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_UNAUTHORIZED_CONSUMPTION_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_UNBILLED_METERED_CONSUMPTION_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DEFAULT_UNBILLED_UNMETERED_CONSUMPTION_KEY);
				mapName = ZONE_WATER_BALANCE_DATA_COMMON_TOP_TO_BOTTOM_DATA_KEY;
			}
		}
		if (dataType.equalsIgnoreCase("data")) {
			if (waterBalanceModel.equalsIgnoreCase("bottom_up")) {

				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_BACKGROUND_LEAKAGE_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_BURSTS_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_CUSTOMER_NIGHT_LEAKAGE_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_NIGHT_CONSUMPTION_NC_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_NIGHT_USE_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_ON_SERVICE_CONNS_ON_MAINS_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_REPORTED_BURSTS_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_UNREPORTED_BURSTS_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_UTILITY_DAILY_LEAKAGE_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_UTILITY_NIGHT_LEAKAGE_KEY);
			} else if (waterBalanceModel.equalsIgnoreCase("top_to_bottom")) {
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_AUTHORIZED_CONSUMPTION_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_BILLED_AUTHORIZED_CONSUMPTION_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_COMMERCIAL_LOSSES_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_NON_REVENUE_WATER_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_PHYSICAL_LOSSES_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_REVENUE_WATER_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_UNBILLED_AUTHORIZED_CONSUMPTION_KEY);
				expectedKeysInWaterModelDataMap.add(ZONE_WATER_BALANCE_DATA_WATER_LOSSES_KEY);
			}
		}

		for (String key : expectedKeysInWaterModelDataMap) {
			try {
				assertTrue(waterBalanceModelDataMap.containsKey(key));
				Log.info("The " + mapName + "  map contains the key '" + key + "' for water balance model: "
						+ waterBalanceModel);
			} catch (Throwable t) {
				Log.error("The " + mapName + " map do not contain the key '" + key + "' for water balance model: "
						+ waterBalanceModel);
				TestRailTestManagement.setAction("The " + mapName + " map do not contain the key '" + key
						+ "' for water balance model: " + waterBalanceModel);
				Log.error(t.getMessage());
				fail(t.getMessage());
			}
		}

	}

	private static void assertingDmsIdsListContainsAllZoneIdsForDataApiForAGivenWaterBalanceModel(
			List<String> dmsIdsList, String waterBalanceModel) {
		// Asserting size of dms_ids is as expected

		assertEquals(dmsIdsList, zoneIdsFinalList);
		Log.info("Asserted successfully the dms_ids list size is same as expected =" + dmsIdsList.size());

		// Asserting the dms_ids List contains all the expected zone Ids

		for (String zoneId : zoneIdsFinalList) {
			try {
				assertTrue(dmsIdsList.contains(zoneId));
				Log.info("dms_ids List contains '" + zoneId + "' for data api,having water balance model "
						+ waterBalanceModel);

			} catch (Throwable t) {
				Log.error("dms_ids List do not contain '" + zoneId + "' for 'data' api,having water balance model "
						+ waterBalanceModel);
				TestRailTestManagement.setAction("dms_ids List do not contain '" + zoneId
						+ "' for 'data' api,having water balance model " + waterBalanceModel);
				Log.error(t.getMessage());
				fail(t.getMessage());
			}

		}
	}

	public static Response getStatisticsAllAPIResponseForAListOfZoneIdsForDefaultDays(String days,
			List<String> zoneIds) {

		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("dma");

		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/statistics/all";
		RestAssured.useRelaxedHTTPSValidation();

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Zone theme - Validate statistics for all zones : " + zoneIds);
		TestRailTestManagement.setTestComment("Validating statistics for all zones : " + zoneIds
				+ System.lineSeparator() + System.lineSeparator() + " Updated by Automation Framework! ");

		long defaultDays = Long.parseLong(days);
		long endTime = System.currentTimeMillis();
		long startTime = endTime - (24 * 60 * 60 * 1000 * defaultDays);

		ConfigFileReader configFileReader = new ConfigFileReader();
		Header xServiceUrl = new Header("X-Service-Url", configFileReader.getAPIHeaderValues("url"));
		Header xServiceTicket = new Header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket"));

		Header xServiceUser = new Header("X-Service-User", configFileReader.getAPIHeaderValues("user"));

		Headers headers = new Headers(xServiceUrl, xServiceTicket, xServiceUser);

		String payload = "{\"request\":{\"st\":" + startTime + ",\"et\":" + endTime + ",\"zone_ids\":" + zoneIds + "}}";

		RequestSpecification httpRequest = RestAssured.given().headers(headers).contentType(ContentType.HTML)
				.body(payload).request().log().all();

		Log.info("Created the Request for statistics/all  API for zoneIds\n" + zoneIds);

		Response response = httpRequest.when().post();
		Log.info("Performing post request on the statistics/all APi end point for zoneIds:\n" + zoneIds);
		Log.info("Response for statistics/all  API for zoneIds:\n" + zoneIds + "\n " + response.asString());
		return response;

	}

	public static void verifyStatisticsAllAPIResponseForListOfZoneIdsForDefaultDays(String days, DataTable zoneIds) {
		TestRailTestManagement.setTestComment("Updated by Automation Framework!");

		List<String> zoneIdsList = zoneIds.asList(String.class);
		Response response = getStatisticsAllAPIResponseForAListOfZoneIdsForDefaultDays(days, zoneIdsList);

		// Getting response
		Log.info("FOLLOWING ASSERTIONS in the  statistics/all api Response for the below list of zone IDs:\n "
				+ zoneIdsList);

		assertEquals(response.getStatusCode(), STATUS_CODE_200);
		Log.info("Asserted Status code for statistics/all api  to be " + response.getStatusCode());
		JsonPath jPath = response.jsonPath();

		Map<String, Object> responseMap = jPath.getMap("$");
		// Asserting Response Map returned is not empty
		assertFalse(responseMap.isEmpty());
		Log.info("Asserted Response Map is not empty ");

		// Asserting the keys in Response object for statistics/all api
		assertingKeysInResponseForStatisticsAllAPI(responseMap);

		@SuppressWarnings("unchecked")
		Map<String, Object> dataMap = (Map<String, Object>) responseMap.get(ZONE_STATISTICS_ALL_RESPONSE_DATA_KEY);
		// Asserting data Map is not empty
		assertFalse(dataMap.isEmpty());

		Log.info("Asserted the  data Map is not empty");

		// Asserting the keys in data object ($.data) for statistics/all api for a list
		// of zoneIds

		assertingKeysInDataMapForStatisticsAllAPI(dataMap);

		// Asserting the columnBands array is not empty($.data.columnBands)

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> columnBandsListOfMaps = (List<Map<String, Object>>) dataMap
				.get(ZONE_STATISTICS_ALL_DATA_COLUMN_BANDS_KEY);
		assertFalse(columnBandsListOfMaps.isEmpty());

		Log.info("Asserted the columnBandsList is not empty");

		// Asserting keys in columnBands Map ($.data.columnBands[0])

		assertingKeysinColumBandsMapForStatisticsAllAPI(columnBandsListOfMaps);

		// Asserting columnBands children list values
		// $.data.columnBands[0].children

		assertingValuesInColumnBandsChildrenListForStatisticsAllAPI(columnBandsListOfMaps);

		// Asserting the labelMapping map is not empty
		// $.data.labelMappingMap
		@SuppressWarnings("unchecked")
		Map<String, Object> labelMappingMap = (Map<String, Object>) dataMap
				.get(ZONE_STATISTICS_ALL_DATA_LABEL_MAPPING_KEY);
		assertFalse(labelMappingMap.isEmpty());

		Log.info("Asserted the labelMapping map is not empty ");

		// asserting keys in labelMapping map
		assertingKeysInLabelMappingMapForStatisticsAllAPI(labelMappingMap);
		// Asserting keys in each map inside label mapping Map
		// Here, asserting keys in $.data.labelMapping.Consumption_3

		assertingKeysForAMapInsideLabelMappingMapForStatisticsAllAPI(labelMappingMap,
				ZONE_STATISTICS_ALL_LABEL_MAPPING_CONSUMPTION_3_KEY);
		// Here, asserting keys in $.data.labelMapping.Demand_4
		assertingKeysForAMapInsideLabelMappingMapForStatisticsAllAPI(labelMappingMap,
				ZONE_STATISTICS_ALL_LABEL_MAPPING_DEMAND_4_KEY);

		// Here,asserting keys in $.data.labelMapping.Flow_1
		assertingKeysForAMapInsideLabelMappingMapForStatisticsAllAPI(labelMappingMap,
				ZONE_STATISTICS_ALL_LABEL_MAPPING_FLOW_1_KEY);

		// Here,asserting keys in $.data.labelMapping.Others_5
		assertingKeysForAMapInsideLabelMappingMapForStatisticsAllAPI(labelMappingMap,
				ZONE_STATISTICS_ALL_LABEL_MAPPING_OTHERS_5_KEY);

		// Here,asserting keys in $.data.labelMapping.Pressure_2

		assertingKeysForAMapInsideLabelMappingMapForStatisticsAllAPI(labelMappingMap,
				ZONE_STATISTICS_ALL_LABEL_MAPPING_PRESSURE_2_KEY);

		// Asserting the label values for the statistic type Consumption_3
		assertingLabeValuesForAGivenStatisticTypeForStatisticAllAPI(jPath,
				ZONE_STATISTICS_ALL_LABEL_MAPPING_CONSUMPTION_3_KEY);

		// Asserting the label values for the statistic type Demand_4
		assertingLabeValuesForAGivenStatisticTypeForStatisticAllAPI(jPath,
				ZONE_STATISTICS_ALL_LABEL_MAPPING_DEMAND_4_KEY);

		// Asserting the label values for all statistics of type Flow_1
		assertingLabeValuesForAGivenStatisticTypeForStatisticAllAPI(jPath,
				ZONE_STATISTICS_ALL_LABEL_MAPPING_FLOW_1_KEY);

		// Asserting the label value of all statistics of type Others_5

		assertingLabeValuesForAGivenStatisticTypeForStatisticAllAPI(jPath,
				ZONE_STATISTICS_ALL_LABEL_MAPPING_OTHERS_5_KEY);

		// Asserting the label value of all statistics of type Pressure_2

		assertingLabeValuesForAGivenStatisticTypeForStatisticAllAPI(jPath,
				ZONE_STATISTICS_ALL_LABEL_MAPPING_PRESSURE_2_KEY);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> statisticsListOfMaps = (List<Map<String, Object>>) dataMap
				.get(ZONE_STATISTICS_ALL_DATA_STATISTICS_LIST_KEY);

		// Asserting statisticsListOfMaps is not empty

		assertFalse(statisticsListOfMaps.isEmpty());

		Log.info("Asserted the statisticsList of Maps is not empty");

		// Size of statisticsList should be same as that the size of Zone IdsList

		assertEquals(statisticsListOfMaps.size(), zoneIdsList.size());

		Log.info("The '" + ZONE_STATISTICS_ALL_DATA_STATISTICS_LIST_KEY + "' size: " + statisticsListOfMaps.size()
				+ " is equal to the size of zone ids List");

		// Asserting each inner Map in statisticsList of Maps is not empty

		assertingKeysInEachStatisticsListMapForStatisticsAllAPI(statisticsListOfMaps);

		assertingKeysInEachStatisticsMapForAStatisticsList(statisticsListOfMaps);

	}

	private static void assertingKeysInResponseForStatisticsAllAPI(Map<String, Object> responseMap) {
		List<String> expectedKeysInResponseMap = new ArrayList<>();
		expectedKeysInResponseMap.add(ZONE_STATISTICS_ALL_RESPONSE_DATA_KEY);
		expectedKeysInResponseMap.add(ZONE_STATISTICS_ALL_RESPONSE_REQUEST_ID_KEY);
		expectedKeysInResponseMap.add(ZONE_STATISTICS_ALL_RESPONSE_REQUEST_TIME_KEY);
		expectedKeysInResponseMap.add(ZONE_STATISTICS_ALL_RESPONSE_RESPONSE_TIME_KEY);
		expectedKeysInResponseMap.add(ZONE_STATISTICS_ALL_RESPONSE_STATUS_KEY);
		expectedKeysInResponseMap.add(ZONE_STATISTICS_ALL_RESPONSE_STATUS_TEXT_KEY);
		expectedKeysInResponseMap.add(ZONE_STATISTICS_ALL_RESPONSE_TIME_KEY);

		for (String key : expectedKeysInResponseMap) {
			try {
				assertTrue(responseMap.containsKey(key));
				Log.info("The Response map contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The Response  map  do not contain the key '" + key + "' ");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}
		}

	}

	private static void assertingKeysInDataMapForStatisticsAllAPI(Map<String, Object> dataMap) {
		List<String> expectedKeysInDataMap = new ArrayList<>();
		expectedKeysInDataMap.add(ZONE_STATISTICS_ALL_DATA_CLASS_KEY);
		expectedKeysInDataMap.add(ZONE_STATISTICS_ALL_DATA_COLUMN_BANDS_KEY);
		expectedKeysInDataMap.add(ZONE_STATISTICS_ALL_DATA_LABEL_MAPPING_KEY);
		expectedKeysInDataMap.add(ZONE_STATISTICS_ALL_DATA_STATISTICS_LIST_KEY);

		for (String key : expectedKeysInDataMap) {
			try {
				assertTrue(dataMap.containsKey(key));
				Log.info("The Data map  contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The Data  map do not contain the key '" + key + "' ");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}
		}

	}

	private static void assertingKeysinColumBandsMapForStatisticsAllAPI(
			List<Map<String, Object>> columnBandsListOfMaps) {
		List<String> expectedKeysInColumnBandsMap = new ArrayList<>();
		expectedKeysInColumnBandsMap.add(ZONE_STATISTICS_ALL_COLUMN_BANDS_CHILDREN_KEY);
		expectedKeysInColumnBandsMap.add(ZONE_STATISTICS_ALL_COLUMN_BANDS_JOIN_BY);

		int i = 0;
		for (Map<String, Object> columnBandsMap : columnBandsListOfMaps) {
			// Asserting each map in columnBandsListOfMaps is not empty

			assertFalse(columnBandsMap.isEmpty());
			Log.info("Asserted  columnBandsMap at index " + i + " is not empty");

			for (String key : expectedKeysInColumnBandsMap) {

				try {
					assertTrue(columnBandsMap.containsKey(key));
					Log.info("The columnBands map at index " + i + " contains the key '" + key + "'");
				} catch (Throwable t) {
					Log.error("The columnBands  map at index " + i + " do not contain the key '" + key + "' ");
					Log.error(t.getMessage());
					fail(t.getMessage());
				}

			}
			i++;

		}
	}

	private static void assertingValuesInColumnBandsChildrenListForStatisticsAllAPI(
			List<Map<String, Object>> columnBandsListOfMaps) {
		// Asserting the columnBands childrenList is not empty
		List<String> expectedValuesInChildrenList = new ArrayList<>();
		expectedValuesInChildrenList.add(ZONE_STATISTICS_ALL_CHILDREN_CURRENT_DEMAND_VALUE);
		expectedValuesInChildrenList.add(ZONE_STATISTICS_ALL_CHILDREN_DEMAND_CONFIDENCE_VALUE);

		int i = 0;
		for (Map<String, Object> columnBandsMap : columnBandsListOfMaps) {
			@SuppressWarnings("unchecked")
			List<String> children = (List<String>) columnBandsMap.get(ZONE_STATISTICS_ALL_COLUMN_BANDS_CHILDREN_KEY);
			assertFalse(children.isEmpty());
			Log.info("The children list for columnBands map at index " + i + " is not empty");

			for (String value : expectedValuesInChildrenList)
				try {
					assertTrue(children.contains(value));
					Log.info("For columnBands map at index " + i + ",the children list contains the expected value '"
							+ value);
				} catch (Throwable t) {
					Log.error("For columnBands  map at index " + i
							+ ",the children list do not contain the expected value '" + value);
					Log.error(t.getMessage());
					fail(t.getMessage());
				}

		}

	}

	private static void assertingKeysInLabelMappingMapForStatisticsAllAPI(Map<String, Object> labelMappingMap) {
		List<String> expectedKeysInLabelMappingMap = new ArrayList<>();
		expectedKeysInLabelMappingMap.add(ZONE_STATISTICS_ALL_LABEL_MAPPING_CONSUMPTION_3_KEY);
		expectedKeysInLabelMappingMap.add(ZONE_STATISTICS_ALL_LABEL_MAPPING_DEMAND_4_KEY);
		expectedKeysInLabelMappingMap.add(ZONE_STATISTICS_ALL_LABEL_MAPPING_FLOW_1_KEY);
		expectedKeysInLabelMappingMap.add(ZONE_STATISTICS_ALL_LABEL_MAPPING_OTHERS_5_KEY);
		expectedKeysInLabelMappingMap.add(ZONE_STATISTICS_ALL_LABEL_MAPPING_PRESSURE_2_KEY);

		for (String key : expectedKeysInLabelMappingMap) {
			try {
				assertTrue(labelMappingMap.containsKey(key));
				Log.info("The labelMapping map contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The labelMapping map do not contain the key '" + key + "' ");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}
		}

	}

	private static void assertingKeysForAMapInsideLabelMappingMapForStatisticsAllAPI(
			Map<String, Object> labelMappingMap, String mapName) {

		List<String> expectedKeys = new ArrayList<>();
		switch (mapName) {
			case ZONE_STATISTICS_ALL_LABEL_MAPPING_CONSUMPTION_3_KEY:
				expectedKeys.add(ZONE_STATISTICS_ALL_AGGREGATE_CONSUMPTION_MECHANCAL_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_AGGREGATE_CONSUMPTION_RATE_SMART_KEY);
				break;
			case ZONE_STATISTICS_ALL_LABEL_MAPPING_DEMAND_4_KEY:
				expectedKeys.add(ZONE_STATISTICS_ALL_CURRENT_DEMAND_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_DEMAND_CONFIDENCE_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_MINIMUM_NIGHT_DEMAND_KEY);
				break;
			case ZONE_STATISTICS_ALL_LABEL_MAPPING_FLOW_1_KEY:
				expectedKeys.add(ZONE_STATISTICS_ALL_MINIMUM_NIGHT_INFLOW_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_MINIMUM_NIGHT_OUTFLOW_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_TOTAL_INFLOW_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_TOTAL_OUTFLOW_KEY);
				break;
			case ZONE_STATISTICS_ALL_LABEL_MAPPING_OTHERS_5_KEY:
				expectedKeys.add(ZONE_STATISTICS_ALL_AGGREGATE_IN_VOLUME_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_AGGREGATE_OUT_VOLUME_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_AGGREGATE_TOTAL_VOLUME_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_MASS_BALANCE_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_NUMBER_OF_ALERTS_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_NUMBER_OF_CONNECTIONS_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_TOTAL_IN_VOLUME_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_TOTAL_OUT_VOLUME_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_TOTAL_VOLUME_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_TOTAL_VOLUME_OVER_TIME_KEY);
				break;
			case ZONE_STATISTICS_ALL_LABEL_MAPPING_PRESSURE_2_KEY:
				expectedKeys.add(ZONE_STATISTICS_ALL_AVERAGE_PRESSURE_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_MAX_NIGHT_AVERAGE_PRESSURE_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_MAX_PRESSURE_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_MIN_DAY_AVERAGE_PRESSURE_KEY);
				expectedKeys.add(ZONE_STATISTICS_ALL_MIN_PRESSURE_KEY);
				break;
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) labelMappingMap.get(mapName);
		assertFalse(map.isEmpty());
		Log.info("The map " + mapName + " is not empty");
		for (String key : expectedKeys)
			try {
				assertTrue(map.containsKey(key));
				Log.info("The map " + mapName + " contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The map " + mapName + " do not contain the key '" + key + "' ");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}

	}

	private static void assertingLabeValuesForAGivenStatisticTypeForStatisticAllAPI(JsonPath jPath,
			String statisticType) {

		Map<String, String> statisticKeyValueMapping = new HashMap<String, String>();

		switch (statisticType) {
			case ZONE_STATISTICS_ALL_LABEL_MAPPING_CONSUMPTION_3_KEY:
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_AGGREGATE_CONSUMPTION_MECHANCAL_KEY,
						ZONE_STATISTICS_ALL_AGGREGATE_CONSUMPTION_MECHANCAL_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_AGGREGATE_CONSUMPTION_RATE_SMART_KEY,
						ZONE_STATISTICS_ALL_AGGREGATE_CONSUMPTION_RATE_SMART_LABEL_VALUE);

				break;
			case ZONE_STATISTICS_ALL_LABEL_MAPPING_DEMAND_4_KEY:
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_CURRENT_DEMAND_KEY,
						ZONE_STATISTICS_ALL_CURRENT_DEMAND_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_DEMAND_CONFIDENCE_KEY,
						ZONE_STATISTICS_ALL_DEMAND_CONFIDENCE_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_MINIMUM_NIGHT_DEMAND_KEY,
						ZONE_STATISTICS_ALL_MINIMUM_NIGHT_DEMAND_LABEL_VALUE);
				break;
			case ZONE_STATISTICS_ALL_LABEL_MAPPING_FLOW_1_KEY:
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_MINIMUM_NIGHT_INFLOW_KEY,
						ZONE_STATISTICS_ALL_MINIMUM_NIGHT_INFLOW_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_MINIMUM_NIGHT_OUTFLOW_KEY,
						ZONE_STATISTICS_ALL_MINIMUM_NIGHT_OUTFLOW_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_TOTAL_INFLOW_KEY,
						ZONE_STATISTICS_ALL_TOTAL_INFLOW_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_TOTAL_OUTFLOW_KEY,
						ZONE_STATISTICS_ALL_TOTAL_OUTFLOW_LABEL_VALUE);
				break;
			case ZONE_STATISTICS_ALL_LABEL_MAPPING_OTHERS_5_KEY:
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_AGGREGATE_IN_VOLUME_KEY,
						ZONE_STATISTICS_ALL_AGGREGATE_IN_VOLUME_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_AGGREGATE_OUT_VOLUME_KEY,
						ZONE_STATISTICS_ALL_AGGREGATE_OUT_VOLUME_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_AGGREGATE_TOTAL_VOLUME_KEY,
						ZONE_STATISTICS_ALL_AGGREGATE_TOTAL_VOLUME_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_MASS_BALANCE_KEY,
						ZONE_STATISTICS_ALL_MASS_BALANCE_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_NUMBER_OF_ALERTS_KEY,
						ZONE_STATISTICS_ALL_NUMBER_OF_ALERTS_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_NUMBER_OF_CONNECTIONS_KEY,
						ZONE_STATISTICS_ALL_NUMBER_OF_CONNECTIONS_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_TOTAL_IN_VOLUME_KEY,
						ZONE_STATISTICS_ALL_TOTAL_IN_VOLUME_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_TOTAL_OUT_VOLUME_KEY,
						ZONE_STATISTICS_ALL_TOTAL_OUT_VOLUME_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_TOTAL_VOLUME_KEY,
						ZONE_STATISTICS_ALL_TOTAL_VOLUME_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_TOTAL_VOLUME_OVER_TIME_KEY,
						ZONE_STATISTICS_ALL_TOTAL_VOLUME_OVER_TIME_LABEL_VALUE);
				break;
			case ZONE_STATISTICS_ALL_LABEL_MAPPING_PRESSURE_2_KEY:
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_AVERAGE_PRESSURE_KEY,
						ZONE_STATISTICS_ALL_AVERAGE_PRESSURE_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_MAX_NIGHT_AVERAGE_PRESSURE_KEY,
						ZONE_STATISTICS_ALL_MAX_NIGHT_AVERAGE_PRESSURE_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_MAX_PRESSURE_KEY,
						ZONE_STATISTICS_ALL_MAX_PRESSURE_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_MIN_DAY_AVERAGE_PRESSURE_KEY,
						ZONE_STATISTICS_ALL_MIN_DAY_AVERAGE_PRESSURE_LABEL_VALUE);
				statisticKeyValueMapping.put(ZONE_STATISTICS_ALL_MIN_PRESSURE_KEY,
						ZONE_STATISTICS_ALL_MIN_PRESSURE_LABEL_VALUE);

				break;
		}

		for (Map.Entry<String, String> entry : statisticKeyValueMapping.entrySet()) {
			Map<String, Object> statMap = jPath.get(ZONE_STATISTICS_ALL_RESPONSE_DATA_KEY + "."
					+ ZONE_STATISTICS_ALL_DATA_LABEL_MAPPING_KEY + "." + statisticType + "." + entry.getKey());
			assertFalse(statMap.isEmpty());
			Log.info("The map " + entry.getKey() + " is not empty");
			try {

				assertTrue(statMap.containsKey(ZONE_STATISTICS_ALL_MAP_KEY));
				Log.info("The map " + entry.getKey() + " contains the key '" + ZONE_STATISTICS_ALL_MAP_KEY + "'");
			} catch (Throwable t) {
				Log.error(
						"The map " + entry.getKey() + " do not contain the key '" + ZONE_STATISTICS_ALL_MAP_KEY + "' ");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}

			Map<String, Object> statInnerMap = jPath
					.get(ZONE_STATISTICS_ALL_RESPONSE_DATA_KEY + "." + ZONE_STATISTICS_ALL_DATA_LABEL_MAPPING_KEY + "."
							+ statisticType + "." + entry.getKey() + "." + ZONE_STATISTICS_ALL_MAP_KEY);
			assertFalse(statInnerMap.isEmpty());
			Log.info("The map: map " + "for statistic " + entry.getKey() + "  is not empty");
			try {

				assertTrue(statInnerMap.containsKey(ZONE_STATISTICS_ALL_MAP_LABEL_KEY));
				Log.info("The map:map " + "for statistic " + entry.getKey() + " contains the key '"
						+ ZONE_STATISTICS_ALL_MAP_LABEL_KEY + "'");
			} catch (Throwable t) {
				Log.error("The map " + entry.getKey() + " do not contain the key '" + ZONE_STATISTICS_ALL_MAP_LABEL_KEY
						+ "' ");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}

			String statisticLabelValue = (String) statInnerMap.get(ZONE_STATISTICS_ALL_MAP_LABEL_KEY);

			try {

				assertEquals(statisticLabelValue, entry.getValue());
				Log.info("The actual statistic label value: '" + statisticLabelValue + "' for statistic '"
						+ entry.getKey() + "' matches with expected: '" + entry.getValue() + "'");
			} catch (Throwable t) {
				Log.error("The actual statistic label value " + statisticLabelValue + " for statistic '"
						+ entry.getKey() + "' do not match with expected " + entry.getValue());
				Log.error(t.getMessage());
				fail(t.getMessage());
			}

		}

	}

	private static void assertingKeysInEachStatisticsListMapForStatisticsAllAPI(
			List<Map<String, Object>> statisticsListOfMaps) {

		List<String> expectedKeys = new ArrayList<>();
		expectedKeys.add(ZONE_STATISTICS_ALL_STATISTICS_LIST_STATISTICS_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_STATISTICS_LIST_ZONE_ID_KEY);
		int i = 0;
		for (Map<String, Object> statisticMap : statisticsListOfMaps) {

			// Asserting each inner Map in statisticsList of Maps is not empty
			assertFalse(statisticMap.isEmpty());
			Log.info("Asserted  statisticsList map at index " + i + " is not empty");
			i++;
			for (String key : expectedKeys) {
				try {
					assertTrue(statisticMap.containsKey(key));
					Log.info("The statisticList map at index " + i + " contains the key '" + key + "'");
				} catch (Throwable t) {
					Log.error("The statisticsList map at index " + i + " do not contain the key '" + key + "'");
					Log.error(t.getMessage());
					fail(t.getMessage());
				}

			}

			i++;

		}

	}

	private static void assertingKeysInEachStatisticsMapForAStatisticsList(
			List<Map<String, Object>> statisticsListOfMaps) {

		List<String> expectedKeys = new ArrayList<>();

		expectedKeys.add(ZONE_STATISTICS_ALL_AGGREGATE_CONSUMPTION_MECHANCAL_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_AGGREGATE_CONSUMPTION_RATE_SMART_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_CURRENT_DEMAND_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_DEMAND_CONFIDENCE_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_MINIMUM_NIGHT_DEMAND_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_MINIMUM_NIGHT_INFLOW_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_MINIMUM_NIGHT_OUTFLOW_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_TOTAL_INFLOW_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_TOTAL_OUTFLOW_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_AGGREGATE_IN_VOLUME_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_AGGREGATE_OUT_VOLUME_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_AGGREGATE_TOTAL_VOLUME_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_MASS_BALANCE_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_NUMBER_OF_ALERTS_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_NUMBER_OF_CONNECTIONS_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_SMART_METER_PERCENTAGE_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_TOTAL_IN_VOLUME_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_TOTAL_OUT_VOLUME_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_TOTAL_VOLUME_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_TOTAL_VOLUME_OVER_TIME_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_AVERAGE_PRESSURE_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_MAX_NIGHT_AVERAGE_PRESSURE_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_MAX_PRESSURE_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_MIN_DAY_AVERAGE_PRESSURE_KEY);
		expectedKeys.add(ZONE_STATISTICS_ALL_MIN_PRESSURE_KEY);

		int i = 0;
		for (Map<String, Object> map : statisticsListOfMaps) {

			@SuppressWarnings("unchecked")
			Map<String, Object> statisticsMap = (Map<String, Object>) map
					.get(ZONE_STATISTICS_ALL_STATISTICS_LIST_STATISTICS_KEY);

			// data.statisticsList[*].statistics
			// Asserting each statistics Map is not empty
			assertFalse(statisticsMap.isEmpty());
			Log.info("Asserted the statistics Map at index " + i + " is not empty");

			for (String key : expectedKeys) {
				try {
					// data.statisticsList[*].statistics ,we are asserting the keys in statistics
					// map
					assertTrue(statisticsMap.containsKey(key));
					Log.info("The statistics map at index " + i + " contains the key '" + key + "'");
				} catch (Throwable t) {
					Log.error("The statistics map at index " + i + " do not contain the key '" + key + "'");
					Log.error(t.getMessage());
					fail(t.getMessage());
				}

			}

			i++;

		}

	}

	public static void gettingZoneIdsFromGetCustomerInfoAPIResponse() {
		Response response = getCustomerInfoAPIResponseForAGivenCustomer();

		TestRailTestManagement.setAction("Zone theme - Retrieving zone IDs from customer Info API");

		JsonPath jPath = response.jsonPath();
		List<String> networkIds = jPath.get("data.network._id");
		List<String> zoneIds = jPath.get("data.zones._id");

		// Adding network id and all zone ids to this List
		zoneIdsFinalList.addAll(networkIds);
		zoneIdsFinalList.addAll(zoneIds);

	}

	public static Response getKMLAPIResponseForAllZoneIds() {
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("gis");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/get/kml";

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);

		ConfigFileReader configFileReader = new ConfigFileReader();
		Log.info("All the zone ids for the customer " + configFileReader.getCustomerID() + " is:\n" + zoneIdsFinalList);

		RestAssured.useRelaxedHTTPSValidation();
		// Since we have queryParameter name 'zoneId' for all ,we will use the
		// queryParam
		// method which takes in name
		// and List as Arguments
		RequestSpecification httpRequest = RestAssured.given().queryParam("zoneId", zoneIdsFinalList).request().log()
				.all();
		Log.info("Created the Request for getKml API");

		Response response = httpRequest.when().get();
		Log.info("Performing GET request on the getKml API ");
		Log.info("getKml API response\n" + response.asString());

		return response;

	}

	public static void verifyGetKMLAPIResponseForAGivenCustomer() {
		Response response = getKMLAPIResponseForAllZoneIds();
		// Getting response
		assertEquals(response.getStatusCode(), STATUS_CODE_200);
		Log.info("Asserted Status code to be " + response.getStatusCode());
		JsonPath jPath = response.jsonPath();
		Map<String, Object> responseMap = jPath.getMap("$");
		// Asserting Response Map returned is not empty
		assertFalse(responseMap.isEmpty());
		Log.info("Asserted Response Map is not empty");
		assertingKeysInGetKMLAPIResponseMap(responseMap);
		int status_codeValue = jPath.getInt(ZONE_GET_KML_RESPONSE_STATUS_CODE_KEY);
		assertEquals(status_codeValue, STATUS_CODE_200);
		Log.info("Asserted the " + ZONE_GET_KML_RESPONSE_STATUS_KEY + " value to be:" + status_codeValue);

		// Asserting data List of Maps is not empty
		List<Map<String, Object>> responseDataListOfMaps = jPath.getList(ZONE_GET_KML_RESPONSE_DATA_KEY);
		assertFalse(responseDataListOfMaps.isEmpty());
		Log.info("Asserted  response data List of Maps is not empty");
		Log.info("The size of the data List of Maps =" + responseDataListOfMaps.size());

		assertingKeysInEachDataMapForGetKMLAPI(responseDataListOfMaps);

		// Asserting Meta Map is not empty
		Map<String, String> jsonResponseMetaMap = jPath.getMap(ZONE_GET_KML_RESPONSE_META_KEY);
		assertFalse(jsonResponseMetaMap.isEmpty());
		Log.info("Asserted Meta map in response is non empty");

		String statusValue = jPath.getString(ZONE_GET_KML_RESPONSE_STATUS_KEY);
		assertEquals(statusValue, ZONE_GET_KML_RESPONSE_STATUS_VALUE);
		Log.info(
				"Successfully asserted the value of key:" + ZONE_GET_KML_RESPONSE_STATUS_KEY + " to be " + statusValue);
	}

	private static void assertingKeysInGetKMLAPIResponseMap(Map<String, Object> responseMap) {
		List<String> expectedKeys = new ArrayList<>();
		expectedKeys.add(ZONE_GET_KML_RESPONSE_STATUS_CODE_KEY);
		expectedKeys.add(ZONE_GET_KML_RESPONSE_DATA_KEY);
		expectedKeys.add(ZONE_GET_KML_RESPONSE_META_KEY);
		expectedKeys.add(ZONE_GET_KML_RESPONSE_STATUS_KEY);

		for (String key : expectedKeys) {
			try {

				assertTrue(responseMap.containsKey(key));
				Log.info("The response map contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The response map do not contain the key '" + key + "'");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}

		}

	}

	private static void assertingKeysInEachDataMapForGetKMLAPI(List<Map<String, Object>> responseDataListOfMaps) {

		List<String> expectedKeys = new ArrayList<>();
		expectedKeys.add(ZONE_GET_KML_DATA_ID_KEY);
		expectedKeys.add(ZONE_GET_KML_DATA_ZONE_ID_KEY);
		expectedKeys.add(ZONE_GET_KML_DATA_CUSTOMER_KEY);
		expectedKeys.add(ZONE_GET_KML_DATA_ZONE_NAME_KEY);
		expectedKeys.add(ZONE_GET_KML_DATA_FILE_NAME_KEY);
		expectedKeys.add(ZONE_GET_KML_DATA_GEO_JSON_KEY);

		int i = 0;
		for (Map<String, Object> dataMap : responseDataListOfMaps) {
			// Asserting each map is not empty
			assertFalse(dataMap.isEmpty());
			Log.info("data map at index " + i + " is not empty");
			for (String key : expectedKeys) {
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

	public static Response getTrendsAPIResponseForAZoneIdAndSensorTypeForDefaultDays(String zoneId, String sensorType,
			String days) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("dma");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/search/data";
		RestAssured.useRelaxedHTTPSValidation();

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement
				.setAction("Zone theme - Validate Trends Api for zoneId: " + zoneId + " and sensorType: " + sensorType);

		TestRailTestManagement.setCompleteTestComment("Validating Trends API for zoneId: " + zoneId + " and sensorType:"
				+ sensorType + System.lineSeparator() + " Updated by Automation Framework! ");

		long defaultDays = Long.parseLong(days);
		long endTime = System.currentTimeMillis();
		long startTime = endTime - (24 * 60 * 60 * 1000 * defaultDays);

		ConfigFileReader configFileReader = new ConfigFileReader();
		Header xServiceUrl = new Header("X-Service-Url", configFileReader.getAPIHeaderValues("url"));
		Header xServiceTicket = new Header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket"));

		Header xServiceUser = new Header("X-Service-User", configFileReader.getAPIHeaderValues("user"));

		Headers headers = new Headers(xServiceUrl, xServiceTicket, xServiceUser);

		String payload = "{\"request\":{\"st\":" + startTime + ",\"et\":" + endTime + ","
				+ "\"intervalmin\":60,\"include\":[\"" + zoneId + "\"],\"sensortype\":\"" + sensorType + "\","
				+ "\"timezone\":\"Europe/London\",\"format\":\"csv\",\"unit\":null}}";

		RequestSpecification httpRequest = RestAssured.given().headers(headers).contentType(ContentType.TEXT)
				.body(payload).request().log().all();

		Log.info("Created the Request for Trends  API for zoneId " + zoneId + " and sensorType " + sensorType);

		Response response = httpRequest.when().post();
		Log.info("Performing post request on the Trends API end point zoneId " + zoneId + " and sensorType "
				+ sensorType);
		Log.info("Response for Trends  API for zoneId " + zoneId + " and sensorType " + sensorType + "\n"
				+ response.asString());
		return response;
	}

	public static void verifyTrendsAPIResponseForAZoneIdAndSensorTypeForDefaultDays(String zoneId, String sensorType,
			String days) {

		Response response = getTrendsAPIResponseForAZoneIdAndSensorTypeForDefaultDays(zoneId, sensorType, days);
		TestRailTestManagement.setAction("Validating the status code to be " + STATUS_CODE_200);
		assertEquals(response.getStatusCode(), STATUS_CODE_200);

		Log.info("Asserted Status code to be " + response.getStatusCode());
		JsonPath jPath = response.jsonPath();
		Map<String, Object> objectMap = jPath.getMap("$");
		// Asserting object Map returned is not empty
		TestRailTestManagement.setAction("Validating the object map to be non empty");
		assertFalse(objectMap.isEmpty());
		Log.info("Asserted object Map is not empty ");

		TestRailTestManagement.setAction(
				"Validating keys within 'object' map for zoneId: " + zoneId + " and sensorType: " + sensorType);
		// Asserting keys in Response object map
		assertingKeysInObjectMapForTrendsAPI(objectMap);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) objectMap.get(ZONE_TRENDS_RESPONSE_DATA_KEY);

		TestRailTestManagement.setAction("Validating data list is not empty");
		assertFalse(dataList.isEmpty());
		Log.info("Asserted the data list is not empty");
		Log.info("The size of the data list =" + dataList.size());

		// Validating data size same as 'count' key value

		TestRailTestManagement.setAction("Validating data list size same as 'count' key value in object map");
		assertEquals(dataList.size(), objectMap.get(ZONE_TRENDS_RESPONSE_COUNT_KEY));

		Log.info("Asserted the data List size to be equal to 'count' key value= " + dataList.size());

		TestRailTestManagement.setAction("Validating keys within each data map in the List");
		assertingKeysInEachDataMapInTheListForTrendsAPI(dataList);

		List<Object> csvDataList = jPath.get("data[0].csvData");
		TestRailTestManagement.setAction("Validating csvData list is not empty inside data Map");
		assertFalse(csvDataList.isEmpty());
		Log.info("Asserted csvData list is not empty");
		Log.info("Size of csvData list =" + csvDataList.size());

		// Asserting each csvData element is a List of size 2

		TestRailTestManagement.setAction("Validating each csvData element is a List of size 2");
		assertingEachCSVDataElementIsAListOfSize2ForTrendsAPI(csvDataList);

		List<String> csvHeaderList = jPath.get("data[0].csvHeader");
		TestRailTestManagement.setAction("Validating csvHeader list is not empty inside data Map");
		assertFalse(csvHeaderList.isEmpty());
		Log.info("Asserted csvHeader list is not empty");
		Log.info("Size of csvHeader list =" + csvHeaderList.size());
		TestRailTestManagement
				.setAction("Validating csvHeader List size to be equal to " + ZONE_TRENDS_CSV_HEADER_SIZE);
		assertEquals(csvHeaderList.size(), ZONE_TRENDS_CSV_HEADER_SIZE);
		Log.info("Asserted the csvHeader list size to be equal to " + csvHeaderList.size());
		// Validating the values in csvHeader list

		TestRailTestManagement.setAction("Validating csvHeader list contains the zoneId " + zoneId);
		assertTrue(csvHeaderList.contains(zoneId));
		Log.info("Asserted the csvHeaderList contains the zoneId " + zoneId);

		TestRailTestManagement.setAction(
				"Validating the csvHeader list contains " + ZONE_TRENDS_CSV_HEADER_TIMESTAMP_VALUE + " as value");
		assertTrue(csvHeaderList.contains(ZONE_TRENDS_CSV_HEADER_TIMESTAMP_VALUE));
		Log.info("Asserted the csvHeaderList contains " + ZONE_TRENDS_CSV_HEADER_TIMESTAMP_VALUE);

		List<Map<String, String>> metaList = jPath.get("data[0].metaList");
		TestRailTestManagement.setAction("Validating metaList is not empty inside data Map");
		assertFalse(metaList.isEmpty());
		Log.info("Asserted meta list is not empty");
		Log.info("Size of meta list =" + metaList.size());

		TestRailTestManagement.setAction("Validating metaList  size to be equal to " + ZONE_TRENDS_METALIST_SIZE);
		assertEquals(metaList.size(), ZONE_TRENDS_METALIST_SIZE);
		Log.info("Asserted the csvHeader list size to be equal to " + metaList.size());

		// Validating keys in metaList Map

		TestRailTestManagement.setAction("Validating keys within each metaList map ");
		assertingKeysInEachMetaListMapForTrendsAPI(metaList);

		// Asserting the value of metaList sensorId key to be same as zoneId

		TestRailTestManagement.setAction("Validating the sensorId value in metaList same as zoneId");
		String sensorId = jPath.get("data[0].metaList[0].sensorId");
		assertEquals(sensorId, zoneId);
		Log.info("Asserted the sensorId key value inside metaList same as zoneId=" + sensorId);

		TestRailTestManagement.setAction("Validating the sensorType key value inside data Map");
		String sensorTypeValue = jPath.get("data[0].sensorType");

		assertEquals(sensorTypeValue, sensorType);
		Log.info("Asserted the sensorType key value ,same as expected sensorType= " + sensorTypeValue);

		// Asserting the value of status key to be 202

		TestRailTestManagement.setAction("Validating the status key value to be " + STATUS_CODE_202);
		assertEquals(jPath.getInt(ZONE_TRENDS_RESPONSE_STATUS_KEY), STATUS_CODE_202);

		Log.info("Asserted the 'status' key value to be as expected=" + jPath.getInt(ZONE_TRENDS_RESPONSE_STATUS_KEY));

		// Asserting the value of statusText key in response object map

		TestRailTestManagement.setAction("Validating the 'statusText' key value to be " + STATUS_TEXT_ACCEPTED);
		assertEquals(jPath.getString(ZONE_TRENDS_RESPONSE_STATUS_TEXT_KEY), STATUS_TEXT_ACCEPTED);

		Log.info("Asserted the 'statusText' key value to be as expected="
				+ jPath.getString(ZONE_TRENDS_RESPONSE_STATUS_TEXT_KEY));

	}

	private static void assertingKeysInObjectMapForTrendsAPI(Map<String, Object> objectMap) {
		List<String> expectedKeys = new ArrayList<>();

		expectedKeys.add(ZONE_TRENDS_RESPONSE_DATA_KEY);
		expectedKeys.add(ZONE_TRENDS_RESPONSE_REQUEST_ID_KEY);
		expectedKeys.add(ZONE_TRENDS_RESPONSE_REQUEST_TIME_KEY);
		expectedKeys.add(ZONE_TRENDS_RESPONSE_RESPONSE_TIME_KEY);
		expectedKeys.add(ZONE_TRENDS_RESPONSE_STATUS_KEY);
		expectedKeys.add(ZONE_TRENDS_RESPONSE_STATUS_TEXT_KEY);
		expectedKeys.add(ZONE_TRENDS_RESPONSE_TIME_KEY);
		expectedKeys.add(ZONE_TRENDS_RESPONSE_COUNT_KEY);

		for (String key : expectedKeys) {
			try {
				assertTrue(objectMap.containsKey(key));
				Log.info("The Response object map contains the key '" + key + "'");
			} catch (Throwable t) {
				Log.error("The Response object  map  do not contain the key '" + key + "' ");
				TestRailTestManagement.setAction("The Response object  map  do not contain the key '" + key + "' ");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}
		}

	}

	private static void assertingKeysInEachDataMapInTheListForTrendsAPI(List<Map<String, Object>> dataList) {
		List<String> expectedKeys = new ArrayList<>();
		expectedKeys.add(ZONE_TRENDS_DATA_CSV_DATA_KEY);
		expectedKeys.add(ZONE_TRENDS_DATA_CSV_HEADER_KEY);
		expectedKeys.add(ZONE_TRENDS_DATA_META_LIST_KEY);
		expectedKeys.add(ZONE_TRENDS_DATA_SENSOR_TYPE_KEY);

		int i = 0;
		for (Map<String, Object> dataMap : dataList) {
			for (String key : expectedKeys) {
				try {
					assertTrue(dataMap.containsKey(key));
					Log.info("The data map at index " + i + " contains the key '" + key + "'");
				} catch (Throwable t) {
					Log.error("The data  map at index " + i + " do not contain the key '" + key + "' ");
					TestRailTestManagement
							.setAction("The data map,at index " + i + " do not contain the key '" + key + "' ");
					Log.error(t.getMessage());
					fail(t.getMessage());
				}
			}
			i = i + 1;
		}

	}

	private static void assertingEachCSVDataElementIsAListOfSize2ForTrendsAPI(List<Object> csvDataList) {
		for (int i = 0; i < csvDataList.size(); i++) {

			try {
				assertTrue(csvDataList.get(i) instanceof List);
				Log.info("The csvData element at index " + i + " is an instance of List");
			} catch (Throwable t) {
				Log.error("The csvData element  at index " + i + " is not an instance of List");
				TestRailTestManagement.setAction("The csvData element  at index " + i + " is not an instance of List");
				Log.error(t.getMessage());
				fail(t.getMessage());
			}
			@SuppressWarnings("unchecked")
			List<Object> csvDataListElement = (List<Object>) csvDataList.get(i);
			try {

				assertEquals(csvDataListElement.size(), ZONE_TRENDS_CSV_DATA_ELEMENT_SIZE);
				Log.info("The csv Data element at index " + i + " size is equal to expected size ="
						+ csvDataListElement.size());
			} catch (Throwable t) {
				Log.error("The csv Data element at index " + i + " size is not as expected ="
						+ csvDataListElement.size());
				TestRailTestManagement.setAction("The csv Data element at index " + i + " size is not as expected ="
						+ csvDataListElement.size());
				Log.error(t.getMessage());
				fail(t.getMessage());
			}

		}
	}

	private static void assertingKeysInEachMetaListMapForTrendsAPI(List<Map<String, String>> metaList) {
		List<String> expectedKeys = new ArrayList<>();
		expectedKeys.add(ZONE_TRENDS_METALIST_SENSOR_ID_KEY);
		expectedKeys.add(ZONE_TRENDS_METALIST_SENSOR_UNIT_KEY);

		int i = 0;
		for (Map<String, String> metaMap : metaList) {
			for (String key : expectedKeys) {
				try {
					assertTrue(metaMap.containsKey(key));
					Log.info("The metaList map at index " + i + " contains the key '" + key + "'");
				} catch (Throwable t) {
					Log.error("The metaList  map at index " + i + " do not contain the key '" + key + "' ");
					TestRailTestManagement
							.setAction("The metaList map,at index " + i + " do not contain the key '" + key + "' ");
					Log.error(t.getMessage());
					fail(t.getMessage());
				}
			}
			i = i + 1;
		}

	}

}