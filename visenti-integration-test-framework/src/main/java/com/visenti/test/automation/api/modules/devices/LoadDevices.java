package com.visenti.test.automation.api.modules.devices;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.*;
import com.visenti.test.automation.utils.CommonUtils;
import io.restassured.path.json.JsonPath;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;

public class LoadDevices extends RestBaseModule {

	public LoadDevices(RestAssuredHelper helper) {
		super(helper);

	}

	public void setUpRequestForDevicesMetaSearchApi(String... args) throws IOException {
		String baseUri;
		String api ="gis";
		String apiUrlSubString = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs(api);;
		String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
		String environment = RuntimeConfigSingleton.getInstance().getExecutionEnvironment();
		if (customerName.equalsIgnoreCase("pub")||customerName.equalsIgnoreCase("cloud")) {
			baseUri = "https://" + apiUrlSubString + "-" + PortalConfigManagement.getPortalPrefix() + "-view"
					+ PortalConfigManagement.getPortalDomain();
		} else  {
			if(environment.equalsIgnoreCase("prod")){
				baseUri = "https://" + apiUrlSubString + "-" + PortalConfigManagement.getPortalDomain();
			} else {
				baseUri = "https://" + apiUrlSubString + "-" + PortalConfigManagement.getPortalPrefix() + "-"
						+ PortalConfigManagement.getPortalDomain();
			}

		}
		helper.addBaseURIAndBasePathToTheRequest(baseUri, "");
		super.addCommonHeadersToTheRequest();
		helper.addHeaderToTheRequest("Content-Type", CONTENT_TYPE_TEXT_PLAIN);
		addPayloadToTheRequest(args[0], args[1], customerName);
	}
	private void addPayloadToTheRequest(String device, String network, String customerName) throws IOException {
		String payload;
		payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(DEVICES_META_SEARCH_NETWORK_PAYLOAD_PATH);
		payload = payload
				.replace("network-name", NETWORK_NAME_POTABLE_WATER)
		        .replace("zone-list",
						Objects.requireNonNull(ConfigFileReader.getConfigProperty(customerName + ".zones.all")));
		switch (device.toLowerCase()) {
		case "acoustic":
			payload = payload.replace("actual-sensors", ACOUSTIC_SENSORTYPE_ACTUAL_VALUE+"]");
			break;

		case "btry":
			payload = payload.replace("actual-sensors", BTRY_SENSORTYPE_ACTUAL_VALUE+"]");
			break;

		case "customermeter":
			payload = payload.replace("actual-sensors", CUSTOMERMETER_SENSORTYPE_ACTUAL_VALUE+"]");
			break;

		case "flow":
			payload = payload.replace("actual-sensors", FLOW_SENSORTYPE_ACTUAL_VALUE+"]");
			break;

		case "level":
			payload = payload.replace("actual-sensors", LEVEL_SENSORTYPE_ACTUAL_VALUE+"]");
			break;

		case "pressure":
			payload = payload.replace("actual-sensors", PRESSURE_SENSORTYPE_ACTUAL_VALUE+"]");
			break;
		case "transient":
			payload = payload.replace("actual-sensors", TRANSIENT_SENSORTYPE_ACTUAL_VALUE+"],"+TRANSIENT_TERM_VALUE);
			break;

		case "wqy":
			payload = payload.replace("actual-sensors", WQY_SENSORTYPE_ACTUAL_VALUE+"]");
			break;

		default:
			throw new RuntimeException("Wrong Device name passed");
		}

		helper.addPayloadStringToTheRequest(payload);
		Log.info("Successfully added payload to the Request for device '" + device + "' and network '" + network
				+ "'");
		Reporter.addStepLog("Successfully added payload to the Request for device '" + device + "' and network '"
				+ network + "' ");
		Log.info("Payload :\n " + payload);
	}

