package com.visenti.test.automation.api.modules.gis.search;

import com.visenti.test.automation.api.modules.base.RestBaseModule;
import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.RestAssuredHelper;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;
import com.visenti.test.automation.utils.CommonUtils;
import io.restassured.path.json.JsonPath;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.visenti.test.automation.constants.APIConstants.*;

public class GISLoadMetaSearch extends RestBaseModule {

	public GISLoadMetaSearch(RestAssuredHelper helper) {
		super(helper);
	}

	public void setUpRequestForGisMeta(String gis) throws IOException{
		String api = "gis";
		super.setUpRequest(api);
		String customerName = RuntimeConfigSingleton.getInstance().getCustomerName().toLowerCase();
		addPayloadToTheRequest(gis, customerName);
	}

	public void setUpRequestForGisIdSearch(String gisType, String gisId) throws IOException{
		String api = "gis";
		super.setUpRequest(api);
		addPayloadToTheGISIdRequest(gisType, gisId);
	}

	private void addPayloadToTheGISIdRequest(String gisType,  String gisId) throws IOException {
		String filePath;
		String payload = null;

		filePath = GIS_META_SEARCH_ID_PAYLOAD_PATH;
		payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);
		payload = payload
				.replace("search-type",gisType+ "\"" +",")
				.replace("search-id", gisId);

