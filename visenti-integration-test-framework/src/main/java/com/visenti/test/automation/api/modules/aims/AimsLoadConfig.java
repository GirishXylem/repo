package com.visenti.test.automation.api.modules.aims;

import static com.visenti.test.automation.constants.APIConstants.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.*;
import com.visenti.test.automation.utils.CommonUtils;

public class AimsLoadConfig extends RestBaseModule {

	public AimsLoadConfig(RestAssuredHelper helper) {
		super(helper);

	}

	public void setUpRequestWithQueryParam(String api) {
		String aimsApiUrlSubString = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs(api);
		String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
		String environment = RuntimeConfigSingleton.getInstance().getExecutionEnvironment();
		String baseUri,customerId = "";
		if(customerName.equalsIgnoreCase("cloud") ||	customerName.equalsIgnoreCase("pub")){
			if(!environment.equalsIgnoreCase("prod")){
				baseUri = "https://" + aimsApiUrlSubString +"-" +PortalConfigManagement.getPortalPrefix() + "-view"
						+ PortalConfigManagement.getPortalDomain();
			} else {
				baseUri = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + aimsApiUrlSubString
						+ PortalConfigManagement.getPortalDomain();
			}
		} else  {
			if(environment.equalsIgnoreCase("prod")){
				baseUri = "https://" + aimsApiUrlSubString + "-" + PortalConfigManagement.getPortalDomain();
			} else {
				baseUri = "https://" + aimsApiUrlSubString + "-" + PortalConfigManagement.getPortalPrefix() + "-"
						+ PortalConfigManagement.getPortalDomain();
			}
		}
		// No base path hence passing empty String
		helper.addBaseURIAndBasePathToTheRequest(baseUri, "");

		// Adding Common headers to the RequestSpecification object
		super.addCommonHeadersToTheRequest();

		// Adding QueryParams to the Request
		// customerId is the QueryParam Value for the QueryParam Nam 'customerId'
		if(customerName.equalsIgnoreCase("pub")||customerName.equalsIgnoreCase("cloud")){
			customerId = "pub";
		} else {
			customerId = PortalConfigManagement.getPortal();
		}

		helper.addQueryParamsToTheRequest(AIMS_CONFIG_QUERY_PARAM_NAME, customerId);
	}

	public void verifyFullResponseForAimsGetConfigApi() throws Exception {
		// Validate the Content-Type header is application/json
		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_JSON);

		// Assert Response is an instance of Map
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");

		// Assert Response object is not empty
		helper.verifyValueNotEmptyAtAJsonPathExpression("$");

		// Assert Response Object contains the expected Keys and verify Data-Types of
		// value of each Key

