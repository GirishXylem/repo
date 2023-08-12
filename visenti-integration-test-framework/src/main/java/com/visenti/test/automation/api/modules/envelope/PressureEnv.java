package com.visenti.test.automation.api.modules.envelope;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;

public class PressureEnv extends RestBaseModule {
    public PressureEnv(RestAssuredHelper helper) {
        super(helper);
    }

    /**
     * Verifies the common headers in the API response, including user, ticket, and customer headers,
     * as well as the "Content-Type" header and the status code.
     *
     * @throws Exception
     */
    public void verifyTheMandatoryRequestHeadersInPressureEnvConfigApi() throws Exception {
        //verify the request headers is not null
        Assert.assertNotNull(headersMap.get("x-service-customer"), "Missing 'x-service-customer' header");
        Assert.assertNotNull(headersMap.get("x-service-user"), "Missing 'x-service-user' header");
        Assert.assertNotNull(headersMap.get("x-service-ticket"), "Missing 'x-service-ticket' header");
        Log.info("Request headers verified successfully");
    }

    /**
     * Verifies common aspects of the Envelope API response.
     *
     * @throws Exception
     */
    public void verifyCommonResponseOfEnvelopeApi() throws Exception {
        // Verify the common headers in the API response
        verifyTheMandatoryRequestHeadersInPressureEnvConfigApi();

        //verify the response headers is not null
        helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_TEXT_HTML);
        Log.info("Response header 'Content-Type' verified successfully");

        // Verify the data type of the root object and non-empty value
        helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");
        helper.verifyValueNotEmptyAtAJsonPathExpression("$");

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

        // Define expected data types for specific keys in the root map
        Map<String, String> expectedKeysDataTypesInMap = new HashMap<>();
        expectedKeysDataTypesInMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
        expectedKeysDataTypesInMap.put(DATA_REQUEST_ID, "String");
        expectedKeysDataTypesInMap.put(SERVICE_TICKET_OR_TOKEN, "String");

        // Verify data types of values for the keys in the root map
        helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath("$", expectedKeysDataTypesInMap);

    }

    /**
     * Verifies the full response for the PressureEnvConfig API, including data types of values, list sizes, and common headers.
     * This method performs comprehensive validation of the API response, ensuring data types of values, list sizes, and common headers are correct.
     *
     * @throws Exception
     */
    public void verifyTheFullResponseForPressureEnvConfigApi() throws Exception {

        verifyCommonResponseOfEnvelopeApi();

        // Verify data types of values in specific lists within the root map
        String dataKey = COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]" + ".";

        helper.verifyTheDataTypesOfValuesInAList(dataKey + MAX_PRESSURE, "float");
        helper.verifyTheDataTypesOfValuesInAList(dataKey + MIN_PRESSURE, "float");
        helper.verifyTheDataTypesOfValuesInAList(dataKey + PIPECENTER, "float");
        helper.verifyTheDataTypesOfValuesInAList(dataKey + STATIONIDS, "String");
        helper.verifyTheDataTypesOfValuesInAList(dataKey + STATION_POSITION, "Integer");

        //verify the Two Json Array Sizes Are Equal
        helper.verifyTheTwoJsonArraySizesAreEqual(dataKey + MAX_PRESSURE, dataKey + MIN_PRESSURE);
        helper.verifyTheTwoJsonArraySizesAreEqual(dataKey + MAX_PRESSURE, dataKey + PIPECENTER);
        helper.verifyTheTwoJsonArraySizesAreEqual(dataKey + STATIONIDS, dataKey + STATION_POSITION);
    }

    /**
     * Sets start and endTime query parameters for an PressureEnvData Api request.
     * @param startTime
     */
    public void setQueryParamForPressureEnvDataApi(Long startTime) {

        //Calculate the end time by adding 5 minutes(with 1 sec less) in milliseconds to the start time
        Long endTime = startTime + ((1000 * 60 * 5) - 1000);

        //Create a LinkedHashMap to store query parameters
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        //Add the query parameters to the API request
        helper.addQueryParamsToTheRequest(map);
    }

    /**
     * This method will verify the full Json response of PressureEnvData Api
     *
     * @throws Exception
     */
    public void verifyTheFullResponseForPressureEnvDataApi() throws Exception {
        verifyCommonResponseOfEnvelopeApi();
        validateStationNameAndValuesInPressureEnvData();
    }

    /**
     * This method validates station names, values, and their schema in a JSON structure.
     */
    private void validateStationNameAndValuesInPressureEnvData() {
        //Get the 'count' value (station count) at given jsonpath
        int count = helper.getJsonPath().get("count");

        //Iterate till count and verifies station name is not null and values are exactly 150(data points)
        for (int i = 0; i < count; i++) {
            String stationJPath = "data[" + i + "].station_name";
            String valuesJpath = "data[" + i + "].values";
            Object stationName = helper.getJsonPath().get(stationJPath);
            //verifies station name is of String dataType
            assert stationName instanceof String : "station_name at " + stationJPath + " is not a string";
            //Get the number of datapoints
            int valuesSize = ((List) helper.getJsonPath().get(valuesJpath)).size();
            //verifies Station name is not empty
            Assert.assertNotNull(stationName, "station_name at " + stationJPath + " is null");
            //verifies values inside the station is exactly 150(data points)
            Assert.assertEquals(valuesSize, 150, "Datapoints in " + stationName + " is less than 150 ");

            //iterate and verify datatype of pressure and timestamp at each datapoint
            for (int j = 0; j < valuesSize; j++) {
                List valuesAtStation = (List) helper.getJsonPath().get(valuesJpath + "[" + j + "]");
                //Assert timestamp at particular datapoint is of Long datatype
                assert valuesAtStation.get(0) instanceof Long : "Timestamp at " + valuesAtStation + " is not Long";
                //Assert pressure at particular datapoint is of Float/Integer datatype
                if (!(valuesAtStation.get(1) instanceof Float) && !(valuesAtStation.get(1) instanceof Integer)) {
                    Assert.fail("Pressure at " + valuesAtStation + " is not Float/Integer datatype");
                }
            }
        }
        Log.info("Verified Station name in not null and it is of String datatype");
        Log.info("Verified each station has 150 data points and it is of either Float or Integer datatype");
        Reporter.log("Verified Station name in not null and it if of String datatype");
        Reporter.log("Verified Values for each station is 150 and it is of either Float or Integer datatype");
    }

}
