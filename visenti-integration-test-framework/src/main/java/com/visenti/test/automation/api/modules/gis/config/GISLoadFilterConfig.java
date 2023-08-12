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

public class GISLoadFilterConfig extends RestBaseModule {
    public GISLoadFilterConfig(RestAssuredHelper helper) {
        super(helper);
    }

    public void setUpRequestForFilterConfig(String api,String paramValue) {
        paramValue = paramValue+"_gis";
        super.setUpRequestWithParam(api,GIS_CONFIG_PARAM_SEARCH_KEY,paramValue);
    }

    public void verifyDefaultResponseContentForFilterConfigApi() throws Exception {
        // Validate the Content-Type header is text/html
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

    public void verifySpecificResponseContentForFilterConfigApi(String filterType){
        SoftAssert softAssert = new SoftAssert();
        JsonPath jPath = helper.getJsonPath();
        List<Map<String, Object>> actualList = null;
        if(filterType.equalsIgnoreCase("pipe")){
            actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0].options");
        } else if (filterType.equalsIgnoreCase("valve")){
            actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"[2].options");
        }

        for (Map<String, Object> actualMap : actualList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
                String expectedKey = entry.getKey();
                if(expectedKey.equals("title")) {
                    String value = entry.getValue().toString();
                    System.out.println("Key: " + expectedKey + " - Value :" + value);
                    if(filterType.equalsIgnoreCase("pipe")){
                        softAssert.assertTrue(getGisPipeFilterList().contains(value),
                                "The "+value+" GIS " + filterType + " filter is not listed in the column list");
                    } else if (filterType.equalsIgnoreCase("valve")) {
                        softAssert.assertTrue(getGisValveFilterList().contains(value),
                                "The "+value+" GIS " + filterType + " filter is not listed in the column list");
                    }
                }
            }
        }
        softAssert.assertAll();
    }

    public List<String> getGisValveFilterList() {
        List<String> list = new ArrayList<>();
        list.add("None");
        list.add("Status");
        list.add("Criticality");

        return list;
    }

    public List<String> getGisPipeFilterList() {
        List<String> list = new ArrayList<>();
        list.add("None");
        list.add("Age");
        list.add("Material");
        list.add("Diameter");
        list.add("Criticality");
        list.add("Surge Risk");
        list.add("SmartBall");

        return list;
    }
}
