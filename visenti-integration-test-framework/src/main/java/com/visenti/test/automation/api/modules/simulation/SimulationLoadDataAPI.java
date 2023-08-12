package com.visenti.test.automation.api.modules.simulation;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;
import com.visenti.test.automation.utils.CommonUtils;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.visenti.test.automation.constants.APIConstants.*;

public class SimulationLoadDataAPI extends RestBaseModule {

    SoftAssert softAssert = new SoftAssert();
    public SimulationLoadDataAPI(RestAssuredHelper helper) {
        super(helper);
    }

    public void setUpRequestForSimulationApi(String api,String basePah) {
        super.setUpRequestWithBasePath(api,basePah);
    }

    public void setUpQueryParamToSimResultAPIs(){
        String startTime,endTime;
        startTime = LocalDateTime.now().minusDays(30)+"Z";

        endTime = LocalDateTime.now()+"Z";

        Map<String,Object> map = new HashMap<>();
        map.put("createdOnStart",startTime);
        map.put("createdOnEnd",endTime);
        helper.addQueryParamsToTheRequest(map);
    }

    public void addPayloadToTheSimulationCreateRequest(String id, String type) throws IOException {
        String startTime,endTime,createdOn,payload = null;
        long fromDateInMilli,toDateInMilli;

        startTime = LocalDateTime.now().plusDays(1).minusHours(6)+"Z";
        endTime = LocalDateTime.now().plusDays(1)+"Z";
        createdOn = LocalDateTime.now().plusMinutes(5)+"Z";
        String uuid = UUID.randomUUID().toString();

        fromDateInMilli = LocalDateTime.now().minusHours(6).atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
        toDateInMilli = LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();

        if(type.equalsIgnoreCase("pipe")){
            payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(SIMULATION_PIPE_ISOLATION_PAYLOAD_PATH);
            payload = payload
                    .replace("pipeId", id);
        } else if (type.equalsIgnoreCase("valve")){
            payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(SIMULATION_VALVE_OPERATION_PAYLOAD_PATH);
            payload = payload
                    .replace("valve_id", id);
        } else if (type.equalsIgnoreCase("nwd") || type.equalsIgnoreCase("nws")){
            payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(SIMULATION_NWD_PAYLOAD_PATH);
            payload = payload
                    .replace("junction_Id", id);
        }

        payload = payload
                .replace("fromDate", Long.toString(fromDateInMilli))
                .replace("toDate",Long.toString(toDateInMilli))
                .replace("startDate",startTime)
                .replace("endDate",endTime)
                .replace("created_on",createdOn)
                .replace("uuid",uuid);

        helper.addPayloadStringToTheRequest(payload);
    }

    public void verifyTheResponseOfSimulationResultAPi() throws Exception {
        helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_JSON);

        helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "list");

        helper.verifyValueNotEmptyAtAJsonPathExpression("$");

        Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();

        expectedKeysDataTypesResponseMap.put("createdOn", "String");
        expectedKeysDataTypesResponseMap.put("id", "String");
        expectedKeysDataTypesResponseMap.put("scenarios", "List");
        expectedKeysDataTypesResponseMap.put("name", "String");
        expectedKeysDataTypesResponseMap.put("user", "String");

        helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath("$", expectedKeysDataTypesResponseMap);

        JsonPath jPath = helper.getJsonPath();
        List<Map<String, Object>> actualList = jPath.getList("$");

        for (Map<String, Object> actualMap : actualList) {
            for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
                String expectedKey = entry.getKey();
                if(expectedKey.equalsIgnoreCase("scenarios")){
                    List<Map<String, Object>> actualList1 = jPath.getList("scenarios[0]");
                    for (Map<String, Object> actualMap1 : actualList1) {
                        for (Map.Entry<String, Object> entry1 : actualMap1.entrySet()) {
                            if(entry1.getKey().equalsIgnoreCase("simulationId")){
                                String simulationId = entry1.getValue().toString();
                                softAssert.assertTrue(getSimulationScenarios().contains(simulationId),
                                        "Simulation type is not as per the available list for the customer");
                            }
                        }
                    }
                }
            }
        }
        softAssert.assertAll();
    }

    public List<String> getSimulationScenarios(){
        List<String> list = new ArrayList<>() ;

        list.add("VALVE_OPERATION");
        list.add("PIPE_ISOLATION");
        list.add("NEW_WATER_DEMAND_OR_SOURCE");

        return list;
    }

    public void verifyTheResponseOfCreateSimulationAPi() throws Exception {
        helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_JSON);

        helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");

        helper.verifyValueNotEmptyAtAJsonPathExpression("$");

        Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();

        expectedKeysDataTypesResponseMap.put("impact", "map");
        //expectedKeysDataTypesResponseMap.put("suggestedActions", "map");
        expectedKeysDataTypesResponseMap.put("timestep", "int");

        helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath("$", expectedKeysDataTypesResponseMap);

        JsonPath jPath = helper.getJsonPath();
        Map<String, Object> actualMap = jPath.getMap("impact");

        for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
            Object value = entry.getValue();
            if(value instanceof List){
                String expectedKey = entry.getKey();
                Assert.assertTrue(getImpactList().contains(expectedKey),
                        "The impact "+expectedKey+" is not from the list");
            }
        }

    }

    public List<String> getImpactList(){
        List<String> list = new ArrayList<>();

        list.add("highFlowChange");
        list.add("highVelocitys");
        list.add("impactedCustomers");
        list.add("lowFlowChange");
        list.add("lowPressure");
        list.add("noFlows");
        list.add("noPressure");
        list.add("reverseFlows");
        list.add("waterAge");

        return list;
    }

    public void verifyTheResponseOfSimulationSettingsAPi() throws Exception {
        helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_JSON);

        helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");

        helper.verifyValueNotEmptyAtAJsonPathExpression("$");

        JsonPath jPath = helper.getJsonPath();
        Map<String,Object> settingsMap = jPath.getMap("$");

        for(Map.Entry<String, Object> entry : settingsMap.entrySet()){
            String key = entry.getKey();
            softAssert.assertTrue(getSettingsKeyList().contains(key),
                    key+" is not from the expected key set");
            if(key.equalsIgnoreCase("newDemand")||key.equalsIgnoreCase("pipeIsolation")
                ||key.equalsIgnoreCase("pwNw")||key.equalsIgnoreCase("valveOperation")){
                Object value = helper.getValueAtAGivenJsonPathExpression(key+".inputTimePeriodInDays");
                softAssert.assertTrue((int) value == 3,
                        "Input time in period in days is not 3 days for the key " + key);
            }
        }
        softAssert.assertAll();
    }

    public List<String> getSettingsKeyList(){
        List<java.lang.String> list = new ArrayList<>();
        String customer = RuntimeConfigSingleton.getInstance().getCustomerName();

        list.add("fireflow");
        list.add("newDemand");
        list.add("pipeIsolation");
        list.add("pwNw");
        list.add("sourceDiscoloration");
        list.add("valveOperation");

        if(customer.equalsIgnoreCase("pub")||customer.equalsIgnoreCase("cloud")){
            list.add("validZoneCombinations");
        }

        return list;
    }
}
