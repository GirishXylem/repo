package com.visenti.test.automation.api.modules.initialportalload;

import static com.visenti.test.automation.constants.APIConstants.COMMON_RESPONSE_OBJECT_DATA_KEY;
import static com.visenti.test.automation.constants.APIConstants.COMMON_RESPONSE_OBJECT_STATUS_KEY;
import static com.visenti.test.automation.constants.APIConstants.COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY;
import static com.visenti.test.automation.constants.APIConstants.CONTENT_TYPE_TEXT_HTML;
import static com.visenti.test.automation.constants.APIConstants.STATUS_CODE_202;
import static com.visenti.test.automation.constants.APIConstants.STATUS_TEXT_ACCEPTED;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_CUSTOMERS_APPLICATION_IDS_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_CUSTOMERS_CUSTOMER_ID_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_CUSTOMERS_ID_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_CUSTOMERS_LICENSE_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_CUSTOMERS_MAP_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_CUSTOMERS_NAME_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_CUSTOMERS_TIMEZONE_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_DATA_CUSTOMERS_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_DATA_CUSTOMER_ID_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_DATA_ID_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_DATA_MAIL_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_DATA_PROFILES_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_DATA_USERNAME_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_DATA_USER_PROFILE_KEY;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_QUERY_PARAM_NAME;
import static com.visenti.test.automation.constants.APIConstants.UMS_ACCOUNT_QUERY_PARAM_VALUE;

import java.util.ArrayList;
import java.util.List;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.*;

public class UmsLoadAccountData extends RestBaseModule {

	public UmsLoadAccountData(RestAssuredHelper helper) {
		super(helper);

	}

	public void setUpRequestWithGivenQueryParams() {
		String baseUri = "";
		String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
		assert customerName!=null;
		if(	customerName.equalsIgnoreCase("pub")){
			baseUri = "https://" + PortalConfigManagement.getPortalUMSPrefix() + ".waterwise"
					+ PortalConfigManagement.getPortalDomain();
		} else {
			baseUri = "https://" + PortalConfigManagement.getPortalUMSPrefix() + "." + PortalConfigManagement.getUMSPortalDomain();
		}
		helper.addBaseURIAndBasePathToTheRequest(baseUri, "");
		super.addCommonHeadersToTheRequest();
		helper.addQueryParamsToTheRequest(UMS_ACCOUNT_QUERY_PARAM_NAME, UMS_ACCOUNT_QUERY_PARAM_VALUE);
	}

	public void verifyFullResponseForUMSAccountApi() throws Exception {

		// Validate the Content-Type header is text/html

		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_TEXT_HTML);
		
		// Validate Response is an instance of Map

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");
		
		
		// Validate Response object is not empty

		helper.verifyValueNotEmptyAtAJsonPathExpression("$");
		
