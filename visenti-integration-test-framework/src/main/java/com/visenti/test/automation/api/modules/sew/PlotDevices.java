package com.visenti.test.automation.api.modules.sew;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import cucumber.api.DataTable;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PlotDevices {

	
	/**
	 * @param days
	 * @param table
	 * Verifying the Plotting API for various Stations ,devices and Sensors
	 */
	public static void verifyPlottingSensorsForVariousDevices(String days,DataTable table)
	{
		//Setting System Time Zone to SEW time zone
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
		//PortalConfigManagement portalConfigs = new PortalConfigManagement();
		String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("data");
		RestAssured.baseURI = "https://"+ PortalConfigManagement.getPortalPrefix() + "-"+ api +PortalConfigManagement.getPortalDomain();
	//	RestAssured.baseURI = "https://sewuk-data-api.eu.cloud.visenti.com";
		RestAssured.basePath="/search/data";
		
		RestAssured.useRelaxedHTTPSValidation();
			
		long defaultDays=(Long.parseLong(days));
		long endTime=System.currentTimeMillis();
		long startTime=endTime-(24*60*60*1000*defaultDays);
		
		List<String> values=gettingPlotDataValuesFromDataTable(table);
		String stationId=values.get(0);
		String device=values.get(1);
		String sensorType=values.get(2);
		String unit=values.get(3);

		TestRailTestManagement.setTestComment("Station ID : " + stationId + System.lineSeparator() + "Sensor Type : " + sensorType + System.lineSeparator() +
				"Testing Period (In days) : " + days + System.lineSeparator() + System.lineSeparator() + " Updated by Automation Framework! " );
		TestRailTestManagement.setAPI(RestAssured.baseURI+RestAssured.basePath);
		TestRailTestManagement.setAction("Plotting for "+ device + " devices" + " for "+ days +" days");

		RequestSpecification httpRequest=RestAssured.given().contentType(ContentType.TEXT)
				.body("{\"request\":{\"st\":"+startTime+",\"et\":"+endTime+","
				+ "\"intervalmin\":15,\"include\":[\""+stationId+"\"],"
						+ "\"sensortype\":\""+sensorType+"\",\"timezone\":\"Europe/London\","
								+ "\"format\":\"csv\",\"unit\":\""+unit+"\"}}").request()
				.log().all();
		Log.info("Created the Plot API request for \nStationId: "+stationId+",device: "+device+" and sensorType: "+sensorType);
		Response response = httpRequest.post();
		Log.info("Performing POST request on the Plot API");
				
		Log.info("Plot API response for StationId: "+stationId+",device: "+device+" and sensorType: "+sensorType+"\n "+response.asString());
				
		assertEquals(response.getStatusCode(),200);
		// Asserting jsonResponseData to be non empty
		List<Object> jsonResponseData = response.jsonPath().get("data");
		assertTrue(!jsonResponseData.isEmpty());
		
		//Asserting jsonResponse csv Data is not empty
		List<List<Object>> jsonResponseCSVData = response.jsonPath().get("data[0].csvData");
		try {
		assertTrue(!jsonResponseCSVData.isEmpty());
		Log.info("CSVData List successfully verified to be non empty for- " +stationId+",device- "+device+",sensorType- "+sensorType);
		}
		catch(Throwable t)
		{
			Log.error("CSVData List returned for stationId- " +stationId+",device- "+device+",sensorType- "+sensorType+" is either empty or null");
			fail(t.getMessage(),t);
		}
		// Verifying inner list is not empty
		for (List<Object> l1 : jsonResponseCSVData) {
			assertTrue(!l1.isEmpty());
				}
		// Asserting CSVHeader not empty
		List<String> jsonResponseCSVHeader = response.jsonPath().get("data[0].csvHeader");
		assertTrue(!jsonResponseCSVHeader.isEmpty());
		
		// Asserting metaList is not empty
		List<Object> jsonResponseMetaList = response.jsonPath().get("data[0].metaList");
		assertTrue(!jsonResponseMetaList.isEmpty());
		Log.info("Asserted Successfully the Plotting API response for given\n StationId: "+stationId+",device: "+device+" and sensorType: "+sensorType );
		org.testng.Reporter.log("Asserted Successfully the Plotting API response for given\n StationId: "+stationId+",device: "+device+" and sensorType: "+sensorType);
	}
	
	/**
	 * @param table
	 * @return
	 * Getting the Plot API data values from Scenario data table and returning them as List
	 */
	private static List<String> gettingPlotDataValuesFromDataTable(DataTable table)
	{
		List<String> values=new ArrayList<String>();
		Map<String,String> plottingDataMap=table.asMap(String.class, String.class);
		String stationId=null;
		String device=null;
		String sensorType=null;
		String unit=null;
		Set<String>keySet=plottingDataMap.keySet();
		
		for(String key:keySet)
		{
			if(key.equalsIgnoreCase("stationId"))
			{
				stationId=plottingDataMap.get(key);
			}
			else if(key.equalsIgnoreCase("device"))
			{
				device=plottingDataMap.get(key);
			}
			
			else if(key.equalsIgnoreCase("sensorType"))
			{
				sensorType=plottingDataMap.get(key);
			}
			else if(key.equalsIgnoreCase("unit"))
			{
				unit=plottingDataMap.get(key);
			}
			else
			{
				throw new RuntimeException("Wrong key passed from Scenario data table");
			}
		}
		values.add(stationId);
		values.add(device);
		values.add(sensorType);
		values.add(unit);
		return values;
		
	}
}
