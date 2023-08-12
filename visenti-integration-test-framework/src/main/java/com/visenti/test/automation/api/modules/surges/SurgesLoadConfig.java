package com.visenti.test.automation.api.modules.surges;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;
import com.visenti.test.automation.utils.CommonUtils;
import io.restassured.path.json.JsonPath;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.visenti.test.automation.constants.APIConstants.*;

public class SurgesLoadConfig extends RestBaseModule {
    public SurgesLoadConfig(RestAssuredHelper helper) {
        super(helper);
    }

    public void setUpRequestWithParamForAPI(String ...api){
        setUpRequest(api[0]);
        String key = "id";
        helper.addQueryParamsToTheRequest(key,api[1]);
    }

    public void setUpRequestForRiskByZone(String api) throws IOException {
        setUpRequest(api);
        addPayloadToTheRequest();
    }

    public void setUpRequestForMetrics(String api) throws IOException {
        setUpRequest(api);
        addPayloadToTheMetricsRequest();
    }

    public void setUpRequestForDamagingMetrics(String api) throws IOException {
        setUpRequest(api);
        addPayloadToTheTransientRequest();
    }

    public void setUpRequestToGetTransients(String api) throws IOException {
        setUpRequest(api);
        addPayloadToTheTransientRequest();
    }
    private void addPayloadToTheTransientRequest() throws IOException {
        String filePath;
        String payload;
        String customer = RuntimeConfigSingleton.getInstance().getCustomerName();
        filePath = DAMAGING_TRANSIENTS_PAYLOAD_PATH;
        Long fromDateInMilli, toDateInMilli;
        payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);
        fromDateInMilli = LocalDateTime.now().minusDays(7).atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();

        toDateInMilli = LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
        payload = payload
                .replace("zone-list", Objects.requireNonNull(ConfigFileReader.getConfigProperty(customer + ".zones.all")))
                .replace("startDate",fromDateInMilli.toString())
                .replace("endDate",toDateInMilli.toString());

