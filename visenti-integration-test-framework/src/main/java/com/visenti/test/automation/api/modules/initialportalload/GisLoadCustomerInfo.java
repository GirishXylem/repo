package com.visenti.test.automation.api.modules.initialportalload;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import io.restassured.path.json.JsonPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.visenti.test.automation.constants.APIConstants.*;

public class GisLoadCustomerInfo extends RestBaseModule {

	public GisLoadCustomerInfo(RestAssuredHelper helper) {
		super(helper);

	}

	public void setUpRequestWithQueryParam(String api) {
		super.setUpRequest(api);
		// Adding QueryParams to the Request
		String customerName = PortalConfigManagement.getPortal();
		helper.addQueryParamsToTheRequest(GIS_CUSTOMER_INFO_QUERY_PARAM_NAME, customerName);
	}

	@SuppressWarnings("unchecked")
	public void verifyFullResponseForGisCustomerInfoApi() throws Exception {
		// Validate the Content-Type header is text/html

		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_JSON);

		// Assert Response is an instance of Map

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");

		// Assert Response object is not empty

		helper.verifyValueNotEmptyAtAJsonPathExpression("$");

		// Assert Response Object contains the expected Keys and verify Data-Types of
		// value of each Key

		// Creating a Map containing the expected keys in Response Object Map and their
		// data types
		Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<String, String>();

		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY, "int");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "map");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_META_KEY, "map");
		// expectedKeysDataTypesResponseMap.put(GIS_CUSTOMER_INFO_RESPONSE_META_KEY,
		// "List");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "String");
		// expectedKeysDataTypesResponseMap.put(GIS_CUSTOMER_INFO_RESPONSE_STATUS_KEY,
		// "List");
		// expectedKeysDataTypesResponseMap.put("Test", "String");

		// This method verifies that the Response Object($) map contains the expected
		// keys
		// the data Type of the value of each key is as expected ,Value of key not null
		// and
		// Value of Key is not empty for the type 'List','String' or 'Map'
		helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath("$", expectedKeysDataTypesResponseMap);

		// Verify status_code key value to be equal to 200

		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY,
				STATUS_CODE_200, "Int");

		// Verify the value of 'status' key equal to 'success'
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY, STATUS_SUCCESS,
				"String");

		// Assert data Map ($.data)contains the expected Keys and verify Data-Types of
		// value of each Key

		// Creating a Map containing the expected keys in data Map and their
		// data types

		Map<String, String> expectedKeysDataTypesDataMap = new HashMap<String, String>();

		expectedKeysDataTypesDataMap.put(GIS_CUSTOMER_INFO_DATA_ID_KEY, "map");
		expectedKeysDataTypesDataMap.put(GIS_CUSTOMER_INFO_DATA_NAME_KEY, "String");
		expectedKeysDataTypesDataMap.put(GIS_CUSTOMER_INFO_DATA_NETWORK_KEY, "List");
		expectedKeysDataTypesDataMap.put(GIS_CUSTOMER_INFO_DATA_ZONES_KEY, "list");

		// This method verifies that the data map($.data) contains the expected
		// keys
		// the data Type of the value of each key is as expected ,Value of key not null
		// and
		// Value of Key is not empty for the type 'List','String' or 'Map'

		helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY,
				expectedKeysDataTypesDataMap);

		// Asserting the value of the data.name key is same as customerName in
		// QueryParam in UpperCase

		filterableRequestSpecification = helper.getFilterableRequestSpecification();

		String customerName = filterableRequestSpecification.getQueryParams().get(GIS_CUSTOMER_INFO_QUERY_PARAM_NAME);

		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_CUSTOMER_INFO_DATA_NAME_KEY, customerName.toUpperCase(),
				"string");

		// Verify the keys and their value data types in each Map of data.network List

		Map<String, String> expectedKeysDataTypesNetworkMap = new HashMap<String, String>();
		expectedKeysDataTypesNetworkMap.put(GIS_CUSTOMER_INFO_NETWORK_ID_KEY, "String");
		expectedKeysDataTypesNetworkMap.put(GIS_CUSTOMER_INFO_NETWORK_NETWORK_NAME_KEY, "String");
		expectedKeysDataTypesNetworkMap.put(GIS_CUSTOMER_INFO_NETWORK_CUSTOMER_ID_KEY, "String");
		expectedKeysDataTypesNetworkMap.put(GIS_CUSTOMER_INFO_NETWORK_LABEL_KEY, "String");
		// expectedKeysDataTypesNetworkMap.put(GIS_CUSTOMER_INFO_NETWORK_LABEL_KEY,
		// "List");

		// This method verifies each Map in the List is not empty,the presence of keys
		// in each Map in a List at the JsonPath ,
		// the dataTypes of value of keys as expected ,Value of key not null and Value
		// of Key is not empty for the type 'List','String' or 'Map'

		helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_CUSTOMER_INFO_DATA_NETWORK_KEY,
				expectedKeysDataTypesNetworkMap);

		// PUB Specific Validations :

		if (PortalConfigManagement.getPortal().equalsIgnoreCase("pub")) {
			// The jsonPath data.network.networkName will return a List of Strings
			// containing all the networkNames
			// Verify the data.network.networkName List contains 'potable_water' and
			// 'newater' for PUB

			List<Object> expectedValuesNetworkNameList = new ArrayList<>();
			expectedValuesNetworkNameList.add(NETWORK_NAME_POTABLE_WATER);
			expectedValuesNetworkNameList.add(NETWORK_NAME_NEWATER);
			/*
			 * expectedValuesNetworkNameList.add("Test1");
			 * expectedValuesNetworkNameList.add("Test2");
			 */

			helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "."
					+ GIS_CUSTOMER_INFO_DATA_NETWORK_KEY + "." + GIS_CUSTOMER_INFO_NETWORK_NETWORK_NAME_KEY,
					expectedValuesNetworkNameList);

			List<Map<String, Object>> networkListOfMaps = (List<Map<String, Object>>) helper
					.getValueAtAGivenJsonPathExpression(
							COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_CUSTOMER_INFO_DATA_NETWORK_KEY);
			int i = 0;
			for (Map<String, Object> networkMap : networkListOfMaps) {
				String networkName = (String) networkMap.get(GIS_CUSTOMER_INFO_NETWORK_NETWORK_NAME_KEY);

				// If the networkName key ,value ='potable_water' then verify data.network Map
				// contains dma key
				// Also verifying the dma key ,value dataType is a List ,and the value is not
				// null and not empty
				if (networkName.equals(NETWORK_NAME_POTABLE_WATER)) {

					helper.verifyKeyAndItsValueDataTypeInAMapAtJsonPath(
							COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_CUSTOMER_INFO_DATA_NETWORK_KEY + "[" + i + "]",
							GIS_CUSTOMER_INFO_NETWORK_DMA_KEY, "List");
				}

				else if (networkName.equals(NETWORK_NAME_NEWATER)) {
					helper.verifyKeyAndItsValueDataTypeInAMapAtJsonPath(
							COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_CUSTOMER_INFO_DATA_NETWORK_KEY + "[" + i + "]",
							GIS_CUSTOMER_INFO_NETWORK_DMZ_KEY, "List");
				}
				i++;
			}

		}
		// Verify the keys and their value data types in each Map of data.zones List

		Map<String, String> expectedKeysDataTypesZonesMap = new HashMap<String, String>();
		expectedKeysDataTypesZonesMap.put(GIS_CUSTOMER_INFO_ZONES_ID_KEY, "String");
		expectedKeysDataTypesZonesMap.put(GIS_CUSTOMER_INFO_ZONES_NETWORK_ID_KEY, "String");
		// expectedKeysDataTypesZonesMap.put(GIS_CUSTOMER_INFO_ZONES_NETWORK_ID_KEY,
		// "List");
		expectedKeysDataTypesZonesMap.put(GIS_CUSTOMER_INFO_ZONES_ZONE_TYPE_KEY, "String");
		expectedKeysDataTypesZonesMap.put(GIS_CUSTOMER_INFO_ZONES_ZONE_NAME_KEY, "String");
		expectedKeysDataTypesZonesMap.put(GIS_CUSTOMER_INFO_ZONES_HAS_PARENT_KEY, "Boolean");
		// expectedKeysDataTypesZonesMap.put(GIS_CUSTOMER_INFO_ZONES_HAS_PARENT_KEY,
		// "Double");
		expectedKeysDataTypesZonesMap.put(GIS_CUSTOMER_INFO_ZONES_PARENT_ZONE_KEY, "String");
		// expectedKeysDataTypesZonesMap.put("Test", "Float");

		// This method verifies each Map in the data.zones List is not empty,the
		// presence of keys
		// in each Map in a List at the JsonPath ,
		// the dataTypes of value of keys as expected ,Value of key not null and Value
		// of Key is not empty for the type 'List','String' or 'Map'

		helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_CUSTOMER_INFO_DATA_ZONES_KEY,
				expectedKeysDataTypesZonesMap);

		// Verify the keys and their value dataTypes in meta Map

		Map<String, String> expectedKeysDataTypeMetaMap = new HashMap<String, String>();

		expectedKeysDataTypeMetaMap.put(COMMON_META_ERROR_MESSAGE_KEY, "String");
		expectedKeysDataTypeMetaMap.put(COMMON_META_ERROR_TYPE_KEY, "String");

		// This method verifies that the meta map($.meta) contains the expected
		// keys
		// the data Type of the value of each key is as expected ,Value of key not null
		// and
		// Value of Key is not empty for the type 'String'

		helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath(COMMON_RESPONSE_OBJECT_META_KEY,
				expectedKeysDataTypeMetaMap);

		// Verify meta.error_message key value to be equal to 'none'

		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_META_KEY + "." + COMMON_META_ERROR_MESSAGE_KEY,
				RESPONSE_META_ERROR_MESSAGE_NONE, "String");

		// Verify meta.error_type key value to be equal to 'none'

		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_META_KEY + "." + COMMON_META_ERROR_TYPE_KEY,
				RESPONSE_META_ERROR_TYPE_NONE, "String");

	}

	public Map<String, Object> getDmaIDAndName(){
		JsonPath jPath = helper.getJsonPath();
		Map<String, Object> dmaMap = new HashMap<>();
		List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_CUSTOMER_INFO_DATA_ZONES_KEY);
		for (Map<String, Object> actualMap : actualList) {
			dmaMap.put(actualMap.get("zoneName").toString(),actualMap.get("_id"));
		}

		return dmaMap;
	}
}
