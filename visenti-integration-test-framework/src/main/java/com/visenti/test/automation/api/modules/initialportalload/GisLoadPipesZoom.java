package com.visenti.test.automation.api.modules.initialportalload;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.visenti.test.automation.constants.APIConstants.*;
import static com.visenti.test.automation.constants.APIConstants.GIS_REGION_NAME_KEY;

public class GisLoadPipesZoom extends RestBaseModule {

    public GisLoadPipesZoom(RestAssuredHelper helper) {
        super(helper);
    }

    public void setUpRequestForGISZoomAPI(String apiModule, String basePath) {
        super.setUpRequestWithBasePath(apiModule, basePath);
    }

    public void setUpQueryParamForZoomApi() {
        String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
        helper.addQueryParamsToTheRequest(GIS_CUSTOMER_ID_KEY, customerName);
    }

    public void verifyCommonResponseForGisZoomApi() throws Exception {
        // Assert the Content-Type header in Response is text/html
        helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_JSON);

        // Assert Response is an instance of Map
        helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");

        // Assert Response object is not empty
        helper.verifyValueNotEmptyAtAJsonPathExpression("$");

        // Assert Response Object contains the expected Keys and verify Data-Types of
        // value of each Key

        // Creating a Map containing the expected keys in Response Object Map and their
        // data types
        Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();

        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "Map");
        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_META_KEY, "Map");
        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "String");
        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY, "Int");


        /*
         * This method verifies that the Response Object($) map contains the expected
         * keys. The data Type of the value of each key is as expected ,Value of key not
         * null and Value of Key is not empty for the type 'List','String' or 'Map'
         */
        helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath("$", expectedKeysDataTypesResponseMap);

        // Assert the status key value to be 200
        helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY, STATUS_CODE_200,
                "int");

        // Assert the statusText key value to be 'Success'
        helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY,
                STATUS_SUCCESS, "String");

        Map<String, String> expectedKeysDataTypeMetaMap = new HashMap<>();

        expectedKeysDataTypeMetaMap.put(COMMON_META_ERROR_MESSAGE_KEY, "String");
        expectedKeysDataTypeMetaMap.put(COMMON_META_ERROR_TYPE_KEY, "String");

        // This method verifies that the meta map($.meta) contains the expected
        // keys
        // the data Type of the value of each key is as expected ,Value of key not null
        // and
        // Value of Key is not empty for the type 'String'

        helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath(COMMON_RESPONSE_OBJECT_META_KEY,
                expectedKeysDataTypeMetaMap);

        // Verify meta.error_message key value to be equal to 'none'

        helper.verifyValueAtAJsonPathExpressionForAValidationType(
                COMMON_RESPONSE_OBJECT_META_KEY + "." + COMMON_META_ERROR_MESSAGE_KEY, RESPONSE_META_ERROR_MESSAGE_NONE,
                "String");

        // Verify meta.error_type key value to be equal to 'none'

        helper.verifyValueAtAJsonPathExpressionForAValidationType(
                COMMON_RESPONSE_OBJECT_META_KEY + "." + COMMON_META_ERROR_TYPE_KEY, RESPONSE_META_ERROR_TYPE_NONE,
                "String");

    }

    public void verifySpecificResponseForGisZoomApi() throws Exception {
            Map<String, String> expectedKeysDataTypeDataMap = new HashMap<>();

            expectedKeysDataTypeDataMap.put(GIS_CUSTOMER_ID_KEY, "String");
            expectedKeysDataTypeDataMap.put(GIS_PREF_NAME_KEY, "String");
            expectedKeysDataTypeDataMap.put(GIS_REGION_NAME_KEY, "String");
            expectedKeysDataTypeDataMap.put(GIS_SERVICE_NAME_KEY, "String");
            expectedKeysDataTypeDataMap.put(GIS_ZOOM_KEY, "List");

            // This method verifies that the data map($.data) contains the expected
            // keys
            // the data Type of the value of each key is as expected ,Value of key not null
            // and
            // Value of Key is not empty for the type 'String'

            helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY,
                    expectedKeysDataTypeDataMap);

            // Asserting the value of the data.customerID key is same as yw in
            // QueryParam in lowerCase
            filterableRequestSpecification = helper.getFilterableRequestSpecification();
            String customerID = filterableRequestSpecification.getQueryParams().get(GIS_CUSTOMER_ID_KEY);

            helper.verifyValueAtAJsonPathExpressionForAValidationType(
                    COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_CUSTOMER_ID_KEY, customerID.toLowerCase(),
                    "string");

            String prefName = filterableRequestSpecification.getBasePath().replace("/", "");
            helper.verifyValueAtAJsonPathExpressionForAValidationType(
                    COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_PREF_NAME_KEY, prefName.toLowerCase(),
                    "string");

            //Commenting this validation since different customers have different value
            /*helper.verifyValueAtAJsonPathExpressionForAValidationType(
                    COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_REGION_NAME_KEY, "Asia/Singapore",
                    "string");*/

            helper.verifyValueAtAJsonPathExpressionForAValidationType(
                    COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_SERVICE_NAME_KEY, "gis",
                    "string");

            // Assert The value of data Key ($.data.zoom) is of Type List

            helper.verifyDataTypeOfValueAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_ZOOM_KEY, "List");

            // Assert the data zoom List is not Empty

            helper.verifyValueNotEmptyAtAJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY + "." + GIS_ZOOM_KEY);

            // Verify the data.zoom[0] Map contains the keys
            // range and diameter

            List<String> expectedKeysEachMapDataZoomList = new ArrayList<>();
            expectedKeysEachMapDataZoomList.add(GIS_ZOOM_DIAMETER_KEY);
            expectedKeysEachMapDataZoomList.add(GIS_ZOOM_RANGE_KEY);

            helper.verifyKeysInEachMapOfAListAtAGivenJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY + "." +
                    GIS_ZOOM_KEY ,expectedKeysEachMapDataZoomList);
        }

}