        helper.addPayloadStringToTheRequest(payload);
    }

    private void addPayloadToTheMetricsRequest() throws IOException {
        String filePath;
        String payload;
        String customer = RuntimeConfigSingleton.getInstance().getCustomerName();
        filePath = SURGES_METRICS_PAYLOAD_PATH;
        Long fromDateInMilli, toDateInMilli;
        payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);
        fromDateInMilli = LocalDateTime.now().minusDays(7).atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();

        toDateInMilli = LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
        payload = payload
                .replace("zone-list", Objects.requireNonNull(ConfigFileReader.getConfigProperty(customer + ".zones.all")))
                .replace("startDate",fromDateInMilli.toString())
                .replace("endDate",toDateInMilli.toString());

        helper.addPayloadStringToTheRequest(payload);
    }
    private void addPayloadToTheRequest() throws IOException {
        String filePath;
        String customer = RuntimeConfigSingleton.getInstance().getCustomerName();
        String payload;
        filePath = SURGES_GET_RISK_BY_ZONE_PAYLOAD_PATH;
        Long fromDateInMilli, toDateInMilli;
        payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);
        fromDateInMilli = LocalDateTime.now().minusDays(7).atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();

        toDateInMilli = LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
        payload = payload
                .replace("zone-list", Objects.requireNonNull(ConfigFileReader.getConfigProperty(customer + ".zones.all")))
                .replace("startDate",fromDateInMilli.toString())
                .replace("endDate",toDateInMilli.toString());

        helper.addPayloadStringToTheRequest(payload);
    }

    public void verifyCommonResponseForSurgesApi() throws Exception {
        helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_JSON);
        helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");
        helper.verifyValueNotEmptyAtAJsonPathExpression("$");

        Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();
        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "Map");
        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "String");
        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY, "Int");
        helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath("$", expectedKeysDataTypesResponseMap);

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

    public void verifyResponseDetailsForSurgesConfigAPI() throws Exception {
        verifyCommonResponseForSurgesApi();
        SoftAssert softAssert = new SoftAssert();
        Object value;
        JsonPath jPath = helper.getJsonPath();
        Map<String, Object> actualMap = jPath.getMap(COMMON_RESPONSE_OBJECT_DATA_KEY);
        for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
            String expectedKey = entry.getKey();
            if (expectedKey.equals("bubbleColorPalette")) {
                value = actualMap.get(expectedKey);
                softAssert.assertTrue(Objects.nonNull(value),
                        "The bubbleColorPalette does not have color codes");
            }
            if(expectedKey.equals("limit")){
                value = actualMap.get(expectedKey);
                softAssert.assertTrue(Objects.nonNull(value),
                        "The limit field does not have any values");
            }
            if(expectedKey.equals("magnitudeAbove")){
                value = actualMap.get(expectedKey);
                softAssert.assertTrue(Objects.nonNull(value),
                        "The magnitudeAbove field does not have any values");
            }
        }
        List<String> riskList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+".risk");
        for (String risk : riskList) {
            softAssert.assertTrue(getRiskValueList().contains(risk.toLowerCase()),
                    "The risk type "+ risk +" is not from the list");
        }
        List<String> pipesActionList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+".pipes.actions");
        for (String action : pipesActionList) {
            softAssert.assertTrue(getActionsList().contains(action.toLowerCase()),
                    "The pipe action "+ action +" is not from the list");
        }
        List<String> sourcesActionList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+".sources.actions");
        for (String action : sourcesActionList) {
            softAssert.assertTrue(getActionsList().contains(action.toLowerCase()),
                    "The sources action "+ action +" is not from the list");
        }
        List<String> consumptionAvailableList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+".sources.consumption_available");
        for (String consumption : consumptionAvailableList) {
            softAssert.assertTrue(getConsumptionList().contains(consumption.toLowerCase()),
                    "The consumption available value "+ consumption +" is not from the list");
        }
        softAssert.assertAll();
    }
    public void verifyResponseDetailsForNetworkConfigAPI(String id) throws Exception {
        verifyCommonResponseForSurgesApi();
        SoftAssert softAssert = new SoftAssert();
        Object value;
        String customer = RuntimeConfigSingleton.getInstance().getCustomerName();
        JsonPath jPath = helper.getJsonPath();
        Map<String, Object> actualMap = jPath.getMap(COMMON_RESPONSE_OBJECT_DATA_KEY);
        for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
            String expectedKey = entry.getKey();
            if (expectedKey.equals("customerId")) {
                value = actualMap.get(expectedKey);
                softAssert.assertTrue(customer.equalsIgnoreCase(value.toString()),
                        "The customer id "+value+" does not match with the customer passed");
            }
            if (expectedKey.equals("id")) {
                value = actualMap.get(expectedKey);
                softAssert.assertTrue(id.equals(String.valueOf(value)),
                        "The id "+value+" does not match with the request id");
            }
            if (expectedKey.equals("created")) {
                value = actualMap.get(expectedKey);
                softAssert.assertTrue(Objects.nonNull(value),
                        "The created date does not have a value");
            }
            if (expectedKey.equals("lastModified")) {
                value = actualMap.get(expectedKey);
                softAssert.assertTrue(Objects.nonNull(value),
                        "The lastModified date does not have a value");
            }
            if (expectedKey.equals("configCriteria")) {
                value = actualMap.get(expectedKey);
                softAssert.assertTrue(Objects.nonNull(value),
                        "The config criteria does not have data");
            }
        }
        softAssert.assertAll();
    }

    public void verifyResponseDetailsForGetRiskZonesAPI() throws Exception {
        verifyCommonResponseForSurgesApi();
        helper.verifyValueNotEmptyAtAJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+".length");
        helper.verifyValueNotEmptyAtAJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+".risk");
        SoftAssert softAssert = new SoftAssert();
        JsonPath jPath = helper.getJsonPath();
        List<Map<String, Object>> lengthList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+".length");
        for (Map<String, Object> actualMap : lengthList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
                String expectedKey = entry.getKey();
                softAssert.assertTrue(lengthKeyList().contains(expectedKey),
                        "Key "+expectedKey +"is not available in the list");
            }
        }
        List<Map<String, Object>> riskList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+".risk");
        for (Map<String, Object> actualMap : riskList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
                String expectedKey = entry.getKey();
                softAssert.assertTrue(riskKeyList().contains(expectedKey),
                        "Key "+expectedKey +"is not available in the list");
            }
        }
        softAssert.assertAll();
    }

    public void verifyResponseDetailsForSurgesMetricsAPI() throws Exception {
        verifyCommonResponseForSurgesApi();
        helper.verifyValueNotEmptyAtAJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+".pipes");
        helper.verifyValueNotEmptyAtAJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+".sensors");
        helper.verifyValueNotEmptyAtAJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+".sources");
        SoftAssert softAssert = new SoftAssert();
        JsonPath jPath = helper.getJsonPath();
        List<Map<String, Object>> pipesList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+".pipes");
        for (Map<String, Object> actualMap : pipesList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
                String expectedKey = entry.getKey();
                softAssert.assertTrue(pipesKeyList().contains(expectedKey),
                        "Key "+expectedKey +"is not available in the pipes key list");
            }
        }
        List<Map<String, Object>> sensorsList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+".sensors");
        for (Map<String, Object> actualMap : sensorsList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
                String expectedKey = entry.getKey();
                softAssert.assertTrue(sensorsKeyList().contains(expectedKey),
                        "Key "+expectedKey +"is not available in the sensors key list");
            }
        }
        List<Map<String, Object>> sourcesList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+".sources");
        for (Map<String, Object> actualMap : sourcesList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
                String expectedKey = entry.getKey();
                softAssert.assertTrue(sourcesKeyList().contains(expectedKey),
                        "Key "+expectedKey +"is not available in the sensors key list");
            }
        }
        softAssert.assertAll();
    }

    public void verifyResponseDetailsForDamagingTransientsAPI() throws Exception {
        verifyCommonResponseForSurgesApi();
    }

    public void verifyResponseDetailsForGettingTransientsAPI() throws Exception {
        verifyCommonResponseForSurgesApi();
        SoftAssert softAssert = new SoftAssert();
        JsonPath jPath = helper.getJsonPath();
        List<Map<String, Object>> columnsList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+".columns");
        for (Map<String, Object> actualMap : columnsList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
                String expectedKey = entry.getKey();
                if(expectedKey.equals("title")){
                    Object value = actualMap.get(expectedKey);
                    softAssert.assertTrue(transientColumnList().contains(value.toString()),
                            "Column "+value +"is not from the specified column list");
                }
            }
        }
        List<Map<String, Object>> recordsList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+".records");
        for (Map<String, Object> actualMap : recordsList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
                String expectedKey = entry.getKey();
                softAssert.assertTrue(recordsMandatoryKeyList().contains(expectedKey),
                        "Key "+expectedKey +" is not available for "+ actualMap.get(expectedKey)+" in the records column list");
            }
        }
        softAssert.assertAll();
    }
    public List<String> getRiskValueList(){
        List<String> list = new ArrayList<>();
        list.add("high");
        list.add("medium");
        list.add("low");

        return list;
    }

    public List<String> lengthKeyList(){
        List<String> list = new ArrayList<>();
        list.add("actual_length");
        list.add("avg_age");
        list.add("length_blue");
        list.add("length_green");
        list.add("length_red");
        list.add("material");
        list.add("over_all_red");

        return list;
    }

    public List<String> riskKeyList(){
        List<String> list = new ArrayList<>();
        list.add("high");
        list.add("low");
        list.add("material");
        list.add("med");

        return list;
    }

    public List<String> getActionsList(){
        List<String> list = new ArrayList<>();
        list.add("ignore");
        list.add("mitigate");
        list.add("repair");
        list.add("replace");

        return list;
    }

    public List<String> pipesKeyList(){
        List<String> list = new ArrayList<>();
        list.add("age");
        list.add("actionAndMitigation");
        list.add("criticality");
        list.add("diameter");
        list.add("length");
        list.add("locations");
        list.add("material");
        list.add("pipeid");
        list.add("risk");
        list.add("risk_type");
        list.add("zone");

        return list;
    }

    public List<String> sensorsKeyList(){
        List<String> list = new ArrayList<>();
        list.add("color_code");
        list.add("frequency");
        list.add("id");
        list.add("level");
        list.add("location");
        list.add("magnitude");
        list.add("risk");
        list.add("station_name");
        list.add("zone");

        return list;
    }

    public List<String> sourcesKeyList(){
        List<String> list = new ArrayList<>();
        list.add("actionAndMitigation");
        list.add("consumption_available");
        list.add("id");
        list.add("location");
        list.add("magnitude");
        list.add("name");
        list.add("risk");
        list.add("riskValue");
        list.add("source");
        list.add("supply_zone");

        return list;
    }

    public List<String> transientColumnList() {
        List<String> list = new ArrayList<>();
        list.add("Stations");
        list.add("Station Name");
        list.add("5 - 10");
        list.add("10 - 20");
        list.add("20 - 50");
        list.add("50 - 100");
        list.add("> 100");
        return list;
    }

    public List<String> recordsMandatoryKeyList(){
        List<String> list = new ArrayList<>();
        list.add("location");
        list.add("stationId");
        list.add("station_name");
        list.add("5_10");
        list.add("10_20");
        list.add("20_50");
        list.add("50_100");
        list.add("gt100");

        return list;
    }

    public List<String> getConsumptionList(){
        List<String> list = new ArrayList<>();
        list.add("yes");
        list.add("no");

        return list;
    }
}
