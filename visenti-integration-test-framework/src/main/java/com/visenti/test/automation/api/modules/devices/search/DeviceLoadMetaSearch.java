package com.visenti.test.automation.api.modules.devices.search;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import com.visenti.test.automation.utils.CommonUtils;
import io.restassured.path.json.JsonPath;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.visenti.test.automation.constants.APIConstants.*;

public class DeviceLoadMetaSearch extends RestBaseModule {

	public DeviceLoadMetaSearch(RestAssuredHelper helper) {
		super(helper);
	}


	public void setUpRequestForDeviceSearch(String searchType, String searchString,String searchField) throws IOException{
		String api = "gis";
		super.setUpRequest(api);
		addPayloadToTheDeviceIdRequest(searchType, searchString,searchField);
	}

	private void addPayloadToTheDeviceIdRequest(String searchType,  String searchString, String searchField) throws IOException {
		String filePath;
		String payload ;

		filePath = GIS_META_SEARCH_ID_PAYLOAD_PATH;
		payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);
		//Note: The common payload doesn't have a field tag so the payload is manipulated to have a field tag
		payload = payload
				.replace("search-type",
						searchType + "\"" +",\"field\""+":"+"\""+searchField.trim()+"\""+",")
				.replace("search-id", searchString);

		helper.addPayloadStringToTheRequest(payload);
	}


	public void verifyDefaultResponseContentForDeviceMetaSearchApi() throws Exception {
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


	public void verifySpecificContentsOfDeviceMetaSearch(String searchString, String field){
		JsonPath jPath = helper.getJsonPath();
		SoftAssert softAssert = new SoftAssert();
		Object value;
		List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"._source");

		for (Map<String, Object> metaDataMap : actualList) {
			for (Map.Entry<String, Object> entry : metaDataMap.entrySet()) {
				String metaDataKey = entry.getKey();
				if (metaDataKey.equalsIgnoreCase("display_name") && field.equals("display_name")) {
					value = metaDataMap.get(metaDataKey);
					softAssert.assertTrue(value.toString().equals(searchString),
							"The display name "+value+ "and the " + searchString + " are not same");
				} else if (metaDataKey.equalsIgnoreCase("_id")){
					value = metaDataMap.get(metaDataKey);
					softAssert.assertTrue(value.toString().equals(searchString),
							"The display name "+value+ "and the " + searchString + " are not same");
				} else if(metaDataKey.equalsIgnoreCase("dma_name")){
					value = metaDataMap.get(metaDataKey);
					softAssert.assertTrue(value.toString().equals(metaDataMap.get("supply_zone")),
							"The DMA name "+value+ "and the supply zone are not same");
				}
			}
		}
		softAssert.assertAll();
	}
}
