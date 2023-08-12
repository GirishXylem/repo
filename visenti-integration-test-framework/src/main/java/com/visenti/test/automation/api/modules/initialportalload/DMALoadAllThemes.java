package com.visenti.test.automation.api.modules.initialportalload;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import io.restassured.path.json.JsonPath;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;

public class DMALoadAllThemes extends RestBaseModule {

    public DMALoadAllThemes(RestAssuredHelper helper) {
        super(helper);

    }

    public void setUpRequestForDMAAllThemes(String apiModule, String basepath) {
        super.setUpRequestWithBasePath(apiModule, basepath);
    }

    public void verifyCommonResponseForDmaAllThemes() throws Exception {

        // Assert the Content-Type header is text/html
        helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_TEXT_HTML);

        // Asserting the data List expected Size should be equal to the 'count' key
        // value

        int count = (int) helper.getValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_COUNT_KEY);

        @SuppressWarnings("unchecked") int dataListSize = ((List<Object>) helper.getValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY))
                .size();

        assertEquals(count, dataListSize,
                "The count key value' " + count + "' is not equal to the dataList size '" + dataListSize);
        Log.info("Asserted the '" + COMMON_RESPONSE_OBJECT_COUNT_KEY + "' value = " + count + " to be equal to the '"
                + COMMON_RESPONSE_OBJECT_DATA_KEY + "' List size");
        Reporter.addStepLog("Asserted the '" + COMMON_RESPONSE_OBJECT_COUNT_KEY + "' value= " + count
                + " to be equal to the '" + COMMON_RESPONSE_OBJECT_DATA_KEY + "' List size");


        // Assert Response is an instance of Map

        helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");

        // Assert Response object is not empty

        helper.verifyValueNotEmptyAtAJsonPathExpression("$");

        // Assert the Response Object contains the expected Keys

        List<String> expectedKeysResponseObject = new ArrayList<>();
        expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_COUNT_KEY);
        expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_DATA_KEY);
        expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_STATUS_KEY);
        expectedKeysResponseObject.add(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY);

        helper.verifyMapContainsKeysAtAGivenJsonPathExpression("$", expectedKeysResponseObject);

        // Assert the status key value to be 202
        helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY, STATUS_CODE_202,
                "int");

        // Assert the statusText key value to be 'Accepted'
        helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY,
                STATUS_TEXT_ACCEPTED, "String");

        // Assert the data List is not Empty
        helper.verifyValueNotEmptyAtAJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY);

        // Assert The value of data Key ($.data) is of Type List
        helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");

    }

    public void verifySpecificResponseForDmaAllThemesApi() throws Exception {
        Map<String, String> expectedMapKeysAndValueDataType = new HashMap<>();
        expectedMapKeysAndValueDataType.put(DMA_THEMES_ALL_DATA_NAME_KEY, "String");
        expectedMapKeysAndValueDataType.put(DMA_THEMES_ALL_DATA_CATEGORY_KEY, "Map");
        expectedMapKeysAndValueDataType.put(DMA_THEMES_ALL_DATA_COMPONENTS_KEY, "Map");

        Map<String, String> expectedMapKeysAndValueDataType1 = new HashMap<>();
        expectedMapKeysAndValueDataType1.put(DMA_THEMES_ALL_DATA_CUSTOMER_ID_KEY, "String");
        expectedMapKeysAndValueDataType1.put(DMA_THEMES_ALL_DATA_ID_KEY, "Int");
        expectedMapKeysAndValueDataType1.put(DMA_THEMES_ALL_DATA_KEY_KEY, "String");

        //verify the map keys and there value data types in each map of list at json path
        helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY, expectedMapKeysAndValueDataType1);

        //verify the map keys and there value is empty
        SoftAssert softAssert = new SoftAssert();
        JsonPath jPath = helper.getJsonPath();
        Object value;
        List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);
        for (Map<String, Object> actualMap : actualList) {
            for (Map.Entry<String, String> entry : expectedMapKeysAndValueDataType.entrySet()) {
                String expectedKey = entry.getKey();
                if (expectedKey.equalsIgnoreCase("name")) {
                    value = actualMap.get(expectedKey);
                    Log.info("Value of the name  Key " + expectedKey + "=" + value);
                    if (!getEmptyListComponentsAndCategoryFromDmaAllThemesAPI().contains(value.toString())) {
                        Object category = actualMap.get("category");
                        Object components = actualMap.get("components");
                        softAssert.assertFalse(category.toString().isEmpty() && components.toString().isEmpty(),
                                value + " is not having a empty category or components");

                    }
                }
            }
        }
        softAssert.assertAll();
    }

    public List<String> getEmptyListComponentsAndCategoryFromDmaAllThemesAPI() {
        List<String> list = new ArrayList<>();
        list.add("Zone");
        list.add("Customer Meters");
        list.add("Acoustic");
        list.add("Transient");
        list.add("AIMS");

        return list;
    }

    public void verifyTheKeyAndNameHavingSameValueForDmaAllThemesApi() {
        Map<String, String> expectedMapKeysAndValueDataType2 = new HashMap<>();
        expectedMapKeysAndValueDataType2.put(DMA_THEMES_ALL_DATA_KEY_KEY, "String");
        expectedMapKeysAndValueDataType2.put(DMA_THEMES_ALL_DATA_NAME_KEY, "String");

        SoftAssert softAssert = new SoftAssert();
        JsonPath jPath = helper.getJsonPath();
        String value;
        List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);
        for (Map<String, Object> actualMap : actualList) {
            for (Map.Entry<String, String> entry : expectedMapKeysAndValueDataType2.entrySet()) {
                String expectedKey = entry.getKey();
                if (expectedKey.equalsIgnoreCase("name")) {
                    value = actualMap.get(expectedKey).toString();
                    Log.info("Value of the key name " + expectedKey + "=" + value);
                    if (!getEmptyListComponentsAndCategoryFromDmaAllThemesAPI().contains(value)) {
                        value = value.toLowerCase().replace(" ","_");
                        String keyValue = actualMap.get("key").toString().toLowerCase();
                        softAssert.assertTrue(value.contains(keyValue),
                                "The name value-" +value + " is not same as key value-"+ keyValue);
                    }
                }
            }
        }
        softAssert.assertAll();
    }
}










