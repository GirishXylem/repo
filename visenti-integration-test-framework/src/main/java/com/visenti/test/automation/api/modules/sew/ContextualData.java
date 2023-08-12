package com.visenti.test.automation.api.modules.sew;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.testng.Assert;

import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ContextualData {

	private static final String QUERYPARAM_TYPE_VALUE_CUSTOMERCALLS = "customer_complaints";
	private static final String QUERYPARAMTYPE_VALUE_SCHEDULEDJOBS = "scheduled_jobs";
	private static final String CUSTOMERCALLS_COLUMN_KEY_CLOSED_DATE = "closed_date";
	private static final String SCHEDULEDJOBS_COLUMN_KEY_SCHEDULED_START = "scheduled_start";

	private static Response getContextualDataResponseForAGivenType(String type, String days) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		// PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("dma");
		RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api
				+ PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/getReferenceData";

		RestAssured.useRelaxedHTTPSValidation();

		long defaultDays = Long.parseLong(days);
		long endTime = System.currentTimeMillis();
		long startTime = endTime - (24 * 60 * 60 * 1000 * defaultDays);

		TestRailTestManagement.setTestComment(" Updated by Automation Framework! ");

		TestRailTestManagement.setAPI(RestAssured.baseURI + RestAssured.basePath);
		TestRailTestManagement.setAction("Loading contextual data for the type " + type + " for " + days + " days");

		ConfigFileReader configFileReader = new ConfigFileReader();
		Header xServiceCustomer = new Header("X-Service-Customer", configFileReader.getAPIHeaderValues("customer"));
		Header xServiceUser = new Header("X-Service-User", configFileReader.getAPIHeaderValues("user"));
		Header xServiceUrl = new Header("X-Service-Url", configFileReader.getAPIHeaderValues("url"));
		Header xServiceTicket = new Header("X-Service-Ticket", configFileReader.getAPIHeaderValues("ticket"));

		Headers headers = new Headers(xServiceCustomer, xServiceUser, xServiceUrl, xServiceTicket);
		Map<String, Object> queryParamsMap = new HashMap<String, Object>();
		queryParamsMap.put("st", startTime);
		queryParamsMap.put("et", endTime);

		if (type.equalsIgnoreCase("customercalls")) {
			queryParamsMap.put("type", QUERYPARAM_TYPE_VALUE_CUSTOMERCALLS);
		} else if (type.equalsIgnoreCase("scheduledjobs")) {
			queryParamsMap.put("type", QUERYPARAMTYPE_VALUE_SCHEDULEDJOBS);
		} else {
			throw new RuntimeException("Wrong Contextual Data type provided");
		}

		RequestSpecification httpRequest = RestAssured.given().headers(headers).queryParams(queryParamsMap).request()
				.log().all();
		Log.info("Created the Request for Contextual data API for " + type);
		Response response = httpRequest.when().get();
		Log.info("Performing GET request on the Contextual data API for " + type);
		Log.info("Response for Contextual data API for " + type + "\n " + response.asString());
		return response;
	}

	public static void verifyContextualDataResponseForAGivenType(String type, String days) {

		Response response = getContextualDataResponseForAGivenType(type, days);

		assertEquals(response.getStatusCode(), 200);
		Log.info("Asserted Status code to be " + response.getStatusCode());

		// Asserting jsonResponseData to be non empty
		List<Object> jsonResponseData = response.jsonPath().get("data");
		assertTrue(!jsonResponseData.isEmpty());
		Log.info("Asserted the Response Data List is not empty");

		// TODO : Need to check response.jsonPath().get("data[0]") size should not be 0.
		// Otherwise fail test case. Following validation only give Java Null value
		// exception.
		// Need to make a test failure.

		// Asserting jsonResponse columns is not empty
		List<Map<String, String>> jsonResponseColumnsList = response.jsonPath().get("data[0].columns");
		assertNotNull(jsonResponseColumnsList);
		assertTrue(!jsonResponseColumnsList.isEmpty());
		Log.info("Asserted the Response Columns List is not empty");
		// verifying each Map in the List is not empty
		for (Map<String, String> map : jsonResponseColumnsList) {
			assertTrue(!map.isEmpty());
		}
		Log.info("Asserted each map inside the Columns List is not empty");
		// Asserting jsonResponse records is not empty
		List<Map<String, Object>> jsonResponseRecordsList = response.jsonPath().get("data[0].records");
		assertTrue(!jsonResponseRecordsList.isEmpty());
		Log.info("Asserted the Response Records List is not empty");
		// verifying each Map in the List is not empty
		for (Map<String, Object> map : jsonResponseRecordsList) {
			assertTrue(!map.isEmpty());
		}
		Log.info("Asserted each map inside Records List is not empty");

		Log.info("Asserted Successfully the Response for Contextual Data API for " + type);

		assertingColumnKeysInRecords(type, response);

	}

	/**
	 * @param type
	 * @param response
	 * 
	 *                 This method verifies each Record contains the column
	 *                 keys(data[0].columns.key)
	 */
	private static void assertingColumnKeysInRecords(String type, Response response) {
		// Fetching value of the Key 'key' from each Map of columns List
		List<String> values = response.jsonPath().get("data[0].columns.key");

		// Removing the column key 'closed_date'from the values List for verification
		// since few records for Customer calls do not contain the key 'closed_date'
		if (type.equalsIgnoreCase("customercalls")) {

			values.remove(CUSTOMERCALLS_COLUMN_KEY_CLOSED_DATE);
			Log.info("Removed the Column Key " + CUSTOMERCALLS_COLUMN_KEY_CLOSED_DATE
					+ " from the List for verification");
		}
		// Removing the column key 'scheduled_start'from the values List for
		// verification
		// since all records for Scheduled Jobs do not contain the key 'closed_date'
		else if (type.equalsIgnoreCase("scheduledjobs")) {
			values.remove(SCHEDULEDJOBS_COLUMN_KEY_SCHEDULED_START);
			Log.info("Removed the Column Key " + SCHEDULEDJOBS_COLUMN_KEY_SCHEDULED_START
					+ " from the List for verification");
		}
		// Getting the List of Record maps
		List<Map<String, Object>> jsonResponseRecordsList = response.jsonPath().get("data[0].records");

		int i = 0;
		for (Map<String, Object> recordMap : jsonResponseRecordsList) {
			for (int j = 0; j < values.size(); j++) {
				try {
					Assert.assertTrue(recordMap.containsKey(values.get(j)));
					Log.info("The Record at Index " + i + " contains the key " + values.get(j));
				} catch (Throwable t) {

					Log.error("The Record at Index " + i + " do not contain the key " + values.get(j));
					Log.error(t.getMessage());
					Assert.fail(t.getMessage());

				}

			}
			i = i + 1;

		}
		Log.info("Asserted successfully Each Record contains the Column keys");

	}

}
