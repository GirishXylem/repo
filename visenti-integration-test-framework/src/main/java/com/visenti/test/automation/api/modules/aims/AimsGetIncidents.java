package com.visenti.test.automation.api.modules.aims;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.*;
import com.visenti.test.automation.utils.CommonUtils;
import io.restassured.path.json.JsonPath;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.visenti.test.automation.constants.APIConstants.*;

public class AimsGetIncidents extends RestBaseModule {

	public AimsGetIncidents(RestAssuredHelper helper) {
		super(helper);

	}

	public void setUpRequestForGetIncidentsApi(String ...apiAndParameters) throws IOException {
		super.setUpRequest(apiAndParameters[0]);
		addPayloadToTheRequest(apiAndParameters[1], apiAndParameters[2], apiAndParameters[3]);
	}

	public void requestForGetIncidentsDetailsApi(String ...apiAndParameters) {
		super.setUpRequest(apiAndParameters[0]);
		addIncidentDetailsPayloadToTheRequest();
	}

	public void verifyCommonResponseForAimsApi() throws Exception {
		// Assert the Content-Type header in Response is text/html
		helper.verifyResponseHeaderValue("Content-Type", CONTENT_TYPE_JSON);
		// Assert Response is an instance of Map
		helper.verifyDataTypeOfValueAtAGivenJsonPathExpression("$", "map");
		// Assert Response object is not empty
		helper.verifyValueNotEmptyAtAJsonPathExpression("$");

		Map<String, String> expectedKeysDataTypesResponseMap = new HashMap<>();
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY, "int");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_DATA_KEY, "List");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_META_KEY, "map");
		expectedKeysDataTypesResponseMap.put(COMMON_RESPONSE_OBJECT_STATUS_KEY, "String");
		//To verify at the path "$" from the above map
		helper.verifyKeysAndTheirValueDataTypeInAMapAtJsonPath("$", expectedKeysDataTypesResponseMap);

		// Verify status_code key value to be equal to 200
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_CODE_KEY,
				STATUS_CODE_200, "Int");
		// Verify the value of 'status' key equal to 'success'
		helper.verifyValueAtAJsonPathExpressionForAValidationType(COMMON_RESPONSE_OBJECT_STATUS_KEY, STATUS_SUCCESS,
				"String");

		// Assert each data Map in the List($.data)contains the expected Keys and verify
		// Data-Types value of each Key
		Map<String, String> expectedKeysDataTypesDataMap = new HashMap<>();
		expectedKeysDataTypesDataMap.put(AIMS_INCIDENT_ID_KEY, "String");
		expectedKeysDataTypesDataMap.put(AIMS_INCIDENT_TYPE_KEY, "String");
		expectedKeysDataTypesDataMap.put(AIMS_LOCATION_KEY, "map");
		expectedKeysDataTypesDataMap.put(AIMS_ONGOING_KEY, "boolean");
		expectedKeysDataTypesDataMap.put(AIMS_RECOMMENDATION_KEY, "String");
		expectedKeysDataTypesDataMap.put(AIMS_STATE_KEY, "String");
		expectedKeysDataTypesDataMap.put(AIMS_STATUS_KEY, "String");
		//expectedKeysDataTypesDataMap.put(AIMS_TAG_NETWORK_NAME_KEY, "String");

		helper.verifyKeysAndTheirValueDataTypeInEachMapOfAListAtJsonPath(COMMON_RESPONSE_OBJECT_DATA_KEY,
				expectedKeysDataTypesDataMap);

		// Verify the keys and their value dataTypes in meta Map
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

	public void verifySpecificDetailsInGetIncidentApi(String incidentType){
		SoftAssert softAssert = new SoftAssert();
		Object value = null;
		JsonPath jPath = helper.getJsonPath();
		List<Map<String, Object>> actualList = jPath.getList(COMMON_RESPONSE_OBJECT_DATA_KEY);
		incidentType = incidentType.replace(" ","_");
		int i=0;
		for (Map<String, Object> actualMap : actualList) {
			for (Map.Entry<String, Object> entry : actualMap.entrySet()) {
				String expectedKey = entry.getKey();
				if(expectedKey.equals("incidentType")){
					value = actualMap.get(expectedKey);
					Log.info("Value of the Data column Key " + expectedKey + "=" + value);
					if(!incidentType.equalsIgnoreCase("all")){
						softAssert.assertTrue(value.toString().equalsIgnoreCase(incidentType),
								"The Incident listed is not as per the Incident type "
										+incidentType + " selected");
					}
				}
				//Verify if state is from the set of the list
				if(expectedKey.equals("state")){
					value = actualMap.get(expectedKey);
					Log.info("Value of the Data column Key " + expectedKey + "=" + value);
					softAssert.assertTrue(getIncidentStateList().contains(value),
							"The state of the incident at " +COMMON_RESPONSE_OBJECT_DATA_KEY+"."+expectedKey+"["+i+"]-"
									+value.toString()+" is from the list");
				}
				//Verify if status is from the set of the list
				if(expectedKey.equals("status")){
					value = actualMap.get(expectedKey);
					Log.info("Value of the Data column Key " + expectedKey + "=" + value);
					softAssert.assertTrue(getIncidentStatusList().contains(value),
							"The status of the incident " +COMMON_RESPONSE_OBJECT_DATA_KEY+"."+expectedKey+"["+i+"]-"
									+value.toString()+" is from the list");
				}
				//Verify if recommendation is from the set of the list
				if(expectedKey.equals("recommendation")){
					value = actualMap.get(expectedKey);
					Log.info("Value of the Data column Key " + expectedKey + "=" + value);
					softAssert.assertTrue(getIncidentRecommendationList().contains(value),
							"The Recommendation of the incident "+COMMON_RESPONSE_OBJECT_DATA_KEY+"."+expectedKey+"["+i+"]-"
									+value.toString()+" is from the list");
				}
			}
		}
		i++;
		softAssert.assertAll();
	}

	private void addPayloadToTheRequest(String incidentType, String anomaly, String zones) throws IOException {
		String filePath;
		String payload = null;
		filePath = AIMS_GET_INCIDENTS_PAYLOAD_PATH;
		Long fromDateInMilli,toDateInMilli;
		payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);
		if("cloud".equalsIgnoreCase(RuntimeConfigSingleton.getInstance().getCustomerName())){
			fromDateInMilli = 1580556305000L;

			toDateInMilli = 1582975505000L;

		} else {
			//update required from and to Date for payload
			fromDateInMilli = LocalDateTime.now().minusHours(24).atZone(ZoneId.systemDefault())
							.toInstant().toEpochMilli();

			toDateInMilli = LocalDateTime.now().atZone(ZoneId.systemDefault())
							.toInstant().toEpochMilli();
		}

		/**
		 * Update the payload with new values like
		 * Incident type, From date, To date
		 */
		payload = payload
					.replace("incident-type","\""+incidentType+"\"")
					.replace("startDate",fromDateInMilli.toString())
					.replace("endDate",toDateInMilli.toString());

		//Update the payload with the required anomaly list
		if (anomaly.toLowerCase().contains("all")) {
			payload = payload.replace("anomaly-list", AIMS_ANOMALY_LIST);
		} else {
			payload = payload.replace("anomaly-list", "\""+anomaly+"\"");
		}
		if(zones.equalsIgnoreCase("all")){
			String zone = ConfigFileReader.getConfigProperty(RuntimeConfigSingleton.getInstance().getCustomerName()+".zones.all");
			assert zone != null;
			payload = payload.replace("zones-list",zone);
		} else {
			payload = payload.replace("zones-list", "\""+zones+"\"");
		}

		helper.addPayloadStringToTheRequest(payload);
	}

	private void addIncidentDetailsPayloadToTheRequest() {
		String incidentId = getIncidentId();
		String payload = "{\"incidentId\":\""+ incidentId +"\",\"tag_network_name\":\"potable_water\"}";
		helper.addPayloadStringToTheRequest(payload);
	}

	public String getIncidentId (){
		JsonPath jPath = helper.getJsonPath();
		String incidentId = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"incidentId");
		System.out.println("Incident ID :" + incidentId);

		return incidentId;
	}

	public List<String> getValuesFromIncidentList (){
		List<String> list = new ArrayList();
		JsonPath jPath = helper.getJsonPath();
		String incidentId = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"incidentId");
		list.add(incidentId);
		String incidentType = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"incidentType");
		list.add(incidentType);
		String ongoing = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"ongoing");
		list.add(ongoing);
		String recommendation = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"recommendation");
		list.add(recommendation);
		String state = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"state");
		list.add(state);
		String status = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"status");
		list.add(status);

		return list;
	}

	public void verifyIncidentListAndDetailsHaveSameValues() {
		SoftAssert softAssert = new SoftAssert();
		JsonPath jPath = helper.getJsonPath();
		String [] incidentValuesList = getValuesFromIncidentList().toArray(new String[0]);
		System.out.println("List from get incidents:" + incidentValuesList );

		String incidentId = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"incidentId");
		softAssert.assertTrue(incidentId.equalsIgnoreCase(incidentValuesList[0]),
				"The Incident id in incident list and incident details are not the same");

		String incidentType = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"incidentType");
		softAssert.assertTrue(incidentType.equalsIgnoreCase(incidentValuesList[1]),
				"The Incident type in incident list and incident details are not the same");
		String ongoing = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"ongoing");
		softAssert.assertTrue(ongoing.equalsIgnoreCase(incidentValuesList[2]),
				"The Incident ongoing value in incident list and incident details are not the same");
		String recommendation = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"recommendation");
		softAssert.assertTrue(recommendation.equalsIgnoreCase(incidentValuesList[3]),
				"The Incident recommendation in incident list and incident details are not the same");
		String state = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"state");
		softAssert.assertTrue(state.equalsIgnoreCase(incidentValuesList[4]),
				"The Incident state in incident list and incident details are not the same");
		String status = jPath.getString(COMMON_RESPONSE_OBJECT_DATA_KEY+"[0]"+"."+"status");
		softAssert.assertTrue(status.equalsIgnoreCase(incidentValuesList[5]),
				"The Incident status in incident list and incident details are not the same");

		softAssert.assertAll();
	}

	public List<String> getIncidentStatusList() {
		List<String> list = new ArrayList<>();
		list.add("Open");
		list.add("Close");

		return list;
	}

	public List<String> getIncidentStateList() {
		List<String> list = new ArrayList<>();
		list.add("Un-Attended");
		list.add("Attended");
		list.add("Investigation-Complete");
		list.add("Under-Investigation");

		return list;
	}

	public List<String> getIncidentRecommendationList() {
		List<String> list = new ArrayList<>();
		list.add("high");
		list.add("medium");
		list.add("low");
		list.add("false");

		return list;
	}


}