		// Creating a Map containing the expected keys in Response Object Map and their
		// data types
		Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY, "int");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_META_KEY, "map");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "String");

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

		// Assert each data Map in the List($.data)contains the expected Keys and verify
		// Data-Types of
		// value of each Key

		// Creating a Map containing the expected keys in each Map and their
		// data types

		Map<String, String> expectedKeysDataTypesDataMap = new HashMap<>();

		// expectedKeysDataTypesDataMap.put("Test", "Float");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_ALERT_TYPES_LIST_KEY, "List");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_ATTEND_KEY, "List");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_BEEP_SOUND_KEY, "List");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_CATEGORY_KEY, "List");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_GROUP_BY_KEY, "List");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_INCIDENT_TYPE_KEY, "list");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_ONGOING_KEY, "List");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_RANK_KEY, "List");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_RECOMMENDATION_KEY, "List");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_SENSOR_KEY, "List");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_SENSOR_TYPES_LIST_KEY, "List");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_STATUS_KEY, "List");
		expectedKeysDataTypesDataMap.put(AIMS_CONFIG_DATA_TYPE_KEY, "List");

		// This method verifies each Map in the List is not empty,the presence of keys
		// in each Map in a List at the JsonPath ,
		// the dataTypes of value of keys as expected ,Value of key not null and Value
		// of Key is not empty for the type 'List','String' or 'Map'
		helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY,
				expectedKeysDataTypesDataMap);

		Set<String> keysDataMap = expectedKeysDataTypesDataMap.keySet();

		// Common keys in each Map of all the List under Data Map
		// ('title' and 'key')

		// These two keys('title' and 'key') are present in each Map of 'recommendation'
		// List ,'status'
		// List , 'sensor' List ,'type' List' ,'groupBy' List etc

		Map<String, String> commonKeysValueDataTypeInEachMap = new HashMap<>();
		commonKeysValueDataTypeInEachMap.put(AIMS_CONFIG_TITLE_KEY, "String");
		commonKeysValueDataTypeInEachMap.put(AIMS_CONFIG_KEY_KEY, "String");

		// Here we are verifying for each Map in all the List under Data Map
		// (recommendation,status List etc)
		// Each Map in recommendation,status List etc
		// is not empty ,contains the common expected Keys ('title' and 'key') ,value of keys
		// is not null ,the datatype of the value of the keys is as expected(String) and value
		// is not empty for type List ,String or Map

		// Iterating over all the keys in Data Map and verifying each Map in all the
		// List (value of Key in Data Map is a List
		// of Maps ,like recommendation List ,status List etc)
		// contains the common keys 'title' and 'key'
		for (String key : keysDataMap) {
			helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(
					COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + key, commonKeysValueDataTypeInEachMap);
		}

		// Here we are getting the Set of expected Values for 'title' key in all the
		// Maps
		// in 'data[0].recommendation' List
		Set<Object> expectedValuesTitleKeyRecommendationMaps = gettingExpectedTitleValuesForDifferentKeysInDataMap(
				AIMS_CONFIG_DATA_RECOMMENDATION_KEY);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_RECOMMENDATION_KEY+"."+AIMS_CONFIG_TITLE_KEY, expectedValuesTitleKeyRecommendationMaps);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_RECOMMENDATION_KEY+"."+AIMS_CONFIG_KEY_KEY, expectedValuesTitleKeyRecommendationMaps);

		Set<Object> expectedValuesTitleKeyStatusMaps = gettingExpectedTitleValuesForDifferentKeysInDataMap(
				AIMS_CONFIG_DATA_STATUS_KEY);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_STATUS_KEY+"."+AIMS_CONFIG_TITLE_KEY, expectedValuesTitleKeyStatusMaps);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_STATUS_KEY+"."+AIMS_CONFIG_KEY_KEY, expectedValuesTitleKeyStatusMaps);

		Set<Object> expectedValuesTitleKeyAttendMaps = gettingExpectedTitleValuesForDifferentKeysInDataMap(
				AIMS_CONFIG_DATA_ATTEND_KEY);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_ATTEND_KEY+"."+AIMS_CONFIG_TITLE_KEY, expectedValuesTitleKeyAttendMaps);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_ATTEND_KEY+"."+AIMS_CONFIG_KEY_KEY, expectedValuesTitleKeyAttendMaps);

		Set<Object> expectedValuesTitleKeyCategoryMaps = gettingExpectedTitleValuesForDifferentKeysInDataMap(
				AIMS_CONFIG_DATA_CATEGORY_KEY);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_CATEGORY_KEY+"."+AIMS_CONFIG_TITLE_KEY, expectedValuesTitleKeyCategoryMaps);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_CATEGORY_KEY+"."+AIMS_CONFIG_KEY_KEY, expectedValuesTitleKeyCategoryMaps);

		Set<Object> expectedValuesTitleKeyBeepSoundMaps = gettingExpectedTitleValuesForDifferentKeysInDataMap(
				AIMS_CONFIG_DATA_BEEP_SOUND_KEY);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_BEEP_SOUND_KEY+"."+AIMS_CONFIG_TITLE_KEY, expectedValuesTitleKeyBeepSoundMaps);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_BEEP_SOUND_KEY+"."+AIMS_CONFIG_KEY_KEY, expectedValuesTitleKeyBeepSoundMaps);

		Set<Object> expectedValuesTitleKeyOngoingMaps = gettingExpectedTitleValuesForDifferentKeysInDataMap(
				AIMS_CONFIG_DATA_ONGOING_KEY);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_ONGOING_KEY+"."+AIMS_CONFIG_TITLE_KEY, expectedValuesTitleKeyOngoingMaps);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_ONGOING_KEY+"."+AIMS_CONFIG_KEY_KEY, expectedValuesTitleKeyOngoingMaps);

		Set<Object> expectedValuesTitleKeyRankMaps = gettingExpectedTitleValuesForDifferentKeysInDataMap(
				AIMS_CONFIG_DATA_RANK_KEY);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_RANK_KEY+"."+AIMS_CONFIG_TITLE_KEY, expectedValuesTitleKeyRankMaps);
		helper.verifyListAtAJsonPathExpressionContainsExpectedItems(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + AIMS_CONFIG_DATA_RANK_KEY+"."+AIMS_CONFIG_KEY_KEY, expectedValuesTitleKeyRankMaps);
		
		Map<String, String> expectedKeysDataTypeMetaMap = new HashMap<>();
		expectedKeysDataTypeMetaMap.put(COMMON_META_ERROR_MESSAGE_KEY, "String");
		expectedKeysDataTypeMetaMap.put(COMMON_META_ERROR_TYPE_KEY, "String");

		/* This method verifies that the meta map($.meta) contains the expected keys
		 the data Type of the value of each key is as expected ,Value of key not null and
		 Value of Key is not empty for the type 'String'*/
		helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath(COMMON_RESPONSE_OBJECT_META_KEY,
				expectedKeysDataTypeMetaMap);
		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_META_KEY + "." + COMMON_META_ERROR_MESSAGE_KEY, RESPONSE_META_ERROR_MESSAGE_NONE,
				"String");
		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_META_KEY + "." + COMMON_META_ERROR_TYPE_KEY, RESPONSE_META_ERROR_TYPE_NONE,
				"String");
	}

	/**
	 * @param key
	 * @return
	 * 
	 * 		This method returns a Set of Expected 'title' key values for
	 *         different keys in data Map
	 *         Example if the key is a 'status' then the expected values for 'title'
	 *         is 'Open' and 'Close'
	 */
	private Set<Object> gettingExpectedTitleValuesForDifferentKeysInDataMap(String key) {
		Set<Object> expectedValues = new HashSet<>();

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
		} else if (key.equals(AIMS_CONFIG_DATA_BEEP_SOUND_KEY)) {
			expectedValues.add(AIMS_CONFIG_BEEP_SOUND_MUTE_VALUE);
			expectedValues.add(AIMS_CONFIG_BEEP_SOUND_SINGLE_BEEP_VALUE);
			expectedValues.add(AIMS_CONFIG_BEEP_SOUND_CONTINUOUS_BEEP_VALUE);
		} else if (key.equalsIgnoreCase(AIMS_CONFIG_DATA_ONGOING_KEY)) {
			expectedValues.add(AIMS_CONFIG_ONGOING_YES_VALUE);
			expectedValues.add(AIMS_CONFIG_ONGOING_NO_VALUE);
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

	public void setUpRequestForGetIncidentsDocApi(String ...apiAndParameters) throws IOException {
		super.setUpRequest(apiAndParameters[0]);
		addPayloadToTheRequest(apiAndParameters[1], apiAndParameters[2], apiAndParameters[3]);
	}

	private void addPayloadToTheRequest(String incidentType, String anomaly, String zones) throws IOException {
		String filePath;
		String payload = null;
		filePath = AIMS_GET_INCIDENTS_PAYLOAD_PATH;
		Long fromDateInMilli,toDateInMilli;
		payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);
		if("cloud".equalsIgnoreCase(RuntimeConfigSingleton.getInstance().getCustomerName())){
			fromDateInMilli = 1580556305000L;

			toDateInMilli = 1582975505000L;

		} else {
			//update required from and to Date for payload
			fromDateInMilli = LocalDateTime.now().minusHours(24).atZone(ZoneId.systemDefault())
					.toInstant().toEpochMilli();

			toDateInMilli = LocalDateTime.now().atZone(ZoneId.systemDefault())
					.toInstant().toEpochMilli();
		}

		/**
		 * Update the payload with new values like
		 * Incident type, From date, To date
		 */
		payload = payload
				.replace("incident-type","\""+incidentType+"\"")
				.replace("startDate",fromDateInMilli.toString())
				.replace("endDate",toDateInMilli.toString());

		//Update the payload with the required anomaly list
		if (anomaly.toLowerCase().contains("all")) {
			payload = payload.replace("anomaly-list", AIMS_ANOMALY_LIST);
		} else {
			payload = payload.replace("anomaly-list", "\""+anomaly+"\"");
		}

		if(zones.equalsIgnoreCase("all")){
			payload = payload.replace("zone-list", ZONE_ANOMALY_LIST);
		} else {
			payload = payload.replace("zone-list", "\""+zones+"\"");
		}

		helper.addPayloadStringToTheRequest(payload);
	}

	public void verifyResponseContentTypeIsCSV()  {
		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_CSV);
	}

}
