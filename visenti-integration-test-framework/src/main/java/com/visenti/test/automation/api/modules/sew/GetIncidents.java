package com.visenti.test.automation.api.modules.sew;

import java.util.TimeZone;
import com.visenti.test.automation.helpers.FileReaderManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.PortalConfigManagement;
import com.visenti.test.automation.helpers.TestRailTestManagement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetIncidents {

    private static final String ANOMALY_KEY_VALUE_FOR_ALL_THEMES = "\"transient\",\"pressure\",\"flow\",\"customer_meter\",\"acoustic\",\"wqy\"";

    public static Response getIncidentsAPIForAGivenAnomalyForDefaultDays(String anomaly, long days) {

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+1"));
       // PortalConfigManagement portalConfigs = new PortalConfigManagement();
        String api = FileReaderManager.getInstance().getConfigReader().getAPISpecificURLs("aims");
        RestAssured.baseURI = "https://" + PortalConfigManagement.getPortalPrefix() + "-" + api + PortalConfigManagement.getPortalDomain();
        RestAssured.basePath = "/getIncidents";

        TestRailTestManagement.setAPI(RestAssured.baseURI+RestAssured.basePath);
        TestRailTestManagement.setAction("Loading incidents for "+ anomaly + " anomaly type" + " for "+ days +" days");

        RestAssured.useRelaxedHTTPSValidation();

        long endTime = System.currentTimeMillis();
        long startTime = endTime - (24 * 60 * 60 * 1000 * days);
        String payload;

        if (anomaly.equalsIgnoreCase("acoustic") || anomaly.equalsIgnoreCase("customer_meter")
                || anomaly.equalsIgnoreCase("flow") || anomaly.equalsIgnoreCase("pressure")
                || anomaly.equalsIgnoreCase("transient") || anomaly.equalsIgnoreCase("wqy")) {
            anomaly = anomaly.toLowerCase();
            payload = "{\"incidentType\":[\"all\"],\"fromDate\":" + startTime + ",\"toDate\":" + endTime
                    + ",\"rank\":[]," + "\"anomaly\":[\"" + anomaly + "\"],\"recommendation\":[],\"status\":[],"
                    + "\"state\":[\"All\"],\"searchByEventTime\":true}";
        } else if (anomaly.equalsIgnoreCase("all")) {
            payload = "{\"incidentType\":[\"all\"],\"fromDate\":" + startTime + ",\"toDate\":" + endTime
                    + ",\"rank\":[]," + "\"anomaly\":[" + ANOMALY_KEY_VALUE_FOR_ALL_THEMES
                    + "],\"recommendation\":[],\"status\":[]," + "\"state\":[\"All\"],\"searchByEventTime\":true}";
        } else {
            Log.error("Wrong anomaly '" + anomaly + "' passed from feature file");
            throw new RuntimeException("Wrong anomaly '" + anomaly + "' passed from feature file");
        }
        RequestSpecification httpRequest = RestAssured.given()
                .header("X-Service-Customer", PortalConfigManagement.getPortalPrefix()).contentType(ContentType.JSON)
                .body(payload).request().log().all();

        Log.info("Created the Request for " + anomaly + " theme getIncidents API for " + days + " days");

        Response response = httpRequest.when().post();
        Log.info("Performing POST request on the " + anomaly.toUpperCase() + " theme getIncidents API ");

        Log.info("getIncidents API response for " + anomaly.toUpperCase() + " theme \n" + response.asString());
        return response;

    }

}
