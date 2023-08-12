package com.visenti.test.automation.api.modules.healthmonitor;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.*;
import com.visenti.test.automation.utils.CommonUtils;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.Range;
import org.bson.Document;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.*;

public class DataLoadHealthAPI extends RestBaseModule {


    public static List<Map> dataStatusFailedList= new ArrayList<>();
    SoftAssert softAssert = new SoftAssert();
    public DataLoadHealthAPI(RestAssuredHelper helper) {
        super(helper);
    }

    public void setUpRequestForStatusApi(String api) {
        super.setUpRequest(api);
    }

    public void setUpRequestForChartsApiWithBasePath(String api, String basePath) {
        super.setUpRequestWithBasePath(api, basePath);
    }
    public void setUpRequestForInstrumentApi(String api) {
        super.setUpRequestWithParam(api,DATA_STATION_SENSOR_ONLINE_OFFLINE_KEY,
                DATA_STATION_SENSOR_ONLINE_OFFLINE_VALUE);
    }

    public void setUpQueryParamToHMAPIs(String vendor){
        Map<String,Object> map = new HashMap<>();
        map.put(DATA_PROJECT_KEY, vendor);
        map.put(DATA_TYPE_KEY,DATA_TYPE_VALUE);
        helper.addQueryParamsToTheRequest(map);
    }

    public void setUpQueryParamToHMChartsAPIs(String vendor){
        long startTime,endTime;
        startTime = LocalDateTime.now().minusDays(7).atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();

        endTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();

        Map<String,Object> map = new HashMap<>();
        map.put(DATA_TYPE_KEY,DATA_TYPE_VALUE);
        map.put(DATA_PROJECT_KEY, vendor);
        map.put("st",startTime);
        map.put("et",endTime);
        map.put("frequency","daily");
        helper.addQueryParamsToTheRequest(map);
    }

    public void setUpQueryParamToHMInstrumentStationAPI(String vendor){
        Map<String,Object> map = new HashMap<>();
        map.put(DATA_STATION_SENSOR_ONLINE_OFFLINE_KEY,DATA_STATION_SENSOR_ONLINE_OFFLINE_VALUE);
        map.put(DATA_PROJECT_KEY, vendor);
        map.put(DATA_ASSET_TYPE_KEY,"offline");
        map.put("ishealth","false");
        helper.addQueryParamsToTheRequest(map);
    }

    public void setUpQueryParamToHMInstrumentSensorsAPI(String vendor){
        Map<String,Object> map = new HashMap<>();
        map.put(DATA_STATION_SENSOR_ONLINE_OFFLINE_KEY,DATA_STATION_SENSOR_ONLINE_OFFLINE_VALUE);
        map.put(DATA_PROJECT_KEY, vendor);
        map.put(DATA_ASSET_TYPE_KEY,"sensor");
        helper.addQueryParamsToTheRequest(map);
    }

    public void setUpRequestForUserPreferences(String api) {
        String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
        String userId = FileReaderManager.getInstance().getConfigReader().getAPIHeaderValues("user");
        super.setUpRequest(api);
        helper.addQueryParamsToTheRequest("userId",userId);
        helper.addQueryParamsToTheRequest("customerId",customerName);
    }

    public void setUpRequestForUpdateUserPreferences(String api, String columns) throws IOException {
        super.setUpRequest(api);
        addPayloadToTheRequest(columns);
    }

    public void setUpRequestForGettingDetections(String api) {
        super.setUpRequest(api);
    }

