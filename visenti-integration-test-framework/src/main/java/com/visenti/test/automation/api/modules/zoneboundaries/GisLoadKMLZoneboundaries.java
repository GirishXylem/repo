package com.visenti.test.automation.api.modules.zoneboundaries;

import static com.visenti.test.automation.constants.APIConstants.COMMON_RESPONSE_OBJECT_DATA_KEY;
import static com.visenti.test.automation.constants.APIConstants.COMMON_RESPONSE_OBJECT_META_KEY;
import static com.visenti.test.automation.constants.APIConstants.COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY;
import static com.visenti.test.automation.constants.APIConstants.COMMON_RESPONSE_OBJECT_STATUS_KEY;
import static com.visenti.test.automation.constants.APIConstants.CONTENT_TYPE_JSON;
import static com.visenti.test.automation.constants.APIConstants.STATUS_CODE_200;
import static com.visenti.test.automation.constants.APIConstants.STATUS_SUCCESS;
//import static org.junit.Assert.assertArrayEquals;
import static org.testng.Assert.assertTrue;
import static com.visenti.test.automation.constants.APIConstants.GIS_KML_QUERY_PARAM_NAME;
import static com.visenti.test.automation.constants.APIConstants.GIS_KML_DATA_ID_KEY;
import static com.visenti.test.automation.constants.APIConstants.GIS_KML_DATA_ZONEID_KEY;
import static com.visenti.test.automation.constants.APIConstants.GIS_KML_DATA_FILENAME_KEY;
import static com.visenti.test.automation.constants.APIConstants.GIS_KML_DATA_GEOJSON_KEY;
import static com.visenti.test.automation.constants.APIConstants.GIS_KML_DATA_CUSTOMER_KEY;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.testng.asserts.SoftAssert;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.api.modules.initialportalload.GisLoadCustomerInfo;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import io.restassured.path.json.JsonPath;

public class GisLoadKMLZoneboundaries extends RestBaseModule {
	SoftAssert softAssert= new SoftAssert();
	public List<Object> expectedZoneIdList = new ArrayList<>();
	private Map<String, Object> expectedDmaMap = new HashMap<>();
	private Map<String, Object> actualDmaMap = new HashMap<>();
	private GisLoadCustomerInfo objCustomerInfo;
	private GisLoadKMLZoneboundaries objKMLZoneBoundaries;

	public GisLoadKMLZoneboundaries(RestAssuredHelper helper) {
		super(helper);
		objCustomerInfo = new GisLoadCustomerInfo(this.helper);
	}

	public void setUpRequestWithQueryParams(String api, String requestType, String endPoint) throws Exception {
		super.setUpRequest(api);
		// get all zoneId
		expectedDmaMap = objCustomerInfo.getDmaIDAndName();

		List<Object> expectedDMAId = new ArrayList<Object>();
		List<String> expectedDMAName = new ArrayList<String>();
		List<Object> expectedDMAIdsublist = new ArrayList<Object>();
		List<String> expectedDMANamesublist = new ArrayList<String>();

		for (Map.Entry<String, Object> entry : expectedDmaMap.entrySet()) {
			expectedDMAName.add(entry.getKey());
			expectedDMAId.add(entry.getValue());
		}

		// maximum we can send 30 query params to the request

		final int BATCH_MAX = 30;
		int totalBatches = expectedDMAId.size() / BATCH_MAX;
		int remainder = expectedDMAId.size() % BATCH_MAX;

		if (remainder > 0) {
			totalBatches += 1;
		}
		int k = 0;
		for (int i = 1; i <= totalBatches; i++) {
			if (k == expectedDMAId.size() - remainder) {
				expectedDMAIdsublist = expectedDMAId.subList(0 + k, expectedDMAId.size());
				expectedDMANamesublist = expectedDMAName.subList(0 + k, expectedDMAId.size());

			} else {
				expectedDMAIdsublist = expectedDMAId.subList(0 + k, BATCH_MAX + k);
				expectedDMANamesublist = expectedDMAName.subList(0 + k, BATCH_MAX + k);
				k = i * BATCH_MAX;
            }
			

			helper.getFilterableRequestSpecification().removeQueryParam(GIS_KML_DATA_ZONEID_KEY);
			// Add QueryParams to request

			helper.addQueryParamsToTheRequest(GIS_KML_QUERY_PARAM_NAME, expectedDMAIdsublist);
			Log.info("added query param zone id list size:"+expectedDMAIdsublist.size()+"at"+i+"batch");
			Reporter.addStepLog("added query param zone id list size:"+expectedDMAIdsublist.size()+"at"+i+"batch");
			helper.performRequestOnAnEndPoint(requestType, endPoint);

			// Validate the status code response

			helper.verifyStatusCodeAsExpected("200");

			// validate the common response
			objKMLZoneBoundaries = new GisLoadKMLZoneboundaries(helper);
			objKMLZoneBoundaries.verifyCommonResponseForGisKMLApi();

			// validate the specific response
			objKMLZoneBoundaries.verifySpecificResponseForGisKMLApi(expectedDMANamesublist);

		}
	}

