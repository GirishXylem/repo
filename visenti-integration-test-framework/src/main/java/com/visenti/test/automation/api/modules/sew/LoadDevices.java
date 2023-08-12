package com.visenti.test.automation.api.modules.sew;


import static com.visenti.test.automation.constants.APIConstants.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LoadDevices {

	private static final String ACOUSTIC_SENSORTYPE_ACTUAL_VALUE = "\"acoustic\",\"hydrophone\"";
	private static final String BTRY_SENSORTYPE_ACTUAL_VALUE = "\"btry\"";
	private static final String CUSTOMERMETER_SENSORTYPE_ACTUAL_VALUE = "\"consumption\",\"reading\"";
	private static final String FLOW_SENSORTYPE_ACTUAL_VALUE = "\"flow\"";
	private static final String TRANSIENT_SENSORTYPE_ACTUAL_VALUE = "\"pressure\"";
	private static final String TRANSIENT_TERM_VALUE = "\"highrate\":true";
	private static final String WQY_SENSORTYPE_ACTUAL_VALUE = "\"ph\",\"cty\",\"chlorine\",\"turbidity\",\"temp\",\"oxygen\",\"orp\"";


	/**
	 * @param device
	 * This method verifies the Loading Device API
	 */
	public static void verifyLoadDevice(String device) {
		
		//PortalConfigManagement portalConfigs = new PortalConfigManagement();
		RestAssured.baseURI = "https://"+ PortalConfigManagement.getPortalPrefix()+PortalConfigManagement.getPortalDomain();
		RestAssured.basePath = "/dataintelapi/"+ PortalConfigManagement.getPortalPrefix()+"/node/meta/search";

		TestRailTestManagement.setTestComment("Updated by Automation Framework! ");
		TestRailTestManagement.setAPI(RestAssured.baseURI+RestAssured.basePath);
		TestRailTestManagement.setAction("Loading "+ device + " devices");

		RestAssured.useRelaxedHTTPSValidation();
		// Payload template ,here based on the Device type 'sensortype_actual' key value
		// will be replaced by Constants defined above

		// For only Transient device the 'term' key dummy value 'term-value' in the
		// payload template will be replaced with value ' TRANSIENT_TERM_VALUE' constant
		// For other devices the 'term' key is not there so the entire 'term' key and
		// its dummy value 'term-value'in the template
		// will be replaced with empty space

		String payload = "query={\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"bool\":{\"must\":[{\"or\":[{\"has_child\":{\"type\":\"sensor_info\",\"query\":{\"filtered\":{\"query\":{\"match_all\":{}},\"filter\":{\"bool\""
				+ ":{\"must\":[{\"terms\":{\"sensortype_actual\":[actual-sensors]}}"
				+ ",{\"term\":{term-value}}]}}}}}},{\"has_parent\":{\"type\":\"station_info\",\"query\":{\"filtered\":{\"query\":{\"match_all\":{}}}}}}]}]}}}},"
				+ "\"size\":100000}&collection=station&username=admin";

		switch (device.toLowerCase()) {
		case "acoustic":
			payload = payload.replace("actual-sensors", ACOUSTIC_SENSORTYPE_ACTUAL_VALUE)
					.replace("\"term\":{term-value}", "");
			break;

		case "btry":
			payload = payload.replace("actual-sensors", BTRY_SENSORTYPE_ACTUAL_VALUE).replace("\"term\":{term-value}",
					"");
			break;

		case "customermeter":
			payload = payload.replace("actual-sensors", CUSTOMERMETER_SENSORTYPE_ACTUAL_VALUE)
					.replace("\"term\":{term-value}", "");
			break;

		case "flow":
			payload = payload.replace("actual-sensors", FLOW_SENSORTYPE_ACTUAL_VALUE).replace("\"term\":{term-value}",
					"");
			break;

		case "transient":
			payload = payload.replace("actual-sensors", TRANSIENT_SENSORTYPE_ACTUAL_VALUE).replace("term-value",
					TRANSIENT_TERM_VALUE);
			break;

		case "wqy":
			payload = payload.replace("actual-sensors", WQY_SENSORTYPE_ACTUAL_VALUE).replace("\"term\":{term-value}",
					"");
			break;

		default:
			throw new RuntimeException("Wrong Device name passed");
		}

		RequestSpecification httpRequest = RestAssured.given().contentType(ContentType.TEXT).body(payload).request()
				.log().all();
		Log.info("Created the Request for loading" + device + " device API");
		Response response = httpRequest.when().post();
		Log.info("Performing POST request on the loading" + device + " device API");
		Log.info("Load " + device + "device API response \n" + response.asString());
		
		TestRailTestManagement.setAction("Validating availability of data for device " + device.toLowerCase());

		// Asserting the Status code to be 200
		assertEquals(response.getStatusCode(), STATUS_CODE_200);
		Log.info("Asserted the status code to be "+STATUS_CODE_200+" for Loading "+device+" devices API");
		
		//Asserting the Response 'object' is not empty
		
		// Getting the response Map
        JsonPath jPath = response.jsonPath();
        Map<String, Object> responseObjectMap = jPath.getMap("$");
        TestRailTestManagement.setAction("Validating object map is not empty");
        assertTrue(!responseObjectMap.isEmpty());
        
        Log.info("Asserted the Response 'object' map is not empty");
        
        TestRailTestManagement.setAction("Validating keys within 'object' map for device " + device.toLowerCase());
        //Asserting keys in Response 'object' map
        assertingKeysInResponseObjectMap(responseObjectMap,device.toLowerCase());
        
        //Asserting the error code to be 0
        
        TestRailTestManagement.setAction("Validating '"+DEVICES_OBJECT_ERROR_CODE_KEY+"' key ,value: as expected");
        assertEquals(responseObjectMap.get(DEVICES_OBJECT_ERROR_CODE_KEY),RESPONSE_ERRORCODE_0);
        Log.info("Asserted the '"+DEVICES_OBJECT_ERROR_CODE_KEY+"' value to be:"+RESPONSE_ERRORCODE_0);
        
		// Getting the response key Map
		@SuppressWarnings("unchecked")
		Map<String, Object> responseMap = (Map<String, Object>) responseObjectMap.get(DEVICES_OBJECT_RESPONSE_KEY);
		TestRailTestManagement.setAction("Validating the response Map is not empty ");
		assertTrue(!responseMap.isEmpty());
        Log.info("Asserted the '"+DEVICES_OBJECT_RESPONSE_KEY+"' Map is not empty");  
		
        TestRailTestManagement.setAction("Validating keys within 'response' map for device " + device.toLowerCase());
		//Asserting the response Map contains the key : 'hits'
        assertTrue(responseMap.containsKey(DEVICES_RESPONSE_HITS_KEY));
        Log.info("response map contains the key '"+DEVICES_RESPONSE_HITS_KEY+"'");
        
        //Asserting the response.hits Map is not empty
        @SuppressWarnings("unchecked")
		Map<String,Object> hitsMap=(Map<String, Object>) responseMap.get(DEVICES_RESPONSE_HITS_KEY);
        TestRailTestManagement.setAction("Validating the response.hits Map is not empty ");
        assertTrue(!hitsMap.isEmpty());
        Log.info("Asserted the '"+DEVICES_RESPONSE_HITS_KEY+"' Map is not empty");;
        
        //Asserting the hits Map contains the key 'hits'
        TestRailTestManagement.setAction("Validating keys within 'response.hits' map for device " + device.toLowerCase());
        assertTrue(hitsMap.containsKey(DEVICES_HITS_HITS_KEY));
        Log.info("hits map contains the key '"+DEVICES_HITS_HITS_KEY+"'");
        
        //Asserting the hits List is not empty
       
        
        @SuppressWarnings("unchecked")
		List<Map<String,Object>> hitsList=(List<Map<String, Object>>) hitsMap.get(DEVICES_HITS_HITS_KEY);
        TestRailTestManagement.setAction("Validating the response.hits.hits List is not empty ");
        assertTrue(!hitsList.isEmpty());
        Log.info("Asserted the '"+DEVICES_HITS_HITS_KEY+"' list is not empty");
        Log.info("The size of '"+DEVICES_HITS_HITS_KEY+"' list for the device '"+device.toLowerCase()+"'  ="+hitsList.size());
        
		//Asserting each hits Map not empty and containing keys
        TestRailTestManagement.setAction("Validating keys within each 'hits Map in the list for the device " + device.toLowerCase());
		assertingKeysInEachHitsMap(hitsList, device.toLowerCase());
		

	}
	
	private static void assertingKeysInResponseObjectMap(Map<String,Object>responseObjectMap,String device)
	{
		List<String>expectedKeys=new ArrayList<String>();
		expectedKeys.add(DEVICES_OBJECT_ERROR_CODE_KEY);
		expectedKeys.add(DEVICES_OBJECT_RESPONSE_KEY);
		
		for(String key:expectedKeys)
		{
			try
			{
				assertTrue(responseObjectMap.containsKey(key));
				Log.info("The object map contains the key '" + key + "' for '"+device +"' device");
            } catch (Throwable t) {
                Log.error("The object map do not contain the key '" + key + "' for '"+device+" 'device");
                Log.error(t.getMessage());
                TestRailTestManagement.setAction("Error -The object map do not contain the key '" + key + "' for '"+device+" 'device");
                fail(t.getMessage());
            }
		}
		
	}
	
	private static void assertingKeysInEachHitsMap(List<Map<String,Object>>hitsList,String device)
	{
		
		List<String>expectedKeys=new ArrayList<String>();
		expectedKeys.add(DEVICES_HITS_TYPE_KEY);
		expectedKeys.add(DEVICES_HITS_ID_KEY);
		expectedKeys.add(DEVICES_HITS_LEVEL_KEY);
		expectedKeys.add(DEVICES_HITS_SOURCE_KEY);
		
		int i=0;
		for(Map<String,Object>hitsMap:hitsList)
		{
			assertTrue(!hitsMap.isEmpty());
			Log.info("hits Map at index "+i+" is not empty for the device '"+device+"'");
			
			for(String key:expectedKeys)
			{
				try
				{
					assertTrue(hitsMap.containsKey(key));
					Log.info("The hits map,at index "+i+" contains the key '" + key + "' for '"+device +"' device ");
	            } catch (Throwable t) {
	                Log.error("The hits map,at index "+i+" do not contain the key '" + key + "' for '"+device +"' device ");
	                Log.error(t.getMessage());
	                TestRailTestManagement.setAction("Error -The hits map,at index "+i+" do not contain the key '" + key + "' for '"+device +"' device");
	                fail(t.getMessage());
	            }
				
			}
			i++;
			
		}
		
		
		
	}

}
