package com.visenti.test.automation.api.modules.othersources;


import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import io.restassured.path.json.JsonPath;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;

public class LoadContextualData extends RestBaseModule {

    SoftAssert softAssert = new SoftAssert();
    public LoadContextualData(RestAssuredHelper helper) {
        super(helper);
    }

    public void setUpRequestForContextualData(String... args) {
        setUpRequest(args[0]);
        int days = Integer.parseInt(args[2]);
        Long fromDateInMilli = LocalDateTime.now().minusDays(days).atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();


        Long toDateInMilli = LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();

        helper.addQueryParamsToTheRequest("st",fromDateInMilli.toString());
        helper.addQueryParamsToTheRequest("et",toDateInMilli.toString());
        helper.addQueryParamsToTheRequest("type",args[1]);
    }

    public void verifyCommonResponseForContextualDataApi() throws Exception {
        // Assert the Content-Type header in Response is text/html
        helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_TEXT_HTML);
        // Assert Response is an instance of Map
        helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");
        // Assert Response object is not empty
        helper.verifyValueNotEmptyAtAJsonPathExpression("$");

        Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();

        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_COUNT_KEY, "int");
        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "int");
        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY, "String");

        //To verify at the path "$" from the above map
        helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath("$", expectedKeysDataTypesResponseMap);

        // Verify status_code key value to be equal to 200
        helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY,
                STATUS_CODE_202, "Int");
        // Verify the value of 'status' key equal to 'success'
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

    public void verifySpecificResponseForContextualApi(String contextualDataType) throws Exception{
        // Data-Types value of each Key
        Map<String, String> expectedKeysDataTypesDataMap = new HashMap<>();
        expectedKeysDataTypesDataMap.put(CONTEXTUAL_DATA_COLUMN_KEY, "list");
        expectedKeysDataTypesDataMap.put(CONTEXTUAL_DATA_RECORD_KEY, "list");

        helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY,
                expectedKeysDataTypesDataMap);
        verifyContextualDataColumns(contextualDataType);
        verifyContextualDataRecords(contextualDataType);
    }

    public void verifyContextualDataColumns(String contextualDataType){
        JsonPath jPath = helper.getJsonPath();
        int count =0;
        switch (contextualDataType.toLowerCase()) {
            case "customer_complaints":
                List<Map<String, Object>> customerCallsList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]."+CONTEXTUAL_DATA_COLUMN_KEY);
                for (Map<String, Object> customerCallsActualMap : customerCallsList) {
                    for (Map.Entry<String, Object> entry : customerCallsActualMap.entrySet()) {
                        String expectedKey = entry.getKey();
                        if(entry.getKey().equalsIgnoreCase("label")){
                            Object value = customerCallsActualMap.get(expectedKey);
                            Log.info("Value of the columns Key " + expectedKey + " for "+contextualDataType+" =" + value);
                            Log.info("Counter: " + count++);
                            softAssert.assertTrue(getCustomerCallsColumns().contains(value),
                                    "The "+contextualDataType+ " is not having the required column "+value);
                        }
                    }
                }
                break;

            case "leak_risk":
                List<Map<String, Object>> leakRiskList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]."+CONTEXTUAL_DATA_COLUMN_KEY);
                for (Map<String, Object> leakRiskActualMap : leakRiskList) {
                    for (Map.Entry<String, Object> entry : leakRiskActualMap.entrySet()) {
                        String expectedKey = entry.getKey();
                        if(entry.getKey().equalsIgnoreCase("label")){
                            Object value = leakRiskActualMap.get(expectedKey);
                            Log.info("Value of the columns Key " + expectedKey + "=" + value);
                            softAssert.assertTrue(getLeakRiskColumns().contains(value.toString()),
                                    "The "+contextualDataType+ " is not having the required column "+value);
                        }
                    }
                }
                break;

            case "work_orders":
                List<Map<String, Object>> workOrdersList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]."+CONTEXTUAL_DATA_COLUMN_KEY);
                for (Map<String, Object> workOrdersActualMap : workOrdersList) {
                    for (Map.Entry<String, Object> entry : workOrdersActualMap.entrySet()) {
                        String expectedKey = entry.getKey();
                        if(entry.getKey().equalsIgnoreCase("label")){
                            Object value = workOrdersActualMap.get(expectedKey);
                            Log.info("Value of the columns Key " + expectedKey + "=" + value);
                            softAssert.assertTrue(getWorkOrdersColumns().contains(value.toString()),
                                    "The "+contextualDataType+ " is not having the required column "+value);
                        }
                    }
                }
                break;

            default:
                break;
        }
        softAssert.assertAll();
    }

    public List<String> getCustomerCallsColumns() {
        List<String> list = new ArrayList<>();
        list.add("Need");
        list.add("ContactNumber");
        list.add("ContactTime");
        list.add("NeedType");

        return list;
    }

    public List<String> getLeakRiskColumns() {
        List<String> list = new ArrayList<>();
        list.add("Reported");
        list.add("Pipe Id");
        list.add("zone");
        list.add("pipeAge");
        list.add("pipeDiameter");
        list.add("pipeMaterial");

        return list;
    }

    public List<String> getWorkOrdersColumns() {
        List<String> list = new ArrayList<>();
        list.add("tramsAssetRefNo");
        list.add("planStartDateTime");
        list.add("orderNo");
        list.add("orderDesc");
        list.add("aibAssetRefNo");
        list.add("planEndDateTime");
        list.add("orderStatus");
        list.add("orderType");
        list.add("creationDateOrder");
        list.add("orderStatusChangeDate");
        list.add("actEndDateTime");
        list.add("actStartDateTime");
        list.add("creationDateNotification");

        return list;
    }

    public void verifyContextualDataRecords(String contextualDataType) throws Exception {
        // Data-Types value of each Key
        Map<String, String> expectedKeysDataTypesDataMap = new HashMap<>();
        if(contextualDataType.equalsIgnoreCase("customer_complaints")){
            expectedKeysDataTypesDataMap.put("contact_id", "String");
            expectedKeysDataTypesDataMap.put("contact_time", "long");
            expectedKeysDataTypesDataMap.put("need", "String");
            expectedKeysDataTypesDataMap.put("need_type", "String");
            expectedKeysDataTypesDataMap.put("lat", "float");
            expectedKeysDataTypesDataMap.put("lng", "float");
        } else if (contextualDataType.equalsIgnoreCase("work_orders")){
            expectedKeysDataTypesDataMap.put("aibassetrefno", "String");
            expectedKeysDataTypesDataMap.put("creationdatenotification", "long");
            expectedKeysDataTypesDataMap.put("creationdateorder", "long");
            expectedKeysDataTypesDataMap.put("lat", "float");
            expectedKeysDataTypesDataMap.put("lng", "float");
            expectedKeysDataTypesDataMap.put("orderdesc", "String");
            expectedKeysDataTypesDataMap.put("orderno", "String");
            expectedKeysDataTypesDataMap.put("orderstatus", "String");
            expectedKeysDataTypesDataMap.put("ordertype", "String");
            expectedKeysDataTypesDataMap.put("orderstatuschangedate", "long");
            expectedKeysDataTypesDataMap.put("planenddatetime", "long");
            expectedKeysDataTypesDataMap.put("planstartdatetime", "long");
        } else if (contextualDataType.equalsIgnoreCase("leak_risks")){
            expectedKeysDataTypesDataMap.put("pipeid", "String");
            expectedKeysDataTypesDataMap.put("reported_time", "long");
            expectedKeysDataTypesDataMap.put("pipediameter", "int");
            expectedKeysDataTypesDataMap.put("zone", "String");
            expectedKeysDataTypesDataMap.put("lat", "float");
            expectedKeysDataTypesDataMap.put("lng", "float");
        }

        helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]."+CONTEXTUAL_DATA_RECORD_KEY,
                expectedKeysDataTypesDataMap);
    }
}
