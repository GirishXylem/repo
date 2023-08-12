package com.visenti.test.automation.api.modules.initialportalload;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.*;

public class DmaLoadCommonAttributes extends RestBaseModule {

	public DmaLoadCommonAttributes(RestAssuredHelper helper) {
		super(helper);

	}

	public void setUpRequestForCommonAttributesApi() {
		super.setUpRequest("dma");
	}

	public void verifyFullResponseForCommonAttributesApi() throws Exception {
		// Assert the Content-Type header is text/html

		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_TEXT_HTML);

		// Assert Response is an instance of Map

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");

		// Assert Response object is not empty

		helper.verifyValueNotEmptyAtAJsonPathExpression("$");

		// Assert the Response Object contains the expected Keys

		List<String> expectedKeysResponseObject = new ArrayList<String>();
		//expectedKeysResponseObject.add("Test1");
		expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_COUNT_KEY);
		expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_DATA_KEY);
		expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_STATUS_KEY);
		expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY);
		//expectedKeysResponseObject.add("Test2");

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
		expectedKeysEachMapDataList.add(DMA_COMM_ATTRIBUTE_DATA_BASE_PROPERTIES_KEY);
		expectedKeysEachMapDataList.add(DMA_COMM_ATTRIBUTE_DATA_CUSTOMER_ID_KEY);
		expectedKeysEachMapDataList.add(DMA_COMM_ATTRIBUTE_DATA_CUSTOMER_KEY);
		//expectedKeysEachMapDataList.add(DMA_COMM_ATTRIBUTE_DATA_ELASTIC_PROPERTIES_KEY);
		expectedKeysEachMapDataList.add(DMA_COMM_ATTRIBUTE_DATA_ID_KEY);
		//	expectedKeysEachMapDataList.add(DMA_COMM_ATTRIBUTE_DATA_VIEW_PROPERTIES_KEY);
		//expectedKeysEachMapDataList.add("Test2");
		helper.verifyKeysInEachMapOfAListAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY,
				expectedKeysEachMapDataList);
		// Verify the value/data Type of the key data[0].customer_id is of type String
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_CUSTOMER_ID_KEY, "String");

		// Verify the value of the Key data[0].customerId equal to X-Service-Customer
		// header value

		String xServiceCustomerHeaderValue = FileReaderManager.getInstance().getConfigReader()
				.getAPIHeaderValues("customer");
		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_CUSTOMER_ID_KEY,
				xServiceCustomerHeaderValue, "String");

		// Verify the data[0].customer key value is of Type Map

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_CUSTOMER_KEY, "Map");

		// Verify the data[0].customer Map is not Empty

		helper.verifyValueNotEmptyAtAJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_CUSTOMER_KEY);

		// Verify the data[0].customer Map contains the keys
		// id and name

		List<String> expectedKeysCustomerMap = new ArrayList<String>();
		//expectedKeysCustomerMap.add("Test1");
		expectedKeysCustomerMap.add(DMA_COMM_ATTRIBUTE_CUSTOMER_ID_KEY);
		expectedKeysCustomerMap.add(DMA_COMM_ATTRIBUTE_CUSTOMER_NAME_KEY);
		//expectedKeysCustomerMap.add("Test2");

		helper.verifyMapContainsKeysAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_CUSTOMER_KEY,
				expectedKeysCustomerMap);

		// Verify the value of data[0].customer.name is of type String

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]."
				+ DMA_COMM_ATTRIBUTE_DATA_CUSTOMER_KEY + "." + DMA_COMM_ATTRIBUTE_CUSTOMER_NAME_KEY, "String");

		// Verify the value of data[0].customer.name key =X-service-Customer Header
		// value in Upper Case

		String xServiceCustomerHeaderUpperCase = xServiceCustomerHeaderValue.toUpperCase();

		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]."
				+ DMA_COMM_ATTRIBUTE_DATA_CUSTOMER_KEY + "." + DMA_COMM_ATTRIBUTE_CUSTOMER_NAME_KEY,
				xServiceCustomerHeaderUpperCase, "String");
		
		//Verify the data[0].baseProperties is an instanceof Map 
		
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_BASE_PROPERTIES_KEY, "Map");
		
		//Verify the data[0].baseProperties Map is not empty
		
		helper.verifyValueNotEmptyAtAJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_BASE_PROPERTIES_KEY);
		
		//Commenting the below code as elasticProperties and viewProperties are not mandatory keys

		// Verify the data[0].elasticProperties an instanceof Map

		/*helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_ELASTIC_PROPERTIES_KEY, "Map");

		// Verify the data[0].elasticProperties Map is not Empty

		helper.verifyValueNotEmptyAtAJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_ELASTIC_PROPERTIES_KEY);

		// Verify the data[0].elasticProperties Map contains the expectedKeys
		// index,layersIndex,pipeIndex,sensorType,stationType

		List<String> expectedKeysElasticPropertiesMap = new ArrayList<String>();
		//expectedKeysElasticPropertiesMap.add("Test1");
		expectedKeysElasticPropertiesMap.add(DMA_COMM_ATTRIBUTE_ELASTIC_PROPERTIES_INDEX_KEY);
		expectedKeysElasticPropertiesMap.add(DMA_COMM_ATTRIBUTE_ELASTIC_PROPERTIES_LAYERS_INDEX_KEY);
		expectedKeysElasticPropertiesMap.add(DMA_COMM_ATTRIBUTE_ELASTIC_PROPERTIES_PIPE_INDEX_KEY);
		expectedKeysElasticPropertiesMap.add(DMA_COMM_ATTRIBUTE_ELASTIC_PROPERTIES_SENSOR_TYPE_KEY);
		expectedKeysElasticPropertiesMap.add(DMA_COMM_ATTRIBUTE_ELASTIC_PROPERTIES_STATION_TYPE_KEY);
		//expectedKeysElasticPropertiesMap.add("Test2");
		helper.verifyMapContainsKeysAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_ELASTIC_PROPERTIES_KEY,
				expectedKeysElasticPropertiesMap);

		// Verify the data[0].viewProperities is an instanceof Map

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_VIEW_PROPERTIES_KEY, "Map");

		// Verify the data[0].viewProperties Map is not Empty

		helper.verifyValueNotEmptyAtAJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_VIEW_PROPERTIES_KEY);

		// Verify the data[0].viewProperties Map contains the expectedKeys
		// (center,zoom)

		List<String> expectedKeysViewPropertiesMap = new ArrayList<String>();
		expectedKeysViewPropertiesMap.add(DMA_COMM_ATTRIBUTE_VIEW_PROPERTIES_CENTRE_KEY);
		expectedKeysViewPropertiesMap.add(DMA_COMM_ATTRIBUTE_VIEW_PROPERTIES_ZOOM_KEY);
		helper.verifyMapContainsKeysAtAGivenJsonPathExpression(
				COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + DMA_COMM_ATTRIBUTE_DATA_VIEW_PROPERTIES_KEY,
				expectedKeysViewPropertiesMap);*/
		}
}
