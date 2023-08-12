package com.visenti.test.automation.api.modules.plotter.station;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import com.visenti.test.automation.utils.CommonUtils;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;

public class DataLoadStationSensors extends RestBaseModule {
	SoftAssert softAssert = new SoftAssert();

	public DataLoadStationSensors(RestAssuredHelper helper) {
		super(helper);
	}

	public void setUpRequestForStationDataApi(String ...apiAndParameters) throws Exception {
		super.setUpRequest(apiAndParameters[0]);
		addPayloadToTheRequestAndVerifyTheResponse(apiAndParameters[1], apiAndParameters[2], apiAndParameters[3],apiAndParameters[4],apiAndParameters[5]);
	}

	private void addPayloadToTheRequestAndVerifyTheResponse(String sensor, String units, String dataType,  String interval, String station) throws Exception {
		String filePath;
		String payload = null;
		filePath = DMA_DATA_PAYLOAD_PATH;
		payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);

		//update required start and end Date for payload
		Long fromDateInMilli = LocalDateTime.now().minusHours(24).atZone(ZoneId.systemDefault())
				.toInstant().toEpochMilli();

		Long toDateInMilli = LocalDateTime.now().atZone(ZoneId.systemDefault())
				.toInstant().toEpochMilli();

		dataType = dataType.toLowerCase().replace(" ","_");

		/**
		 * Update the payload with new values like
		 * sensor type, start and end date, units
		 */
			payload = payload
					.replace("dma-data-type",dataType)
					.replace("dma-sensor-unit",units)
					.replace("dma-sensor",sensor)
					.replace("dma-interval",interval)
					.replace("dma-id", station)
					.replace("startDate",fromDateInMilli.toString())
					.replace("endDate",toDateInMilli.toString());
			//Added the update payload for the request
			helper.addPayloadStringToTheRequest(payload);
	}

	public void verifyResponseForStationDataApi() throws Exception {
		// Assert the Content-Type header in Response is text/html
		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_TEXT_HTML);

		// Assert Response is an instance of Map
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");

		// Assert Response object is not empty
		helper.verifyValueNotEmptyAtAJsonPathExpression("$");

		// Assert Response Object contains the expected Keys and verify Data-Types of value of each Key

		// Creating a Map containing the expected keys in Response Object Map and their
		// data types
		Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();

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

	public void verifyMetaDataResponseForStationDataApi(String station, String units, String sensor){
		//Verify the sensor type is the same
		String sensorTypeActual = helper.getValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+DATA_META_LIST_KEY+"[0].sensorTypeActual").toString();
		String sensorTypeBackend = helper.getValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+DATA_META_LIST_KEY+"[0].sensorTypeBackend").toString();
		softAssert.assertTrue(sensor.contains(sensorTypeActual)
				,"For "+station+ " the sensor type actual "+sensor+" in the request " +
						"and response sensor type "+sensor+" are not same");
		softAssert.assertTrue(sensorTypeBackend.equals(sensor)
				,"For "+station+ " the sensor type backend "+sensor+" in the request " +
						"and response sensor type "+sensor+" are not same");

		//Verify units is the same
		String sensorUnits = helper.getValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+DATA_META_LIST_KEY+"[0].sensorUnit").toString();
		softAssert.assertTrue(sensorUnits.equals(units)
				,"For "+station+ " the sensor type backend "+sensor+" in the request " +
						"and response sensor type "+sensor+" are not same");

		//Verify station data is the same
		String sensorId = helper.getValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+DATA_META_LIST_KEY+"[0].sensorId").toString();
		softAssert.assertTrue(sensorId.equals(station+"_"+sensor)
				,"Sensor id "+station+"_"+sensor+ " for "+ station +" and" + sensor + " is not correct");
		String stationId = helper.getValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+DATA_META_LIST_KEY+"[0].stationId").toString();
		softAssert.assertTrue(stationId.equals(station)
				,"Station id for "+ station +" is not the same");
		softAssert.assertAll();
	}

}