    private void addPayloadToTheRequest(String columns) throws IOException {
        String filePath;
        String payload = null;
        String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
        String userId = FileReaderManager.getInstance().getConfigReader().getAPIHeaderValues("user");
        filePath = HM_USER_PREFERENCE_PAYLOAD_PATH;
        payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);
        payload = payload
                .replace("uID",userId)
                .replace("cId",customerName);
        if(columns.equalsIgnoreCase("all")){
            payload = payload
                    .replace("column-list",HM_MAINTENANCE_ACTIVITY_COLUMNS_LIST);
        } else {
            payload = payload
                    .replace("column-list","\""+columns+"\"");
        }
        helper.addPayloadStringToTheRequest(payload);
    }

    public void verifyCommonResponseForHealthMonitorApi() throws Exception {
        // Assert the Content-Type header in Response is text/html
        helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_TEXT_HTML);

        // Assert Response is an instance of Map
        helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");

        // Assert Response object is not empty
        helper.verifyValueNotEmptyAtAJsonPathExpression("$");

        // Assert Response Object contains the expected Keys and verify Data-Types of value of each Key

        // Creating a Map containing the expected keys in Response Object Map and their
        // data types
        Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();

        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_COUNT_KEY, "int");
        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "Int");
        expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY, "String");

        /* This method verifies that the Response Object($) map contains the expected keys.
           The data Type of the value of each key is as expected ,Value of key not null and
           Value of Key is not empty for the type 'List','String' or 'Map'*/
        helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath("$", expectedKeysDataTypesResponseMap);

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
        Reporter.addStepLog("Asserted the '" + COMMON_RESPONSE_OBJECT_COUNT_KEY + "' value= " + count
                + " to be equal to the '" + COMMON_RESPONSE_OBJECT_DATA_KEY + "' List size");
    }

    public void verifyFullResponseForInstrumentHealthApi() throws Exception {

        verifyCommonResponseForHealthMonitorApi();
        // Assert each data Map in the List($.data)contains the expected Keys and verify
        // Data-Types of value of each Key

        // Creating a Map containing the expected keys in each Map and their data types
        Map<String, String> expectedKeysDataTypesDataMap = new HashMap<>();

        expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_CUSTOMER_ID_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_STATION_SENSOR_COUNT,"Integer");
        expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_LATITUDE_KEY, "Float");
        expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_LONGITUDE_KEY, "Float");
        expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_MAXIMUM_LATENCY_KEY, "Integer");
        expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_NAME_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_SOURCE_ID_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_STATION_ID_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_STATION_SENSOR_LIVE_STATUS_ID_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_STATUS_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_TYPE_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_LIVE_STATUS_ZONE_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_LAST_MODIFIED, "Long");


        // This method verifies each Map in the List is not empty,the presence of keys
        // in each Map in a List at the JsonPath ,
        // the dataTypes of value of keys as expected ,Value of key not null and Value
        // of Key is not empty for the type 'List','String' or 'Map'
        helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY,
                expectedKeysDataTypesDataMap);
    }

    public void verifyOnlineStations() {
        // Getting the List of Maps at the jsonPath
        JsonPath jPath = helper.getJsonPath();
        List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);

        for (Map<String, Object> actualMap : actualList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {

                String expectedKey = entry.getKey();
                if(expectedKey.equals("inactive_out_of_total_sensor")){
                    Object value = actualMap.get(expectedKey);
                    Log.info("Value of the Data column Key " + expectedKey + "=" + value);
                    String [] sensorCount = value.toString().split("/");
                    if(!sensorCount[1].equals(sensorCount[0])){
                        String status = actualMap.get(DATA_LIVE_STATUS_STATUS_KEY).toString();
                        softAssert.assertTrue(status.equalsIgnoreCase("Online"),
                                "The Station "+ actualMap.get(DATA_LIVE_STATUS_NAME_KEY)
                                        +" should be online but found "+ actualMap.get(DATA_LIVE_STATUS_STATUS_KEY));
                    }
                }
            }
        }
        softAssert.assertAll();
    }

    public void verifyOfflineStations() {
        // Getting the List of Maps at the jsonPath
        JsonPath jPath = helper.getJsonPath();
        List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);

        for (Map<String, Object> actualMap : actualList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {

                String expectedKey = entry.getKey();
                if(expectedKey.equals("inactive_out_of_total_sensor")){
                    Object value = actualMap.get(expectedKey);
                    Log.info("Value of the Data column Key " + expectedKey + "=" + value);
                    String [] sensorCount = value.toString().split("/");
                    if(sensorCount[1].equals(sensorCount[0])){
                        String status = actualMap.get(DATA_LIVE_STATUS_STATUS_KEY).toString();
                        softAssert.assertTrue(status.equalsIgnoreCase("Offline"),
                                "The Station "+ actualMap.get(DATA_LIVE_STATUS_NAME_KEY)
                                        +" should be offline but found "+ actualMap.get(DATA_LIVE_STATUS_STATUS_KEY));
                    }
                }
            }
        }
        softAssert.assertAll();
    }

    public void findDifferenceInHours(String hours) {
        // Getting the List of Maps at the jsonPath
        JsonPath jPath = helper.getJsonPath();
        List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);

        for (Map<String, Object> actualMap : actualList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
                String expectedKey = entry.getKey();
                if(expectedKey.equals("last_heard")  ){
                    //Last heard in milliseconds
                    long lastReceivedTime = Long.parseLong(actualMap.get(expectedKey).toString());
                    //Present date time in milliseconds
                    long presentDateTime = Instant.now().toEpochMilli();
                    //Difference between present time and the last heard time
                    long difference = presentDateTime - lastReceivedTime;
                    //Difference in hours between last heard and present hour
                    long hoursDifference = TimeUnit.MILLISECONDS.toHours(difference);
                    //Permitted hours in case data is not received
                    long approvedHours = Long.parseLong(hours);
                    softAssert.assertTrue(approvedHours > hoursDifference,
                            "The  Station - "+actualMap.get(DATA_LIVE_STATUS_NAME_KEY) +
                                    " have not received data for more than " + approvedHours + " hours");
                }
            }
        }
        softAssert.assertAll();
    }

    //Data Availability Methods
    public void verifyDataStatusHealthApiResponse() throws Exception{
        verifyCommonResponseForHealthMonitorApi();

        Map<String, String> expectedKeysDataTypesDataMap = new HashMap<>();

        expectedKeysDataTypesDataMap.put(DATA_STATUS_REMARK_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_STATUS_STREAM_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_STATUS_SENSOR_TYPE_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_STATUS_MODE_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_STATUS_ONLINE_PERCENTAGE_KEY, "Float");
        expectedKeysDataTypesDataMap.put(DATA_STATUS_ONLINE_STATIONS_COUNT_KEY, "Int");
        expectedKeysDataTypesDataMap.put(DATA_STATUS_SOURCE_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_STATUS_SOURCE_REMARK_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_STATUS_KEY, "String");
        expectedKeysDataTypesDataMap.put(DATA_STATUS_TOTAL_STATIONS_COUNT_KEY, "Int");

        helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY,
                expectedKeysDataTypesDataMap);
    }

    public void verifyDataAvailability() {
        Map<String, String> expectedDataAvailabilityMap = new HashMap<>();

        expectedDataAvailabilityMap.put(DATA_STATUS_SOURCE_KEY, "String");
        expectedDataAvailabilityMap.put(DATA_STATUS_SENSOR_TYPE_KEY, "String");
        expectedDataAvailabilityMap.put(DATA_STATUS_ONLINE_PERCENTAGE_KEY, "Float");

        JsonPath jPath = helper.getJsonPath();

        List<AssertionError> assertionErrorsList = new ArrayList<>();
        // Getting the List of Maps at the jsonPath
        List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);

        for (Map<String, Object> actualMap : actualList){
            for (Map.Entry<String, String> entry : expectedDataAvailabilityMap.entrySet()) {

                String expectedKey = entry.getKey();

                Object value = actualMap.get(expectedKey);
                Log.info("Value of the Data column Key " + expectedKey + "=" + value);

                try {
                    String customer = FileReaderManager.getInstance().getConfigReader()
                            .getAPIHeaderValues("customer").toUpperCase();
                    String benchmark = customer+"_"+actualMap.get(DATA_STATUS_SOURCE_KEY)+
                            "_"+actualMap.get(DATA_STATUS_SENSOR_TYPE_KEY).toString().toUpperCase().replace("/","_");
                    if(entry.getKey().equalsIgnoreCase(DATA_STATUS_ONLINE_PERCENTAGE_KEY)){
                         Document document = new Document("title", "Data-Availability")
                                .append("customer", FileReaderManager.getInstance().getConfigReader().getAPIHeaderValues("customer").toUpperCase())
                                .append("vendor", actualMap.get(DATA_STATUS_SOURCE_KEY))
                                .append("sensor_type", actualMap.get(DATA_STATUS_SENSOR_TYPE_KEY))
                                .append("online_percentage",actualMap.get(expectedKey))
                                .append("threshold", ConfigFileReader.getDataStatusConfigProperty(benchmark))
                                .append("last_updated", CommonUtils.convertTimeInMillisecondsToUTCDateFormat(Long.parseLong(actualMap.get(DATA_STATUS_LAST_MODIFIED_KEY).toString()), "dd/MM/YYYY HH:mm:ss"))
                                .append("last_updated_ms", actualMap.get(DATA_STATUS_LAST_MODIFIED_KEY))
                                .append("job_run_timestamp",CommonUtils.convertTimeInMillisecondsToUTCDateFormat(new Date().getTime(), "dd/MM/YYYY HH:mm:ss"))
                                .append("job_run_timestamp_ms",new Date().getTime());
                        //Commenting the insertIntoMongo for now , can be uncommented when ever
                        // needed with proper connection details
                        //DatabaseManager.insertIntoMongoCollection("Test","Data_Availability",document);
                        assertTrue(
                        Float.parseFloat(actualMap.get(expectedKey).toString()) > Float.parseFloat(ConfigFileReader.getDataStatusConfigProperty(benchmark))
                                || Float.parseFloat(actualMap.get(expectedKey).toString()) == Float.parseFloat(ConfigFileReader.getDataStatusConfigProperty(benchmark)),
                        "The  vendor- "+actualMap.get(DATA_STATUS_SOURCE_KEY) +
                                    " is having low stations count % of " + actualMap.get(expectedKey) +" then the threshold "+ ConfigFileReader.getDataStatusConfigProperty(benchmark) +
                                        " for the sensor/s type- " + actualMap.get(DATA_STATUS_SENSOR_TYPE_KEY));
                    }

                }catch (AssertionError e){
                    Log.error(e.getMessage());
                    assertionErrorsList.add(e);
                    dataStatusFailedList.add(actualMap);
                }
            }
        }
        if (!assertionErrorsList.isEmpty()) {
            StringBuilder sb = new StringBuilder("The following assertions failed while iterating "
                    + "at jsonPath '" + COMMON_RESPONSE_OBJECT_DATA_KEY + "': \n\n");
            for (AssertionError assertionError : assertionErrorsList) {
                sb.append(assertionError.getMessage()).append("\n\n");
            }
            throw new AssertionError(sb);
        }
    }

    public void verifyWidgetStatsFromWidgetApi() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        String cName =  RuntimeConfigSingleton.getInstance().getCustomerName();
        assert cName != null;
        JsonPath jPath = helper.getJsonPath();

        helper.verifyValueNotEmptyAtAJsonPathExpression(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+WIDGET_STATISTICS_LIST_KEY);

        Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();

        expectedKeysDataTypesResponseMap.put(WIDGET_DATA_OFFLINE_KEY, "int");
        expectedKeysDataTypesResponseMap.put(WIDGET_DATA_PERCENT_KEY, "Float");
        expectedKeysDataTypesResponseMap.put(WIDGET_DATA_TOTAL_KEY, "Int");
        expectedKeysDataTypesResponseMap.put(WIDGET_DATA_TYPE_KEY, "String");
        expectedKeysDataTypesResponseMap.put(WIDGET_DATA_VARIATION_RATE_KEY, "int");

        /* This method verifies that the Response Object($) map contains the expected keys.
           The data Type of the value of each key is as expected ,Value of key not null and
           Value of Key is not empty for the type 'List','String' or 'Map'*/
        helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+WIDGET_STATISTICS_LIST_KEY, expectedKeysDataTypesResponseMap);

        List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+WIDGET_STATISTICS_LIST_KEY);
        for (Map<String, Object> actualMap : actualList) {
            for (Map.Entry<String, String> entry : expectedKeysDataTypesResponseMap.entrySet()) {

                String expectedKey = entry.getKey();
                if(expectedKey.equalsIgnoreCase("type")){
                    Object value = actualMap.get(expectedKey);
                    Log.info("Value of the Data column Key " + expectedKey + "=" + value);

                    softAssert.assertTrue(getHealthIssueTypesList(cName).contains(value.toString().toLowerCase()),
                            "The widget type "+ value +" is not in the customer "+ cName + " list");
                }
            }
        }
        softAssert.assertAll();
    }

    public List<String> getHealthIssueTypesList(String cName){
        List<String> list = new ArrayList<>();

        list.add("offline");
        list.add("sensor");
        list.add("battery");
        list.add("rtu");
        list.add("intrusion");
        list.add("data");
        list.add("diagnostics");

        return list;
    }

    public void verifyIssueTypeDistributionTableDetailsInTheApi(String vendor) throws Exception {
        SoftAssert softAssert = new SoftAssert();
        String cName =  RuntimeConfigSingleton.getInstance().getCustomerName();
        assert cName != null;
        JsonPath jPath = helper.getJsonPath();
        Object value;

        Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();
        expectedKeysDataTypesResponseMap.put(DATA_ZONE_KEY, "String");
        expectedKeysDataTypesResponseMap.put(DATA_STATION_NAME_KEY, "String");
        expectedKeysDataTypesResponseMap.put(DATA_SOURCE_ID_KEY, "String");
        expectedKeysDataTypesResponseMap.put(DATA_STATION_ID_KEY, "String");
        expectedKeysDataTypesResponseMap.put(DATA_TYPE_KEY, "String");
        expectedKeysDataTypesResponseMap.put(DATA_STATUS_KEY, "String");
        expectedKeysDataTypesResponseMap.put(DATA_PROJECT_KEY, "String");
        helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY, expectedKeysDataTypesResponseMap);

        List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);
        for (Map<String, Object> actualMap : actualList) {
            for (Map.Entry<String, String> entry : expectedKeysDataTypesResponseMap.entrySet()) {

                String expectedKey = entry.getKey();
                String stationID  = actualMap.get(DATA_STATION_ID_KEY).toString();
                String sourceID = actualMap.get(DATA_SOURCE_ID_KEY).toString();
                softAssert.assertTrue(sourceID.contains(stationID),
                        "The station ID "+ stationID + " and the source ID "+ sourceID + " are not same");
                if(expectedKey.equalsIgnoreCase("type")){
                    value = actualMap.get(expectedKey);
                    Log.info("Value of the issue type Key " + expectedKey + "=" + value);

                    softAssert.assertTrue(getHealthIssueTypesList(cName).contains(value.toString().toLowerCase()),
                            "The issue type "+ value +" is not in the customer "+ cName + " list");
                }
                if(expectedKey.equalsIgnoreCase("project")){
                    value = actualMap.get(expectedKey);
                    Log.info("Value of the vendor Key " + expectedKey + "=" + value);

                    softAssert.assertTrue(vendor.contains(value.toString()),
                            "The project "+ value +" is not as per the vendor selected "+ vendor);
                }
            }
        }
        softAssert.assertAll();
    }

    public void verifySpecificDetailsForDailyIssuesChartAPI() {
        JsonPath jPath = helper.getJsonPath();
        String path = COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." +COMMON_RESPONSE_OBJECT_DATA_KEY;
        List<Map<String, Object>> actualList;
        String cName = RuntimeConfigSingleton.getInstance().getCustomerName();
        assert cName != null;
        Map<String, Object> actualMap = jPath.getMap(path);
        for (Map.Entry<String, Object> entry : actualMap.entrySet()){
            assertTrue(getHealthIssueTypesList(cName).contains(entry.getKey().toLowerCase()),
                    "Issue type " + entry.getKey() + "' is not as per the expected values of " + cName + " issue type list");
            Log.info("Asserted the " + entry.getKey() + " value is from the issue type list:" + getHealthIssueTypesList(cName));
            actualList = jPath.getList(path+"."+entry.getKey());
            assertFalse(actualList.isEmpty(), entry.getKey()+ "is not having any values");
            Log.info("Asserted to check if " + path+"."+entry.getKey() + " is not empty");
        }
    }

    public void verifySpecificDetailsForIssuesResolvedNewAndBacklogChartAPI() {
        JsonPath jPath = helper.getJsonPath();
        String path = COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]";
        List<Map<String, Object>> actualList;

        Map<String, Object> actualMap = jPath.getMap(path);
        for (Map.Entry<String, Object> entry : actualMap.entrySet()){
            assertTrue(getHealthIssueStatusList().contains(entry.getKey().toLowerCase()),
                    "Issue status " + entry.getKey() + "' is not as per the expected values of Issue status list");
            Log.info("Asserted the " + entry.getKey() + " value is from the issue type list:" + getHealthIssueStatusList());
            actualList = jPath.getList(path+"."+entry.getKey());
            assertFalse(actualList.isEmpty(), entry.getKey()+ "is not having any values");
            Log.info("Asserted to check if " + path+"."+entry.getKey() + " is not empty");
        }
    }

    public List<String> getHealthIssueStatusList(){
        List<String> list = new ArrayList<>();
        list.add("assigned");
        list.add("resolved");
        list.add("unassigned");

        return list;
    }

    public void verifySpecificDetailsForHourOnHourChartAPI() {
        JsonPath jPath = helper.getJsonPath();
        String path = COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." +COMMON_RESPONSE_OBJECT_DATA_KEY;
        Range<Integer> myRange = Range.between(0, 23);
        String cName = RuntimeConfigSingleton.getInstance().getCustomerName();
        assert cName != null;

        Map<String, Object> actualMap = jPath.getMap(path);
        for (Map.Entry<String, Object> entry : actualMap.entrySet()){
            assertTrue(getHealthIssueTypesList(cName).contains(entry.getKey().toLowerCase()),
                    "Issue type " + entry.getKey() + "' is not as per the expected values of " + cName + " issue type list");
            Log.info("Asserted the " + entry.getKey() + " value is from the issue type list:" + getHealthIssueTypesList(cName));
            List<Map<String, Object>> actualList = jPath.getList(path+"."+entry.getKey());
            assertFalse(actualList.isEmpty(), entry.getKey()+ "is not having any values");
            Log.info("Asserted to check if " + path+"."+entry.getKey() + " is not empty");

            for (int i=0;i<actualList.size();i++){
                List<Map<String, Object>> newList = jPath.getList(path+"."+entry.getKey()+"["+i+"]");

                Object hour = newList.get(0);
                assertTrue(myRange.contains(Integer.parseInt(hour.toString())),
                        hour + " value is not a clock hour");
                Log.info("Asserted to check if " + hour + " is a valid hour in the path :"+ path+"."+entry.getKey()+"["+i+"]");
            }
        }
    }

    public void verifySpecificDetailsForTrendsPieChartAPI() throws Exception {
        JsonPath jPath = helper.getJsonPath();
        String cName = RuntimeConfigSingleton.getInstance().getCustomerName();
        assert cName != null;
        Map<String, Object> issuesMap;

        Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();
        expectedKeysDataTypesResponseMap.put("issues_rate", "int");
        expectedKeysDataTypesResponseMap.put("total_issues", "int");
        expectedKeysDataTypesResponseMap.put("issues", "map");

        String path = COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]";
        Map<String, Object> dataMap = jPath.getMap(path);
        for (Map.Entry<String, Object> entry : dataMap.entrySet()){
            String key = entry.getKey();
            String pieChartIssuesPath =  path+"."+key+".piechart_issues";
            List<Map<String, Object>> chartIssuesList = jPath.getList(pieChartIssuesPath);

            issuesMap = jPath.getMap(pieChartIssuesPath+"[0]");
            Log.info("List of pie chart issues: " +issuesMap + " for "+ key +" week");
            helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath(pieChartIssuesPath+"[0]",expectedKeysDataTypesResponseMap);

            for (Map<String, Object> pieMap : chartIssuesList) {
                for (Map.Entry<String, String> mapEntry : expectedKeysDataTypesResponseMap.entrySet()) {
                    String expectedKey = mapEntry.getKey();
                    System.out.println("expect:"+expectedKey);
                    Object value = pieMap.get(expectedKey);
                    Log.info("Value of the issue type Key " + expectedKey + "=" + value);
                    if(expectedKey.equalsIgnoreCase("issues")){
                        Map<String, Object> issues = jPath.getMap(pieChartIssuesPath+"[0].issues");
                        for (Map.Entry<String, Object> issuesKeyEntry : issues.entrySet()) {
                                String issueType = issuesKeyEntry.getKey();
                                assertTrue(getHealthIssueTypesList(cName).contains(issueType.toLowerCase()),
                                        issueType.toLowerCase() +" is not from the list"+getHealthIssueTypesList(cName));
                        }
                    }
                }
            }
        }
    }

    public void verifySpecificDetailsForIssueDistributionChartAPI(){
        JsonPath jPath = helper.getJsonPath();
        String cName = RuntimeConfigSingleton.getInstance().getCustomerName();
        assert cName != null;

        String path = COMMON_RESPONSE_OBJECT_DATA_KEY + "[0]." + COMMON_RESPONSE_OBJECT_DATA_KEY;
        Map<String, Object> dataMap = jPath.getMap(path);
        for (Map.Entry<String, Object> entry : dataMap.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            Log.info("The issue which is being verified is - "+ key);
            assertTrue(getHealthIssueTypesList(cName).contains(key.toLowerCase()),
                    "Issue type "+ key + " is not as per the specified list for the customer " + cName);
            assertFalse(value.toString().isEmpty(),"Issue type "+ key + " does not have any data");
        }
    }

    public void verifySpecificDetailsForInstrumentTabSensorDetails(){
        JsonPath jPath = helper.getJsonPath();
        String cName = RuntimeConfigSingleton.getInstance().getCustomerName();
        assert cName != null;
        List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);

        for (Map<String, Object> actualMap : actualList) {
            Object value = actualMap.get("customer_id");
            softAssert.assertTrue(value.toString().equals(cName),"Customer ID is not the correct customer");
            softAssert.assertTrue(actualMap.get("liveStatusId").toString().equals(actualMap.get("source_id").toString()),
                    "Live status id and source id are not the same");
            softAssert.assertTrue(actualMap.get("source_id").toString().contains(actualMap.get("stationId").toString()),
                    "The station and the source id does'nt belong to same source :"+ actualMap.get("stationId"));
            softAssert.assertTrue(actualMap.get("source_id").toString().contains(actualMap.get("sensorType").toString()),
                    "The sensor type" + actualMap.get("sensorType") + " is not the actual sensor which created issue");
            softAssert.assertTrue(getIssueStatus().contains(actualMap.get("status").toString()),
                    "The status is not online or offline - "+ actualMap.get("status"));
        }
        softAssert.assertAll();
    }

    public void verifyResponseDetailsForUserPreference() throws Exception {
        verifyCommonResponseForHealthMonitorApi();

        String userId =  FileReaderManager.getInstance().getConfigReader().getAPIHeaderValues("user");
        assert userId != null;
        JsonPath jPath = helper.getJsonPath();
        List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);

        for (Map<String, Object> actualMap : actualList) {
            Object value = actualMap.get("userId");
            Assert.assertEquals(value,userId,"The user ID are not the same");
            Object idValue = actualMap.get("id");
            Assert.assertEquals(idValue,1,"The ID are not the same");
        }
        List<String> columnList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0].hmMaintenanceActivitiesCols");
        for (String column : columnList) {
            softAssert.assertTrue(getMaintenanceColumnList().contains(column),
                    "The column is not from the list");
        }
        softAssert.assertAll();
    }

    public void verifyResponseDetailsToUpdateUserPreference() throws Exception {
        helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_TEXT_HTML);
        helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");
        helper.verifyValueNotEmptyAtAJsonPathExpression("$");
        helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY, STATUS_CODE_201,
                "int");
        helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_TEXT_KEY,
                STATUS_TEXT_CREATED, "String");

        String userId =  FileReaderManager.getInstance().getConfigReader().getAPIHeaderValues("user");
        assert userId != null;
        JsonPath jPath = helper.getJsonPath();
        Map<String, Object> dataMap = jPath.getMap(COMMON_RESPONSE_OBJECT_DATA_KEY);
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            Object value;
            if(key.equals("userId")){
                value = entry.getValue();
                Assert.assertEquals(value,userId,"The user ID are not the same");
            } else if (key.equals("id")) {
                value = entry.getValue();
                Assert.assertEquals(value,1,"The id is not the same");
            } else if (key.equals("hmMaintenanceActivitiesCols")) {
                /*for (String column : ) {
                    softAssert.assertTrue(getMaintenanceColumnList().contains(column),
                            "The column is not from the list");
                }*/
            }
        }
        softAssert.assertAll();
    }


    public void verifyResponseDetailsOfHMDetections() throws Exception {
        verifyCommonResponseForHealthMonitorApi();
        SoftAssert softAssert = new SoftAssert();
        String cName =  RuntimeConfigSingleton.getInstance().getCustomerName();
        assert cName != null;
        JsonPath jPath = helper.getJsonPath();
        List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"categories");
        for (Map<String, Object> actualMap : actualList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {

                String expectedKey = entry.getKey();
                if(expectedKey.equalsIgnoreCase("title")){
                    Object value = actualMap.get(expectedKey);
                    Log.info("Value of the category Key " + expectedKey + "=" + value);

                    softAssert.assertTrue(getHealthIssueTypesList(cName).contains(value.toString().toLowerCase()),
                            "The category "+ value +" is not from the customer "+ cName + " list");
                }
            }
        }
        List<Map<String, Object>> statusList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"status");
        for (Map<String, Object> actualMap : statusList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {

                String expectedKey = entry.getKey();
                if(expectedKey.equalsIgnoreCase("title")){
                    Object value = actualMap.get(expectedKey);
                    Log.info("Status Key " + expectedKey + "=" + value);

                    softAssert.assertTrue(getDifferentStatus().contains(value.toString().toLowerCase()),
                            "The status "+ value +" is not from the list");
                }
            }
        }
        List<Map<String, Object>> maintenanceStatusList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"maintenanceStatus");
        for (Map<String, Object> actualMap : maintenanceStatusList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {

                String expectedKey = entry.getKey();
                if(expectedKey.equalsIgnoreCase("title")){
                    Object value = actualMap.get(expectedKey);
                    Log.info("Maintenance status Key " + expectedKey + "=" + value);

                    softAssert.assertTrue(getMaintenanceStatus().contains(value.toString().toLowerCase()),
                            "The maintenance status "+ value +" is not from the list");
                }
            }
        }
        //Note:If needed will have to assert for issue type and health data
        softAssert.assertAll();
    }
    public List<String> getIssueStatus(){
        List<String> list = new ArrayList<>();
        list.add("online");
        list.add("offline");

        return list;
    }

    public List<String> getDifferentStatus(){
        List<String> list = new ArrayList<>();
        list.add("online");
        list.add("offline");
        list.add("active");
        list.add("inactive");

        return list;
    }

    public List<String> getMaintenanceStatus(){
        List<String> list = new ArrayList<>();
        list.add("assigned for maintenance");
        list.add("planned");
        list.add("done");
        list.add("needs full replacement");
        list.add("waiting for parts");
        list.add("require attention");

        return list;
    }

    public List<String> getMaintenanceColumnList(){
        List<String> list = new ArrayList<>();
        list.add("activitiesAction");
        list.add("activitiesZone");
        list.add("activitiesReported");
        list.add("stationName");
        list.add("id");
        list.add("activitiesStationStatus");
        list.add("activitiesTeamName");
        list.add("lastBateryVolt");
        list.add("sub_vendor_name");
        list.add("activitiesDateOfOps");
        list.add("activitiesIssueType");
        list.add("activitiesStatus");
        list.add("activitiesIssueDetails");
        list.add("initialAssignDate");
        list.add("comment");

        return list;
    }
}
