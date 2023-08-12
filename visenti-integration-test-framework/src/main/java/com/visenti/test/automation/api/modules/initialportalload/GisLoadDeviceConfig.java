package com.visenti.test.automation.api.modules.initialportalload;


import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.RestAssuredHelper;

import cucumber.runtime.io.Helpers;

public class GisLoadDeviceConfig extends RestBaseModule {

	public GisLoadDeviceConfig(RestAssuredHelper helper) {
		super(helper);

	}

	public void setUpRequestForGisConfig(String api) {
		super.setUpRequest(api);
	}

	@SuppressWarnings("unchecked")
	public void verifyCommonResponseForDeviceConfigApi() throws Exception {
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
		Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<String, String>();

		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_COUNT_KEY, "Int");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "Int");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY, "String");

		/*
		 * This method verifies that the Response Object($) map contains the expected
		 * keys. The data Type of the value of each key is as expected ,Value of key not
		 * null and Value of Key is not empty for the type 'List','String' or 'Map'
		 */
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
}
	
		@SuppressWarnings("unchecked")
		public void verifySpecificResponseForDeviceConfigApi() throws Exception {
		// Assert data list ($.data)contains the expected Keys and verify Data-Types of
		// value of each Key

		// Creating a Map containing the expected keys in data list and their
		// data types

		Map<String, String> expectedKeysDataTypesDataMap = new HashMap<String, String>();

		expectedKeysDataTypesDataMap.put(GIS_CONFIG_DATA_ACL_KEY, "Map");
		expectedKeysDataTypesDataMap.put(GIS_CONFIG_DATA_DEVICE_ID_KEY, "String");
		expectedKeysDataTypesDataMap.put(GIS_CONFIG_DATA_ENABLED_KEY, "Boolean");
		expectedKeysDataTypesDataMap.put(GIS_CONFIG_DATA_FILTERS_KEY, "Map");
		expectedKeysDataTypesDataMap.put(GIS_CONFIG_DATA_ICON_KEY, "String");
		expectedKeysDataTypesDataMap.put(GIS_CONFIG_DATA_KEY_KEY, "String");
		expectedKeysDataTypesDataMap.put(GIS_CONFIG_DATA_NAME_KEY, "String");
		expectedKeysDataTypesDataMap.put(GIS_CONFIG_DATA_TYPE_KEY, "String");

		// This method verifies that the data ($.data) contains the expected
		// keys
		// the data Type of the value of each key is as expected ,Value of key not null
		// and
		// Value of Key is not empty for the type 'List','String' or 'Map'

		helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY,
				expectedKeysDataTypesDataMap);

		// Verify the keys and their value data types in each map of data.ACL

		Map<String, String> expectedKeysDataTypesACLMap = new HashMap<String, String>();
		expectedKeysDataTypesACLMap.put(GIS_CONFIG_ACL_HEATMAP_KEY, "Map");
		expectedKeysDataTypesACLMap.put(GIS_CONFIG_ACL_MARKER_KEY, "Map");

		// This method verifies each Map in the List is not empty,the presence of keys
		// in each Map in a List at the JsonPath ,
		// the dataTypes of value of keys as expected ,Value of key not null and Value
		// of Key is not empty for the type 'List','String' or 'Map'

		helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_CONFIG_DATA_ACL_KEY, expectedKeysDataTypesACLMap);

		// Verify the keys and their value data types in each map of data.ACL.Heatmap

		Map<String, String> expectedKeysDataTypesACLHeatMap = new HashMap<String, String>();
		expectedKeysDataTypesACLMap.put(GIS_CONFIG_ACL_HEATMAP_OPERATION_KEY, "String");
		expectedKeysDataTypesACLMap.put(GIS_CONFIG_ACL_HEATMAP_TYPE_KEY, "String");

		// This method verifies each Map in the List is not empty,the presence of keys
		// in each Map in a List at the JsonPath ,
		// the dataTypes of value of keys as expected ,Value of key not null and Value
		// of Key is not empty for the type 'List','String' or 'Map'

		helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_CONFIG_DATA_ACL_KEY + "." + GIS_CONFIG_ACL_HEATMAP_KEY,
				expectedKeysDataTypesACLHeatMap);

		// Verify the keys and their value data types in each map of data.ACL.Marker

		Map<String, String> expectedKeysDataTypesACLMarkerMap = new HashMap<String, String>();
		expectedKeysDataTypesACLMap.put(GIS_CONFIG_ACL_MARKER_OPERATION_KEY, "String");
		expectedKeysDataTypesACLMap.put(GIS_CONFIG_ACL_MARKER_TYPE_KEY, "String");

		// This method verifies each Map in the List is not empty,the presence of keys
		// in each Map in a List at the JsonPath ,
		// the dataTypes of value of keys as expected ,Value of key not null and Value
		// of Key is not empty for the type 'List','String' or 'Map'

		helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_CONFIG_DATA_ACL_KEY + "." + GIS_CONFIG_ACL_MARKER_KEY,
				expectedKeysDataTypesACLMarkerMap);
       }
}