	public void verifyFullResponseForTheLoadDevicesApi() throws Exception {
		// Validate Response is an instance of Map
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");
		// Validate Response object Map is not empty
		helper.verifyValueNotEmptyAtAJsonPathExpression("$");
		Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY, "int");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_META_KEY, "map");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "String");
		//To verify at the path "$" from the above map
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

		helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath(COMMON_RESPONSE_OBJECT_META_KEY,
				expectedKeysDataTypeMetaMap);
		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_META_KEY + "." + COMMON_META_ERROR_MESSAGE_KEY, RESPONSE_META_ERROR_MESSAGE_NONE,
				"String");
		helper.verifyValueAtAJsonPathExpressionForAValidationType(
				COMMON_RESPONSE_OBJECT_META_KEY + "." + COMMON_META_ERROR_TYPE_KEY, RESPONSE_META_ERROR_TYPE_NONE,
				"String");

	}

	public void verifySpecificResponseForTheLoadDevicesApi(String network) throws Exception {
		Map<String, String> expectedKeysDataTypesDataMap = new HashMap<>();
		expectedKeysDataTypesDataMap.put("_id", "String");
		expectedKeysDataTypesDataMap.put("_level", "Int");
		expectedKeysDataTypesDataMap.put("_source", "map");
		expectedKeysDataTypesDataMap.put("_type", "String");

		helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY,
				expectedKeysDataTypesDataMap);

		SoftAssert softAssert = new SoftAssert();
		Object value;
		JsonPath jPath = helper.getJsonPath();
		List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"._source");
		for (Map<String, Object> actualMap : actualList) {
			for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
				String expectedKey = entry.getKey();
				if(expectedKey.equalsIgnoreCase("sensorList")){
					value = actualMap.get(expectedKey);
					softAssert.assertTrue(Objects.nonNull(value),"Stations is not having any sensors");
				} else if (expectedKey.equalsIgnoreCase("icon")){
					value = actualMap.get(expectedKey);
					softAssert.assertTrue(Objects.nonNull(value),"Stations is not having any icon associated");
				} else if (expectedKey.equalsIgnoreCase("loc")){
					value = actualMap.get(expectedKey);
					softAssert.assertTrue(Objects.nonNull(value),"Stations is not having any location");
				} else if (expectedKey.equalsIgnoreCase("display_name")){
					value = actualMap.get(expectedKey);
					String station = actualMap.get("name").toString();
					softAssert.assertEquals(station,value.toString(),"Stations display name and name attribute are not matching");
				} else if (expectedKey.equalsIgnoreCase("tag_network_name")) {
					value = actualMap.get(expectedKey);
					network = network.toLowerCase().replace(" ", "_");
					softAssert.assertTrue(value.toString().equals(network), "Network is not matching of the device");
				}
			}
		}
		softAssert.assertAll();
	}

	public void setUpRequestForDevicesLatestDataApi(String api,String station){
		super.setUpRequest(api);
		String paramValue = station+"_btry,"+station+"_hydrophone,"+station+"_minhydrophone,"+station+"_pressure,"+station+"_magnitudepressure";
		helper.addQueryParamsToTheRequest("sensorId",paramValue);
	}

	public void verifyResponseForDevicesLatestDataApi() throws Exception {
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");
		// Validate Response object Map is not empty
		helper.verifyValueNotEmptyAtAJsonPathExpression("$");
		Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_COUNT_KEY, "int");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY, "String");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "int");
		//To verify at the path "$" from the above map
		helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath("$", expectedKeysDataTypesResponseMap);
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY, STATUS_CODE_202,
				"int");
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY,
				STATUS_TEXT_ACCEPTED, "String");
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

		JsonPath jPath = helper.getJsonPath();
		List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);

		SoftAssert softAssert = new SoftAssert();
		for (Map<String, Object> actualMap : actualList) {
			for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
				String expectedKey = entry.getKey();
				Object value ;
				if(expectedKey.equals("value")){
					value = actualMap.get(expectedKey);
					softAssert.assertTrue(Objects.nonNull(value),"The sensor does not have a value even though the value tag is there");
				}
				if(expectedKey.equals("sensorId")){
					value = actualMap.get(expectedKey);
					softAssert.assertTrue(value.toString().contains(actualMap.get("sensorName").toString()),
							"Sensor ID and sensor name "+ actualMap.get("sensorName") + "are not mapped correctly");
				}
			}
		}
		softAssert.assertAll();
	}
}
