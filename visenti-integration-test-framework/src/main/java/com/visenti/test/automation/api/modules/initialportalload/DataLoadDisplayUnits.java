package com.visenti.test.automation.api.modules.initialportalload;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.*;

public class DataLoadDisplayUnits extends RestBaseModule {

	public DataLoadDisplayUnits(RestAssuredHelper helper) {
		super(helper);

	}

	public void setUpRequestForDataApiDisplayUnit() {
		super.setUpRequest("data");
	}

	public void verifyFullResponseForDisplayUnitApi() throws Exception {
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

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_STATUS_KEY, "int");

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

		
		//Verify eachMap in the the DataList contains the expected keys and verify the DataTypes of each Key
		Map<String,String>expectedMapKeysAndValueDataType=new HashMap<String,String>();
		//expectedMapKeysAndDataType.put("Fake", "Int");
		expectedMapKeysAndValueDataType.put(DATA_DISPLAY_UNIT_DATA_ID_KEY, "Int");
		expectedMapKeysAndValueDataType.put(DATA_DISPLAY_UNIT_DATA_SENSOR_TYPE_ACTUAL_KEY, "string");
		expectedMapKeysAndValueDataType.put(DATA_DISPLAY_UNIT_DATA_SENSOR_TYPE_BACKEND_KEY, "String");
		//expectedMapKeysAndDataType.put("Test", "Int");
		
		//This method verifies each Map in the List is not empty,the presence of keys in each Map in a List at the JsonPath ,
		//the dataTypes of value of keys as expected ,Value of key not null and Value of Key is not empty for the type 'List','String' or 'Map'
		helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY, expectedMapKeysAndValueDataType);
		
		}

}