	@SuppressWarnings("unchecked")
	public void verifyCommonResponseForGisKMLApi() throws Exception {
		// Validate the Content-Type header is text/html

		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_JSON);

		// Assert Response is an instance of Map

		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "Map");

		// Assert Response object is not empty

		helper.verifyValueNotEmptyAtAJsonPathExpression("$");

		// Assert Response Object contains the expected Keys and verify Data-Types of
		// value of each Key

		// Creating a Map containing the expected keys in Response Object Map and their
		// data types
		Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<String, String>();

		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY, "Int");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_META_KEY, "map");
		// expectedKeysDataTypesResponseMap.put(GIS_CUSTOMER_INFO_RESPONSE_META_KEY,
		// "List");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "String");
		// expectedKeysDataTypesResponseMap.put(GIS_CUSTOMER_INFO_RESPONSE_STATUS_KEY,
		// "List");
		// expectedKeysDataTypesResponseMap.put("Test", "String");

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

	}

	@SuppressWarnings("unchecked")
	public void verifySpecificResponseForGisKMLApi(List<String> expectedDMANameList)
			throws Exception {

		// Assert data list ($.data)contains the expected Keys and verify Data-Types of
		// value of each Key

		// Creating a Map containing the expected keys in data list and their
		// data types

		Map<String, String> expectedKeysDataTypesDataMap = new HashMap<String, String>();

		expectedKeysDataTypesDataMap.put(GIS_KML_DATA_ID_KEY, "String");
		expectedKeysDataTypesDataMap.put(GIS_KML_DATA_ZONEID_KEY, "String");
		expectedKeysDataTypesDataMap.put(GIS_KML_DATA_CUSTOMER_KEY, "String");
		expectedKeysDataTypesDataMap.put(GIS_KML_DATA_FILENAME_KEY, "String");
		expectedKeysDataTypesDataMap.put(GIS_KML_DATA_GEOJSON_KEY, "String");

		// In response zoneName is null and its not required field
		// expectedKeysDataTypesDataMap.put(GIS_KML_DATA_ZONENAME_KEY, "String");

		// This method verifies that the data ($.data) contains the expected
		// keys
		// the data Type of the value of each key is as expected ,Value of key not null
		// and
		// Value of Key is not empty for the type 'List','String' or 'Map'

		helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY,
				expectedKeysDataTypesDataMap);

		// validate zoneName in the response
		helper.getValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_KML_DATA_ID_KEY);
		JsonPath jPath = helper.getJsonPath();
		List<String> actualDMAFileNameList = new ArrayList<String>();
		List<Map<String, Object>> actualZoneList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);
		for (Map<String, Object> actualDataMap : actualZoneList) {
			actualDMAFileNameList.add(actualDataMap.get(GIS_KML_DATA_FILENAME_KEY).toString());
		}
		for(String actualDmaFileName:actualDMAFileNameList) {
        	boolean foundDmaName=false;
        	for(String expectedDmaName:expectedDMANameList) {
        		if(actualDmaFileName.contains(expectedDmaName)) {
        			foundDmaName=true;
        			break;
        			}
        	}
        	softAssert.assertTrue(foundDmaName,"actualDmaFileName"+actualDmaFileName+" does not contain the expected DMA name list"+expectedDMANameList);
    		Log.info("asserted actualDmaFileName"+actualDmaFileName+" contains in  the expected DMA name list"+expectedDMANameList);
			Reporter.addStepLog("asserted actualDmaFileName"+actualDmaFileName+" contains in  the expected DMA name list"+expectedDMANameList);
        }
		actualDMAFileNameList.clear();
	}

}
