package com.visenti.test.automation.api.modules.initialportalload;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.*;

public class DataLoadLiveStatus extends RestBaseModule {

	public DataLoadLiveStatus(RestAssuredHelper helper) {
		super(helper);

	}

	public void setUpRequestWithQueryParam() {
		super.setUpRequest("data");
		// Encoding the QueryParam value using the URLEncoder class
		// String gets converted to the application/x-www-form-urlencoded MIMEformat

		// Encoded value would be :%22station%22
		String queryValueEncoded = URLEncoder.encode(DATA_LIVE_STATUS_QUERY_PARAM_VALUE_STATION, StandardCharsets.UTF_8);

		// The value for the Query name 'type' in the url is passed as a List,hence
		// adding the Encoded
		// query value to a List
		List<String> typeList = new ArrayList<>();
		typeList.add(queryValueEncoded);

		// typeList.toString() = [%22station%22] -expected value for queryParam 'type'
		// in the Url
		helper.addQueryParamsToTheRequest(DATA_LIVE_STATUS_QUERY_PARAM_NAME, typeList.toString());
	}

	public void verifyFullResponseForLiveStatusApi() throws Exception {
		// Assert the Content-Type header in Response is text/html
		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_TEXT_HTML);
		// Assert Response is an instance of Map
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");
		// Assert Response object is not empty
		helper.verifyValueNotEmptyAtAJsonPathExpression("$");
		// Assert Response Object contains the expected Keys and verify Data-Types of
		// value of each Key
		// Creating a Map containing the expected keys in Response Object Map and their
		// data types
		Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();

		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_COUNT_KEY, "int");
		//expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_COUNT_KEY, "String");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "Int");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY, "String");
		//expectedKeysDataTypesResponseMap.put("test1", "String");

		// This method verifies that the Response Object($) map contains the expected
		// keys
		// the data Type of the value of each key is as expected ,Value of key not null
		// and
		// Value of Key is not empty for the type 'List','String' or 'Map'
		helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath("$", expectedKeysDataTypesResponseMap);
		// Assert the status key value to be 202
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY, STATUS_CODE_202,
				"int");
		// Assert the statusText key value to be 'Accepted'
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY,
				STATUS_TEXT_ACCEPTED, "String");
	     // Asserting the data List expected Size should be equal to the 'count' key
		// value
		int count = (int) helper.getValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_COUNT_KEY);
		@SuppressWarnings("unchecked")
		int dataListSize = ((List<Object>) helper.getValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY))
				.size();

		assertEquals(count, dataListSize,
				"The count key value' " + count + "' is not equal to the dataList size '" + dataListSize);
		Log.info("Asserted the '" + COMMON_RESPONSE_OBJECT_COUNT_KEY + "' value = " + count + " to be equal to the '"
				+ COMMON_RESPONSE_OBJECT_DATA_KEY + "' List size");
		Reporter.addStepLog("Asserted the '" + COMMON_RESPONSE_OBJECT_COUNT_KEY + "' value= " + count
				+ " to be equal to the '" + COMMON_RESPONSE_OBJECT_DATA_KEY + "' List size");

		// Assert each data Map in the List($.data)contains the expected Keys and verify
		// Data-Types of
		// value of each Key

		// Creating a Map containing the expected keys in each Map and their
		// data types

		Map<String, String> expectedKeysDataTypesDataMap = new HashMap<>();

		expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_CUSTOMER_ID_KEY, "String");
		expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_INACTIVE_SENSOR_KEY, "String");
		expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_LATITUDE_KEY, "Float");
		expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_LONGITUDE_KEY, "Float");
		expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_MAXIMUM_LATENCY_KEY, "Integer");
		expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_NAME_KEY, "String");
		expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_SOURCE_ID_KEY, "String");
		expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_STATION_ID_KEY, "String");
		expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_STATUS_KEY, "String");
		expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_TYPE_KEY, "String");
		expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_ZONE_KEY, "String");

		// This method verifies each Map in the List is not empty,the presence of keys
		// in each Map in a List at the JsonPath ,
		// the dataTypes of value of keys as expected ,Value of key not null and Value
		// of Key is not empty for the type 'List','String' or 'Map'
		helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY,
				expectedKeysDataTypesDataMap);
		String xServiceCustomerHeaderValue = FileReaderManager.getInstance().getConfigReader()
				.getAPIHeaderValues("customer");
		
		//Getting the QueryParam value for type and removing the double quotes
		String queryValueType=DATA_LIVE_STATUS_QUERY_PARAM_VALUE_STATION;
		
		queryValueType=queryValueType.replace("\"", "");
			
		Map<String,Object>expectedKeyValuesInEachDataMap=new HashMap<>();
		expectedKeyValuesInEachDataMap.put(DATA_LIVE_STATUS_CUSTOMER_ID_KEY, xServiceCustomerHeaderValue);
		expectedKeyValuesInEachDataMap.put(DATA_LIVE_STATUS_TYPE_KEY, queryValueType.toUpperCase());
		
		helper.verifyValueOfKeysInEachMapOfAListAsExpected(COMMON_RESPONSE_OBJECT_DATA_KEY, expectedKeyValuesInEachDataMap);
		
		}

}
