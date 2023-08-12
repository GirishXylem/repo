package com.visenti.test.automation.api.modules.plotter.dma;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.api.modules.initialportalload.GisLoadCustomerInfo;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import com.visenti.test.automation.utils.CommonUtils;
import io.restassured.path.json.JsonPath;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class DataLoadDmaSensors extends RestBaseModule {
	SoftAssert softAssert = new SoftAssert();
	public static List<String> dmaDataFailedList= new ArrayList<>();

	public DataLoadDmaSensors(RestAssuredHelper helper) {
		super(helper);

	}

	public void setUpRequestForDmaDataApi(String ...apiAndParameters) throws Exception {
		super.setUpRequest(apiAndParameters[0]);
		addPayloadToTheRequestAndVerifyTheResponse(apiAndParameters[1], apiAndParameters[2], apiAndParameters[3],apiAndParameters[4],apiAndParameters[5]);
	}

	private void addPayloadToTheRequestAndVerifyTheResponse(String endPoint, String sensorType, String units, String dataType,  String interval) throws Exception {
		String filePath;
		String payload = null;
		filePath = DMA_DATA_PAYLOAD_PATH;
		payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);

		//update required start and end Date for payload
		Long fromDateInMilli = LocalDateTime.now().minusHours(24).atZone(ZoneId.systemDefault())
				.toInstant().toEpochMilli();

		Long toDateInMilli = LocalDateTime.now().atZone(ZoneId.systemDefault())
				.toInstant().toEpochMilli();

		sensorType= sensorType.toLowerCase().replace(" ","_");
		dataType = dataType.toLowerCase().replace(" ","_");

		//To get all the Dma Id and Name to a Map.
		GisLoadCustomerInfo gisLoadCustomerInfo = new GisLoadCustomerInfo(helper);
		Map<String, Object> dmaNameAndID = gisLoadCustomerInfo.getDmaIDAndName();

		/**
		 * Update the payload with new values like
		 * sensor type, start and end date, units
		 */
		for(Map.Entry<String, Object> entry : dmaNameAndID.entrySet()){
			Log.info("DMA being replaced in payload is : "+entry.getKey()+"-"+entry.getValue().toString());
			payload = payload
					.replace("dma-data-type",dataType)
					.replace("dma-sensor-unit",units)
					.replace("dma-sensor",sensorType)
					.replace("dma-interval",interval)
					.replace("dma-id", entry.getValue().toString())
					.replace("startDate",fromDateInMilli.toString())
					.replace("endDate",toDateInMilli.toString());
			//Added the update payload for the request
			helper.addPayloadStringToTheRequest(payload);
			//Replace the DMA id with the string to pick the new DMA
			payload = payload.replace(entry.getValue().toString(),"dma-id");
			//Perform the Curd operation on the endpoint
			helper.performRequestOnAnEndPoint("post",endPoint);
			//Verify the response is successful
			helper.verifyStatusCodeAsExpected("200");
			//Verify response with details
			verifyResponseForDmaDataApi();

			JsonPath jPath = helper.getJsonPath();
			//Verify the sensor type is the same
			String value = helper.getValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0].sensorType").toString();
			boolean sensorKey = false,dataNotNullKey = false,unitsKey = false, sensorIdKey = false;
			if(value.equalsIgnoreCase(sensorType)){
				sensorKey = true;
			}else{
				if (!dmaDataFailedList.contains(entry.getKey()))
					dmaDataFailedList.add(entry.getKey());
			}
			softAssert.assertTrue(sensorKey
					,"For "+entry.getKey()+ "the sensor type "+sensorType+" in the request " +
							"and response sensor type "+value+" are not same");
			if(helper.getValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]."+CUSTOMER_LEAKS_TRENDS_CSV_DATA_KEY)!=null){
				dataNotNullKey=true;
			}else{
				if (!dmaDataFailedList.contains(entry.getKey()))
					dmaDataFailedList.add(entry.getKey());
			}
			//Verify if we have data for one day time period by verifying the csvData Key
			softAssert.assertTrue(dataNotNullKey,
					"There is no data for "+entry.getKey());

			List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"."+CUSTOMER_LEAKS_TRENDS_META_LIST_KEY+"[0]");
			for (Map<String, Object> actualMap : actualList) {
				for (Map.Entry<String, Object> sensorEntry : actualMap.entrySet()) {
					if(sensorEntry.getKey().equalsIgnoreCase("sensorUnit")){
						if(sensorEntry.getValue().toString().equalsIgnoreCase(units)){
							unitsKey=true;
						}else{
							if (!dmaDataFailedList.contains(entry.getKey()))
								dmaDataFailedList.add(entry.getKey());
						}
						softAssert.assertTrue(unitsKey
								,"For "+entry.getKey()+ " and the sensor "+sensorType+","+
										" the units "+units+" in the request and response units "
										+sensorEntry.getValue()+" are not same");
					} else {
						if(sensorEntry.getValue().toString().equalsIgnoreCase(entry.getValue().toString())){
							sensorIdKey=true;
						}else{
							if (!dmaDataFailedList.contains(entry.getKey()))
								dmaDataFailedList.add(entry.getKey());
						}
						softAssert.assertTrue(sensorIdKey
								,"For "+entry.getKey()+ " and the sensor "+sensorType+","+
										"the dma id "+entry.getValue().toString()+" in the request " +
										"and response sensorId "+sensorEntry.getValue()+" are not same");
					}
				}
			}
		}
		softAssert.assertAll();
	}

	public void verifyResponseForDmaDataApi() throws Exception {
		// Assert the Content-Type header in Response is text/html
		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_TEXT_HTML);

		// Assert Response is an instance of Map
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");

		// Assert Response object is not empty
		helper.verifyValueNotEmptyAtAJsonPathExpression("$");

		// Assert Response Object contains the expected Keys and verify Data-Types of value of each Key

		// Creating a Map containing the expected keys in Response Object Map and their
		// data types
		Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<String, String>();

		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_COUNT_KEY, "int");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "Int");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY, "String");

        /* This method verifies that the Response Object($) map contains the expected keys.
           The data Type of the value of each key is as expected ,Value of key not null and
           Value of Key is not empty for the type 'List','String' or 'Map'*/
		helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath("$", expectedKeysDataTypesResponseMap);

		// Assert the status key value to be 202
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY, STATUS_CODE_202,
				"int");

		// Assert the statusText key value to be 'Accepted'
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY,
				STATUS_TEXT_ACCEPTED, "String");

		// Asserting the data List expected Size should be equal to the 'count' key value
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

}
