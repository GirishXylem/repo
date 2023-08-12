package com.visenti.test.automation.api.modules.aims;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.*;
import com.visenti.test.automation.utils.CommonUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.visenti.test.automation.constants.APIConstants.*;

public class AimsLoadUserPreferences extends RestBaseModule {

	public AimsLoadUserPreferences(RestAssuredHelper helper) {
		super(helper);

	}

	public void setUpRequestWithQueryParams(String api) {
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

		if(customerName.equalsIgnoreCase("pub")||customerName.equalsIgnoreCase("cloud")){
			customerId = "pub";
		} else {
			customerId = PortalConfigManagement.getPortal();
		}

		String userId = FileReaderManager.getInstance().getConfigReader().getAPIHeaderValues("user");

		Map<String, Object> queryParamsMap = new HashMap<>();
		queryParamsMap.put(AIMS_USER_PREFERENCES_QUERY_PARAM_NAME_USER_ID, userId);
		queryParamsMap.put(AIMS_USER_PREFERENCES_QUERY_PARAM_NAME_CUSTOMER_ID, customerId);

		helper.addQueryParamsToTheRequest(queryParamsMap);
		
		//Disabling the url encoding as @ will in the userId query param will get encoded to %40
		helper.urlEncodingEnabled(false);
	}

	public void setUpRequestForSaveUserPreference(String api) throws IOException {
		super.setUpRequest(api);
		addPayloadToTheRequest();
	}

	private void addPayloadToTheRequest() throws IOException {
		String filePath,customerName;
		String payload = null;
		filePath = AIMS_SAVE_PREFERENCE_PATH;
		payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);
		ConfigFileReader configFileReader = new ConfigFileReader();

		if ("cloud".equalsIgnoreCase(RuntimeConfigSingleton.getInstance().getCustomerName())) {
			customerName = "pub";
		} else {
			customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
		}


		payload = payload
				.replace("user_name", configFileReader.getAPIHeaderValues("user"))
				.replace("customer_name",customerName);

		helper.addPayloadStringToTheRequest(payload);
	}

	public void verifyResponseForAIMSSaveUserPreferenceAPI() throws Exception {
		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_JSON);
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");
		helper.verifyValueNotEmptyAtAJsonPathExpression("$");
		helper.verifyMapContainsAKeyAtAGivenJsonPathExpression("$",COMMON_RESPONSE_OBJECT_DATA_KEY );
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY,
				STATUS_CODE_200, "Int");
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY, STATUS_SUCCESS,
				"String");

		Map<String, String> expectedKeysDataTypeMetaMap = new HashMap<>();

		expectedKeysDataTypeMetaMap.put(COMMON_META_ERROR_MESSAGE_KEY, "String");
		expectedKeysDataTypeMetaMap.put(COMMON_META_ERROR_TYPE_KEY, "String");

		helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath(COMMON_RESPONSE_OBJECT_META_KEY,
				expectedKeysDataTypeMetaMap);

		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_META_KEY + "." + COMMON_META_ERROR_MESSAGE_KEY, RESPONSE_META_ERROR_MESSAGE_NONE,
				"String");

		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_META_KEY + "." + COMMON_META_ERROR_TYPE_KEY, RESPONSE_META_ERROR_TYPE_NONE,
				"String");

	}
	public void verifyFullResponseForAimsGetUserPreferencesApi() throws Exception {
		// Validate the Content-Type header is application/json

		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_JSON);

		// Assert Response is an instance of Map

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");

		// Assert Response object is not empty

		helper.verifyValueNotEmptyAtAJsonPathExpression("$");
		
		//Since the value of 'data' key in the response can be an empty List for new user
		//who hasn't saved preferences,hence doing separate validations and not asserting empty
		
		//Asserting 'data' key is present in the Response Map
		
		helper.verifyMapContainsAKeyAtAGivenJsonPathExpression("$",COMMON_RESPONSE_OBJECT_DATA_KEY );
		
		//Asserting '$.data' key value is an instance of List
		
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
		
		//Asserting Value of a $.data key is not null
		
		helper.verifyValueNotNullAtAGivenJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY);

		// Assert Response Object contains the expected Keys and verify Data-Types of
		// value of each Key

		// Creating a Map containing the expected keys in Response Object Map and their
		// data types
		
		Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();

		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY, "int");
		//expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
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

		// Verify the keys and their value dataTypes in meta Map

		Map<String, String> expectedKeysDataTypeMetaMap = new HashMap<>();

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
				COMMON_RESPONSE_OBJECT_META_KEY + "." + COMMON_META_ERROR_MESSAGE_KEY, RESPONSE_META_ERROR_MESSAGE_NONE,
				"String");

		// Verify meta.error_type key value to be equal to 'none'

		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_META_KEY + "." + COMMON_META_ERROR_TYPE_KEY, RESPONSE_META_ERROR_TYPE_NONE,
				"String");
	}

}
