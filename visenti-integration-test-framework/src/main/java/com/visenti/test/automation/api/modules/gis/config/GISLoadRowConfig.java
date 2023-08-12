package com.visenti.test.automation.api.modules.gis.config;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;
import io.restassured.path.json.JsonPath;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;

public class GISLoadRowConfig extends RestBaseModule {

	public GISLoadRowConfig(RestAssuredHelper helper) {
		super(helper);
	}

	public void setUpRequestForRowConfig(String api) {
		super.setUpRequest(api);
	}

	public void verifyDefaultResponseContentForGISRowConfigApi() throws Exception {
		// Validate the Content-Type header is application/json
		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_TEXT_HTML);
		// Assert Response is an instance of Map
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");
		// Assert Response object is not empty
		helper.verifyValueNotEmptyAtAJsonPathExpression("$");

		// Assert Response Object contains the expected Keys and verify Data-Types of
		// value of each Key
		Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();

		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY, "String");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "int");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_COUNT_KEY, "int");

		// This method verifies that the Response Object($) map contains the expected keys
		// the data Type of the value of each key is as expected ,Value of key not null and
		// Value of Key is not empty for the type 'List','String' or 'Map'
		helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath("$", expectedKeysDataTypesResponseMap);

		// Verify response status and code
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY,
				STATUS_CODE_202, "Int");
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY, STATUS_TEXT_ACCEPTED,
				"String");
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

	public void verifyAllGisDetailsIsAvailable(){
		SoftAssert softAssert = new SoftAssert();
		JsonPath jPath = helper.getJsonPath();
		List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);
		for (Map<String, Object> actualMap : actualList) {
			for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
				String expectedKey = entry.getKey();
				String value = entry.getValue().toString();
				String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
				if(!customerName.equalsIgnoreCase("pub")){
					if(expectedKey.equals("name") && !value.contains("Direct/Indirect")){
						softAssert.assertTrue(getGisRowList().contains(value),
								"The "+value+" GIS is not listed in the GIS list");
					}
				} else {
					softAssert.assertTrue(getGisRowList().contains(value),
							"The "+value+" GIS is not listed in the GIS list");
				}
			}
		}
		softAssert.assertAll();
	}

	public List<String> getGisRowList() {
		List<String> list = new ArrayList<>();
			list.add("BulkMeter");
			list.add("Hydrant");
			list.add("Junction");
			list.add("Pipe");
			list.add("Valve");

		return list;
	}

}
