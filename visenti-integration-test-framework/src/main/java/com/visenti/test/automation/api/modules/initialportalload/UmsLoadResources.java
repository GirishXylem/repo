package com.visenti.test.automation.api.modules.initialportalload;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.*;

import static com.visenti.test.automation.constants.APIConstants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.testng.Assert.*;

public class UmsLoadResources extends RestBaseModule {

	public UmsLoadResources(RestAssuredHelper helper) {
		super(helper);

	}

	public void setUpRequestWithQueryAndPathParams() {
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

		// Adding PathParams to the Request
		Map<String, Object> pathParamsMap = new HashMap<String, Object>();
		pathParamsMap.put("application", UMS_SERVICE_VALIDATES_APPLICATION_NAME);
		pathParamsMap.put("customer", customerName);
		helper.addPathParamsToTheRequest(pathParamsMap);

		// Adding QueryParam to the Request
		helper.addQueryParamsToTheRequest(UMS_SERVICE_VALIDATES_QUERY_PARAM_NAME,
				UMS_SERVICE_VALIDATES_QUERY_PARAM_VALUE);
	}

	public void verifyFullResponseForUMSServiceValidatesApi() throws Exception {
		// Assert the Content-Type header is text/html

		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_TEXT_HTML);

		// Assert Response is an instance of Map

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");

		// Assert Response object is not empty

		helper.verifyValueNotEmptyAtAJsonPathExpression("$");

		// Assert the Response Object contains the expected Keys

		List<String> expectedKeysResponseObject = new ArrayList<String>();
		expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_COUNT_KEY);
		expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_DATA_KEY);
		expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_STATUS_KEY);
		expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY);
		helper.verifyMapContainsKeysAtAGivenJsonPathExpression("$", expectedKeysResponseObject);

		// Verify the '$.status' key value is of type 'Integer'

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_STATUS_KEY, "Int");

		// Assert the status key value to be 202

		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY, STATUS_CODE_202,
				"int");

		// Verify the $.statusText value is of Type String

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY, "string");

		// Assert the statusText key value to be 'Accepted'

		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY,
				STATUS_TEXT_ACCEPTED, "String");

		// Assert The value of data Key ($.data) is of Type List

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");

		// Assert the data List is not Empty

		helper.verifyValueNotEmptyAtAJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY);

					
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
		
		// Assert Each Map in $.data List contains the expected Keys
		List<String> expectedKeysEachMapDataList = new ArrayList<String>();
		//expectedKeysEachMapDataList.add("Test1");
		expectedKeysEachMapDataList.add(UMS_SERVICE_VALIDATES_DATA_BY_RESOURCES_KEY);
		expectedKeysEachMapDataList.add(UMS_SERVICE_VALIDATES_DATA_CUSTOMER_ID_KEY);
		expectedKeysEachMapDataList.add(UMS_SERVICE_VALIDATES_DATA_MAIL_KEY);
		expectedKeysEachMapDataList.add(UMS_SERVICE_VALIDATES_DATA_PROFILES_KEY);
		expectedKeysEachMapDataList.add(UMS_SERVICE_VALIDATES_DATA_USERNAME_KEY);
		//expectedKeysEachMapDataList.add("Test2");
		helper.verifyKeysInEachMapOfAListAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY,
				expectedKeysEachMapDataList);

		//Verify the value Type of the key data[0].customer_id is of type String
		
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + UMS_SERVICE_VALIDATES_DATA_CUSTOMER_ID_KEY, "string");

		// Verify the value of the key data[0].customer_id-(Actual Value) is same as the
		// value of the PathParam {customer}(Expected Value)
		String pathParamCustomerValue = helper.getFilterableRequestSpecification().getPathParams().get("customer");

		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + UMS_SERVICE_VALIDATES_DATA_CUSTOMER_ID_KEY,
				pathParamCustomerValue, "String");
		
		//Verify the value/data type of the key at JsonPath data[0].username is of type String
		
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + UMS_SERVICE_VALIDATES_DATA_USERNAME_KEY, "String");

		// Verify the value of the '$.data[0].username' key is same as the x-service-user
		// Request header
		String xServiceUserHeaderValue = FileReaderManager.getInstance().getConfigReader().getAPIHeaderValues("user");

		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + UMS_SERVICE_VALIDATES_DATA_USERNAME_KEY, xServiceUserHeaderValue,
				"String");
		
		// Verify the profiles Key inside DataList Map(0th index) if of type List
		// '$.data[0].profiles

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + UMS_SERVICE_VALIDATES_DATA_PROFILES_KEY, "List");

		// Verify the profiles List inside the Map at 0th index in DataList is not empty
		// data[0].profiles

		helper.verifyValueNotEmptyAtAJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + UMS_SERVICE_VALIDATES_DATA_PROFILES_KEY);

		// Verify the by_resources Key Value inside the Data List Map(index 0) is an
		// instance of Map
		// data[0].by_resources

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + UMS_SERVICE_VALIDATES_DATA_BY_RESOURCES_KEY, "Map");

		// Verify the by_Resources Map is not empty

		// data[0].by_resources -Not empty

		helper.verifyValueNotEmptyAtAJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + UMS_SERVICE_VALIDATES_DATA_BY_RESOURCES_KEY);

		//Verify the  data[0].by_resources Map keys and their expected datatypes
		
		//Create a Map of expectedKeys and dataTypes and pass as an argument to the Method
		//verifyKeysAndTheirDataTypeInAMapAtJsonPath
		Map<String,String>expectedMapKeysAndDataType=new HashMap<String,String>();
		//expectedMapKeysAndDataType.put("Test1", "list");
		expectedMapKeysAndDataType.put(UMS_SERVICE_VALIDATES_BY_RESOURCES_APPS_KEY, "list");
		expectedMapKeysAndDataType.put(UMS_SERVICE_VALIDATES_BY_RESOURCES_SCRIPTS_KEY, "list");
		expectedMapKeysAndDataType.put(UMS_SERVICE_VALIDATES_BY_RESOURCES_SENSORS_KEY, "list");
		expectedMapKeysAndDataType.put(UMS_SERVICE_VALIDATES_BY_RESOURCES_ZONES_KEY, "List");
		//expectedMapKeysAndDataType.put("Test2", "map");
		//This method verifies the presence of keys in the Map at the JsonPath ,the dataTypes of keys as expected
		//Value of key not null and Value of Key is not empty for the type 'List','String' or 'Map'
		helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + UMS_SERVICE_VALIDATES_DATA_BY_RESOURCES_KEY, expectedMapKeysAndDataType);
		
		}
}