		helper.addPayloadStringToTheRequest(payload);
	}

	private void addPayloadToTheRequest(String gisType, String customerName) throws IOException {
		String filePath;
		String left_lat = ConfigFileReader.getConfigProperty(customerName+".left.lat");
		String left_lon = ConfigFileReader.getConfigProperty(customerName+".left.lon");
		String right_lat = ConfigFileReader.getConfigProperty(customerName+".right.lat");
		String right_lon = ConfigFileReader.getConfigProperty(customerName+".right.lon");
		assert right_lat != null;
		assert left_lat != null;
		assert left_lon != null;
		assert right_lon != null;
		String payload = null;
		if(gisType.equalsIgnoreCase("pipe")) {
			filePath = GIS_META_PIPE_PAYLOAD_PATH;
			payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);

			payload = payload
					.replace("left-lat",left_lat)
					.replace("left-lon",left_lon)
					.replace("right-lat",right_lat)
					.replace("right-lon",right_lon)
					.replace("gis-type",gisType);
		} else{
			filePath = GIS_META_NON_PIPE_PAYLOAD_PATH;
			payload = CommonUtils.generateStringFromFileAndRemoveAllWhiteSpaces(filePath);
			payload = payload
					.replace("left-lat",left_lat)
					.replace("left-lon",left_lon)
					.replace("right-lat",right_lat)
					.replace("right-lon",right_lon)
					.replace("gis-type",gisType);
		}
		helper.addPayloadStringToTheRequest(payload);
	}

	public void verifyDefaultResponseContentForGISMetaSearchApi() throws Exception {
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

	public void verifySpecificContentsOfGISMetaSearch(String gis) {
		SoftAssert softAssert = new SoftAssert();
		verifyGisMetaInformation(gis,softAssert);
		verifyGisLocationInformation(gis,softAssert);
		softAssert.assertAll();
	}

	public void getSomeDetails(){
		JsonPath jPath = helper.getJsonPath();
		String hitsPath = META_SEARCH_OBJECT_RESPONSE_KEY + "." + META_SEARCH_RESPONSE_HITS_KEY + "." + META_SEARCH_RESPONSE_HITS_KEY ;
		List<Map<String, Object>> gisHitsList = jPath.getList(hitsPath);
		System.out.println("Search result list -"+ gisHitsList);
	}

	public void verifyGisMetaInformation(String gis, SoftAssert softAssert){
		JsonPath jPath = helper.getJsonPath();
		String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();
		String metaPath = META_SEARCH_OBJECT_RESPONSE_KEY + "." + META_SEARCH_RESPONSE_HITS_KEY + "." + META_SEARCH_RESPONSE_HITS_KEY + "." + GIS_HITS_META_KEY;
		List<Map<String, Object>> gisMetaDataList = jPath.getList(metaPath);
		for (Map<String, Object> gisMetaDataMap : gisMetaDataList) {
			for (Map.Entry<String, Object> entry : gisMetaDataMap.entrySet()) {
				String metaDataKey = entry.getKey();
				if(metaDataKey.equalsIgnoreCase("TYPE")) {
					softAssert.assertTrue(entry.getValue().toString().equalsIgnoreCase(gis),
							"The meta key -" + metaDataKey +" does not have " +
										"the required type of "+ gis);
				} else if(metaDataKey.equalsIgnoreCase("zone")) {
					softAssert.assertTrue(entry.getValue().toString().equalsIgnoreCase(customerName),
							"The meta key -" + metaDataKey +" does not have the expected " +
										"value of zone "+ customerName);
				}
				switch(gis.toLowerCase()) {
					case "pipe":
						softAssert.assertTrue(getGisPipeMetaDataKeys().contains(metaDataKey),
								"For GIS -" + gis +" meta key -"+ metaDataKey+ " is not from " +
											"the expected list");
						if(metaDataKey.equalsIgnoreCase("id")) {
							softAssert.assertTrue(entry.getValue().toString().toLowerCase().startsWith("p"),
									"The meta key -" + metaDataKey +" and its value -"+ entry.getValue()+
												" does not start with P");
						}
						break;

					case "valve":
						softAssert.assertTrue(getGisNonPipeMetaDataKeys().contains(metaDataKey),
								"For GIS -" + gis +" meta key -"+ metaDataKey+ " is not from the " +
											"expected list");
						if(metaDataKey.equalsIgnoreCase("id")) {
							softAssert.assertTrue(entry.getValue().toString().toLowerCase().startsWith("v"),
									"The meta key -" + metaDataKey +" and it's value -" + entry.getValue()+
												" does not start with V");
						}
						break;

					case "junction":
						softAssert.assertTrue(getGisNonPipeMetaDataKeys().contains(metaDataKey),
								"For GIS -" + gis +" meta key -"+ metaDataKey+ " is not from " +
											"the expected list");
						if(metaDataKey.equalsIgnoreCase("id")) {
							softAssert.assertTrue(entry.getValue().toString().toLowerCase().startsWith("j"),
									"The meta key -" + metaDataKey +" and it's value -" + entry.getValue()+
												" does not start with J");
						}
						break;

					case "hydrant":
						softAssert.assertTrue(getGisNonPipeMetaDataKeys().contains(metaDataKey),
								"For GIS -" + gis +" meta key -"+ metaDataKey+ " is not from " +
											"the expected list");
						if(metaDataKey.equalsIgnoreCase("id")) {
							softAssert.assertTrue(entry.getValue().toString().toLowerCase().startsWith("h"),
									"The meta key -" + metaDataKey +" and it's value -"+entry.getValue()+
												" does not start with H");
						}
						break;
				}
			}

		}
	}

	public void verifyGisLocationInformation(String gis,SoftAssert softAssert){
		JsonPath jPath = helper.getJsonPath();
		String locationPath = META_SEARCH_OBJECT_RESPONSE_KEY + "." + META_SEARCH_RESPONSE_HITS_KEY + "." + META_SEARCH_RESPONSE_HITS_KEY + "." + GIS_HITS_LOCATION_KEY;
		List<Object> gisLocationDataList = jPath.getList(locationPath);
		for(int i=0;i<gisLocationDataList.size();i++){
			List<Map<String,Object>> gisLocationDataSubList = jPath.getList(locationPath+"["+i+"]");
			for (Map<String, Object> gisLocationDataMap : gisLocationDataSubList) {
				for (Map.Entry<String, Object> entry : gisLocationDataMap.entrySet()) {
					String locationDataKey = entry.getKey();
					softAssert.assertTrue(getGISLocationDataKeys().contains(locationDataKey),
							"For GIS -" + gis +" location key -"+ locationDataKey+ " is not from the expected list");
					softAssert.assertTrue(!entry.getValue().toString().isEmpty(),
							"For GIS -" + gis +" location key "+ locationDataKey+ " is having an " +
										"empty value at -"+i);
				}
			}
		}
	}
	public List<String> getGisPipeMetaDataKeys() {
		List<String> list = new ArrayList<>();
			list.add("TYPE");
			list.add("id");
			list.add("pipeAge");
			list.add("pipeDiameter");
			list.add("pipeMaterial");
			list.add("zone");

		return list;
	}

	public List<String> getGisNonPipeMetaDataKeys() {
		List<String> list = new ArrayList<>();
		list.add("TYPE");
		list.add("DESCRIPTION");
		list.add("id");
		list.add("zone");

		return list;
	}

	public List<String> getGISLocationDataKeys(){
		List<String> list = new ArrayList<>();
		list.add("lat");
		list.add("lon");
		list.add("sequence");

		return list;
	}

}