		List<String> expectedKeysResponseObject = new ArrayList<String>();
		expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_DATA_KEY);
		expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_STATUS_KEY);
		expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY);
		//expectedKeysResponseObject.add("Test1");
		//expectedKeysResponseObject.add("Test2");

		helper.verifyMapContainsKeysAtAGivenJsonPathExpression("$", expectedKeysResponseObject);
		
		
		//Verify the '$.status' key value is of type 'Integer'
		
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_STATUS_KEY, "Int");

		// Assert the status key value to be 202

		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY,
				STATUS_CODE_202, "int");
		
		//Verify the $.statusText value is of Type String
		
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY, "string");
		
		// Assert the statusText key value to be 'Accepted'

		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY, STATUS_TEXT_ACCEPTED, "String");

				
		//Verify $.data key value is of type Map
		
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY, "Map");
		
		//Verify the data Map is not empty
		
		helper.verifyValueNotEmptyAtAJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY);
		
		// Assert $.data Map contains Expected Keys
		
		List<String> expectedKeysDataMap = new ArrayList<String>();
		//expectedKeysDataMap.add("Fake 2");
		expectedKeysDataMap.add(UMS_ACCOUNT_DATA_CUSTOMER_ID_KEY);
		expectedKeysDataMap.add(UMS_ACCOUNT_DATA_ID_KEY);
		expectedKeysDataMap.add(UMS_ACCOUNT_DATA_MAIL_KEY);
		expectedKeysDataMap.add(UMS_ACCOUNT_DATA_USER_PROFILE_KEY);
		expectedKeysDataMap.add(UMS_ACCOUNT_DATA_USERNAME_KEY);
		expectedKeysDataMap.add(UMS_ACCOUNT_DATA_CUSTOMERS_KEY);
		expectedKeysDataMap.add(UMS_ACCOUNT_DATA_PROFILES_KEY);
		//expectedKeysDataMap.add("Fake 1");

		helper.verifyMapContainsKeysAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY, expectedKeysDataMap);
		
		// Verify the '$.data.username key value is of type String

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + UMS_ACCOUNT_DATA_USERNAME_KEY, "String");

		// Verify the value of the '$.data.username' key is same as the x-service-user
		// Request header
		String xServiceUserHeaderValue = FileReaderManager.getInstance().getConfigReader().getAPIHeaderValues("user");

		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + UMS_ACCOUNT_DATA_USERNAME_KEY, xServiceUserHeaderValue,
				"String");
		
		// Verify the $.data.customers value is an instance of list

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + UMS_ACCOUNT_DATA_CUSTOMERS_KEY, "List");

		// Verify the $.data.customers List is not empty

		helper.verifyValueNotEmptyAtAJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + UMS_ACCOUNT_DATA_CUSTOMERS_KEY);
		
		// Verify Keys in each Map of data.customers List

		List<String> expectedKeysEachMapInCustomersList = new ArrayList<String>();
		expectedKeysEachMapInCustomersList.add(UMS_ACCOUNT_CUSTOMERS_CUSTOMER_ID_KEY);
		expectedKeysEachMapInCustomersList.add(UMS_ACCOUNT_CUSTOMERS_ID_KEY);
		expectedKeysEachMapInCustomersList.add(UMS_ACCOUNT_CUSTOMERS_TIMEZONE_KEY);
		//expectedKeysEachMapInCustomersList.add("123");
		expectedKeysEachMapInCustomersList.add(UMS_ACCOUNT_CUSTOMERS_APPLICATION_IDS_KEY);
		expectedKeysEachMapInCustomersList.add(UMS_ACCOUNT_CUSTOMERS_MAP_KEY);
		expectedKeysEachMapInCustomersList.add(UMS_ACCOUNT_CUSTOMERS_NAME_KEY);
		expectedKeysEachMapInCustomersList.add(UMS_ACCOUNT_CUSTOMERS_LICENSE_KEY);
		//expectedKeysEachMapInCustomersList.add("Test-fail");
		helper.verifyKeysInEachMapOfAListAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + UMS_ACCOUNT_DATA_CUSTOMERS_KEY,
				expectedKeysEachMapInCustomersList);
		
		// Verify the $.data.customers.customer_id list should contains the customer
		// defined in x-service-customer header

		String xServiceCustomerHeaderValue = FileReaderManager.getInstance().getConfigReader()
				.getAPIHeaderValues("customer");

		helper.verifyListAtAJsonPathExpressionContainsAnItem(COMMON_RESPONSE_OBJECT_DATA_KEY + "."
				+ UMS_ACCOUNT_DATA_CUSTOMERS_KEY + "." + UMS_ACCOUNT_CUSTOMERS_CUSTOMER_ID_KEY,
				xServiceCustomerHeaderValue);

		// Verify the $.data.profiles value is an instance of list

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + UMS_ACCOUNT_DATA_PROFILES_KEY, "List");

		// Verify the $.data.profiles List is not empty

		helper.verifyValueNotEmptyAtAJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "." + UMS_ACCOUNT_DATA_PROFILES_KEY);
		
		
	}

}
